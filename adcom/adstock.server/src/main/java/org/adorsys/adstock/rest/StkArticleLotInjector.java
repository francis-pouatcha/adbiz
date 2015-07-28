package org.adorsys.adstock.rest;

import javax.ejb.EJB;

import org.adorsys.adcore.jpa.CoreAbstBsnsObject;
import org.adorsys.adcore.jpa.CoreAbstEntityCstr;
import org.adorsys.adcore.rest.CoreAbstBsnsItemEJB;
import org.adorsys.adcore.rest.CoreAbstBsnsItemLookup;
import org.adorsys.adcore.rest.CoreAbstBsnsObjBatch;
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
import org.adorsys.adstock.jpa.StkArticleLot;
import org.adorsys.adstock.jpa.StkArticleLotHstry;
import org.adorsys.adstock.jpa.StkArticleLotJob;
import org.adorsys.adstock.jpa.StkArticleLotStep;

public class StkArticleLotInjector extends
		CoreAbstBsnsObjInjector<CoreAbstBsnsObject, StkArticleLot, StkArticleLotHstry, StkArticleLotJob, StkArticleLotStep, CoreAbstEntityCstr> {

	@EJB
	private StkArticleLotLookup itemLookup;
	@EJB
	private StkArticleLotEJB itemEjb;
	@EJB
	private StkArticleLotHstryLookup hstryLookup;
	@EJB
	private StkArticleLotHstryEJB hstryEjb;
	@EJB
	private StkArticleLotJobEJB jobEjb;
	@EJB
	private StkArticleLotJobLookup jobLookup;
	@EJB
	private StkArticleLotStepEJB stepEJB;
	@EJB
	private StkArticleLotStepLookup stepLookup;

	@Override
	protected CoreAbstBsnsObjectLookup<CoreAbstBsnsObject> getBsnsObjLookup() {
		return null;
	}

	@Override
	protected CoreAbstBsnsObjectEJB<CoreAbstBsnsObject, StkArticleLot, 
		StkArticleLotHstry, StkArticleLotJob, StkArticleLotStep, CoreAbstEntityCstr> getBsnsObjEjb() {
		return null;
	}

	@Override
	protected CoreAbstBsnsItemLookup<StkArticleLot> getItemLookup() {
		return itemLookup;
	}

	@Override
	protected CoreAbstBsnsItemEJB<CoreAbstBsnsObject, StkArticleLot, 
		StkArticleLotHstry, StkArticleLotJob, StkArticleLotStep, CoreAbstEntityCstr> getItemEjb() {
		return itemEjb;
	}

	@Override
	protected CoreAbstBsnsObjectHstryLookup<StkArticleLotHstry> getHstrLookup() {
		return hstryLookup;
	}

	@Override
	protected CoreAbstBsnsObjectHstryEJB<CoreAbstBsnsObject, StkArticleLot, 
		StkArticleLotHstry, StkArticleLotJob, StkArticleLotStep, CoreAbstEntityCstr> getHstrEjb() {
		return hstryEjb;
	}

	@Override
	protected String getSequenceGeneratorPrefix() {
		return SequenceGenerator.LOT_SEQUENCE_PREFIXE;
	}

	@Override
	protected CoreAbstEntityJobEJB<StkArticleLotJob> getJobEjb() {
		return jobEjb;
	}

	@Override
	protected CoreAbstEntityJobLookup<StkArticleLotJob> getJobLookup() {
		return jobLookup;
	}

	@Override
	protected CoreAbstEntityStepEJB<StkArticleLotStep> getStepEjb() {
		return stepEJB;
	}

	@Override
	protected CoreAbstEntityStepLookup<StkArticleLotStep> getStepLookup() {
		return stepLookup;
	}

	@Override
	protected CoreAbstEntityCstrEJB<CoreAbstEntityCstr> getCstrEjb() {
		return null;
	}

	@Override
	protected CoreAbstEntityCstrLookup<CoreAbstEntityCstr> getCstrLookup() {
		return null;
	}

	@Override
	protected CoreAbstBsnsObjBatch<CoreAbstBsnsObject, StkArticleLot, StkArticleLotHstry, StkArticleLotJob, StkArticleLotStep, CoreAbstEntityCstr> getBatch() {
		return null;
	}
}
