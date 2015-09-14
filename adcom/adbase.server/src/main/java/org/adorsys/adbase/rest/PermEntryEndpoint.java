package org.adorsys.adbase.rest;

import java.lang.reflect.Field;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.adorsys.adbase.jpa.PermEntry;
import org.adorsys.adbase.jpa.PermEntrySearchInput;
import org.adorsys.adbase.jpa.PermEntrySearchResult;
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
@Path("/permentrys")
public class PermEntryEndpoint extends CoreAbstIdentifiedEndpoint<PermEntry> {

	@Inject
	private PermEntryEJB ejb;
	@Inject
	private PermEntryLookup lookup;

	@Override
	protected CoreAbstIdentifLookup<PermEntry> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<PermEntry> getEjb() {
		return ejb;
	}

	@Override
	protected Field[] getEntityFields() {
		return PermEntry.class.getFields();
	}

	@Override
	protected CoreAbstIdentifObjectSearchInput<PermEntry> newSearchInput() {
		return new PermEntrySearchInput();
	}

	@Override
	protected CoreAbstIdentifObjectSearchResult<PermEntry> newSearchResult(
			Long count, Long total, List<PermEntry> resultList,
			CoreAbstIdentifObjectSearchInput<PermEntry> searchInput) {
		return new PermEntrySearchResult(count, total, resultList, searchInput);
	}

}