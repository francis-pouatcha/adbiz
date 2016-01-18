package org.adorsys.adinvtry.task;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.rest.CoreAbstEntityJobExecutor;
import org.adorsys.adcore.task.CoreAbstEntityBatch;
import org.adorsys.adinvtry.jpa.InvInvtryItem;
import org.adorsys.adinvtry.jpa.InvJob;
import org.adorsys.adinvtry.jpa.InvPrcssgStep;
import org.adorsys.adinvtry.jpa.InvStep;
import org.adorsys.adinvtry.rest.InvInvtryBatch;
import org.adorsys.adinvtry.rest.InvInvtryItemEJB;
import org.adorsys.adinvtry.rest.InvInvtryItemLookup;
import org.adorsys.adinvtry.rest.InvStepLookup;
import org.adorsys.adstock.jpa.StkArticleLot;
import org.adorsys.adstock.jpa.StkArticleLot2StrgSctn;
import org.adorsys.adstock.rest.StkArticleLot2StrgSctnLookup;
import org.adorsys.adstock.rest.StkArticleLotLookup;

@Stateless
public class InvInvtryPrepareExecTask extends CoreAbstEntityJobExecutor<InvJob, InvStep, InvPrcssgStep> {

	@EJB
	private InvInvtryBatch batch;
	@EJB
	private InvInvtryPrepareExecTask task;
	
	@Inject
	private InvInvtryItemEJB itemEJB;
	@Inject
	private StkArticleLotLookup articleLotLookup;
	@Inject
	private InvInvtryItemLookup itemLookup;
	@Inject
	private InvStepLookup stepLookup;
	@Inject
	private StkArticleLot2StrgSctnLookup lot2SctLookup;

	public void execute(String stepIdentif) {
		InvStep step = stepLookup.findByIdentif(stepIdentif);
		if (step == null)return;
		createInvntryItems(step);
		batch.terminateStep(stepIdentif);
	}

	private void createInvntryItems(InvStep step) {
		Long eltCount = lot2SctLookup.countByIdentifBetween(
				step.getStepStartId(), step.getStepEndId());
		int eltStart = 0;
		int max = 100;
		while (eltStart < eltCount) {
			int firstResult = eltStart;
			eltStart += max;
			List<StkArticleLot2StrgSctn> lot2Sections = lot2SctLookup
					.findByIdentifBetween(step.getStepStartId(),
							step.getStepEndId(), firstResult, max);
			for (StkArticleLot2StrgSctn lot2StrgSctn : lot2Sections) {
				String salIndex = InvInvtryItem.toSalIndex(
						lot2StrgSctn.getSection(), lot2StrgSctn.getLotPic(),
						lot2StrgSctn.getArtPic());

				Long itemCount = itemLookup.countByCntnrIdentifAndSalIndex(
						step.getEntIdentif(), salIndex);
				if (itemCount > 0)
					continue;

				InvInvtryItem invtryItem = new InvInvtryItem();
				StkArticleLot articleLot = articleLotLookup
						.findByIdentif(lot2StrgSctn.getLotPic());
				articleLot.copyTo(invtryItem);
				invtryItem.setCntnrIdentif(step.getEntIdentif());
				invtryItem.setSection(lot2StrgSctn.getSection());
				invtryItem.setExpectedQty(lot2StrgSctn.getStockQty());
				invtryItem.setValueDt(lot2StrgSctn.getQtyDt());

				itemCount = itemLookup.countByCntnrIdentifAndSalIndex(
						step.getEntIdentif(), salIndex);
				if (itemCount > 0)
					continue;
				invtryItem = itemEJB.create(invtryItem);
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
