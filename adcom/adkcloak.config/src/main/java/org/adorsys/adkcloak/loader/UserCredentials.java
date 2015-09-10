package org.adorsys.adkcloak.loader;

import org.keycloak.representations.idm.CredentialRepresentation;

public class UserCredentials extends CredentialRepresentation {

	private String realmId;
	private String username;

	public String getRealmId() {
		return realmId;
	}

	public void setRealmId(String realmId) {
		this.realmId = realmId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
