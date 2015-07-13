package org.adorsys.adcatal.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArt2ProductFamily;
import org.adorsys.adcatal.repo.CatalArt2ProductFamilyRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifDataRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedLookup;

@Stateless
public class CatalArt2ProductFamilyLookup extends CoreAbstIdentifiedLookup<CatalArt2ProductFamily>{

	@Inject
	private CatalArt2ProductFamilyRepository repository;

	@Override
	protected CoreAbstIdentifDataRepo<CatalArt2ProductFamily> getRepo() {
		return repository;
	}

	@Override
	protected Class<CatalArt2ProductFamily> getEntityClass() {
		return CatalArt2ProductFamily.class;
	}
}
