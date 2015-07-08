package org.adorsys.adcatal.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalFamilyFeatMaping;
import org.adorsys.adcatal.repo.CatalFamilyFeatMapingRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifDataRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedLookup;

@Stateless
public class CatalFamilyFeatMapingLookup  extends CoreAbstIdentifiedLookup<CatalFamilyFeatMaping>{

	@Inject
	private CatalFamilyFeatMapingRepository repository;

	@Override
	protected CoreAbstIdentifDataRepo<CatalFamilyFeatMaping> getRepo() {
		return repository;
	}

}
