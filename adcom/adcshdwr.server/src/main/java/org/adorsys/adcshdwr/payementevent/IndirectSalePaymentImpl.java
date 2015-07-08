package org.adorsys.adcshdwr.payementevent;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.exceptions.AdException;
import org.adorsys.adcshdwr.jpa.CdrCshDrawer;
import org.adorsys.adcshdwr.jpa.CdrCstmrVchr;
import org.adorsys.adcshdwr.jpa.CdrPymnt;
import org.adorsys.adcshdwr.jpa.CdrPymntItem;
import org.adorsys.adcshdwr.jpa.CdrPymntMode;
import org.adorsys.adcshdwr.rest.CdrCshDrawerEJB;
import org.adorsys.adcshdwr.rest.CdrCstmrVchrEJB;
import org.adorsys.adcshdwr.rest.CdrPymntEJB;
import org.adorsys.adcshdwr.rest.CdrPymntItemEJB;
import org.apache.commons.lang3.StringUtils;

/**
* 
* @author guymoyo
*
*/

@Stateless
public class IndirectSalePaymentImpl {

	@Inject
	private CdrPymntItemEJB cdrPymntItemEJB;
	@Inject
	private CdrCshDrawerEJB cdrCshDrawerEJB;
	@Inject
	private CdrPymntEJB cdrPymntEJB;
	@Inject
	private CdrCstmrVchrEJB cdrCstmrVchrEJB;
	
	public void pay(PaymentEvent pymtEvt) throws AdException{
		
		CdrCshDrawer activeCshDrawer = cdrCshDrawerEJB.getActiveCshDrawer();
		
		CdrPymnt cdrPymnt = new CdrPymnt();
		if(StringUtils.isBlank(pymtEvt.getPymntNbr())) {	
			cdrPymnt.setCdrNbr(activeCshDrawer.getCdrNbr());
			cdrPymnt.setInvNbr(pymtEvt.getSaleNbr());
		    cdrPymnt = cdrPymntEJB.create(cdrPymnt);
		}else{
			List<CdrPymnt> list = cdrPymntEJB.findByPymntNbr(pymtEvt.getPymntNbr());
			cdrPymnt = list.get(0);
		}
		
		if(pymtEvt.getPymntMode()==null) pymtEvt.setPymntMode(CdrPymntMode.CASH);
		if(pymtEvt.getPymntMode().equals(CdrPymntMode.VOUCHER)){
			List<CdrCstmrVchr> listVcher = cdrCstmrVchrEJB.findByVchrNbr(pymtEvt.getVchrNbr());
			CdrCstmrVchr cdrCstmrVchr = listVcher.get(0);
			cdrCstmrVchr.AddAmtUsed(pymtEvt.getAmt());
			cdrCstmrVchr.evlte();
			cdrCstmrVchrEJB.update(cdrCstmrVchr);
		}
		
		CdrPymntItem pymntItem = new CdrPymntItem();
		pymntItem.setAmt(pymtEvt.getAmt());
		pymntItem.setRcvdAmt(pymtEvt.getRcvdAmt());
		pymntItem.setPymntNbr(cdrPymnt.getPymntNbr());
		pymntItem.setPymntMode(pymtEvt.getPymntMode());
		pymntItem.setPymntDocType("InDirect_Sale_Payment");//todo, change with ennum
		pymntItem.evlte();
		pymntItem = cdrPymntItemEJB.create(pymntItem);	
		
		//update payment
		cdrPymnt.addAmnt(pymntItem.getAmt());
		cdrPymnt = cdrPymntEJB.update(cdrPymnt);	
		//update cshDrawer
		switch (pymtEvt.getPymntMode()) {
		case VOUCHER: activeCshDrawer.AddTtlVchrIn(pymntItem.getAmt());break;
		case CASH: activeCshDrawer.AddTtlCash(pymntItem.getAmt());break;
		case CHECK: activeCshDrawer.AddTtlCheck(pymntItem.getAmt());break;
		case CREDIT_CARD: activeCshDrawer.AddTtlCredCard(pymntItem.getAmt());break;
		}
		activeCshDrawer.evlte();
		cdrCshDrawerEJB.update(activeCshDrawer);
		
	}

}
