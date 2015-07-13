package org.adorsys.adcatal.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArtDetailConfig;
import org.adorsys.adcatal.repo.CatalArtDetailConfigRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifDataRepo;

@Stateless
public class CatalArtDetailConfigLookup extends
		CatalAbstractArticleLookup<CatalArtDetailConfig> {

	@Inject
	private CatalArtDetailConfigRepository repository;

	@Override
	protected CoreAbstIdentifDataRepo<CatalArtDetailConfig> getRepo() {
		return repository;
	}

	@Override
	protected Class<CatalArtDetailConfig> getEntityClass() {
		return CatalArtDetailConfig.class;
	}
}
