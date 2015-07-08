package org.adorsys.adcatal.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalProductFamily;
import org.adorsys.adcatal.repo.CatalProductFamilyRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifDataRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedLookup;

@Stateless
public class CatalProductFamilyLookup  extends CoreAbstIdentifiedLookup<CatalProductFamily>{

	@Inject
	private CatalProductFamilyRepository repository;

	@Override
	protected CoreAbstIdentifDataRepo<CatalProductFamily> getRepo() {
		return repository;
	}


}
