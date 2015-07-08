package org.adorsys.adstock.recptcls.inv;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.adorsys.adinvtry.jpa.InvInvtry;
import org.adorsys.adinvtry.jpa.InvInvtryEvt;
import org.adorsys.adinvtry.jpa.InvInvtryEvtLease;
import org.adorsys.adinvtry.jpa.InvInvtryItem;
import org.adorsys.adinvtry.repo.InvInvtryItemRepository;
import org.adorsys.adinvtry.repo.InvInvtryRepository;
import org.adorsys.adinvtry.rest.InvInvtryEvtLeaseEJB;
import org.adorsys.adstock.jpa.StkInvtryItemHstry;
import org.adorsys.adstock.rest.StkInvtryItemHstryEJB;
import org.apache.commons.lang3.time.DateUtils;

/**
 * Check for the incoming of inventory closed event and 
 * process corresponding inventory items.
 * 
 * @author francis
 *
 */
@Stateless
public class StkInvtryEvtProcessor {

	@Inject
	private InvInvtryRepository invInvtryRepository;
	@Inject
	private InvInvtryItemRepository invInvtryItemRepository;
	@Inject
	private StkInvtryItemEvtProcessor itemEvtProcessor;
	@Inject
	private InvInvtryEvtLeaseEJB evtLeaseEJB;
	@Inject
	private StkInvtryItemHstryEJB invtryItemHstryEJB;
	@Inject
	private StkInvtryEvtProcessorHelper evtProcessorHelper;
	
	@Asynchronous
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void process(InvInvtryEvt invtryEvt) {

		if(!evtProcessorHelper.shallProcessEvtLease(invtryEvt)) return;

		// This identifies a run.
		String processId = UUID.randomUUID().toString();
		
		Date now = new Date();
		String leaseId = null;
		
		// 1. Check if there is a lease associated with this processor.
		List<InvInvtryEvtLease> leases = evtLeaseEJB.findByEvtIdAndHandlerName(invtryEvt.getId(), getHandlerName());
		if(leases.isEmpty()){
			// create one
			InvInvtryEvtLease lease = new InvInvtryEvtLease();
			lease.setEvtId(invtryEvt.getId());
			lease.setEvtName(invtryEvt.getEvtName());
			lease.setHandlerName(getHandlerName());
			lease.setProcessOwner(processId);
			lease = evtLeaseEJB.create(lease);
			leaseId = lease.getId();
		} else {
			InvInvtryEvtLease lease = leases.iterator().next();
			// look if lease expired.
			if(lease.expired(now)){
				leaseId = evtLeaseEJB.recover(processId, lease.getId());
			}
		}
		if(leaseId==null) return;
				
		String entIdentif = invtryEvt.getEntIdentif();
		InvInvtry invtryEvtData = invInvtryRepository.findBy(entIdentif);
		if(invtryEvtData==null) {
			evtProcessorHelper.closeEvtLease(processId, leaseId, invtryEvt);
			return;
		}
		
		
		String invtryNbr = invtryEvtData.getInvtryNbr();
		Long evtDataCount = invInvtryItemRepository.countByBsnsObjNbr(invtryNbr);
		
		int start = 0;
		int max = 100;
		List<String> itemEventDataToProcess = new ArrayList<String>();
		while(start<=evtDataCount){
			List<InvInvtryItem> list = invInvtryItemRepository.findBBsnsObjNbr(invtryNbr, start, max);
			start +=max;
			for (InvInvtryItem itemEvtData : list) {
				StkInvtryItemHstry invtryItemEvt = invtryItemHstryEJB.findById(itemEvtData.getIdentif());
				if(invtryItemEvt!=null) continue;// processed.
				itemEventDataToProcess.add(itemEvtData.getId());
			}
		}
		if(itemEventDataToProcess.isEmpty()) {
			evtProcessorHelper.closeEvtLease(processId, leaseId, invtryEvt);
			return;
		}
		Date time = new Date();
		for (String itemEvtDataId : itemEventDataToProcess) {
			itemEvtProcessor.process(itemEvtDataId, invtryEvt);
			if(DateUtils.addMinutes(new Date(), 1).before(time)){
				evtLeaseEJB.recover(processId, leaseId);
			}
		}
	}

	private String getHandlerName(){
		return StkInvtryEvtProcessor.class.getSimpleName();
	}
}
