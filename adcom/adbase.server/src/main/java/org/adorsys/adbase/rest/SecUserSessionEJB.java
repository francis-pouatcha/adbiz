package org.adorsys.adbase.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.SecUserSession;
import org.adorsys.adbase.repo.SecUserSessionRepository;
import org.adorsys.adcore.repo.CoreAbstEntityRepo;
import org.adorsys.adcore.rest.CoreAbstEntityEJB;

@Stateless
public class SecUserSessionEJB extends CoreAbstEntityEJB<SecUserSession> {

	@Inject
	private SecUserSessionRepository repo;

	@Override
	protected CoreAbstEntityRepo<SecUserSession> getRepo() {
		return repo;
	}
}
