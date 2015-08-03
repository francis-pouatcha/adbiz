package org.adorsys.adstock.task;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArtLangMapping;
import org.adorsys.adcatal.rest.CatalArtLangMappingLookup;
import org.adorsys.adcore.rest.CoreAbstEntityJobExecutor;
import org.adorsys.adstock.jpa.StkArtLotSrchIdx;
import org.adorsys.adstock.jpa.StkArticleLot;
import org.adorsys.adstock.jpa.StkArticleLot2StrgSctn;
import org.adorsys.adstock.jpa.StkJob;
import org.adorsys.adstock.jpa.StkPrcssgStep;
import org.adorsys.adstock.jpa.StkStep;
import org.adorsys.adstock.rest.StkArtLotSrchIdxEJB;
import org.adorsys.adstock.rest.StkArticleLot2StrgSctnLookup;
import org.adorsys.adstock.rest.StkArticleLotLookup;
import org.adorsys.adstock.rest.StkBatch;
import org.adorsys.adstock.rest.StkStepLookup;

/**
 * TODO: also handle article name changed event.
 * 
 * @author francis
 *
 */
@Stateless
public class StkArtLotSrchIdxLotCreatedExecTask extends
	CoreAbstEntityJobExecutor<StkJob, StkStep, StkPrcssgStep> {

	@Inject
	private StkArtLotSrchIdxEJB idxEjb;

	@EJB
	private StkArtLotSrchIdxLotCreatedExecTask execTask;
	@EJB
	private CatalArtLangMappingLookup artLangMappingLookup;
	@EJB
	private StkArticleLot2StrgSctnLookup lot2StrgSctnLookup;
	@EJB
	private StkArticleLotLookup lotLookup;

	@EJB
	private StkStepLookup stepLookup;
	@EJB
	private StkBatch batch;

	@Override
	public StkArtLotSrchIdxLotCreatedExecTask getEjb() {
		return execTask;
	}

	@Override
	public StkBatch getBatch() {
		return batch;
	}

	@Override
	public void execute(String stepIdentif) {
		StkStep step = stepLookup.findByIdentif(stepIdentif);
		processNewLotIndex(step);
	}

	private void processNewLotIndex(StkStep step){
		Long lotCount = lot2StrgSctnLookup.countByCntnrIdentifBetween(step.getStepStartId(), step.getStepEndId());
		List<StkArticleLot2StrgSctn> lotList = lot2StrgSctnLookup.findByCntnrIdentifBetween(step.getStepStartId(), step.getStepEndId(), 0, lotCount.intValue());
		for (StkArticleLot2StrgSctn lot2StrgSctn : lotList) {
			StkArticleLot articleLot = lotLookup.findByIdentif(lot2StrgSctn.getLotPic());
			Long count = artLangMappingLookup.countByArtPic(lot2StrgSctn.getArtPic());
			int start = 0;
			int max = 100;
			while(start<count){
				int firstResult = start;
				start+=max;
				List<CatalArtLangMapping> list = artLangMappingLookup.findByArtPic(lot2StrgSctn.getArtPic(), firstResult, max);
				for (CatalArtLangMapping catalArtLangMapping : list) {
					StkArtLotSrchIdx stkArtLotSrchIdx = new StkArtLotSrchIdx();
					articleLot.copyTo(stkArtLotSrchIdx);
					lot2StrgSctn.copyTo(stkArtLotSrchIdx);
					stkArtLotSrchIdx.cleanId();
					stkArtLotSrchIdx.setArtName(catalArtLangMapping.getArtName());
					stkArtLotSrchIdx.setLangIso2(catalArtLangMapping.getLangIso2());
					stkArtLotSrchIdx.setArtLMIdx(catalArtLangMapping.getIdentif());
					idxEjb.create(stkArtLotSrchIdx);
				}
			}	
		}

		getBatch().terminateStep(step.getIdentif());
	}
	
	
}
