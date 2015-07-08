package org.adorsys.adcshdwr.rest;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstBsnsObjHstryRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectHstryEJB;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesHstry;
import org.adorsys.adcshdwr.repo.CdrDrctSalesHstryRepo;

@Stateless
public class CdrDrctSalesHstryEJB extends
		CoreAbstBsnsObjectHstryEJB<CdrDrctSalesHstry> {

	@Inject
	private CdrDrctSalesHstryRepo repository;

	@Inject
	@CdrDrctSalesHstryEvent
	private Event<CdrDrctSalesHstry> hstryEvent;

	@Override
	protected CoreAbstBsnsObjHstryRepo<CdrDrctSalesHstry> getRepo() {
		return repository;
	}

	@Override
	protected void fireEvent(CdrDrctSalesHstry hstry) {
		hstryEvent.fire(hstry);
	}

	@Override
	protected CdrDrctSalesHstry newHstryObj() {
		return new CdrDrctSalesHstry();
	}
}
