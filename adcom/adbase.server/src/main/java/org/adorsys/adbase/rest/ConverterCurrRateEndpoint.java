package org.adorsys.adbase.rest;

import java.lang.reflect.Field;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.adorsys.adbase.jpa.ConverterCurrRate;
import org.adorsys.adbase.jpa.ConverterCurrRateSearchInput;
import org.adorsys.adbase.jpa.ConverterCurrRateSearchResult;
import org.adorsys.adbase.jpa.ConverterCurrRate_;
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
@Path("/convertercurrrates")
public class ConverterCurrRateEndpoint extends CoreAbstIdentifiedEndpoint<ConverterCurrRate>{

	@Inject
	private ConverterCurrRateEJB ejb;
	@Inject
	private ConverterCurrRateLookup lookup;

	@Override
	protected CoreAbstIdentifLookup<ConverterCurrRate> getLookup() {
		return lookup;
	}
	@Override
	protected CoreAbstIdentifiedEJB<ConverterCurrRate> getEjb() {
		return ejb;
	}
	@Override
	protected Field[] getEntityFields() {
		return ConverterCurrRate_.class.getFields();
	}
	@Override
	protected CoreAbstIdentifObjectSearchInput<ConverterCurrRate> newSearchInput() {
		return new ConverterCurrRateSearchInput();
	}
	@Override
	protected CoreAbstIdentifObjectSearchResult<ConverterCurrRate> newSearchResult(
			Long count, Long total, List<ConverterCurrRate> resultList,
			CoreAbstIdentifObjectSearchInput<ConverterCurrRate> searchInput) {
		return new ConverterCurrRateSearchResult(count, total, resultList, searchInput);
	}

}