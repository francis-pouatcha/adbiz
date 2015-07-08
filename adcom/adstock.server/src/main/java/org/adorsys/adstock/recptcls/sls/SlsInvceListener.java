package org.adorsys.adstock.recptcls.sls;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.ejb.AccessTimeout;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.adorsys.adcore.enums.CoreHistoryTypeEnum;
import org.adorsys.adsales.jpa.SlsInvceEvt;
import org.adorsys.adsales.rest.SlsInvceEvtEJB;

/**
 * Watch over direct sales events and update the corresponding stock.
 * 
 * @author francis
 *
 */
@Singleton
public class SlsInvceListener {
	
	@Inject
	private SlsInvceEvtEJB evtEJB;
	
	@Inject
	private SlsInvceEvtProcessor evtProcessor; 

	@Schedule(minute = "*", hour="*", persistent=false, second="*/35")
	@AccessTimeout(unit=TimeUnit.MINUTES, value=10)
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void process() throws Exception {
		List<SlsInvceEvt> events = evtEJB.findByEvtName(CoreHistoryTypeEnum.DELIVERED.name());
		for (SlsInvceEvt evt : events) {
			evtProcessor.process(evt);
		}
	}
}
