package org.adorsys.adkcloak.loader;

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
public class KcloakLoaderExecutor extends CorAbstDataSheetLoaderExecutor{

	@Inject
	private DataSheetLoader dataSheetLoader;
	@EJB
	private KcloakLoaderExecutor ejb;


	@Override
	protected CoreAbstEntityJobExecutor<CorLdrJob, CorLdrStep, CorLdrPrcssngStep> getEjb() {
		return ejb;
	}


	@Override
	protected AbstractLoader getLoader() {
		return dataSheetLoader;
	}
}
