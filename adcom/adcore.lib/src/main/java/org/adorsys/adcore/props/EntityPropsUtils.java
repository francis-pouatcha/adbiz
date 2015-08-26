package org.adorsys.adcore.props;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.inject.Singleton;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

@Singleton
public class EntityPropsUtils {

	private static final String defaultLang = "en";
	// TODO load this from a property file. // Single config location like keycloack.
	private List<String> languageList = Arrays.asList("en", "fr");

	public Map<String, Properties> load(Class<?> klass, List<String> languageList) {
		if(languageList==null) languageList=this.languageList;
		Map<String, Properties> translationMap = new HashMap<String, Properties>();
		try {
			loadInternal(translationMap, klass, null);
			for (String lg : languageList) {
				loadInternal(translationMap, klass, lg);
			}
		} catch (IOException e){
			throw new IllegalStateException(e);
		}
		return translationMap;
	}
	
	private void loadInternal(Map<String, Properties> translationMap, Class<?> klass, String lg) throws IOException{
		InputStream inputStream = null;
		if(StringUtils.isBlank(lg)){
			lg = defaultLang;
			inputStream = klass.getResourceAsStream(klass.getSimpleName()+".properties");
		}
		if(inputStream==null){
			if(translationMap.containsKey(lg)) return;// Property already loaded
			inputStream = klass.getResourceAsStream(klass.getSimpleName()+"_"+lg+".properties");
			if(inputStream==null){
				return;
				/*if(lg.equals(defaultLang)){
					throw new IllegalStateException("Missing property file: " + klass.getSimpleName()+".properties or " + klass.getSimpleName()+"_"+lg+".properties");
				} else {
					throw new IllegalStateException("Missing property file: " + klass.getSimpleName()+"_"+lg+".properties");
				}*/
			}
		}
		
		try {
			Properties props = new Properties();
			props.load(inputStream);
			translationMap.put(lg, props);
		} finally {
			IOUtils.closeQuietly(inputStream);
		}
	}
}
