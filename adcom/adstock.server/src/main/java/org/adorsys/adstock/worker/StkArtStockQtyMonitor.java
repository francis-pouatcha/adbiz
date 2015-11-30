package org.adorsys.adstock.worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Singleton;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.adorsys.adcore.event.EntityCreatedEvent;
import org.adorsys.adstock.jpa.StkArtStockQty;

/**
 * This is an in vm singleton. It gather info on entries. Check for
 * inconsistencies and triggers consolidation if necessary. 
 * 
 * @author francis
 *
 */
@Singleton
public class StkArtStockQtyMonitor {

	private Map<String, List<StkArtStockQty>> cache = new HashMap<String, List<StkArtStockQty>>();
	
	@Inject
	private StkArtStockQtyCtrlWkr wkr;
	
	public void handleNewStockQtyEvent(@Observes @EntityCreatedEvent StkArtStockQty lotStockQty){
		newEntry(lotStockQty);
	}
	
	private void newEntry(StkArtStockQty lotStockQty){
		String key = lotStockQty.getCntnrIdentif();
		List<StkArtStockQty> list = cache.get(key);
		if(list==null){
			synchronized (this) {
				list = cache.get(key);
				if(list==null){
					list = new ArrayList<StkArtStockQty>();
					cache.put(key, list);
				}
			}
		}
		list.add(lotStockQty);
		Integer seqNbr = lotStockQty.getSeqNbr();
		synchronized (this) {
			ArrayList<StkArtStockQty> arrayList = new ArrayList<StkArtStockQty>(list);
			for (StkArtStockQty s : arrayList) {
				if(s.getSeqNbr()<seqNbr)list.remove(s);
			}
		}		
		if(list.size()>2){
			consolidate(lotStockQty);
		}
	}

	private void consolidate(StkArtStockQty lotStockQty) {
		wkr.cleaup(lotStockQty);
	}
}
