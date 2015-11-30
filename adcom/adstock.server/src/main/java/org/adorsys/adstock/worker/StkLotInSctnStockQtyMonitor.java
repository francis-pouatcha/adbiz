package org.adorsys.adstock.worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Singleton;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.adorsys.adcore.event.EntityCreatedEvent;
import org.adorsys.adstock.jpa.StkLotInSctnStockQty;

/**
 * This is an in vm singleton. It gather info on entries. Check for
 * inconsistencies and triggers consolidation if necessary. 
 * 
 * @author francis
 *
 */
@Singleton
public class StkLotInSctnStockQtyMonitor {

	private Map<String, List<StkLotInSctnStockQty>> cache = new HashMap<String, List<StkLotInSctnStockQty>>();
	
	@Inject
	private StkLotInSctnStockQtyCtrlWkr wkr;
	
	public void handleNewStockQtyEvent(@Observes @EntityCreatedEvent StkLotInSctnStockQty lotInSctnStockQty){
		newEntry(lotInSctnStockQty);
	}
	
	private void newEntry(StkLotInSctnStockQty lotInSctnStockQty){
		String key = lotInSctnStockQty.artPicAndLotPicAndSection();
		List<StkLotInSctnStockQty> list = cache.get(key);
		if(list==null){
			synchronized (this) {
				list = cache.get(key);
				if(list==null){
					list = new ArrayList<StkLotInSctnStockQty>();
					cache.put(key, list);
				}
			}
		}
		list.add(lotInSctnStockQty);
		Integer seqNbr = lotInSctnStockQty.getSeqNbr();
		synchronized (this) {
			ArrayList<StkLotInSctnStockQty> arrayList = new ArrayList<StkLotInSctnStockQty>(list);
			for (StkLotInSctnStockQty s : arrayList) {
				if(s.getSeqNbr()<seqNbr)list.remove(s);
			}
		}		
		if(list.size()>2){
			consolidate(lotInSctnStockQty);
		}
	}

	private void consolidate(StkLotInSctnStockQty lotInSctnStockQty) {
		wkr.cleaup(lotInSctnStockQty);
	}
}
