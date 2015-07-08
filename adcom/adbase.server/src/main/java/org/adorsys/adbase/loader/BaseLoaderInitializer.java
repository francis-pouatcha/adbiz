package org.adorsys.adbase.loader;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.adorsys.adbase.spi.BaseEnumDataProvider;

@Startup
@Singleton
public class BaseLoaderInitializer {
	
	@Inject
	private BaseEnumDataProvider baseEnumDataProvider;
	
	@Inject
	private BaseHistoryTypeLoader baseHistoryTypeLoader;
	@Inject
	private BaseDocTypeLoader baseDocTypeLoader;
	@Inject
	private BaseProcessStatusLoader baseProcessStatusLoader;
	@Inject
	private BaseProcStepLoader baseProcStepLoader;
	@Inject
	private BaseRoleInProcessLoader baseRoleInProcessLoader;
	@Inject
	private BaseSttlmtOpLoader baseSttlmtOpLoader;
	
	@PostConstruct
	public void postConstruct(){
		baseHistoryTypeLoader.load(baseEnumDataProvider.getHistoryTypeData());
		baseDocTypeLoader.load(baseEnumDataProvider.getDocTypeData());
		baseProcessStatusLoader.load(baseEnumDataProvider.getProcessStatusData());
		baseProcStepLoader.load(baseEnumDataProvider.getProcStepData());
		baseRoleInProcessLoader.load(baseEnumDataProvider.getRoleInProcessData());
		baseSttlmtOpLoader.load(baseEnumDataProvider.getSttlmtOpData());
	}
}
