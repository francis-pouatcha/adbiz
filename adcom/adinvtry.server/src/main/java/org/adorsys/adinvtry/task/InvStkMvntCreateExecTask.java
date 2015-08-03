package org.adorsys.adinvtry.task;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.adorsys.adcore.rest.CoreAbstEntityJobExecutor;
import org.adorsys.adcore.task.CoreAbstEntityBatch;
import org.adorsys.adinvtry.jpa.InvInvtry;
import org.adorsys.adinvtry.jpa.InvInvtryHstry;
import org.adorsys.adinvtry.jpa.InvInvtryItem;
import org.adorsys.adinvtry.jpa.InvJob;
import org.adorsys.adinvtry.jpa.InvPrcssgStep;
import org.adorsys.adinvtry.jpa.InvStep;
import org.adorsys.adinvtry.rest.InvInvtryBatch;
import org.adorsys.adinvtry.rest.InvInvtryHstryLookup;
import org.adorsys.adinvtry.rest.InvInvtryItemLookup;
import org.adorsys.adinvtry.rest.InvInvtryLookup;
import org.adorsys.adinvtry.rest.InvJobLookup;
import org.adorsys.adinvtry.rest.InvStepLookup;
import org.adorsys.adstock.jpa.StkMvnt;
import org.adorsys.adstock.rest.StkMvntListener;

@Stateless
public class InvStkMvntCreateExecTask extends CoreAbstEntityJobExecutor<InvJob, InvStep, InvPrcssgStep> {

	@EJB
	private InvInvtryBatch batch;
	@EJB
	private InvStkMvntCreateExecTask task;
	@EJB
	private InvInvtryItemLookup itemLookup;
	@EJB
	private InvStepLookup stepLookup;
	@EJB
	private StkMvntListener stkMvntListener;
	@EJB
	private InvJobLookup jobLookup;
	@EJB
	private InvInvtryHstryLookup invtryHstryLookup;
	@EJB
	private InvInvtryLookup invtryLookup;

	public void execute(String stepIdentif) {
		InvStep step = stepLookup.findByIdentif(stepIdentif);
		if (step == null)return;
		createStkMvnt(step);
		batch.terminateStep(stepIdentif);
	}

	private void createStkMvnt(InvStep step) {
		Long eltCount = itemLookup.countByIdentifBetween(step.getStepStartId(), step.getStepEndId());
		int eltStart = 0;
		int max = 100;
		InvJob job = jobLookup.findByIdentif(step.getCntnrIdentif());
		InvInvtryHstry hstry = invtryHstryLookup.findById(job.getHistIdentif());
		InvInvtry invInvtry = invtryLookup.findByIdentif(job.getEntIdentif());
		
		while (eltStart < eltCount) {
			int firstResult = eltStart;
			eltStart += max;
			List<InvInvtryItem> list = itemLookup
					.findByIdentifBetween(step.getStepStartId(),
							step.getStepEndId(), firstResult, max);
			for (InvInvtryItem item : list) {
				StkMvnt stkMvnt = new StkMvnt();
				item.copyTo(stkMvnt);
				stkMvnt.setCntnrIdentif(item.getCntnrIdentif());
				stkMvnt.setValueDt(hstry.getHstryDt());
				stkMvnt.setOrigDocNbrs(invInvtry.getIdentif());
				stkMvnt.setOrigProcs(InvInvtryItem.class.getSimpleName());
				stkMvnt.setOrigProcsNbr(item.getIdentif());
				stkMvntListener.fireNewMvntEvent(stkMvnt,hstry);
			}
		}
	}

	@Override
	protected CoreAbstEntityBatch<InvJob, InvStep, InvPrcssgStep> getBatch() {
		return batch;
	}

	@Override
	protected CoreAbstEntityJobExecutor<InvJob, InvStep, InvPrcssgStep> getEjb() {
		return task;
	}
}
