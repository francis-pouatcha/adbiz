package org.adorsys.adbase.rest;

import java.lang.reflect.Field;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.adorsys.adbase.jpa.Locality;
import org.adorsys.adbase.jpa.LocalitySearchInput;
import org.adorsys.adbase.jpa.LocalitySearchResult;
import org.adorsys.adbase.jpa.Locality_;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEndpoint;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/localitys")
public class LocalityEndpoint extends CoreAbstIdentifiedEndpoint<Locality> {
	@Inject
	private LocalityEJB ejb;
	@Inject
	private LocalityLookup lookup;

	@Override
	protected CoreAbstIdentifLookup<Locality> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<Locality> getEjb() {
		return ejb;
	}

	@Override
	protected Field[] getEntityFields() {
		return Locality_.class.getFields();
	}

	@Override
	protected CoreAbstIdentifObjectSearchInput<Locality> newSearchInput() {
		return new LocalitySearchInput();
	}

	@Override
	protected CoreAbstIdentifObjectSearchResult<Locality> newSearchResult(
			Long count, Long total, List<Locality> resultList,
			CoreAbstIdentifObjectSearchInput<Locality> searchInput) {
		return new LocalitySearchResult(count, total, resultList, searchInput);
	}
}