package org.adorsys.adprocmt.loader;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.adorsys.adcore.loader.ejb.CorLdrConstraintChecker;
import org.adorsys.adcore.loader.jpa.CorLdrJob;
import org.adorsys.adcore.loader.jpa.CorLdrJobCstr;
import org.adorsys.adcore.loader.jpa.CorLdrPrcssngStep;
import org.adorsys.adcore.loader.jpa.CorLdrStep;
import org.adorsys.adcore.rest.CoreAbstEntityJobExecutor;
import org.adorsys.adcore.xls.AbstractLoader;
import org.adorsys.adcore.xls.CoreAbstLoaderRegistration;
import org.adorsys.adprocmt.api.PrcmtDeliveryExcel;
import org.adorsys.adprocmt.api.PrcmtDlvryItemExcel;

@Startup
@Singleton
public class PrcmtLoaderRegistration extends CoreAbstLoaderRegistration {

	@Inject
	private DataSheetLoader dataSheetLoader;
	@Inject
	private PrcmtDeliveryLoader prcmtDeliveryLoader;
	@Inject
	private PrcmtDlvryItemLoader prcmtDlvryItemLoader;
	@EJB
	private PrcmtLoaderRegistration registration;
	@EJB
	private PrcmtLoaderExecutor execTask;
	@Inject
	private CorLdrConstraintChecker constraintChecker;

	@PostConstruct
	public void postConstruct(){
		super.postConstruct();
		dataSheetLoader.registerLoader(PrcmtDeliveryExcel.class.getSimpleName(), prcmtDeliveryLoader);
		dataSheetLoader.registerLoader(PrcmtDlvryItemExcel.class.getSimpleName(), prcmtDlvryItemLoader);
		dataSheetLoader.registerLoader(CorLdrJobCstr.class.getSimpleName(), constraintChecker);
	}

	@Override
	protected AbstractLoader getDataSheetLoader() {
		return dataSheetLoader;
	}

	@Override
	protected CoreAbstEntityJobExecutor<CorLdrJob, CorLdrStep, CorLdrPrcssngStep> getExecTask() {
		return execTask;
	}

	@Override
	protected CoreAbstEntityJobExecutor<CorLdrJob, CorLdrStep, CorLdrPrcssngStep> getEjb() {
		return registration;
	}	
}
