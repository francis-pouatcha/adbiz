package org.adorsys.adsales.rmtevents;

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
import org.adorsys.adsales.api.ModConstants;
import org.adorsys.adsales.event.SlsInvceDeliveredEvent;
import org.adorsys.adsales.jpa.SlsInvceEvt;
import org.adorsys.adsales.jpa.SlsInvceEvtLease;
import org.adorsys.adsales.jpa.SlsInvceHistory;
import org.adorsys.adsales.rest.SlsInvceEvtEJB;
import org.adorsys.adsales.rest.SlsInvceEvtLeaseEJB;
import org.apache.commons.lang3.time.DateUtils;

@Stateless
public class SlsRemoteEventGateway {

	@Inject
	private SlsInvceEvtEJB evtEJB;

	@Inject
	private BaseBatchEvtEJB batchEvtEJB;
	
	@Inject
	private SlsInvceEvtLeaseEJB evtLeaseEJB;

	public void handleSlsInvceDeliveredEvent(
			@Observes @SlsInvceDeliveredEvent SlsInvceHistory invceHistory) {

		String evtName = invceHistory.getHstryType();
		SlsInvceEvt evt = new SlsInvceEvt();
		invceHistory.copyTo(evt);
		evt.setEvtName(evtName);
		evt.setId(invceHistory.getId());
		evtEJB.create(evt);
		
		BaseBatchEvt batchEvt = new BaseBatchEvt();
		invceHistory.copyTo(batchEvt);
		batchEvt.setEvtName(evtName);
		batchEvt.setId(UUID.randomUUID().toString());
		batchEvt.setEvtModule(ModConstants.MODULE_NAME);
		batchEvt.setEvtKlass(SlsInvceEvt.class.getSimpleName());
		batchEvtEJB.create(batchEvt);
	}


	@Schedule(minute = "*", second="1/35" ,hour="*", persistent=false)
	@AccessTimeout(unit=TimeUnit.MINUTES, value=10)
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void cleanUpProcessedEvent() {
		List<SlsInvceEvt> listAll = evtEJB.listAll(0, 10);
		for (SlsInvceEvt evt : listAll) {
			Date now = new Date();
			Date hstryDt = evt.getHstryDt();
			
			// 1 hour wait time for listeners to process the event.
			if (now.before(DateUtils.addHours(hstryDt, 1)))
				return;
			
			List<SlsInvceEvtLease> leases = evtLeaseEJB.findByEvtId(evt.getId());
			for (SlsInvceEvtLease lease : leases) {
				if (!lease.expired(now))
					return;
			}
			// remove leases;
			leases = evtLeaseEJB.findByEvtId(evt.getId());
			for (SlsInvceEvtLease lease : leases) {
				evtLeaseEJB.deleteById(lease.getId());
			}

			// remove evt
			evtEJB.deleteById(evt.getId());
		}
	}
}
