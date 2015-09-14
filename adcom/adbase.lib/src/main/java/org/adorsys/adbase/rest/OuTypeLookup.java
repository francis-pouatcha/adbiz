package org.adorsys.adbase.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.OuType;
import org.adorsys.adbase.repo.OuTypeRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;

@Stateless
public class OuTypeLookup extends CoreAbstIdentifLookup<OuType> {

	@Inject
	private OuTypeRepository repository;

	@Override
	protected CoreAbstIdentifRepo<OuType> getRepo() {
		return repository;
	}

	@Override
	protected Class<OuType> getEntityClass() {
		return OuType.class;
	}
}
