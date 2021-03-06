package org.adorsys.adcore.task;

import java.util.Date;
import java.util.List;

import javax.enterprise.event.Observes;

import org.adorsys.adcore.enums.CoreJobStatusEnum;
import org.adorsys.adcore.enums.CoreProcessStatusEnum;
import org.adorsys.adcore.event.EntityHstryEvent;
import org.adorsys.adcore.jpa.CoreAbstBsnsItem;
import org.adorsys.adcore.jpa.CoreAbstBsnsObject;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectHstry;
import org.adorsys.adcore.jpa.CoreAbstEntityCstr;
import org.adorsys.adcore.jpa.CoreAbstEntityJob;
import org.adorsys.adcore.jpa.CoreAbstEntityStep;
import org.adorsys.adcore.jpa.CoreAbstPrcssngStep;
import org.adorsys.adcore.rest.CoreAbstBsnsObjInjector;
import org.adorsys.adcore.rest.CoreAbstEntityJobExecutor;
import org.apache.commons.lang3.time.DateUtils;

public abstract class CoreAbstBsnsItemDeleteSplitterTask<
	E extends CoreAbstBsnsObject, I extends CoreAbstBsnsItem, 
	H extends CoreAbstBsnsObjectHstry, J extends CoreAbstEntityJob,
	S extends CoreAbstEntityStep, C extends CoreAbstEntityCstr, PS extends CoreAbstPrcssngStep> extends CoreAbstEntityJobExecutor<J, S, PS>{

	protected abstract CoreAbstBsnsObjInjector<E, I, H, J, S, C> getInjector();
	public abstract CoreAbstBsnsItemDeleteExecTask<E, I, H, J, S, C, PS> getDeleteTaskExecutor();
	
	@Override
	public void execute(String stepIdentif) {
		S step = getInjector().getStepLookup().findByIdentif(stepIdentif);// Refresh the step object
		prepareDeleteJob(step.getCntnrIdentif(), step.getIdentif());
	}

	private static final int ITEM_COUNT_TRESHOLD = 100;
	private void prepareDeleteJob(String jobIdentif, String stepIdentif){
		// use the get ejb interface to activate new transaction
		getBatch().lease(stepIdentif, 300);// 5 mins for the preparation.
		
		// Only do this job, if you controle the prepare job
		J job = getInjector().getJobLookup().findByIdentif(jobIdentif);
		String entIdentif = job.getEntIdentif();
		Long itemsCount = getInjector().getItemLookup().countByCntnrIdentif(entIdentif);
		int itemStart = 0;
		while(itemStart<itemsCount){
			int firstResult = itemStart;
			itemStart+=ITEM_COUNT_TRESHOLD;
			List<I> list = getInjector().getItemLookup().findByCntnrIdentifOrderByIdentifAsc(entIdentif, firstResult, 1);
			I first = list.iterator().next();
			if(itemStart<itemsCount){ // there is still another round. 
				list = getInjector().getItemLookup().findByCntnrIdentifOrderByIdentifAsc(entIdentif, itemStart-1, 1);
			} else {// start>=count. Fetch the last object of the list.
				list = getInjector().getItemLookup().findByCntnrIdentifOrderByIdentifDesc(entIdentif, 0, 1);
			}
			I last = list.iterator().next();
			
			S s = getInjector().getStepEjb().newStepInstance();
			s.setEntIdentif(entIdentif);
			s.setCntnrIdentif(job.getIdentif());
			s.setStepStartId(first.getIdentif());
			s.setStepEndId(last.getIdentif());
			s.setPreceedingStep(stepIdentif);
			s.setExecutorId(getDeleteTaskExecutor().getExecutorId());
			getInjector().getStepEjb().create(s);
		}
		getBatch().terminateStep(stepIdentif);
	}

	public void handleEntityDeletedEvent(@Observes @EntityHstryEvent H hstry){
		if(CoreProcessStatusEnum.DELETED.name().equals(hstry.getEntStatus())){
			createDeleteJob(hstry);
		}
	}

	public void createDeleteJob(H hstry) {
		J job = getInjector().getJobEjb().newJobInstance();
		job.setExecutorId(getClass().getSimpleName());
		job.setJobStatus(CoreJobStatusEnum.INITIATED.name());
		job.setStartTime(new Date());
		getInjector().getJobEjb().create(job);
	
		S s = getInjector().getStepEjb().newStepInstance();
		s.setEntIdentif(hstry.getEntIdentif());
		s.setCntnrIdentif(job.getIdentif());
		s.setExecutorId(getClass().getSimpleName());
		s.setSchdldStart(DateUtils.addMinutes(new Date(), 1));
		getInjector().getStepEjb().create(s);
	}	
}

