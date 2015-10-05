package org.adorsys.adbase.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.SecUserSession;
import org.adorsys.adbase.repo.SecUserSessionRepository;
import org.adorsys.adcore.repo.CoreAbstEntityRepo;
import org.adorsys.adcore.rest.CoreAbstEntityLookup;

@Stateless
public class SecUserSessionLookup extends CoreAbstEntityLookup<SecUserSession> {

	@Inject
	private SecUserSessionRepository repository;

	@Override
	protected CoreAbstEntityRepo<SecUserSession> getRepo() {
		return repository;
	}

	@Override
	protected Class<SecUserSession> getEntityClass() {
		return SecUserSession.class;
	}
}
