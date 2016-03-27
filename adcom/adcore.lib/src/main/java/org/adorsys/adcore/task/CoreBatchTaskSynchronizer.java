package org.adorsys.adcore.task;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Singleton;

/**
 * This is a singleton bean that holds the processId of the web application
 * instance running a task.
 * 
 * @author fpo
 *
 */
@Singleton
public class CoreBatchTaskSynchronizer {

	private AtomicBoolean processStepsBusy = new AtomicBoolean(false);
	private AtomicBoolean cleanupJobsBusy = new AtomicBoolean(false);
	private AtomicBoolean recoverStepsBusy = new AtomicBoolean(false);		

	private Map<String, Object> executorMap = new HashMap<String, Object>();
	public void registerJobExecutor(String executorId, Object executor){
		executorMap.put(executorId, executor);
	}
	
	public Object getExecutor(String executorId){
		return executorMap.get(executorId);
	}
	
	public boolean processStepsBusyCompareAndSet(boolean expect, boolean update){
		return processStepsBusy.compareAndSet(expect, update);
	}
	public void processStepsBusySet(boolean newValue){
		processStepsBusy.set(newValue);
	}

	public boolean cleanupJobsBusyCompareAndSet(boolean expect, boolean update){
		return cleanupJobsBusy.compareAndSet(expect, update);
	}
	public void cleanupJobsBusySet(boolean newValue){
		cleanupJobsBusy.set(newValue);
	}

	public boolean recoverStepsBusyCompareAndSet(boolean expect, boolean update){
		return recoverStepsBusy.compareAndSet(expect, update);
	}
	public void recoverStepsBusySet(boolean newValue){
		recoverStepsBusy.set(newValue);
	}
}
