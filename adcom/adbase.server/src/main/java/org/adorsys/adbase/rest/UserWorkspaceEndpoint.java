package org.adorsys.adbase.rest;

import java.lang.reflect.Field;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.adorsys.adbase.jpa.UserWorkspace;
import org.adorsys.adbase.jpa.UserWorkspaceSearchInput;
import org.adorsys.adbase.jpa.UserWorkspaceSearchResult;
import org.adorsys.adbase.jpa.UserWorkspace_;
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
@Path("/userworkspaces")
public class UserWorkspaceEndpoint extends
		CoreAbstIdentifiedEndpoint<UserWorkspace> {

	@Inject
	private UserWorkspaceEJB ejb;

	@Inject
	private UserWorkspaceLookup lookup;

	@Override
	protected CoreAbstIdentifLookup<UserWorkspace> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<UserWorkspace> getEjb() {
		return ejb;
	}

	@Override
	protected Field[] getEntityFields() {
		return UserWorkspace_.class.getFields();
	}

	@Override
	protected CoreAbstIdentifObjectSearchInput<UserWorkspace> newSearchInput() {
		return new UserWorkspaceSearchInput();
	}

	@Override
	protected CoreAbstIdentifObjectSearchResult<UserWorkspace> newSearchResult(
			Long count, Long total, List<UserWorkspace> resultList,
			CoreAbstIdentifObjectSearchInput<UserWorkspace> searchInput) {
		return new UserWorkspaceSearchResult(count, total, resultList,
				searchInput);
	}

}