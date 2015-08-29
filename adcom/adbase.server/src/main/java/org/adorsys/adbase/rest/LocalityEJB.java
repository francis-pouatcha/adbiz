package org.adorsys.adbase.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.Locality;
import org.adorsys.adbase.repo.LocalityRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;

@Stateless
public class LocalityEJB extends CoreAbstIdentifiedEJB<Locality> {
	@Inject
	private LocalityRepository repository;

	@Override
	protected CoreAbstIdentifRepo<Locality> getRepo() {
		return repository;
	}
}
