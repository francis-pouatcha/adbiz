package org.adorsys.adcore.rest;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.adorsys.adcore.enums.CoreHistoryTypeEnum;
import org.adorsys.adcore.event.EntityClosingEvent;
import org.adorsys.adcore.event.EntityPostingEvent;
import org.adorsys.adcore.event.EntityUpdatingEvent;
import org.adorsys.adcore.exceptions.AdException;
import org.adorsys.adcore.jpa.CoreAbstBsnsItem;
import org.adorsys.adcore.jpa.CoreAbstBsnsObject;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectHstry;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchResult;
import org.adorsys.adcore.jpa.CoreAbstEntityCstr;
import org.adorsys.adcore.jpa.CoreAbstEntityJob;
import org.adorsys.adcore.jpa.CoreAbstEntityStep;
import org.adorsys.adcore.utils.BigDecimalUtils;
import org.adorsys.adcore.utils.CalendarUtil;
import org.apache.commons.lang3.StringUtils;


public abstract class CoreAbstBsnsObjectManager<E extends CoreAbstBsnsObject, I extends CoreAbstBsnsItem, 
	H extends CoreAbstBsnsObjectHstry, J extends CoreAbstEntityJob,
	S extends CoreAbstEntityStep, C extends CoreAbstEntityCstr, SI extends CoreAbstBsnsObjectSearchInput<E>> {

	protected abstract CoreAbstBsnsObjInjector<E, I, H, J, S, C> getInjector();	
	
	protected abstract Field[] getEntityFields();
	protected abstract CoreAbstBsnsObjectSearchResult<E, SI> newSearchResult(Long size, List<E> resultList,CoreAbstBsnsObjectSearchInput<E> searchInput);
	protected abstract CoreAbstBsnsObjectSearchInput<E> newSearchInput();

	@Inject
	@EntityClosingEvent
	private Event<E> entityClosingEvent;

	@Inject
	@EntityPostingEvent
	private Event<E> entityPostingEvent;

	@Inject
	@EntityUpdatingEvent
	private Event<E> entityUpdatingEvent;
	
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

			bsnsObj = getInjector().getBsnsObjEjb().create(bsnsObj);
		}
		return bsnsObj;
	}

	/**
	 * Update an incoming inventory.
	 * 	- a partial list of inventory
	 * @return
	 */
	public E update(E entity){
		entityUpdatingEvent.fire(entity);
		return getInjector().getBsnsObjEjb().update(entity);
	}
	
	public E close(E entity){
		entityClosingEvent.fire(entity);

		entity = getInjector().getBsnsObjLookup().findById(entity.getId());
		Date conflictDt = entity.getConflictDt();
		getInjector().getBsnsObjEjb().validateBusinessObject(entity.getIdentif());
		// Conflict detected. Save and return
		if(conflictDt==null && entity.getConflictDt()!=null) 
			return getInjector().getBsnsObjEjb().update(entity);
		
		// conflict still there. Just return
		if(entity.getConflictDt()!=null)return entity;

		return getInjector().getBsnsObjEjb().close(entity);
	}	

	public E validate(E entity){
		if(getInjector().getHstrLookup().hasAnyStatus(entity.getIdentif(), Arrays.asList(CoreHistoryTypeEnum.CLOSED.name()))) return entity;
		
		entity = getInjector().getBsnsObjLookup().findById(entity.getId());
		Date conflictDt = entity.getConflictDt();
		getInjector().getBsnsObjEjb().validateBusinessObject(entity.getIdentif());

		if(conflictDt!=entity.getConflictDt()) entity = getInjector().getBsnsObjEjb().update(entity);
		
		return getInjector().getBsnsObjLookup().findById(entity.getId());
	}	
	
	public E post(E entity){
		entityPostingEvent.fire(entity);

		Date conflictDt = entity.getConflictDt();
		getInjector().getBsnsObjEjb().validateBusinessObject(entity.getIdentif());
		// Conflict detected. Save and return
		if(conflictDt==null && entity.getConflictDt()!=null) return getInjector().getBsnsObjEjb().update(entity);
		
		// conflict still there. Just return
		if(entity.getConflictDt()!=null)return entity;
		return getInjector().getBsnsObjEjb().post(entity);
	}	
	
	public I addItem(I item) throws AdException {
		loadExistingBsnsObject(item);

		item.setAcsngUser(getInjector().getCallerPrincipal().getLoginName());
		String identifier = I.toIdentifier(item.getCntnrIdentif(), item.getAcsngUser(), item.getLotPic(), item.getArtPic(), item.getSection());
		
		I existing = getInjector().getItemLookup().findByIdentif(identifier);
		if(existing!=null){
			item.setId(existing.getId());
			return updateItem(item);
		}else {
			return getInjector().getItemEjb().create(item);
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

		I existing = getInjector().getItemLookup().findById(item.getId());
		if(existing==null)
			throw new AdException("Business Item inexistant");
		if(!CalendarUtil.isSameInstant(item.getDisabledDt(), existing.getDisabledDt()))
			throw new IllegalStateException("Use disableItem/enableItem to change the status of an business item.");

		String currentLoginName = getInjector().getCallerPrincipal().getLoginName();

		boolean changed = false;
		if(!BigDecimalUtils.strictEquals(item.getTrgtQty(), existing.getTrgtQty())){

			if(item.getAcsngDt()==null){
				item.setAcsngDt(new Date());
			}

			existing.setTrgtQty(item.getTrgtQty());
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
			existing = getInjector().getItemEjb().update(existing);
		}

		return existing;
	}

	public void disableItem(I item) throws AdException {
		loadExistingBsnsObject(item);
		I existing = getInjector().getItemLookup().findById(item.getId());
		if(existing==null)
			throw new AdException("Business Item inexistant");
		
		if(existing.getDisabledDt()==null){
			Date disabledDt = item.getDisabledDt()!=null?item.getDisabledDt():new Date();
			existing.setDisabledDt(disabledDt);
			existing = getInjector().getItemEjb().update(existing);
		}
	}

	public void enableItem(I item) throws AdException {
		loadExistingBsnsObject(item);

		I existing = getInjector().getItemLookup().findById(item.getId());
		if(existing==null)
			throw new AdException("Business Item inexistant");

		if(existing.getDisabledDt()!=null) {
			existing.setDisabledDt(null);
			existing = getInjector().getItemEjb().update(existing);
		}
	}
	
	private E loadExistingBsnsObject(I item) throws AdException{
		E entity = getInjector().getBsnsObjLookup().findByIdentif(item.getCntnrIdentif());
		if(entity==null) 
			throw new AdException("Missing business object");
		if(getInjector().getHstrLookup().hasAnyStatus(entity.getIdentif(), CoreHistoryTypeEnum.CLOSED.name()))
			throw new AdException("Business object closed");	
		
		return entity;
	}
}