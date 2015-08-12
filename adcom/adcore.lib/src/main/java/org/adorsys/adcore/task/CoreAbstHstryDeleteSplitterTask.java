package org.adorsys.adcore.task;

import java.util.Date;
import java.util.List;

import javax.enterprise.event.Observes;

import org.adorsys.adcore.enums.CoreJobStatusEnum;
import org.adorsys.adcore.enums.CoreProcessStatusEnum;
import org.adorsys.adcore.event.EntityHstryEvent;
import org.adorsys.adcore.jpa.CoreAbstBsnsObject;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectHstry;
import org.adorsys.adcore.jpa.CoreAbstEntityJob;
import org.adorsys.adcore.jpa.CoreAbstEntityStep;
import org.adorsys.adcore.jpa.CoreAbstPrcssngStep;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectHstryLookup;
import org.adorsys.adcore.rest.CoreAbstEntityJobEJB;
import org.adorsys.adcore.rest.CoreAbstEntityJobExecutor;
import org.adorsys.adcore.rest.CoreAbstEntityJobLookup;
import org.adorsys.adcore.rest.CoreAbstEntityStepEJB;
import org.adorsys.adcore.rest.CoreAbstEntityStepLookup;
import org.apache.commons.lang3.time.DateUtils;

public abstract class CoreAbstHstryDeleteSplitterTask<E extends CoreAbstBsnsObject, H extends CoreAbstBsnsObjectHstry, 
	J extends CoreAbstEntityJob, S extends CoreAbstEntityStep, PS extends CoreAbstPrcssngStep> extends CoreAbstEntityJobExecutor<J,S, PS>{

	private static final int HSTRY_COUNT_TRESHOLD = 100;
	public abstract CoreAbstBsnsObjectHstryLookup<H> getHstrLookup();
	public abstract CoreAbstEntityStepLookup<S> getStepLookup();
	public abstract CoreAbstEntityJobLookup<J> getJobLookup();
	public abstract CoreAbstEntityStepEJB<S> getStepEjb();
	public abstract CoreAbstEntityJobEJB<J> getJobEjb();
	protected abstract CoreAbstHstryDeleteExecTask<E, H, J, S, PS> getDeleteTaskExecutor();
	
	@Override
	public void execute(String stepIdentif) {
		S step = getStepLookup().findByIdentif(stepIdentif);
		splittJob(step.getCntnrIdentif(), step.getIdentif());
	}
	
	private void splittJob(String jobIdentif, String stepIdentif) {
		// use the get ejb interface to activate new transaction
		getBatch().lease(stepIdentif, 300);// 5 mins for the preparation.

		// Only do this job, if you controle the prepare job
		J job = getJobLookup().findByIdentif(jobIdentif);
		String entIdentif = job.getEntIdentif();
		Long itemsCount = getHstrLookup().countByEntIdentif(entIdentif);
		int itemStart = 0;
		while (itemStart < itemsCount) {
			int firstResult = itemStart;
			itemStart += HSTRY_COUNT_TRESHOLD;
			List<H> list = getHstrLookup().findByEntIdentifOrderByIdAsc(entIdentif, firstResult, 1);
			H first = list.iterator().next();
			if(itemStart<itemsCount){ // there is still another round. 
				list = getHstrLookup().findByEntIdentifOrderByIdAsc(entIdentif, itemStart-1, 1);
			} else {// start>=count. Fetch the last object of the list.
				list = getHstrLookup().findByEntIdentifOrderByIdDesc(entIdentif, 0, 1);
			}
			H last = list.iterator().next();
			
			S s = getStepEjb().newStepInstance();
			s.setEntIdentif(entIdentif);
			s.setCntnrIdentif(job.getIdentif());
			s.setStepStartId(first.getId());
			s.setStepEndId(last.getId());
			s.setExecutorId(getDeleteTaskExecutor().getExecutorId());
			s.setPreceedingStep(stepIdentif);
			getStepEjb().create(s);
		}

		getBatch().terminateStep(stepIdentif);
	}
	
	public void handleEntityDeletedEvent(@Observes @EntityHstryEvent H hstry){
		if(CoreProcessStatusEnum.DELETED.name().equals(hstry.getEntStatus())){
			createDeleteJob(hstry);
		}
	}

	private void createDeleteJob(H hstry) {
		J job = getJobEjb().newJobInstance();
		job.setExecutorId(getClass().getSimpleName());
		job.setJobStatus(CoreJobStatusEnum.INITIATED.name());
		job.setStartTime(new Date());
		getJobEjb().create(job);

		S s = getStepEjb().newStepInstance();
		s.setEntIdentif(hstry.getEntIdentif());
		s.setCntnrIdentif(job.getIdentif());
		s.setExecutorId(getClass().getSimpleName());
		s.setSchdldStart(DateUtils.addMinutes(new Date(), 1));
		getStepEjb().create(s);
	}
		
}
