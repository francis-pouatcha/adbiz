package org.adorsys.adcore.rest;

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
	
	public I create(I entity) {
		Date now = new Date();		
		Boolean sameQty = updateConflicting(entity, now);

		// update persisting entity.
		if(sameQty && entity.getConflictDt()!=null)entity.setConflictDt(null);
		if(!sameQty && entity.getConflictDt()==null)entity.setConflictDt(now);
		I item = internalUpdate(entity);

		return item;
	}
	
	public I update(I entity) {
		Date now = new Date();
		Boolean sameQty = updateConflicting(entity, now);

		// update persisting entity.
		if(sameQty && entity.getConflictDt()!=null)entity.setConflictDt(null);
		if(!sameQty && entity.getConflictDt()==null)entity.setConflictDt(now);
		I item = internalUpdate(entity);

		return item;
	}
	
	protected Boolean updateConflicting(I entity, Date now){
		// Check if all article counted have the same quantity
		List<I> compareList = readPeerSalIndex(entity);
		Boolean sameQty = checkSameQty(compareList);

		for (I item : compareList) {
			if(item==entity)continue;
			
			if(!sameQty){
				// Only save if conflict date was null.
				if(item.getConflictDt()==null){
					item.setConflictDt(now);
					internalUpdate(item);
				}
			} else { 
				// Only save if conflict date was not null.
				if(item.getConflictDt()!=null){
					item.setConflictDt(null);
					internalUpdate(item);
				}
			}
		}
		return sameQty;
	}
	
	protected Boolean checkSameQty(List<I> compareList) {
		// return true if single or empty.
		if(compareList==null || compareList.size()<2) return Boolean.TRUE;
		
		I currentItem = null;
		for (I item : compareList) {
			// Ignore disabled item.
			if(item.getDisabledDt()!=null) continue;
			
			if(currentItem==null) {
				currentItem=item;
				continue;
			}
			
			if(!BigDecimalUtils.strictEquals(currentItem.getTrgtQty(), item.getTrgtQty())) return Boolean.FALSE;
		}
		
		return Boolean.TRUE;
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
	

	private List<I> readPeerSalIndex(I entity){
		// The section article lot index
		String salIndex = entity.getSalIndex();
		
	
		// Do we have this item counted
		CoreAbstBsnsObjInjector<E, I, H, J, S, C> injector = getInjector();
		CoreAbstBsnsItemLookup<I> itemLookup = injector.getItemLookup();
		String cntnrIdentif = entity.getCntnrIdentif();
		Long cnt = itemLookup.countByCntnrIdentifAndSalIndex(cntnrIdentif, salIndex);

		List<I> found = null;
		if(cnt>0){
			found = itemLookup.findByCntnrIdentifAndSalIndex(cntnrIdentif, salIndex, 0, cnt.intValue());
		} else {
			found = Collections.emptyList();
		}

		List<I> compareList = new ArrayList<I>();
		compareList.add(entity);
		for (I item : found) {
			// Add everything but the target entity.
			if(!StringUtils.equals(item.getId(), entity.getId())){
				compareList.add(item);
			}
		}
		return compareList;
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
		if(resultList.isEmpty()) return;
		
		Boolean sameQty = checkSameQty(resultList);
		// If any conflict, save and return.
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
		
		if(oldest==null) return;
		
		if(oldest.getDisabledDt()!=null || oldest.getConflictDt()!=null){
			oldest.setDisabledDt(null);
			oldest.setConflictDt(null);
			internalUpdate(oldest);
		}
		
		// enable oldest and
		resultList = new ArrayList<I>(resultList);
		resultList.remove(oldest);
		
		Date date = new Date();
		for (I splItem : resultList) {
			if(splItem.getDisabledDt()==null || splItem.getConflictDt()==null){
				if(splItem.getDisabledDt()==null)splItem.setDisabledDt(date);
				if(splItem.getConflictDt()==null)splItem.setConflictDt(date);
				internalUpdate(splItem);
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

