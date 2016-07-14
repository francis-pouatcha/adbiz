package org.adorsys.adcshdwr.rest;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstBsnsItemRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsItemEJB;
import org.adorsys.adcore.rest.CoreAbstBsnsObjInjector;
import org.adorsys.adcshdwr.jpa.CdrDrctSales;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesCstr;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesHstry;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesItem;
import org.adorsys.adcshdwr.jpa.CdrJob;
import org.adorsys.adcshdwr.jpa.CdrStep;
import org.adorsys.adcshdwr.repo.CdrDrctSalesItemRepo;

@Stateless
public class CdrDrctSalesItemEJB extends CoreAbstBsnsItemEJB<CdrDrctSales, CdrDrctSalesItem, CdrDrctSalesHstry, CdrJob, CdrStep, CdrDrctSalesCstr>
{
	@Inject
	private CdrDrctSalesItemRepo repository;
	@Inject
	private CdrDrctSalesItemLookup lookup;
	@EJB
	private CdrDrctSalesItemEJB ejb;
	
	@EJB
	private CdrDrctSalesInjector injector;
	@Override
	protected CoreAbstBsnsObjInjector<CdrDrctSales, CdrDrctSalesItem, CdrDrctSalesHstry, CdrJob, CdrStep, CdrDrctSalesCstr> getInjector() {
		// TODO Auto-generated method stub
		return injector;
	}
	@Override
	protected CoreAbstBsnsItemRepo<CdrDrctSalesItem> getBsnsRepo() {
		// TODO Auto-generated method stub
		return repository;
	}

}
