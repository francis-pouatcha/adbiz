package org.adorsys.adcshdwr.payementevent;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.adorsys.adcore.exceptions.AdException;
/**
 * 
 * @author guymoyo
 *
 */
@Stateless
public class PaymentHandler {
	
	@Inject
	DirectSalePaymentImpl directSalePayment;
	@Inject
	IndirectSalePaymentImpl indirectSalePayment;
	
	public void directSalePayment(@Observes @DirectSale PaymentEvent event) throws AdException {
		
				directSalePayment.pay(event);
				
    }
	
	public void indirectSalePayment(@Observes @IndirectSale PaymentEvent event) throws AdException {
		indirectSalePayment.pay(event);
    }

}
