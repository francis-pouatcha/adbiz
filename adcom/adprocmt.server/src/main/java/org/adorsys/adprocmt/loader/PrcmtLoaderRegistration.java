package org.adorsys.adprocmt.loader;

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
import org.adorsys.adprocmt.jpa.PrcmtDelivery;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem;

@Startup
@Singleton
public class PrcmtLoaderRegistration extends CoreAbstLoaderRegistration {

	@Inject
	private DataSheetLoader dataSheetLoader;
	@Inject
	private PrcmtDeliveryLoader prcmtDeliveryLoader;
	@Inject
	private PrcmtDlvryItemLoader prcmtDlvryItemLoader;
	@Inject
	private PrcmtLoaderRegistration registration;
	@EJB
	private PrcmtDeliveryLoaderExecutor execTask;

	@PostConstruct
	public void postConstruct(){
		dataSheetLoader.registerLoader(PrcmtDelivery.class.getSimpleName(), prcmtDeliveryLoader);
		dataSheetLoader.registerLoader(PrcmtDlvryItem.class.getSimpleName(), prcmtDlvryItemLoader);
	}

	@Override
	protected AbstractLoader getDataSheetLoader() {
		return dataSheetLoader;
	}

	@Override
	protected CoreAbstEntityJobExecutor<CorLdrJob, CorLdrStep, CorLdrPrcssngStep> getExecTask() {
		return registration;
	}

	@Override
	protected CoreAbstEntityJobExecutor<CorLdrJob, CorLdrStep, CorLdrPrcssngStep> getEjb() {
		return execTask;
	}	
}
