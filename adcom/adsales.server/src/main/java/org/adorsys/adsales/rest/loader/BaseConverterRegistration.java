package org.adorsys.adsales.rest.loader;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.adorsys.adcore.enums.CoreProcessStatusEnum;
import org.adorsys.adcore.xls.XlsConverterFactory;

@Startup
@Singleton
public class BaseConverterRegistration {

	@Inject
	private BaseProcessStatusConverter processStatusConverter;

	@Inject
	private XlsConverterFactory converterFactory;
	
	@PostConstruct
	public void postConstruct(){
		converterFactory.registerConverter(CoreProcessStatusEnum.class.getName(), processStatusConverter);
	}
}
