package org.adorsys.adcore.loader.ejb;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.adorsys.adcore.loader.jpa.CorLdrJob;
import org.adorsys.adcore.loader.jpa.CorLdrPrcssngStep;
import org.adorsys.adcore.loader.jpa.CorLdrStep;
import org.adorsys.adcore.rest.CoreAbstEntityJobEJB;
import org.adorsys.adcore.rest.CoreAbstEntityJobLookup;
import org.adorsys.adcore.rest.CoreAbstEntityStepEJB;
import org.adorsys.adcore.rest.CoreAbstEntityStepLookup;
import org.adorsys.adcore.rest.CoreAbstPrcssngStepEJB;
import org.adorsys.adcore.rest.CoreAbstPrcssngStepLookup;
import org.adorsys.adcore.task.CoreAbstEntityBatch;

@Stateless
public class CorLdrBatch extends CoreAbstEntityBatch<CorLdrJob, CorLdrStep, CorLdrPrcssngStep> {

	@EJB
	private CorLdrJobEJB jobEJB;
	@EJB
	private CorLdrJobLookup jobLookup;

	@EJB
	private CorLdrStepEJB stepEJB;
	@EJB
	private CorLdrStepLookup stepLookup;

	@EJB
	private CorLdrBatch batch;

	@EJB
	private CorLdrPrcssngStepEJB prcssngStepEJB;
	@EJB
	private CorLdrPrcssngStepLookup prcssngStepLookup;
	
	@Override
	protected CoreAbstEntityJobEJB<CorLdrJob> getJobEjb() {
		return jobEJB;
	}

	@Override
	protected CoreAbstEntityJobLookup<CorLdrJob> getJobLookup() {
		return jobLookup;
	}

	@Override
	protected CoreAbstEntityStepEJB<CorLdrStep> getStepEjb() {
		return stepEJB;
	}

	@Override
	protected CoreAbstEntityStepLookup<CorLdrStep> getStepLookup() {
		return stepLookup;
	}

	@Override
	protected CoreAbstEntityBatch<CorLdrJob, CorLdrStep, CorLdrPrcssngStep> getBatch() {
		return batch;
	}

	@Override
	protected CoreAbstPrcssngStepEJB<CorLdrPrcssngStep> getPrcssngStepEJB() {
		return prcssngStepEJB;
	}

	@Override
	protected CoreAbstPrcssngStepLookup<CorLdrPrcssngStep> getPrcssngStepLookup() {
		return prcssngStepLookup;
	}

}
