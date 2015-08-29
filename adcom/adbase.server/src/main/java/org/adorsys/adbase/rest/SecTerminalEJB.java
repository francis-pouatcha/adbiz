package org.adorsys.adbase.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.SecTerminal;
import org.adorsys.adbase.repo.SecTerminalRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;

@Stateless
public class SecTerminalEJB extends CoreAbstIdentifiedEJB<SecTerminal> {

	@Inject
	private SecTerminalRepository repository;

	@Override
	protected CoreAbstIdentifRepo<SecTerminal> getRepo() {
		return repository;
	}
}
