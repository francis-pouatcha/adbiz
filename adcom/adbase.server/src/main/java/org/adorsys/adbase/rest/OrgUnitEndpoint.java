package org.adorsys.adbase.rest;

import java.lang.reflect.Field;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.adorsys.adbase.jpa.OrgUnit;
import org.adorsys.adbase.jpa.OrgUnitSearchResult;
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
@Path("/orgunits")
public class OrgUnitEndpoint extends CoreAbstIdentifiedEndpoint<OrgUnit> {

	@Inject
	private OrgUnitEJB ejb;

	@Inject
	private OrgUnitLookup lookup;

	@Override
	protected CoreAbstIdentifLookup<OrgUnit> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<OrgUnit> getEjb() {
		return ejb;
	}

	@Override
	protected Field[] getEntityFields() {
		return OrgUnit.class.getFields();
	}

	@Override
	protected CoreAbstIdentifObjectSearchInput<OrgUnit> newSearchInput() {
		return newSearchInput();
	}

	@Override
	protected CoreAbstIdentifObjectSearchResult<OrgUnit> newSearchResult(
			Long count, Long total, List<OrgUnit> resultList,
			CoreAbstIdentifObjectSearchInput<OrgUnit> searchInput) {
		return new OrgUnitSearchResult(count, total, resultList, searchInput);
	}
}