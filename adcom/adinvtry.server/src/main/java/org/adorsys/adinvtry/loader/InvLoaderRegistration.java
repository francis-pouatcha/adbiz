package org.adorsys.adinvtry.loader;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.ejb.AccessTimeout;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

@Startup
@Singleton
public class InvLoaderRegistration {

	@Inject
	private DataSheetLoader dataSheetLoader;
	@Inject
	private InvInvtryLoader intInvInvtryLoader;
	@Inject
	private InvInvtryItemLoader invInvtryItemLoader;
	
	@PostConstruct
	public void postConstruct(){
		dataSheetLoader.registerLoader(InvInvtryExcel.class.getSimpleName(), intInvInvtryLoader);
		dataSheetLoader.registerLoader(InvInvtryItemExcel.class.getSimpleName(), invInvtryItemLoader);
		createTemplate();
	}

	@Schedule(minute = "*", second="3/33" ,hour="*", persistent=false)
	@AccessTimeout(unit=TimeUnit.MINUTES, value=10)
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void process() throws Exception {
		dataSheetLoader.process();
	}
	
	public void createTemplate(){
	}
	
}
