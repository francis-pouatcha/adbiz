package org.adorsys.adinvtry.loader;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.adorsys.adcore.loader.jpa.CorLdrJob;
import org.adorsys.adcore.loader.jpa.CorLdrPrcssngStep;
import org.adorsys.adcore.loader.jpa.CorLdrStep;
import org.adorsys.adcore.rest.CoreAbstEntityJobExecutor;
import org.adorsys.adcore.xls.AbstractLoader;
import org.adorsys.adcore.xls.CoreAbstLoaderRegistration;
import org.adorsys.adinvtry.jpa.InvInvtry;
import org.adorsys.adinvtry.jpa.InvInvtryItem;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

@Startup
@Singleton
public class InvLoaderRegistration extends CoreAbstLoaderRegistration{

	@Inject
	private DataSheetLoader dataSheetLoader;
	@Inject
	private InvInvtryLoader intInvInvtryLoader;
	@Inject
	private InvInvtryItemLoader invInvtryItemLoader;
	@EJB
	private InvLoaderRegistration registration;
	
	@EJB
	private InvLoaderExecutor execTask;
	
	@PostConstruct
	public void postConstruct(){
		super.postConstruct();
		dataSheetLoader.registerLoader(InvInvtry.class.getSimpleName(), intInvInvtryLoader);
		dataSheetLoader.registerLoader(InvInvtryItem.class.getSimpleName(), invInvtryItemLoader);
		createTemplate();
	}

	@Override
	protected CoreAbstEntityJobExecutor<CorLdrJob, CorLdrStep, CorLdrPrcssngStep> getEjb() {
		return registration;
	}

	@Override
	protected AbstractLoader getDataSheetLoader() {
		return dataSheetLoader;
	}

	@Override
	protected CoreAbstEntityJobExecutor<CorLdrJob, CorLdrStep, CorLdrPrcssngStep> getExecTask() {
		return execTask;
	}

	public void createTemplate(){
		HSSFWorkbook workbook = new HSSFWorkbook();
		intInvInvtryLoader.createTemplate(workbook);
		invInvtryItemLoader.createTemplate(workbook);
		dataSheetLoader.exportTemplate(workbook);
	}
}
