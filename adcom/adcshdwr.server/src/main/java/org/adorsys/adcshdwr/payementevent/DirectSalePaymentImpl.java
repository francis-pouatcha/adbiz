package org.adorsys.adcshdwr.payementevent;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.exceptions.AdException;
import org.adorsys.adcshdwr.jpa.CdrCshDrawer;
import org.adorsys.adcshdwr.jpa.CdrDsPymntItem;
import org.adorsys.adcshdwr.rest.CdrCshDrawerEJB;
import org.adorsys.adcshdwr.rest.CdrDsPymntItemEJB;
/**
* 
* @author guymoyo
*
*/
@Stateless
public class DirectSalePaymentImpl {
	
	@Inject
	private CdrDsPymntItemEJB cdrDsPymntItemEJB;
	@Inject
	private CdrCshDrawerEJB cdrCshDrawerEJB;
	
	public void pay(PaymentEvent pymtEvt) throws AdException{
	
		CdrDsPymntItem dsPymntItem = new CdrDsPymntItem();
		dsPymntItem.setAmt(pymtEvt.getAmt());
		dsPymntItem.setRcvdAmt(pymtEvt.getRcvdAmt());
		dsPymntItem.setDsNbr(pymtEvt.getSaleNbr());
		dsPymntItem.setPymntMode(pymtEvt.getPymntMode());
		dsPymntItem.setPymntDocType("Direct_Sale_Payment");
		dsPymntItem.soustractDiffAmt();
		dsPymntItem = cdrDsPymntItemEJB.create(dsPymntItem);		
		//update cshDrawer
		CdrCshDrawer activeCshDrawer;
		activeCshDrawer = cdrCshDrawerEJB.getActiveCshDrawer();
		activeCshDrawer.AddTtlCash(dsPymntItem.getAmt());
		activeCshDrawer.evlte();
		cdrCshDrawerEJB.update(activeCshDrawer);	
	}

}
