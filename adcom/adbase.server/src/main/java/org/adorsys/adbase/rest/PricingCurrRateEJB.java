package org.adorsys.adbase.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.PricingCurrRate;
import org.adorsys.adbase.repo.PricingCurrRateRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;

@Stateless
public class PricingCurrRateEJB extends CoreAbstIdentifiedEJB<PricingCurrRate> {

	@Inject
	private PricingCurrRateRepository repository;

	@Override
	protected CoreAbstIdentifRepo<PricingCurrRate> getRepo() {
		return repository;
	}
}
