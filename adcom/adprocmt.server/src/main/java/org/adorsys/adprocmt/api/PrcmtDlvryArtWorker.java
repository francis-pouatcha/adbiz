package org.adorsys.adprocmt.api;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.ejb.AccessTimeout;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.adorsys.adprocmt.jpa.PrcmtDelivery;
import org.adorsys.adprocmt.rest.PrcmtDeliveryEJB;

@Stateless
public class PrcmtDlvryArtWorker {

	@Inject
	private PrcmtDlvryArtWorkerAuth workerAuth;
	
	@Inject
	private PrcmtDeliveryEJB deliveryEJB;

	@Schedule(minute = "*", second="1/35" ,hour="*", persistent=false)
	@AccessTimeout(unit=TimeUnit.MINUTES, value=10)
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void process() throws Exception {
		List<String> closingDeliveryIds = deliveryEJB.findClosingDeliveries(10);
		for (String id : closingDeliveryIds) {
			PrcmtDelivery delivery = deliveryEJB.findById(id);
			workerAuth.processAsSystem(delivery);
		}
	}
	
	/**
	 * Handles the delivery after the transaction is completed.
	 *  
	 * @param hisHstry
	public void handleDeliveryClosingEvent(@Observes(during=TransactionPhase.AFTER_COMPLETION) 
		@PrcmtDeliveryClosingEvent PrcmtDeliveryHstry hisHstry)
	{
		String entIdentif = hisHstry.getEntIdentif();
		PrcmtDelivery delivery = deliveryEJB.findByIdentif(entIdentif);
		workerAuth.processAsUser(delivery);
	}
	 */
}
