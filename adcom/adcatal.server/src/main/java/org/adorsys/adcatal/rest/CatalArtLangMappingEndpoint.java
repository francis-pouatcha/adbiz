package org.adorsys.adcatal.rest;

import java.lang.reflect.Field;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.adorsys.adcatal.jpa.CatalArtLangMapping;
import org.adorsys.adcatal.jpa.CatalArtLangMappingSearchInput;
import org.adorsys.adcatal.jpa.CatalArtLangMappingSearchResult;
import org.adorsys.adcatal.jpa.CatalArtLangMapping_;
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
@Path("/catalartfeatmappings")
public class CatalArtLangMappingEndpoint extends
		CoreAbstIdentifiedEndpoint<CatalArtLangMapping> {

	@Inject
	private CatalArtLangMappingEJB ejb;
	@Inject
	private CatalArtLangMappingLookup lookup;
	
	@Override
	protected CoreAbstIdentifLookup<CatalArtLangMapping> getLookup() {
		return lookup;
	}
	@Override
	protected CoreAbstIdentifiedEJB<CatalArtLangMapping> getEjb() {
		return ejb;
	}
	@Override
	protected Field[] getEntityFields() {
		return CatalArtLangMapping_.class.getFields();
	}
	@Override
	protected CoreAbstIdentifObjectSearchInput<CatalArtLangMapping> newSearchInput() {
		return new CatalArtLangMappingSearchInput();
	}
	@Override
	protected CoreAbstIdentifObjectSearchResult<CatalArtLangMapping> newSearchResult(
			Long count,Long total, List<CatalArtLangMapping> resultList,
			CoreAbstIdentifObjectSearchInput<CatalArtLangMapping> searchInput) {
		return new CatalArtLangMappingSearchResult(count, total, resultList, searchInput);
	}
}