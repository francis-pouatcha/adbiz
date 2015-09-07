package org.adorsys.adbase.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.Workspace;
import org.adorsys.adbase.repo.WorkspaceRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;

@Stateless
public class WorkspaceLookup extends CoreAbstIdentifLookup<Workspace> {
	@Inject
	private WorkspaceRepository repository;

	@Override
	protected CoreAbstIdentifRepo<Workspace> getRepo() {
		return repository;
	}

	@Override
	protected Class<Workspace> getEntityClass() {
		return Workspace.class;
	}
}
