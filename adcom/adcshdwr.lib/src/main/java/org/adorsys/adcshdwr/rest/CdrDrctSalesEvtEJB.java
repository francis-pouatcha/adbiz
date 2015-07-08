package org.adorsys.adcshdwr.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstEvtRepo;
import org.adorsys.adcore.rest.CoreAbstEvtEJB;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesEvt;
import org.adorsys.adcshdwr.repo.CdrDrctSalesEvtRepo;

@Stateless
public class CdrDrctSalesEvtEJB extends CoreAbstEvtEJB<CdrDrctSalesEvt> {

	@Inject
	private CdrDrctSalesEvtRepo repository;

	@Override
	protected CoreAbstEvtRepo<CdrDrctSalesEvt> getRepo() {
		return repository;
	}

}
