package org.adorsys.adcost.ejb;

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
import org.adorsys.adcost.ejb.CstJobLookup;
import org.adorsys.adcost.ejb.CstStepLookup;
import org.adorsys.adcost.ejb.CstStmntCstrLookup;
import org.adorsys.adcost.ejb.CstStmntHstryLookup;
import org.adorsys.adcost.ejb.CstStmntItemLookup;
import org.adorsys.adcost.ejb.CstStmntLookup;
import org.adorsys.adcost.jpa.CstJob;
import org.adorsys.adcost.jpa.CstStep;
import org.adorsys.adcost.jpa.CstStmnt;
import org.adorsys.adcost.jpa.CstStmntCstr;
import org.adorsys.adcost.jpa.CstStmntHstry;
import org.adorsys.adcost.jpa.CstStmntItem;

@Stateless
public class CstStmntInjector extends
		CoreAbstBsnsObjInjector<CstStmnt, CstStmntItem, CstStmntHstry, CstJob, CstStep, CstStmntCstr> {

	@EJB
	private CstStmntLookup bsnsObjLookup;
	@EJB
	private CstStmntEJB bsnsObjEjb;
	@EJB
	private CstStmntBatch batch;
	@EJB
	private CstStmntCstrLookup cstrLookup;
	@EJB
	private CstStmntCstrEJB cstrEjb;
	@EJB
	private CstStepLookup stepLookup;
	@EJB
	private CstStepEJB stepEjb;
	@EJB
	private CstJobLookup jobLookup;
	@EJB
	private CstJobEJB jobEjb;
	@EJB
	private CstStmntHstryEJB hstrEjb;
	@EJB
	private CstStmntHstryLookup hstrLookup;
	@EJB
	private CstStmntItemEJB itemEjb;
	@EJB
	private CstStmntItemLookup itemLookup;

	@Override
	public CoreAbstBsnsObjectLookup<CstStmnt> getBsnsObjLookup() {
		return bsnsObjLookup;
	}

	@Override
	public CoreAbstBsnsObjectEJB<CstStmnt, CstStmntItem, CstStmntHstry, CstJob, CstStep, CstStmntCstr> getBsnsObjEjb() {
		return bsnsObjEjb;
	}

	@Override
	public CoreAbstBsnsItemLookup<CstStmntItem> getItemLookup() {
		return itemLookup;
	}

	@Override
	public CoreAbstBsnsItemEJB<CstStmnt, CstStmntItem, CstStmntHstry, CstJob, CstStep, CstStmntCstr> getItemEjb() {
		return itemEjb;
	}

	@Override
	public CoreAbstBsnsObjectHstryLookup<CstStmntHstry> getHstrLookup() {
		return hstrLookup;
	}

	@Override
	public CoreAbstBsnsObjectHstryEJB<CstStmnt, CstStmntItem, CstStmntHstry, CstJob, CstStep, CstStmntCstr> getHstrEjb() {
		return hstrEjb;
	}

	@Override
	public String getSequenceGeneratorPrefix() {
		return SequenceGenerator.INVENTORY_SEQUENCE_PREFIXE;
	}

	@Override
	public CoreAbstEntityJobEJB<CstJob> getJobEjb() {
		return jobEjb;
	}

	@Override
	public CoreAbstEntityJobLookup<CstJob> getJobLookup() {
		return jobLookup;
	}

	@Override
	public CoreAbstEntityStepEJB<CstStep> getStepEjb() {
		return stepEjb;
	}

	@Override
	public CoreAbstEntityStepLookup<CstStep> getStepLookup() {
		return stepLookup;
	}

	@Override
	public CoreAbstEntityCstrEJB<CstStmntCstr> getCstrEjb() {
		return cstrEjb;
	}

	@Override
	public CoreAbstEntityCstrLookup<CstStmntCstr> getCstrLookup() {
		return cstrLookup;
	}

}
