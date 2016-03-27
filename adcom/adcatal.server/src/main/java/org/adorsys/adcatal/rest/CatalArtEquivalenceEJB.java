package org.adorsys.adcatal.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArtEquivalence;
import org.adorsys.adcatal.repo.CatalArtEquivalenceRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;

@Stateless
public class CatalArtEquivalenceEJB extends
		CoreAbstIdentifiedEJB<CatalArtEquivalence> {

	@Inject
	private CatalArtEquivalenceRepository repository;

	@Override
	protected CoreAbstIdentifRepo<CatalArtEquivalence> getRepo() {
		return repository;
	}	
}
