package org.adorsys.adprocmt.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adcore.auth.TermWsUserPrincipal;
import org.adorsys.adcore.enums.CoreHistoryTypeEnum;
import org.adorsys.adcore.enums.CoreProcStepEnum;
import org.adorsys.adcore.enums.CoreProcessStatusEnum;
import org.adorsys.adprocmt.jpa.PrcmtDelivery;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2Ou;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2POItem;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2StrgSctn;
import org.adorsys.adprocmt.jpa.PrcmtPOItem;
import org.adorsys.adprocmt.jpa.PrcmtProcOrder;
import org.adorsys.adprocmt.jpa.PrcmtProcOrderHstry;
import org.adorsys.adprocmt.rest.PrcmtDeliveryEJB;
import org.adorsys.adprocmt.rest.PrcmtDlvryItemEJB;
import org.adorsys.adprocmt.rest.PrcmtDlvryItemLookup;
import org.adorsys.adprocmt.rest.PrcmtPOItemEJB;
import org.adorsys.adprocmt.rest.PrcmtPOItemLookup;
import org.adorsys.adprocmt.rest.PrcmtProcOrderEJB;
import org.adorsys.adprocmt.rest.PrcmtProcOrderHstryEJB;
import org.adorsys.adprocmt.trigger.TriggerModeExecuter;
import org.adorsys.adprocmt.trigger.TriggerModeHandlerFactoryProducer;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class PrcmtOrderManager {
	@Inject
	private PrcmtProcOrderEJB prcmtOrderEJB;
	@Inject
	private PrcmtPOItemEJB prcmtPOItemEJB;
	@Inject
	private PrcmtPOItemLookup prcmtPOItemLookup;
	@Inject
	private PrcmtProcOrderHstryEJB orderHstryEJB;
	@Inject
	private PrcmtDeliveryEJB deliveryEJB;
	@Inject
	private SecurityUtil securityUtil;
	@Inject
	private PrcmtDlvryItemEJB dlvryItemEJB;
	@Inject
	private PrcmtDlvryItemLookup dlvryItemLookup;
	@Inject
	private TriggerModeHandlerFactoryProducer triggerModeHandlerFactoryProducer;
	
	public PrcmtOrderHolder createPcrmtOrder(PrcmtProcOrder prcmtOrder){
		
		PrcmtProcOrder prcmtProcOrder = prcmtOrderEJB.createCustom(prcmtOrder);
		TriggerModeExecuter triggerModeExecuter = triggerModeHandlerFactoryProducer.getTriggerModeHandlerFactory().findHandler(prcmtOrder.getPoTriggerMode());
		PrcmtOrderHolder prcmtOrderHolder = triggerModeExecuter.executeTriggerMode(prcmtProcOrder);	
		return updateOrder(prcmtOrderHolder);
	}

	public PrcmtOrderHolder updateOrder(PrcmtOrderHolder prcmtOrderHolder){
		PrcmtProcOrder prcmtOrder = prcmtOrderHolder.getPrcmtProcOrder();
		prcmtOrder = prcmtOrderEJB.findByPoNbr(prcmtOrder.getPoNbr());//flush the entity, to avoid opstimic exception
		
		boolean modified = false;
		boolean itemModified = deleteHolders(prcmtOrderHolder);
					
		List<PrcmtOrderItemHolder> poItems = prcmtOrderHolder.getPoItems();
		if(poItems == null) poItems = new ArrayList<PrcmtOrderItemHolder>();
		
		for (PrcmtOrderItemHolder itemHolder : poItems) {
			PrcmtPOItem orderItem = itemHolder.getPrcmtPOItem();

			if(StringUtils.isBlank(orderItem.getBsnsObjNbr()))
				orderItem.setBsnsObjNbr(prcmtOrder.getPoNbr());
			// check presence of the article pic
			if(StringUtils.isBlank(orderItem.getArtPic()))
				throw new IllegalStateException("Missing article identification code.");

			if(StringUtils.isNotBlank(orderItem.getId())){
				// todo check mdified
				PrcmtPOItem persDi = prcmtPOItemLookup.findById(orderItem.getId());
				if(persDi==null) throw new IllegalStateException("Missing delivery item with id: " + orderItem.getId());
				if(!orderItem.contentEquals(persDi)){
					orderItem.copyTo(persDi);
					orderItem.evlte();
					orderItem = prcmtPOItemEJB.update(persDi);
					itemModified = true;
				}
			} else {
				if (StringUtils.isNotBlank(orderItem.getBsnsObjNbr())) {
					PrcmtPOItem persDi = prcmtPOItemLookup.findById(orderItem.getBsnsObjNbr());
					if(persDi!=null){
						if(!orderItem.contentEquals(persDi)){
							orderItem.copyTo(persDi);
							orderItem.evlte();
							orderItem = prcmtPOItemEJB.update(persDi);
							itemModified = true;
						}
					} else {
						orderItem.evlte();
						orderItem.setBsnsObjNbr(prcmtOrder.getPoNbr());
						orderItem = prcmtPOItemEJB.create(orderItem);
						itemModified = true;
					}
				} else {
					orderItem.evlte();
					orderItem = prcmtPOItemEJB.create(orderItem);
					itemModified = true;
				}
			}

			itemHolder.setPrcmtPOItem(orderItem);
		}
		
		if(itemModified){
			recomputeOrder(prcmtOrder);
			prcmtOrder.setPoStatus(CoreProcessStatusEnum.ONGOING.name());
			prcmtOrder = prcmtOrderEJB.update(prcmtOrder);
			prcmtOrderHolder.setPrcmtProcOrder(prcmtOrder);		
		}
		if(modified || itemModified){
			createModifiedOrderHistory(prcmtOrder);
		}
		return prcmtOrderHolder;
	}
	
	private void recomputeOrder(final PrcmtProcOrder prcmtOrder){
		// update order object.
		String poNbr = prcmtOrder.getPoNbr();
		Long count = prcmtPOItemLookup.countByBsnsObjNbr(poNbr);
		int start = 0;
		int max = 100;
		prcmtOrder.clearAmts();
		while(start<=count){
			List<PrcmtPOItem> list = prcmtPOItemLookup.findBBsnsObjNbr(poNbr, start, max);
			start +=max;
			for (PrcmtPOItem prcmtOrderItem : list) {
				prcmtOrder.addGrossPPPreTax(prcmtOrderItem.getPrchGrossPrcPreTax());
				prcmtOrder.addRebate(prcmtOrderItem.getPrchRebateAmt());
				prcmtOrder.addNetPPPreTax(prcmtOrderItem.getPrchNetPrcPreTax());
				prcmtOrder.addVatAmount(prcmtOrderItem.getPrchVatAmt());
				prcmtOrder.addNetPPTaxIncl(prcmtOrderItem.getPrchNetPrcTaxIncl());
			}
		}
		prcmtOrder.evlte();
	}
	
	private void createModifiedOrderHistory(PrcmtProcOrder prcmtOrder){
		TermWsUserPrincipal callerPrincipal = securityUtil.getCallerPrincipal();
		PrcmtProcOrderHstry orderHstry = new PrcmtProcOrderHstry();
		orderHstry.setComment(CoreHistoryTypeEnum.MODIFIED.name());
		orderHstry.setAddtnlInfo(ProcOrderInfo.prinInfo(prcmtOrder));
		orderHstry.setEntIdentif(prcmtOrder.getId());
		orderHstry.setEntStatus(prcmtOrder.getPoStatus());
		orderHstry.setHstryDt(new Date());
		orderHstry.setHstryType(CoreHistoryTypeEnum.MODIFIED.name());	
		orderHstry.setOrignLogin(callerPrincipal.getName());
		orderHstry.setOrignWrkspc(callerPrincipal.getWorkspaceId());
		orderHstry.setProcStep(CoreProcStepEnum.MODIFYING.name());
		orderHstry.makeHistoryId(false);
		orderHstryEJB.create(orderHstry);
	}
	
	private void createClosedOrderHistory(PrcmtProcOrder prcmtOrder){
		TermWsUserPrincipal callerPrincipal = securityUtil.getCallerPrincipal();
		PrcmtProcOrderHstry orderHstry = new PrcmtProcOrderHstry();
		orderHstry.setComment(CoreHistoryTypeEnum.CLOSED.name());
		orderHstry.setAddtnlInfo(ProcOrderInfo.prinInfo(prcmtOrder));
		orderHstry.setEntIdentif(prcmtOrder.getId());
		orderHstry.setEntStatus(prcmtOrder.getPoStatus());
		orderHstry.setHstryDt(new Date());
		orderHstry.setHstryType(CoreHistoryTypeEnum.CLOSED.name());	
		orderHstry.setOrignLogin(callerPrincipal.getName());
		orderHstry.setOrignWrkspc(callerPrincipal.getWorkspaceId());
		orderHstry.setProcStep(CoreProcStepEnum.CLOSING.name());
		orderHstry.makeHistoryId(true);
		orderHstryEJB.create(orderHstry);
	}
	
	
	private boolean deleteHolders(PrcmtOrderHolder prcmtOrderHolder){
		List<PrcmtOrderItemHolder> poItems = prcmtOrderHolder.getPoItems();
		List<PrcmtOrderItemHolder> oiToRemove = new ArrayList<PrcmtOrderItemHolder>();
		boolean modified = false;
		for (PrcmtOrderItemHolder itemHolder : poItems) {
			if(itemHolder.isDeleted()){
				PrcmtPOItem orderItem = itemHolder.getPrcmtPOItem();
				String id = StringUtils.isNotBlank(orderItem.getId())?orderItem.getId():orderItem.getBsnsObjNbr();
				if(StringUtils.isNotBlank(id)){
					prcmtPOItemEJB.deleteById(id);
					modified = true;					
				}
				oiToRemove.add(itemHolder);
			}
		}
		poItems.removeAll(oiToRemove);
		return modified;
	}
	
	public PrcmtOrderHolder closeOrder(PrcmtOrderHolder prcmtOrderHolder){
		prcmtOrderHolder = updateOrder(prcmtOrderHolder);
		PrcmtProcOrder procOrder = prcmtOrderHolder.getPrcmtProcOrder();
		procOrder = prcmtOrderEJB.findByPoNbr(procOrder.getPoNbr());
		//recomputeOrder(procOrder);
		if(!StringUtils.equals(CoreProcessStatusEnum.CLOSED.name(), procOrder.getPoStatus())){
			procOrder.setPoStatus(CoreProcessStatusEnum.CLOSED.name());
			procOrder = prcmtOrderEJB.update(procOrder);	
			createClosedOrderHistory(procOrder);// closed, no need processor?
		}
		prcmtOrderHolder.setPrcmtProcOrder(procOrder);
		return prcmtOrderHolder;
	}

	// TODO: remove full scan.
	public PrcmtOrderHolder findOrder(String id){
		PrcmtOrderHolder prcmtOrderHolder = new PrcmtOrderHolder();
		PrcmtProcOrder procOrder = prcmtOrderEJB.findById(id);
		prcmtOrderHolder.setPrcmtProcOrder(procOrder);
//		List<PrcmtPOItem> poItems = prcmtPOItemLookup.findByHldrNbr(procOrder.getPoNbr());
//		for(PrcmtPOItem poi:poItems) {
//			PrcmtOrderItemHolder prcmtOrderItemHolder = new PrcmtOrderItemHolder();	
//			prcmtOrderItemHolder.setPrcmtPOItem(poi);
//			prcmtOrderHolder.getPoItems().add(prcmtOrderItemHolder);
//		}
		return prcmtOrderHolder;
	}
	
	public PrcmtDeliveryHolder order2Delivery(PrcmtOrderHolder prcmtOrderHolder){
		
		if(!StringUtils.equals(CoreProcessStatusEnum.CLOSED.name(), prcmtOrderHolder.getPrcmtProcOrder().getPoStatus())) {
			 prcmtOrderHolder = closeOrder(prcmtOrderHolder);
		}
		PrcmtProcOrder procOrder = prcmtOrderHolder.getPrcmtProcOrder();
		PrcmtDelivery delivery = new PrcmtDelivery();
		delivery.fillDataFromOrder(procOrder);
		deliveryEJB.createCustom(delivery);
		
		PrcmtDeliveryHolder deliveryHolder = new PrcmtDeliveryHolder();
		deliveryHolder.setDelivery(delivery);
		
		List<PrcmtOrderItemHolder> poItems = prcmtOrderHolder.getPoItems();
		for(PrcmtOrderItemHolder poItemHolder:poItems){
			PrcmtPOItem prcmtPOItem = poItemHolder.getPrcmtPOItem();
			PrcmtDlvryItem dlvryItem = new PrcmtDlvryItem();
			dlvryItem.fillDataFromOrderItem(prcmtPOItem);
			dlvryItem.evlte();
			dlvryItem.setBsnsObjNbr(delivery.getDlvryNbr());
			dlvryItem = dlvryItemEJB.create(dlvryItem);
			
			PrcmtDlvryItem2POItem dlvryItem2POItem = dlvryItemEJB.addDlvryItem2POItem(
					dlvryItem, prcmtPOItem.getBsnsObjNbr(), prcmtPOItem.getQtyDlvrd(), dlvryItem.getQtyDlvrd(), dlvryItem.getFreeQty());
			PrcmtDlvryItem2PoItemHolder prcmtDlvryItem2PoItemHolder = new PrcmtDlvryItem2PoItemHolder();
			prcmtDlvryItem2PoItemHolder.setPoItem(dlvryItem2POItem);
			
			PrcmtDeliveryItemHolder deliveryItemHolder = new PrcmtDeliveryItemHolder();
			deliveryItemHolder.setDlvryItem(dlvryItem);
			deliveryItemHolder.getPoItems().add(prcmtDlvryItem2PoItemHolder);
			
			
			if(StringUtils.isNotBlank(prcmtPOItem.getOrgUnit())){
				PrcmtDlvryItem2Ou dlvryItem2Ou = dlvryItemEJB.addDlvryItem2Ou(dlvryItem, prcmtPOItem.getOrgUnit(), dlvryItem.getQtyDlvrd(), dlvryItem.getFreeQty());
				PrcmtDlvryItem2RcvngOrgUnitHolder prcmtDlvryItem2RcvngOrgUnitHolder = new PrcmtDlvryItem2RcvngOrgUnitHolder();
				prcmtDlvryItem2RcvngOrgUnitHolder.setRcvngOrgUnit(dlvryItem2Ou);
				deliveryItemHolder.getRecvngOus().add(prcmtDlvryItem2RcvngOrgUnitHolder);
			}
			
			if(StringUtils.isNotBlank(prcmtPOItem.getSection())){
				PrcmtDlvryItem2StrgSctn addDlvryItem2StrgSctn = dlvryItemEJB.addDlvryItem2StrgSctn(dlvryItem, prcmtPOItem.getSection(), null, dlvryItem.getQtyDlvrd());
				PrcmtDlvryItem2StrgSctnHolder prcmtDlvryItem2StrgSctnHolder = new PrcmtDlvryItem2StrgSctnHolder();
				prcmtDlvryItem2StrgSctnHolder.setStrgSctn(addDlvryItem2StrgSctn);
				deliveryItemHolder.getStrgSctns().add(prcmtDlvryItem2StrgSctnHolder);
			}
			
			deliveryHolder.getDeliveryItems().add(deliveryItemHolder);
			
			PrcmtOrderNbrHolder prcmtOrderNbrHolder = new PrcmtOrderNbrHolder();
			prcmtOrderNbrHolder.setProcmtOrderNbr(prcmtPOItem.getBsnsObjNbr());
			deliveryHolder.getProcmtOrderNbrs().add(prcmtOrderNbrHolder);
		}
		return deliveryHolder;
	}
	
}
