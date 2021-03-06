package org.adorsys.adcatal.rest;

import java.lang.reflect.Field;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.adorsys.adcatal.jpa.CatalArtDetailConfig;
import org.adorsys.adcatal.jpa.CatalArtDetailConfigSearchInput;
import org.adorsys.adcatal.jpa.CatalArtDetailConfigSearchResult;
import org.adorsys.adcatal.jpa.CatalArtDetailConfig_;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEndpoint;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/catalartdetailconfigs")
public class CatalArtDetailConfigEndpoint extends
		CoreAbstIdentifiedEndpoint<CatalArtDetailConfig> {

	@Inject
	private CatalArtDetailConfigEJB ejb;
	@Inject
	private CatalArtDetailConfigLookup lookup;

	@Override
	protected CoreAbstIdentifLookup<CatalArtDetailConfig> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<CatalArtDetailConfig> getEjb() {
		return ejb;
	}

	@Override
	protected Field[] getEntityFields() {
		return CatalArtDetailConfig_.class.getFields();
	}

	@Override
	protected CoreAbstIdentifObjectSearchInput<CatalArtDetailConfig> newSearchInput() {
		return new CatalArtDetailConfigSearchInput();
	}

	@Override
	protected CoreAbstIdentifObjectSearchResult<CatalArtDetailConfig> newSearchResult(
			Long count, Long total,List<CatalArtDetailConfig> resultList,
			CoreAbstIdentifObjectSearchInput<CatalArtDetailConfig> searchInput) {
		return new CatalArtDetailConfigSearchResult(count, total, resultList, searchInput);
	}

}