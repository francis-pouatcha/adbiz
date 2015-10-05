package org.adorsys.adbase.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.BaseCountryName;
import org.adorsys.adbase.repo.BaseCountryNameRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;

@Stateless
public class BaseCountryNameLookup extends
		CoreAbstIdentifLookup<BaseCountryName> {

	@Inject
	private BaseCountryNameRepository repository;

	@Override
	protected CoreAbstIdentifRepo<BaseCountryName> getRepo() {
		return repository;
	}

	@Override
	protected Class<BaseCountryName> getEntityClass() {
		return BaseCountryName.class;
	}
}
