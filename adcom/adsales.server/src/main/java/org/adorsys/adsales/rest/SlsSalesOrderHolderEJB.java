package org.adorsys.adsales.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adsales.jpa.SlsSOItem;
import org.adorsys.adsales.jpa.SlsSOPtnr;
import org.adorsys.adsales.jpa.SlsSalesOrder;
import org.adorsys.adsales.repo.SlsSOItemRepository;
import org.adorsys.adsales.repo.SlsSOPtnrRepository;

/**
 * @author Hsimo
 * class SlsSalesOrderHolderEJB for retrieving partners and items
 */
@Stateless
public class SlsSalesOrderHolderEJB {
	
	@Inject
	private SlsSOPtnrRepository slsSOPtnrRepository;
	
	@Inject
	private SlsSOItemRepository slsSOItemRepository;

    
   public SlsSalesOrder loadSlsSOItems(SlsSalesOrder salesOrder){
	   String soNbr = salesOrder.getSoNbr();
	   List<SlsSOItem> resultList = slsSOItemRepository.findBySoNbr(soNbr).getResultList();
	   salesOrder.setSlsSOItems(resultList);
	   return salesOrder;
   }
   
   public SlsSalesOrder loadSlsSOPtnrs(SlsSalesOrder salesOrder){
	   String soNbr = salesOrder.getSoNbr();
	   List<SlsSOPtnr> resultList = slsSOPtnrRepository.findBySoNbr(soNbr).getResultList();
	   salesOrder.setSlsSOPtnrs(resultList);
	   return salesOrder;
   }
   
   public List<SlsSalesOrder> reloadSlsSalesOrders(List<SlsSalesOrder> slsSalesOrders){
	   List<SlsSalesOrder> salesOrders= new ArrayList<SlsSalesOrder>();
	   for(SlsSalesOrder salesOrder: slsSalesOrders){
		 SlsSalesOrder loadSlsSOItems = loadSlsSOItems(salesOrder); 
		 SlsSalesOrder loadSlsSOPtnrs = loadSlsSOPtnrs(loadSlsSOItems);
		 salesOrders.add(loadSlsSOPtnrs);
	   }
	   return salesOrders;
   }

}
