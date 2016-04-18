package org.adorsys.adbase.rest;

import java.lang.reflect.Field;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.adorsys.adbase.jpa.Workspace;
import org.adorsys.adbase.jpa.WorkspaceSearchInput;
import org.adorsys.adbase.jpa.WorkspaceSearchResult;
import org.adorsys.adbase.jpa.Workspace_;
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
@Path("/workspaces")
public class WorkspaceEndpoint extends CoreAbstIdentifiedEndpoint<Workspace> {

	@Inject
	private WorkspaceEJB ejb;
	@Inject
	private WorkspaceLookup lookup;
	
	@Override
	protected CoreAbstIdentifLookup<Workspace> getLookup() {
		return lookup;
	}
	@Override
	protected CoreAbstIdentifiedEJB<Workspace> getEjb() {
		return ejb;
	}
	@Override
	protected Field[] getEntityFields() {
		return Workspace_.class.getFields();
	}
	@Override
	protected CoreAbstIdentifObjectSearchInput<Workspace> newSearchInput() {
		return new WorkspaceSearchInput();
	}
	@Override
	protected CoreAbstIdentifObjectSearchResult<Workspace> newSearchResult(
			Long count, Long total, List<Workspace> resultList,
			CoreAbstIdentifObjectSearchInput<Workspace> searchInput) {
		return new WorkspaceSearchResult(count, total, resultList, searchInput);
	}

}