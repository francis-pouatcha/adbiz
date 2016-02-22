package org.adorsys.adstock.loader;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.adorsys.adcore.xls.XlsConverterFactory;

@Startup
@Singleton
public class BaseConverterRegistration {

	@Inject
	private StkSectionTypeConverter sectionTypeConverter;
	
	private XlsConverterFactory converterFactory = XlsConverterFactory.singleton();
	
	@PostConstruct
	public void postConstruct(){
		converterFactory.registerConverter(StkSectionTypeConverter.class.getName(), sectionTypeConverter);
	}
}
