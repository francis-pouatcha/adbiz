package org.adorsys.adcatal.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArt2ProductFamily;
import org.adorsys.adcatal.repo.CatalArt2ProductFamilyRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;

@Stateless
public class CatalArt2ProductFamilyLookup extends CoreAbstIdentifLookup<CatalArt2ProductFamily>{

	@Inject
	private CatalArt2ProductFamilyRepository repository;

	@Override
	protected CoreAbstIdentifRepo<CatalArt2ProductFamily> getRepo() {
		return repository;
	}

	@Override
	protected Class<CatalArt2ProductFamily> getEntityClass() {
		return CatalArt2ProductFamily.class;
	}
}
