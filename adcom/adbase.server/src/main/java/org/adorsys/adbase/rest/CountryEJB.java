package org.adorsys.adbase.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.Country;
import org.adorsys.adbase.repo.CountryRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;

@Stateless
public class CountryEJB extends CoreAbstIdentifiedEJB<Country> {
	@Inject
	private CountryRepository repository;

	@Override
	protected CoreAbstIdentifRepo<Country> getRepo() {
		return repository;
	}

}
