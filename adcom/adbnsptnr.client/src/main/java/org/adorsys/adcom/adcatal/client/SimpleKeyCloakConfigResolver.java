package org.adorsys.adcom.adcatal.client;

import org.adorsys.adcom.adres.lib.KeyCloakConfigResolver;

public class SimpleKeyCloakConfigResolver implements KeyCloakConfigResolver {

	@Override
	public String getResource() {
		return "adbnsptnr.client";
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
		return System.getProperty("adbnsptnr.client.credential");
	}
	
	@Override
	public boolean isPublicClient(){
		return true;
	}

}
