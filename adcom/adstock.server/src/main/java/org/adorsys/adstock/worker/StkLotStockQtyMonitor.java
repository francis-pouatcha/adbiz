package org.adorsys.adstock.worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Singleton;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.adorsys.adcore.event.EntityCreatedEvent;
import org.adorsys.adstock.jpa.StkLotStockQty;

/**
 * This is an in vm singleton. It gather info on entries. Check for
 * inconsistencies and triggers consolidation if necessary. 
 * 
 * @author francis
 *
 */
@Singleton
public class StkLotStockQtyMonitor {

	private Map<String, List<StkLotStockQty>> cache = new HashMap<String, List<StkLotStockQty>>();
	
	@Inject
	private StkStockQtyCtrlWkr wkr;
	
	public void handleNewLotStockQtyEvent(@Observes @EntityCreatedEvent StkLotStockQty lotStockQty){
		newEntry(lotStockQty);
	}
	
	private void newEntry(StkLotStockQty lotStockQty){
		String key = lotStockQty.artPicAndLotPicAndSection();
		List<StkLotStockQty> list = cache.get(key);
		if(list==null){
			synchronized (this) {
				list = cache.get(key);
				if(list==null){
					list = new ArrayList<StkLotStockQty>();
					cache.put(key, list);
				}
			}
		}
		list.add(lotStockQty);
		Integer seqNbr = lotStockQty.getSeqNbr();
		synchronized (this) {
			ArrayList<StkLotStockQty> arrayList = new ArrayList<StkLotStockQty>(list);
			for (StkLotStockQty s : arrayList) {
				if(s.getSeqNbr()<seqNbr)list.remove(s);
			}
		}		
		if(list.size()>2){
			consolidate(lotStockQty);
		}
	}

	private void consolidate(StkLotStockQty lotStockQty) {
		wkr.cleaup(lotStockQty);
	}
}
