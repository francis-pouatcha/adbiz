package org.adorsys.adcost.task;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.adorsys.adcore.rest.CoreAbstEntityJobExecutor;
import org.adorsys.adcore.task.CoreAbstEntityBatch;
import org.adorsys.adcost.ejb.CstJobLookup;
import org.adorsys.adcost.ejb.CstStepLookup;
import org.adorsys.adcost.ejb.CstStmntBatch;
import org.adorsys.adcost.ejb.CstStmntHstryLookup;
import org.adorsys.adcost.ejb.CstStmntItemLookup;
import org.adorsys.adcost.ejb.CstStmntLookup;
import org.adorsys.adcost.jpa.CstJob;
import org.adorsys.adcost.jpa.CstPrcssgStep;
import org.adorsys.adcost.jpa.CstStep;
import org.adorsys.adcost.jpa.CstStmnt;
import org.adorsys.adcost.jpa.CstStmntHstry;
import org.adorsys.adcost.jpa.CstStmntItem;
import org.adorsys.adstock.jpa.StkMvnt;
import org.adorsys.adstock.rest.StkMvntListener;

@Stateless
public class CstStkMvntCreateExecTask extends CoreAbstEntityJobExecutor<CstJob, CstStep, CstPrcssgStep> {

	@EJB
	private CstStmntBatch batch;
	@EJB
	private CstStkMvntCreateExecTask task;
	@EJB
	private CstStmntItemLookup itemLookup;
	@EJB
	private CstStepLookup stepLookup;
	@EJB
	private StkMvntListener stkMvntListener;
	@EJB
	private CstJobLookup jobLookup;
	@EJB
	private CstStmntHstryLookup invtryHstryLookup;
	@EJB
	private CstStmntLookup invtryLookup;

	public void execute(String stepIdentif) {
		CstStep step = stepLookup.findByIdentif(stepIdentif);
		if (step == null)return;
		createStkMvnt(step);
		batch.terminateStep(stepIdentif);
	}

	private void createStkMvnt(CstStep step) {
		Long eltCount = itemLookup.countByIdentifBetween(step.getStepStartId(), step.getStepEndId());
		int eltStart = 0;
		int max = 100;
		CstJob job = jobLookup.findByIdentif(step.getCntnrIdentif());
		CstStmntHstry hstry = invtryHstryLookup.findById(job.getHistIdentif());
		CstStmnt invCsttry = invtryLookup.findByIdentif(job.getEntIdentif());
		
		while (eltStart < eltCount) {
			int firstResult = eltStart;
			eltStart += max;
			List<CstStmntItem> list = itemLookup
					.findByIdentifBetween(step.getStepStartId(),
							step.getStepEndId(), firstResult, max);
			for (CstStmntItem item : list) {
				StkMvnt stkMvnt = new StkMvnt();
				item.copyTo(stkMvnt);
				stkMvnt.setCntnrIdentif(item.getCntnrIdentif());
				stkMvnt.setValueDt(hstry.getHstryDt());
				stkMvnt.setOrigDocNbrs(invCsttry.getIdentif());
				stkMvnt.setOrigProcs(CstStmntItem.class.getSimpleName());
				stkMvnt.setOrigProcsNbr(item.getIdentif());
				stkMvntListener.fireNewMvntEvent(stkMvnt,hstry);
			}
		}
	}

	@Override
	protected CoreAbstEntityBatch<CstJob, CstStep, CstPrcssgStep> getBatch() {
		return batch;
	}

	@Override
	protected CoreAbstEntityJobExecutor<CstJob, CstStep, CstPrcssgStep> getEjb() {
		return task;
	}
}
