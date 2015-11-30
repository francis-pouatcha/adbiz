package org.adorsys.adprocmt.loader;

import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.adorsys.adcore.loader.ejb.CorLdrBatch;
import org.adorsys.adcore.loader.ejb.CorLdrStepLookup;
import org.adorsys.adcore.loader.jpa.CorLdrJob;
import org.adorsys.adcore.loader.jpa.CorLdrPrcssngStep;
import org.adorsys.adcore.loader.jpa.CorLdrStep;
import org.adorsys.adcore.rest.CoreAbstEntityJobExecutor;
import org.adorsys.adcore.task.CoreAbstEntityBatch;

public class PrcmtDeliveryLoaderExecutor extends CoreAbstEntityJobExecutor<CorLdrJob, CorLdrStep, CorLdrPrcssngStep>{
	@Inject
	private DataSheetLoader dataSheetLoader;
	@Inject
	private CorLdrStepLookup stepLookup;
	@EJB
	private CorLdrBatch batch;
	@EJB
	private PrcmtDeliveryLoaderExecutor ejb;

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void execute(String stepIdentif) {
		CorLdrStep step = stepLookup.findByIdentif(stepIdentif);
		if(step==null || step.getEnded()!=null) return;
		dataSheetLoader.processSingleFile(step.getCntnrIdentif(), step.getEntIdentif(), step.getIdentif());
	}

	@Override
	protected CoreAbstEntityBatch<CorLdrJob, CorLdrStep, CorLdrPrcssngStep> getBatch() {
		return batch;
	}

	@Override
	protected CoreAbstEntityJobExecutor<CorLdrJob, CorLdrStep, CorLdrPrcssngStep> getEjb() {
		return ejb;
	}
}
