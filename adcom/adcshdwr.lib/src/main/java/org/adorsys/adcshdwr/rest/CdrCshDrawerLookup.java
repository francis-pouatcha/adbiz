package org.adorsys.adcshdwr.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcshdwr.jpa.CdrCshDrawer;
import org.adorsys.adcshdwr.repo.CdrCshDrawerRepository;

@Stateless
public class CdrCshDrawerLookup extends CoreAbstIdentifLookup<CdrCshDrawer> {

	@Inject
	private CdrCshDrawerRepository repo;

	@Override
	protected Class<CdrCshDrawer> getEntityClass() {
		return CdrCshDrawer.class;
	}

	@Override
	protected CoreAbstIdentifRepo<CdrCshDrawer> getRepo() {
		return repo;
	}

	public List<CdrCshDrawer> findOpenedCshDrawerByCashier(String cashier) {
		return repo.findOpenedCshDrawerByCashier(cashier);
	}

	public List<CdrCshDrawer> findPrevious(String cashier) {
		return repo.findPrevious(cashier);
	}
}
