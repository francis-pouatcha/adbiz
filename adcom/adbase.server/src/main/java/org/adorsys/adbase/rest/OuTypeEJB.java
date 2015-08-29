package org.adorsys.adbase.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.OuType;
import org.adorsys.adbase.repo.OuTypeRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;

@Stateless
public class OuTypeEJB extends CoreAbstIdentifiedEJB<OuType> {

	@Inject
	private OuTypeRepository repository;

	@Override
	protected CoreAbstIdentifRepo<OuType> getRepo() {
		return repository;
	}

}
