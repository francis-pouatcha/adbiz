package org.adorsys.adcshdwr.rest;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstBsnsObjectRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsObjInjector;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectEJB;
import org.adorsys.adcshdwr.jpa.CdrDrctSales;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesCstr;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesHstry;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesItem;
import org.adorsys.adcshdwr.jpa.CdrJob;
import org.adorsys.adcshdwr.jpa.CdrStep;
import org.adorsys.adcshdwr.repo.CdrDrctSalesRepo;

@Stateless
public class CdrDrctSalesEJB extends CoreAbstBsnsObjectEJB<CdrDrctSales, CdrDrctSalesItem, CdrDrctSalesHstry, 
	CdrJob, CdrStep, CdrDrctSalesCstr>
{

	@Inject
	private CdrDrctSalesRepo repository;

	@EJB
	private CdrDrctSalesInjector injector;

	@Override
	protected CoreAbstBsnsObjectRepo<CdrDrctSales> getBsnsRepo() {
		return repository;
	}

	@Override
	protected CoreAbstBsnsObjInjector<CdrDrctSales, CdrDrctSalesItem, CdrDrctSalesHstry, CdrJob, CdrStep, CdrDrctSalesCstr> getInjector() {
		return injector;
	}
}
