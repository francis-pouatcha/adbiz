package org.adorsys.adcatal.rest;

import java.lang.reflect.Field;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.adorsys.adcatal.jpa.CatalProdFmlyLangMap;
import org.adorsys.adcatal.jpa.CatalProdFmlyLangMapSearchInput;
import org.adorsys.adcatal.jpa.CatalProdFmlyLangMapSearchResult;
import org.adorsys.adcatal.jpa.CatalProdFmlyLangMap_;
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
@Path("/catalfamilyfeatmapings")
public class CatalProdFmlyLangMapEndpoint extends
		CoreAbstIdentifiedEndpoint<CatalProdFmlyLangMap> {

	@Inject
	private CatalProdFmlyLangMapEJB ejb;
	@Inject
	private CatalProdFmlyLangMapLookup lookup;

	@Override
	protected CoreAbstIdentifiedLookup<CatalProdFmlyLangMap> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<CatalProdFmlyLangMap> getEjb() {
		return ejb;
	}

	@Override
	protected Field[] getEntityFields() {
		return CatalProdFmlyLangMap_.class.getFields();
	}

	@Override
	protected CoreAbstIdentifObjectSearchInput<CatalProdFmlyLangMap> newSearchInput() {
		return new CatalProdFmlyLangMapSearchInput();
	}

	@Override
	protected CoreAbstIdentifObjectSearchResult<CatalProdFmlyLangMap> newSearchResult(
			Long count, List<CatalProdFmlyLangMap> resultList,
			CoreAbstIdentifObjectSearchInput<CatalProdFmlyLangMap> searchInput) {
		return new CatalProdFmlyLangMapSearchResult(count, resultList, searchInput);
	}

}