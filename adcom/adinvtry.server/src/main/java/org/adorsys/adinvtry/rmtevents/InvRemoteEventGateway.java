package org.adorsys.adinvtry.rmtevents;

import java.util.UUID;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.adorsys.adinvtry.api.ModConstants;
import org.adorsys.adinvtry.jpa.InvInvtryHstry;

@Stateless
public class InvRemoteEventGateway {

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
}
