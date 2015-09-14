package org.adorsys.adbase.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.BaseCountryName;
import org.adorsys.adbase.repo.BaseCountryNameRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;

@Stateless
public class BaseCountryNameEJB extends CoreAbstIdentifiedEJB<BaseCountryName> {

	@Inject
	private BaseCountryNameRepository repository;

	@Override
	protected CoreAbstIdentifRepo<BaseCountryName> getRepo() {
		return repository;
	}

}
