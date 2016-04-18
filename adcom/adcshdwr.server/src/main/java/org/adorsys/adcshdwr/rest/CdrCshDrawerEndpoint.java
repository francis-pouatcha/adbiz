package org.adorsys.adcshdwr.rest;

import java.lang.reflect.Field;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.adorsys.adcore.exceptions.AdException;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEndpoint;
import org.adorsys.adcshdwr.api.CdrCshDrawerManager;
import org.adorsys.adcshdwr.jpa.CdrCshDrawer;
import org.adorsys.adcshdwr.jpa.CdrCshDrawerSearchInput;
import org.adorsys.adcshdwr.jpa.CdrCshDrawerSearchResult;
import org.adorsys.adcshdwr.jpa.CdrCshDrawer_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/cdrcshdrawers")
public class CdrCshDrawerEndpoint extends CoreAbstIdentifiedEndpoint<CdrCshDrawer> {

	@Inject
	private CdrCshDrawerEJB ejb;
	@Inject
	private CdrCshDrawerLookup lookup;
	@Inject
	private CdrCshDrawerManager manager;
	
	@POST
	@Path("/openCshDrawer")
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public CdrCshDrawer openCshDrawer(CdrCshDrawer cshDrawer) {
		cshDrawer = manager.openCshDrawer(cshDrawer);
		return cshDrawer;
	}

	@POST
	@Path("/closeCshDrawer")
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public CdrCshDrawer closeCshDrawer(CdrCshDrawer cshDrawer) throws AdException {
		cshDrawer = manager.closeCshDrawer(cshDrawer);
		return cshDrawer;
	}

	@GET
	@Path("/getActive")
	@Produces({ "application/json", "application/xml" })
	public CdrCshDrawer findActive() throws AdException {
		CdrCshDrawer cdrCshDrawer = manager.getActiveCshDrawer();
		return cdrCshDrawer;
	}

	@GET
	@Path("/findPreviousCdrCshDrawer")
	@Produces({ "application/json", "application/xml" })
	public List<CdrCshDrawer> findPreviousCdrCshDrawer() {
		List<CdrCshDrawer> cdrCshDrawers = manager.findPreviousCdrCshDrawer();
		return cdrCshDrawers;
	}

	@Override
	protected CoreAbstIdentifiedEJB<CdrCshDrawer> getEjb() {
		return ejb;
	}

	@Override
	protected CoreAbstIdentifLookup<CdrCshDrawer> getLookup() {
		return lookup;
	}

	@Override
	protected Field[] getEntityFields() {
		return CdrCshDrawer_.class.getFields();
	}

	@Override
	protected CoreAbstIdentifObjectSearchInput<CdrCshDrawer> newSearchInput() {
		return new CdrCshDrawerSearchInput();
	}

	@Override
	protected CoreAbstIdentifObjectSearchResult<CdrCshDrawer> newSearchResult(Long count, Long total,
			List<CdrCshDrawer> resultList, CoreAbstIdentifObjectSearchInput<CdrCshDrawer> searchInput) {
		return new CdrCshDrawerSearchResult(count, total, resultList, searchInput);
	}
}