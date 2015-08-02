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
import org.adorsys.adcore.jpa.CoreCurrencyEnum;
import org.adorsys.adsales.jpa.SlsSOHstry;
import org.adorsys.adsales.jpa.SlsSOItem;
import org.adorsys.adsales.jpa.SlsSOPtnr;
import org.adorsys.adsales.jpa.SlsSalesOrder;
import org.adorsys.adsales.rest.SlsSOHstryEJB;
import org.adorsys.adsales.rest.SlsSOItemEJB;
import org.adorsys.adsales.rest.SlsSOItemLookup;
import org.adorsys.adsales.rest.SlsSOPtnrEJB;
import org.adorsys.adsales.rest.SlsSalesOrderEJB;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class SaleOrderManager {

	@Inject
	private SecurityUtil securityUtil;
	@Inject
	private SlsSalesOrderEJB slsSalesOrderEJB;
	@Inject
	private SlsSOHstryEJB slsSOHstryEJB;
	@Inject
	private SlsSOItemEJB slsSOItemEJB;
	@Inject
	private SlsSOItemLookup slsSOItemLookup;
	@Inject
	private SlsSOPtnrEJB slsSOPtnrEJB;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public SlsSalesOrderHolder doSale(SlsSalesOrderHolder saleOrderHolder) {
		
		String currentLoginName = securityUtil.getCurrentLoginName();
		Date now = new Date();
		SlsSalesOrder slsSalesOrder = saleOrderHolder.getSlsSalesOrder();		
		slsSalesOrder = createSaleOrderObject(slsSalesOrder, currentLoginName, now);
		
		saleOrderHolder.setSlsSalesOrder(slsSalesOrder);
		boolean modified = false;
			
		modified |= synchSlsSOPtnr(saleOrderHolder);
		
		boolean itemModified = deleteHolders(saleOrderHolder);
		
		List<SlsSOItemHolder> slsSOItemsholder = saleOrderHolder.getSlsSOItemsholder();
		if(slsSOItemsholder==null) slsSOItemsholder=new ArrayList<SlsSOItemHolder>();
		
		for (SlsSOItemHolder itemHolder : slsSOItemsholder) {
			SlsSOItem soItem = itemHolder.getSlsSOItem();
			
			if(StringUtils.isBlank(soItem.getLotPic())) continue;
			
			if(StringUtils.isBlank(soItem.getBsnsObjNbr()))
				soItem.setBsnsObjNbr(slsSalesOrder.getSoNbr());
			// check presence of the article pic
			if(StringUtils.isBlank(soItem.getArtPic()))
				throw new IllegalStateException("Missing article identification code.");

			if(StringUtils.isNotBlank(soItem.getId())){
				// todo check mdified
				  SlsSOItem persSo = slsSOItemLookup.findById(soItem.getId());
				if(persSo==null) throw new IllegalStateException("Missing slsSalesOrder item with id: " + soItem.getId());
				if(!soItem.contentEquals(persSo)){
					soItem.copyTo(persSo);
					soItem.evlte();
					soItem = slsSOItemEJB.update(persSo);
					itemModified = true;
				}
			} else {
				if (StringUtils.isNotBlank(soItem.getBsnsObjNbr())) {
					SlsSOItem persSo = slsSOItemLookup.findById(soItem.getBsnsObjNbr());
					if(persSo!=null){
						if(!soItem.contentEquals(persSo)){
							soItem.copyTo(persSo);
							soItem.evlte();
							soItem = slsSOItemEJB.update(persSo);
							itemModified = true;
						}
					} else {
						soItem.evlte();
						soItem.setBsnsObjNbr(slsSalesOrder.getSoNbr());
						soItem = slsSOItemEJB.create(soItem);
						itemModified = true;
					}
				} else {
					soItem.evlte();
					soItem = slsSOItemEJB.create(soItem);
					itemModified = true;
				}
			}
			itemHolder.setSlsSOItem(soItem);
		}
		if(itemModified){
			recomputeslsSalesOrder(slsSalesOrder);
			slsSalesOrder.setSoStatus(CoreProcessStatusEnum.ONGOING);
			slsSalesOrder = slsSalesOrderEJB.update(slsSalesOrder);
			saleOrderHolder.setSlsSalesOrder(slsSalesOrder);		
		}
		if(modified || itemModified){
			createModifiedslsSalesOrderHistory(slsSalesOrder);
		}
		return saleOrderHolder;
	}

	private void createModifiedslsSalesOrderHistory(SlsSalesOrder slsSalesOrder) {
		TermWsUserPrincipal callerPrincipal = securityUtil.getCallerPrincipal();
		SlsSOHstry saleOrderHstry = new SlsSOHstry();
		saleOrderHstry.setComment(CoreHistoryTypeEnum.MODIFIED.name());
		saleOrderHstry.setAddtnlInfo(SaleOrderInfo.prinInfo(slsSalesOrder));
		saleOrderHstry.setEntIdentif(slsSalesOrder.getId());
		saleOrderHstry.setEntStatus(slsSalesOrder.getSoStatus().name());
		saleOrderHstry.setHstryDt(new Date());
		saleOrderHstry.setHstryType(CoreHistoryTypeEnum.MODIFIED.name());	
		saleOrderHstry.setOrignLogin(callerPrincipal.getName());
		saleOrderHstry.setOrignWrkspc(callerPrincipal.getWorkspaceId());
		saleOrderHstry.setProcStep(CoreProcStepEnum.MODIFYING.name());
		saleOrderHstry.makeHistoryId(true);
		slsSOHstryEJB.create(saleOrderHstry);
	}

	private void recomputeslsSalesOrder(final SlsSalesOrder slsSalesOrder) {
		String soNbr = slsSalesOrder.getSoNbr();
		Long count = slsSOItemLookup.countByBsnsObjNbr(soNbr);
		int start = 0;
		int max = 100;
		slsSalesOrder.clearAmts();
		while(start<=count){
			List<SlsSOItem> list = slsSOItemLookup.findBBsnsObjNbr(soNbr, start, max);
			start +=max;
			for (SlsSOItem slsSOItem : list) {
				slsSalesOrder.addGrossSPPreTax(slsSOItem.getSlsGrossPrcPreTax());
				slsSalesOrder.addRebate(slsSOItem.getSlsRebateAmt());
				slsSalesOrder.addNetSPPreTax(slsSOItem.getSlsNetPrcPreTax());
				slsSalesOrder.addVatAmount(slsSOItem.getSlsVatAmt());
				slsSalesOrder.addNetSPTaxIncl(slsSOItem.getSlsNetPrcTaxIncl());
			}
		}
		slsSalesOrder.evlte();
	}

	private boolean deleteHolders(SlsSalesOrderHolder saleOrderHolder) {
		List<SlsSOItemHolder> soItems = saleOrderHolder.getSlsSOItemsholder();
		List<SlsSOItemHolder> soiToRemove = new ArrayList<SlsSOItemHolder>();
		boolean modified = false;
		for (SlsSOItemHolder itemHolder : soItems) {
			if(itemHolder.isDeleted()){
				SlsSOItem soItem = itemHolder.getSlsSOItem();
				String id = StringUtils.isNotBlank(soItem.getId())?soItem.getId():soItem.getBsnsObjNbr();
				if(StringUtils.isNotBlank(id)){
					slsSOItemEJB.deleteById(id);
					modified = true;					
				}
				soiToRemove.add(itemHolder);
			}
		}
		soItems.removeAll(soiToRemove);
		return modified;
	}

	private boolean synchSlsSOPtnr(SlsSalesOrderHolder saleOrderHolder) {
		SlsSalesOrder salesOrder = saleOrderHolder.getSlsSalesOrder();
		String soNbr = salesOrder.getSoNbr();
		boolean modified = false;
		
		List<SlsSOPtnrHolder> soPtnrs = saleOrderHolder.getSlsSOPtnrsHolder();
		List<SlsSOPtnrHolder> soPtnrsToRemove = new ArrayList<SlsSOPtnrHolder>();
		if(!soPtnrs.isEmpty()){
			for (SlsSOPtnrHolder soPtnrHolder : soPtnrs) {
				SlsSOPtnr soPtnrPersi = slsSOPtnrEJB.findPtnr(soNbr,soPtnrHolder.getSlsSOPtnr().getPtnrNbr(),soPtnrHolder.getSlsSOPtnr().getRoleInSO());
				if(soPtnrHolder.isDeleted()){
					if(soPtnrPersi!=null){
						slsSOPtnrEJB.deleteById(soPtnrPersi.getId());
						soPtnrsToRemove.add(soPtnrHolder);
						modified = true;
					}
					continue;
				}
				if(soPtnrPersi==null){
					soPtnrPersi = slsSOPtnrEJB.addPtnr(salesOrder, soPtnrHolder.getSlsSOPtnr().getPtnrNbr(), soPtnrHolder.getSlsSOPtnr().getRoleInSO());
					soPtnrHolder.setSlsSOPtnr(soPtnrPersi);
					modified = true;
				} else {
					SlsSOPtnr soPtnr = soPtnrHolder.getSlsSOPtnr();
					if(!soPtnr.contentEquals(soPtnrPersi)){
						soPtnr.copyTo(soPtnrPersi);
						soPtnrPersi.setId(soPtnr.getId());
						soPtnrPersi = slsSOPtnrEJB.update(soPtnrPersi);
						soPtnrHolder.setSlsSOPtnr(soPtnrPersi);
						modified = true;
					}
				}
			}
		}
		soPtnrs.removeAll(soPtnrsToRemove);
		return modified;
	}

	private SlsSalesOrder createSaleOrderObject(SlsSalesOrder slsSalesOrder,
			String currentLoginName, Date now) {
	
		if(StringUtils.isBlank(slsSalesOrder.getId())){
			slsSalesOrder.setAcsngUser(currentLoginName);
			slsSalesOrder.setAcsngDt(now);
			if(slsSalesOrder.getSoDt()==null) slsSalesOrder.setSoDt(now);
			slsSalesOrder.setSoStatus(CoreProcessStatusEnum.ONGOING);
			if(slsSalesOrder.getSoCur()==null)slsSalesOrder.setSoCur(CoreCurrencyEnum.XAF.name());
			slsSalesOrder = slsSalesOrderEJB.create(slsSalesOrder);
			createInitialSaleOrderHistory(slsSalesOrder);
		}else{
			slsSalesOrder = slsSalesOrderEJB.findById(slsSalesOrder.getId());
		}
		return slsSalesOrder;
	}

	private void createInitialSaleOrderHistory(SlsSalesOrder slsSalesOrder) {
		TermWsUserPrincipal callerPrincipal = securityUtil.getCallerPrincipal();
		SlsSOHstry saleOrderHstry = new SlsSOHstry();
		saleOrderHstry.setComment(CoreHistoryTypeEnum.INITIATED.name());
		saleOrderHstry.setAddtnlInfo(SaleOrderInfo.prinInfo(slsSalesOrder));
		saleOrderHstry.setEntIdentif(slsSalesOrder.getId());
		saleOrderHstry.setEntStatus(slsSalesOrder.getSoStatus().name());
		saleOrderHstry.setHstryDt(new Date());
		saleOrderHstry.setHstryType(CoreHistoryTypeEnum.INITIATED.name());
		
		saleOrderHstry.setOrignLogin(callerPrincipal.getName());
		saleOrderHstry.setOrignWrkspc(callerPrincipal.getWorkspaceId());
		saleOrderHstry.setProcStep(CoreProcStepEnum.INITIATING.name());
		saleOrderHstry.makeHistoryId(true);
		slsSOHstryEJB.create(saleOrderHstry);
	}

	public SlsSalesOrderHolder findSaleOrder(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
