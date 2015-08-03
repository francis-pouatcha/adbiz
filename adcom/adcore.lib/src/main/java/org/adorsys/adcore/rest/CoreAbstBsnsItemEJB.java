package org.adorsys.adcore.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.adorsys.adcore.enums.CoreHistoryTypeEnum;
import org.adorsys.adcore.enums.CoreProcStepEnum;
import org.adorsys.adcore.enums.CoreProcessStatusEnum;
import org.adorsys.adcore.jpa.CoreAbstBsnsItem;
import org.adorsys.adcore.jpa.CoreAbstBsnsObject;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectHstry;
import org.adorsys.adcore.jpa.CoreAbstEntityCstr;
import org.adorsys.adcore.jpa.CoreAbstEntityJob;
import org.adorsys.adcore.jpa.CoreAbstEntityStep;
import org.adorsys.adcore.jpa.CoreBsnsItemInfo;
import org.adorsys.adcore.repo.CoreAbstBsnsItemRepo;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.utils.BigDecimalUtils;
import org.apache.commons.lang3.StringUtils;

public abstract class CoreAbstBsnsItemEJB<
	E extends CoreAbstBsnsObject, I extends CoreAbstBsnsItem, 
	H extends CoreAbstBsnsObjectHstry, J extends CoreAbstEntityJob,
	S extends CoreAbstEntityStep, C extends CoreAbstEntityCstr> extends CoreAbstIdentifiedEJB<I> {
	
	protected abstract CoreAbstBsnsObjInjector<E, I, H, J, S, C> getInjector();
	protected abstract CoreAbstBsnsItemRepo<I> getBsnsRepo();

	protected String prinHstryInfo(I item){
		return CoreBsnsItemInfo.prinInfo(item);
	};
	
	protected CoreAbstIdentifRepo<I> getRepo(){
		return getBsnsRepo();
	};
	
	public I create(I entity)
	{
		String salIndex = entity.getSalIndex();
		Long cnt = getInjector().getItemLookup().countByCntnrIdentifAndSalIndex(entity.getCntnrIdentif(), salIndex);
		List<I> found = null;
		if(cnt>0){
			found = getInjector().getItemLookup().findByCntnrIdentifAndSalIndex(entity.getCntnrIdentif(), salIndex, 0, cnt.intValue());
		} else {
			found = Collections.emptyList();
		}
		List<I> compareList = new ArrayList<I>();
		compareList.add(entity);
		compareList.addAll(found);
		Boolean sameQty = checkSameQty(compareList);
		Date now = new Date();
		if(!sameQty){
			entity.setConflictDt(now);
			for (I item : found) {
				// Only save if conflict date was null.
				if(item.getConflictDt()==null){
					item.setConflictDt(now);
					internalUpdate(item);
				}
			}
		} else { // resolve conflict if any
			entity.setConflictDt(null);
			for (I item : found) {
				// Only save if conflict date was null.
				if(item.getConflictDt()!=null){
					item.setConflictDt(null);
					internalUpdate(item);
				}
			}
		}

		I item = internalUpdate(entity);

		return item;
	}
	
	protected Boolean checkSameQty(List<I> compareList) {
		BigDecimal qty = null;
		Boolean sameQty = Boolean.FALSE;
		int count = 0;
		for (I item : compareList) {
			if(item.getDisabledDt()!=null) continue;
			if(count==0){
				qty = item.getTrgtQty();
				if(qty!=null)
					sameQty = Boolean.TRUE;
			} else {
				if(item.getTrgtQty()==null || !BigDecimalUtils.strictEquals(qty, item.getTrgtQty())){
					sameQty = Boolean.FALSE;
				} else {
					sameQty = Boolean.TRUE;
				}
			}
			count +=1;
		}
		return sameQty;
	}
	
	private I internalUpdate(I item){
		boolean created = StringUtils.isBlank(item.getId());
		item = getBsnsRepo().save(attach(item));
		if(created)
			getInjector().getHstrEjb().createItemHstry(item.getCntnrIdentif(), item.getIdentif(), CoreProcessStatusEnum.ITEM_CREATED.name(), CoreHistoryTypeEnum.ITEM_CREATED.name(),CoreProcStepEnum.EDITING.name(), CoreHistoryTypeEnum.ITEM_CREATED.name(), prinHstryInfo(item));
		else 
			getInjector().getHstrEjb().createItemHstry(item.getCntnrIdentif(), item.getIdentif(), CoreProcessStatusEnum.ITEM_UPDATED.name(), CoreHistoryTypeEnum.ITEM_UPDATED.name(),CoreProcStepEnum.EDITING.name(), CoreHistoryTypeEnum.ITEM_UPDATED.name(), prinHstryInfo(item));			
		return item;
	}
	

	public I update(I entity)
	{
		// Make sure there is a consistency, if not set conflicting date.
		// 1. Select all items of this object with the salIndex.
		String salIndex = entity.getSalIndex();
		Long cnt = getBsnsRepo().findByCntnrIdentifAndSalIndex(entity.getCntnrIdentif(), salIndex).count();
		List<I> found = null;
		if(cnt>0){
			found = getBsnsRepo().findByCntnrIdentifAndSalIndex(entity.getCntnrIdentif(), salIndex).firstResult(0).maxResults(cnt.intValue()).getResultList();
		} else {
			found = Collections.emptyList();
		}
		List<I> compareList = new ArrayList<I>();
		for (I item : found) {
			if(StringUtils.equals(item.getId(), entity.getId())){
				compareList.add(entity);
			} else {
				compareList.add(item);
			}
		}
		// 2. Make sure all non disabled have a number.
		Boolean sameQty = checkSameQty(compareList);
		I item2 = null;
		// If not all identical qty, set conflict.
		// Unless they are discarded.
		Date now = new Date();
		if(!sameQty){
			for (I item : compareList) {
				if(StringUtils.equals(item.getId(), entity.getId())){
					if(item.getConflictDt()==null){
						item.setConflictDt(now);
					}
					item2 = internalUpdate(item);
				} else {
					// Only save if conflict date was null.
					if(item.getConflictDt()==null){
						item.setConflictDt(now);
						internalUpdate(item);
					}
				}
			}
		} else { // resolve conflict if any
			for (I item : compareList) {
				if(StringUtils.equals(item.getId(), entity.getId())){
					if(item.getConflictDt()!=null){
						item.setConflictDt(null);
					}
					item2 = internalUpdate(item);
				} else {
					// Only save if conflict date was null.
					if(item.getConflictDt()!=null){
						item.setConflictDt(null);
						internalUpdate(item);
					}
				}
			}
		}
		return item2;
	}
	
	public void removeByCntnrIdentif(String bsnsObjNbr, int start, int max) {
		List<I> resultList = getBsnsRepo().findByCntnrIdentif(bsnsObjNbr).firstResult(start).maxResults(max).getResultList();
		for (I e : resultList) {
			deleteById(e.getId());
		}
	}

	public I deleteById(String id)
	{
		I item = getBsnsRepo().findBy(id);
		if (item != null)
		{
			Long bsnsObjCount = getInjector().getBsnsObjLookup().countByIdentif(item.getCntnrIdentif());
			getBsnsRepo().remove(item);
			if(bsnsObjCount>0)
				getInjector().getHstrEjb().createItemHstry(item.getCntnrIdentif(), item.getIdentif(), CoreProcessStatusEnum.ITEM_DELETED.name(), CoreHistoryTypeEnum.ITEM_DELETED.name(),CoreProcStepEnum.EDITING.name(), CoreHistoryTypeEnum.ITEM_DELETED.name(), prinHstryInfo(item));			
		}
		return item;
	}


	/**
	 * Returns the number of item automatically consolidated. This happens when all the business object of that 
	 * same item are counted with the same quantity. In this case the system automatically marks the latest 
	 * count as enabled by marking everything else disabled.
	 * 
	 * @param cntnrIdentif
	 * @return
	 */
	public void makeConsistent(String cntnrIdentif, String salIndex){
		Long countEnabled = getBsnsRepo().findByCntnrIdentifAndSalIndexAndDisabledDtIsNull(cntnrIdentif, salIndex).count();
		if(countEnabled==1) return;
		List<I> resultList = getBsnsRepo().findByCntnrIdentifAndSalIndex(cntnrIdentif, salIndex).getResultList();
		Boolean sameQty = checkSameQty(resultList);
		if(!sameQty) {
			setConflicting(resultList, new Date());
			getInjector().getBsnsObjEjb().handleInconsistentBsnsObj(cntnrIdentif);
			return;
		}
		I oldest = null;
		for (I item : resultList) {
			// continue if this item is disabled and there is more than one enabled item.
			if(item.getDisabledDt()!=null && countEnabled>1) continue;
			
			// set this as oldest.
			if(oldest==null) {
				oldest  = item;
				continue;
			}
			if(oldest.getAcsngDt()!=null && item.getAcsngDt()!=null && oldest.getAcsngDt().before(item.getAcsngDt())){
				oldest = item;
				continue;
			}
		}
		// enable oldest and 
		if(oldest!=null){
			Date date = new Date();
			for (I splItem : resultList) {
				boolean update = false;
				if(StringUtils.equals(oldest.getId(),splItem.getId())){
					if(splItem.getDisabledDt()==null){
						splItem.setDisabledDt(date);
						update = true;
					}
					if(splItem.getConflictDt()!=null){
						splItem.setConflictDt(null);
						update = true;
					}
				}
				if(update){
					internalUpdate(splItem);
				}
			}
			boolean update = false;
			if(oldest.getDisabledDt()!=null){
				oldest.setDisabledDt(null);
				update = true;
			}
			if(oldest.getConflictDt()!=null){
				oldest.setConflictDt(null);
				update = true;
			}
			if(update){
				internalUpdate(oldest);
			}
		}
	}
	
	public void setConflicting(List<I> items, Date conflictDt){
		for (I spleItem : items) {
			if(spleItem.getConflictDt()==null) {
				spleItem.setConflictDt(conflictDt);
				internalUpdate(spleItem);
			}
		}
	}	
}

