package org.adorsys.adstock.task;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.adorsys.adcore.event.EntityDeletedEvent;
import org.adorsys.adcore.rest.CoreAbstEntityJobExecutor;
import org.adorsys.adstock.jpa.StkArtLotSrchIdx;
import org.adorsys.adstock.jpa.StkArticleLot2StrgSctn;
import org.adorsys.adstock.jpa.StkJob;
import org.adorsys.adstock.jpa.StkPrcssgStep;
import org.adorsys.adstock.jpa.StkStep;
import org.adorsys.adstock.rest.StkArtLotSrchIdxLookup;
import org.adorsys.adstock.rest.StkBatch;
import org.adorsys.adstock.rest.StkJobEJB;
import org.adorsys.adstock.rest.StkStepEJB;
import org.adorsys.adstock.rest.StkStepLookup;

/**
 * TODO: also handle article name changed event.
 * 
 * @author francis
 *
 */
@Stateless
public class StkArtLotSrchIdxLotDeletedSplitterTask extends
	CoreAbstEntityJobExecutor<StkJob, StkStep, StkPrcssgStep> {

	@Inject
	private StkArtLotSrchIdxLookup idxLookup;
	@EJB
	private StkJobEJB jobEjb;

	@EJB
	private StkArtLotSrchIdxLotDeletedSplitterTask ejb;

	@EJB
	private StkStepEJB stepEJB;
	@EJB
	private StkStepLookup stepLookup;
	@EJB
	private StkBatch batch;
	
	@EJB
	private StkArtLotSrchIdxLotDeletedExecTask execTask;

	@Override
	public StkArtLotSrchIdxLotDeletedSplitterTask getEjb() {
		return ejb;
	}

	@Override
	public StkBatch getBatch() {
		return batch;
	}

	public void handleStkArticleLot2StrgSctnDeletedEvent(@Observes @EntityDeletedEvent StkArticleLot2StrgSctn articleLot2StrgSctn){
		String lotAndSectionIdx = StkArtLotSrchIdx.toLotPicAndDectionKey(articleLot2StrgSctn.getLotPic(), articleLot2StrgSctn.getSection());
		StkJob job = new StkJob();
		job.setEntIdentif(lotAndSectionIdx);
		job.setCntnrIdentif(lotAndSectionIdx);
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
		Long count = idxLookup.countByCntnrIdentif(step.getEntIdentif());
		int start = 0;
		int max = 100;
		while(start<count){
			int firstResult = start;
			start+=max;
			List<StkArtLotSrchIdx> list = idxLookup.findByCntnrIdentifAsc(step.getEntIdentif(), firstResult, 1);
			StkArtLotSrchIdx first = list.iterator().next();
			if(start<count){ // there is still another round. 
				list = idxLookup.findByCntnrIdentifAsc(step.getEntIdentif(), start-1, 1);
			} else {// start>=count. Fetch the last object of the list.
				list = idxLookup.findByCntnrIdentifDesc(step.getEntIdentif(), 0, 1);
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
