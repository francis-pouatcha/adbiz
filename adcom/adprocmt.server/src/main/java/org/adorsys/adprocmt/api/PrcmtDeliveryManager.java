package org.adorsys.adprocmt.api;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adcore.auth.TermWsUserPrincipal;
import org.adorsys.adcore.enums.CoreHistoryTypeEnum;
import org.adorsys.adcore.enums.CoreProcStepEnum;
import org.adorsys.adcore.enums.CoreProcessStatusEnum;
import org.adorsys.adcore.utils.BigDecimalUtils;
import org.adorsys.adprocmt.jpa.PrcmtAbstractDlvry2PO;
import org.adorsys.adprocmt.jpa.PrcmtDelivery;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryHstry;
import org.adorsys.adprocmt.jpa.PrcmtDlvry2Ou;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2Ou;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2POItem;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2StrgSctn;
import org.adorsys.adprocmt.rest.PrcmtDeliveryEJB;
import org.adorsys.adprocmt.rest.PrcmtDeliveryHstryEJB;
import org.adorsys.adprocmt.rest.PrcmtDlvryItemEJB;
import org.adorsys.adprocmt.rest.PrcmtDlvryItemLookup;
import org.adorsys.adstock.jpa.StkArticleLot2StrgSctn;
import org.adorsys.adstock.rest.StkArticleLot2StrgSctnLookup;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class PrcmtDeliveryManager {
	
	@Inject
	private PrcmtDeliveryEJB deliveryEJB;
	
	@Inject
	private PrcmtDlvryItemEJB dlvryItemEJB; 
	@Inject
	private PrcmtDlvryItemLookup dlvryItemLookup; 
	
	@Inject
	private SecurityUtil securityUtil;

	@Inject
	private PrcmtDeliveryHstryEJB deliveryHstryEJB;

	@Inject
	private StkArticleLot2StrgSctnLookup articleLot2StrgSctnLookup;
		
	
	/**
	 * 
	 * Process an incoming delivery. The delivery holds :
	 * 	- a partial list of delivery items holders,
	 * 	- a complete list of receiving organization units holders,
	 * 
	 * The
	 * @param deliveryHolder
	 * @return
	 */
	@Lock(LockType.WRITE)
	public PrcmtDeliveryHolder updateDelivery(PrcmtDeliveryHolder deliveryHolder){
		PrcmtDelivery delivery = deliveryHolder.getDelivery();
		String currentLoginName = securityUtil.getCurrentLoginName();
		Date now = new Date();
		
		// Create the delivery object if necessary
		delivery = createDeliveryObject(delivery, currentLoginName, now);
		deliveryHolder.setDelivery(delivery);
		boolean modified = false;
		
		modified |= synchProcmtOrderNbrs(deliveryHolder);
		
		modified |= synchRcvngOrgUnits(deliveryHolder);
		
		boolean itemModified = deleteHolders(deliveryHolder);
		
		List<PrcmtDeliveryItemHolder> deliveryItems = deliveryHolder.getDeliveryItems();
		if(deliveryItems==null) deliveryItems=new ArrayList<PrcmtDeliveryItemHolder>();
		
		for (PrcmtDeliveryItemHolder itemHolder : deliveryItems) {
			PrcmtDlvryItem dlvryItem = itemHolder.getDlvryItem();

			if(StringUtils.isBlank(dlvryItem.getBsnsObjNbr()))
				dlvryItem.setBsnsObjNbr(delivery.getDlvryNbr());
			// check presence of the article pic
			if(StringUtils.isBlank(dlvryItem.getArtPic()))
				throw new IllegalStateException("Missing article identification code.");

			if(StringUtils.isNotBlank(dlvryItem.getId())){
				// todo check mdified
				PrcmtDlvryItem persDi = dlvryItemLookup.findById(dlvryItem.getId());
				if(persDi==null) throw new IllegalStateException("Missing delivery item with id: " + dlvryItem.getId());
				if(!dlvryItem.contentEquals(persDi)){
					dlvryItem.copyTo(persDi);
					dlvryItem.evlte();
					dlvryItem = dlvryItemEJB.update(persDi);
					itemModified = true;
				}
			} else {
				if (StringUtils.isNotBlank(dlvryItem.getItemNbr())) {
					PrcmtDlvryItem persDi = dlvryItemLookup.findById(dlvryItem.getItemNbr());
					if(persDi!=null){
						if(!dlvryItem.contentEquals(persDi)){
							dlvryItem.copyTo(persDi);
							dlvryItem.evlte();
							dlvryItem = dlvryItemEJB.update(persDi);
							itemModified = true;
						}
					} else {
						dlvryItem.evlte();
						dlvryItem.setBsnsObjNbr(delivery.getDlvryNbr());
						dlvryItem = dlvryItemEJB.create(dlvryItem);
						itemModified = true;
					}
				} else {
					dlvryItem.evlte();
					dlvryItem = dlvryItemEJB.create(dlvryItem);
					itemModified = true;
				}
			}

			itemHolder.setDlvryItem(dlvryItem);
			itemModified |= processItems2PoItems(itemHolder);
			itemModified |= processItems2RecvngOus(itemHolder, deliveryHolder);
			itemModified |= processItem2StrgSctns(itemHolder);
		}
		
		if(itemModified){
			// Create or update the delivery.
			recomputeDelivery(delivery);
			//delivery.setDlvryStatus(BaseProcessStatusEnum.ONGOING.name());
			delivery = deliveryEJB.update(delivery);
			deliveryHolder.setDelivery(delivery);		
		}
		if(modified || itemModified){
			createModifiedDeliveryHistory(delivery);
		}
		return deliveryHolder;
	}
	
	private boolean deleteHolders(PrcmtDeliveryHolder deliveryHolder){
		List<PrcmtDeliveryItemHolder> deliveryItems = deliveryHolder.getDeliveryItems();
		List<PrcmtDeliveryItemHolder> diToRemove = new ArrayList<PrcmtDeliveryItemHolder>();
		boolean modified = false;
		for (PrcmtDeliveryItemHolder itemHolder : deliveryItems) {
			if(itemHolder.isDeleted()){
				PrcmtDlvryItem dlvryItem = itemHolder.getDlvryItem();
				String id = StringUtils.isNotBlank(dlvryItem.getId())?dlvryItem.getId():dlvryItem.getItemNbr();
				if(StringUtils.isNotBlank(id)){
					dlvryItemEJB.deleteById(dlvryItem.getId());
					modified = true;					
				}
				diToRemove.add(itemHolder);
			}
		}
		deliveryItems.removeAll(diToRemove);
		return modified;
	}

	private boolean processItem2StrgSctns(PrcmtDeliveryItemHolder itemHolder) {
		PrcmtDlvryItem dlvryItem = itemHolder.getDlvryItem();
		String dlvryItemNbr = dlvryItem.getItemNbr();
		boolean modified = false;
		List<PrcmtDlvryItem2StrgSctnHolder> strgSctns = itemHolder.getStrgSctns();
		List<PrcmtDlvryItem2StrgSctnHolder> itemsToRemove = new ArrayList<PrcmtDlvryItem2StrgSctnHolder>();
		for (PrcmtDlvryItem2StrgSctnHolder ouHolder : strgSctns) {
			PrcmtDlvryItem2StrgSctn strgSctn = ouHolder.getStrgSctn();
			PrcmtDlvryItem2StrgSctn persStrgSctn = dlvryItemLookup.findDlvryItem2StrgSctn(dlvryItemNbr, strgSctn.getStrgSection());
			if(ouHolder.isDeleted()){
				if(persStrgSctn!=null){
					dlvryItemEJB.deleteStrgSctn(dlvryItemNbr, strgSctn.getStrgSection());
					modified=true;
				}
				itemsToRemove.add(ouHolder);
				continue;
			}
			if(persStrgSctn==null){
				strgSctn = dlvryItemEJB.addDlvryItem2StrgSctn(dlvryItem, strgSctn.getStrgSection(), strgSctn.getStkQtyPreDlvry(), strgSctn.getQtyStrd());
				ouHolder.setStrgSctn(strgSctn);
				modified=true;
			} else {
				if(!strgSctn.contentEquals(persStrgSctn)){
					strgSctn = dlvryItemEJB.updateDlvryItem2StrgSctn(dlvryItem, strgSctn);
					ouHolder.setStrgSctn(strgSctn);
					modified=true;
				}
			}
		}
		// Initialize storage section if not defined.
		List<PrcmtDlvryItem2StrgSctn> item2StrgSctn = dlvryItemLookup.listDlvryItem2StrgSctn(dlvryItemNbr);
		if(item2StrgSctn.isEmpty()){
			List<StkArticleLot2StrgSctn> lot2StrgSctns = articleLot2StrgSctnLookup.findByArtPic(dlvryItem.getArtPic(), 0, 1);
			if(lot2StrgSctns.isEmpty())
				throw new IllegalStateException("Missing storage section for delivery item: " + dlvryItem.getId());
			StkArticleLot2StrgSctn lot2StrgSctn = lot2StrgSctns.iterator().next();
//			PrcmtDlvryItem2StrgSctn strgSctn = strgSctnHolder.getStrgSctn();
			// TODO fix it
			PrcmtDlvryItem2StrgSctn strgSctn = dlvryItemEJB.addDlvryItem2StrgSctn(dlvryItem, lot2StrgSctn.getStrgSection(), lot2StrgSctn.getSectionArticleLot().getLotQty(),dlvryItem.getQtyDlvrd());
			PrcmtDlvryItem2StrgSctnHolder strgSctnHolder = new PrcmtDlvryItem2StrgSctnHolder();
			strgSctnHolder.setStrgSctn(strgSctn);
			modified=true;
			strgSctns.add(strgSctnHolder);
		}
		strgSctns.removeAll(itemsToRemove);
		return modified;
	}

	private boolean processItems2RecvngOus(PrcmtDeliveryItemHolder itemHolder, PrcmtDeliveryHolder deliveryHolder) {
		PrcmtDlvryItem dlvryItem = itemHolder.getDlvryItem();
		String dlvryItemNbr = dlvryItem.getItemNbr();
		boolean modified = false;
		List<PrcmtDlvryItem2RcvngOrgUnitHolder> recvngOus = itemHolder.getRecvngOus();
		if(!recvngOus.isEmpty()) {
			List<PrcmtDlvryItem2RcvngOrgUnitHolder> itemsToRemove = new ArrayList<PrcmtDlvryItem2RcvngOrgUnitHolder>();
			for (PrcmtDlvryItem2RcvngOrgUnitHolder ouHolder : recvngOus) {
				PrcmtDlvryItem2Ou orgUnit = ouHolder.getRcvngOrgUnit();
				PrcmtDlvryItem2Ou persOrgUnit = dlvryItemLookup.findDlvryItem2Ou(dlvryItemNbr, orgUnit.getRcvngOrgUnit());
				if(ouHolder.isDeleted()){
					if(persOrgUnit!=null){
						dlvryItemEJB.deleteOu(dlvryItemNbr, orgUnit.getRcvngOrgUnit());
						modified=true;
					}
					itemsToRemove.add(ouHolder);
					continue;
				}
				if(persOrgUnit==null){
					orgUnit = dlvryItemEJB.addDlvryItem2Ou(dlvryItem, orgUnit.getRcvngOrgUnit(), orgUnit.getQtyDlvrd(), orgUnit.getFreeQty());
					ouHolder.setRcvngOrgUnit(orgUnit);
					modified=true;
				} else {
					if(!orgUnit.contentEquals(persOrgUnit)){
						orgUnit = dlvryItemEJB.updateDlvryItem2Ou(dlvryItem, orgUnit);
						ouHolder.setRcvngOrgUnit(orgUnit);
						modified=true;
					}
				}
			}
			recvngOus.removeAll(itemsToRemove);
		} else { // Share according to the proportions of the receiving orgunit.
			recvngOus = new ArrayList<PrcmtDlvryItem2RcvngOrgUnitHolder>();
			List<PrcmtDlvryRcvngOrgUnitHolder> rcvngOrgUnitHolders = deliveryHolder.getRcvngOrgUnits();
			for (PrcmtDlvryRcvngOrgUnitHolder rcvngOrgUnitHolder : rcvngOrgUnitHolders) {
				PrcmtDlvry2Ou rcvngOrgUnit = rcvngOrgUnitHolder.getRcvngOrgUnit();
				PrcmtDlvryItem2RcvngOrgUnitHolder itemOuHolder = new PrcmtDlvryItem2RcvngOrgUnitHolder();
				PrcmtDlvryItem2Ou dlvryItem2Ou = new PrcmtDlvryItem2Ou();
				itemOuHolder.setRcvngOrgUnit(dlvryItem2Ou);
				dlvryItem2Ou.setDlvryItemNbr(dlvryItemNbr);
				dlvryItem2Ou.setFreeQty(BigDecimalUtils.basePercentOfRatePct(rcvngOrgUnit.getQtyPct(), dlvryItem.getFreeQty(), RoundingMode.HALF_EVEN));
				dlvryItem2Ou.setQtyDlvrd(BigDecimalUtils.basePercentOfRatePct(rcvngOrgUnit.getQtyPct(), dlvryItem.getQtyDlvrd(), RoundingMode.HALF_EVEN));
				dlvryItem2Ou.setRcvngOrgUnit(rcvngOrgUnit.getRcvngOrgUnit());
				recvngOus.add(itemOuHolder);
			}
		}
		return modified;
	}

	private boolean processItems2PoItems(PrcmtDeliveryItemHolder itemHolder) {
		PrcmtDlvryItem dlvryItem = itemHolder.getDlvryItem();
		String dlvryItemNbr = dlvryItem.getItemNbr();
		boolean modified = false;
		List<PrcmtDlvryItem2PoItemHolder> poItems = itemHolder.getPoItems();
		List<PrcmtDlvryItem2PoItemHolder> itemsToRemove = new ArrayList<PrcmtDlvryItem2PoItemHolder>();
		for (PrcmtDlvryItem2PoItemHolder poItemHolder : poItems) {
			PrcmtDlvryItem2POItem poItem = poItemHolder.getPoItem();
			PrcmtDlvryItem2POItem persPOItem = dlvryItemLookup.findDlvryItem2POItem(dlvryItemNbr, poItem.getPoItemNbr());
			if(poItemHolder.isDeleted()){
				if(persPOItem!=null){
					dlvryItemEJB.deletePoItem(dlvryItemNbr, poItem.getPoItemNbr());
					modified=true;
				}
				itemsToRemove.add(poItemHolder);
				continue;
			}
			if(persPOItem==null){
				poItem = dlvryItemEJB.addDlvryItem2POItem(dlvryItem, poItem.getPoItemNbr(), poItem.getQtyOrdered(), poItem.getQtyDlvrd(), poItem.getFreeQty());
				poItemHolder.setPoItem(poItem);
				modified=true;
			} else {
				if(!poItem.contentEquals(persPOItem)){
					poItem = dlvryItemEJB.updateDlvryItem2POItem(dlvryItem, poItem);
					poItemHolder.setPoItem(poItem);
					modified=true;
				}
			}
		}
		poItems.removeAll(itemsToRemove);
		return modified;
	}

	private boolean synchRcvngOrgUnits(PrcmtDeliveryHolder deliveryHolder){
		PrcmtDelivery delivery = deliveryHolder.getDelivery();
		String dlvryNbr = delivery.getDlvryNbr();
		boolean modified = false;
		
		// Process associated org units.
		List<PrcmtDlvryRcvngOrgUnitHolder> rcvngOrgUnits = deliveryHolder.getRcvngOrgUnits();
		List<PrcmtDlvryRcvngOrgUnitHolder> orgUnitToRemove = new ArrayList<PrcmtDlvryRcvngOrgUnitHolder>();
		if(!rcvngOrgUnits.isEmpty()){
			for (PrcmtDlvryRcvngOrgUnitHolder ouHolder : rcvngOrgUnits) {
				PrcmtDlvry2Ou dlvry2Ou = deliveryEJB.findOrgUnit(dlvryNbr, ouHolder.getRcvngOrgUnit().getRcvngOrgUnit());
				if(ouHolder.isDeleted()){
					if(dlvry2Ou!=null){
						deliveryEJB.deleteOrgUnit(dlvry2Ou.getId());
						orgUnitToRemove.add(ouHolder);
						modified = true;
					}
					continue;
				}
				if(dlvry2Ou==null){
					dlvry2Ou = deliveryEJB.addOrgUnit(delivery, ouHolder.getRcvngOrgUnit().getRcvngOrgUnit(), ouHolder.getRcvngOrgUnit().getQtyPct());
					ouHolder.setRcvngOrgUnit(dlvry2Ou);
					modified = true;
				} else {
					PrcmtDlvry2Ou rcvngOrgUnit = ouHolder.getRcvngOrgUnit();
					if(!rcvngOrgUnit.contentEquals(dlvry2Ou)){
						rcvngOrgUnit.copyTo(dlvry2Ou);
						dlvry2Ou.setId(rcvngOrgUnit.getId());
						dlvry2Ou = deliveryEJB.updateOrgUnit(dlvry2Ou);
						ouHolder.setRcvngOrgUnit(dlvry2Ou);
						modified = true;
					}
				}
			}
		} else if(StringUtils.isNotBlank(deliveryHolder.getDelivery().getRcvngOrgUnit())){
			PrcmtDlvry2Ou foundOrgUnit = deliveryEJB.findOrgUnit(deliveryHolder.getDelivery().getDlvryNbr(), deliveryHolder.getDelivery().getRcvngOrgUnit());
			if(foundOrgUnit==null){
				PrcmtDlvry2Ou dlvry2Ou = deliveryEJB.addOrgUnit(deliveryHolder.getDelivery(), deliveryHolder.getDelivery().getRcvngOrgUnit(), new BigDecimal("100"));
				PrcmtDlvryRcvngOrgUnitHolder ouHolder =new PrcmtDlvryRcvngOrgUnitHolder();
				ouHolder.setRcvngOrgUnit(dlvry2Ou);
				rcvngOrgUnits.add(ouHolder);
			}
		}
		rcvngOrgUnits.removeAll(orgUnitToRemove);
		return modified;
	}
	
	private boolean synchProcmtOrderNbrs(PrcmtDeliveryHolder deliveryHolder){
		PrcmtDelivery delivery = deliveryHolder.getDelivery();
		String dlvryNbr = delivery.getDlvryNbr();
		boolean modified = false;
		// Process associated orders
		List<PrcmtOrderNbrHolder> procmtOrderNbrs = deliveryHolder.getProcmtOrderNbrs();
		List<PrcmtOrderNbrHolder> poToRemove = new ArrayList<PrcmtOrderNbrHolder>();
		for (PrcmtOrderNbrHolder poNbrHolder : procmtOrderNbrs) {
			PrcmtAbstractDlvry2PO dlvryPO = deliveryEJB.findProcOrder(dlvryNbr, poNbrHolder.getProcmtOrderNbr());
			if(poNbrHolder.isDeleted()){
				if(dlvryPO!=null){
					deliveryEJB.deleteProcOrder(dlvryPO.getId());
				}
				poToRemove.add(poNbrHolder);
				modified = true;
				continue;
			}
			if(dlvryPO==null){
				dlvryPO = deliveryEJB.addProcOrder(delivery, poNbrHolder.getProcmtOrderNbr());
				modified = true;
			}
		}
		procmtOrderNbrs.removeAll(poToRemove);
		
		return modified;
		
	}
	
	private PrcmtDelivery createDeliveryObject(PrcmtDelivery delivery, String currentLoginName, Date now){
		if(StringUtils.isBlank(delivery.getId())){
			delivery.setCreatingUsr(currentLoginName);
			delivery.setCreationDt(now);
			if(delivery.getDlvryDt()==null) delivery.setDlvryDt(now);
			delivery.setDlvryStatus(CoreProcessStatusEnum.ONGOING.name());
			delivery = deliveryEJB.create(delivery);
			createInitialDeliveryHistory(delivery);
		}else{
			//flush the delivery
			 delivery = deliveryEJB.findById(delivery.getId());
		}
		return delivery;
	}
	public PrcmtDeliveryHolder closeDelivery(PrcmtDeliveryHolder deliveryHolder){
		deliveryHolder = updateDelivery(deliveryHolder);
		PrcmtDelivery delivery = deliveryHolder.getDelivery();
		PrcmtDelivery deliveryPersi = deliveryEJB.findByIdentif(delivery.getIdentif());
		//recomputeDelivery(delivery);
		if(!StringUtils.equals(deliveryPersi.getDlvryStatus(), CoreProcessStatusEnum.CLOSING.name())
				&& !StringUtils.equals(deliveryPersi.getDlvryStatus(), CoreProcessStatusEnum.CLOSED.name())){
			deliveryPersi.setDlvryStatus(CoreProcessStatusEnum.CLOSING.name());
			deliveryPersi = deliveryEJB.update(deliveryPersi);	
			createClosingDeliveryHistory(deliveryPersi);// Status closing
		}	
		deliveryHolder.setDelivery(deliveryPersi);
		return deliveryHolder;
	}	
	
	public PrcmtDelivery closePrcmtDelivery(PrcmtDelivery delivery){	
		PrcmtDelivery deliveryPersi = deliveryEJB.findByIdentif(delivery.getIdentif());		
			deliveryPersi.setDlvryStatus(CoreProcessStatusEnum.CLOSING.name());
			deliveryPersi = deliveryEJB.update(deliveryPersi);	
			createClosingDeliveryHistory(deliveryPersi);// Status closing		
		return deliveryPersi;
	}	

	public void closeDelivery(PrcmtDelivery delivery){
		delivery = deliveryEJB.findById(delivery.getId());
		if(!StringUtils.equals(delivery.getDlvryStatus(), CoreProcessStatusEnum.CLOSING.name())) return;
		delivery.setDlvryStatus(CoreProcessStatusEnum.CLOSED.name());
		delivery = deliveryEJB.update(delivery);
		createClosedDeliveryHistory(delivery);// Status closed
	}	
	
	private void recomputeDelivery(final PrcmtDelivery delivery){
		// update delivery object.
		String dlvryNbr = delivery.getDlvryNbr();
		Long count = dlvryItemLookup.countByBsnsObjNbr(dlvryNbr);
		int start = 0;
		int max = 100;
		delivery.clearAmts();
		while(start<=count){
			List<PrcmtDlvryItem> list = dlvryItemLookup.findBBsnsObjNbr(dlvryNbr, start, max);
			start +=max;
			for (PrcmtDlvryItem prcmtDlvryItem : list) {
				delivery.addGrossPPPreTax(prcmtDlvryItem.getPrchGrossPrcPreTax());
				delivery.addRebate(prcmtDlvryItem.getPrchRebateAmt());
				delivery.addNetPPPreTax(prcmtDlvryItem.getPrchNetPrcPreTax());
				delivery.addVatAmount(prcmtDlvryItem.getPrchVatAmt());
				delivery.addNetPPTaxIncl(prcmtDlvryItem.getPrchNetPrcTaxIncl());
			}
		}
		delivery.evlte();
	}
	
	private void createClosingDeliveryHistory(PrcmtDelivery delivery){
		TermWsUserPrincipal callerPrincipal = securityUtil.getCallerPrincipal();
		PrcmtDeliveryHstry deliveryHstry = new PrcmtDeliveryHstry();
		deliveryHstry.setAddtnlInfo(DeliveryInfo.prinInfo(delivery));
		deliveryHstry.setComment(CoreHistoryTypeEnum.CLOSING.name());
		deliveryHstry.setEntIdentif(delivery.getId());
		deliveryHstry.setEntStatus(delivery.getDlvryStatus());
		deliveryHstry.setHstryDt(new Date());
		deliveryHstry.setHstryType(CoreHistoryTypeEnum.CLOSING.name());
		
		deliveryHstry.setOrignLogin(callerPrincipal.getName());
		deliveryHstry.setOrignWrkspc(callerPrincipal.getWorkspaceId());
		deliveryHstry.setProcStep(CoreProcStepEnum.CLOSING.name());
		deliveryHstry.makeHistoryId(true);
		deliveryHstryEJB.create(deliveryHstry);
	}

	private void createInitialDeliveryHistory(PrcmtDelivery delivery){
		TermWsUserPrincipal callerPrincipal = securityUtil.getCallerPrincipal();
		PrcmtDeliveryHstry deliveryHstry = new PrcmtDeliveryHstry();
		deliveryHstry.setComment(CoreHistoryTypeEnum.INITIATED.name());
		deliveryHstry.setAddtnlInfo(DeliveryInfo.prinInfo(delivery));
		deliveryHstry.setEntIdentif(delivery.getId());
		deliveryHstry.setEntStatus(delivery.getDlvryStatus());
		deliveryHstry.setHstryDt(new Date());
		deliveryHstry.setHstryType(CoreHistoryTypeEnum.INITIATED.name());
		
		deliveryHstry.setOrignLogin(callerPrincipal.getName());
		deliveryHstry.setOrignWrkspc(callerPrincipal.getWorkspaceId());
		deliveryHstry.setProcStep(CoreProcStepEnum.INITIATING.name());
		deliveryHstry.makeHistoryId(true);
		deliveryHstryEJB.create(deliveryHstry);
	}

	private void createModifiedDeliveryHistory(PrcmtDelivery delivery){
		TermWsUserPrincipal callerPrincipal = securityUtil.getCallerPrincipal();
		PrcmtDeliveryHstry deliveryHstry = new PrcmtDeliveryHstry();
		deliveryHstry.setComment(CoreHistoryTypeEnum.MODIFIED.name());
		deliveryHstry.setAddtnlInfo(DeliveryInfo.prinInfo(delivery));
		deliveryHstry.setEntIdentif(delivery.getId());
		deliveryHstry.setEntStatus(delivery.getDlvryStatus());
		deliveryHstry.setHstryDt(new Date());
		deliveryHstry.setHstryType(CoreHistoryTypeEnum.MODIFIED.name());
		
		deliveryHstry.setOrignLogin(callerPrincipal.getName());
		deliveryHstry.setOrignWrkspc(callerPrincipal.getWorkspaceId());
		deliveryHstry.setProcStep(CoreProcStepEnum.MODIFYING.name());
		deliveryHstry.makeHistoryId(false);
		deliveryHstryEJB.create(deliveryHstry);
	}
	
	private void createClosedDeliveryHistory(PrcmtDelivery delivery){
		TermWsUserPrincipal callerPrincipal = securityUtil.getCallerPrincipal();
		PrcmtDeliveryHstry deliveryHstry = new PrcmtDeliveryHstry();
		deliveryHstry.setAddtnlInfo(DeliveryInfo.prinInfo(delivery));
		deliveryHstry.setComment(CoreHistoryTypeEnum.CLOSED.name());
		deliveryHstry.setEntIdentif(delivery.getId());
		deliveryHstry.setEntStatus(delivery.getDlvryStatus());
		deliveryHstry.setHstryDt(new Date());
		deliveryHstry.setHstryType(CoreHistoryTypeEnum.CLOSED.name());
		
		deliveryHstry.setOrignLogin(callerPrincipal.getName());
		deliveryHstry.setOrignWrkspc(callerPrincipal.getWorkspaceId());
		deliveryHstry.setProcStep(CoreProcStepEnum.CLOSING.name());
		deliveryHstry.makeHistoryId(true);
		deliveryHstryEJB.create(deliveryHstry);
	}

	// FRANCIS: Interdit de faire des Fullscan dans cette application.
	
//	public PrcmtDeliveryHolder findDelivery(String id, int itemStart, int pageSize) {
//		
//		PrcmtDeliveryHolder prcmtDeliveryHolder = new PrcmtDeliveryHolder();
//		PrcmtDelivery delivery = deliveryEJB.findById(id);
//		prcmtDeliveryHolder.setDelivery(delivery);
//		List<PrcmtDlvry2Ou> listOrgUnits = deliveryEJB.listOrgUnits(delivery.getDlvryNbr());
//		for(PrcmtDlvry2Ou prcmtDlvry2Ou:listOrgUnits){
//			PrcmtDlvryRcvngOrgUnitHolder prcmtDlvryRcvngOrgUnitHolder = new PrcmtDlvryRcvngOrgUnitHolder();
//			prcmtDlvryRcvngOrgUnitHolder.setRcvngOrgUnit(prcmtDlvry2Ou);
//			prcmtDeliveryHolder.getRcvngOrgUnits().add(prcmtDlvryRcvngOrgUnitHolder);
//		}
//			
//		List<PrcmtDlvryItem> deliveryItems = dlvryItemEJB.findByHldrNbr(delivery.getDlvryNbr(), itemStart, pageSize);
//		for(PrcmtDlvryItem deliveryItem:deliveryItems){
//			PrcmtDeliveryItemHolder prcmtDeliveryItemHolder = new PrcmtDeliveryItemHolder();
//			prcmtDeliveryItemHolder.setDlvryItem(deliveryItem);
//			
//			List<PrcmtDlvryItem2Ou> listDlvryItem2Ou = dlvryItemEJB.listDlvryItem2Ou(deliveryItem.getHldrNbr());
//			for(PrcmtDlvryItem2Ou prcmtDlvryItem2Ou:listDlvryItem2Ou){		
//				PrcmtDlvryItem2RcvngOrgUnitHolder prcmtDlvryItem2RcvngOrgUnitHolder = new PrcmtDlvryItem2RcvngOrgUnitHolder();
//				prcmtDlvryItem2RcvngOrgUnitHolder.setRcvngOrgUnit(prcmtDlvryItem2Ou);
//				prcmtDeliveryItemHolder.getRecvngOus().add(prcmtDlvryItem2RcvngOrgUnitHolder);
//			}				
//			List<PrcmtDlvryItem2POItem> listDlvryItem2POItem = dlvryItemEJB.listDlvryItem2POItem(deliveryItem.getHldrNbr());
//			for(PrcmtDlvryItem2POItem prcmtDlvryItem2POItem:listDlvryItem2POItem){
//				PrcmtDlvryItem2PoItemHolder prcmtDlvryItem2PoItemHolder = new PrcmtDlvryItem2PoItemHolder();
//				prcmtDlvryItem2PoItemHolder.setPoItem(prcmtDlvryItem2POItem);
//				prcmtDeliveryItemHolder.getPoItems().add(prcmtDlvryItem2PoItemHolder);
//			}
//			List<PrcmtDlvryItem2StrgSctn> listDlvryItem2StrgSctn = dlvryItemEJB.listDlvryItem2StrgSctn(deliveryItem.getHldrNbr());
//			for(PrcmtDlvryItem2StrgSctn prcmtDlvryItem2StrgSctn:listDlvryItem2StrgSctn){
//				PrcmtDlvryItem2StrgSctnHolder prcmtDlvryItem2StrgSctnHolder = new PrcmtDlvryItem2StrgSctnHolder();
//				prcmtDlvryItem2StrgSctnHolder.setStrgSctn(prcmtDlvryItem2StrgSctn);
//				prcmtDeliveryItemHolder.getStrgSctns().add(prcmtDlvryItem2StrgSctnHolder);
//			}
//			
//			prcmtDeliveryHolder.getDeliveryItems().add(prcmtDeliveryItemHolder);
//		}
//			
//		return prcmtDeliveryHolder;
//	}
	
//	public List<PrcmtDlvryItem2StrgSctnHolder> lookupSection(PrcmtDlvryItem dlvryItem){
//		List<StkArticleLot2StrgSctn> sections = articleLot2StrgSctnLookup.findByArtPic(dlvryItem.getArtPic(), 0, 1);
//		if(sections.isEmpty()) return Collections.emptyList(); 
//
//		StkArticleLot2StrgSctn strgSctn = sections.iterator().next();
//		List<PrcmtDlvryItem2StrgSctnHolder> result = new ArrayList<PrcmtDlvryItem2StrgSctnHolder>();
//		PrcmtDlvryItem2StrgSctnHolder holder = new PrcmtDlvryItem2StrgSctnHolder();
//		PrcmtDlvryItem2StrgSctn item2StrgSctn = new PrcmtDlvryItem2StrgSctn();
//		item2StrgSctn.setStrgSection(strgSctn.getStrgSection());
//		item2StrgSctn.setQtyStrd(dlvryItem.getQtyDlvrd());
//		holder.setStrgSctn(item2StrgSctn);
//		result.add(holder);
//		return result;
//	}
}
