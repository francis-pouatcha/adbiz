package org.adorsys.adbase.rest;

import java.lang.reflect.Field;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.adorsys.adbase.jpa.OuWorkspace;
import org.adorsys.adbase.jpa.OuWorkspaceSearchInput;
import org.adorsys.adbase.jpa.OuWorkspaceSearchResult;
import org.adorsys.adbase.jpa.OuWorkspace_;
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
@Path("/ouworkspaces")
public class OuWorkspaceEndpoint extends
		CoreAbstIdentifiedEndpoint<OuWorkspace> {

	@Inject
	private OuWorkspaceEJB ejb;
	@Inject
	private OuWorkspaceLookup lookup;

	@Override
	protected CoreAbstIdentifLookup<OuWorkspace> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<OuWorkspace> getEjb() {
		return ejb;
	}

	@Override
	protected Field[] getEntityFields() {
		return OuWorkspace_.class.getFields();
	}

	@Override
	protected CoreAbstIdentifObjectSearchInput<OuWorkspace> newSearchInput() {
		return new OuWorkspaceSearchInput();
	}

	@Override
	protected CoreAbstIdentifObjectSearchResult<OuWorkspace> newSearchResult(
			Long count, Long total, List<OuWorkspace> resultList,
			CoreAbstIdentifObjectSearchInput<OuWorkspace> searchInput) {
		return new OuWorkspaceSearchResult(count, total, resultList,
				searchInput);
	}
}