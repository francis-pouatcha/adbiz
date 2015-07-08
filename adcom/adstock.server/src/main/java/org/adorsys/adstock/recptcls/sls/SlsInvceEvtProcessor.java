package org.adorsys.adstock.recptcls.sls;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.adorsys.adsales.jpa.SlsInvceEvt;
import org.adorsys.adsales.jpa.SlsInvceEvtLease;
import org.adorsys.adsales.jpa.SlsInvceItem;
import org.adorsys.adsales.jpa.SlsInvoice;
import org.adorsys.adsales.rest.SlsInvceEvtLeaseEJB;
import org.adorsys.adsales.rest.SlsInvceItemLookup;
import org.adorsys.adsales.rest.SlsInvoiceLookup;
import org.adorsys.adstock.jpa.StkDirectSalesItemHstry;
import org.adorsys.adstock.rest.StkDirectSalesItemHstryEJB;
import org.apache.commons.lang3.time.DateUtils;

/**
 * Check for the incoming of direct sales closed event and 
 * process corresponding inventory items.
 * 
 * @author francis
 *
 */
@Stateless
public class SlsInvceEvtProcessor {

	@Inject
	private SlsInvoiceLookup evtDataEJB;
	@Inject
	private SlsInvceItemLookup itemEvtDataEJB;
	@Inject
	private SlsInvceItemEvtProcessor itemEvtProcessor;
	@Inject
	private SlsInvceEvtLeaseEJB evtLeaseEJB;
	@Inject
	private StkDirectSalesItemHstryEJB itemHstryEJB;
	@Inject
	private SlsInvceEvtProcessorHelper evtProcessorHelper;
	
	@Asynchronous
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void process(SlsInvceEvt evt) {

		if(!evtProcessorHelper.shallProcessEvtLease(evt)) return;

		// This identifies a run.
		String processId = UUID.randomUUID().toString();
		
		Date now = new Date();
		String leaseId = null;
		
		// 1. Check if there is a lease associated with this processor.
		List<SlsInvceEvtLease> leases = evtLeaseEJB.findByEvtIdAndHandlerName(evt.getId(), getHandlerName());
		if(leases.isEmpty()){
			// create one
			SlsInvceEvtLease lease = new SlsInvceEvtLease();
			lease.setEvtId(evt.getId());
			lease.setEvtName(evt.getEvtName());
			lease.setHandlerName(getHandlerName());
			lease.setProcessOwner(processId);
			lease = evtLeaseEJB.create(lease);
			leaseId = lease.getId();
		} else {
			SlsInvceEvtLease lease = leases.iterator().next();
			// look if lease expired.
			if(lease.expired(now)){
				leaseId = evtLeaseEJB.recover(processId, lease.getId());
			}
		}
		if(leaseId==null) return;
				
		String entIdentif = evt.getEntIdentif();
		SlsInvoice invoice = evtDataEJB.findById(entIdentif);
		if(invoice==null) {
			evtProcessorHelper.closeEvtLease(processId, leaseId, evt);
			return;
		}
		
		String invceNbr = invoice.getInvceNbr();
		Long evtDataCount = itemEvtDataEJB.countByInvceNbr(invceNbr);
		
		int start = 0;
		int max = 100;
		List<String> itemEventDataToProcess = new ArrayList<String>();
		while(start<=evtDataCount){
			List<SlsInvceItem> list = itemEvtDataEJB.findByInvceNbr(invceNbr, start, max);
			start +=max;
			for (SlsInvceItem itemEvtData : list) {
				StkDirectSalesItemHstry itemEvt = itemHstryEJB.findById(itemEvtData.getIdentif());
				if(itemEvt!=null) continue;// processed.
				itemEventDataToProcess.add(itemEvtData.getId());
			}
		}
		if(itemEventDataToProcess.isEmpty()) {
			evtProcessorHelper.closeEvtLease(processId, leaseId, evt);
			return;
		}
		Date time = new Date();
		for (String itemEvtDataId : itemEventDataToProcess) {
			itemEvtProcessor.process(itemEvtDataId, evt);
			if(DateUtils.addMinutes(new Date(), 1).before(time)){
				evtLeaseEJB.recover(processId, leaseId);
			}
		}
	}

	private String getHandlerName(){
		return SlsInvceEvtProcessor.class.getSimpleName();
	}
}
