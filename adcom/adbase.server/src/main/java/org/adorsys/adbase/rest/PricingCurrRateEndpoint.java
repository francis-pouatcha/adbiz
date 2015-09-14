package org.adorsys.adbase.rest;

import java.lang.reflect.Field;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.adorsys.adbase.jpa.PricingCurrRate;
import org.adorsys.adbase.jpa.PricingCurrRateSearchInput;
import org.adorsys.adbase.jpa.PricingCurrRateSearchResult;
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
@Path("/pricingcurrrates")
public class PricingCurrRateEndpoint extends
		CoreAbstIdentifiedEndpoint<PricingCurrRate> {

	@Inject
	private PricingCurrRateEJB ejb;
	@Inject
	private PricingCurrRateLookup lookup;

	@Override
	protected CoreAbstIdentifLookup<PricingCurrRate> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<PricingCurrRate> getEjb() {
		return ejb;
	}

	@Override
	protected Field[] getEntityFields() {
		return PricingCurrRate.class.getFields();
	}

	@Override
	protected CoreAbstIdentifObjectSearchInput<PricingCurrRate> newSearchInput() {
		return new PricingCurrRateSearchInput();
	}

	@Override
	protected CoreAbstIdentifObjectSearchResult<PricingCurrRate> newSearchResult(
			Long count, Long total, List<PricingCurrRate> resultList,
			CoreAbstIdentifObjectSearchInput<PricingCurrRate> searchInput) {
		return new PricingCurrRateSearchResult(count, total, resultList,
				searchInput);
	}
}