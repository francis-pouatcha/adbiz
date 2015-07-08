package org.adorsys.adstock.recptcls.inv;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.ejb.AccessTimeout;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.adorsys.adcore.enums.CoreHistoryTypeEnum;
import org.adorsys.adinvtry.jpa.InvInvtryEvt;
import org.adorsys.adinvtry.rest.InvInvtryEvtEJB;

/**
 * Watch over inventory events and update the corresponding stock.
 * 
 * @author francis
 *
 */
@Singleton
public class StkInvtryListener {
	
	@Inject
	private InvInvtryEvtEJB evtEJB;
	
	@Inject
	private StkInvtryEvtProcessor evtProcessor; 

	@Schedule(minute = "*", hour="*", persistent=false, second="*/35")
	@AccessTimeout(unit=TimeUnit.MINUTES, value=10)
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void process() throws Exception {
		List<InvInvtryEvt> events = evtEJB.findByEvtName(CoreHistoryTypeEnum.POSTED.name());
		for (InvInvtryEvt invtryEvt : events) {
			evtProcessor.process(invtryEvt);
		}
	}
}
