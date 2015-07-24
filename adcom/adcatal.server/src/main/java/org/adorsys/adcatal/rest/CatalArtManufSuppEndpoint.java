package org.adorsys.adcatal.rest;

import java.lang.reflect.Field;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.adorsys.adcatal.jpa.CatalArtManufSupp;
import org.adorsys.adcatal.jpa.CatalArtManufSuppSearchInput;
import org.adorsys.adcatal.jpa.CatalArtManufSuppSearchResult;
import org.adorsys.adcatal.jpa.CatalArtManufSupp_;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEndpoint;
import org.adorsys.adcore.rest.CoreAbstIdentifiedLookup;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/catalartmanufsupps")
public class CatalArtManufSuppEndpoint extends
		CoreAbstIdentifiedEndpoint<CatalArtManufSupp> {

	@Inject
	private CatalArtManufSuppEJB ejb;
	@Inject
	private CatalArtManufSuppLookup lookup;

	@Override
	protected CoreAbstIdentifiedLookup<CatalArtManufSupp> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<CatalArtManufSupp> getEjb() {
		return ejb;
	}

	@Override
	protected Field[] getEntityFields() {
		return CatalArtManufSupp_.class.getFields();
	}

	@Override
	protected CoreAbstIdentifObjectSearchInput<CatalArtManufSupp> newSearchInput() {
		return new CatalArtManufSuppSearchInput();
	}

	@Override
	protected CoreAbstIdentifObjectSearchResult<CatalArtManufSupp> newSearchResult(
			Long count, Long total,List<CatalArtManufSupp> resultList,
			CoreAbstIdentifObjectSearchInput<CatalArtManufSupp> searchInput) {
		return new CatalArtManufSuppSearchResult(count, total, resultList, searchInput);
	}
}