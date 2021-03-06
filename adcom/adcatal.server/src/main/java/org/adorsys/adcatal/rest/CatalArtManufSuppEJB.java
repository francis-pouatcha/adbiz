package org.adorsys.adcatal.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArtManufSupp;
import org.adorsys.adcatal.repo.CatalArtManufSuppRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;

@Stateless
public class CatalArtManufSuppEJB extends
		CoreAbstIdentifiedEJB<CatalArtManufSupp> {

	@Inject
	private CatalArtManufSuppRepository repository;

	@Override
	protected CoreAbstIdentifRepo<CatalArtManufSupp> getRepo() {
		return repository;
	}

}
