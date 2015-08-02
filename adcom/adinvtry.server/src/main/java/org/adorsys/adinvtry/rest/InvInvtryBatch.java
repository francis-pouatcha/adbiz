package org.adorsys.adinvtry.rest;

import javax.ejb.EJB;
import javax.ejb.Singleton;

import org.adorsys.adcore.rest.CoreAbstEntityJobEJB;
import org.adorsys.adcore.rest.CoreAbstEntityJobLookup;
import org.adorsys.adcore.rest.CoreAbstEntityStepEJB;
import org.adorsys.adcore.rest.CoreAbstEntityStepLookup;
import org.adorsys.adcore.rest.CoreAbstPrcssngStepEJB;
import org.adorsys.adcore.rest.CoreAbstPrcssngStepLookup;
import org.adorsys.adcore.task.CoreAbstEntityBatch;
import org.adorsys.adinvtry.jpa.InvJob;
import org.adorsys.adinvtry.jpa.InvPrcssgStep;
import org.adorsys.adinvtry.jpa.InvStep;

@Singleton
public class InvInvtryBatch extends CoreAbstEntityBatch<InvJob, InvStep, InvPrcssgStep> {

	@EJB
	private InvJobEJB jobEJB;
	@EJB
	private InvJobLookup jobLookup;
	@EJB
	private InvStepEJB stepEJB;
	@EJB
	private InvStepLookup stepLookup;
	@EJB
	private InvInvtryBatch batch;
	@EJB
	private InvPrcssgStepLookup prcssgStepLookup;
	@EJB
	private InvPrcssgStepEJB prcssgStepEJB;
	
	@Override
	protected CoreAbstEntityJobEJB<InvJob> getJobEjb() {
		return jobEJB;
	}

	@Override
	public CoreAbstEntityJobLookup<InvJob> getJobLookup() {
		return jobLookup;
	}

	@Override
	public CoreAbstEntityStepEJB<InvStep> getStepEjb() {
		return stepEJB;
	}

	@Override
	public CoreAbstEntityStepLookup<InvStep> getStepLookup() {
		return stepLookup;
	}

	@Override
	protected CoreAbstEntityBatch<InvJob, InvStep, InvPrcssgStep> getBatch() {
		return batch;
	}

	@Override
	protected CoreAbstPrcssngStepEJB<InvPrcssgStep> getPrcssngStepEJB() {
		return prcssgStepEJB;
	}

	@Override
	protected CoreAbstPrcssngStepLookup<InvPrcssgStep> getPrcssngStepLookup() {
		return prcssgStepLookup;
	}
}
