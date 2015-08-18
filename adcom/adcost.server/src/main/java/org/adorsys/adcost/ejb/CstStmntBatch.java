package org.adorsys.adcost.ejb;

import javax.ejb.EJB;
import javax.ejb.Singleton;

import org.adorsys.adcore.rest.CoreAbstEntityJobEJB;
import org.adorsys.adcore.rest.CoreAbstEntityJobLookup;
import org.adorsys.adcore.rest.CoreAbstEntityStepEJB;
import org.adorsys.adcore.rest.CoreAbstEntityStepLookup;
import org.adorsys.adcore.rest.CoreAbstPrcssngStepEJB;
import org.adorsys.adcore.rest.CoreAbstPrcssngStepLookup;
import org.adorsys.adcore.task.CoreAbstEntityBatch;
import org.adorsys.adcost.ejb.CstJobLookup;
import org.adorsys.adcost.ejb.CstPrcssgStepLookup;
import org.adorsys.adcost.ejb.CstStepLookup;
import org.adorsys.adcost.jpa.CstJob;
import org.adorsys.adcost.jpa.CstPrcssgStep;
import org.adorsys.adcost.jpa.CstStep;

@Singleton
public class CstStmntBatch extends CoreAbstEntityBatch<CstJob, CstStep, CstPrcssgStep> {

	@EJB
	private CstJobEJB jobEJB;
	@EJB
	private CstJobLookup jobLookup;
	@EJB
	private CstStepEJB stepEJB;
	@EJB
	private CstStepLookup stepLookup;
	@EJB
	private CstStmntBatch batch;
	@EJB
	private CstPrcssgStepLookup prcssgStepLookup;
	@EJB
	private CstPrcssgStepEJB prcssgStepEJB;
	
	@Override
	protected CoreAbstEntityJobEJB<CstJob> getJobEjb() {
		return jobEJB;
	}

	@Override
	public CoreAbstEntityJobLookup<CstJob> getJobLookup() {
		return jobLookup;
	}

	@Override
	public CoreAbstEntityStepEJB<CstStep> getStepEjb() {
		return stepEJB;
	}

	@Override
	public CoreAbstEntityStepLookup<CstStep> getStepLookup() {
		return stepLookup;
	}

	@Override
	protected CoreAbstEntityBatch<CstJob, CstStep, CstPrcssgStep> getBatch() {
		return batch;
	}

	@Override
	protected CoreAbstPrcssngStepEJB<CstPrcssgStep> getPrcssngStepEJB() {
		return prcssgStepEJB;
	}

	@Override
	protected CoreAbstPrcssngStepLookup<CstPrcssgStep> getPrcssngStepLookup() {
		return prcssgStepLookup;
	}
}
