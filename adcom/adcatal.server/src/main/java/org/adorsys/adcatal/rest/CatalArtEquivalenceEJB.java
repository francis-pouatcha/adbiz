package org.adorsys.adcatal.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArtEquivalence;
import org.adorsys.adcatal.repo.CatalArtEquivalenceRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifDataRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;

@Stateless
public class CatalArtEquivalenceEJB extends
		CoreAbstIdentifiedEJB<CatalArtEquivalence> {

	@Inject
	private CatalArtEquivalenceRepository repository;

	/**
	 * Generate the equivalence code
	 * 
	 * @param mainArtIdentif
	 * @param equivArtIdentif
	 * @return
	 */
	public String generateEquivCode(String mainArtIdentif,
			String equivArtIdentif) {
		return mainArtIdentif + "-" + equivArtIdentif;
	}

	@Override
	protected CoreAbstIdentifDataRepo<CatalArtEquivalence> getRepo() {
		return repository;
	}
}
