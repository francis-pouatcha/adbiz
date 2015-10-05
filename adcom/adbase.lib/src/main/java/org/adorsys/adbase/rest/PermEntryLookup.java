package org.adorsys.adbase.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.PermEntry;
import org.adorsys.adbase.repo.PermEntryRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;

@Stateless
public class PermEntryLookup extends CoreAbstIdentifLookup<PermEntry> {
	@Inject
	private PermEntryRepository repository;

	@Override
	protected CoreAbstIdentifRepo<PermEntry> getRepo() {
		return repository;
	}

	@Override
	protected Class<PermEntry> getEntityClass() {
		return PermEntry.class;
	}
}
