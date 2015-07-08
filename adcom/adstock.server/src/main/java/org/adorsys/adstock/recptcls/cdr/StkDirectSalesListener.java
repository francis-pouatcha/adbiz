package org.adorsys.adstock.recptcls.cdr;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.ejb.AccessTimeout;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.adorsys.adcore.enums.CoreHistoryTypeEnum;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesEvt;
import org.adorsys.adcshdwr.rest.CdrDrctSalesEvtEJB;

/**
 * Watch over direct sales events and update the corresponding stock.
 * 
 * @author francis
 *
 */
@Singleton
public class StkDirectSalesListener {
	
	@Inject
	private CdrDrctSalesEvtEJB evtEJB;
	
	@Inject
	private StkDirectSalesEvtProcessor evtProcessor; 

	@Schedule(minute = "*", hour="*", persistent=false, second="*/35")
	@AccessTimeout(unit=TimeUnit.MINUTES, value=10)
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void process() throws Exception {
		List<CdrDrctSalesEvt> events = evtEJB.findByEvtName(CoreHistoryTypeEnum.CLOSED.name());
		for (CdrDrctSalesEvt evt : events) {
			evtProcessor.process(evt);
		}
	}
}
