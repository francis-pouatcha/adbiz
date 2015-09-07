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
	private PrimitiveIntegerConverter primitiveIntegerConverter;
	
	@Inject
	private LongConverter longConverter;

	@Inject
	private PrimitiveLongConverter primitiveLongConverter;

	@Inject
	private DoubleConverter doubleConverter;

	@Inject
	private PrimitiveDoubleConverter primitiveDoubleConverter;

	@Inject
	private ShortConverter shortConverter;
	
	@Inject
	private PrimitiveShortConverter primitiveShortConverter;
	
	@Inject
	private BooleanConverter booleanConverter;
	
	@Inject
	private PrimitiveBooleanConverter primitiveBooleanConverter;
	
	@Inject
	private DateConverter dateConverter;
	
	@Inject
	private XlsConverterFactory converterFactory;
	
	@PostConstruct
	public void postConstruct(){
		converterFactory.registerConverter(String.class.getName(), stringConverter);
		converterFactory.registerConverter(Integer.class.getName(), integerConverter);
		converterFactory.registerConverter(int.class.getName(), primitiveIntegerConverter);
		converterFactory.registerConverter(Long.class.getName(), longConverter);
		converterFactory.registerConverter(long.class.getName(), primitiveLongConverter);
		converterFactory.registerConverter(Double.class.getName(), doubleConverter);
		converterFactory.registerConverter(double.class.getName(), primitiveDoubleConverter);
		converterFactory.registerConverter(Short.class.getName(), shortConverter);
		converterFactory.registerConverter(short.class.getName(), primitiveShortConverter);
		converterFactory.registerConverter(BigDecimal.class.getName(), bigDecimalConverter);
		converterFactory.registerConverter(Boolean.class.getName(), booleanConverter);
		converterFactory.registerConverter(boolean.class.getName(), primitiveBooleanConverter);
		converterFactory.registerConverter(Date.class.getName(), dateConverter);
	}
}
