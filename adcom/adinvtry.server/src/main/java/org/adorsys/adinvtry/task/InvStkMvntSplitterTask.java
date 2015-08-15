package org.adorsys.adinvtry.task;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;

import org.adorsys.adcore.enums.CoreProcessStatusEnum;
import org.adorsys.adcore.event.EntityHstryEvent;
import org.adorsys.adcore.rest.CoreAbstEntityJobExecutor;
import org.adorsys.adcore.task.CoreAbstEntityBatch;
import org.adorsys.adinvtry.jpa.InvInvtryHstry;
import org.adorsys.adinvtry.jpa.InvInvtryItem;
import org.adorsys.adinvtry.jpa.InvJob;
import org.adorsys.adinvtry.jpa.InvPrcssgStep;
import org.adorsys.adinvtry.jpa.InvStep;
import org.adorsys.adinvtry.rest.InvInvtryBatch;
import org.adorsys.adinvtry.rest.InvInvtryItemLookup;
import org.adorsys.adinvtry.rest.InvJobEJB;
import org.adorsys.adinvtry.rest.InvStepEJB;
import org.adorsys.adinvtry.rest.InvStepLookup;

@Stateless
public class InvStkMvntSplitterTask extends CoreAbstEntityJobExecutor<InvJob, InvStep, InvPrcssgStep> {

	@EJB
	private InvInvtryBatch batch;
	
	@EJB
	private InvStkMvntSplitterTask task;

	@EJB
	private InvStepEJB stepEJB;
	@EJB
	private InvStepLookup stepLookup;
	@EJB
	private InvJobEJB jobEJB;

	@EJB
	private InvInvtryItemLookup itemLookup;
	
	@EJB
	private InvStkMvntCreateExecTask execTask;

	public void handlePostedEvent(@Observes @EntityHstryEvent InvInvtryHstry hstry){
		if(CoreProcessStatusEnum.POSTED.name().equals(hstry.getEntStatus())){
			createJob(hstry);
		}
	}
	
	@Override
	public void execute(String stepIdentif) {
		InvStep step = stepLookup.findByIdentif(stepIdentif);
		if (step == null)return;
		splittInSteps(step);
		batch.terminateStep(stepIdentif);
	}

	private void splittInSteps(InvStep s) {
		Long count = itemLookup.countByCntnrIdentifAndDisabledDtIsNull(s.getEntIdentif());
		int max = 100;
		if(count>10000){
			max = (int) (count/100);
		}
		int start = 0;
		while(start<count){
			int firstResult = start;
			start+=max;
			List<InvInvtryItem> list = itemLookup.findByCntnrIdentifAndDisabledDtIsNullOrderByIdentifAsc(s.getEntIdentif(),firstResult, 1);
			InvInvtryItem first = list.iterator().next();
			if(start<count){ // there is still another round. 
				list = itemLookup.findByCntnrIdentifAndDisabledDtIsNullOrderByIdentifAsc(s.getEntIdentif(), start-1, 1);
			} else {// start>=count. Fetch the last object of the list.
				list = itemLookup.findByCntnrIdentifAndDisabledDtIsNullOrderByIdentifDesc(s.getEntIdentif(), 0, 1);
			}
			InvInvtryItem last = list.iterator().next();
			
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

	public void createJob(InvInvtryHstry hstry){
		
		InvJob job = new InvJob();
		job.setHistIdentif(hstry.getId());
		job.setEntIdentif(hstry.getEntIdentif());
		job.setCntnrIdentif(hstry.getEntIdentif());
		job.setExecutorId(getClass().getSimpleName());
		job = jobEJB.create(job);
		
		InvStep step = new InvStep();
		step.setCntnrIdentif(job.getIdentif());
		step.setEntIdentif(hstry.getEntIdentif());
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
		return task;
	}
}
