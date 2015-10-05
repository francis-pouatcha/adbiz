package org.adorsys.adbase.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.OuWorkspace;
import org.adorsys.adbase.repo.OuWorkspaceRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;

@Stateless
public class OuWorkspaceLookup extends CoreAbstIdentifLookup<OuWorkspace> {

	@Inject
	private OuWorkspaceRepository repository;

	@Override
	protected CoreAbstIdentifRepo<OuWorkspace> getRepo() {
		return repository;
	}

	@Override
	protected Class<OuWorkspace> getEntityClass() {
		return OuWorkspace.class;
	}
}
