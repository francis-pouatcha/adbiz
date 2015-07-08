package org.adorsys.adcshdwr.payementevent;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.exceptions.AdException;
import org.adorsys.adcshdwr.jpa.CdrCshDrawer;
import org.adorsys.adcshdwr.jpa.CdrCstmrVchr;
import org.adorsys.adcshdwr.jpa.CdrDsPymntItem;
import org.adorsys.adcshdwr.jpa.CdrPymnt;
import org.adorsys.adcshdwr.jpa.CdrPymntMode;
import org.adorsys.adcshdwr.rest.CdrCshDrawerEJB;
import org.adorsys.adcshdwr.rest.CdrCstmrVchrEJB;
import org.adorsys.adcshdwr.rest.CdrDsPymntItemEJB;
import org.adorsys.adcshdwr.rest.CdrPymntEJB;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author guymoyo
 *
 */
@Stateless
public class PymntValidator {
	
	@Inject
	private CdrCshDrawerEJB cdrCshDrawerEJB;
	@Inject
	private CdrPymntEJB cdrPymntEJB;
	@Inject
	private CdrCstmrVchrEJB cdrCstmrVchrEJB;
	@Inject
	private CdrDsPymntItemEJB cdrDsPymntItemEJB;
	
	public void check(PaymentEvent pymtEvt) throws AdException {
		
		CdrCshDrawer activeCshDrawer = cdrCshDrawerEJB.getActiveCshDrawer();
		if(activeCshDrawer == null) throw new AdException("No open cash drawer");
		
		if(pymtEvt == null) throw new AdException("No Payment");
		if(StringUtils.isBlank(pymtEvt.getSaleNbr())) throw new AdException("No Sale Number");
		if(pymtEvt.getAmt() == null) throw new AdException("No Amount to pay");
		if(pymtEvt.getRcvdAmt() == null) throw new AdException("No Recieved amount");
		if(BigDecimal.ZERO.compareTo(pymtEvt.getAmt()) == 1) throw new AdException("Amount Net to pay less than 0");
		if(BigDecimal.ZERO.compareTo(pymtEvt.getRcvdAmt()) == 1) throw new AdException("Received Amount less than 0");	
		if(pymtEvt.getAmt().compareTo(pymtEvt.getRcvdAmt()) == 1) throw new AdException("Received Amount less than Amount Net to Pay");
		
		List<CdrDsPymntItem> dsPymntItems = cdrDsPymntItemEJB.findByDsNbr(pymtEvt.getSaleNbr());
		if(!dsPymntItems.isEmpty()) throw new AdException("Existing payment for this sale");
			
		if(!StringUtils.isBlank(pymtEvt.getPymntNbr())){
			List<CdrPymnt> list = cdrPymntEJB.findByPymntNbr(pymtEvt.getPymntNbr());
			if(list.isEmpty()) throw new AdException("Incorrect Payment Number");
		}
		
		if(pymtEvt.getPymntMode() != null && pymtEvt.getPymntMode().equals(CdrPymntMode.VOUCHER)){
			if(StringUtils.isBlank(pymtEvt.getVchrNbr())) throw new AdException("No Voucher number");
			List<CdrCstmrVchr> listVcher = cdrCstmrVchrEJB.findByVchrNbr(pymtEvt.getVchrNbr());
			if(listVcher.isEmpty()) throw new AdException("No Voucher found with this number");
			CdrCstmrVchr cdrCstmrVchr = listVcher.get(0);
			if(pymtEvt.getAmt().compareTo(cdrCstmrVchr.getRestAmt()) == 1) throw new AdException("Voucher amount less than amount to pay");
		}
		
	}
}
