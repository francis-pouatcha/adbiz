package org.adorsys.adcatal.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArtLangMapping;
import org.adorsys.adcatal.jpa.CatalArtLangMappingHstry;
import org.adorsys.adcatal.repo.CatalArtLangMappingHstryRepo;
import org.adorsys.adcore.repo.CoreAbstIdentifObjectHstryRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedHstryEJB;

@Stateless
public class CatalArtLangMappingHstryEJB  extends CoreAbstIdentifiedHstryEJB<CatalArtLangMappingHstry, CatalArtLangMapping>{

	@Inject
	private CatalArtLangMappingHstryRepo repo;

	@Override
	protected CoreAbstIdentifObjectHstryRepo<CatalArtLangMappingHstry> getRepo() {
		return repo;
	}

	@Override
	protected CatalArtLangMappingHstry newHstryObj() {
		return new CatalArtLangMappingHstry();
	}
}
