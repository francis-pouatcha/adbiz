package org.adorsys.adcom.adsales.client;

import org.adorsys.adcom.adres.lib.KeyCloakConfigResolver;

public class SimpleKeyCloakConfigResolver implements KeyCloakConfigResolver {

	@Override
	public String getResource() {
		return "adsales.client";
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
		return System.getProperty("adsales.client.credential");
	}
	
	@Override
	public boolean isPublicClient(){
		return true;
	}

}
