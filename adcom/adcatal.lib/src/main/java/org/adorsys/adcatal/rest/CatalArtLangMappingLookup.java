package org.adorsys.adcatal.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArtLangMapping;
import org.adorsys.adcatal.repo.CatalArtLangMappingRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifDataRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedLookup;

@Stateless
public class CatalArtLangMappingLookup  extends CoreAbstIdentifiedLookup<CatalArtLangMapping>{

	@Inject
	private CatalArtLangMappingRepository repository;

	@Override
	protected CoreAbstIdentifDataRepo<CatalArtLangMapping> getRepo() {
		return repository;
	}

}
