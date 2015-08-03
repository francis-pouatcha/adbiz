package org.adorsys.adcore.xls;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;

import org.adorsys.adcore.loader.ejb.CorLdrBatch;
import org.adorsys.adcore.loader.ejb.CorLdrJobEJB;
import org.adorsys.adcore.loader.ejb.CorLdrJobLookup;
import org.adorsys.adcore.loader.ejb.CorLdrStepEJB;
import org.adorsys.adcore.loader.ejb.CorLdrStepLookup;
import org.adorsys.adcore.loader.jpa.CorLdrJob;
import org.adorsys.adcore.loader.jpa.CorLdrPrcssngStep;
import org.adorsys.adcore.loader.jpa.CorLdrStep;
import org.adorsys.adcore.rest.CoreAbstEntityJobExecutor;
import org.adorsys.adcore.task.CoreAbstEntityBatch;

public abstract class CoreAbstLoaderRegistration extends CoreAbstEntityJobExecutor<CorLdrJob, CorLdrStep, CorLdrPrcssngStep>{

	@Inject
	private CorLdrStepLookup stepLookup;
	@Inject
	private CorLdrStepEJB stepEJB;
	@Inject
	private CorLdrJobLookup jobLookup;
	@Inject
	private CorLdrJobEJB jobEJB;
	@Inject
	private CorLdrBatch batch;
	
	protected abstract AbstractLoader getDataSheetLoader();
	protected abstract CoreAbstEntityJobExecutor<CorLdrJob, CorLdrStep, CorLdrPrcssngStep> getExecTask();
	
	@PostConstruct
	public void postConstruct(){
		super.postConstruct();
		createLoaderJob();
	}

	@Override
	public void execute(String stepIdentif) {
		CorLdrStep s = stepLookup.findByIdentif(stepIdentif);
		if(s==null) return;
		List<File> listFiles = getDataSheetLoader().listFiles();
		int processedCount = 0;
		for (File file : listFiles) {
			String entityIdentif = file.getName();
			String cntnrIdentif = getDataSheetLoader().getDir();
			Long count = stepLookup.countByCntnrIdentifAndEntIdentif(cntnrIdentif, entityIdentif);
			if(count>0) continue;
			CorLdrStep step = new CorLdrStep();
			step.setCntnrIdentif(cntnrIdentif);
			step.setEntIdentif(entityIdentif);
			step.setExecutorId(getExecTask().getExecutorId());
			step.setSchdldStart(new Date());
			stepEJB.create(step);
			processedCount+=1;
		}
		if(processedCount<=0){
			getBatch().reschedule(stepIdentif, 5000);
		} else {
			getBatch().reschedule(stepIdentif, 300000);
		}
	}

	@Override
	protected CoreAbstEntityBatch<CorLdrJob, CorLdrStep, CorLdrPrcssngStep> getBatch() {
		return batch;
	}

	public void createLoaderJob(){
		String identif = getDataSheetLoader().getDir();
		
		CorLdrJob job = jobLookup.findByIdentif(identif);
		if(job!=null) return;
		
		job = new CorLdrJob();
		job.setEntIdentif(identif);
		job.setCntnrIdentif(identif);
		job.setIdentif(identif);
		job.setExecutorId(getClass().getSimpleName());
		try {
			job = jobEJB.create(job);
		} catch(EntityExistsException e){// another process created
			return;
		}
		
		CorLdrStep step = new CorLdrStep();
		step.setCntnrIdentif(job.getIdentif());
		step.setEntIdentif(identif);
		job.setIdentif(identif);
		step.setExecutorId(getClass().getSimpleName());
		step.setSchdldStart(new Date());
		stepEJB.create(step);
	}
}
