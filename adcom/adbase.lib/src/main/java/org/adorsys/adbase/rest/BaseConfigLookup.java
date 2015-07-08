package org.adorsys.adbase.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.BaseConfig;
import org.adorsys.adbase.repo.BaseConfigRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifDataRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedLookup;

@Stateless
public class BaseConfigLookup extends CoreAbstIdentifiedLookup<BaseConfig>{

	@Inject
	private BaseConfigRepository repository;

	@Override
	protected CoreAbstIdentifDataRepo<BaseConfig> getRepo() {
		return repository;
	}

}
