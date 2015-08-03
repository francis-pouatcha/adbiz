package org.adorsys.adstock.task;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.rest.CoreAbstEntityJobExecutor;
import org.adorsys.adstock.jpa.StkArtLotSrchIdx;
import org.adorsys.adstock.jpa.StkJob;
import org.adorsys.adstock.jpa.StkPrcssgStep;
import org.adorsys.adstock.jpa.StkStep;
import org.adorsys.adstock.rest.StkArtLotSrchIdxEJB;
import org.adorsys.adstock.rest.StkArtLotSrchIdxLookup;
import org.adorsys.adstock.rest.StkBatch;
import org.adorsys.adstock.rest.StkStepLookup;

/**
 * TODO: also handle article name changed event.
 * 
 * @author francis
 *
 */
@Stateless
public class StkArtLotSrchIdxLotDeletedExecTask extends
		CoreAbstEntityJobExecutor<StkJob, StkStep, StkPrcssgStep> {

	@Inject
	private StkArtLotSrchIdxLookup idxLookup;
	@Inject
	private StkArtLotSrchIdxEJB idxEjb;

	private StkArtLotSrchIdxLotDeletedExecTask ejb;

	@EJB
	private StkStepLookup stepLookup;
	@EJB
	private StkBatch batch;

	@Override
	public StkArtLotSrchIdxLotDeletedExecTask getEjb() {
		return ejb;
	}

	@Override
	public StkBatch getBatch() {
		return batch;
	}

	@Override
	public void execute(String stepIdentif) {
		StkStep step = stepLookup.findByIdentif(stepIdentif);
		processLotDeletedIndex(step);
	}

	private void processLotDeletedIndex(StkStep step) {
		Long count = idxLookup.countByIdentifBetween(step.getStepStartId(),
				step.getStepEndId());
		List<StkArtLotSrchIdx> list = idxLookup
				.findByIdentifBetween(step.getStepStartId(),
						step.getStepEndId(), 0, count.intValue());
		for (StkArtLotSrchIdx stkArtLotSrchIdx : list) {
			idxEjb.deleteById(stkArtLotSrchIdx.getId());
		}

		getBatch().terminateStep(step.getIdentif());
	}

}
