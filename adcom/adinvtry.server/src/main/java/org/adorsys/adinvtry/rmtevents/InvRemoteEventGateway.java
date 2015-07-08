package org.adorsys.adinvtry.rmtevents;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.ejb.AccessTimeout;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.BaseBatchEvt;
import org.adorsys.adbase.rest.BaseBatchEvtEJB;
import org.adorsys.adinvtry.api.ModConstants;
import org.adorsys.adinvtry.event.InvInvtryPostedEvent;
import org.adorsys.adinvtry.jpa.InvInvtryEvt;
import org.adorsys.adinvtry.jpa.InvInvtryEvtLease;
import org.adorsys.adinvtry.jpa.InvInvtryHstry;
import org.adorsys.adinvtry.rest.InvInvtryEvtEJB;
import org.adorsys.adinvtry.rest.InvInvtryEvtLeaseEJB;
import org.apache.commons.lang3.time.DateUtils;

@Stateless
public class InvRemoteEventGateway {

	@Inject
	private InvInvtryEvtLeaseEJB evtLeaseEJB;

	@Inject
	private InvInvtryEvtEJB evtEJB;

	@Inject
	private BaseBatchEvtEJB batchEvtEJB;

	public void handleInvtryPostedEvent(
			@Observes @InvInvtryPostedEvent InvInvtryHstry invtryHstry) {
		// Move this operation to an event.
		String evtName = invtryHstry.getHstryType();
		InvInvtryEvt evt = new InvInvtryEvt();
		invtryHstry.copyTo(evt);
		evt.setEvtName(evtName);
		evt.setId(invtryHstry.getId());
		evtEJB.create(evt);
		
		BaseBatchEvt batchEvt = new BaseBatchEvt();
		invtryHstry.copyTo(batchEvt);
		batchEvt.setEvtName(evtName);
		batchEvt.setId(UUID.randomUUID().toString());
		batchEvt.setEvtModule(ModConstants.MODULE_NAME);
		batchEvt.setEvtKlass(InvInvtryEvt.class.getSimpleName());
		batchEvtEJB.create(batchEvt);
	}

	@Schedule(minute = "*", second="1/35" ,hour="*", persistent=false)
	@AccessTimeout(unit=TimeUnit.MINUTES, value=10)
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void cleanUpProcessedEventData() {
		List<InvInvtryEvt> listAll = evtEJB.listAll(0, 10);
		for (InvInvtryEvt evt : listAll) {
			Date now = new Date();
			Date hstryDt = evt.getHstryDt();
			
			// 1 hour wait time for listeners to process the event.
			if (now.before(DateUtils.addHours(hstryDt, 1)))
				return;
			
			List<InvInvtryEvtLease> leases = evtLeaseEJB.findByEvtId(evt.getId());
			for (InvInvtryEvtLease lease : leases) {
				if (!lease.expired(now))
					return;
			}
			// remove leases;
			leases = evtLeaseEJB.findByEvtId(evt.getId());
			for (InvInvtryEvtLease lease : leases) {
				evtLeaseEJB.deleteById(lease.getId());
			}

			// remove evt
			evtEJB.deleteById(evt.getId());
		}
	}
}
