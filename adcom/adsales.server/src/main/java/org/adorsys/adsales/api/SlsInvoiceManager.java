package org.adorsys.adsales.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adcore.auth.TermWsUserPrincipal;
import org.adorsys.adcore.enums.CoreHistoryTypeEnum;
import org.adorsys.adcore.enums.CoreProcStepEnum;
import org.adorsys.adcore.enums.CoreProcessStatusEnum;
import org.adorsys.adcore.exceptions.AdException;
import org.adorsys.adcore.jpa.CoreCurrencyEnum;
import org.adorsys.adsales.jpa.SlsInvceHistory;
import org.adorsys.adsales.jpa.SlsInvceItem;
import org.adorsys.adsales.jpa.SlsInvcePtnr;
import org.adorsys.adsales.jpa.SlsInvcePymtStatus;
import org.adorsys.adsales.jpa.SlsInvoice;
import org.adorsys.adsales.rest.SlsInvceHistoryEJB;
import org.adorsys.adsales.rest.SlsInvceItemEJB;
import org.adorsys.adsales.rest.SlsInvcePtnrEJB;
import org.adorsys.adsales.rest.SlsInvoiceEJB;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class SlsInvoiceManager {

	@Inject
	private SecurityUtil securityUtil;
	@Inject
	private SlsInvoiceEJB slsInvoiceEJB;
	@Inject
	private SlsInvceHistoryEJB slsInvceHistoryEJB;
	@Inject
	private SlsInvceItemEJB slsInvceItemEJB;
	@Inject
	private SlsInvcePtnrEJB slsInvcePtnrEJB;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public SlsInvoiceHolder processInvoice(SlsInvoiceHolder invoiceHolder) throws AdException {
		
		String currentLoginName = securityUtil.getCurrentLoginName();
		Date now = new Date();
		SlsInvoice slsInvoice = invoiceHolder.getSlsInvoice();		
		slsInvoice = createinvoiceObject(slsInvoice, currentLoginName, now);
		
		invoiceHolder.setSlsInvoice(slsInvoice);
		boolean modified = false;
			
		modified |= synchslsInvcePtnr(invoiceHolder);
		
		boolean itemModified = deleteHolders(invoiceHolder);
		
		List<SlsInvceItemHolder> slsInvceItemsholder = invoiceHolder.getSlsInvceItemsholder();
		if(slsInvceItemsholder==null) slsInvceItemsholder=new ArrayList<SlsInvceItemHolder>();
		
		for (SlsInvceItemHolder itemHolder : slsInvceItemsholder) {
			SlsInvceItem invceItem = itemHolder.getSlsInvceItem();
			
			if(StringUtils.isBlank(invceItem.getLotPic())) continue;
			
			if(StringUtils.isBlank(invceItem.getInvNbr()))
				invceItem.setInvNbr(slsInvoice.getInvceNbr());
			// check presence of the article pic
			if(StringUtils.isBlank(invceItem.getArtPic()))
				throw new AdException("Missing article identification code.");

			if(StringUtils.isNotBlank(invceItem.getId())){
				// todo check mdified
				   SlsInvceItem persInvce = slsInvceItemEJB.findById(invceItem.getId());
				if(persInvce==null) throw new AdException("Missing slsInvoice item with id: " + invceItem.getId());
				if(!invceItem.contentEquals(persInvce)){
					invceItem.copyTo(persInvce);
					invceItem.evlte();
					invceItem = slsInvceItemEJB.update(persInvce);
					itemModified = true;
				}
			} else {
				if (StringUtils.isNotBlank(invceItem.getInvNbr())) {
					SlsInvceItem persInvce = slsInvceItemEJB.findById(invceItem.getInvNbr());
					if(persInvce!=null){
						if(!invceItem.contentEquals(persInvce)){
							invceItem.copyTo(persInvce);
							invceItem.evlte();
							invceItem = slsInvceItemEJB.update(persInvce);
							itemModified = true;
						}
					} else {
						invceItem.evlte();
						invceItem.setInvNbr(slsInvoice.getInvceNbr());
						invceItem = slsInvceItemEJB.create(invceItem);
						itemModified = true;
					}
				} else {
					invceItem.evlte();
					invceItem = slsInvceItemEJB.create(invceItem);
					itemModified = true;
				}
			}
			itemHolder.setSlsInvceItem(invceItem);
		}
		if(itemModified){
			recomputeslsInvoice(slsInvoice);
			slsInvoice.setInvceStatus(CoreProcessStatusEnum.ONGOING.name());
			slsInvoice = slsInvoiceEJB.update(slsInvoice);
			invoiceHolder.setSlsInvoice(slsInvoice);		
		}
		if(modified || itemModified){
			createModifiedInvoiceHistory(slsInvoice);
		}
		return invoiceHolder;
	}

	private void recomputeslsInvoice(final SlsInvoice slsInvoice) {
		String InvNbr = slsInvoice.getInvceNbr();
		Long count = slsInvceItemEJB.countByInvceNbr(InvNbr);
		int start = 0;
		int max = 100;
		slsInvoice.clearAmts();
		while(start<=count){
			List<SlsInvceItem> list = slsInvceItemEJB.findInvceNbr(InvNbr, start, max);
			start +=max;
			for (SlsInvceItem slsInvceItem : list) {
				slsInvoice.addGrossSPPreTax(slsInvceItem.getGrossSPPreTax());
				slsInvoice.addRebate(slsInvceItem.getRebate());
				slsInvoice.addNetSPPreTax(slsInvceItem.getNetSPPreTax());
				slsInvoice.addVatAmount(slsInvceItem.getVatAmount());
				slsInvoice.addNetSPTaxIncl(slsInvceItem.getNetSPTaxIncl());
			}
		}
		slsInvoice.evlte();
	}

	private boolean deleteHolders(SlsInvoiceHolder invoiceHolder) {
		List<SlsInvceItemHolder> invceItems = invoiceHolder.getSlsInvceItemsholder();
		List<SlsInvceItemHolder> soiToRemove = new ArrayList<SlsInvceItemHolder>();
		boolean modified = false;
		for (SlsInvceItemHolder itemHolder : invceItems) {
			if(itemHolder.isDeleted()){
				SlsInvceItem invceItem = itemHolder.getSlsInvceItem();
				String id = StringUtils.isNotBlank(invceItem.getId())?invceItem.getId():invceItem.getInvNbr();
				if(StringUtils.isNotBlank(id)){
					slsInvceItemEJB.deleteById(id);
					modified = true;					
				}
				soiToRemove.add(itemHolder);
			}
		}
		invceItems.removeAll(soiToRemove);
		return modified;
	}

	private boolean synchslsInvcePtnr(SlsInvoiceHolder invoiceHolder) {
		SlsInvoice Invoice = invoiceHolder.getSlsInvoice();
		String InvNbr = Invoice.getInvceNbr();
		boolean modified = false;
		
		List<SlsInvcePtnrHolder> invcePtnrs = invoiceHolder.getSlsInvcePtnrsHolder();
		List<SlsInvcePtnrHolder> invcePtnrsToRemove = new ArrayList<SlsInvcePtnrHolder>();
		if(!invcePtnrs.isEmpty()){
			for (SlsInvcePtnrHolder invcePtnrHolder : invcePtnrs) {
				SlsInvcePtnr invcePtnrPersi = slsInvcePtnrEJB.findPtnr(InvNbr,invcePtnrHolder.getSlsInvcePtnr().getPtnrNbr(),invcePtnrHolder.getSlsInvcePtnr().getRoleInInvce());
				if(invcePtnrHolder.isDeleted()){
					if(invcePtnrPersi!=null){
						slsInvcePtnrEJB.deleteById(invcePtnrPersi.getId());
						invcePtnrsToRemove.add(invcePtnrHolder);
						modified = true;
					}
					continue;
				}
				if(invcePtnrPersi==null){
					invcePtnrPersi = slsInvcePtnrEJB.addPtnr(Invoice, invcePtnrHolder.getSlsInvcePtnr().getPtnrNbr(), invcePtnrHolder.getSlsInvcePtnr().getRoleInInvce());
					invcePtnrHolder.setSlsInvcePtnr(invcePtnrPersi);
					modified = true;
				} else {
					SlsInvcePtnr invcePtnr = invcePtnrHolder.getSlsInvcePtnr();
					if(!invcePtnr.contentEquals(invcePtnrPersi)){
						invcePtnr.copyTo(invcePtnrPersi);
						invcePtnrPersi.setId(invcePtnr.getId());
						invcePtnrPersi = slsInvcePtnrEJB.update(invcePtnrPersi);
						invcePtnrHolder.setSlsInvcePtnr(invcePtnrPersi);
						modified = true;
					}
				}
			}
		}
		invcePtnrs.removeAll(invcePtnrsToRemove);
		return modified;
	}

	private SlsInvoice createinvoiceObject(SlsInvoice slsInvoice,
			String currentLoginName, Date now) {
	
		if(StringUtils.isBlank(slsInvoice.getId())){
			slsInvoice.setCreatingUsr(currentLoginName);
			if(slsInvoice.getInvceDt()==null) slsInvoice.setInvceDt(now);
			slsInvoice.setInvceStatus(CoreProcessStatusEnum.ONGOING.name());
			if(slsInvoice.getInvceCur()==null)slsInvoice.setInvceCur(CoreCurrencyEnum.XAF.name());
			slsInvoice = slsInvoiceEJB.create(slsInvoice);
			createInitialinvoiceHistory(slsInvoice);
		}else{
			slsInvoice = slsInvoiceEJB.findById(slsInvoice.getId());
		}
		return slsInvoice;
	}
	
	private SlsInvceHistory createinvoiceHistory(SlsInvoice slsInvoice, String baseHistoryTypeEnum, String baseProcStepEnum) {
		TermWsUserPrincipal callerPrincipal = securityUtil.getCallerPrincipal();
		SlsInvceHistory invoiceHstry = new SlsInvceHistory();
		invoiceHstry.setComment(baseHistoryTypeEnum);
		invoiceHstry.setAddtnlInfo(SlsInvoiceInfo.prinInfo(slsInvoice));
		invoiceHstry.setEntIdentif(slsInvoice.getId());
		invoiceHstry.setEntStatus(slsInvoice.getInvceStatus());
		invoiceHstry.setHstryDt(new Date());
		invoiceHstry.setHstryType(baseHistoryTypeEnum);	
		invoiceHstry.setOrignLogin(callerPrincipal.getName());
		invoiceHstry.setOrignWrkspc(callerPrincipal.getWorkspaceId());
		invoiceHstry.setProcStep(baseProcStepEnum);
		invoiceHstry.makeHistoryId(true);
		return slsInvceHistoryEJB.create(invoiceHstry);
	}
	private void createInitialinvoiceHistory(SlsInvoice slsInvoice) {
		createinvoiceHistory(slsInvoice,CoreHistoryTypeEnum.INITIATED.name(),CoreProcStepEnum.INITIATING.name());
	}
	private void createModifiedInvoiceHistory(SlsInvoice slsInvoice) {
		createinvoiceHistory(slsInvoice,CoreHistoryTypeEnum.MODIFIED.name(),CoreProcStepEnum.MODIFYING.name());
	}
	private void createClosedinvoiceHistory(SlsInvoice slsInvoice) {
		createinvoiceHistory(slsInvoice,CoreHistoryTypeEnum.CLOSED.name(),CoreProcStepEnum.CLOSING.name());
	}
	private void createSuspendedinvoiceHistory(SlsInvoice slsInvoice) {
		createinvoiceHistory(slsInvoice,CoreHistoryTypeEnum.SUSPENDED.name(),CoreProcStepEnum.SUSPENDING.name());
	}
	private void createResumedinvoiceHistory(SlsInvoice slsInvoice) {
		createinvoiceHistory(slsInvoice,CoreHistoryTypeEnum.RESUMED.name(),CoreProcStepEnum.RESUMING.name());
	}
	private void createDeliveredinvoiceHistory(SlsInvoice slsInvoice) {
		createinvoiceHistory(slsInvoice,CoreHistoryTypeEnum.DELIVERED.name(),CoreProcStepEnum.DELIVERING.name());
	}


	public SlsInvoiceHolder findInvoice(String id) {
		SlsInvoiceHolder slsInvoiceHolder = new SlsInvoiceHolder();
		SlsInvoice slsInvoice = slsInvoiceEJB.findById(id);
		slsInvoiceHolder.setSlsInvoice(slsInvoice);
		
		List<SlsInvceItem> listItems = slsInvceItemEJB.findInvceNbr(slsInvoice.getInvceNbr());
		for(SlsInvceItem item:listItems){
			SlsInvceItemHolder slsInvceItemHolder = new SlsInvceItemHolder();
			slsInvceItemHolder.setSlsInvceItem(item);
			slsInvoiceHolder.getSlsInvceItemsholder().add(slsInvceItemHolder);
		}
		List<SlsInvcePtnr> listPtnrs = slsInvcePtnrEJB.findByInvceNbr(slsInvoice.getInvceNbr());
		for(SlsInvcePtnr ptnr:listPtnrs){
			SlsInvcePtnrHolder slsInvcePtnrHolder = new SlsInvcePtnrHolder();
			slsInvcePtnrHolder.setSlsInvcePtnr(ptnr);
			slsInvoiceHolder.getSlsInvcePtnrsHolder().add(slsInvcePtnrHolder);
		}	
		return slsInvoiceHolder;
	}

	public SlsInvoiceHolder saveInvoice(SlsInvoiceHolder slsInvoiceHolder) throws AdException {
				return processInvoice(slsInvoiceHolder);
	}

	public SlsInvoiceHolder closeInvoice(SlsInvoiceHolder slsInvoiceHolder) throws AdException {
		slsInvoiceHolder = processInvoice(slsInvoiceHolder);
		SlsInvoice slsInvoice = slsInvoiceHolder.getSlsInvoice();
		slsInvoice.setInvceStatus(CoreProcessStatusEnum.CLOSED.name());
		slsInvoice = slsInvoiceEJB.update(slsInvoice);
		slsInvoiceHolder.setSlsInvoice(slsInvoice);
		createClosedinvoiceHistory(slsInvoice);
		return slsInvoiceHolder;
	}

	public SlsInvoice suspendInvoice(String invce) throws AdException {
		SlsInvoice slsInvoice = slsInvoiceEJB.findById(invce);
		if(SlsInvcePymtStatus.AVANCE.equals(slsInvoice.getInvcePymntStatus()) 
				|| SlsInvcePymtStatus.PAYE.equals(slsInvoice.getInvcePymntStatus())
				|| slsInvoice.getInvceDelivered())
			throw new AdException("Facture paye ou livre, vous ne pouvez suspendre");
		
		slsInvoice.setInvceStatus(CoreProcessStatusEnum.SUSPENDED.name());
		slsInvoice = slsInvoiceEJB.update(slsInvoice);
		createSuspendedinvoiceHistory(slsInvoice);
		return slsInvoice;
	}

	/**
	 * Make sure each invoice item carries the information lotPic and section. If not promt for 
	 * collection of those infos.
	 * 
	 * @param id
	 * @return
	 */
	public SlsInvoice deliveredInvoice(String id) {
		SlsInvoice slsInvoice = slsInvoiceEJB.findById(id);
		// TODO verify info on lotPic and section.
		
		// TODO check that lot price is identical to initial lot price.
		// If lot different.
		slsInvoice.setInvceDelivered(true);
		slsInvoice = slsInvoiceEJB.update(slsInvoice);
		createDeliveredinvoiceHistory(slsInvoice);
		return slsInvoice;
	}

	public SlsInvoice resumeInvoice(String invce) {
		SlsInvoice slsInvoice = slsInvoiceEJB.findById(invce);
		slsInvoice.setInvceStatus(CoreProcessStatusEnum.RESUMED.name());
		slsInvoice = slsInvoiceEJB.update(slsInvoice);
		createResumedinvoiceHistory(slsInvoice);
		return slsInvoice;
	}

}
