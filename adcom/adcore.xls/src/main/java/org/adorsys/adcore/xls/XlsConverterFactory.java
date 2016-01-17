package org.adorsys.adcore.xls;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class XlsConverterFactory {

	private Map<String, Converter> converters = new HashMap<String, Converter>();
	
	private static XlsConverterFactory instance = new XlsConverterFactory();
	public static XlsConverterFactory singleton(){
		return instance;
	}
	private XlsConverterFactory(){
		registerConverter(String.class.getName(), new StringConverter());
		registerConverter(Integer.class.getName(), new IntegerConverter());
		registerConverter(int.class.getName(), new PrimitiveIntegerConverter());
		registerConverter(Long.class.getName(), new LongConverter());
		registerConverter(long.class.getName(), new PrimitiveLongConverter());
		registerConverter(Double.class.getName(), new DoubleConverter());
		registerConverter(double.class.getName(), new PrimitiveDoubleConverter());
		registerConverter(Short.class.getName(), new ShortConverter());
		registerConverter(short.class.getName(), new PrimitiveShortConverter());
		registerConverter(BigDecimal.class.getName(), new BigDecimalConverter());
		registerConverter(Boolean.class.getName(), new BooleanConverter());
		registerConverter(boolean.class.getName(), new PrimitiveBooleanConverter());
		registerConverter(Date.class.getName(), new DateConverter());
	}
	
	public void registerConverter(String key, Converter converter){
		converters.put(key, converter);
	}
	
	public Converter findConverter(String key){
		return converters.get(key);
	}
}
