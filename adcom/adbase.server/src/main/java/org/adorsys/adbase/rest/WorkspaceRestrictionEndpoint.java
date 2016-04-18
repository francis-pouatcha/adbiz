package org.adorsys.adbase.rest;

import java.lang.reflect.Field;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.adorsys.adbase.jpa.WorkspaceRestriction;
import org.adorsys.adbase.jpa.WorkspaceRestrictionSearchInput;
import org.adorsys.adbase.jpa.WorkspaceRestrictionSearchResult;
import org.adorsys.adbase.jpa.WorkspaceRestriction_;
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
@Path("/workspacerestrictions")
public class WorkspaceRestrictionEndpoint extends
		CoreAbstIdentifiedEndpoint<WorkspaceRestriction> {

	@Inject
	private WorkspaceRestrictionEJB ejb;

	@Inject
	private WorkspaceRestrictionLookup lookup;

	@Override
	protected CoreAbstIdentifLookup<WorkspaceRestriction> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<WorkspaceRestriction> getEjb() {
		return ejb;
	}

	@Override
	protected Field[] getEntityFields() {
		return WorkspaceRestriction_.class.getFields();
	}

	@Override
	protected CoreAbstIdentifObjectSearchInput<WorkspaceRestriction> newSearchInput() {
		return new WorkspaceRestrictionSearchInput();
	}

	@Override
	protected CoreAbstIdentifObjectSearchResult<WorkspaceRestriction> newSearchResult(
			Long count, Long total, List<WorkspaceRestriction> resultList,
			CoreAbstIdentifObjectSearchInput<WorkspaceRestriction> searchInput) {
		return new WorkspaceRestrictionSearchResult(count, total, resultList, searchInput);
	}
}