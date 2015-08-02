package org.adorsys.adcatal.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArtLangMappingHstry;
import org.adorsys.adcatal.repo.CatalArtLangMappingHstryRepo;
import org.adorsys.adcore.repo.CoreAbstIdentifObjectHstryRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedHstryLookup;

@Stateless
public class CatalArtLangMappingHstryLookup  extends CoreAbstIdentifiedHstryLookup<CatalArtLangMappingHstry>{

	@Inject
	private CatalArtLangMappingHstryRepo repo;

	@Override
	protected CoreAbstIdentifObjectHstryRepo<CatalArtLangMappingHstry> getRepo() {
		return repo;
	}
}
