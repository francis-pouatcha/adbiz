package org.adorsys.adinvtry.task;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArtLangMapping;
import org.adorsys.adcatal.rest.CatalArtLangMappingLookup;
import org.adorsys.adcore.rest.CoreAbstEntityJobExecutor;
import org.adorsys.adcore.task.CoreAbstEntityBatch;
import org.adorsys.adinvtry.jpa.InvInvtry;
import org.adorsys.adinvtry.jpa.InvInvtryItem;
import org.adorsys.adinvtry.jpa.InvInvtryType;
import org.adorsys.adinvtry.jpa.InvJob;
import org.adorsys.adinvtry.jpa.InvPrcssgStep;
import org.adorsys.adinvtry.jpa.InvStep;
import org.adorsys.adinvtry.rest.InvInvtryBatch;
import org.adorsys.adinvtry.rest.InvInvtryItemEJB;
import org.adorsys.adinvtry.rest.InvInvtryItemLookup;
import org.adorsys.adinvtry.rest.InvInvtryLookup;
import org.adorsys.adinvtry.rest.InvStepLookup;
import org.adorsys.adstock.jpa.StkArticleLot;
import org.adorsys.adstock.jpa.StkArticleLot2StrgSctn;
import org.adorsys.adstock.rest.StkArticleLot2StrgSctnLookup;
import org.adorsys.adstock.rest.StkArticleLotLookup;
import org.apache.commons.lang3.StringUtils;

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
	@Inject
	private CatalArtLangMappingLookup artLangMappingLookup;
	@Inject
	private InvInvtryLookup invtryLookup;

	public void execute(String stepIdentif) {
		InvStep step = stepLookup.findByIdentif(stepIdentif);
		if (step == null)return;
		createInvntryItems(step);
		batch.terminateStep(stepIdentif);
	}

	private void createInvntryItems(InvStep step) {
		String entIdentif = step.getEntIdentif();
		InvInvtry inventory = invtryLookup.findByIdentif(entIdentif);
		InvInvtryType invType = null;
		try {
			invType = InvInvtryType.valueOf(inventory.getTxType());
		} catch (Exception e){
			invType = InvInvtryType.FREE_INV;
		}

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
				// Discard if not in range.
				List<CatalArtLangMapping> artLangMap = artLangMappingLookup.findByArtPic(lot2StrgSctn.getArtPic(), 0, 1);
				// Continue if not a candidate.
				if(!shallProcess(lot2StrgSctn, invType, inventory, artLangMap)) continue;
				
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
				// Nicht sinnvoll
//				invtryItem.setExpectedQty(lot2StrgSctn.getStockQty());
//				invtryItem.setQtyBefore(lot2StrgSctn.getStockQty());
				invtryItem.setValueDt(lot2StrgSctn.getQtyDt());
				if(!artLangMap.isEmpty()){
					invtryItem.setArtName(artLangMap.iterator().next().getArtName());
				}

				itemCount = itemLookup.countByCntnrIdentifAndSalIndex(
						step.getEntIdentif(), salIndex);
				
				if (itemCount > 0)
					continue;
				invtryItem = itemEJB.create(invtryItem);
			}
		}
	}

	/*
	 * Checks if the given inventory falls in the range of selected inventory items.
	 */
	private boolean shallProcess(StkArticleLot2StrgSctn lot2StrgSctn, InvInvtryType invType, InvInvtry inventory, List<CatalArtLangMapping> artLangMap) {
		switch (invType) {
		case BY_SECTION:
			// Discard if not the matching section for sectional inventories.
			if(StringUtils.equalsIgnoreCase(lot2StrgSctn.getSection(), inventory.getSection())) return true;
			return false;
		case ALPHABETICAL_ORDER_RANGE:
			if(artLangMap.isEmpty()) return false;
			for (CatalArtLangMapping catalArtLangMapping : artLangMap) {
				
				String artName = catalArtLangMapping.getArtName();
				if(StringUtils.isBlank(artName)) continue;
				
				if (StringUtils.isNotBlank(inventory.getRangeStart()) || inventory.getRangeStart().compareToIgnoreCase(artName)>0) 
					continue; 
				if (StringUtils.isNotBlank(inventory.getRangeEnd()) || inventory.getRangeEnd().compareToIgnoreCase(artName)<0)
					continue;
				
				return true;
			}
			return false;
		default:
			return true;
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
