package org.adorsys.adbase.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.WorkspaceRestriction;
import org.adorsys.adbase.repo.WorkspaceRestrictionRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;

@Stateless
public class WorkspaceRestrictionEJB extends
		CoreAbstIdentifiedEJB<WorkspaceRestriction> {

	@Inject
	private WorkspaceRestrictionRepository repo;

	@Override
	protected CoreAbstIdentifRepo<WorkspaceRestriction> getRepo() {
		return repo;
	}

}
