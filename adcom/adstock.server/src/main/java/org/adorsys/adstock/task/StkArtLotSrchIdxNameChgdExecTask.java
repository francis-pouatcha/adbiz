package org.adorsys.adstock.task;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArtLangMapping;
import org.adorsys.adcatal.rest.CatalArtLangMappingLookup;
import org.adorsys.adcore.rest.CoreAbstEntityJobExecutor;
import org.adorsys.adstock.jpa.StkArtLotSrchIdx;
import org.adorsys.adstock.jpa.StkJob;
import org.adorsys.adstock.jpa.StkPrcssgStep;
import org.adorsys.adstock.jpa.StkStep;
import org.adorsys.adstock.rest.StkArtLotSrchIdxEJB;
import org.adorsys.adstock.rest.StkArtLotSrchIdxLookup;
import org.adorsys.adstock.rest.StkBatch;
import org.adorsys.adstock.rest.StkStepEJB;
import org.adorsys.adstock.rest.StkStepLookup;

/**
 * @author francis
 *
 */
@Stateless
public class StkArtLotSrchIdxNameChgdExecTask extends
	CoreAbstEntityJobExecutor<StkJob, StkStep, StkPrcssgStep> {

	@Inject
	private StkArtLotSrchIdxLookup idxLookup;
	@Inject
	private StkArtLotSrchIdxEJB idxEjb;

	@EJB
	private StkArtLotSrchIdxNameChgdExecTask ejb;
	@EJB
	private CatalArtLangMappingLookup artLangMappingLookup;

	@EJB
	private StkStepEJB stepEJB;
	@EJB
	private StkStepLookup stepLookup;
	@EJB
	private StkBatch batch;

	@Override
	public StkArtLotSrchIdxNameChgdExecTask getEjb() {
		return ejb;
	}

	@Override
	public StkBatch getBatch() {
		return batch;
	}

	@Override
	public void execute(String stepIdentif) {
		StkStep step = stepLookup.findByIdentif(stepIdentif);
		processNameChangeStep(step);
	}
		
	public void processNameChangeStep(StkStep step) {
		Long count = idxLookup.countByIdentifBetween(step.getStepStartId(), step.getStepEndId());
		List<StkArtLotSrchIdx> list = idxLookup.findByIdentifBetween(step.getStepStartId(), step.getStepEndId(), 0, count.intValue());
		
		CatalArtLangMapping artLangMapping = null;
		for (StkArtLotSrchIdx stkArtLotSrchIdx : list) {
			if(artLangMapping==null){
				artLangMapping = artLangMappingLookup.findByIdentif(stkArtLotSrchIdx.getArtLMIdx());
				if(artLangMapping==null)break;
			}
			
			stkArtLotSrchIdx.setArtName(artLangMapping.getArtName());
			idxEjb.update(stkArtLotSrchIdx);
		}
		stepEJB.deleteById(step.getId());
	}
}
