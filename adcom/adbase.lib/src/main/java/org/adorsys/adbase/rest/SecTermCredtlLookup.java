package org.adorsys.adbase.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.SecTermCredtl;
import org.adorsys.adbase.repo.SecTermCredtlRepository;
import org.adorsys.adcore.repo.CoreAbstEntityRepo;
import org.adorsys.adcore.rest.CoreAbstEntityLookup;

@Stateless
public class SecTermCredtlLookup extends CoreAbstEntityLookup<SecTermCredtl> {

	@Inject
	private SecTermCredtlRepository repository;

	@Override
	protected CoreAbstEntityRepo<SecTermCredtl> getRepo() {
		return repository;
	}

	@Override
	protected Class<SecTermCredtl> getEntityClass() {
		return SecTermCredtl.class;
	}
}
