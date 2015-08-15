package org.adorsys.adcore.rest;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adcore.jpa.CoreAbstEntityJob;
import org.adorsys.adcore.jpa.CoreAbstEntityStep;
import org.adorsys.adcore.jpa.CoreAbstPrcssngStep;
import org.adorsys.adcore.task.CoreAbstEntityBatch;

public abstract class CoreAbstEntityJobExecutor<J extends CoreAbstEntityJob, S extends CoreAbstEntityStep, PS extends CoreAbstPrcssngStep> implements CoreEntityJobExecutor<J, S> {

	protected abstract CoreAbstEntityBatch<J,S,PS> getBatch();

	protected abstract CoreAbstEntityJobExecutor<J,S,PS> getEjb();

	@Inject
	private CoreDefaultExecutionTimeEstimator timeEstimator;
	
	@PostConstruct
	public void postConstruct(){
		getBatch().registerJobExecutor(getClass().getSimpleName(), getEjb());
	}

	@Override
	public int estimateExecTimeMilisec(String stepIdentif){
		return timeEstimator.estimateExecTimeMilisec(stepIdentif);
	}
	
	public String getExecutorId(){
		return getClass().getSimpleName();
	}
}
