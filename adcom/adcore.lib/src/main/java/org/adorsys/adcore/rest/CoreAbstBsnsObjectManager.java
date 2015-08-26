package org.adorsys.adcore.rest;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.adorsys.adcore.auth.AdcomUser;
import org.adorsys.adcore.enums.CoreHistoryTypeEnum;
import org.adorsys.adcore.event.EntityClosingEvent;
import org.adorsys.adcore.event.EntityPostingEvent;
import org.adorsys.adcore.event.EntityUpdatingEvent;
import org.adorsys.adcore.exceptions.AdException;
import org.adorsys.adcore.exceptions.AdRestException;
import org.adorsys.adcore.jpa.CoreAbstBsnsItem;
import org.adorsys.adcore.jpa.CoreAbstBsnsObject;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectHstry;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchResult;
import org.adorsys.adcore.jpa.CoreAbstEntityCstr;
import org.adorsys.adcore.jpa.CoreAbstEntityJob;
import org.adorsys.adcore.jpa.CoreAbstEntityStep;
import org.adorsys.adcore.utils.BigDecimalUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;


public abstract class CoreAbstBsnsObjectManager<E extends CoreAbstBsnsObject, I extends CoreAbstBsnsItem, 
	H extends CoreAbstBsnsObjectHstry, J extends CoreAbstEntityJob,
	S extends CoreAbstEntityStep, C extends CoreAbstEntityCstr, SI extends CoreAbstBsnsObjectSearchInput<E>> {

	protected abstract CoreAbstBsnsObjInjector<E, I, H, J, S, C> getInjector();	
	
	protected abstract Field[] getEntityFields();
	protected abstract CoreAbstBsnsObjectSearchResult<E> newSearchResult(Long size, List<E> resultList,CoreAbstBsnsObjectSearchInput<E> searchInput);
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
	
	@Inject
	private AdcomUser callerPrincipal;
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

	public E update(String identif, E entity) throws AdRestException{
		E existing = getInjector().getBsnsObjLookup().findByIdentif(identif);
		if(existing==null) throw new AdRestException(Response.Status.NOT_FOUND);

		if(!StringUtils.equals(existing.getIdentif(), entity.getIdentif()))
			throw new AdRestException(Response.Status.CONFLICT);

		entityUpdatingEvent.fire(entity);
		
		return getInjector().getBsnsObjEjb().update(entity);
	}
	
	public E close(String identif) throws AdRestException{
		E entity = getInjector().getBsnsObjLookup().findByIdentif(identif);
		if(entity==null) throw new AdRestException(Response.Status.NOT_FOUND);
		
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

	public E validate(String identif) throws AdRestException {
		E entity = getInjector().getBsnsObjLookup().findByIdentif(identif);
		if(entity==null) throw new AdRestException(Response.Status.NOT_FOUND);
		
		if(getInjector().getHstrLookup().hasAnyStatus(entity.getIdentif(), Arrays.asList(CoreHistoryTypeEnum.CLOSED.name()))) return entity;
		
		entity = getInjector().getBsnsObjLookup().findById(entity.getId());
		Date conflictDt = entity.getConflictDt();
		getInjector().getBsnsObjEjb().validateBusinessObject(entity.getIdentif());

		if(conflictDt!=entity.getConflictDt()) entity = getInjector().getBsnsObjEjb().update(entity);
		
		return getInjector().getBsnsObjLookup().findById(entity.getId());
	}	
	
	public E post(String identif) throws AdRestException{
		E entity = getInjector().getBsnsObjLookup().findByIdentif(identif);
		if(entity==null) throw new AdRestException(Response.Status.NOT_FOUND);

		entityPostingEvent.fire(entity);

		Date conflictDt = entity.getConflictDt();
		getInjector().getBsnsObjEjb().validateBusinessObject(entity.getIdentif());
		// Conflict detected. Save and return
		if(conflictDt==null && entity.getConflictDt()!=null) return getInjector().getBsnsObjEjb().update(entity);
		
		// conflict still there. Just return
		if(entity.getConflictDt()!=null)return entity;
		return getInjector().getBsnsObjEjb().post(entity);
	}	
	
	public I addItem(String identif, I item) throws AdRestException {
		
		loadExistingBsnsObject(identif);
		
		if(!StringUtils.equals(identif, item.getCntnrIdentif()))
			throw new AdRestException(Response.Status.CONFLICT);
		
		item.setAcsngUser(callerPrincipal.getLoginName());
		
		item.evlte();
		
		return getInjector().getItemEjb().create(item);
	}
	
	public I updateTrgtQty(String identif, String itemIdentif, BigDecimal trgtQty, Date acsngDt)throws AdRestException {
		loadExistingBsnsObject(identif); 
		I existing = loadExistingBsnsItem(identif, itemIdentif);

		
		if(BigDecimalUtils.strictEquals(existing.getTrgtQty(), trgtQty) && 
				DateUtils.truncatedEquals(existing.getAcsngDt(),acsngDt, Calendar.SECOND))
			return existing;

		if(!BigDecimalUtils.strictEquals(trgtQty, existing.getTrgtQty())){
			existing.setTrgtQty(trgtQty);
		}
		if(acsngDt==null) acsngDt = new Date();
		if(!DateUtils.truncatedEquals(existing.getAcsngDt(),acsngDt, Calendar.SECOND)){
			existing.setAcsngDt(acsngDt);
		}
		String currentLoginName = callerPrincipal.getLoginName();
		existing.setAcsngUser(currentLoginName);
		existing.evlte();
		
		existing = getInjector().getItemEjb().update(existing);
		return existing;
	}
	
	public I updateExpirDt(String identif, String itemIdentif, Date expirDate) throws AdRestException {
		loadExistingBsnsObject(identif); 
		I existing = loadExistingBsnsItem(identif, itemIdentif);

		if(!DateUtils.isSameDay(expirDate, existing.getExpirDt()) && expirDate!=null){
			existing.setExpirDt(expirDate);
			existing = getInjector().getItemEjb().update(existing);
		}

		return existing;
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
	public I updateItem(String identif, String itemIdentif, I item) throws AdRestException {
		loadExistingBsnsObject(identif); 
		I existing = loadExistingBsnsItem(identif, itemIdentif);
		
		if(!StringUtils.equals(existing.getIdentif(),item.getIdentif()))
			throw new AdRestException(Response.Status.CONFLICT);

		if(!DateUtils.isSameInstant(item.getDisabledDt(), existing.getDisabledDt()))
			throw new IllegalStateException("Use disableItem/enableItem to change the status of an business item.");

		if(!BigDecimalUtils.strictEquals(item.getTrgtQty(), existing.getTrgtQty()))
			throw new IllegalStateException("Use updateTrgtQty To update the target quantity of this item");

		if(!DateUtils.isSameDay(item.getExpirDt(), existing.getExpirDt()))
			throw new IllegalStateException("Use updateExpirDt To update the target expiry date of this item");

		item.setId(existing.getId());
		return getInjector().getItemEjb().update(item);
	}

	public I disableItem(String identif, String itemIdentif, Date disabledDt) throws AdRestException {
		loadExistingBsnsObject(identif); 
		I existing = loadExistingBsnsItem(identif, itemIdentif);
		
		if(existing.getDisabledDt()==null){
			if(disabledDt==null) disabledDt=new Date();
			existing.setDisabledDt(disabledDt);
			existing = getInjector().getItemEjb().update(existing);
		}
		return existing;
	}

	public I enableItem(String identif, String itemIdentif) throws AdRestException {
		loadExistingBsnsObject(identif); 
		I existing = loadExistingBsnsItem(identif, itemIdentif);

		if(existing.getDisabledDt()!=null) {
			existing.setDisabledDt(null);
			existing = getInjector().getItemEjb().update(existing);
		}
		return existing;
	}
	
	private E loadExistingBsnsObject(String identif) throws AdRestException{
		E existing = getInjector().getBsnsObjLookup().findByIdentif(identif);
		if(existing==null) throw new AdRestException(Response.Status.NOT_FOUND);
		return existing;
	}
	
	private I loadExistingBsnsItem(String identif, String itemIdentif) throws AdRestException{
		I item = getInjector().getItemLookup().findByIdentif(itemIdentif);
		
		if(item==null) throw new AdRestException(Response.Status.NOT_FOUND);
		
		if(!StringUtils.equals(identif, item.getCntnrIdentif()))
			throw new AdRestException(Response.Status.CONFLICT);
		
		return item;
	}
}