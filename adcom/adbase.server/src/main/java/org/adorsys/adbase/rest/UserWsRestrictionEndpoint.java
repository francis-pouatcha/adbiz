package org.adorsys.adbase.rest;

import java.lang.reflect.Field;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.adorsys.adbase.jpa.UserWsRestriction;
import org.adorsys.adbase.jpa.UserWsRestrictionSearchInput;
import org.adorsys.adbase.jpa.UserWsRestrictionSearchResult;
import org.adorsys.adbase.jpa.UserWsRestriction_;
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
@Path("/userwsrestrictions")
public class UserWsRestrictionEndpoint extends
		CoreAbstIdentifiedEndpoint<UserWsRestriction> {

	@Inject
	private UserWsRestrictionEJB ejb;
	@Inject
	private UserWsRestrictionLookup lookup;

	@Override
	protected CoreAbstIdentifLookup<UserWsRestriction> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<UserWsRestriction> getEjb() {
		return ejb;
	}

	@Override
	protected Field[] getEntityFields() {
		return UserWsRestriction_.class.getFields();
	}

	@Override
	protected CoreAbstIdentifObjectSearchInput<UserWsRestriction> newSearchInput() {
		return new UserWsRestrictionSearchInput();
	}

	@Override
	protected CoreAbstIdentifObjectSearchResult<UserWsRestriction> newSearchResult(
			Long count, Long total, List<UserWsRestriction> resultList,
			CoreAbstIdentifObjectSearchInput<UserWsRestriction> searchInput) {
		return new UserWsRestrictionSearchResult(count, total, resultList, searchInput);
	}

}