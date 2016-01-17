package org.adorsys.adkcloak.services.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

public class KeycloakProperties {
	public static final String propertyFileName = "keycloak-config.properties";
	Properties properties = new Properties();
	File file = new File(propertyFileName);
	
	private static KeycloakProperties instance = new KeycloakProperties();
	public static final KeycloakProperties singleton(){
		return instance;
	}
	
	private KeycloakProperties(){
		if(file.exists()){
			FileInputStream fileInputStream = null;
			try {
				fileInputStream = new FileInputStream(file);
				properties.load(fileInputStream);
			} catch (IOException e) {
				throw new IllegalStateException(e);
			} finally {
				IOUtils.closeQuietly(fileInputStream);
			}
		}
	}
	
	public void addProperperty(String key, String value){
		properties.put(key, value);
		store();
	}
	
	public void store(){
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(file);
			properties.store(fileOutputStream, "");
		} catch (IOException e) {
			throw new IllegalStateException(e);
		} finally {
			IOUtils.closeQuietly(fileOutputStream);
		}
	}
}
