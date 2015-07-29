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

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adinvtry.jpa.InvInvtry;
import org.adorsys.adinvtry.rest.InvInvtryEJB;
import org.adorsys.adinvtry.rest.InvInvtryItemEJB;
import org.adorsys.adinvtry.rest.InvInvtryItemLookup;
import org.adorsys.adinvtry.rest.InvInvtryLookup;
import org.adorsys.adinvtry.rest.InvInvtrySearchInput;
import org.adorsys.adstock.jpa.StkArticleLot2StrgSctn;
import org.adorsys.adstock.rest.StkArticleLot2StrgSctnLookup;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class InvInvtryPrepareWkr {
	
	@Inject
	private InvInvtryEJB inventoryEJB;
	@Inject
	private InvInvtryLookup lookup;
	@Inject
	private StkArticleLot2StrgSctnLookup sctnLookup;

	@Inject
	private InvInvtryPrepareHelper prepareHelper;

	@Schedule(second="*/45", minute = "*/3", hour="*", persistent=false)
	@AccessTimeout(unit=TimeUnit.HOURS, value=2)
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void prepareInventories() {
		Long count = lookup.countByPreparedDtIsNull();
		int start = 0;
		int max = 100;
		while(start<count){
			int firstResult = start;
			start+=max;
			List<InvInvtry> invtrys = lookup.findByPreparedDtIsNull(firstResult, max);
			
			for (InvInvtry inventory : invtrys) {
				boolean close = true;
				// Create object jobs.
				if(StringUtils.isNotBlank(inventory.getRangeStart()) || StringUtils.isNotBlank(inventory.getRangeEnd())){
					close = false;
					String rangeStart = inventory.getRangeStart();
					if(StringUtils.isBlank(rangeStart))rangeStart="a";
					String rangeEnd = inventory.getRangeEnd();
					if(StringUtils.isBlank(rangeEnd))rangeEnd="z";
					
					InvInvtrySearchInput searchInput = new InvInvtrySearchInput();
					InvInvtry invInvtry = new InvInvtry();
					invInvtry.setRangeEnd(rangeEnd);
					invInvtry.setRangeStart(rangeStart);
					if(StringUtils.isNotBlank(inventory.getSection()))
						invInvtry.setSection(inventory.getSection());
					searchInput.setEntity(invInvtry);
					
					Long itemCount = lookup.countCustom(searchInput);
					lookup.countBySectionAndArtNameRange();
					if(StringUtils.isNotBlank(inventory.getSection())){
						Long count = sctnLookup.countBySectionAndArtNameRange(inventory.getSection(), rangeStart, rangeEnd);
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
	}

}
