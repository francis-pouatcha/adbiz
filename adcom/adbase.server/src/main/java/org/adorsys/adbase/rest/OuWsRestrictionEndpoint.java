package org.adorsys.adbase.rest;

import java.lang.reflect.Field;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.adorsys.adbase.jpa.OuWsRestriction;
import org.adorsys.adbase.jpa.OuWsRestrictionSearchInput;
import org.adorsys.adbase.jpa.OuWsRestrictionSearchResult;
import org.adorsys.adbase.jpa.OuWsRestriction_;
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
@Path("/ouwsrestrictions")
public class OuWsRestrictionEndpoint extends
		CoreAbstIdentifiedEndpoint<OuWsRestriction> {

	@Inject
	private OuWsRestrictionEJB ejb;
	@Inject
	private OuWsRestrictionLookup lookup;

	@Override
	protected CoreAbstIdentifLookup<OuWsRestriction> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<OuWsRestriction> getEjb() {
		return ejb;
	}

	@Override
	protected Field[] getEntityFields() {
		return OuWsRestriction_.class.getFields();
	}

	@Override
	protected CoreAbstIdentifObjectSearchInput<OuWsRestriction> newSearchInput() {
		return new OuWsRestrictionSearchInput();
	}

	@Override
	protected CoreAbstIdentifObjectSearchResult<OuWsRestriction> newSearchResult(
			Long count, Long total, List<OuWsRestriction> resultList,
			CoreAbstIdentifObjectSearchInput<OuWsRestriction> searchInput) {
		return new OuWsRestrictionSearchResult(count, total, resultList,
				searchInput);
	}

}