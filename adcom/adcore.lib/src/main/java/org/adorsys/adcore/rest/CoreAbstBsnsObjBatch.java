package org.adorsys.adcore.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.adorsys.adcore.enums.CoreJobExecutorIdEnum;
import org.adorsys.adcore.enums.CoreJobStatusEnum;
import org.adorsys.adcore.jpa.CoreAbstBsnsItem;
import org.adorsys.adcore.jpa.CoreAbstBsnsObject;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectHstry;
import org.adorsys.adcore.jpa.CoreAbstEntityCstr;
import org.adorsys.adcore.jpa.CoreAbstEntityJob;
import org.adorsys.adcore.jpa.CoreAbstEntityStep;
import org.apache.commons.lang3.time.DateUtils;

public abstract class CoreAbstBsnsObjBatch<E extends CoreAbstBsnsObject, I extends CoreAbstBsnsItem, 
	H extends CoreAbstBsnsObjectHstry, J extends CoreAbstEntityJob, S extends CoreAbstEntityStep, C extends CoreAbstEntityCstr> {

	protected abstract CoreAbstBsnsObjInjector<E, I, H, J, S, C> getInjector();
	protected abstract String getCurrentProcessIdentif();

	protected void executeSteps(){
		Long jobCount = getInjector().getJobLookup().countByJobStatusAndStartTimeLessThanAndEndTimeIsNull(CoreJobStatusEnum.PREPARED.name());
		if(jobCount<=0) return;
		int start = 0;
		int max = 100;
		Date now = new Date();
		while(start<jobCount){
			int firstResult = start;
			start +=max;
			List<J> jobs = getInjector().getJobLookup().findByJobStatusAndStartTimeLessThanAndEndTimeIsNull(CoreJobStatusEnum.INITIATED.name(), firstResult, max);
			for (J j : jobs) {
				Long stepCount = getInjector().getStepLookup().countByJobIdentifAndStartedIsNullAndSchdldStartLessThan(now);
				if(stepCount<=0) {	
					getInjector().getBatch().terminateJob(j);
					continue;
				}
				int stepStart = 0;
				int stepMax = 1;
				while(stepStart<stepCount){
					int stepFirstResult = start;
					stepStart +=stepMax;
					List<S> stepList = getInjector().getStepLookup().findByJobIdentifAndStartedIsNullAndSchdldStartLessThan(now, stepFirstResult, stepMax);
					for (S s : stepList) {
						getInjector().getBatch().processStep(s);
					}
				}
			}
		}
	}

	/*
	 * Purpose is the recovery of steps whoch where started, but could not be recovered.
	 * 
	 * A pending step has following caracteristics:
	 * 	- The step is started.
	 * 	- The leaseEnd is not null
	 * 	- The lease end is more that x minutes in the past.
	 */
	protected void recoverSteps(){
		Date now = new Date();
		Long count = getInjector().getStepLookup().countByStartedIsNotNullAndEndedIsNullAndLeaseEndLessThan(now);
		if(count<=0) return; // No step to recover
		for(int i =0; i < count; i++){
			List<S> l = getInjector().getStepLookup().findByStartedIsNotNullAndEndedIsNullAndLeaseEndLessThan(now, 0, 1);
			for (S s : l) {
				recoverStep(s);
			}
		}
	}
	
	private void processStep(S step){
		if(CoreJobExecutorIdEnum.bsnsItemEJB.name().equals(step.getExecutorId())){
			getInjector().getItemEjb().processStep(step);					
		} else if (CoreJobExecutorIdEnum.bsnsObjectEJB.name().equals(step.getExecutorId())){
			getInjector().getBsnsObjEjb().processStep(step);					
		} else if (CoreJobExecutorIdEnum.historyEJB.name().equals(step.getExecutorId())){
			getInjector().getHstrEjb().processStep(step);					
		} 
	}
	private void recoverStep(S step) {
		step = getInjector().getStepLookup().findByIdentif(step.getIdentif());// Refresh the
																// step object
		if (step.getEnded() != null)
			return;
		Date now = new Date();
		if (now.after(step.getLeaseEnd())) { // take over.
			processStep(step);
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void lease(String stepIdentifier, int seconds){
		S s = getInjector().getStepLookup().findByIdentif(stepIdentifier);
		s.setStepOwner(getCurrentProcessIdentif());
		Date now = new Date();
		s.setStarted(now );
		s.setLeaseEnd(DateUtils.addSeconds(now, seconds));
		getInjector().getStepEjb().update(s);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void terminateJob(J job){
		J j = getInjector().getJobLookup().findByIdentif(job.getIdentif());
		j.setEndTime(new Date());
		j.setJobStatus(CoreJobStatusEnum.TERMINATED.name());
		getInjector().getJobEjb().update(j);
	}
}
