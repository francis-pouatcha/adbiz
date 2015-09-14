package org.adorsys.adbase.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.Locality;
import org.adorsys.adbase.repo.LocalityRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;

@Stateless
public class LocalityLookup extends CoreAbstIdentifLookup<Locality> {

	@Inject
	private LocalityRepository repository;

	@Override
	protected CoreAbstIdentifRepo<Locality> getRepo() {
		return repository;
	}

	@Override
	protected Class<Locality> getEntityClass() {
		return Locality.class;
	}
}
