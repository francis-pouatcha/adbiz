package org.adorsys.adstock.loader;

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
import org.adorsys.adstock.jpa.StkArt2Section;
import org.adorsys.adstock.jpa.StkSection;

@Startup
@Singleton
public class StkLoaderRegistration extends CoreAbstLoaderRegistration {

	@Inject
	private DataSheetLoader dataSheetLoader;
	@Inject
	private StkSectionLoader stkSectionLoader;
	@Inject
	private StkArt2SectionLoader stkArt2SectionLoader;
	@EJB
	private StkLoaderRegistration registration;
	@EJB
	private StkLoaderExecutor execTask;
	@Inject
	private CorLdrConstraintChecker constraintChecker;
	
	@PostConstruct
	public void postConstruct(){
		super.postConstruct();
		dataSheetLoader.registerLoader(StkSection.class.getSimpleName(), stkSectionLoader);
		dataSheetLoader.registerLoader(StkArt2Section.class.getSimpleName(), stkArt2SectionLoader);
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
