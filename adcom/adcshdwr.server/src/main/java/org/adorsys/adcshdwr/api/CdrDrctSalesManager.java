/**
 * 
 */
package org.adorsys.adcshdwr.api;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adcore.auth.TermWsUserPrincipal;
import org.adorsys.adcore.enums.CoreHistoryTypeEnum;
import org.adorsys.adcore.enums.CoreProcStepEnum;
import org.adorsys.adcore.exceptions.AdException;
import org.adorsys.adcshdwr.jpa.CdrCshDrawer;
import org.adorsys.adcshdwr.jpa.CdrDrctSales;
import org.adorsys.adcshdwr.jpa.CdrDsArtItem;
import org.adorsys.adcshdwr.jpa.CdrDsHstry;
import org.adorsys.adcshdwr.jpa.CdrDsInfo;
import org.adorsys.adcshdwr.jpa.CdrDsPymntItem;
import org.adorsys.adcshdwr.jpa.CdrPymntMode;
import org.adorsys.adcshdwr.payementevent.DirectSale;
import org.adorsys.adcshdwr.payementevent.PaymentEvent;
import org.adorsys.adcshdwr.payementevent.PymntValidator;
import org.adorsys.adcshdwr.receiptprint.ReceiptPrintTemplatePDF;
import org.adorsys.adcshdwr.rest.CdrCshDrawerEJB;
import org.adorsys.adcshdwr.rest.CdrCstmrVchrEJB;
import org.adorsys.adcshdwr.rest.CdrDrctSalesEJB;
import org.adorsys.adcshdwr.rest.CdrDsArtItemEJB;
import org.adorsys.adcshdwr.rest.CdrDsArtItemLookup;
import org.adorsys.adcshdwr.rest.CdrDrctSalesHstryEJB;
import org.adorsys.adcshdwr.rest.CdrDsPymntItemEJB;
import org.apache.commons.lang3.StringUtils;

/**
 * @author boriswaguia
 *@author guymoyo
 */
@Stateless
public class CdrDrctSalesManager {

	@Inject
	private CdrDrctSalesEJB cdrDrctSalesEJB;

	@Inject
	private CdrDsArtItemEJB cdrDsArtItemEJB;
	@Inject
	private CdrDsArtItemLookup cdrDsArtItemLookup;

	@Inject
	private CdrCshDrawerEJB cshDrawerEJB;

	@Inject
	private CdrDrctSalesHstryEJB cdrDsHstryEJB;

	@Inject
	private SecurityUtil securityUtil;
	
	@Inject
    @DirectSale
    Event<PaymentEvent> directSaleEvent;
	
	@Inject
	CdrDsPymntItemEJB cdrDsPymntItemEJB;
	
	
	@Inject
	CdrCstmrVchrEJB cdrCstmrVchrEJB;
	@Inject
	private PymntValidator pymntValidator;
	
	@Inject
	private CdrDrctSalesPrinterEJB salesPrinterEJB;

	private CdrDsArtHolder updateOrder(CdrDsArtHolder cdrDsArtHolder) throws AdException{
		CdrDrctSales cdrDrctSales = cdrDsArtHolder.getCdrDrctSales();
		CdrCshDrawer activeCshDrawer = cshDrawerEJB.getActiveCshDrawer();
		if(activeCshDrawer == null) throw new AdException("No opened cash drawer found for this session, please open one.");
		cdrDrctSales.setCdrNbr(activeCshDrawer.getCdrNbr());
		if(StringUtils.isBlank(cdrDrctSales.getTxDocNbr())) {
			cdrDrctSales.setTxDocNbr("0000");
		}
		if(StringUtils.isBlank(cdrDrctSales.getId())) {
			cdrDrctSales = cdrDrctSalesEJB.create(cdrDrctSales);
		}
//		boolean modified = false;
		boolean itemModified = deleteHolders(cdrDsArtHolder);

		List<CdrDsArtItemHolder> cdrDsArtItemHolders = cdrDsArtHolder.getItems();
		if(cdrDsArtItemHolders == null) cdrDsArtItemHolders = new ArrayList<CdrDsArtItemHolder>();

		for (CdrDsArtItemHolder cdrDsArtItemHolder : cdrDsArtItemHolders) {
			CdrDsArtItem cdrDsArtItem = cdrDsArtItemHolder.getItem();

			if(StringUtils.isBlank(cdrDsArtItem.getBsnsObjNbr()))
				cdrDsArtItem.setBsnsObjNbr(cdrDrctSales.getDsNbr());
			// check presence of the article pic
			if(StringUtils.isBlank(cdrDsArtItem.getArtPic()))
				throw new AdException("Missing article identification code.");

			if(StringUtils.isNotBlank(cdrDsArtItem.getId())){
				CdrDsArtItem persDi = cdrDsArtItemLookup.findById(cdrDsArtItem.getId());
				if(persDi==null) throw new AdException("Missing directsales item with id: " + cdrDsArtItem.getId());
				if(!cdrDsArtItem.contentEquals(persDi)){
					cdrDsArtItem.copyTo(persDi);
					cdrDsArtItem = cdrDsArtItemEJB.update(persDi);
					itemModified = true;
				}
			} else {
				/*cdrDsArtItem.evlte();
				cdrDsArtItem.setObjctOrgUnit(securityUtil.getCurrentOrgUnit().getIdentif());
				cdrDsArtItem.setDsNbr(cdrDrctSales.getDsNbr());
				cdrDsArtItem = cdrDsArtItemEJB.create(cdrDsArtItem);
				itemModified = true;*/
				if (StringUtils.isNotBlank(cdrDsArtItem.getId())) {
					CdrDsArtItem persDi = cdrDsArtItemLookup.findById(cdrDsArtItem.getBsnsObjNbr());
					if(persDi!=null){
						if(!cdrDsArtItem.contentEquals(persDi)){
							cdrDsArtItem.copyTo(persDi);
							cdrDsArtItem = cdrDsArtItemEJB.update(persDi);
							itemModified = true;
						}
					} else {
						cdrDsArtItem.evlte();
						cdrDsArtItem.setBsnsObjNbr(cdrDrctSales.getDsNbr());
						cdrDsArtItem = cdrDsArtItemEJB.create(cdrDsArtItem);
						itemModified = true;
					}
				} else {
					cdrDsArtItem.evlte();
					if(StringUtils.isBlank(cdrDsArtItem.getOrgUnit())) {
						cdrDsArtItem.setOrgUnit(securityUtil.getCurrentOrgUnit().getIdentif());
					}
					cdrDsArtItem = cdrDsArtItemEJB.create(cdrDsArtItem);
					itemModified = true;
				}
			}

			cdrDsArtItemHolder.setItem(cdrDsArtItem);
		}
		
		// I split this block, because payment detail are done on closing.
		if(itemModified){
			recomputeOrder(cdrDrctSales);
			cdrDrctSales = cdrDrctSalesEJB.update(cdrDrctSales);
			cdrDsArtHolder.setCdrDrctSales(cdrDrctSales);
		}
		return cdrDsArtHolder;
	}


	private boolean deleteHolders(CdrDsArtHolder cdrDsArtHolder){
		List<CdrDsArtItemHolder> cdrDsArtItemHolders = cdrDsArtHolder.getItems();
		List<CdrDsArtItemHolder> oiToRemove = new ArrayList<CdrDsArtItemHolder>();
		boolean modified = false;
		for (CdrDsArtItemHolder itemHolder : cdrDsArtItemHolders) {
			if(itemHolder.isDeleted()){
				CdrDsArtItem cdrDsArtItem = itemHolder.getItem();
				String id = StringUtils.isNotBlank(cdrDsArtItem.getId())?cdrDsArtItem.getId():cdrDsArtItem.getBsnsObjNbr();
				if(StringUtils.isNotBlank(id)){
					cdrDsArtItemEJB.deleteById(id);
					modified = true;					
				}
				oiToRemove.add(itemHolder);
			}
		}
		cdrDsArtItemHolders.removeAll(oiToRemove);
		return modified;
	}

	private void recomputeOrder(final CdrDrctSales cdrDrctSales){
		// recompute sales object.
		String dsNbr = cdrDrctSales.getDsNbr();
		Long count = cdrDsArtItemLookup.countByBsnsObjNbr(dsNbr);
		int start = 0;
		int max = 100;
		cdrDrctSales.clearAmts();
		while(start<=count){
			List<CdrDsArtItem> list = cdrDsArtItemLookup.findBBsnsObjNbr(dsNbr, start, max);
			start +=max;
			for (CdrDsArtItem cdrDsArtItem : list) {
				cdrDrctSales.addGrossSPPreTax(cdrDsArtItem.getSlsGrossPrcPreTax());
				cdrDrctSales.addRebate(cdrDsArtItem.getSlsRebateAmt());
				cdrDrctSales.addNetSPPreTax(cdrDsArtItem.getSlsNetPrcPreTax());
				cdrDrctSales.addVatAmount(cdrDsArtItem.getSlsVatAmt());
				cdrDrctSales.addNetSPTaxIncl(cdrDsArtItem.getSlsNetPrcTaxIncl());
			}
		}
		cdrDrctSales.evlte();
	}

	/**
	 * I am not understanding while this can be different: closeSales and returnSales.
	 * 
	 * Something is wrong here. What happens if a business want to return cash to the
	 * customer that returns an item?
	 * 
	 * @param cdrDsArtHolder
	 * @return
	 * @throws AdException
	 */
	public CdrDsArtHolder closeSales(CdrDsArtHolder cdrDsArtHolder) throws AdException{
		cdrDsArtHolder = updateOrder(cdrDsArtHolder);
		CdrDrctSales cdrDrctSales = cdrDsArtHolder.getCdrDrctSales();

		if(BigDecimal.ZERO.compareTo(cdrDrctSales.getNetAmtToPay())==1){
//			cdrCstmrVchrEJB.generateVoucher(cdrDsArtHolder);
		}else{
			
			PaymentEvent paymentEvent = new PaymentEvent(CdrPymntMode.CASH, cdrDrctSales.getNetAmtToPay(), cdrDsArtHolder.getPaidAmt(), new Date(), cdrDrctSales.getDsNbr());
			pymntValidator.check(paymentEvent);
			directSaleEvent.fire(paymentEvent);	
		}
			
		//Inject an EJB for printing ticket pdf 
		//ReceiptPrintTemplatePDF worker = salesPrinterEJB.printReceiptPdf(cdrDsArtHolder);
		//cdrDsArtHolder.setPrintMode(worker.getReceiptPrintMode());
		
		createClosedSalesHistory(cdrDrctSales);
		cdrDsArtHolder.setCdrDrctSales(cdrDrctSales);
		
		return cdrDsArtHolder;
	}

	/**
	 * Dealing with the return of products.
	 * 
	 * It is important not to modify the content of a direct sales. This means that if we receive
	 * a return request, we have to check if the original direct sales object was closed. If
	 * this is the case, we have to create a new one and compute only the value returned.
	 * 
	 * @param cdrDsArtHolder
	 * @return
	 * @throws AdException
	public CdrDsArtHolder returnProduct(CdrDsArtHolder cdrDsArtHolder) throws AdException {
		// First process return.
		CdrDrctSales cdrDrctSales = cdrDsArtHolder.getCdrDrctSales();
		if(StringUtils.isNotBlank(cdrDrctSales.getDsNbr())){// This direct sales exists. Check it's status.
			CdrDsHstry model = new CdrDsHstry();
			model.setEntIdentif(cdrDrctSales.getDsNbr());
			model.setHstryType(BaseHistoryTypeEnum.CLOSED.name());	
			CdrDrctSales found = cdrDrctSalesEJB.findById(cdrDrctSales.getDsNbr());
			Long countBy = cdrDsHstryEJB.countBy(model, new SingularAttribute[]{CdrDsHstry_.entIdentif, CdrDsHstry_.hstryType});
			if(countBy>0){ // Sales is closed. Create a new sales and copy returned items
				cdrDsArtHolder = returnFromClosedSales(cdrDsArtHolder);
			} else {
				cdrDsArtHolder = updateOrder(cdrDsArtHolder);				
			}
		} else {
			cdrDsArtHolder = updateOrder(cdrDsArtHolder);				
		}

		cdrCstmrVchrEJB.generateVoucher(cdrDsArtHolder);

		createClosedSalesHistory(cdrDsArtHolder.getCdrDrctSales());

		return cdrDsArtHolder;
	}
	 */

	public CdrDsArtHolder findOrder(String id){
		CdrDsArtHolder cdrDsArtHolder = new CdrDsArtHolder();
		CdrDrctSales cdrDrctSales = cdrDrctSalesEJB.findById(id);
		cdrDsArtHolder.setCdrDrctSales(cdrDrctSales);
//		List<CdrDsArtItem> cdrDsArtItems = cdrDsArtItemLookup.findByHldrNbr(cdrDrctSales.getDsNbr());
//		for(CdrDsArtItem cdrDsArtItem:cdrDsArtItems) {
//			CdrDsArtItemHolder cdrDsArtItemHolder = new CdrDsArtItemHolder();
//			cdrDsArtItemHolder.setItem(cdrDsArtItem);
//			cdrDsArtHolder.getItems().add(cdrDsArtItemHolder);
//		}
		return cdrDsArtHolder;
	}


	private void createClosedSalesHistory(CdrDrctSales cdrDrctSales){
		TermWsUserPrincipal callerPrincipal = securityUtil.getCallerPrincipal();
		CdrDsHstry cdrDsHstry = new CdrDsHstry();

		cdrDsHstry.setComment(CoreHistoryTypeEnum.CLOSED.name());
		cdrDsHstry.setAddtnlInfo(CdrDsInfo.prinInfo(cdrDrctSales));
		cdrDsHstry.setEntIdentif(cdrDrctSales.getId());
		//		cdrDsHstry.setEntStatus(prcmtOrder.getPoStatus());
		cdrDsHstry.setHstryDt(new Date());
		cdrDsHstry.setHstryType(CoreHistoryTypeEnum.CLOSED.name());	
		cdrDsHstry.setOrignLogin(callerPrincipal.getName());
		cdrDsHstry.setOrignWrkspc(callerPrincipal.getWorkspaceId());
		cdrDsHstry.setProcStep(CoreProcStepEnum.CLOSING.name());
		cdrDsHstry.makeHistoryId(true);
		cdrDsHstryEJB.create(cdrDsHstry);
	}

	// TODO talk with francis. No full scan on database. Always use cursor.
	public CdrDsArtHolder findCdrDsArtHolder(String id) {
		CdrDsArtHolder cdrDsArtHolder = new CdrDsArtHolder();
		CdrDrctSales cdrDrctSales = cdrDrctSalesEJB.findById(id);
		cdrDsArtHolder.setCdrDrctSales(cdrDrctSales);
//		List<CdrDsArtItem> listItem = cdrDsArtItemLookup.findByHldrNbr(cdrDrctSales.getDsNbr());
//		for(CdrDsArtItem item:listItem){
//			CdrDsArtItemHolder cdrDsArtItemHolder = new CdrDsArtItemHolder();
//			cdrDsArtItemHolder.setItem(item);
//			cdrDsArtItemHolder.setArtName(item.getArtName());
//			cdrDsArtHolder.getItems().add(cdrDsArtItemHolder);
//		}
//	
//		List<CdrDsPymntItem> findByDsNbr = cdrDsPymntItemEJB.findByDsNbr(cdrDrctSales.getDsNbr());
//		if(!findByDsNbr.isEmpty()){
//			CdrDsPymntItem cdrDsPymntItem = findByDsNbr.get(0);
//			cdrDsArtHolder.setPaidAmt(cdrDsPymntItem.getRcvdAmt());
//			cdrDsArtHolder.setChangeAmt(cdrDsPymntItem.getDiffAmt());	
//		}
		return cdrDsArtHolder;
	}
	
	
	
	

}