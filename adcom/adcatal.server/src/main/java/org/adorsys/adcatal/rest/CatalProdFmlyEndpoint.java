package org.adorsys.adcatal.rest;

import java.lang.reflect.Field;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.adorsys.adcatal.jpa.CatalProdFmly;
import org.adorsys.adcatal.jpa.CatalProdFmlySearchInput;
import org.adorsys.adcatal.jpa.CatalProdFmlySearchResult;
import org.adorsys.adcatal.jpa.CatalProdFmly_;
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
@Path("/catalproductfamilys")
public class CatalProdFmlyEndpoint extends
		CoreAbstIdentifiedEndpoint<CatalProdFmly> {

	@Inject
	private CatalProdFmlyEJB ejb;
	@Inject
	private CatalProdFmlyLookup lookup;

	@Override
	protected CoreAbstIdentifiedLookup<CatalProdFmly> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<CatalProdFmly> getEjb() {
		return ejb;
	}

	@Override
	protected Field[] getEntityFields() {
		return CatalProdFmly_.class.getFields();
	}

	@Override
	protected CoreAbstIdentifObjectSearchInput<CatalProdFmly> newSearchInput() {
		return new CatalProdFmlySearchInput();
	}

	@Override
	protected CoreAbstIdentifObjectSearchResult<CatalProdFmly> newSearchResult(
			Long count,Long total, List<CatalProdFmly> resultList,
			CoreAbstIdentifObjectSearchInput<CatalProdFmly> searchInput) {
		return new CatalProdFmlySearchResult(count, total, resultList, searchInput);
	}
}