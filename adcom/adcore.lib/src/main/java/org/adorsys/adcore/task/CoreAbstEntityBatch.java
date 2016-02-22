package org.adorsys.adcore.task;

import java.util.Date;
import java.util.List;

import javax.ejb.Asynchronous;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.LockModeType;
import javax.persistence.LockTimeoutException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;

import org.adorsys.adcore.enums.CoreJobStatusEnum;
import org.adorsys.adcore.jpa.CoreAbstEntityJob;
import org.adorsys.adcore.jpa.CoreAbstEntityStep;
import org.adorsys.adcore.jpa.CoreAbstPrcssngStep;
import org.adorsys.adcore.rest.CoreAbstEntityJobEJB;
import org.adorsys.adcore.rest.CoreAbstEntityJobLookup;
import org.adorsys.adcore.rest.CoreAbstEntityStepEJB;
import org.adorsys.adcore.rest.CoreAbstEntityStepLookup;
import org.adorsys.adcore.rest.CoreAbstPrcssngStepEJB;
import org.adorsys.adcore.rest.CoreAbstPrcssngStepLookup;
import org.adorsys.adcore.rest.CoreEntityJobExecutor;
import org.adorsys.adcore.utils.RandomMilisec;
import org.apache.commons.lang3.time.DateUtils;

/**
 * Subclasses of this must be singletons
 * 
 * @author francis
 *
 * @param <J>
 * @param <S>
 */
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Lock(LockType.READ)
public abstract class CoreAbstEntityBatch<J extends CoreAbstEntityJob, S extends CoreAbstEntityStep, PS extends CoreAbstPrcssngStep> {
	
	@Inject
	private CoreProcessIdHolder processIdHolder;
	
	@Inject
	private CoreBatchTaskSynchronizer taskSynchronizer;
	
	protected abstract CoreAbstEntityJobEJB<J> getJobEjb();
	protected abstract CoreAbstEntityJobLookup<J> getJobLookup();
	protected abstract CoreAbstEntityStepEJB<S> getStepEjb();
	protected abstract CoreAbstEntityStepLookup<S> getStepLookup();
	protected abstract CoreAbstEntityBatch<J,S,PS> getBatch();
	protected abstract CoreAbstPrcssngStepEJB<PS> getPrcssngStepEJB();
	protected abstract CoreAbstPrcssngStepLookup<PS> getPrcssngStepLookup();

	public String getCurrentProcessIdentif() {
		return processIdHolder.getProcessId();
	}
	
	public void registerJobExecutor(String executorId, CoreEntityJobExecutor<J,S> executor){
		taskSynchronizer.registerJobExecutor(executorId, executor);
	}
	
	@SuppressWarnings({"unchecked" })
	private CoreEntityJobExecutor<J,S> getExecutor(String executorId){
		Object executor = taskSynchronizer.getExecutor(executorId);
		if(executor==null) return null;
		return (CoreEntityJobExecutor<J, S>) executor;
	}
	

	@Schedule(minute = "*", second="*/5", hour="*")
	public void processSteps() throws Exception {
		getBatch().processStepsAsynch();
	}

	@Asynchronous
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void processStepsAsynch() throws Exception {
		
		// Return if another job running.
		if (!taskSynchronizer.processStepsBusyCompareAndSet(false, true)) {
            return;
        }
		
		try {
			RandomMilisec.doWait(3);
			Date now = new Date();
			Long count = getStepLookup().countByStartedIsNullAndSchdldStartLessThan(now);
			int start = 0;
			int max = 100;
			while(start<count){
				int firstResult = start;
				start+=max;
				List<S> schdledList = getStepLookup().findByStartedIsNullAndSchdldStartLessThan(now, firstResult, max);
				for (S step : schdledList) {
					if(step.getEnded()!=null || step.getStarted()!=null) continue;
					if(step.getSchdldStart()!=null && step.getSchdldStart().after(now)) continue;
					doExecuteStep(step);
				}
			}
		} finally {
			taskSynchronizer.processStepsBusySet(false);
		}
	}
	
	private void doExecuteStep(S step){
		J job = getJobLookup().findByIdentif(step.getCntnrIdentif());
		if(job==null || job.hasTerminated()){// mark step as done.
			if(step.getEnded()==null) 
				getBatch().terminateStep(step.getIdentif());
			return;
		}
		if(job.canExecute()){
			CoreEntityJobExecutor<J, S> executor = getExecutor(step.getExecutorId());
			if(executor==null) return;
			
			int execTimeMilisec = executor.estimateExecTimeMilisec(step.getIdentif());
			boolean leased = getBatch().lease(step.getIdentif(), execTimeMilisec);
			if(!leased) return;
			if(job.getStartTime()==null){
				job.setStartTime(new Date());
				job.setJobStatus(CoreJobStatusEnum.RUNNING.name());
				getJobEjb().update(job);
			}
			executor.execute(step.getIdentif());
		}
	}

	/**
	 * Check for terminated jobs and schedule their removal them. A job has no step when all steps
	 * have been executed.
	 *  
	 */
	@Schedule(minute = "*/5", hour="*")
	public void cleanupJobs() throws Exception {
		getBatch().cleanupJobsAsynch();
	}
	
	@Asynchronous
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void cleanupJobsAsynch() throws Exception {
		// Return if another job running.
		if (!taskSynchronizer.cleanupJobsBusyCompareAndSet(false, true)) {
            return;
        }
		
		try {
			Long count = getJobLookup().countByJobStatus(CoreJobStatusEnum.TERMINATED.name());
			int start = 0;
			int max = 100;
			while(start<count){
				int firstResult = start;
				start+=max;
				List<J> terminatedJobs = getJobLookup().findByJobStatus(CoreJobStatusEnum.TERMINATED.name(), firstResult, max);
				for (J j : terminatedJobs) {
					getJobEjb().deleteById(j.getId());
				}
			}
		} finally {
			taskSynchronizer.cleanupJobsBusySet(false);
		}
	}
	
	/**
	 * Obtains a lock to process the step.
	 * 
	 * @param stepIdentifier : The step identifier.
	 * @param execTimeMilisec : The estimated execution time in millisec.
	 * @return
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean lease(String stepIdentifier, int execTimeMilisec){
		boolean locked = getBatch().lock(stepIdentifier);
		if(!locked) return false;
		
		S s = getStepLookup().findByIdentif(stepIdentifier);
		Date now = new Date();
		if(s.getLeaseEnd()!=null && s.getLeaseEnd().after(now)) {
			getBatch().unLock(stepIdentifier);
			return false;
		}
		
		s.setStepOwner(getCurrentProcessIdentif());
		s.setStarted(now);
		s.setLeaseEnd(DateUtils.addMilliseconds(now, execTimeMilisec));
		getStepEjb().update(s);
		
		getBatch().unLock(stepIdentifier);
		return true;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public boolean reschedule(String stepIdentifier, int inTimeMilisec){
		boolean locked = getBatch().lock(stepIdentifier);
		if(!locked) return false;
		
		S s = getStepLookup().findByIdentif(stepIdentifier);
		Date now = new Date();
		s.setStepOwner(null);
		s.setStarted(null);
		s.setLeaseEnd(null);
		s.setSchdldStart(DateUtils.addMilliseconds(now, inTimeMilisec));
		getStepEjb().update(s);
		
		getBatch().unLock(stepIdentifier);
		return true;
	}
		
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean lock(String lockIdentifier) throws PersistenceException{
		PS ps = getPrcssngStepLookup().findByIdentif(lockIdentifier);
		if(ps!=null){
			// if valueDate older than 20 seconds, recover.
			if(DateUtils.addSeconds(new Date(), -20).before(ps.getValueDt())){
				// Somebody else owns the lock.
				return false;
			}
				
			// recover
			ps.setCntnrIdentif(getCurrentProcessIdentif());
			ps.setValueDt(new Date());
			try {
				getPrcssngStepEJB().lock(ps, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
				ps = getPrcssngStepEJB().updateFlushAndRefresh(ps);
				getPrcssngStepEJB().lock(ps, LockModeType.NONE);
				return true;
			} catch (OptimisticLockException | LockTimeoutException e){
				return false;
			}

		} else {
			ps = getPrcssngStepEJB().newInstance();
			ps.setCntnrIdentif(getCurrentProcessIdentif());
			ps.setIdentif(lockIdentifier);
			ps.setValueDt(new Date());
			try {
				ps = getPrcssngStepEJB().create(ps);
				return true;
			} catch (EntityExistsException e){
				 return false;
			}
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean unLock(String lockIdentifier) throws PersistenceException{
		PS ps = getPrcssngStepLookup().findByIdentif(lockIdentifier);
		if(ps==null) return true;
		if(!getCurrentProcessIdentif().equals(ps.getCntnrIdentif())) return false;
		getPrcssngStepEJB().deleteById(ps.getId());
		return true;
	}
	
	/*
	 * Purpose is the recovery of steps which where started, but could not be recovered.
	 * 
	 * A pending step has following characteristics:
	 * 	- The step is started.
	 * 	- The leaseEnd is not null
	 * 	- The lease end is more that x minutes in the past.
	 */
	@Schedule(minute = "*/13", hour="*")
	public void recoverSteps(){
		getBatch().recoverStepsAsynch();
	}
	
	@Asynchronous
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void recoverStepsAsynch(){
		// Return if another job running.
		if (!taskSynchronizer.recoverStepsBusyCompareAndSet(false, true)) {
            return;
        }
		
		try {
			
			Date now = new Date();
			Long count = getStepLookup().countByStartedIsNotNullAndEndedIsNullAndLeaseEndLessThan(now);
			if(count<=0) return; // No step to recover
			int start = 0;
			int max = 100;
			while(start<count){
				int firstResult = start;
				start+=max;
				List<S> list = getStepLookup().findByStartedIsNotNullAndEndedIsNullAndLeaseEndLessThan(now, firstResult, max);
				for (S step : list) {
					doExecuteStep(step);
				}
			}
		} finally {
			taskSynchronizer.recoverStepsBusySet(false);			
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void terminateJob(String jobIdentif){
		// We can only terminate a job if there is no open steps.
		Long stepCount = getStepLookup().countByCntnrIdentifAndEndedIsNotNull(jobIdentif);
		if(stepCount>0) return;
		if(!getBatch().lock(jobIdentif)) return;
		J j = getJobLookup().findByIdentif(jobIdentif);
		j.setEndTime(new Date());
		j.setJobStatus(CoreJobStatusEnum.TERMINATED.name());
		getJobEjb().update(j);
		getBatch().unLock(jobIdentif);
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void terminateStep(String stepIdentif){
		if(!getBatch().lock(stepIdentif)) return;
		S s = getStepLookup().findByIdentif(stepIdentif);
		if(s!=null && s.getEnded()==null){
			s.setEnded(new Date());
			getStepEjb().update(s);
		} else {
			getBatch().unLock(stepIdentif);
			// throw Ended exception. Step ended by another thread.
			throw new IllegalStateException("Step ended by another thread.");
		}
		getBatch().unLock(stepIdentif);
	}
}
