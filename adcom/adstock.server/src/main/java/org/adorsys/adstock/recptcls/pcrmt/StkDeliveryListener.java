package org.adorsys.adstock.recptcls.pcrmt;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.ejb.AccessTimeout;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.adorsys.adcore.enums.CoreHistoryTypeEnum;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryEvt;
import org.adorsys.adprocmt.rest.PrcmtDeliveryEvtEJB;

/**
 * Watch over delivery events and update the corresponding stock.
 * 
 * @author francis
 *
 */
@Singleton
public class StkDeliveryListener {
	
	@Inject
	private PrcmtDeliveryEvtEJB evtEJB;
	
	@Inject
	private StkDeliveryEvtProcessor evtProcessor; 

	@Schedule(minute = "*", hour="*", persistent=false, second="*/41")
	@AccessTimeout(unit=TimeUnit.MINUTES, value=10)
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void process() throws Exception {
		List<PrcmtDeliveryEvt> events = evtEJB.findByEvtName(CoreHistoryTypeEnum.CLOSED.name());
		for (PrcmtDeliveryEvt deliveryEvt : events) {
			evtProcessor.process(deliveryEvt);
		}
	}
}
