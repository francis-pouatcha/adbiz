package org.adorsys.adcshdwr.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcshdwr.jpa.CdrCshDrawer;
import org.adorsys.adcshdwr.repo.CdrCshDrawerRepository;

@Stateless
public class CdrCshDrawerEJB extends CoreAbstIdentifiedEJB<CdrCshDrawer>{
	@Inject
	private CdrCshDrawerRepository repo;

	@Override
	protected CoreAbstIdentifRepo<CdrCshDrawer> getRepo() {
		return repo;
	}
}
