package org.adorsys.adcom.adstock.client;

import org.adorsys.adcom.adres.lib.KeyCloakConfigResolver;

public class SimpleKeyCloakConfigResolver implements KeyCloakConfigResolver {

	@Override
	public String getResource() {
		return "adstock.client";
	}

	@Override
	public String getRealmPublicKey() {
		String prop = System.getProperty("realm-public-key");
		return prop;
	}

	@Override
	public String getAuthServerUrl() {
		String prop = System.getProperty("auth-server-url");
		return prop;
	}

	@Override
	public boolean isExposeToken() {
		return true;
	}

	@Override
	public String getSecret() {
		return null;
	}
	
	@Override
	public boolean isPublicClient(){
		return true;
	}

}
