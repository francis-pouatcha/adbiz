package org.adorsys.adkcloak.services.xls;

import org.keycloak.representations.idm.UserRepresentation;

public class UserReprestn extends UserRepresentation {
	private String realmId;

	public String getRealmId() {
		return realmId;
	}

	public void setRealmId(String realmId) {
		this.realmId = realmId;
	}
}
