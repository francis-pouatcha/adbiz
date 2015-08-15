package org.adorsys.adcost.rest;

import java.lang.reflect.Field;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEndpoint;
import org.adorsys.adcost.ejb.CstBearerEJB;
import org.adorsys.adcost.ejb.CstBearerLookup;
import org.adorsys.adcost.jpa.CstBearer;
import org.adorsys.adcost.jpa.CstBearerSearchInput;
import org.adorsys.adcost.jpa.CstBearerSearchResult;
import org.adorsys.adcost.jpa.CstBearer_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/cstBearers")
public class CstBearerEndpoint extends CoreAbstIdentifiedEndpoint<CstBearer> {

	@Inject
	private CstBearerEJB ejb;

	@Inject
	private CstBearerLookup lookup;

	@Override
	protected CoreAbstIdentifLookup<CstBearer> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<CstBearer> getEjb() {
		return ejb;
	}

	@Override
	protected Field[] getEntityFields() {
		return CstBearer_.class.getFields();
	}

	@Override
	protected CoreAbstIdentifObjectSearchInput<CstBearer> newSearchInput() {
		return new CstBearerSearchInput();
	}

	@Override
	protected CoreAbstIdentifObjectSearchResult<CstBearer> newSearchResult(
			Long count, Long total, List<CstBearer> resultList,
			CoreAbstIdentifObjectSearchInput<CstBearer> searchInput) {
		return new CstBearerSearchResult(count, total, resultList, searchInput);
	}

}