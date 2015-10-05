package org.adorsys.adcost.task;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;

import org.adorsys.adcore.enums.CoreProcessStatusEnum;
import org.adorsys.adcore.event.EntityHstryEvent;
import org.adorsys.adcore.rest.CoreAbstEntityJobExecutor;
import org.adorsys.adcore.task.CoreAbstEntityBatch;
import org.adorsys.adcost.ejb.CstJobEJB;
import org.adorsys.adcost.ejb.CstStepEJB;
import org.adorsys.adcost.ejb.CstStepLookup;
import org.adorsys.adcost.ejb.CstStmntBatch;
import org.adorsys.adcost.ejb.CstStmntItemLookup;
import org.adorsys.adcost.jpa.CstJob;
import org.adorsys.adcost.jpa.CstPrcssgStep;
import org.adorsys.adcost.jpa.CstStep;
import org.adorsys.adcost.jpa.CstStmntHstry;
import org.adorsys.adcost.jpa.CstStmntItem;

@Stateless
public class CstStkMvntSplitterTask extends CoreAbstEntityJobExecutor<CstJob, CstStep, CstPrcssgStep> {

	@EJB
	private CstStmntBatch batch;
	
	@EJB
	private CstStkMvntSplitterTask task;

	@EJB
	private CstStepEJB stepEJB;
	@EJB
	private CstStepLookup stepLookup;
	@EJB
	private CstJobEJB jobEJB;

	@EJB
	private CstStmntItemLookup itemLookup;
	
	@EJB
	private CstStkMvntCreateExecTask execTask;

	public void handlePostedEvent(@Observes @EntityHstryEvent CstStmntHstry hstry){
		if(CoreProcessStatusEnum.POSTED.name().equals(hstry.getEntStatus())){
			createJob(hstry);
		}
	}
	
	@Override
	public void execute(String stepIdentif) {
		CstStep step = stepLookup.findByIdentif(stepIdentif);
		if (step == null)return;
		splittInSteps(step);
		batch.terminateStep(stepIdentif);
	}

	private void splittInSteps(CstStep s) {
		Long count = itemLookup.countByCntnrIdentifAndDisabledDtIsNull(s.getEntIdentif());
		int max = 100;
		if(count>10000){
			max = (int) (count/100);
		}
		int start = 0;
		while(start<count){
			int firstResult = start;
			start+=max;
			List<CstStmntItem> list = itemLookup.findByCntnrIdentifAndDisabledDtIsNullOrderByIdentifAsc(s.getEntIdentif(),firstResult, 1);
			CstStmntItem first = list.iterator().next();
			if(start<count){ // there is still another round. 
				list = itemLookup.findByCntnrIdentifAndDisabledDtIsNullOrderByIdentifAsc(s.getEntIdentif(), start-1, 1);
			} else {// start>=count. Fetch the last object of the list.
				list = itemLookup.findByCntnrIdentifAndDisabledDtIsNullOrderByIdentifDesc(s.getEntIdentif(), 0, 1);
			}
			CstStmntItem last = list.iterator().next();
			
			CstStep step = new CstStep();
			step.setCntnrIdentif(s.getCntnrIdentif());
			step.setEntIdentif(s.getEntIdentif());
			step.setExecutorId(execTask.getExecutorId());
			step.setSchdldStart(new Date());
			step.setStepStartId(first.getIdentif());
			step.setStepEndId(last.getIdentif());
			stepEJB.create(step);
		}
	}

	public void createJob(CstStmntHstry hstry){
		
		CstJob job = new CstJob();
		job.setHistIdentif(hstry.getId());
		job.setEntIdentif(hstry.getEntIdentif());
		job.setCntnrIdentif(hstry.getEntIdentif());
		job.setExecutorId(getClass().getSimpleName());
		job = jobEJB.create(job);
		
		CstStep step = new CstStep();
		step.setCntnrIdentif(job.getIdentif());
		step.setEntIdentif(hstry.getEntIdentif());
		step.setExecutorId(getClass().getSimpleName());
		step.setSchdldStart(new Date());
		stepEJB.create(step);
	}
	
	@Override
	protected CoreAbstEntityBatch<CstJob, CstStep, CstPrcssgStep> getBatch() {
		return batch;
	}

	@Override
	protected CoreAbstEntityJobExecutor<CstJob, CstStep, CstPrcssgStep> getEjb() {
		return task;
	}
}
