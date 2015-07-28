package org.adorsys.adsales.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adsales.jpa.SlsInvceItem;
import org.adorsys.adsales.jpa.SlsInvcePtnr;
import org.adorsys.adsales.jpa.SlsInvoice;
import org.adorsys.adsales.repo.SlsInvceItemRepository;
import org.adorsys.adsales.repo.SlsInvcePtnrRepository;

/**
 * @author Hsimo
 * Session Bean implementation class SlsInvoiceHolderEJB
 */
@Stateless
public class SlsInvoiceHolderEJB {

	@Inject
	SlsInvcePtnrRepository slsInvcePtnrRepository;
	
	@Inject
	SlsInvceItemRepository slsInvceItemRepository;
	
	public SlsInvoice loadSlsInvceItems(SlsInvoice slsInvoice){
		   String invceNbr = slsInvoice.getInvceNbr();
		   List<SlsInvceItem> resultList = slsInvceItemRepository.findByInvceNbr(invceNbr).getResultList();
		   slsInvoice.setSlsInvceItems(resultList);
		   return slsInvoice;
	   }
	
	
	 public SlsInvoice loadSlsInvcePtnrs(SlsInvoice slsInvoice){
		   String invceNbr = slsInvoice.getInvceNbr();
		   List<SlsInvcePtnr> resultList = slsInvcePtnrRepository.findByInvceNbr(invceNbr).getResultList();
		   slsInvoice.setSlsInvcePtnrs(resultList);
		   return slsInvoice;
	   }
	   
	   public List<SlsInvoice> reloadSlsInvoices(List<SlsInvoice> slsInvoices){
		   List<SlsInvoice> invoices= new ArrayList<SlsInvoice>();
		   for(SlsInvoice invoice: slsInvoices){
			   SlsInvoice loadSlsInvoiceItems = loadSlsInvceItems(invoice); 
			   SlsInvoice loadSlsInvoicePtnrs = loadSlsInvcePtnrs(loadSlsInvoiceItems);
			   invoices.add(loadSlsInvoicePtnrs);
		   }
		   return invoices;
	   }

}
