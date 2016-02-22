package org.adorsys.adcore.xls;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.adorsys.adcore.loader.ejb.CorLdrBatch;
import org.adorsys.adcore.loader.ejb.CorLdrFileStreamEJB;
import org.adorsys.adcore.loader.ejb.CorLdrFileStreamLookup;
import org.adorsys.adcore.loader.ejb.CorLdrStepLookup;
import org.adorsys.adcore.loader.jpa.CorLdrFileStream;
import org.adorsys.adcore.loader.jpa.CorLdrJob;
import org.adorsys.adcore.loader.jpa.CorLdrPrcssngStep;
import org.adorsys.adcore.loader.jpa.CorLdrStep;
import org.adorsys.adcore.rest.CoreAbstEntityJobExecutor;
import org.adorsys.adcore.task.CoreAbstEntityBatch;
import org.apache.commons.io.FileUtils;

public abstract class CorAbstDataSheetLoaderExecutor extends CoreAbstEntityJobExecutor<CorLdrJob, CorLdrStep, CorLdrPrcssngStep>{

	protected abstract AbstractLoader getLoader();
	
	@Inject
	private CorLdrStepLookup stepLookup;
	@EJB
	private CorLdrBatch batch;

	@Inject
	private CorLdrFileStreamEJB fileStreamEJB;
	@Inject
	private CorLdrFileStreamLookup fileStreamLookup;

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void execute(String stepIdentif) {
		CorLdrStep step = stepLookup.findByIdentif(stepIdentif);
		if(step==null || step.getEnded()!=null) return;
		
		Long countByCntnrIdentif = fileStreamLookup.countByCntnrIdentif(step.getIdentif());
		int processed = 0;
		int max = 5;
		while(processed<countByCntnrIdentif){
			int start = processed;
			processed+=max;
			List<CorLdrFileStream> found = fileStreamLookup.findByCntnrIdentif(step.getIdentif(), start, max);
			for (CorLdrFileStream corLdrFileStream : found) {
				File file = new File(corLdrFileStream.getFileName());
				String name = file.getName();
				try {
					File createTempFile = File.createTempFile(UUID.randomUUID().toString(), name);
					fileStreamEJB.load(corLdrFileStream, createTempFile);
					boolean finishedProcessing = getLoader().processSingleFile(step.getCntnrIdentif(), createTempFile.getAbsolutePath(), step.getIdentif());
					FileUtils.deleteQuietly(createTempFile);
					if(finishedProcessing) {
						fileStreamEJB.deleteById(corLdrFileStream.getId());
						batch.terminateStep(stepIdentif);
					} else {
						batch.reschedule(stepIdentif, 8000);
					}
				} catch (IOException e) {
					throw new IllegalStateException(e);
				}
			}
		}
	}

	@Override
	protected CoreAbstEntityBatch<CorLdrJob, CorLdrStep, CorLdrPrcssngStep> getBatch() {
		return batch;
	}
}
