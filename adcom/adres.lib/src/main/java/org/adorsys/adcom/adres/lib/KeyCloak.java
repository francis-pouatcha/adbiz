package org.adorsys.adcom.adres.lib;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonProperty;

public class KeyCloak {
	private String realm = "adcom";
	private String resource;
	@JsonProperty("realm-public-key")
	private String realmPublicKey;
	@JsonProperty("auth-server-url")
	private String authServerUrl;
	@JsonProperty("ssl-required")
	private String sslRequired = "external";
	@JsonProperty("expose-token")
	private boolean exposeToken = true;
	private Creds credentials;
	@JsonProperty("public-client")
	private boolean publicClient;

	public KeyCloak(String resource, String realmPublicKey,
			String authServerUrl, boolean exposeToken,
			String secret, boolean publicClient) {
		super();
//		this.realm = realm;
		this.resource = resource;
		this.realmPublicKey = realmPublicKey;
		this.authServerUrl = authServerUrl;
//		this.sslRequired = sslRequired;
		this.exposeToken = exposeToken;
		if(StringUtils.isNotBlank(secret)){
			this.credentials = new Creds();
			this.credentials.setSecret(secret);
		}
		this.publicClient = publicClient;
	}

	public String getRealm() {
		return realm;
	}

	public void setRealm(String realm) {
		this.realm = realm;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getRealmPublicKey() {
		return realmPublicKey;
	}

	public void setRealmPublicKey(String realmPublicKey) {
		this.realmPublicKey = realmPublicKey;
	}

	public String getAuthServerUrl() {
		return authServerUrl;
	}

	public void setAuthServerUrl(String authServerUrl) {
		this.authServerUrl = authServerUrl;
	}

	public String getSslRequired() {
		return sslRequired;
	}

	public void setSslRequired(String sslRequired) {
		this.sslRequired = sslRequired;
	}

	public boolean isExposeToken() {
		return exposeToken;
	}

	public void setExposeToken(boolean exposeToken) {
		this.exposeToken = exposeToken;
	}

	public Creds getCredentials() {
		return credentials;
	}

	public void setCredentials(Creds credentials) {
		this.credentials = credentials;
	}


	public boolean isPublicClient() {
		return publicClient;
	}

	public void setPublicClient(boolean publicClient) {
		this.publicClient = publicClient;
	}
}
