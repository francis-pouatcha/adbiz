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
import org.adorsys.adstock.jpa.StkMvnt;
import org.adorsys.adstock.jpa.StkMvntHstry;
import org.adorsys.adstock.jpa.StkMvntJob;
import org.adorsys.adstock.jpa.StkMvntStep;

public class StkMvntInjector extends
		CoreAbstBsnsObjInjector<CoreAbstBsnsObject, StkMvnt, StkMvntHstry, StkMvntJob, StkMvntStep, CoreAbstEntityCstr> {

	@EJB
	private StkMvntLookup itemLookup;
	@EJB
	private StkMvntEJB itemEjb;
	@EJB
	private StkMvntHstryLookup hstryLookup;
	@EJB
	private StkMvntHstryEJB hstryEjb;
	@EJB
	private StkMvntJobEJB jobEjb;
	@EJB
	private StkMvntJobLookup jobLookup;
	@EJB
	private StkMvntStepEJB stepEJB;
	@EJB
	private StkMvntStepLookup stepLookup;
	
	@Override
	protected CoreAbstBsnsObjectLookup<CoreAbstBsnsObject> getBsnsObjLookup() {
		return null;
	}

	@Override
	protected CoreAbstBsnsObjectEJB<CoreAbstBsnsObject, StkMvnt, 
		StkMvntHstry, StkMvntJob, StkMvntStep, CoreAbstEntityCstr> getBsnsObjEjb() {
		return null;
	}

	@Override
	protected CoreAbstBsnsItemLookup<StkMvnt> getItemLookup() {
		return itemLookup;
	}

	@Override
	protected CoreAbstBsnsItemEJB<CoreAbstBsnsObject, StkMvnt, 
		StkMvntHstry, StkMvntJob, StkMvntStep, CoreAbstEntityCstr> getItemEjb() {
		return itemEjb;
	}

	@Override
	protected CoreAbstBsnsObjectHstryLookup<StkMvntHstry> getHstrLookup() {
		return hstryLookup;
	}

	@Override
	protected CoreAbstBsnsObjectHstryEJB<CoreAbstBsnsObject, StkMvnt, 
		StkMvntHstry, StkMvntJob, StkMvntStep, CoreAbstEntityCstr> getHstrEjb() {
		return hstryEjb;
	}

	@Override
	protected String getSequenceGeneratorPrefix() {
		return SequenceGenerator.STK_MVNT_SEQUENCE_PREFIXE;
	}

	@Override
	protected CoreAbstEntityJobEJB<StkMvntJob> getJobEjb() {
		return jobEjb;
	}

	@Override
	protected CoreAbstEntityJobLookup<StkMvntJob> getJobLookup() {
		return jobLookup;
	}

	@Override
	protected CoreAbstEntityStepEJB<StkMvntStep> getStepEjb() {
		return stepEJB;
	}

	@Override
	protected CoreAbstEntityStepLookup<StkMvntStep> getStepLookup() {
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
	protected CoreAbstBsnsObjBatch<CoreAbstBsnsObject, StkMvnt, StkMvntHstry, StkMvntJob, StkMvntStep, CoreAbstEntityCstr> getBatch() {
		return null;
	}

}
