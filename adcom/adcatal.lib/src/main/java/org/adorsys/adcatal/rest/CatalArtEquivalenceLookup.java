package org.adorsys.adcatal.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArtEquivalence;
import org.adorsys.adcatal.repo.CatalArtEquivalenceRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifDataRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedLookup;

@Stateless
public class CatalArtEquivalenceLookup extends
		CoreAbstIdentifiedLookup<CatalArtEquivalence> {

	@Inject
	private CatalArtEquivalenceRepository repository;

	@Override
	protected CoreAbstIdentifDataRepo<CatalArtEquivalence> getRepo() {
		return repository;
	}

	@Override
	protected Class<CatalArtEquivalence> getEntityClass() {
		return CatalArtEquivalence.class;
	}
}
