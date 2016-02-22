package org.adorsys.adprocmt.rest;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.adorsys.adcore.rest.CoreAbstEntityJobEJB;
import org.adorsys.adcore.rest.CoreAbstEntityJobLookup;
import org.adorsys.adcore.rest.CoreAbstEntityStepEJB;
import org.adorsys.adcore.rest.CoreAbstEntityStepLookup;
import org.adorsys.adcore.rest.CoreAbstPrcssngStepEJB;
import org.adorsys.adcore.rest.CoreAbstPrcssngStepLookup;
import org.adorsys.adcore.task.CoreAbstEntityBatch;
import org.adorsys.adprocmt.jpa.PrcmtJob;
import org.adorsys.adprocmt.jpa.PrcmtPrcssgStep;
import org.adorsys.adprocmt.jpa.PrcmtStep;

@Stateless
public class PrcmtBatch extends CoreAbstEntityBatch<PrcmtJob, PrcmtStep, PrcmtPrcssgStep> {

	@EJB
	private PrcmtJobEJB jobEJB;
	@EJB
	private PrcmtJobLookup jobLookup;
	@EJB
	private PrcmtStepEJB stepEJB;
	@EJB
	private PrcmtStepLookup stepLookup;
	@EJB
	private PrcmtBatch batch;
	@EJB
	private PrcmtPrcssgStepLookup prcssgStepLookup;
	@EJB
	private PrcmtPrcssgStepEJB prcssgStepEJB;
	
	@Override
	protected CoreAbstEntityJobEJB<PrcmtJob> getJobEjb() {
		return jobEJB;
	}

	@Override
	public CoreAbstEntityJobLookup<PrcmtJob> getJobLookup() {
		return jobLookup;
	}

	@Override
	public CoreAbstEntityStepEJB<PrcmtStep> getStepEjb() {
		return stepEJB;
	}

	@Override
	public CoreAbstEntityStepLookup<PrcmtStep> getStepLookup() {
		return stepLookup;
	}

	@Override
	protected CoreAbstEntityBatch<PrcmtJob, PrcmtStep, PrcmtPrcssgStep> getBatch() {
		return batch;
	}

	@Override
	protected CoreAbstPrcssngStepEJB<PrcmtPrcssgStep> getPrcssngStepEJB() {
		return prcssgStepEJB;
	}

	@Override
	protected CoreAbstPrcssngStepLookup<PrcmtPrcssgStep> getPrcssngStepLookup() {
		return prcssgStepLookup;
	}
}
