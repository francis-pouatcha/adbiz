package org.adorsys.adcore.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.ejb.AccessTimeout;
import javax.ejb.Asynchronous;
import javax.ejb.Schedule;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityExistsException;

import org.adorsys.adcore.jpa.CoreAbstIdentifHstry;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;
import org.adorsys.adcore.jpa.CoreAbstLastProcessedHstry;
import org.adorsys.adcore.jpa.CoreAbstProcessedHstry;
import org.adorsys.adcore.jpa.CoreAbstProcessingEntity;
import org.apache.commons.lang3.time.DateUtils;

public abstract class CoreAbstIdentifHstryProcessor<H extends CoreAbstIdentifHstry, 
	E extends CoreAbstIdentifObject, 
	PsngE extends CoreAbstProcessingEntity, PsedH extends CoreAbstProcessedHstry, LastPsedH extends CoreAbstLastProcessedHstry> {

	protected abstract CoreAbstIdentifiedHstryLookup<H> getHstryLookup();
	protected abstract CoreAbstIdentifLookup<LastPsedH> getLastPsedHLookup();
	protected abstract CoreAbstIdentifiedEJB<LastPsedH> getLastPsedHEJB();
	protected abstract CoreAbstIdentifLookup<PsedH> getPsedHLookup();
	protected abstract CoreAbstIdentifiedEJB<PsedH> getPsedHEJB();
	protected abstract CoreAbstIdentifLookup<PsngE> getPsngELookup();
	protected abstract CoreAbstIdentifiedEJB<PsngE> getPsngEEJB();
	
	protected abstract CoreAbstIdentifHstryProcessor<H, E, PsngE, PsedH, LastPsedH> getProcessor();
	protected abstract PsedH newPsedH();
	protected abstract void doProcessHstry(H h);
	protected abstract String getProcessIdentifier();
	protected abstract PsngE newPsngE();
	protected abstract LastPsedH newLastPsedH();
	
	@Schedule(minute = "*", second="7/35" ,hour="*", persistent=false)
	@AccessTimeout(unit=TimeUnit.MINUTES, value=10)
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void processHstry(){
		LastPsedH lastPsedH = cleanLastProcessed();
		
		Long hstryCount = null;
		if(lastPsedH!=null){
			hstryCount = getHstryLookup().countByIdGreaterThan(lastPsedH.getIdentif());
		} else {
			hstryCount = getHstryLookup().count();
		}
		// Iterate over history, and for each one queue processing.
		int start = 0;
		int max = 100;
		while(start < hstryCount){
			int firstResult = start;
			start+=max;
			List<H> list = null;
			if(lastPsedH!=null){			
				list = getHstryLookup().findByIdGreaterThan(lastPsedH.getIdentif(), firstResult, max);
			} else {
				list = getHstryLookup().findAll(firstResult, max);
			}
			for (H h : list) {
				getProcessor().processHistory(h.getId());
			}
		}
	}
	
	@Schedule(minute = "*", second="13/35" ,hour="*", persistent=false)
	@AccessTimeout(unit=TimeUnit.MINUTES, value=10)
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void synchLastProcessed(){
		LastPsedH lastPsedH = cleanLastProcessed();
		Long hstryCount = null;
		if(lastPsedH!=null){
			hstryCount = getHstryLookup().countByIdGreaterThan(lastPsedH.getIdentif());
		} else {
			hstryCount = getHstryLookup().count();
		}
		// Iterate over history, and for each one queue processing.
		int start = 0;
		int max = 100;
		while(start < hstryCount){
			int firstResult = start;
			start+=max;
			List<H> list = null;
			if(lastPsedH!=null){			
				list = getHstryLookup().findByIdGreaterThan(lastPsedH.getIdentif(), firstResult, max);
			} else {
				list = getHstryLookup().findAll(firstResult, max);
			}
			H lastProcessedHist = null;
			for (H h : list) {
				Long idCount = getPsedHLookup().countByIdentif(h.getId());// the identifier is the id of the history.
				if(idCount>0) continue;
				lastProcessedHist = h;
				break;
			}
			if(lastProcessedHist!=null){
				LastPsedH entity = newLastPsedH();
				entity.setCntnrIdentif(lastProcessedHist.getEntIdentif());
				entity.setIdentif(lastProcessedHist.getId());
				entity.setValueDt(new Date());
				if(lastPsedH!=null)
					entity.setSeqNbr(lastPsedH.getSeqNbr()+1);
				getLastPsedHEJB().create(entity);
			}
		}
	}
	
	@Schedule(minute = "*", second="17/35" ,hour="*", persistent=false)
	@AccessTimeout(unit=TimeUnit.MINUTES, value=10)
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void cleanProcessedHstry(){
		LastPsedH lastPsedH = cleanLastProcessed();
		if(lastPsedH==null) return;
		Long count = getPsedHLookup().countByIdentifLessThan(lastPsedH.getIdentif());
		int start = 0;
		int max = 100;
		while (start< count){
			int first=start;
			start+=max;
			List<PsedH> list = getPsedHLookup().findByIdentifLessThan(lastPsedH.getIdentif(), first, max);
			List<String> idList = new ArrayList<String>();
			for (PsedH psedH : list) {
				idList.add(psedH.getId());
			}
			getProcessor().deleteProcessedHstry(idList);
		}
	}
	
	@Asynchronous
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void deleteProcessedHstry(List<String> idList){
		for (String id : idList) {
			getPsedHEJB().deleteById(id);
		}
	}
	
	private LastPsedH cleanLastProcessed(){
		Long count = getLastPsedHLookup().count();// We are supposed to have at most one in there.
		List<LastPsedH> listAll = getLastPsedHLookup().listAll(0, count.intValue());
		List<LastPsedH> deleteList = new ArrayList<LastPsedH>();
		LastPsedH lastPsedH = null;
		for (LastPsedH l : listAll) {
			if(lastPsedH==null){
				lastPsedH=l; continue;
			}
			if(lastPsedH.getSeqNbr()<l.getSeqNbr()){
				deleteList.add(lastPsedH);
				lastPsedH=l;
			}
		}
		if(!deleteList.isEmpty()){
			List<String> idList = new ArrayList<String>();
			for (LastPsedH la : deleteList) {
				idList.add(la.getId());
			}
			getProcessor().cleanLastPsedHstry(idList);
		}
		
		return lastPsedH;
	}
	
	@Asynchronous
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void processHistory(String histId) {
		H h = getHstryLookup().findById(histId);
		if(h==null) return;
		// check is this history was processed.
		Long idCount = getPsedHLookup().countByIdentif(h.getId());// the identifier is the id of the history.
		if(idCount>0) return;
		
		// check if the corresponding entity is being processed.
		Long entIdCount = getPsngELookup().countByIdentif(h.getEntIdentif());
		if(entIdCount>0) return;// processing will be done by the thread.
		
		// Lock processing entity
		PsngE lockedPsngE = null;
		try {
			lockedPsngE = getProcessor().lockPsngE(h.getId());
		} catch (EntityExistsException e){
			// noop
			return;
		}
		
		doProcessHstry(h);
		
		PsedH psedH = newPsedH();
		psedH.setCntnrIdentif(h.getEntIdentif());
		psedH.setIdentif(h.getId());
		psedH.setValueDt(new Date());
		getPsedHEJB().create(psedH);
		
		unlockPsngE(lockedPsngE);
		
		// get the next history of this entity and process.
		List<H> nextList = getHstryLookup().findByEntIdentifAndIdGreaterThan(h.getEntIdentif(), h.getId(), 0, 1);
		if(!nextList.isEmpty())
			getProcessor().processHistory(nextList.iterator().next().getId());
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public PsngE lockPsngE(String histId) throws EntityExistsException {
		H h = getHstryLookup().findById(histId);
		if(h==null) return null;
		// Lock processing entity
		PsngE psngEntity = newPsngE();
		psngEntity.setCntnrIdentif(getProcessIdentifier());
		psngEntity.setIdentif(h.getEntIdentif());
		psngEntity.setValueDt(DateUtils.addMinutes(new Date(), 5));// five minutes lease.
		return getPsngEEJB().create(psngEntity);
	}
	
	private void unlockPsngE(PsngE psngE){
		getPsngEEJB().deleteById(psngE.getId());
	}
	
	@Asynchronous
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void cleanLastPsedHstry(List<String> deleteList) {
		for (String id : deleteList) {
			getLastPsedHEJB().deleteById(id);
		}
	}

}
