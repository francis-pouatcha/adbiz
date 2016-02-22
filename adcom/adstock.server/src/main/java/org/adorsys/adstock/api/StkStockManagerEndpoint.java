package org.adorsys.adstock.api;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.adorsys.adcore.exceptions.AdException;
import org.adorsys.adstock.jpa.StkMvnt;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/stock")
public class StkStockManagerEndpoint  {

	@Inject
	private StkStockManager stockManager;

	/**
	 * Create a new stock movement.
	 * 
	 * @param identif
	 * @param mvnt
	 * @return
	 * @throws AdException
	 */
	@POST
	@Path("/moveIn")
	@Consumes({ "application/json"})
	@Produces({ "application/json"})
	public StkMvnt moveIn(StkMvnt stkMvnt) throws AdException {
		return stockManager.moveIn(stkMvnt);
	}
	
	@POST
	@Path("/moveOut")
	@Consumes({ "application/json"})
	@Produces({ "application/json"})
	public StkMvnt moveOut(StkMvnt stkMvnt) throws AdException {
		return stockManager.moveOut(stkMvnt);
	}
}