package org.adorsys.adcore.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.adorsys.adcore.auth.TermWsUserPrincipal;
import org.adorsys.adcore.enums.CoreHistoryTypeEnum;
import org.adorsys.adcore.enums.CoreProcStepEnum;
import org.adorsys.adcore.enums.CoreProcessStatusEnum;
import org.adorsys.adcore.jpa.CoreAbstBsnsItem;
import org.adorsys.adcore.jpa.CoreAbstBsnsObject;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectHstry;
import org.adorsys.adcore.jpa.CoreAbstEntityCstr;
import org.adorsys.adcore.jpa.CoreAbstEntityJob;
import org.adorsys.adcore.jpa.CoreAbstEntityStep;
import org.adorsys.adcore.repo.CoreAbstBsnsObjectRepo;
import org.adorsys.adcore.repo.CoreAbstIdentifDataRepo;
import org.adorsys.adcore.utils.SequenceGenerator;
import org.apache.commons.lang3.StringUtils;

public abstract class CoreAbstBsnsObjectEJB<
	E extends CoreAbstBsnsObject, I extends CoreAbstBsnsItem, 
	H extends CoreAbstBsnsObjectHstry, J extends CoreAbstEntityJob,
	S extends CoreAbstEntityStep, C extends CoreAbstEntityCstr> extends CoreAbstIdentifiedEJB<E>{

	protected abstract CoreAbstBsnsObjectRepo<E> getBsnsRepo();
	protected abstract CoreAbstBsnsObjectLookup<E> getLookup();
	protected abstract CoreAbstBsnsObjectEJB<E, I, H, J, S, C> getEjb();
	protected abstract CoreAbstBsnsItemLookup<I> getItemLookup();
	protected abstract CoreAbstBsnsItemEJB<E, I, H, J, S, C> getItemEjb();
	protected abstract CoreAbstBsnsObjectHstryLookup<H> getHistoryLookup();
	protected abstract CoreAbstBsnsObjectHstryEJB<E, I, H, J, S, C> getHistoryEjb();
	protected abstract String getSequenceGeneratorPrefix();
	protected abstract TermWsUserPrincipal getCallerPrincipal();
	protected abstract String prinHstryInfo(E entity);
	protected abstract CoreAbstBsnsObjLifeCycle<H> getLifeCycle();
	protected abstract CoreAbstEntityJobEJB<J> getJobEjb();
	protected abstract CoreAbstEntityJobLookup<J> getJobLookup();
	protected abstract CoreAbstEntityStepEJB<S> getStepEjb();
	protected abstract CoreAbstEntityStepLookup<S> getStepLookup();
	protected abstract CoreAbstEntityCstrEJB<C> getCstrEjb();
	protected abstract CoreAbstEntityCstrLookup<C> getCstrLookup();
	
	protected CoreAbstIdentifDataRepo<E> getRepo(){
		return getBsnsRepo();
	};

	public E create(E entity)
	{
		if(StringUtils.isBlank(entity.getIdentif())){
			String sequence = SequenceGenerator.getSequence(getSequenceGeneratorPrefix());
			entity.setIdentif(sequence);
		}

		E saved = getBsnsRepo().save(attach(entity));
		getHistoryEjb().createHstry(saved.getIdentif(), CoreProcessStatusEnum.INITIATED.name(), CoreHistoryTypeEnum.INITIATED.name(),CoreProcStepEnum.INITIATING.name(), CoreHistoryTypeEnum.INITIATED.name(), prinHstryInfo(saved));
		return saved;
	}
	

	public E close(E entity){
		recompute(entity);
		entity.evlte();

		E saved = getBsnsRepo().save(attach(entity));
		
		getHistoryEjb().createHstry(saved.getIdentif(), CoreProcessStatusEnum.CLOSED.name(), CoreHistoryTypeEnum.CLOSED.name(),CoreProcStepEnum.CLOSING.name(), CoreHistoryTypeEnum.CLOSED.name(), prinHstryInfo(saved));
		return saved;
	}

	public E post(E entity){
		
		recompute(entity);
		
		entity.evlte();

		E saved = getBsnsRepo().save(attach(entity));
		
		getHistoryEjb().createHstry(saved.getIdentif(), CoreProcessStatusEnum.POSTED.name(), CoreHistoryTypeEnum.POSTED.name(),CoreProcStepEnum.POSTING.name(), CoreHistoryTypeEnum.POSTED.name(), prinHstryInfo(saved));
		return saved;
	}
	
	
	public E deleteById(String id)
	{
		E entity = getBsnsRepo().findBy(id);
		if(entity==null) return null;

		getBsnsRepo().remove(entity);
		getHistoryEjb().createHstry(entity.getIdentif(), CoreProcessStatusEnum.DELETED.name(), CoreHistoryTypeEnum.DELETED.name(),CoreProcStepEnum.DELETING.name(), CoreHistoryTypeEnum.DELETED.name(), prinHstryInfo(entity));

		return entity;
	}
		
	private E internalUpdate(E entity){
		return getBsnsRepo().save(attach(entity));		
	}

	public E recompute(E entity){
		entity.clearValues();
		String bsnsObjNbr = entity.getIdentif();
		Long itemCount = getItemLookup().countByCntnrIdentif(bsnsObjNbr);
		int start = 0;
		int max = 100;
		List<I> currentItems = new ArrayList<I>();
		String currentSalIndex = null;
		while(start<itemCount){
			List<I> list = getItemLookup().findByCntnrIdentifOrderBySalIndexAsc(bsnsObjNbr, start, max);
			for (I item : list) {
				if(currentSalIndex==null){
					currentSalIndex = item.getSalIndex();
					currentItems.clear();
					currentItems.add(item);
				} else if(currentSalIndex.equals(item.getSalIndex())){
					currentItems.add(item);
				} else {
					addItemFromList(entity, currentItems);
					currentSalIndex = item.getSalIndex();
					currentItems.clear();
					currentItems.add(item);
				}
			}
		}
		// The last item.
		addItemFromList(entity, currentItems);
		
		return entity;
	}
	/*
	 * Auto selection of items for automatic computation of a 
	 * totals, even if some conflicts exit.
	 */
	private void addItemFromList(E entity, List<I> currentItems) {
		if(currentItems.isEmpty()) return;
		if(currentItems.size()==1) {
			entity.addItemValue(currentItems.iterator().next());
		}else {
			// if only one is active, take it.
			List<I> enabledItems = new ArrayList<I>();
			I anyOldestItem = null;
			I enabledOldesItem = null;
			for (I i : currentItems) {
				if(anyOldestItem==null) {
					anyOldestItem = i;
				} else {
					choseOldest(anyOldestItem, i);
				}
				if(i.getDisabledDt()==null){
					enabledItems.add(i);
					if(enabledOldesItem==null) {
						enabledOldesItem = i;
					} else {
						choseOldest(enabledOldesItem, i);
					}
				}
			}
			if(enabledItems.size()==1){
				entity.addItemValue(enabledItems.iterator().next());
			} else if(enabledItems.size()>1) {
				entity.addItemValue(enabledOldesItem);
			} else {
				entity.addItemValue(anyOldestItem);
			}
		}
	}
	
	private I choseOldest(I itemA, I itemB){
		if(itemA.getAcsngDt()==itemB.getAcsngDt()) return itemA;
		if(itemA.getAcsngDt()==null) return itemB;
		if(itemB.getAcsngDt()==null) return itemA;
		if(itemA.getAcsngDt().before(itemB.getAcsngDt())) return itemB;
		return itemA;
	}
	
	public void processStep(S step){}
	
	public void validateBusinessObject(String cntnrIdentif){

		Long countByCntnrIdentif = getItemLookup().countByCntnrIdentif(cntnrIdentif);
		int start = 0;
		int max = 100;
		String salIndex = null;
		boolean cleanIndex = false;
		boolean clean = true;
		while(start<=countByCntnrIdentif){
			// @WARNIGN increase counter before request to avoid endless loop on error. 
			int firstResult = start;
			start +=max;
			List<I> found = getItemLookup().findByCntnrIdentifOrderBySalIndexAsc(cntnrIdentif, firstResult, max);
			for (I item : found) {
				if(StringUtils.equals(salIndex, item.getSalIndex()) && !cleanIndex){
					Long enbld = getItemLookup().countByCntnrIdentifAndSalIndexAndDisabledDtIsNull(cntnrIdentif, salIndex);
					if(enbld==1) {
						cleanIndex=true;
						continue;
					} else {
						clean = false;
						getItemEjb().makeConsistent(cntnrIdentif, salIndex);
					}
				} else {
					salIndex = item.getSalIndex();
					cleanIndex = false;
				}
			}
			
		}
		if(clean)// send noConflictEvent.
			handleConsistentBsnsObj(cntnrIdentif);
	}	
	
	public CoreAbstBsnsObject recomputeBusinessObject(String hldrNbr, CoreAbstBsnsObject accumulator){
		Long count = getItemLookup().countByCntnrIdentifAndDisabledDtIsNull(hldrNbr);
		int start = 0;
		int max = 100;
		while(start<=count){
			int firstResult = start;
			start +=max;
			List<I> found = getItemLookup().findByCntnrIdentifOrderBySalIndexAsc(hldrNbr, firstResult, max);
			for (I e : found) {
				accumulator.addItemValue(e);
			}
		}
		return accumulator;
	}	
	
	public void handleInconsistentBsnsObj(String bsnsObjNbr){
		E entity = getLookup().findByIdentif(bsnsObjNbr);
		if(entity==null) return;
		if(entity.getConflictDt()!=null) return;
		entity.setConflictDt(new Date());
		internalUpdate(entity);
	}

	public void handleConsistentBsnsObj(String bsnsObjNbr){
		E entity = getLookup().findByIdentif(bsnsObjNbr);
		if(entity==null) return;
		if(entity.getConflictDt()==null) return;
		entity.setConflictDt(null);
		internalUpdate(entity);
	}
}
