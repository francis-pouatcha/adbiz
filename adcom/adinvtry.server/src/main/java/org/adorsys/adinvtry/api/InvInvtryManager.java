package org.adorsys.adinvtry.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adcore.auth.TermWsUserPrincipal;
import org.adorsys.adcore.enums.CoreHistoryTypeEnum;
import org.adorsys.adcore.enums.CoreProcStepEnum;
import org.adorsys.adcore.jpa.CoreAbstBsnsItemHeader;
import org.adorsys.adcore.utils.BigDecimalUtils;
import org.adorsys.adcore.utils.CalendarUtil;
import org.adorsys.adcore.utils.FormatedValidFrom;
import org.adorsys.adcore.vo.StringListHolder;
import org.adorsys.adinvtry.jpa.InvInvtry;
import org.adorsys.adinvtry.jpa.InvInvtryHstry;
import org.adorsys.adinvtry.jpa.InvInvtryItem;
import org.adorsys.adinvtry.jpa.InvInvtryStatus;
import org.adorsys.adinvtry.rest.InvInvtryEJB;
import org.adorsys.adinvtry.rest.InvInvtryHstryLookup;
import org.adorsys.adinvtry.rest.InvInvtryItemEJB;
import org.adorsys.adinvtry.rest.InvInvtryItemLookup;
import org.adorsys.adstock.jpa.StkArticleLot;
import org.adorsys.adstock.jpa.StkArticleLot2StrgSctn;
import org.adorsys.adstock.jpa.StkLotStockQty;
import org.adorsys.adstock.rest.StkArticleLot2StrgSctnLookup;
import org.adorsys.adstock.rest.StkArticleLotLookup;
import org.adorsys.adstock.rest.StkLotStockQtyLookup;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class InvInvtryManager {
	
	@Inject
	private InvInvtryEJB inventoryEJB;
	
	@Inject
	private InvInvtryItemEJB invInvtryItemEJB; 
	@Inject
	private InvInvtryItemLookup invInvtryItemLookup; 
	
	@Inject
	private SecurityUtil securityUtil;

	@Inject
	private InvInvtryHstryLookup invtryHstryEJB;

	@Inject
	private StkArticleLot2StrgSctnLookup sctnLookup;
	
	@Inject
	private StkArticleLotLookup articleLotLookup;
	
	public InvInvtry prepareInventory(InvInvtry invtry) {
		// Create the inventory object if necessary
		Date now = new Date();
		invtry.setInvtryStatus(InvInvtryStatus.INITIALIZING);
		return createInventoryObject(invtry,now);
	}
	
	/**
	 * Process an incoming inventory. The inventory holds :
	 * 	- a partial list of inventory
	 * 
	 * @param invtryHolder
	 * @return
	 */
	public InvInvtry updateInventory(InvInvtry invtry){
		if(invtry.getInvtryStatus()==InvInvtryStatus.INITIALIZING || invtry.getInvtryStatus()==InvInvtryStatus.POSTED)
			return invtry;// Not updatable.
		return inventoryEJB.update(invtry);
	}
	
	public InvInvtry closeInventory(InvInvtry invtry){
		
		if(invtry.getInvtryStatus()==InvInvtryStatus.CLOSED || invtry.getInvtryStatus()==InvInvtryStatus.POSTED)
			return invtry;// Not closable.
		
		invtry = inventoryEJB.findById(invtry.getId());
		Date conflictDt = invtry.getConflictDt();
		invInvtryItemEJB.validateBusinessObject(invtry.getInvtryNbr());
		// Conflict detected. Save and return
		if(conflictDt==null && invtry.getConflictDt()!=null) return inventoryEJB.update(invtry);
		
		// conflict still there. Just return
		if(invtry.getConflictDt()!=null)return invtry;
		
		InvInvtryItem accumulator = invInvtryItemEJB.recomputeBusinessObject(invtry.getInvtryNbr(), new InvInvtryItem());
		
		invtry.setGapPurchAmtHT(accumulator.getPrchGrossPrcPreTax());
		invtry.setGapSaleAmtHT(accumulator.getSlsGrossPrcPreTax());
		invtry.setInvtryStatus(InvInvtryStatus.CLOSED);
		invtry.setClosedDate(new Date());
		invtry = inventoryEJB.update(invtry);
		createClosedInventoryHistory(invtry);// Status closed
		return invtry;
	}	

	public InvInvtry validateInventory(InvInvtry invtry){
		
		if(invtry.getInvtryStatus()==InvInvtryStatus.CLOSED || invtry.getInvtryStatus()==InvInvtryStatus.POSTED)
			return invtry;// Not closable.
		
		invtry = inventoryEJB.findById(invtry.getId());
		Date conflictDt = invtry.getConflictDt();
		invInvtryItemEJB.validateBusinessObject(invtry.getInvtryNbr());

		if(conflictDt!=invtry.getConflictDt()) invtry = inventoryEJB.update(invtry);
		
		return inventoryEJB.findById(invtry.getId());
	}	
	
	public InvInvtry postInventory(InvInvtry invtry){
		if(invtry.getInvtryStatus()!=InvInvtryStatus.CLOSED)
			return invtry;// Not closable.

		Date conflictDt = invtry.getConflictDt();
		invInvtryItemEJB.validateBusinessObject(invtry.getInvtryNbr());
		// Conflict detected. Save and return
		if(conflictDt==null && invtry.getConflictDt()!=null) return inventoryEJB.update(invtry);
		
		// conflict still there. Just return
		if(invtry.getConflictDt()!=null)return invtry;
		InvInvtryItem accumulator = invInvtryItemEJB.recomputeBusinessObject(invtry.getInvtryNbr(), new InvInvtryItem());
		
		invtry.setGapPurchAmtHT(accumulator.getPrchGrossPrcPreTax());
		invtry.setGapSaleAmtHT(accumulator.getSlsGrossPrcPreTax());

		invtry.setInvtryStatus(InvInvtryStatus.POSTED);
		invtry.setPostedDate(new Date());
		invtry = inventoryEJB.update(invtry);
		createPostedInventoryHistory(invtry);// Status closed
		return invtry;
	}	

	@Inject
	private StkLotStockQtyLookup stockQtyLookup; 
	/**
	 * Add an inventory item. Conditions are:
	 * <ol>
	 * 	<li>The Inventory must not have been closed yet.</li>
	 * </ol>
	 * @param invtryItem
	 * @return
	 */
	public InvInvtryItem addItem(InvInvtryItem invtryItem) {

		InvInvtry invtry = inventoryEJB.findByIdentif(invtryItem.getBsnsObjNbr());
		if(invtry==null) 
			throw new IllegalArgumentException("Missing inventory object");
		if(invtryHstryEJB.isClosed(invtry.getIdentif()))
			throw new IllegalStateException("Inventory object closed");
		
		invtryItem.setAcsngUser(securityUtil.getCurrentLoginName());
		String identifier = InvInvtryItem.toIdentifier(invtryItem.getBsnsObjNbr(), invtryItem.getAcsngUser(), invtryItem.getLotPic(), invtryItem.getArtPic(), invtryItem.getSection());
		
		InvInvtryItem existing = invInvtryItemLookup.findByIdentif(identifier);
		if(existing!=null){
			invtryItem.setId(existing.getId());
			return updateItem(invtryItem);
		}

		StkArticleLot articleLot = articleLotLookup.findByIdentif(invtryItem.getLotPic());
		if(articleLot!=null){
			CoreAbstBsnsItemHeader cache = new CoreAbstBsnsItemHeader();
			Date expirDt = invtryItem.getExpirDt();
			cache.copyFrom(invtryItem);// backup haeder.
			articleLot.copyTo(invtryItem);// copy payload
			cache.copyTo(invtryItem);// Restore
			if(expirDt!=null) invtryItem.setExpirDt(expirDt);
		}
		
		if(invtryItem.getAsseccedQty()!=null){
			if(invtryItem.getAcsngDt()==null){
				invtryItem.setAcsngDt(new Date());
			}
			StkLotStockQty stockQty = stockQtyLookup.findLatestQty(invtryItem.getArtPic(), invtryItem.getLotPic(), invtryItem.getSection());
			if(stockQty!=null)
				invtryItem.setExpectedQty(stockQty.getStockQty());

			invtryItem.evlte();
		}
		
		StkArticleLot2StrgSctn lot2Section = sctnLookup.findByStrgSectionAndLotPicAndArtPic(invtryItem.getSection(), invtryItem.getLotPic(), invtryItem.getArtPic());
		if(lot2Section!=null){
			invtryItem.setArtName(lot2Section.getArtName());
		}
		InvInvtryItem created = invInvtryItemEJB.create(invtryItem);
		setConflicting(invtry, created);
		return created;
	}
	
	private void setConflicting(InvInvtry invtry, InvInvtryItem created){
		if(created.getConflictDt()!=null && invtry.getConflictDt()==null){
			invtry = inventoryEJB.findById(invtry.getId());
			if(invtry.getConflictDt()==null){
				invtry.setConflictDt(new Date());
				inventoryEJB.update(invtry);
			}
		}
	}
	
	/**
	 * Updates an inventory item. Conditions are:
	 * <ol>
	 * 	<li>The Inventory must not have been closed yet.</li>
	 * 	<li>The Inventory item has been created already. Is just being modified.</li>
	 * 	<li>The Inventory disabledDt must not have changed.</li>
	 * </ol>
	 * @param invtryItem
	 * @return
	 */
	public InvInvtryItem updateItem(InvInvtryItem invtryItem) {
		InvInvtry invtry = inventoryEJB.findByIdentif(invtryItem.getBsnsObjNbr());
		if(invtry==null) 
			throw new IllegalArgumentException("Missing inventory object");
		if(invtryHstryEJB.isClosed(invtry.getIdentif()))
			throw new IllegalStateException("Inventory object closed");
		InvInvtryItem existing = invInvtryItemLookup.findById(invtryItem.getId());
		if(existing==null)
			throw new IllegalStateException("Inventory Item inexistant");
		if(!CalendarUtil.isSameInstant(invtryItem.getDisabledDt(), existing.getDisabledDt()))
			throw new IllegalStateException("Use disableItem/enableItem to change the status of an inventory item.");

		String currentLoginName = securityUtil.getCurrentLoginName();

		boolean changed = false;
		if(!BigDecimalUtils.strictEquals(invtryItem.getAsseccedQty(), existing.getAsseccedQty())){

			if(invtryItem.getAcsngDt()==null){
				invtryItem.setAcsngDt(new Date());
			}
			StkLotStockQty stockQty = stockQtyLookup.findLatestQty(existing.getArtPic(), existing.getLotPic(), existing.getSection());
			if(stockQty!=null)
				existing.setExpectedQty(stockQty.getStockQty());
			
			existing.setAsseccedQty(invtryItem.getAsseccedQty());
			existing.setAcsngDt(invtryItem.getAcsngDt());
			existing.setAcsngUser(currentLoginName);
			existing.evlte();
			changed = true;
		}
		if(!CalendarUtil.isSameDay(invtryItem.getExpirDt(), existing.getExpirDt()) && invtryItem.getExpirDt()!=null){
			existing.setExpirDt(invtryItem.getExpirDt());
			changed = true;
		}
		if(changed) {
			existing = invInvtryItemEJB.update(existing);
			if(!StringUtils.equals(currentLoginName, invtryItem.getAcsngUser())){
				createQuantityModifiedItemHistory(invtry, existing);
			}
		}

		setConflicting(invtry, existing);
		
		return existing;
	}

	public void disableItem(InvInvtryItem invtryItem) {
		InvInvtry invtry = inventoryEJB.findByIdentif(invtryItem.getBsnsObjNbr());
		if(invtry==null) 
			throw new IllegalArgumentException("Missing inventory object");
		if(invtryHstryEJB.isClosed(invtry.getIdentif()))
			throw new IllegalStateException("Inventory object closed");
		InvInvtryItem existing = invInvtryItemLookup.findById(invtryItem.getId());
		if(existing==null)
			throw new IllegalStateException("Inventory Item inexistant");
		
		if(existing.getDisabledDt()==null){
			Date disabledDt = invtryItem.getDisabledDt()!=null?invtryItem.getDisabledDt():new Date();
			existing.setDisabledDt(disabledDt);
			existing = invInvtryItemEJB.update(existing);
			
			// Create history.
			createDisabledInventoryItemHistory(invtry, existing);

			setConflicting(invtry, existing);
		}
//		int count = invInvtryItemLookup.countByHldrNbrAndSalIndex(existing.getHldrNbr(), existing.getSalIndex()).intValue();
//		if(count>100) count =100;//TODO What if they a many of these. 
//		return invInvtryItemLookup.findByHldrNbrAndSalIndex(existing.getHldrNbr(), existing.getSalIndex(),0,count);
	}

	public void enableItem(InvInvtryItem invtryItem) {
		InvInvtry invtry = inventoryEJB.findByIdentif(invtryItem.getBsnsObjNbr());
		if(invtry==null) 
			throw new IllegalArgumentException("Missing inventory object");
		if(invtryHstryEJB.isClosed(invtry.getIdentif()))
			throw new IllegalStateException("Inventory object closed");
		InvInvtryItem existing = invInvtryItemLookup.findById(invtryItem.getId());
		if(existing==null)
			throw new IllegalStateException("Inventory Item inexistant");

		if(existing.getDisabledDt()!=null) {
			existing.setDisabledDt(null);
			existing = invInvtryItemEJB.update(existing);
			
			// Create history.
			createDisabledInventoryItemHistory(invtry, existing);

			setConflicting(invtry, existing);
		}
	}

	private InvInvtry createInventoryObject(InvInvtry inventory,Date now){
		String currentLoginName = securityUtil.getCurrentLoginName();
		if(StringUtils.isBlank(inventory.getId())){
			if(StringUtils.isBlank(inventory.getAcsngUser()))
				inventory.setAcsngUser(currentLoginName);
			if(inventory.getInvtryDt()==null)
				inventory.setInvtryDt(now);
			if(inventory.getInvtryStatus()==null)
				inventory.setInvtryStatus(InvInvtryStatus.ONGOING);
			if(StringUtils.isBlank(inventory.getSection()) && StringUtils.isBlank(inventory.getRangeStart()) && StringUtils.isBlank(inventory.getRangeEnd())){
				inventory.setPreparedDt(new Date());
			}
			inventory = inventoryEJB.create(inventory);
			createInitialInventoryHistory(inventory);
			
		}
		return inventory;
	}

	private void createClosedInventoryHistory(InvInvtry invtry){
		TermWsUserPrincipal callerPrincipal = securityUtil.getCallerPrincipal();
		InvInvtryHstry invtryHstry = new InvInvtryHstry();
		invtryHstry.setAddtnlInfo(InventoryInfo.prinInfo(invtry));
		invtryHstry.setComment(CoreHistoryTypeEnum.CLOSED.name());
		invtryHstry.setEntIdentif(invtry.getId());
		invtryHstry.setEntStatus(invtry.getInvtryStatus().name());
		invtryHstry.setHstryDt(new Date());
		invtryHstry.setHstryType(CoreHistoryTypeEnum.CLOSED.name());
		
		invtryHstry.setOrignLogin(callerPrincipal.getName());
		invtryHstry.setOrignWrkspc(callerPrincipal.getWorkspaceId());
		invtryHstry.setProcStep(CoreProcStepEnum.CLOSING.name());
		invtryHstryEJB.create(invtryHstry);
	}

	private void createPostedInventoryHistory(InvInvtry invtry){
		TermWsUserPrincipal callerPrincipal = securityUtil.getCallerPrincipal();
		InvInvtryHstry invtryHstry = new InvInvtryHstry();
		invtryHstry.setAddtnlInfo(InventoryInfo.prinInfo(invtry));
		invtryHstry.setComment(CoreHistoryTypeEnum.POSTED.name());
		invtryHstry.setEntIdentif(invtry.getId());
		invtryHstry.setEntStatus(invtry.getInvtryStatus().name());
		invtryHstry.setHstryDt(new Date());
		invtryHstry.setHstryType(CoreHistoryTypeEnum.POSTED.name());
		
		invtryHstry.setOrignLogin(callerPrincipal.getName());
		invtryHstry.setOrignWrkspc(callerPrincipal.getWorkspaceId());
		invtryHstry.setProcStep(CoreProcStepEnum.POSTING.name());
		invtryHstryEJB.create(invtryHstry);
	}
	
	private void createInitialInventoryHistory(InvInvtry invtry){
		TermWsUserPrincipal callerPrincipal = securityUtil.getCallerPrincipal();
		InvInvtryHstry invtryHstry = new InvInvtryHstry();
		invtryHstry.setComment(CoreHistoryTypeEnum.INITIATED.name());
		invtryHstry.setAddtnlInfo(InventoryInfo.prinInfo(invtry));
		invtryHstry.setEntIdentif(invtry.getId());
		invtryHstry.setEntStatus(invtry.getInvtryStatus().name());
		invtryHstry.setHstryDt(new Date());
		invtryHstry.setHstryType(CoreHistoryTypeEnum.INITIATED.name());
		
		invtryHstry.setOrignLogin(callerPrincipal.getName());
		invtryHstry.setOrignWrkspc(callerPrincipal.getWorkspaceId());
		invtryHstry.setProcStep(CoreProcStepEnum.INITIATING.name());
		invtryHstryEJB.create(invtryHstry);
	}

	private void createModifiedInventoryHistory(InvInvtry invtry){
		TermWsUserPrincipal callerPrincipal = securityUtil.getCallerPrincipal();
		InvInvtryHstry invtryHstry = new InvInvtryHstry();
		invtryHstry.setComment(CoreHistoryTypeEnum.MODIFIED.name());
		invtryHstry.setAddtnlInfo(InventoryInfo.prinInfo(invtry));
		invtryHstry.setEntIdentif(invtry.getId());
		invtryHstry.setEntStatus(invtry.getInvtryStatus().name());
		invtryHstry.setHstryDt(new Date());
		invtryHstry.setHstryType(CoreHistoryTypeEnum.MODIFIED.name());
		
		invtryHstry.setOrignLogin(callerPrincipal.getName());
		invtryHstry.setOrignWrkspc(callerPrincipal.getWorkspaceId());
		invtryHstry.setProcStep(CoreProcStepEnum.MODIFYING.name());
		invtryHstryEJB.create(invtryHstry);
	}

	private void createDisabledInventoryItemHistory(InvInvtry invtry, InvInvtryItem invtryItem){
		TermWsUserPrincipal callerPrincipal = securityUtil.getCallerPrincipal();
		InvInvtryHstry invtryHstry = new InvInvtryHstry();
		invtryHstry.setComment(CoreHistoryTypeEnum.ITEM_MODIFIED.name());
		invtryHstry.setAddtnlInfo(FormatedValidFrom.format(invtryItem.getDisabledDt()));
		invtryHstry.setEntIdentif(invtry.getIdentif());
		invtryHstry.setEntStatus(invtry.getInvtryStatus().name());
		invtryHstry.setHstryDt(new Date());
		invtryHstry.setHstryType(CoreHistoryTypeEnum.MODIFIED.name());
		
		invtryHstry.setOrignLogin(callerPrincipal.getName());
		invtryHstry.setOrignWrkspc(callerPrincipal.getWorkspaceId());
		invtryHstry.setProcStep(CoreProcStepEnum.MODIFYING.name());
		invtryHstryEJB.create(invtryHstry);
	}
	
	private void createQuantityModifiedItemHistory(InvInvtry invtry, InvInvtryItem invtryItem){
		TermWsUserPrincipal callerPrincipal = securityUtil.getCallerPrincipal();
		InvInvtryHstry invtryHstry = new InvInvtryHstry();
		invtryHstry.setComment(CoreHistoryTypeEnum.ITEM_MODIFIED.name());
		invtryHstry.setAddtnlInfo("" + invtryItem.getAsseccedQty());
		invtryHstry.setEntIdentif(invtry.getIdentif());
		invtryHstry.setEntStatus(invtry.getInvtryStatus().name());
		invtryHstry.setHstryDt(new Date());
		invtryHstry.setHstryType(CoreHistoryTypeEnum.MODIFIED.name());
		
		invtryHstry.setOrignLogin(callerPrincipal.getName());
		invtryHstry.setOrignWrkspc(callerPrincipal.getWorkspaceId());
		invtryHstry.setProcStep(CoreProcStepEnum.MODIFYING.name());
		invtryHstryEJB.create(invtryHstry);
	}

	/**
	 * Merge all listed inventories into a single inventory.
	 * 
	 * @param searchInput
	 * @return
	 */
	public StringListHolder merge(StringListHolder invtryNbrs) {
		List<String> list = invtryNbrs.getList();
		if(list.isEmpty() || list.size()==1) return invtryNbrs;
		String containerInvtryNbr = list.get(0);
		// Validate Inventry for merging.
		InvInvtry containerInvtry = inventoryEJB.findByIdentif(containerInvtryNbr);
		checkCandidateContainer(containerInvtry);
		for (String invtryNbr : list) {
			if(StringUtils.equals(invtryNbr,containerInvtryNbr)) continue;
			InvInvtry invtry = inventoryEJB.findByIdentif(invtryNbr);
			checkCandidateMerge(containerInvtry, invtry);
			if(invtry.getContainerId()==null){
				invtry.setContainerId(containerInvtry.getInvtryNbr());
				invtry.setInvtryStatus(InvInvtryStatus.MERGED);
				inventoryEJB.update(invtry);
			}
		}
		containerInvtry.setContainerId(containerInvtry.getContainerId());
		inventoryEJB.update(containerInvtry);
		return invtryNbrs;
	}
	
	private void checkCandidateContainer(InvInvtry containerInvtry){
		if(containerInvtry==null)
			throw new IllegalArgumentException("InvInvtry_candidateContainerNotFound_error");
		// COnditions
		if(containerInvtry.getContainerId()!=null || containerInvtry.getMergedDate()!=null)
			throw new IllegalArgumentException("InvInvtry_mergedCanNotContain_error");
		if(containerInvtry.getPostedDate()!=null || containerInvtry.getMergedDate()!=null)
			throw new IllegalArgumentException("InvInvtry_postedCanNotContain_error");
	}

	private void checkCandidateMerge(InvInvtry invtry, InvInvtry containerInvtry){
		if(invtry==null)
			throw new IllegalArgumentException("InvInvtry_candidateMergeNotFound_error");
		// Conditions
		if(invtry.getPostedDate()!=null)
			throw new IllegalArgumentException("InvInvtry_postedCanNotBeMerged_error");
		if(invtry.getContainerId()!=null && !StringUtils.equals(invtry.getContainerId(), containerInvtry.getInvtryNbr()))
			throw new IllegalArgumentException("InvInvtry_mergeCandAssignedToAnotherContainer_error");
		if(invtry.getMergedDate()!=null && !StringUtils.equals(invtry.getContainerId(), containerInvtry.getInvtryNbr()))
			throw new IllegalArgumentException("InvInvtry_mergeCandMergedIntoAnotherContainer_error");
	}
}
