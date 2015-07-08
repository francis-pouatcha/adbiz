package org.adorsys.adcore.xls;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

@Singleton
public class XlsConverterFactory {

	private Map<String, Converter> converters = new HashMap<String, Converter>();
	
	public void registerConverter(String key, Converter converter){
		converters.put(key, converter);
	}
	
	public Converter findConverter(String key){
		return converters.get(key);
	}
}
