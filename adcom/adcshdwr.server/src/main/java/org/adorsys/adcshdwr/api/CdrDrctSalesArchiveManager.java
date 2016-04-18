package org.adorsys.adcshdwr.api;

import javax.inject.Inject;

import org.adorsys.adcore.rest.CoreAbstArchiveManager;
import org.adorsys.adcore.rest.CoreAbstBsnsObjInjector;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcshdwr.jpa.CdrDrctSales;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesArchive;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesCstr;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesHstry;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesHstryArchive;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesItem;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesItemArchive;
import org.adorsys.adcshdwr.jpa.CdrJob;
import org.adorsys.adcshdwr.jpa.CdrStep;
import org.adorsys.adcshdwr.rest.CdrDrctSalesArchiveEJB;
import org.adorsys.adcshdwr.rest.CdrDrctSalesInjector;

public class CdrDrctSalesArchiveManager extends CoreAbstArchiveManager<CdrDrctSales, CdrDrctSalesArchive, CdrDrctSalesItem, CdrDrctSalesItemArchive, CdrDrctSalesHstry, CdrDrctSalesHstryArchive, CdrJob, CdrStep, CdrDrctSalesCstr> {

	@Inject
	private CdrDrctSalesInjector injector;
	
	@Inject
	private CdrDrctSalesArchiveEJB ejb;
	
	@Override
	protected CoreAbstBsnsObjInjector<CdrDrctSales, CdrDrctSalesItem, CdrDrctSalesHstry, CdrJob, CdrStep, CdrDrctSalesCstr> getInjector() {
		return injector;
	}

	@Override
	protected CoreAbstIdentifiedEJB<CdrDrctSalesArchive> getArchiveObjEjb() {
		return ejb;
	}

	@Override
	protected CdrDrctSalesArchive newArchiveObject() {
		return new CdrDrctSalesArchive();
	}
	
}
