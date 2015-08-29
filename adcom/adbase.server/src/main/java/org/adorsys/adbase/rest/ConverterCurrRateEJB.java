package org.adorsys.adbase.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.ConverterCurrRate;
import org.adorsys.adbase.repo.ConverterCurrRateRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;

@Stateless
public class ConverterCurrRateEJB extends CoreAbstIdentifiedEJB<ConverterCurrRate>{

	@Inject
	private ConverterCurrRateRepository repository;

	@Override
	protected CoreAbstIdentifRepo<ConverterCurrRate> getRepo() {
		return repository;
	}

}
