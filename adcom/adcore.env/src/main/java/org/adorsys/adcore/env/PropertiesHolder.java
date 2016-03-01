package org.adorsys.adcore.env;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesHolder {

//	public static final String propertyFileName = "keycloak-config.properties";
	
	Properties properties = new Properties();
	
	private static Map<String, PropertiesHolder> instances = new HashMap<String, PropertiesHolder>();
	
	public static final PropertiesHolder singleton(String propertiesFilePath){
		PropertiesHolder propertiesHolder = instances.get(propertiesFilePath);
		if(propertiesHolder!=null)return propertiesHolder;
		
		synchronized (PropertiesHolder.class) {
			propertiesHolder = instances.get(propertiesFilePath);
			if(propertiesHolder!=null)return propertiesHolder;
			
			propertiesHolder = new PropertiesHolder(propertiesFilePath);
			instances.put(propertiesFilePath, propertiesHolder);
			return propertiesHolder;
		}
	}
	
	private File propertyFile;
	private long lastModified;
	private PropertiesHolder(String propertiesFilePath){
		propertyFile = new File(propertiesFilePath);
		loadProperties();
	}
	
	private void loadProperties(){
		if(propertyFile.exists()){
			FileInputStream fileInputStream = null;
			try {
				fileInputStream = new FileInputStream(propertyFile);
				properties.load(fileInputStream);
			} catch (IOException e) {
				throw new IllegalStateException(e);
			} finally {
				IOHelpers.closeQuietly(fileInputStream);
			}
			lastModified = propertyFile.lastModified();
		} else {
			throw new IllegalStateException("Non existant property file : " + propertyFile.getAbsolutePath());
		}
	}
	
	private void needReload(){
		if(propertyFile.lastModified()>lastModified){
			loadProperties();
		}
	}
	
	public void addProperperty(String key, String value){
		properties.put(key, value);
		store();
	}
	
	public void store(){
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(propertyFile);
			properties.store(fileOutputStream, "");
			lastModified = System.currentTimeMillis();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		} finally {
			IOHelpers.closeQuietly(fileOutputStream);
		}
	}
	
	public String getProperty(String key){
		needReload();
		return properties.getProperty(key);
	}
	
	public Properties toProperties(){
		needReload();
		Properties properties2 = new Properties();
		properties2.putAll(properties);
		return properties2;
	}
}
