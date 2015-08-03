package org.adorsys.adstock.rest;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.adorsys.adcore.jpa.CoreAbstBsnsObject;
import org.adorsys.adcore.jpa.CoreAbstEntityCstr;
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
import org.adorsys.adstock.jpa.StkArticleLot;
import org.adorsys.adstock.jpa.StkArticleLotHstry;
import org.adorsys.adstock.jpa.StkJob;
import org.adorsys.adstock.jpa.StkStep;

@Stateless
public class StkArticleLotInjector extends
		CoreAbstBsnsObjInjector<CoreAbstBsnsObject, StkArticleLot, StkArticleLotHstry, StkJob, StkStep, CoreAbstEntityCstr> {

	@EJB
	private StkArticleLotLookup itemLookup;
	@EJB
	private StkArticleLotEJB itemEjb;
	@EJB
	private StkArticleLotHstryLookup hstryLookup;
	@EJB
	private StkArticleLotHstryEJB hstryEjb;
	@EJB
	private StkJobEJB jobEjb;
	@EJB
	private StkJobLookup jobLookup;
	@EJB
	private StkStepEJB stepEJB;
	@EJB
	private StkStepLookup stepLookup;

	@Override
	public CoreAbstBsnsObjectLookup<CoreAbstBsnsObject> getBsnsObjLookup() {
		return null;
	}

	@Override
	public CoreAbstBsnsObjectEJB<CoreAbstBsnsObject, StkArticleLot, 
		StkArticleLotHstry, StkJob, StkStep, CoreAbstEntityCstr> getBsnsObjEjb() {
		return null;
	}

	@Override
	public CoreAbstBsnsItemLookup<StkArticleLot> getItemLookup() {
		return itemLookup;
	}

	@Override
	public CoreAbstBsnsItemEJB<CoreAbstBsnsObject, StkArticleLot, 
		StkArticleLotHstry, StkJob, StkStep, CoreAbstEntityCstr> getItemEjb() {
		return itemEjb;
	}

	@Override
	public CoreAbstBsnsObjectHstryLookup<StkArticleLotHstry> getHstrLookup() {
		return hstryLookup;
	}

	@Override
	public CoreAbstBsnsObjectHstryEJB<CoreAbstBsnsObject, StkArticleLot, 
		StkArticleLotHstry, StkJob, StkStep, CoreAbstEntityCstr> getHstrEjb() {
		return hstryEjb;
	}

	@Override
	public String getSequenceGeneratorPrefix() {
		return SequenceGenerator.LOT_SEQUENCE_PREFIXE;
	}

	@Override
	public CoreAbstEntityJobEJB<StkJob> getJobEjb() {
		return jobEjb;
	}

	@Override
	public CoreAbstEntityJobLookup<StkJob> getJobLookup() {
		return jobLookup;
	}

	@Override
	public CoreAbstEntityStepEJB<StkStep> getStepEjb() {
		return stepEJB;
	}

	@Override
	public CoreAbstEntityStepLookup<StkStep> getStepLookup() {
		return stepLookup;
	}

	@Override
	public CoreAbstEntityCstrEJB<CoreAbstEntityCstr> getCstrEjb() {
		return null;
	}

	@Override
	public CoreAbstEntityCstrLookup<CoreAbstEntityCstr> getCstrLookup() {
		return null;
	}
}
