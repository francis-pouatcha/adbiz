package org.adorsys.adcore.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.adorsys.adcore.exceptions.AdException;
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
	protected abstract CoreAbstBsnsItemSearchResult<I, CoreAbstBsnsItemSearchInput<I>> newItemSearchResult(long count, List<I> resultList, CoreAbstBsnsItemSearchInput<I> itemSearchInput);

	@PUT
	@Path("/prepare")
	@Consumes({ "application/json"})
	@Produces({ "application/json"})
	public E newBsnsObj(E bsnsObj) {
		return getBsnsObjManager().initiateBsnsObj(bsnsObj);
	}

	@PUT
	@Path("/update")
	@Consumes({ "application/json"})
	@Produces({ "application/json"})
	public E updateBsnsObj(E bsnsObj) {
		return getBsnsObjManager().update(bsnsObj);
	}

	@PUT
	@Path("/close")
	@Consumes({ "application/json"})
	@Produces({ "application/json"})
	public E closeBsnsObj(E bsnsObj) {
		return getBsnsObjManager().close(bsnsObj);
	}

	@PUT
	@Path("/validate")
	@Consumes({ "application/json"})
	@Produces({ "application/json"})
	public E validateBsnsObj(E bsnsObj) {
		return getBsnsObjManager().validate(bsnsObj);
	}

	@PUT
	@Path("/post")
	@Consumes({ "application/json"})
	@Produces({ "application/json"})
	public E postBsnsObj(E bsnsObj) {
		return getBsnsObjManager().post(bsnsObj);
	}

	@PUT
	@Path("/addItem")
	@Consumes({ "application/json"})
	@Produces({ "application/json"})
	public I addItem(I item) throws AdException {
		return getBsnsObjManager().addItem(item);
	}

	@PUT
	@Path("/updateItem")
	@Consumes({ "application/json"})
	@Produces({ "application/json"})
	public I updateItem(I item) throws AdException {
		return getBsnsObjManager().updateItem(item);
	}

	@PUT
	@Path("/disableItem")
	@Consumes({ "application/json"})
	@Produces({ "application/json"})
	public CoreAbstBsnsItemSearchResult<I, CoreAbstBsnsItemSearchInput<I>> disableItem(I item) throws AdException {
		getBsnsObjManager().disableItem(item);
		return findByCntnrIdentifAndSalIndex(item);
	}

	@PUT
	@Path("/enableItem")
	@Consumes({ "application/json"})
	@Produces({ "application/json"})
	public CoreAbstBsnsItemSearchResult<I, CoreAbstBsnsItemSearchInput<I>> enableItem(I item) throws AdException {
		getBsnsObjManager().enableItem(item);
		return findByCntnrIdentifAndSalIndex(item);
	}

	private CoreAbstBsnsItemSearchResult<I, CoreAbstBsnsItemSearchInput<I>> findByCntnrIdentifAndSalIndex(I item) {
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
}