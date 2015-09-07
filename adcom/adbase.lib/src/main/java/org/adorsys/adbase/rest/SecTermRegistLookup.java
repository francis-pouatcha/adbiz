package org.adorsys.adbase.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.SecTermRegist;
import org.adorsys.adbase.repo.SecTermRegistRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;

@Stateless
public class SecTermRegistLookup extends CoreAbstIdentifLookup<SecTermRegist> {

	@Inject
	private SecTermRegistRepository repository;

	@Override
	protected CoreAbstIdentifRepo<SecTermRegist> getRepo() {
		return repository;
	}

	@Override
	protected Class<SecTermRegist> getEntityClass() {
		return SecTermRegist.class;
	}
}
