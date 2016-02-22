package org.adorsys.adstock.task;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.ejb.AccessTimeout;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.adorsys.adstock.jpa.StkMvntHstry;
import org.adorsys.adstock.jpa.StkMvntHstryGtwy;
import org.adorsys.adstock.repo.StkMvntHstryGtwyRepo;
import org.adorsys.adstock.rest.StkMvntHstryEJB;

@Singleton
@Startup
public class StkMvntHstryGtwyProcessor {

	@Inject
	private StkMvntHstryGtwyRepo gtwyRepo;
	
	@Inject
	private StkMvntHstryEJB hstryEJB;
	
	@EJB
	private StkMvntHstryGtwyProcessor processor;
	
	
	@Schedule(minute = "*", second="*/5" ,hour="*", persistent=false)
	@AccessTimeout(unit=TimeUnit.MINUTES, value=10)
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void loadGtwy(){
		Long count = gtwyRepo.count();
		if(count>1200) count = 1200l;
		int start = 0;
		int max = 100;
		while (start<count){
			int firstResult = start;
			start+=max;
			List<StkMvntHstryGtwy> list = gtwyRepo.findAll(firstResult, max);
			for (StkMvntHstryGtwy stkMvntHstryGtwy : list) {
				try {
					processor.processGtw(stkMvntHstryGtwy.getId());
				} catch (PersistenceException e){
					// ignore.
				}
			}
		}
	}
	
	@Asynchronous
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void processGtw(String id){
		StkMvntHstryGtwy htryGtwy = gtwyRepo.findBy(id);
		if(htryGtwy ==null) return;
		
		StkMvntHstry hstry = new StkMvntHstry();
		htryGtwy.copyTo(hstry);
		hstry.setId(id);
		
		
		hstry = hstryEJB.create(hstry);
		
		gtwyRepo.remove(htryGtwy);
	}
}
