package org.adorsys.adcom.adres.lib;

public interface KeyCloakConfigResolver {
	public String getResource();
	public String getRealmPublicKey();
	public String getAuthServerUrl();
	public boolean isExposeToken();
	public String getSecret();
	public boolean isPublicClient();
}
