package org.adorsys.adcost.rest;

import java.lang.reflect.Field;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEndpoint;
import org.adorsys.adcost.ejb.CstActivityCenterEJB;
import org.adorsys.adcost.ejb.CstActivityCenterLookup;
import org.adorsys.adcost.jpa.CstActivityCenter;
import org.adorsys.adcost.jpa.CstActivityCenterSearchInput;
import org.adorsys.adcost.jpa.CstActivityCenterSearchResult;
import org.adorsys.adcost.jpa.CstActivityCenter_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/cstActivityCenters")
public class CstActivityCenterEndpoint  extends CoreAbstIdentifiedEndpoint<CstActivityCenter>{

	@Inject
	private CstActivityCenterEJB ejb;
	@Inject
	private CstActivityCenterLookup lookup;

	@Override
	protected CoreAbstIdentifLookup<CstActivityCenter> getLookup() {
		return lookup;
	}
	@Override
	protected CoreAbstIdentifiedEJB<CstActivityCenter> getEjb() {
		return ejb;
	}
	@Override
	protected Field[] getEntityFields() {
		return CstActivityCenter_.class.getFields();
	}
	@Override
	protected CoreAbstIdentifObjectSearchInput<CstActivityCenter> newSearchInput() {
		return new CstActivityCenterSearchInput();
	}
	@Override
	protected CoreAbstIdentifObjectSearchResult<CstActivityCenter> newSearchResult(
			Long count, Long total, List<CstActivityCenter> resultList,
			CoreAbstIdentifObjectSearchInput<CstActivityCenter> searchInput) {
		return new CstActivityCenterSearchResult(count, total, resultList, searchInput);
	}
}