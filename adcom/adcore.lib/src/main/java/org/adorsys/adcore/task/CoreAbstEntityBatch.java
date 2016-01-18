package org.adorsys.adcore.task;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.ejb.AccessTimeout;
import javax.ejb.Schedule;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
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
public abstract class CoreAbstEntityBatch<J extends CoreAbstEntityJob, S extends CoreAbstEntityStep, PS extends CoreAbstPrcssngStep> {
	
	private final String processId = UUID.randomUUID().toString();

	protected abstract CoreAbstEntityJobEJB<J> getJobEjb();
	protected abstract CoreAbstEntityJobLookup<J> getJobLookup();
	protected abstract CoreAbstEntityStepEJB<S> getStepEjb();
	protected abstract CoreAbstEntityStepLookup<S> getStepLookup();
	protected abstract CoreAbstEntityBatch<J,S,PS> getBatch();
	protected abstract CoreAbstPrcssngStepEJB<PS> getPrcssngStepEJB();
	protected abstract CoreAbstPrcssngStepLookup<PS> getPrcssngStepLookup();
	
	public String getCurrentProcessIdentif() {
		return processId;
	}
	
	private Map<String, CoreEntityJobExecutor<J,S>> executorMap = new HashMap<String, CoreEntityJobExecutor<J,S>>();
	public void registerJobExecutor(String executorId, CoreEntityJobExecutor<J,S> executor){
		executorMap.put(executorId, executor);
	}

	@Schedule(minute = "*", second="*/2", hour="*", persistent=false)
	@AccessTimeout(unit=TimeUnit.MINUTES, value=10)
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void processSteps() throws Exception {
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
				if(step.getEnded()!=null) continue;
				doExecuteStep(step);
			}
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
			CoreEntityJobExecutor<J, S> executor = executorMap.get(step.getExecutorId());
			if(executor==null) return;
			
			int execTimeMilisec = executor.estimateExecTimeMilisec(step.getIdentif());
			boolean leased = getBatch().lease(step.getIdentif(), execTimeMilisec);
			if(!leased) return;

			executor.execute(step.getIdentif());
		}
	}

	/**
	 * Check for terminated jobs and schedule their removal them. A job has no step when all steps
	 * have been executed.
	 *  
	 */
	@Schedule(minute = "*/5", hour="*", persistent=false)
	@AccessTimeout(unit=TimeUnit.MINUTES, value=15)
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void cleanupJobs() throws Exception {
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
		if(s.getLeaseEnd()!=null && s.getLeaseEnd().before(now)) {
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
			// if valueDate older than 3 seconds, recover.
			if(DateUtils.addSeconds(new Date(), -3).before(ps.getValueDt())){
				// Somebody else owns the lock.
				return false;
			}
				
			// recover
			ps.setCntnrIdentif(processId);
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
			ps.setCntnrIdentif(processId);
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
		if(!processId.equals(ps.getCntnrIdentif())) return false;
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
	@Schedule(minute = "*/13", hour="*", persistent=false)
	@AccessTimeout(unit=TimeUnit.MINUTES, value=15)
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void recoverSteps(){
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
	}
	
	public void terminateJob(String jobIdentif){
		getBatch().lock(jobIdentif);
		J j = getJobLookup().findByIdentif(jobIdentif);
		j.setEndTime(new Date());
		j.setJobStatus(CoreJobStatusEnum.TERMINATED.name());
		getJobEjb().update(j);
		getBatch().unLock(jobIdentif);
	}
	
	public void terminateStep(String stepIdentif){
		getBatch().lock(stepIdentif);
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
