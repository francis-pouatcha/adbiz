/**
 * 
 */
package org.adorsys.adcshdwr.api;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.adorsys.adcore.exceptions.AdException;
import org.adorsys.adcshdwr.jpa.CdrPymnt;
import org.adorsys.adcshdwr.jpa.CdrPymntItem;
import org.adorsys.adcshdwr.payementevent.IndirectSale;
import org.adorsys.adcshdwr.payementevent.PaymentEvent;
import org.adorsys.adcshdwr.payementevent.PymntValidator;
import org.adorsys.adcshdwr.rest.CdrPymntEJB;
import org.adorsys.adcshdwr.rest.CdrPymntItemEJB;

/**
 * @author guymoyo
 *
 */
@Stateless
public class CdrPymntManager {

	
	@Inject
    @IndirectSale
    private Event<PaymentEvent> indirectSaleEvent;
	@Inject
	private CdrPymntEJB cdrPymntEJB;
	@Inject
	private CdrPymntItemEJB pymntItemEJB;
	@Inject
	private PymntValidator pymntValidator;
	

	public CdrPymntHolder savePymt(CdrPymntHolder cdrPymntHolder) throws AdException {
		if(cdrPymntHolder.getRcvdAmt() == null || BigDecimal.ZERO.compareTo(cdrPymntHolder.getRcvdAmt()) == 1)
			cdrPymntHolder.setRcvdAmt(cdrPymntHolder.getAmt());
		
		PaymentEvent paymentEvent = new PaymentEvent(cdrPymntHolder.getPymntMode(), cdrPymntHolder.getAmt(), cdrPymntHolder.getRcvdAmt(),
				new Date(), cdrPymntHolder.getInvceNbr(), cdrPymntHolder.getVchrNbr(), cdrPymntHolder.getPymntNbr());	
			pymntValidator.check(paymentEvent);
			indirectSaleEvent.fire(paymentEvent);
		
			return invPymt(cdrPymntHolder.getInvceNbr());
	}


	public CdrPymntHolder invPymt(String invNbr) {
		CdrPymntHolder cdrPymntHolder = new CdrPymntHolder();
		cdrPymntHolder.setInvceNbr(invNbr);
		List<CdrPymnt> listCdrPymnt = cdrPymntEJB.findByInvNbr(invNbr);
		if(listCdrPymnt.isEmpty()) return cdrPymntHolder;
		
		CdrPymnt cdrPymnt = listCdrPymnt.get(0);
		cdrPymntHolder.setCdrPymnt(cdrPymnt);
		String pymntNbr = cdrPymnt.getPymntNbr();
		cdrPymntHolder.setPymntNbr(pymntNbr);
		List<CdrPymntItem> lsitItem = pymntItemEJB.findByPymntNbr(pymntNbr);
		cdrPymntHolder.getCdrPymntItems().clear();
		cdrPymntHolder.getCdrPymntItems().addAll(lsitItem);
		return cdrPymntHolder;
	}
}
