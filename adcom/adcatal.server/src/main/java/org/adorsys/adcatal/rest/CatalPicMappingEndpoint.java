package org.adorsys.adcatal.rest;

import java.lang.reflect.Field;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.adorsys.adcatal.jpa.CatalPicMapping;
import org.adorsys.adcatal.jpa.CatalPicMappingSearchInput;
import org.adorsys.adcatal.jpa.CatalPicMappingSearchResult;
import org.adorsys.adcatal.jpa.CatalPicMapping_;
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
@Path("/catalpicmappings")
public class CatalPicMappingEndpoint extends
		CoreAbstIdentifiedEndpoint<CatalPicMapping> {

	@Inject
	private CatalPicMappingEJB ejb;
	@Inject
	private CatalPicMappingLookup lookup;

	@Override
	protected CoreAbstIdentifLookup<CatalPicMapping> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<CatalPicMapping> getEjb() {
		return ejb;
	}

	@Override
	protected Field[] getEntityFields() {
		return CatalPicMapping_.class.getFields();
	}

	@Override
	protected CoreAbstIdentifObjectSearchInput<CatalPicMapping> newSearchInput() {
		return new CatalPicMappingSearchInput();
	}

	@Override
	protected CoreAbstIdentifObjectSearchResult<CatalPicMapping> newSearchResult(
			Long count, Long total,List<CatalPicMapping> resultList,
			CoreAbstIdentifObjectSearchInput<CatalPicMapping> searchInput) {
		return new CatalPicMappingSearchResult(count, total, resultList, searchInput);
	}
}