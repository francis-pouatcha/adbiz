package org.adorsys.adinvtry.api;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.ejb.AccessTimeout;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.adorsys.adinvtry.jpa.InvInvtry;
import org.adorsys.adinvtry.jpa.InvInvtryItem;
import org.adorsys.adinvtry.jpa.InvInvtryStatus;
import org.adorsys.adinvtry.rest.InvInvtryEJB;
import org.adorsys.adinvtry.rest.InvInvtryItemEJB;
import org.adorsys.adinvtry.rest.InvInvtryItemLookup;
import org.adorsys.adstock.jpa.StkArticleLot2StrgSctn;
import org.adorsys.adstock.rest.StkArticleLot2StrgSctnLookup;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

@Stateless
public class InvInvtryPrepareWkr {
	
	@Inject
	private InvInvtryEJB inventoryEJB;
	
	@Inject
	private InvInvtryItemEJB invInvtryItemEJB; 
	@Inject
	private InvInvtryItemLookup invInvtryItemLookup;

	@Inject
	private StkArticleLot2StrgSctnLookup sctnLookup;

	@Inject
	private InvInvtryMerger invInvtryMerger;

	@Inject
	private InvInvtryPrepareHelper prepareHelper;

	@Schedule(second="*/45", minute = "*/3", hour="*", persistent=false)
	@AccessTimeout(unit=TimeUnit.HOURS, value=2)
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void prepareInventories() {
		List<InvInvtry> invtrys = inventoryEJB.findPreparingInvtrys();
		for (InvInvtry inventory : invtrys) {
			boolean close = true;
			// Create object jobs.
			if(StringUtils.isNotBlank(inventory.getRangeStart()) || StringUtils.isNotBlank(inventory.getRangeEnd())){
				close = false;
				String rangeStart = inventory.getRangeStart();
				if(StringUtils.isBlank(rangeStart))rangeStart="a";
				String rangeEnd = inventory.getRangeEnd();
				if(StringUtils.isBlank(rangeEnd))rangeEnd="z";
				if(StringUtils.isNotBlank(inventory.getSection())){
					Long count = sctnLookup.countByStrgSectionAndArtNameRange(inventory.getSection(), rangeStart, rangeEnd);
					int max = 50;
					int first = 0;
					while(first<count){
						int firstResult = first;
						first+=count;
						List<StkArticleLot2StrgSctn> lot2Sections = sctnLookup.findByStrgSectionAndArtNameRange(inventory.getSection(), rangeStart, rangeEnd, firstResult, max);
						close |= prepareHelper.createInvntryItems(lot2Sections, inventory);
					}
				} else {
					Long count = sctnLookup.countByArtNameRange(rangeStart, rangeEnd);
					int max = 50;
					int first = 0;
					while(first<count){
						int firstResult = first;
						first+=count;
						List<StkArticleLot2StrgSctn> lot2Sections = sctnLookup.findByArtNameRange(rangeStart, rangeEnd, firstResult, max);
						close |= prepareHelper.createInvntryItems(lot2Sections, inventory);
					}
				}
			} else if (StringUtils.isNotBlank(inventory.getSection())){
				close = false;
				Long count = sctnLookup.countByStrgSection(inventory.getSection());
				int max = 50;
				int first = 0;
				while(first<count){
					int firstResult = first;
					first+=count;
					List<StkArticleLot2StrgSctn> lot2Sections = sctnLookup.findByStrgSectionSorted(inventory.getSection(), firstResult, max);
					close |= prepareHelper.createInvntryItems(lot2Sections, inventory);
				}
			}
			if(close){
				InvInvtry found = inventoryEJB.findById(inventory.getId());
				found.setPreparedDt(new Date());
				if(InvInvtryStatus.INITIALIZING==found.getInvtryStatus())
					found.setInvtryStatus(InvInvtryStatus.ONGOING);
				inventoryEJB.update(found);
			}
		}
	}
	
	@Schedule(second="*/47", minute = "*/7", hour="*", persistent=false)
	@AccessTimeout(unit=TimeUnit.HOURS, value=2)
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void mergeInventories() {
		List<InvInvtry> invtrys = inventoryEJB.findMergingInvtrys();
		for (InvInvtry inventory : invtrys) {
			Long itemCount = invInvtryItemLookup.countByBsnsObjNbr(inventory.getInvtryNbr());
			if(itemCount<=0L){
				invInvtryMerger.setMerged(inventory.getInvtryNbr());
				continue;
			} else {
				if(inventory.getInvtryStatus()!=InvInvtryStatus.MERGED){
					invInvtryMerger.setMerging(inventory.getInvtryNbr());
				}
			}
			int max = 50;
			int first = 0;
			while(first<itemCount){
				int firstResult = first;
				first+=max;
				List<InvInvtryItem> items = invInvtryItemLookup.findBBsnsObjNbr(inventory.getInvtryNbr(), firstResult, max);
				for (InvInvtryItem invtryItem : items) {
					invInvtryMerger.mergeTo(invtryItem.getIdentif(), inventory.getContainerId());
				}
			}
			itemCount = invInvtryItemLookup.countByBsnsObjNbr(inventory.getInvtryNbr());
			if(itemCount<=0L){
				invInvtryMerger.setMerged(inventory.getInvtryNbr());
			}
		}
	}

	@Schedule(second="*/49", minute = "*", hour="*", persistent=false)
	@AccessTimeout(unit=TimeUnit.HOURS, value=2)
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void deleteMergedInventories() {
		List<InvInvtry> invtrys = inventoryEJB.findMergedInvtrys();
		for (InvInvtry inventory : invtrys) {
			Long itemCount = invInvtryItemLookup.countByBsnsObjNbr(inventory.getInvtryNbr());
			if(itemCount>0L) continue;
			Date mergedDate = inventory.getMergedDate();
			if(mergedDate==null) return;
			// delete if merged 10 minutes ago.
			if(DateUtils.addMinutes(mergedDate, 10).before(new Date())){
				inventoryEJB.deleteById(inventory.getId());
			}
		}
	}
}
