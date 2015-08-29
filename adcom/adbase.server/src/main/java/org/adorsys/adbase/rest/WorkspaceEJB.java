package org.adorsys.adbase.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.Workspace;
import org.adorsys.adbase.repo.WorkspaceRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;

@Stateless
public class WorkspaceEJB extends CoreAbstIdentifiedEJB<Workspace> {

	@Inject
	private WorkspaceRepository repo;

	@Override
	protected CoreAbstIdentifRepo<Workspace> getRepo() {
		return repo;
	}
}
