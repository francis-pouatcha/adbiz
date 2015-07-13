package org.adorsys.adcatal.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalPicMapping;
import org.adorsys.adcatal.repo.CatalPicMappingRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifDataRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedLookup;

@Stateless
public class CatalPicMappingLookup  extends CoreAbstIdentifiedLookup<CatalPicMapping>{

	@Inject
	private CatalPicMappingRepository repository;

	@Override
	protected CoreAbstIdentifDataRepo<CatalPicMapping> getRepo() {
		return repository;
	}

	@Override
	protected Class<CatalPicMapping> getEntityClass() {
		return CatalPicMapping.class;
	}
}
