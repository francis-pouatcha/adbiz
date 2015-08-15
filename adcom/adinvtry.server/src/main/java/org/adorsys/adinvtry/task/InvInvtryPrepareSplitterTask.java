package org.adorsys.adinvtry.task;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.rest.CoreAbstEntityJobExecutor;
import org.adorsys.adcore.task.CoreAbstEntityBatch;
import org.adorsys.adinvtry.jpa.InvJob;
import org.adorsys.adinvtry.jpa.InvPrcssgStep;
import org.adorsys.adinvtry.jpa.InvStep;
import org.adorsys.adinvtry.rest.InvInvtryBatch;
import org.adorsys.adinvtry.rest.InvJobEJB;
import org.adorsys.adinvtry.rest.InvStepEJB;
import org.adorsys.adinvtry.rest.InvStepLookup;
import org.adorsys.adstock.jpa.StkArticleLot2StrgSctn;
import org.adorsys.adstock.rest.StkArticleLot2StrgSctnLookup;

@Stateless
public class InvInvtryPrepareSplitterTask extends CoreAbstEntityJobExecutor<InvJob, InvStep, InvPrcssgStep> {

	@EJB
	private InvInvtryBatch batch;
	
	@EJB
	private InvInvtryPrepareSplitterTask splitter;
	
	@Inject
	private StkArticleLot2StrgSctnLookup lot2SctLookup;
	@Inject
	private InvStepEJB stepEJB;
	@Inject
	private InvStepLookup stepLookup;
	@Inject
	private InvJobEJB jobEJB;
	
	@EJB
	private InvInvtryPrepareExecTask execTask;

	@Override
	public void execute(String stepIdentif) {
		InvStep step = stepLookup.findByIdentif(stepIdentif);
		if (step == null)return;
		splittInSteps(step);
		batch.terminateStep(stepIdentif);
	}

	private void splittInSteps(InvStep s) {
		Long count = lot2SctLookup.countByClosedDtIsNull();
		int max = 100;
		if(count>10000){
			max = (int) (count/100);
		}
		int start = 0;
		while(start<count){
			int firstResult = start;
			start+=max;
			List<StkArticleLot2StrgSctn> list = lot2SctLookup.findByClosedDtIsNullAsc(firstResult, 1);
			StkArticleLot2StrgSctn first = list.iterator().next();
			if(start<count){ // there is still another round. 
				list = lot2SctLookup.findByClosedDtIsNullAsc(start-1, 1);
			} else {// start>=count. Fetch the last object of the list.
				list = lot2SctLookup.findByClosedDtIsNullAsc(0, 1);
			}
			StkArticleLot2StrgSctn last = list.iterator().next();
			
			InvStep step = new InvStep();
			step.setCntnrIdentif(s.getCntnrIdentif());
			step.setEntIdentif(s.getEntIdentif());
			step.setExecutorId(execTask.getExecutorId());
			step.setSchdldStart(new Date());
			step.setStepStartId(first.getIdentif());
			step.setStepEndId(last.getIdentif());
			stepEJB.create(step);
		}
	}

	public void createPrepareJob(String identif){
		
		InvJob job = new InvJob();
		job.setEntIdentif(identif);
		job.setCntnrIdentif(identif);
		job.setExecutorId(getClass().getSimpleName());
		job = jobEJB.create(job);
		
		InvStep step = new InvStep();
		step.setCntnrIdentif(job.getIdentif());
		step.setEntIdentif(identif);
		step.setExecutorId(getClass().getSimpleName());
		step.setSchdldStart(new Date());
		stepEJB.create(step);
	}
	
	@Override
	protected CoreAbstEntityBatch<InvJob, InvStep, InvPrcssgStep> getBatch() {
		return batch;
	}

	@Override
	protected CoreAbstEntityJobExecutor<InvJob, InvStep, InvPrcssgStep> getEjb() {
		return splitter;
	}
}
