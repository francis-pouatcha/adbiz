package org.adorsys.adcatal.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArtDetailConfig;
import org.adorsys.adcatal.repo.CatalArtDetailConfigRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifDataRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;

@Stateless
public class CatalArtDetailConfigEJB extends
		CoreAbstIdentifiedEJB<CatalArtDetailConfig> {

	@Inject
	private CatalArtDetailConfigRepository repository;

	@Override
	protected CoreAbstIdentifDataRepo<CatalArtDetailConfig> getRepo() {
		return repository;
	}

}
