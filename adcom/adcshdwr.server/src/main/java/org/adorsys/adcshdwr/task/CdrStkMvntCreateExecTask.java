package org.adorsys.adcshdwr.task;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.adorsys.adcore.rest.CoreAbstEntityJobExecutor;
import org.adorsys.adcore.task.CoreAbstEntityBatch;
import org.adorsys.adcshdwr.jpa.CdrDrctSales;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesHstry;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesItem;
import org.adorsys.adcshdwr.jpa.CdrJob;
import org.adorsys.adcshdwr.jpa.CdrPrcssgStep;
import org.adorsys.adcshdwr.jpa.CdrStep;
import org.adorsys.adcshdwr.rest.CdrBatch;
import org.adorsys.adcshdwr.rest.CdrDrctSalesHstryLookup;
import org.adorsys.adcshdwr.rest.CdrDrctSalesItemLookup;
import org.adorsys.adcshdwr.rest.CdrDrctSalesLookup;
import org.adorsys.adcshdwr.rest.CdrJobLookup;
import org.adorsys.adcshdwr.rest.CdrStepLookup;
import org.adorsys.adstock.jpa.StkMvnt;
import org.adorsys.adstock.rest.StkMvntListener;

@Stateless
public class CdrStkMvntCreateExecTask extends CoreAbstEntityJobExecutor<CdrJob, CdrStep, CdrPrcssgStep> {

	@EJB
	private CdrBatch batch;
	@EJB
	private CdrStkMvntCreateExecTask task;
	@EJB
	private CdrDrctSalesItemLookup itemLookup;
	@EJB
	private CdrStepLookup stepLookup;
	@EJB
	private StkMvntListener stkMvntListener;
	@EJB
	private CdrJobLookup jobLookup;
	@EJB
	private CdrDrctSalesHstryLookup hstryLookup;
	@EJB
	private CdrDrctSalesLookup lookup;

	public void execute(String stepIdentif) {
		CdrStep step = stepLookup.findByIdentif(stepIdentif);
		if (step == null)return;
		createStkMvnt(step);
		batch.terminateStep(stepIdentif);
	}

	private void createStkMvnt(CdrStep step) {
		Long eltCount = itemLookup.countByIdentifBetween(step.getStepStartId(), step.getStepEndId());
		int eltStart = 0;
		int max = 100;
		CdrJob job = jobLookup.findByIdentif(step.getCntnrIdentif());
		CdrDrctSalesHstry hstry = hstryLookup.findById(job.getHistIdentif());
		CdrDrctSales drtSale = lookup.findByIdentif(job.getEntIdentif());
		
		while (eltStart < eltCount) {
			int firstResult = eltStart;
			eltStart += max;
			List<CdrDrctSalesItem> list = itemLookup
					.findByIdentifBetween(step.getStepStartId(),
							step.getStepEndId(), firstResult, max);
			for (CdrDrctSalesItem item : list) {
				StkMvnt stkMvnt = new StkMvnt();
				item.copyTo(stkMvnt);
				
				// Suggestion Francis 19.07.2016: negate the item quantities in the stock movement
				// and reevaluate.
				stkMvnt.negate();
				stkMvnt.evlte();
				
				stkMvnt.setCntnrIdentif(item.getCntnrIdentif());
				stkMvnt.setValueDt(hstry.getHstryDt());
				stkMvnt.setOrigDocNbrs(drtSale.getIdentif());
				stkMvnt.setOrigProcs(CdrDrctSalesItem.class.getSimpleName());
				stkMvnt.setOrigProcsNbr(item.getIdentif());
				stkMvntListener.fireNewMvntEvent(stkMvnt,hstry);
			}
		}
	}

	@Override
	protected CoreAbstEntityBatch<CdrJob, CdrStep, CdrPrcssgStep> getBatch() {
		return batch;
	}

	@Override
	protected CoreAbstEntityJobExecutor<CdrJob, CdrStep, CdrPrcssgStep> getEjb() {
		return task;
	}
}
