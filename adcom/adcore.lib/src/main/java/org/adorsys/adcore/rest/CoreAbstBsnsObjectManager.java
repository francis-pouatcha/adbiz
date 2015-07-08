package org.adorsys.adcore.rest;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.adorsys.adcore.auth.TermWsUserPrincipal;
import org.adorsys.adcore.enums.CoreHistoryTypeEnum;
import org.adorsys.adcore.enums.CoreProcStepEnum;
import org.adorsys.adcore.enums.CoreProcessStatusEnum;
import org.adorsys.adcore.exceptions.AdException;
import org.adorsys.adcore.jpa.CoreAbstBsnsItem;
import org.adorsys.adcore.jpa.CoreAbstBsnsObject;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectHstry;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchResult;
import org.adorsys.adcore.jpa.CoreAbstEntityCstr;
import org.adorsys.adcore.jpa.CoreAbstEntityJob;
import org.adorsys.adcore.jpa.CoreAbstEntityStep;
import org.adorsys.adcore.repo.CoreAbstBsnsObjectRepo;
import org.adorsys.adcore.utils.BigDecimalUtils;
import org.adorsys.adcore.utils.CalendarUtil;
import org.apache.commons.lang3.StringUtils;


public abstract class CoreAbstBsnsObjectManager<E extends CoreAbstBsnsObject, I extends CoreAbstBsnsItem, 
	H extends CoreAbstBsnsObjectHstry, J extends CoreAbstEntityJob,
	S extends CoreAbstEntityStep, C extends CoreAbstEntityCstr, SI extends CoreAbstBsnsObjectSearchInput<E>> {

	protected abstract CoreAbstBsnsObjectLookup<E> getLookup();
	protected abstract Field[] getEntityFields();
	protected abstract CoreAbstBsnsObjectSearchResult<E, SI> newSearchResult(Long size, List<E> resultList,CoreAbstBsnsObjectSearchInput<E> searchInput);
	protected abstract CoreAbstBsnsObjectSearchInput<E> newSearchInput();
	protected abstract CoreAbstBsnsObjectRepo<E> getBsnsRepo();
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

	/**
	 * New business processs. The object is created if necessary.
	 * 
	 * @param bsnsObj
	 * @return
	 */
	public E initiateBsnsObj(E bsnsObj) {
		Date now = new Date();
		if(StringUtils.isBlank(bsnsObj.getId())){
			if(bsnsObj.getValueDt()==null)
				bsnsObj.setValueDt(now);

			bsnsObj = getEjb().create(bsnsObj);
		}
		return bsnsObj;
	}

	/**
	 * Update an incoming inventory.
	 * 	- a partial list of inventory
	 * @return
	 */
	public E update(E entity){
		getLifeCycle().onUpdate(entity.getIdentif()); 
		return getEjb().update(entity);
	}
	
	public E close(E entity){
		getHistoryEjb().createHstry(entity.getIdentif(), CoreProcessStatusEnum.CLOSING.name(), CoreHistoryTypeEnum.CLOSING.name(),CoreProcStepEnum.CLOSING.name(), CoreHistoryTypeEnum.CLOSING.name(), prinHstryInfo(entity));

		entity = getLookup().findById(entity.getId());
		Date conflictDt = entity.getConflictDt();
		getEjb().validateBusinessObject(entity.getIdentif());
		// Conflict detected. Save and return
		if(conflictDt==null && entity.getConflictDt()!=null) 
			return getEjb().update(entity);
		
		// conflict still there. Just return
		if(entity.getConflictDt()!=null)return entity;

		return getEjb().close(entity);
	}	

	public E validate(E entity){
		if(getHistoryLookup().hasAnyStatus(entity.getIdentif(), Arrays.asList(CoreHistoryTypeEnum.CLOSED.name()))) return entity;
		
		entity = getLookup().findById(entity.getId());
		Date conflictDt = entity.getConflictDt();
		getEjb().validateBusinessObject(entity.getIdentif());

		if(conflictDt!=entity.getConflictDt()) entity = getEjb().update(entity);
		
		return getLookup().findById(entity.getId());
	}	
	
	public E post(E entity){
		getHistoryEjb().createHstry(entity.getIdentif(), CoreProcessStatusEnum.POSTING.name(), CoreHistoryTypeEnum.POSTING.name(),CoreProcStepEnum.POSTING.name(), CoreHistoryTypeEnum.POSTING.name(), prinHstryInfo(entity));


		Date conflictDt = entity.getConflictDt();
		getEjb().validateBusinessObject(entity.getIdentif());
		// Conflict detected. Save and return
		if(conflictDt==null && entity.getConflictDt()!=null) return getEjb().update(entity);
		
		// conflict still there. Just return
		if(entity.getConflictDt()!=null)return entity;
		return getEjb().post(entity);
	}	
	
	public I addItem(I item) throws AdException {
		loadExistingBsnsObject(item);

		item.setAcsngUser(getCallerPrincipal().getLoginName());
		String identifier = I.toIdentifier(item.getCntnrIdentif(), item.getAcsngUser(), item.getLotPic(), item.getArtPic(), item.getSection());
		
		I existing = getItemLookup().findByIdentif(identifier);
		if(existing!=null){
			item.setId(existing.getId());
			return updateItem(item);
		}else {
			return getItemEjb().create(item);
		}
	}
	
	
	/**
	 * Updates an inventory item. Conditions are:
	 * <ol>
	 * 	<li>The Inventory must not have been closed yet.</li>
	 * 	<li>The Inventory item has been created already. Is just being modified.</li>
	 * 	<li>The Inventory disabledDt must not have changed.</li>
	 * </ol>
	 * @param item
	 * @return
	 * @throws AdException 
	 */
	public I updateItem(I item) throws AdException {
		loadExistingBsnsObject(item); 

		I existing = getItemLookup().findById(item.getId());
		if(existing==null)
			throw new AdException("Business Item inexistant");
		if(!CalendarUtil.isSameInstant(item.getDisabledDt(), existing.getDisabledDt()))
			throw new IllegalStateException("Use disableItem/enableItem to change the status of an business item.");

		String currentLoginName = getCallerPrincipal().getLoginName();

		boolean changed = false;
		if(!BigDecimalUtils.strictEquals(item.getAssdQty(), existing.getAssdQty())){

			if(item.getAcsngDt()==null){
				item.setAcsngDt(new Date());
			}

			existing.setAssdQty(item.getAssdQty());
			existing.setAcsngDt(item.getAcsngDt());
			existing.setAcsngUser(currentLoginName);
			existing.evlte();
			changed = true;
		}
		if(!CalendarUtil.isSameDay(item.getExpirDt(), existing.getExpirDt()) && item.getExpirDt()!=null){
			existing.setExpirDt(item.getExpirDt());
			changed = true;
		}
		if(changed) {
			existing = getItemEjb().update(existing);
		}

		return existing;
	}

	public void disableItem(I item) throws AdException {
		loadExistingBsnsObject(item);
		I existing = getItemLookup().findById(item.getId());
		if(existing==null)
			throw new AdException("Business Item inexistant");
		
		if(existing.getDisabledDt()==null){
			Date disabledDt = item.getDisabledDt()!=null?item.getDisabledDt():new Date();
			existing.setDisabledDt(disabledDt);
			existing = getItemEjb().update(existing);
		}
	}

	public void enableItem(I item) throws AdException {
		loadExistingBsnsObject(item);

		I existing = getItemLookup().findById(item.getId());
		if(existing==null)
			throw new AdException("Business Item inexistant");

		if(existing.getDisabledDt()!=null) {
			existing.setDisabledDt(null);
			existing = getItemEjb().update(existing);
		}
	}
	
	private E loadExistingBsnsObject(I item) throws AdException{
		E entity = getLookup().findByIdentif(item.getCntnrIdentif());
		if(entity==null) 
			throw new AdException("Missing business object");
		if(getHistoryLookup().hasAnyStatus(entity.getIdentif(), CoreHistoryTypeEnum.CLOSED.name()))
			throw new AdException("Business object closed");	
		
		return entity;
	}
}