package org.adorsys.adcshdwr.rest;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.adorsys.adcore.rest.CoreAbstEntityJobEJB;
import org.adorsys.adcore.rest.CoreAbstEntityJobLookup;
import org.adorsys.adcore.rest.CoreAbstEntityStepEJB;
import org.adorsys.adcore.rest.CoreAbstEntityStepLookup;
import org.adorsys.adcore.rest.CoreAbstPrcssngStepEJB;
import org.adorsys.adcore.rest.CoreAbstPrcssngStepLookup;
import org.adorsys.adcore.task.CoreAbstEntityBatch;
import org.adorsys.adcshdwr.jpa.CdrJob;
import org.adorsys.adcshdwr.jpa.CdrPrcssgStep;
import org.adorsys.adcshdwr.jpa.CdrStep;

@Stateless
public class CdrBatch extends CoreAbstEntityBatch<CdrJob, CdrStep, CdrPrcssgStep> {

	@EJB
	private CdrJobEJB jobEJB;
	@EJB
	private CdrJobLookup jobLookup;
	@EJB
	private CdrStepEJB stepEJB;
	@EJB
	private CdrStepLookup stepLookup;
	@EJB
	private CdrBatch batch;
	@EJB
	private CdrPrcssgStepLookup prcssgStepLookup;
	@EJB
	private CdrPrcssgStepEJB prcssgStepEJB;
	
	@Override
	protected CoreAbstEntityJobEJB<CdrJob> getJobEjb() {
		return jobEJB;
	}

	@Override
	public CoreAbstEntityJobLookup<CdrJob> getJobLookup() {
		return jobLookup;
	}

	@Override
	public CoreAbstEntityStepEJB<CdrStep> getStepEjb() {
		return stepEJB;
	}

	@Override
	public CoreAbstEntityStepLookup<CdrStep> getStepLookup() {
		return stepLookup;
	}

	@Override
	protected CoreAbstEntityBatch<CdrJob, CdrStep, CdrPrcssgStep> getBatch() {
		return batch;
	}

	@Override
	protected CoreAbstPrcssngStepEJB<CdrPrcssgStep> getPrcssngStepEJB() {
		return prcssgStepEJB;
	}

	@Override
	protected CoreAbstPrcssngStepLookup<CdrPrcssgStep> getPrcssngStepLookup() {
		return prcssgStepLookup;
	}
}
