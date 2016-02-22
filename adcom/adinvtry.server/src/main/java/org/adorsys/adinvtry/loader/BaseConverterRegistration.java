package org.adorsys.adinvtry.loader;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.adorsys.adcore.xls.XlsConverterFactory;
import org.adorsys.adinvtry.jpa.InvInvtryType;

@Startup
@Singleton
public class BaseConverterRegistration {

	@Inject
	private InvInvtryTypeConverter invInvtryTypeConverter;
	
	private XlsConverterFactory converterFactory = XlsConverterFactory.singleton();
	
	@PostConstruct
	public void postConstruct(){
		converterFactory.registerConverter(InvInvtryType.class.getName(), invInvtryTypeConverter);
	}
}
