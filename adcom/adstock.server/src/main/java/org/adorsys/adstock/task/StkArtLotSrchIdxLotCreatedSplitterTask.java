package org.adorsys.adstock.task;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;

import org.adorsys.adcatal.jpa.CatalArtLangMapping;
import org.adorsys.adcatal.rest.CatalArtLangMappingLookup;
import org.adorsys.adcore.event.EntityCreatedEvent;
import org.adorsys.adcore.rest.CoreAbstEntityJobExecutor;
import org.adorsys.adstock.jpa.StkArticleLot2StrgSctn;
import org.adorsys.adstock.jpa.StkJob;
import org.adorsys.adstock.jpa.StkPrcssgStep;
import org.adorsys.adstock.jpa.StkStep;
import org.adorsys.adstock.rest.StkArticleLot2StrgSctnLookup;
import org.adorsys.adstock.rest.StkArticleLotLookup;
import org.adorsys.adstock.rest.StkBatch;
import org.adorsys.adstock.rest.StkJobEJB;
import org.adorsys.adstock.rest.StkJobLookup;
import org.adorsys.adstock.rest.StkStepEJB;
import org.adorsys.adstock.rest.StkStepLookup;

/**
 * 
 * @author francis
 *
 */
@Stateless
public class StkArtLotSrchIdxLotCreatedSplitterTask extends
	CoreAbstEntityJobExecutor<StkJob, StkStep, StkPrcssgStep> {

	@EJB
	private StkJobEJB jobEjb;
	@EJB
	private StkJobLookup jobLookup;
	@EJB
	private StkArtLotSrchIdxLotCreatedSplitterTask ejb;
	@EJB
	private CatalArtLangMappingLookup artLangMappingLookup;
	@EJB
	private StkArticleLot2StrgSctnLookup lot2StrgSctnLookup;
	@EJB
	private StkArticleLotLookup lotLookup;
	@EJB
	private StkStepEJB stepEJB;
	@EJB
	private StkStepLookup stepLookup;
	@EJB
	private StkArtLotSrchIdxLotCreatedExecTask execTask;

	@EJB
	private StkBatch batch;

	@Override
	public StkArtLotSrchIdxLotCreatedSplitterTask getEjb() {
		return ejb;
	}

	@Override
	public StkBatch getBatch() {
		return batch;
	}

	public void handleStkArticleLot2StrgSctnCreatedEvent(@Observes @EntityCreatedEvent StkArticleLot2StrgSctn articleLot2StrgSctn){
		StkJob job = new StkJob();
		job.setEntIdentif(articleLot2StrgSctn.getIdentif());
		job.setCntnrIdentif(articleLot2StrgSctn.getIdentif());
		job.setExecutorId(getClass().getSimpleName());
		job = jobEjb.create(job);
		
		StkStep step = new StkStep();
		step.setCntnrIdentif(job.getCntnrIdentif());
		step.setEntIdentif(job.getEntIdentif());
		step.setExecutorId(getClass().getSimpleName());
		step.setSchdldStart(new Date());
		step.setValueDt(new Date());
		stepEJB.create(step);
	}

	@Override
	public void execute(String stepIdentif) {
		StkStep step = stepLookup.findByIdentif(stepIdentif);
		processNewLotSplitt(step);
	}

	private void processNewLotSplitt(StkStep step){
		StkArticleLot2StrgSctn lot2StrgSctn = lot2StrgSctnLookup.findByIdentif(step.getEntIdentif());
		if(lot2StrgSctn!=null) {
			Long count = artLangMappingLookup.countByArtPic(lot2StrgSctn.getArtPic());
			int start = 0;
			int max = 100;
			while(start<count){
				int firstResult = start;
				start+=max;
				List<CatalArtLangMapping> list = artLangMappingLookup.findByCntnrIdentifAsc(lot2StrgSctn.getArtPic(), firstResult, 1);
				CatalArtLangMapping first = list.iterator().next();
				if(start<count){ // there is still another round. 
					list = artLangMappingLookup.findByCntnrIdentifAsc(lot2StrgSctn.getArtPic(), start-1, 1);
				} else {// start>=count. Fetch the last object of the list.
					list = artLangMappingLookup.findByCntnrIdentifDesc(lot2StrgSctn.getArtPic(), 0, 1);
				}
				CatalArtLangMapping last = list.iterator().next();
				
				StkStep s = new StkStep();
				s.setCntnrIdentif(step.getCntnrIdentif());
				s.setEntIdentif(step.getEntIdentif());
				s.setExecutorId(execTask.getExecutorId());
				s.setStepStartId(first.getIdentif());
				s.setStepEndId(last.getIdentif());
				stepEJB.create(s);
			}	
		}

		getBatch().terminateStep(step.getIdentif());
	}
}
