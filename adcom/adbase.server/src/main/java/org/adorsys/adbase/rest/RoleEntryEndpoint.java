package org.adorsys.adbase.rest;

import java.lang.reflect.Field;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.adorsys.adbase.jpa.RoleEntry;
import org.adorsys.adbase.jpa.RoleEntrySearchInput;
import org.adorsys.adbase.jpa.RoleEntrySearchResult;
import org.adorsys.adbase.jpa.RoleEntry_;
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
@Path("/roleentrys")
public class RoleEntryEndpoint extends CoreAbstIdentifiedEndpoint<RoleEntry> {

	@Inject
	private RoleEntryEJB ejb;
	@Inject
	private RoleEntryLookup lookup;

	@Override
	protected CoreAbstIdentifLookup<RoleEntry> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<RoleEntry> getEjb() {
		return ejb;
	}

	@Override
	protected Field[] getEntityFields() {
		return RoleEntry_.class.getFields();
	}

	@Override
	protected CoreAbstIdentifObjectSearchInput<RoleEntry> newSearchInput() {
		return new RoleEntrySearchInput();
	}

	@Override
	protected CoreAbstIdentifObjectSearchResult<RoleEntry> newSearchResult(
			Long count, Long total, List<RoleEntry> resultList,
			CoreAbstIdentifObjectSearchInput<RoleEntry> searchInput) {
		return new RoleEntrySearchResult(count, total, resultList, searchInput);
	}
}