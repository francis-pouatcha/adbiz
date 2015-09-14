package org.adorsys.adbase.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.SecTermSession;
import org.adorsys.adbase.repo.SecTermSessionRepository;
import org.adorsys.adcore.repo.CoreAbstEntityRepo;
import org.adorsys.adcore.rest.CoreAbstEntityLookup;

@Stateless
public class SecTermSessionLookup extends CoreAbstEntityLookup<SecTermSession> {
	@Inject
	private SecTermSessionRepository repository;

	@Override
	protected CoreAbstEntityRepo<SecTermSession> getRepo() {
		return repository;
	}

	@Override
	protected Class<SecTermSession> getEntityClass() {
		return SecTermSession.class;
	}
}
