package org.adorsys.adcore.xls;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Startup
@Singleton
public class ConverterRegistration {

	@Inject
	private StringConverter stringConverter;
	
	@Inject
	private BigDecimalConverter bigDecimalConverter;

	@Inject
	private IntegerConverter integerConverter;
	
	@Inject
	private LongConverter longConverter;

	@Inject
	private DoubleConverter doubleConverter;

	@Inject
	private ShortConverter shortConverter;
	
	@Inject
	private BooleanConverter booleanConverter;
	
	@Inject
	private DateConverter dateConverter;
	
	@Inject
	private XlsConverterFactory converterFactory;
	
	@PostConstruct
	public void postConstruct(){
		converterFactory.registerConverter(String.class.getName(), stringConverter);
		converterFactory.registerConverter(Integer.class.getName(), integerConverter);
		converterFactory.registerConverter(int.class.getName(), integerConverter);
		converterFactory.registerConverter(Long.class.getName(), longConverter);
		converterFactory.registerConverter(long.class.getName(), longConverter);
		converterFactory.registerConverter(Double.class.getName(), doubleConverter);
		converterFactory.registerConverter(double.class.getName(), doubleConverter);
		converterFactory.registerConverter(Short.class.getName(), shortConverter);
		converterFactory.registerConverter(short.class.getName(), shortConverter);
		converterFactory.registerConverter(BigDecimal.class.getName(), bigDecimalConverter);
		converterFactory.registerConverter(Boolean.class.getName(), booleanConverter);
		converterFactory.registerConverter(boolean.class.getName(), booleanConverter);
		converterFactory.registerConverter(Date.class.getName(), dateConverter);
	}
}
