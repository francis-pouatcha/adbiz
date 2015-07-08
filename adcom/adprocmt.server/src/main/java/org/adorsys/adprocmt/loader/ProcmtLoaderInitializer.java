package org.adorsys.adprocmt.loader;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.adorsys.adprocmt.spi.ProcmtEnumDataProvider;

@Startup
@Singleton
public class ProcmtLoaderInitializer {
	
	@Inject
	private ProcmtEnumDataProvider procmtEnumDataProvider;
	
	@Inject
	private ProcmtPOTypeLoader procmtPOTypeLoader;
	@Inject
	private ProcmtPOTriggerModeLoader procmtPOTriggerModeLoader;

	@PostConstruct
	public void postConstruct(){
		procmtPOTriggerModeLoader.load(procmtEnumDataProvider.getProcmtPOTriggerModeData());
		procmtPOTypeLoader.load(procmtEnumDataProvider.getProcmtPOTypeData());
	}
}
