package org.adorsys.adcore.env;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class EnvProperties {
	
	private final Properties properties;
	
	private static final EnvProperties staticInstance = new EnvProperties(null); 
	
	public EnvProperties(Properties properties) {
		if(properties==null) {
			this.properties=new Properties(); 
		} else {
			this.properties = properties;
		}
	}

	/*
	 * Read a property form machine environment. If not set, read it from System properties
	 */
	public String getProp(String propertyName) {
		return getEnvOrSystProp0(propertyName, true, null, null, false);
	}

	public String getProp(String propertyName, String defaultValue) {
		return getEnvOrSystProp0(propertyName, defaultValue, true, null, null, false);
	}

	public String getPropThrowException(String propertyName){
		return getEnvOrSystProp0(propertyName, true, null, null, true);
	}
	
	public String resolve(String propertyValue){
		return mapVariable(propertyValue, null, null, false);
	}

	/*
	 * Read a property form machine environment. If not set, read it from System properties
	 */
	public static String getEnvOrSystProp(String propertyName) {
		return staticInstance.getProp(propertyName);
	}

	public static String getEnvOrSystProp(String propertyName, String defaultValue) {
		return staticInstance.getProp(propertyName, defaultValue);
	}

	public static String getEnvOrSystPropThrowException(String propertyName){
		return staticInstance.getPropThrowException(propertyName);
	}
	
	private String mapVariable(String env, Map<String, String> context, Set<String> resolving, boolean throwException) {
		// Hold resolved variables
		if(context==null) context = new HashMap<String, String>();
		// Holds resolving keys to check for circularity.
		if(resolving==null) resolving = new HashSet<String>();
		
		if(StringUtils.contains(env, "${")){
			String[] propertyNames = StringUtils.substringsBetween(env, "${", "}");
			for (String propertyName : propertyNames) {
				if(resolving.contains(propertyName)) throw new IllegalStateException("Circular dependency on property : ${" + propertyName+"}");
				resolving.add(propertyName);
				String propertyValue = null;
				if(context.containsKey(propertyName)){
					propertyValue = context.get(propertyName);
				} else {
					propertyValue = getEnvOrSystProp0(propertyName, false, context, resolving, throwException);
					propertyValue = throwExceptionIfBlank(propertyName, propertyValue, throwException);
					if(hasProperties(propertyValue))propertyValue=mapVariable(propertyValue, context, resolving, throwException);
				}
				env = replaceStyle1(env, context, resolving, propertyName, propertyValue);
			}
		}
		if(StringUtils.contains(env, "$")){
			String[] propertyNames = StringUtils.splitPreserveAllTokens(env, "./-:");
			for (String propertyNamePrefixed : propertyNames) {
				if(StringUtils.contains(propertyNamePrefixed, "${")) continue;// take out the other case.
				if(!StringUtils.contains(propertyNamePrefixed, "$")) continue; // only process keys.
				String propertyName = StringUtils.substringAfter(propertyNamePrefixed, "$");
				if(resolving.contains(propertyName)) throw new IllegalStateException("Circular dependency on property : $" + propertyName+"");
				resolving.add(propertyName);
				String propertyValue = null;
				if(context.containsKey(propertyName)){
					propertyValue = context.get(propertyName);
				} else {
					propertyValue = getEnvOrSystProp0(propertyName, false, context, resolving, throwException);
					propertyValue = throwExceptionIfBlank(propertyName, propertyValue, throwException);
					if(hasProperties(propertyValue))propertyValue=mapVariable(propertyValue, context, resolving, throwException);
				}
				env = replaceStyle2(env, context, resolving, propertyName, propertyValue);
			}
		}
		return env;
	}
	
	private static String replaceStyle1(String env, Map<String, String> context, Set<String> resolving, String propertyName, String propertyValue){
		resolving.remove(propertyName);
		context.put(propertyName, propertyValue);
		if(StringUtils.isBlank(propertyValue)) return env;
		return StringUtils.replace(env, "${"+propertyName+"}", propertyValue);
	}
	
	private static String replaceStyle2(String env, Map<String, String> context, Set<String> resolving, String propertyName, String propertyValue){
		resolving.remove(propertyName);
		context.put(propertyName, propertyValue);
		if(StringUtils.isBlank(propertyValue)) return env;
		return StringUtils.replace(env, "$"+propertyName, propertyValue);
	}
	private static boolean hasProperties(String env){
		return StringUtils.contains(env, "$");
	}
	
	private static void throwExceptionOnBlankValue(String propertyName){
		throw new IllegalStateException("Property with name: " + propertyName + " neither defined as Evironment property nor as system property.");
	}
	private static void throwExceptionOnBlankKey(String key){
		if(StringUtils.isBlank(key))
		throw new IllegalStateException("Property key can not be blank");
	}
	
	private static String throwExceptionIfBlank(String propertyName, String propertyValue, boolean throwException){
		if(StringUtils.isBlank(propertyValue)){
			if(throwException) throwExceptionOnBlankValue(propertyName);
			return "";
		}		
		return propertyValue;
	}
	
	private String getEnvOrSystProp0(String propertyName, boolean resolve, Map<String, String> context, Set<String> resolving, boolean throwExceptionIfBlank) {
		throwExceptionOnBlankKey(propertyName);
		String env = properties.getProperty(propertyName);
		if(StringUtils.isBlank(env)) env = System.getenv(propertyName);
		if(StringUtils.isBlank(env)) env = System.getProperty(propertyName);
		if(!resolve) return env;
		// Resolve.
		if(!hasProperties(env)) return env;		
		return mapVariable(env, context, resolving, throwExceptionIfBlank);
	}

	private String getEnvOrSystProp0(String propertyName, String defaultValue, boolean resolve, Map<String, String> context, Set<String> resolving, boolean throwExceptionIfBlank) {
		throwExceptionOnBlankKey(propertyName);
		String env = properties.getProperty(propertyName);
		if(StringUtils.isBlank(env)) env = System.getenv(propertyName);
		if(StringUtils.isBlank(env)) env = System.getProperty(propertyName, defaultValue);
		if(!resolve) return env;
		// Resolve.
		if(!hasProperties(env)) return env;		
		return mapVariable(env, context, resolving, throwExceptionIfBlank);
	}
	
}
