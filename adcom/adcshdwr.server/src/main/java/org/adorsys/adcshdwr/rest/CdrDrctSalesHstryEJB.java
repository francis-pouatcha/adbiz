package org.adorsys.adcshdwr.rest;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstBsnsObjHstryRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsObjInjector;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectHstryEJB;
import org.adorsys.adcshdwr.jpa.CdrDrctSales;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesCstr;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesHstry;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesItem;
import org.adorsys.adcshdwr.jpa.CdrJob;
import org.adorsys.adcshdwr.jpa.CdrStep;
import org.adorsys.adcshdwr.repo.CdrDrctSalesHstryRepo;

@Stateless
public class CdrDrctSalesHstryEJB extends
		CoreAbstBsnsObjectHstryEJB<CdrDrctSales, CdrDrctSalesItem, CdrDrctSalesHstry, CdrJob, CdrStep, CdrDrctSalesCstr> {

	@Inject
	private CdrDrctSalesHstryRepo repository;
	@EJB
	private CdrDrctSalesInjector injector;

	@Override
	protected CoreAbstBsnsObjHstryRepo<CdrDrctSalesHstry> getRepo() {
		return repository;
	}

	@Override
	protected CdrDrctSalesHstry newHstryObj() {
		return new CdrDrctSalesHstry();
	}

	@Override
	protected CoreAbstBsnsObjInjector<CdrDrctSales, CdrDrctSalesItem, CdrDrctSalesHstry, CdrJob, CdrStep, CdrDrctSalesCstr> getInjector() {
		return injector;
	}
}
