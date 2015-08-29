package org.adorsys.adbase.rest;

import java.lang.reflect.Field;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.adorsys.adbase.jpa.Country;
import org.adorsys.adbase.jpa.CountrySearchInput;
import org.adorsys.adbase.jpa.CountrySearchResult;
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
@Path("/countrys")
public class CountryEndpoint extends CoreAbstIdentifiedEndpoint<Country> {
	@Inject
	private CountryEJB ejb;
	@Inject
	private CountryLookup lookup;

	@Override
	protected CoreAbstIdentifLookup<Country> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<Country> getEjb() {
		return ejb;
	}

	@Override
	protected Field[] getEntityFields() {
		return Country.class.getFields();
	}

	@Override
	protected CoreAbstIdentifObjectSearchInput<Country> newSearchInput() {
		return new CountrySearchInput();
	}

	@Override
	protected CoreAbstIdentifObjectSearchResult<Country> newSearchResult(
			Long count, Long total, List<Country> resultList,
			CoreAbstIdentifObjectSearchInput<Country> searchInput) {
		return new CountrySearchResult(count, total, resultList, searchInput);
	}
}