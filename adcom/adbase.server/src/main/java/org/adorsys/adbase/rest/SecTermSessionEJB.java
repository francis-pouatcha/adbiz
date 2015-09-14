package org.adorsys.adbase.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.SecTermSession;
import org.adorsys.adbase.repo.SecTermSessionRepository;
import org.adorsys.adcore.repo.CoreAbstEntityRepo;
import org.adorsys.adcore.rest.CoreAbstEntityEJB;

@Stateless
public class SecTermSessionEJB extends CoreAbstEntityEJB<SecTermSession> {

	@Inject
	private SecTermSessionRepository repo;

	@Override
	protected CoreAbstEntityRepo<SecTermSession> getRepo() {
		return repo;
	}
}
