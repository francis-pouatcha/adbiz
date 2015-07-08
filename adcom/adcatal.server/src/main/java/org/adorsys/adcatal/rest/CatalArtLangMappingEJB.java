package org.adorsys.adcatal.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArtLangMapping;
import org.adorsys.adcatal.repo.CatalArtLangMappingRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifDataRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;

@Stateless
public class CatalArtLangMappingEJB  extends CoreAbstIdentifiedEJB<CatalArtLangMapping>{

	@Inject
	private CatalArtLangMappingRepository repository;

	@Override
	protected CoreAbstIdentifDataRepo<CatalArtLangMapping> getRepo() {
		return repository;
	}

}
