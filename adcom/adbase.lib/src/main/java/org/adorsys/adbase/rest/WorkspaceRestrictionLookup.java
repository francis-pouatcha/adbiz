package org.adorsys.adbase.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.WorkspaceRestriction;
import org.adorsys.adbase.repo.WorkspaceRestrictionRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;

@Stateless
public class WorkspaceRestrictionLookup extends
		CoreAbstIdentifLookup<WorkspaceRestriction> {
	@Inject
	private WorkspaceRestrictionRepository repository;

	@Override
	protected CoreAbstIdentifRepo<WorkspaceRestriction> getRepo() {
		return repository;
	}

	@Override
	protected Class<WorkspaceRestriction> getEntityClass() {
		return WorkspaceRestriction.class;
	}
}
