package org.adorsys.adstock.recptcls.cdr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.adorsys.adcshdwr.jpa.CdrDrctSales;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesEvt;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesEvtLease;
import org.adorsys.adcshdwr.jpa.CdrDsArtItem;
import org.adorsys.adcshdwr.rest.CdrDrctSalesEvtLeaseEJB;
import org.adorsys.adcshdwr.rest.CdrDrctSalesLookup;
import org.adorsys.adcshdwr.rest.CdrDsArtItemLookup;
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
public class StkDirectSalesEvtProcessor {

	@Inject
	private CdrDrctSalesLookup evtDataEJB;
	@Inject
	private CdrDsArtItemLookup itemEvtDataEJB;
	@Inject
	private StkDirectSalesItemEvtProcessor itemEvtProcessor;
	@Inject
	private CdrDrctSalesEvtLeaseEJB evtLeaseEJB;
	@Inject
	private StkDirectSalesItemHstryEJB itemHstryEJB;
	@Inject
	private StkDirectSalesEvtProcessorHelper evtProcessorHelper;
	
	@Asynchronous
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void process(CdrDrctSalesEvt evt) {

		if(!evtProcessorHelper.shallProcessEvtLease(evt)) return;

		// This identifies a run.
		String processId = UUID.randomUUID().toString();
		
		Date now = new Date();
		String leaseId = null;
		
		// 1. Check if there is a lease associated with this processor.
		List<CdrDrctSalesEvtLease> leases = evtLeaseEJB.findByEvtIdAndHandlerName(evt.getId(), getHandlerName());
		if(leases.isEmpty()){
			// create one
			CdrDrctSalesEvtLease lease = new CdrDrctSalesEvtLease();
			lease.setEvtId(evt.getId());
			lease.setEvtName(evt.getEvtName());
			lease.setHandlerName(getHandlerName());
			lease.setProcessOwner(processId);
			lease = evtLeaseEJB.create(lease);
			leaseId = lease.getId();
		} else {
			CdrDrctSalesEvtLease lease = leases.iterator().next();
			// look if lease expired.
			if(lease.expired(now)){
				leaseId = evtLeaseEJB.recover(processId, lease.getId());
			}
		}
		if(leaseId==null) return;
				
		String entIdentif = evt.getEntIdentif();
		CdrDrctSales cdrDrctSales = evtDataEJB.findById(entIdentif);
		if(cdrDrctSales==null) {
			evtProcessorHelper.closeEvtLease(processId, leaseId, evt);
			return;
		}
		
		String dsNbr = cdrDrctSales.getDsNbr();
		Long evtDataCount = itemEvtDataEJB.countByDsNbr(dsNbr);
		
		int start = 0;
		int max = 100;
		List<String> itemEventDataToProcess = new ArrayList<String>();
		while(start<=evtDataCount){
			List<CdrDsArtItem> list = itemEvtDataEJB.findBBsnsObjNbr(dsNbr, start, max);
			start +=max;
			for (CdrDsArtItem itemEvtData : list) {
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
		return StkDirectSalesEvtProcessor.class.getSimpleName();
	}
}
