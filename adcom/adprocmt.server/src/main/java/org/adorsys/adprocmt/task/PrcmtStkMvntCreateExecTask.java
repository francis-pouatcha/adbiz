package org.adorsys.adprocmt.task;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.adorsys.adcore.rest.CoreAbstEntityJobExecutor;
import org.adorsys.adcore.task.CoreAbstEntityBatch;
import org.adorsys.adprocmt.jpa.PrcmtDelivery;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryHstry;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem;
import org.adorsys.adprocmt.jpa.PrcmtJob;
import org.adorsys.adprocmt.jpa.PrcmtPrcssgStep;
import org.adorsys.adprocmt.jpa.PrcmtStep;
import org.adorsys.adprocmt.rest.PrcmtBatch;
import org.adorsys.adprocmt.rest.PrcmtDeliveryHstryLookup;
import org.adorsys.adprocmt.rest.PrcmtDeliveryLookup;
import org.adorsys.adprocmt.rest.PrcmtDlvryItemLookup;
import org.adorsys.adprocmt.rest.PrcmtJobLookup;
import org.adorsys.adprocmt.rest.PrcmtStepLookup;
import org.adorsys.adstock.jpa.StkMvnt;
import org.adorsys.adstock.rest.StkMvntListener;

@Stateless
public class PrcmtStkMvntCreateExecTask extends CoreAbstEntityJobExecutor<PrcmtJob, PrcmtStep, PrcmtPrcssgStep> {

	@EJB
	private PrcmtBatch batch;
	@EJB
	private PrcmtStkMvntCreateExecTask task;
	@EJB
	private PrcmtDlvryItemLookup itemLookup;
	@EJB
	private PrcmtStepLookup stepLookup;
	@EJB
	private StkMvntListener stkMvntListener;
	@EJB
	private PrcmtJobLookup jobLookup;
	@EJB
	private PrcmtDeliveryHstryLookup hstryLookup;
	@EJB
	private PrcmtDeliveryLookup lookup;

	public void execute(String stepIdentif) {
		PrcmtStep step = stepLookup.findByIdentif(stepIdentif);
		if (step == null)return;
		createStkMvnt(step);
		batch.terminateStep(stepIdentif);
	}

	private void createStkMvnt(PrcmtStep step) {
		Long eltCount = itemLookup.countByIdentifBetween(step.getStepStartId(), step.getStepEndId());
		int eltStart = 0;
		int max = 100;
		PrcmtJob job = jobLookup.findByIdentif(step.getCntnrIdentif());
		PrcmtDeliveryHstry hstry = hstryLookup.findById(job.getHistIdentif());
		PrcmtDelivery bnsObj = lookup.findByIdentif(job.getEntIdentif());
		
		while (eltStart < eltCount) {
			int firstResult = eltStart;
			eltStart += max;
			List<PrcmtDlvryItem> list = itemLookup
					.findByIdentifBetween(step.getStepStartId(),
							step.getStepEndId(), firstResult, max);
			for (PrcmtDlvryItem item : list) {
				StkMvnt stkMvnt = new StkMvnt();
				item.copyTo(stkMvnt);
				stkMvnt.setCntnrIdentif(item.getCntnrIdentif());
				stkMvnt.setValueDt(hstry.getHstryDt());
				stkMvnt.setOrigDocNbrs(bnsObj.getIdentif());
				stkMvnt.setOrigProcs(PrcmtDlvryItem.class.getSimpleName());
				stkMvnt.setOrigProcsNbr(item.getIdentif());
				stkMvntListener.fireNewMvntEvent(stkMvnt,hstry);
			}
		}
	}

	@Override
	protected CoreAbstEntityBatch<PrcmtJob, PrcmtStep, PrcmtPrcssgStep> getBatch() {
		return batch;
	}

	@Override
	protected CoreAbstEntityJobExecutor<PrcmtJob, PrcmtStep, PrcmtPrcssgStep> getEjb() {
		return task;
	}
}
