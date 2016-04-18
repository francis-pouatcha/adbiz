package org.adorsys.adcshdwr.rest;

import java.lang.reflect.Field;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEndpoint;
import org.adorsys.adcshdwr.jpa.CdrCstmrVchr;
import org.adorsys.adcshdwr.jpa.CdrCstmrVchrSearchInput;
import org.adorsys.adcshdwr.jpa.CdrCstmrVchrSearchResult;
import org.adorsys.adcshdwr.jpa.CdrCstmrVchr_;


/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/cdrcstmrvchrs")
public class CdrCstmrVchrEndpoint extends CoreAbstIdentifiedEndpoint<CdrCstmrVchr> {

	@Inject
	private CdrCstmrVchrEJB ejb;
	@Inject
	private CdrCstmrVchrLookup lookup;
	
	@PUT
	@Path("/cancel/{id}")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public CdrCstmrVchr cancel(CdrCstmrVchr entity) {
		return detach(ejb.cancel(entity));
	}

	@Override
	protected CoreAbstIdentifiedEJB<CdrCstmrVchr> getEjb() {
		return ejb;
	}

	@Override
	protected CoreAbstIdentifLookup<CdrCstmrVchr> getLookup() {
		return lookup;
	}

	@Override
	protected Field[] getEntityFields() {
		return CdrCstmrVchr_.class.getFields();
	}

	@Override
	protected CoreAbstIdentifObjectSearchInput<CdrCstmrVchr> newSearchInput() {
		return new CdrCstmrVchrSearchInput();
	}

	@Override
	protected CoreAbstIdentifObjectSearchResult<CdrCstmrVchr> newSearchResult(Long count, Long total,
			List<CdrCstmrVchr> resultList, CoreAbstIdentifObjectSearchInput<CdrCstmrVchr> searchInput) {
		return new CdrCstmrVchrSearchResult(count, total, resultList, searchInput);
	}
}