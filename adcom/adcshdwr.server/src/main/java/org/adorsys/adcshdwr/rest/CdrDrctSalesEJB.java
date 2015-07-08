package org.adorsys.adcshdwr.rest;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adbase.util.BaseProcessUtils;
import org.adorsys.adcore.auth.TermWsUserPrincipal;
import org.adorsys.adcore.repo.CoreAbstBsnsObjectRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsItemEJB;
import org.adorsys.adcore.rest.CoreAbstBsnsItemLookup;
import org.adorsys.adcore.rest.CoreAbstBsnsObjLifeCycle;
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
import org.adorsys.adcshdwr.api.CdrDrctSalesInfo;
import org.adorsys.adcshdwr.jpa.CdrDrctSales;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesCstr;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesHstry;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesJob;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesStep;
import org.adorsys.adcshdwr.jpa.CdrDsArtItem;
import org.adorsys.adcshdwr.repo.CdrDrctSalesRepo;

@Stateless
public class CdrDrctSalesEJB extends CoreAbstBsnsObjectEJB<CdrDrctSales, CdrDsArtItem, CdrDrctSalesHstry, 
	CdrDrctSalesJob, CdrDrctSalesStep, CdrDrctSalesCstr>
{

	@Inject
	private CdrDrctSalesRepo repository;

	@Inject
	private SecurityUtil securityUtil;

	@Inject
	private CdrDrctSalesLookup lookup;
	
	@EJB
	private CdrDrctSalesEJB ejb;
	@Inject
	private CdrDsArtItemEJB itemEJB;
	@Inject
	private CdrDsArtItemLookup itemLookup;
	@Inject
	private CdrDrctSalesHstryLookup hstryLookup;
	@Inject
	private CdrDrctSalesHstryEJB hstryEjb;
	@Inject
	private CdrDrctSalesLifeCycle lifeCycle;
	@Inject
	private CdrDrctSalesJobEJB jobEjb;
	@Inject
	private CdrDrctSalesJobLookup jobLookup;
	@Inject
	private CdrDrctSalesStepEjb stepEjb;
	@Inject
	private CdrDrctSalesStepLookup stepLookup;
	@Inject
	private CdrDrctSalesCstrLookup cstrLookup;
	@Inject
	private BaseProcessUtils processUtils;
	@Inject
	private CdrDrctSalesCstrEJB cstrEjb;

	@Override
	protected CoreAbstBsnsObjectRepo<CdrDrctSales> getBsnsRepo() {
		return repository;
	}

	@Override
	protected CoreAbstBsnsObjectLookup<CdrDrctSales> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstBsnsObjectEJB<CdrDrctSales, CdrDsArtItem, CdrDrctSalesHstry, CdrDrctSalesJob, CdrDrctSalesStep, CdrDrctSalesCstr> getEjb() {
		return ejb;
	}

	@Override
	protected CoreAbstBsnsItemLookup<CdrDsArtItem> getItemLookup() {
		return itemLookup;
	}

	@Override
	protected CoreAbstBsnsItemEJB<CdrDsArtItem> getItemEjb() {
		return itemEJB;
	}

	@Override
	protected CoreAbstBsnsObjectHstryLookup<CdrDrctSalesHstry> getHistoryLookup() {
		return hstryLookup;
	}

	@Override
	protected CoreAbstBsnsObjectHstryEJB<CdrDrctSalesHstry> getHistoryEjb() {
		return hstryEjb;
	}

	@Override
	protected String getSequenceGeneratorPrefix() {
		return SequenceGenerator.DIRECT_SALES_SEQUENCE_PREFIXE;
	}

	@Override
	protected TermWsUserPrincipal getCallerPrincipal() {
		return securityUtil.getCallerPrincipal();
	}

	@Override
	protected String prinHstryInfo(CdrDrctSales entity) {
		return CdrDrctSalesInfo.prinInfo(entity);
	}

	@Override
	protected CoreAbstBsnsObjLifeCycle<CdrDrctSalesHstry> getLifeCycle() {
		return lifeCycle;
	}

	@Override
	protected CoreAbstEntityJobEJB<CdrDrctSalesJob> getJobEjb() {
		return jobEjb;
	}

	@Override
	protected CoreAbstEntityJobLookup<CdrDrctSalesJob> getJobLookup() {
		return jobLookup;
	}

	@Override
	protected CoreAbstEntityStepEJB<CdrDrctSalesStep> getStepEjb() {
		return stepEjb;
	}

	@Override
	protected CoreAbstEntityStepLookup<CdrDrctSalesStep> getStepLookup() {
		return stepLookup;
	}

	@Override
	protected CoreAbstEntityCstrEJB<CdrDrctSalesCstr> getCstrEjb() {
		return cstrEjb;
	}

	@Override
	protected CoreAbstEntityCstrLookup<CdrDrctSalesCstr> getCstrLookup() {
		return cstrLookup;
	}

	@Override
	protected String getCurrentProcessIdentif() {
		return processUtils.getProcessId();
	}

}
