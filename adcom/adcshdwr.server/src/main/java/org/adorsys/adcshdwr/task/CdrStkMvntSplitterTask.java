package org.adorsys.adcshdwr.task;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;

import org.adorsys.adcore.enums.CoreProcessStatusEnum;
import org.adorsys.adcore.event.EntityHstryEvent;
import org.adorsys.adcore.rest.CoreAbstEntityJobExecutor;
import org.adorsys.adcore.task.CoreAbstEntityBatch;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesHstry;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesItem;
import org.adorsys.adcshdwr.jpa.CdrJob;
import org.adorsys.adcshdwr.jpa.CdrPrcssgStep;
import org.adorsys.adcshdwr.jpa.CdrStep;
import org.adorsys.adcshdwr.rest.CdrBatch;
import org.adorsys.adcshdwr.rest.CdrDrctSalesItemLookup;
import org.adorsys.adcshdwr.rest.CdrJobEJB;
import org.adorsys.adcshdwr.rest.CdrStepEJB;
import org.adorsys.adcshdwr.rest.CdrStepLookup;

@Stateless
public class CdrStkMvntSplitterTask extends CoreAbstEntityJobExecutor<CdrJob, CdrStep, CdrPrcssgStep> {

	@EJB
	private CdrBatch batch;
	
	@EJB
	private CdrStkMvntSplitterTask task;

	@EJB
	private CdrStepEJB stepEJB;
	@EJB
	private CdrStepLookup stepLookup;
	@EJB
	private CdrJobEJB jobEJB;

	@EJB
	private CdrDrctSalesItemLookup itemLookup;
	
	@EJB
	private CdrStkMvntCreateExecTask execTask;

	public void handlePostedEvent(@Observes @EntityHstryEvent CdrDrctSalesHstry hstry){
		if(CoreProcessStatusEnum.POSTED.name().equals(hstry.getEntStatus())){
			createJob(hstry);
		}
	}
	
	@Override
	public void execute(String stepIdentif) {
		CdrStep step = stepLookup.findByIdentif(stepIdentif);
		if (step == null)return;
		splittInSteps(step);
		batch.terminateStep(stepIdentif);
	}

	private void splittInSteps(CdrStep s) {
		Long count = itemLookup.countByCntnrIdentifAndDisabledDtIsNull(s.getEntIdentif());
		int max = 100;
		if(count>10000){
			max = (int) (count/100);
		}
		int start = 0;
		while(start<count){
			int firstResult = start;
			start+=max;
			List<CdrDrctSalesItem> list = itemLookup.findByCntnrIdentifAndDisabledDtIsNullOrderByIdentifAsc(s.getEntIdentif(),firstResult, 1);
			CdrDrctSalesItem first = list.iterator().next();
			if(start<count){ // there is still another round. 
				list = itemLookup.findByCntnrIdentifAndDisabledDtIsNullOrderByIdentifAsc(s.getEntIdentif(), start-1, 1);
			} else {// start>=count. Fetch the last object of the list.
				list = itemLookup.findByCntnrIdentifAndDisabledDtIsNullOrderByIdentifDesc(s.getEntIdentif(), 0, 1);
			}
			CdrDrctSalesItem last = list.iterator().next();
			
			CdrStep step = new CdrStep();
			step.setCntnrIdentif(s.getCntnrIdentif());
			step.setEntIdentif(s.getEntIdentif());
			step.setExecutorId(execTask.getExecutorId());
			step.setSchdldStart(new Date());
			step.setStepStartId(first.getIdentif());
			step.setStepEndId(last.getIdentif());
			stepEJB.create(step);
		}
	}

	public void createJob(CdrDrctSalesHstry hstry){
		
		CdrJob job = new CdrJob();
		job.setHistIdentif(hstry.getId());
		job.setEntIdentif(hstry.getEntIdentif());
		job.setCntnrIdentif(hstry.getEntIdentif());
		job.setExecutorId(getClass().getSimpleName());
		job = jobEJB.create(job);
		
		CdrStep step = new CdrStep();
		step.setCntnrIdentif(job.getIdentif());
		step.setEntIdentif(hstry.getEntIdentif());
		step.setExecutorId(getClass().getSimpleName());
		step.setSchdldStart(new Date());
		stepEJB.create(step);
	}
	
	@Override
	protected CoreAbstEntityBatch<CdrJob, CdrStep, CdrPrcssgStep> getBatch() {
		return batch;
	}

	@Override
	protected CoreAbstEntityJobExecutor<CdrJob, CdrStep, CdrPrcssgStep> getEjb() {
		return task;
	}
}
