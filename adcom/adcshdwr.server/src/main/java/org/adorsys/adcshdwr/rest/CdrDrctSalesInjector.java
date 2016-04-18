package org.adorsys.adcshdwr.rest;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.adorsys.adcore.rest.CoreAbstBsnsItemEJB;
import org.adorsys.adcore.rest.CoreAbstBsnsItemLookup;
import org.adorsys.adcore.rest.CoreAbstBsnsObjInjector;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectEJB;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectHstryEJB;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectHstryLookup;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectLookup;
import org.adorsys.adcore.rest.CoreAbstEntityCstrEJB;
import org.adorsys.adcore.rest.CoreAbstEntityCstrLookup;
import org.adorsys.adcore.rest.CoreAbstEntityJobEJB;
import org.adorsys.adcore.rest.CoreAbstEntityJobLookup;
import org.adorsys.adcore.rest.CoreAbstEntityStepEJB;
import org.adorsys.adcore.rest.CoreAbstEntityStepLookup;
import org.adorsys.adcore.utils.SequenceGenerator;
import org.adorsys.adcshdwr.jpa.CdrDrctSales;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesCstr;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesHstry;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesItem;
import org.adorsys.adcshdwr.jpa.CdrJob;
import org.adorsys.adcshdwr.jpa.CdrStep;

@Stateless
public class CdrDrctSalesInjector extends
		CoreAbstBsnsObjInjector<CdrDrctSales, CdrDrctSalesItem, CdrDrctSalesHstry, CdrJob, CdrStep, CdrDrctSalesCstr> {

	@EJB
	private CdrDrctSalesLookup bsnsObjLookup;
	@EJB
	private CdrDrctSalesEJB bsnsObjEjb;
	@EJB
	private CdrBatch batch;
	@EJB
	private CdrDrctSalesCstrLookup cstrLookup;
	@EJB
	private CdrDrctSalesCstrEJB cstrEjb;
	@EJB
	private CdrStepLookup stepLookup;
	@EJB
	private CdrStepEJB stepEjb;
	@EJB
	private CdrJobLookup jobLookup;
	@EJB
	private CdrJobEJB jobEjb;
	@EJB
	private CdrDrctSalesHstryEJB hstrEjb;
	@EJB
	private CdrDrctSalesHstryLookup hstrLookup;
	@EJB
	private CdrDrctSalesItemEJB itemEjb;
	@EJB
	private CdrDrctSalesItemLookup itemLookup;

	@Override
	public CoreAbstBsnsObjectLookup<CdrDrctSales> getBsnsObjLookup() {
		return bsnsObjLookup;
	}

	@Override
	public CoreAbstBsnsObjectEJB<CdrDrctSales, CdrDrctSalesItem, CdrDrctSalesHstry, CdrJob, CdrStep, CdrDrctSalesCstr> getBsnsObjEjb() {
		return bsnsObjEjb;
	}

	@Override
	public CoreAbstBsnsItemLookup<CdrDrctSalesItem> getItemLookup() {
		return itemLookup;
	}

	@Override
	public CoreAbstBsnsItemEJB<CdrDrctSales, CdrDrctSalesItem, CdrDrctSalesHstry, CdrJob, CdrStep, CdrDrctSalesCstr> getItemEjb() {
		return itemEjb;
	}

	@Override
	public CoreAbstBsnsObjectHstryLookup<CdrDrctSalesHstry> getHstrLookup() {
		return hstrLookup;
	}

	@Override
	public CoreAbstBsnsObjectHstryEJB<CdrDrctSales, CdrDrctSalesItem, CdrDrctSalesHstry, CdrJob, CdrStep, CdrDrctSalesCstr> getHstrEjb() {
		return hstrEjb;
	}

	@Override
	public String getSequenceGeneratorPrefix() {
		return SequenceGenerator.DIRECT_SALES_SEQUENCE_PREFIXE;
	}

	@Override
	public CoreAbstEntityJobEJB<CdrJob> getJobEjb() {
		return jobEjb;
	}

	@Override
	public CoreAbstEntityJobLookup<CdrJob> getJobLookup() {
		return jobLookup;
	}

	@Override
	public CoreAbstEntityStepEJB<CdrStep> getStepEjb() {
		return stepEjb;
	}

	@Override
	public CoreAbstEntityStepLookup<CdrStep> getStepLookup() {
		return stepLookup;
	}

	@Override
	public CoreAbstEntityCstrEJB<CdrDrctSalesCstr> getCstrEjb() {
		return cstrEjb;
	}

	@Override
	public CoreAbstEntityCstrLookup<CdrDrctSalesCstr> getCstrLookup() {
		return cstrLookup;
	}

}
