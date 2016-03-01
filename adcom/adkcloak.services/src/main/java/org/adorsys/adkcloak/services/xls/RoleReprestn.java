package org.adorsys.adkcloak.services.xls;

import org.keycloak.representations.idm.RoleRepresentation;

public class RoleReprestn extends RoleRepresentation {
	private String realmId;

	public String getRealmId() {
		return realmId;
	}

	public void setRealmId(String realmId) {
		this.realmId = realmId;
	}
}
