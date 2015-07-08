package org.adorsys.adbase.loader;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.PermActionEnum;
import org.adorsys.adcore.xls.XlsConverterFactory;

@Startup
@Singleton
public class BaseConverterRegistration {

	@Inject
	private PermActionEnumConverter permActionEnumConverter;

	@Inject
	private XlsConverterFactory converterFactory;
	
	@PostConstruct
	public void postConstruct(){
		converterFactory.registerConverter(PermActionEnum.class.getName(), permActionEnumConverter);
	}
}
