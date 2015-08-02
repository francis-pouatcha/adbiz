package org.adorsys.adstock.task;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArtLangMapping;
import org.adorsys.adcore.event.EntityCreatedEvent;
import org.adorsys.adcore.event.EntityUpdatedEvent;
import org.adorsys.adcore.rest.CoreAbstEntityJobExecutor;
import org.adorsys.adstock.jpa.StkArtLotSrchIdx;
import org.adorsys.adstock.jpa.StkJob;
import org.adorsys.adstock.jpa.StkPrcssgStep;
import org.adorsys.adstock.jpa.StkStep;
import org.adorsys.adstock.rest.StkArtLotSrchIdxLookup;
import org.adorsys.adstock.rest.StkBatch;
import org.adorsys.adstock.rest.StkJobEJB;
import org.adorsys.adstock.rest.StkStepEJB;
import org.adorsys.adstock.rest.StkStepLookup;

/**
 * 
 * @author francis
 *
 */
@Stateless
public class StkArtLotSrchIdxNameChgdSplitterTask extends
	CoreAbstEntityJobExecutor<StkJob, StkStep, StkPrcssgStep> {

	@Inject
	private StkArtLotSrchIdxLookup idxLookup;
	@EJB
	private StkJobEJB jobEjb;

	@EJB
	private StkArtLotSrchIdxNameChgdSplitterTask splitter;

	@EJB
	private StkStepEJB stepEJB;
	@EJB
	private StkStepLookup stepLookup;

	@EJB
	private StkBatch batch;
	
	@EJB
	private StkArtLotSrchIdxNameChgdExecTask execTask;

	@Override
	public StkArtLotSrchIdxNameChgdSplitterTask getEjb() {
		return splitter;
	}

	@Override
	public StkBatch getBatch() {
		return batch;
	}

	public void handleArtNameChangedEvent(@Observes @EntityUpdatedEvent CatalArtLangMapping artLangMapping){
		createIndexJob(artLangMapping);
	}
	public void handleArtNameCratedEvent(@Observes @EntityCreatedEvent CatalArtLangMapping artLangMapping){
		createIndexJob(artLangMapping);
	}
	
	private void createIndexJob(CatalArtLangMapping artLangMapping){
		StkJob job = new StkJob();
		job.setEntIdentif(artLangMapping.getIdentif());
		job.setCntnrIdentif(artLangMapping.getIdentif());
		job.setExecutorId(getClass().getSimpleName());
		job = jobEjb.create(job);
		
		StkStep step = new StkStep();
		step.setCntnrIdentif(job.getIdentif());
		step.setEntIdentif(job.getEntIdentif());
		step.setExecutorId(getClass().getSimpleName());
		step.setStarted(new Date());
		stepEJB.create(step );
	}

	@Override
	public void execute(String stepIdentif) {
		StkStep step = stepLookup.findByIdentif(stepIdentif);
		processNameChangeSplitt(step);
	}

	/**
	 * For a name change job, you must get all matching entries and update the name
	 * We will create Steps of hundred elements and delete the job.
	 * 
	 * @param job
	 */
	public void processNameChangeSplitt(StkStep step) {
		Long count = idxLookup.countByArtLMIdx(step.getEntIdentif());
		int start = 0;
		int max = 100;
		while(start<count){
			int firstResult = start;
			start+=max;
			List<StkArtLotSrchIdx> list = idxLookup.findByArtLMIdxOrderAsc(step.getEntIdentif(), firstResult, 1);
			StkArtLotSrchIdx first = list.iterator().next();
			if(start<count){ // there is still another round. 
				list = idxLookup.findByArtLMIdxOrderAsc(step.getEntIdentif(), start-1, 1);
			} else {// start>=count. Fetch the last object of the list.
				list = idxLookup.findByArtLMIdxOrderDesc(step.getEntIdentif(), 0, 1);
			}
			StkArtLotSrchIdx last = list.iterator().next();
			
			StkStep s = new StkStep();
			s.setCntnrIdentif(step.getCntnrIdentif());
			s.setEntIdentif(step.getEntIdentif());
			s.setExecutorId(execTask.getExecutorId());
			s.setStepStartId(first.getIdentif());
			s.setStepEndId(last.getIdentif());
			stepEJB.create(s);
		}
		getBatch().terminateStep(step.getIdentif());
	}

}
