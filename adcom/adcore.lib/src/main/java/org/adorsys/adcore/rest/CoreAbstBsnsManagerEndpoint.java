package org.adorsys.adcore.rest;

import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.adorsys.adcore.exceptions.AdException;
import org.adorsys.adcore.exceptions.AdRestException;
import org.adorsys.adcore.jpa.CoreAbstBsnsItem;
import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchInput;
import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchResult;
import org.adorsys.adcore.jpa.CoreAbstBsnsObject;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectHstry;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstEntityCstr;
import org.adorsys.adcore.jpa.CoreAbstEntityJob;
import org.adorsys.adcore.jpa.CoreAbstEntityStep;

public abstract class CoreAbstBsnsManagerEndpoint<E extends CoreAbstBsnsObject, I extends CoreAbstBsnsItem, H extends CoreAbstBsnsObjectHstry, J extends CoreAbstEntityJob, S extends CoreAbstEntityStep, C extends CoreAbstEntityCstr> {

	protected abstract CoreAbstBsnsObjectManager<E, I, H, J, S, C, CoreAbstBsnsObjectSearchInput<E>> getBsnsObjManager();

	protected abstract CoreAbstBsnsObjInjector<E, I, H, J, S, C> getInjector();
	protected abstract CoreAbstBsnsItemSearchInput<I> newItemSearchInput();
	protected abstract CoreAbstBsnsItemSearchResult<I> newItemSearchResult(long count, List<I> resultList, CoreAbstBsnsItemSearchInput<I> itemSearchInput);

	@POST
	@Consumes({ "application/json"})
	@Produces({ "application/json"})
	public E createBsnsObj(E bsnsObj) {
		return getBsnsObjManager().initiateBsnsObj(bsnsObj);
	}

	@PUT
	@Path("/{identif}")
	@Produces({ "application/json"})
	@Consumes({ "application/json"})
	public E updateBsnsObj(E bsnsObj, @PathParam("identif") String identif) throws AdRestException {
		return getBsnsObjManager().update(identif, bsnsObj);
	}

	@PUT
	@Path("/{identif}/close")
	@Produces({ "application/json"})
	public E closeBsnsObj(@PathParam("identif") String identif) throws AdRestException {
		return getBsnsObjManager().close(identif);
	}

	@PUT
	@Path("/{identif}/validate")
	@Produces({ "application/json"})
	public E validateBsnsObj(@PathParam("identif") String identif) throws AdRestException{
		return getBsnsObjManager().validate(identif);
	}

	@PUT
	@Path("/{identif}/post")
	@Consumes({ "application/json"})
	@Produces({ "application/json"})
	public E postBsnsObj(@PathParam("identif") String identif) throws AdRestException{
		return getBsnsObjManager().post(identif);
	}

	@POST
	@Path("/{identif}/items")
	@Consumes({ "application/json"})
	@Produces({ "application/json"})
	public I addItem(@PathParam("identif") String identif, I item) throws AdException {
		return getBsnsObjManager().addItem(identif, item);
	}

	@PUT
	@Path("/{identif}/items/{itemIdentif}")
	@Consumes({ "application/json"})
	@Produces({ "application/json"})
	public I updateItem(@PathParam("identif") String identif, @PathParam("itemIdentif") String itemIdentif, I item) throws AdException {
		return getBsnsObjManager().updateItem(identif, itemIdentif, item);
	}

	@PUT
	@Path("/{identif}/items/{itemIdentif}/disable")
	@Consumes({ "application/json"})
	@Produces({ "application/json"})
	public I disableItem(@PathParam("identif") String identif, 
			@PathParam("itemIdentif") String itemIdentif) throws AdRestException {
		I item = getBsnsObjManager().disableItem(identif, itemIdentif, new Date());
		return item;
		//return findByCntnrIdentifAndSalIndex(item);
	}

	@PUT
	@Path("/{identif}/items/{itemIdentif}/enable")
	@Consumes({ "application/json"})
	@Produces({ "application/json"})
	public I enableItem(@PathParam("identif") String identif, @PathParam("itemIdentif") String itemIdentif) throws AdException {
		I item = getBsnsObjManager().enableItem(identif, itemIdentif);
		return item;
		//return findByCntnrIdentifAndSalIndex(item);
	}

	private CoreAbstBsnsItemSearchResult<I> findByCntnrIdentifAndSalIndex(I item) {
		int count = getInjector().getItemLookup().countByCntnrIdentifAndSalIndex(
				item.getCntnrIdentif(), item.getSalIndex()).intValue();
		
		List<I> resultList = getInjector().getItemLookup()
				.findByCntnrIdentifAndSalIndex(item.getCntnrIdentif(),item.getSalIndex(), 0, 100);
		
		CoreAbstBsnsItemSearchInput<I> searchInput = newItemSearchInput();

		searchInput.setEntity(item);
		searchInput.setMax(100);
		searchInput.setStart(0);
		searchInput.getFieldNames().add("cntnrIdentif");
		searchInput.getFieldNames().add("salIndex");
		
		return newItemSearchResult(count, resultList, searchInput);
	}
	
	@PUT
	@Path("/{identif}/items/{itemIdentif}/targetQty")
	@Consumes({ "application/json"})
	@Produces({ "application/json"})
	public I updatetargetQty(@PathParam("identif") String identif, @PathParam("itemIdentif") String itemIdentif, I item) throws AdException {
		return getBsnsObjManager().updateTrgtQty(identif, itemIdentif, item.getTrgtQty(), item.getAcsngDt());
	}

}