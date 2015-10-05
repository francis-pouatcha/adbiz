package org.adorsys.adbase.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.RoleEntry;
import org.adorsys.adbase.repo.RoleEntryRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;

@Stateless
public class RoleEntryLookup extends CoreAbstIdentifLookup<RoleEntry> {

	@Inject
	private RoleEntryRepository repository;

	@Override
	protected CoreAbstIdentifRepo<RoleEntry> getRepo() {
		return repository;
	}

	@Override
	protected Class<RoleEntry> getEntityClass() {
		return RoleEntry.class;
	}
}
