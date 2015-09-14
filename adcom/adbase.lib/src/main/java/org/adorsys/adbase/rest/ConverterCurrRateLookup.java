package org.adorsys.adbase.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.ConverterCurrRate;
import org.adorsys.adbase.repo.ConverterCurrRateRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;

@Stateless
public class ConverterCurrRateLookup extends
		CoreAbstIdentifLookup<ConverterCurrRate> {

	@Inject
	private ConverterCurrRateRepository repo;

	@Override
	protected CoreAbstIdentifRepo<ConverterCurrRate> getRepo() {
		return repo;
	}

	@Override
	protected Class<ConverterCurrRate> getEntityClass() {
		return ConverterCurrRate.class;
	}
}
