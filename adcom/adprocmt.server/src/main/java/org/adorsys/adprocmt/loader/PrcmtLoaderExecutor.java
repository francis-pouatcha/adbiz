package org.adorsys.adprocmt.loader;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.adorsys.adcore.loader.jpa.CorLdrJob;
import org.adorsys.adcore.loader.jpa.CorLdrPrcssngStep;
import org.adorsys.adcore.loader.jpa.CorLdrStep;
import org.adorsys.adcore.rest.CoreAbstEntityJobExecutor;
import org.adorsys.adcore.xls.AbstractLoader;
import org.adorsys.adcore.xls.CorAbstDataSheetLoaderExecutor;

@Startup
@Singleton
public class PrcmtLoaderExecutor extends CorAbstDataSheetLoaderExecutor {
	@Inject
	private DataSheetLoader dataSheetLoader;
	@EJB
	private PrcmtLoaderExecutor ejb;

	@Override
	protected CoreAbstEntityJobExecutor<CorLdrJob, CorLdrStep, CorLdrPrcssngStep> getEjb() {
		return ejb;
	}

	@Override
	protected AbstractLoader getLoader() {
		return dataSheetLoader;
	}
}
