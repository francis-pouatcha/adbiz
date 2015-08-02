package org.adorsys.adcore.loader.ejb;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.adorsys.adcore.loader.jpa.CorLdrJob;
import org.adorsys.adcore.loader.jpa.CorLdrStep;
import org.adorsys.adcore.rest.CoreAbstEntityStepEJB;
import org.adorsys.adcore.rest.CoreAbstEntityStepLookup;
import org.adorsys.adcore.task.CoreAbstStepCallback;

@Stateless
public class CorLdrStepCallback extends CoreAbstStepCallback<CorLdrJob, CorLdrStep> {

	@EJB
	private CorLdrStepLookup stepLookup;
	@EJB
	private CorLdrStepEJB stepEJB;
	@EJB
	private CorLdrBatch batch;

	@Override
	public void done(String stepIdentif) {
		batch.terminateStep(stepIdentif);
	}

	@Override
	protected CoreAbstEntityStepLookup<CorLdrStep> getStelLookup() {
		return stepLookup;
	}

	@Override
	protected CoreAbstEntityStepEJB<CorLdrStep> getStelEJB() {
		return stepEJB;
	}

}
