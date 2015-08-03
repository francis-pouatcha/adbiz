package org.adorsys.adstock.rest;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.adorsys.adcore.rest.CoreAbstEntityJobEJB;
import org.adorsys.adcore.rest.CoreAbstEntityJobLookup;
import org.adorsys.adcore.rest.CoreAbstEntityStepEJB;
import org.adorsys.adcore.rest.CoreAbstEntityStepLookup;
import org.adorsys.adcore.rest.CoreAbstPrcssngStepEJB;
import org.adorsys.adcore.rest.CoreAbstPrcssngStepLookup;
import org.adorsys.adcore.task.CoreAbstEntityBatch;
import org.adorsys.adstock.jpa.StkJob;
import org.adorsys.adstock.jpa.StkPrcssgStep;
import org.adorsys.adstock.jpa.StkStep;

@Stateless
public class StkBatch extends CoreAbstEntityBatch<StkJob, StkStep, StkPrcssgStep>{
	
	@EJB
	private StkJobEJB jobEjb;
	@EJB
	private StkJobLookup jobLookup; 
	@EJB
	private StkStepEJB stepEJB;
	@EJB
	private StkStepLookup stepLookup;
	@EJB
	private StkBatch batch;
	@EJB
	private StkPrcssgStepEJB prcssgStepEJB;
	@EJB
	private StkPrcssgStepLookup prcssgStepLookup;

	@Override
	protected CoreAbstEntityJobEJB<StkJob> getJobEjb() {
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
	protected CoreAbstEntityBatch<StkJob, StkStep, StkPrcssgStep> getBatch() {
		return batch;
	}

	@Override
	protected CoreAbstPrcssngStepEJB<StkPrcssgStep> getPrcssngStepEJB() {
		return prcssgStepEJB;
	}

	@Override
	protected CoreAbstPrcssngStepLookup<StkPrcssgStep> getPrcssngStepLookup() {
		return prcssgStepLookup;
	}

}
