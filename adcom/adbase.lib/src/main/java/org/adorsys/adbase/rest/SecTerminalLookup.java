package org.adorsys.adbase.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.SecTerminal;
import org.adorsys.adbase.repo.SecTerminalRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;

@Stateless
public class SecTerminalLookup extends CoreAbstIdentifLookup<SecTerminal>{

	@Inject
	private SecTerminalRepository repository;

	@Override
	protected CoreAbstIdentifRepo<SecTerminal> getRepo() {
		return repository;
	}

	@Override
	protected Class<SecTerminal> getEntityClass() {
		return SecTerminal.class;
	}

}
