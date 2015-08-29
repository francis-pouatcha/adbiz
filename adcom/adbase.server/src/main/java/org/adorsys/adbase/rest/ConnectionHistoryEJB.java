package org.adorsys.adbase.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.ConnectionHistory;
import org.adorsys.adbase.repo.ConnectionHistoryRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;

@Stateless
public class ConnectionHistoryEJB extends CoreAbstIdentifiedEJB<ConnectionHistory>{

	@Inject
	private ConnectionHistoryRepository repository;

	@Override
	protected CoreAbstIdentifRepo<ConnectionHistory> getRepo() {
		return repository;
	}

}
