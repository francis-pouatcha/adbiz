package org.adorsys.adcore.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.adorsys.adcore.auth.TermWsUserPrincipal;
import org.adorsys.adcore.enums.CoreJobExecutorIdEnum;
import org.adorsys.adcore.enums.CoreJobStatusEnum;
import org.adorsys.adcore.jpa.CoreAbstBsnsItem;
import org.adorsys.adcore.jpa.CoreAbstBsnsObject;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectHstry;
import org.adorsys.adcore.jpa.CoreAbstEntityCstr;
import org.adorsys.adcore.jpa.CoreAbstEntityJob;
import org.adorsys.adcore.jpa.CoreAbstEntityStep;
import org.adorsys.adcore.repo.CoreAbstBsnsObjectRepo;
import org.apache.commons.lang3.time.DateUtils;

public abstract class CoreAbstBsnsItemBatch<E extends CoreAbstBsnsObject, I extends CoreAbstBsnsItem, 
	H extends CoreAbstBsnsObjectHstry, J extends CoreAbstEntityJob, S extends CoreAbstEntityStep, C extends CoreAbstEntityCstr> {

	protected abstract CoreAbstBsnsItemBatch<E, I, H, J, S, C> getBatch();

	protected abstract CoreAbstBsnsObjectRepo<E> getBsnsRepo();
	protected abstract CoreAbstBsnsObjectLookup<E> getLookup();
	protected abstract CoreAbstBsnsObjectEJB<E, I, H, J, S, C> getEjb();
	protected abstract CoreAbstBsnsItemLookup<I> getItemLookup();
	protected abstract CoreAbstBsnsItemEJB<E, I, H, J, S, C> getItemEjb();
	protected abstract CoreAbstBsnsObjectHstryLookup<H> getHistoryLookup();
	protected abstract CoreAbstBsnsObjectHstryEJB<E, I, H, J, S, C> getHistoryEjb();
	protected abstract String getSequenceGeneratorPrefix();
	protected abstract TermWsUserPrincipal getCallerPrincipal();
	protected abstract String prinHstryInfo(E entity);
	protected abstract CoreAbstBsnsObjLifeCycle<H> getLifeCycle();
	protected abstract CoreAbstEntityJobEJB<J> getJobEjb();
	protected abstract CoreAbstEntityJobLookup<J> getJobLookup();
	protected abstract CoreAbstEntityStepEJB<S> getStepEjb();
	protected abstract CoreAbstEntityStepLookup<S> getStepLookup();
	protected abstract CoreAbstEntityCstrEJB<C> getCstrEjb();
	protected abstract CoreAbstEntityCstrLookup<C> getCstrLookup();
	protected abstract String getCurrentProcessIdentif();

	protected void executeSteps(){
		Long jobCount = getJobLookup().countByJobStatusAndStartTimeLessThanAndEndTimeIsNull(CoreJobStatusEnum.PREPARED.name());
		if(jobCount<=0) return;
		int start = 0;
		int max = 100;
		Date now = new Date();
		while(start<jobCount){
			int firstResult = start;
			start +=max;
			List<J> jobs = getJobLookup().findByJobStatusAndStartTimeLessThanAndEndTimeIsNull(CoreJobStatusEnum.INITIATED.name(), firstResult, max);
			for (J j : jobs) {
				Long stepCount = getStepLookup().countByJobIdentifAndStartedIsNullAndSchdldStartLessThan(now);
				if(stepCount<=0) {	
					getBatch().terminateJob(j);
					continue;
				}
				int stepStart = 0;
				int stepMax = 1;
				while(stepStart<stepCount){
					int stepFirstResult = start;
					stepStart +=stepMax;
					List<S> stepList = getStepLookup().findByJobIdentifAndStartedIsNullAndSchdldStartLessThan(now, stepFirstResult, stepMax);
					for (S s : stepList) {
						getBatch().processStep(s);
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
		Long count = getStepLookup().countByStartedIsNotNullAndEndedIsNullAndLeaseEndLessThan(now);
		if(count<=0) return; // No step to recover
		for(int i =0; i < count; i++){
			List<S> l = getStepLookup().findByStartedIsNotNullAndEndedIsNullAndLeaseEndLessThan(now, 0, 1);
			for (S s : l) {
				recoverStep(s);
			}
		}
	}
	
	private void processStep(S step){
		if(CoreJobExecutorIdEnum.bsnsItemEJB.name().equals(step.getExecutorId())){
			getItemEjb().processStep(step);					
		} else if (CoreJobExecutorIdEnum.bsnsObjectEJB.name().equals(step.getExecutorId())){
			getEjb().processStep(step);					
		} else if (CoreJobExecutorIdEnum.historyEJB.name().equals(step.getExecutorId())){
			getHistoryEjb().processStep(step);					
		} 
	}
	private void recoverStep(S step) {
		step = getStepLookup().findByIdentif(step.getIdentif());// Refresh the
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
		S s = getStepLookup().findByIdentif(stepIdentifier);
		s.setStepOwner(getCurrentProcessIdentif());
		Date now = new Date();
		s.setStarted(now );
		s.setLeaseEnd(DateUtils.addSeconds(now, seconds));
		getStepEjb().update(s);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void terminateJob(J job){
		J j = getJobLookup().findByIdentif(job.getIdentif());
		j.setEndTime(new Date());
		j.setJobStatus(CoreJobStatusEnum.TERMINATED.name());
		getJobEjb().update(j);
	}
}
