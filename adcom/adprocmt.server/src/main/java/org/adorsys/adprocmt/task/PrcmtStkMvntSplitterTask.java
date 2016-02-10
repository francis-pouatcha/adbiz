package org.adorsys.adprocmt.task;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;

import org.adorsys.adcore.enums.CoreProcessStatusEnum;
import org.adorsys.adcore.event.EntityHstryEvent;
import org.adorsys.adcore.rest.CoreAbstEntityJobExecutor;
import org.adorsys.adcore.task.CoreAbstEntityBatch;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryHstry;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem;
import org.adorsys.adprocmt.jpa.PrcmtJob;
import org.adorsys.adprocmt.jpa.PrcmtPrcssgStep;
import org.adorsys.adprocmt.jpa.PrcmtStep;
import org.adorsys.adprocmt.rest.PrcmtBatch;
import org.adorsys.adprocmt.rest.PrcmtDlvryItemLookup;
import org.adorsys.adprocmt.rest.PrcmtJobEJB;
import org.adorsys.adprocmt.rest.PrcmtStepEJB;
import org.adorsys.adprocmt.rest.PrcmtStepLookup;

@Stateless
public class PrcmtStkMvntSplitterTask extends CoreAbstEntityJobExecutor<PrcmtJob, PrcmtStep, PrcmtPrcssgStep> {

	@EJB
	private PrcmtBatch batch;
	
	@EJB
	private PrcmtStkMvntSplitterTask task;

	@EJB
	private PrcmtStepEJB stepEJB;
	@EJB
	private PrcmtStepLookup stepLookup;
	@EJB
	private PrcmtJobEJB jobEJB;

	@EJB
	private PrcmtDlvryItemLookup itemLookup;
	
	@EJB
	private PrcmtStkMvntCreateExecTask execTask;

	public void handlePostedEvent(@Observes @EntityHstryEvent PrcmtDeliveryHstry hstry){
		if(CoreProcessStatusEnum.POSTED.name().equals(hstry.getEntStatus())){
			createJob(hstry);
		}
	}
	
	@Override
	public void execute(String stepIdentif) {
		PrcmtStep step = stepLookup.findByIdentif(stepIdentif);
		if (step == null)return;
		splittInSteps(step);
		batch.terminateStep(stepIdentif);
	}

	private void splittInSteps(PrcmtStep s) {
		Long count = itemLookup.countByCntnrIdentifAndDisabledDtIsNull(s.getEntIdentif());
		int max = 100;
		if(count>10000){
			max = (int) (count/100);
		}
		int start = 0;
		while(start<count){
			int firstResult = start;
			start+=max;
			List<PrcmtDlvryItem> list = itemLookup.findByCntnrIdentifAndDisabledDtIsNullOrderByIdentifAsc(s.getEntIdentif(),firstResult, 1);
			PrcmtDlvryItem first = list.iterator().next();
			if(start<count){ // there is still another round. 
				list = itemLookup.findByCntnrIdentifAndDisabledDtIsNullOrderByIdentifAsc(s.getEntIdentif(), start-1, 1);
			} else {// start>=count. Fetch the last object of the list.
				list = itemLookup.findByCntnrIdentifAndDisabledDtIsNullOrderByIdentifDesc(s.getEntIdentif(), 0, 1);
			}
			PrcmtDlvryItem last = list.iterator().next();
			
			PrcmtStep step = new PrcmtStep();
			step.setCntnrIdentif(s.getCntnrIdentif());
			step.setEntIdentif(s.getEntIdentif());
			step.setExecutorId(execTask.getExecutorId());
			step.setSchdldStart(new Date());
			step.setStepStartId(first.getIdentif());
			step.setStepEndId(last.getIdentif());
			stepEJB.create(step);
		}
	}

	public void createJob(PrcmtDeliveryHstry hstry){
		
		PrcmtJob job = new PrcmtJob();
		job.setHistIdentif(hstry.getId());
		job.setEntIdentif(hstry.getEntIdentif());
		job.setCntnrIdentif(hstry.getEntIdentif());
		job.setExecutorId(getClass().getSimpleName());
		job = jobEJB.create(job);
		
		PrcmtStep step = new PrcmtStep();
		step.setCntnrIdentif(job.getIdentif());
		step.setEntIdentif(hstry.getEntIdentif());
		step.setExecutorId(getClass().getSimpleName());
		step.setSchdldStart(new Date());
		stepEJB.create(step);
	}
	
	@Override
	protected CoreAbstEntityBatch<PrcmtJob, PrcmtStep, PrcmtPrcssgStep> getBatch() {
		return batch;
	}

	@Override
	protected CoreAbstEntityJobExecutor<PrcmtJob, PrcmtStep, PrcmtPrcssgStep> getEjb() {
		return task;
	}
}
