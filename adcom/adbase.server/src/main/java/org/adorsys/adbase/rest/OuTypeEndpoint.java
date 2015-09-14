package org.adorsys.adbase.rest;

import java.lang.reflect.Field;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.adorsys.adbase.jpa.OuType;
import org.adorsys.adbase.jpa.OuTypeSearchInput;
import org.adorsys.adbase.jpa.OuTypeSearchResult;
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
@Path("/outypes")
public class OuTypeEndpoint extends CoreAbstIdentifiedEndpoint<OuType> {

	@Inject
	private OuTypeEJB ejb;
	@Inject
	private OuTypeLookup lookup;

	@Override
	protected CoreAbstIdentifLookup<OuType> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<OuType> getEjb() {
		return ejb;
	}

	@Override
	protected Field[] getEntityFields() {
		return OuType.class.getFields();
	}

	@Override
	protected CoreAbstIdentifObjectSearchInput<OuType> newSearchInput() {
		return new OuTypeSearchInput();
	}

	@Override
	protected CoreAbstIdentifObjectSearchResult<OuType> newSearchResult(
			Long count, Long total, List<OuType> resultList,
			CoreAbstIdentifObjectSearchInput<OuType> searchInput) {
		return new OuTypeSearchResult(count, total, resultList, searchInput);
	}
}