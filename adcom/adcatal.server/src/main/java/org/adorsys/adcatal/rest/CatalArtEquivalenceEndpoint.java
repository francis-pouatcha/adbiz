package org.adorsys.adcatal.rest;

import java.lang.reflect.Field;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.adorsys.adcatal.jpa.CatalArtEquivalence;
import org.adorsys.adcatal.jpa.CatalArtEquivalenceSearchInput;
import org.adorsys.adcatal.jpa.CatalArtEquivalenceSearchResult;
import org.adorsys.adcatal.jpa.CatalArtEquivalence_;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEndpoint;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/catalartequivalences")
public class CatalArtEquivalenceEndpoint extends
		CoreAbstIdentifiedEndpoint<CatalArtEquivalence> {

	@Inject
	private CatalArtEquivalenceEJB ejb;
	@Inject
	private CatalArtEquivalenceLookup lookup;
	@Override
	protected CoreAbstIdentifLookup<CatalArtEquivalence> getLookup() {
		return lookup;
	}
	@Override
	protected CoreAbstIdentifiedEJB<CatalArtEquivalence> getEjb() {
		return ejb;
	}
	@Override
	protected Field[] getEntityFields() {
		return CatalArtEquivalence_.class.getFields();
	}
	@Override
	protected CoreAbstIdentifObjectSearchInput<CatalArtEquivalence> newSearchInput() {
		return new CatalArtEquivalenceSearchInput();
	}
	@Override
	protected CoreAbstIdentifObjectSearchResult<CatalArtEquivalence> newSearchResult(
			Long count, List<CatalArtEquivalence> resultList,
			CoreAbstIdentifObjectSearchInput<CatalArtEquivalence> searchInput) {
		return new CatalArtEquivalenceSearchResult(count, resultList, searchInput);
	}

}