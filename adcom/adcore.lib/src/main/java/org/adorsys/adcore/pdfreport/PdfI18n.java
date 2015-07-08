package org.adorsys.adcore.pdfreport;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.ejb.Singleton;
/**
 * 
 * @author guymoyo
 *
 */
@Singleton
public class PdfI18n {

	private Map<String, Map<String, Properties>> globalTranslationMap = new HashMap<String, Map<String, Properties>>();
	
	public void registerKlass(Class<?> klass){
		if(globalTranslationMap.containsKey(klass.getName())) return;
		synchronized (klass) {
			if(!globalTranslationMap.containsKey(klass.getName()))
				globalTranslationMap.put(klass.getName(), new HashMap<String, Properties>());
		}
		load(klass);
	}
	
	public String i18n(Class<?> klass, String key, String lang) {	
		try {
			return globalTranslationMap.get(klass.getName()).get(lang).getProperty(key);
		} catch (Exception e) {
			return key;
		}
	}
	
	private static final String defaultLang = "en";
	private void load(Class<?> klass, String... langs) {
		try {
			InputStream inputStream = klass.getResourceAsStream(klass.getSimpleName()+".properties");
			Properties enProps = new Properties();
			enProps.load(inputStream);
			globalTranslationMap.get(klass.getName()).put(defaultLang, enProps);
			inputStream.close();
			
			for (String lg : langs) {
				if(globalTranslationMap.get(klass.getName()).containsKey(lg)) continue;// Property already loaded
				inputStream = klass.getResourceAsStream(klass.getSimpleName()+"_"+lg+".properties");
				if(inputStream==null) throw new IllegalStateException("Missing property file: " + klass.getSimpleName()+"_"+lg+".properties");
				Properties props = new Properties();
				props.load(inputStream);
				globalTranslationMap.get(klass.getName()).put(lg, props);
				inputStream.close();
			}
		} catch (IOException e) {
			// noop
		}
	}
	
	/*
	 * Will need this later for validatiion of completeness of property files.
	private List<String> getKeys(Class<?> klass) {
		List<String> keys = new ArrayList<String>();
		PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(klass);
		String name;
		for(int i=0;i<descriptors.length;i++){
			 name = descriptors[i].getName();
			if("id".equals(name) || "version".equals(name) || "class".equals(name))
				continue;	
			keys.add(name);
		}
		return keys;
	}
	 */

}
