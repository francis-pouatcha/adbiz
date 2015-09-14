package org.adorsys.adbase.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.UserWorkspace;
import org.adorsys.adbase.repo.UserWorkspaceRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;

@Stateless
public class UserWorkspaceEJB extends CoreAbstIdentifiedEJB<UserWorkspace> {

	@Inject
	private UserWorkspaceRepository repository;

	@Override
	protected CoreAbstIdentifRepo<UserWorkspace> getRepo() {
		return repository;
	}

}
