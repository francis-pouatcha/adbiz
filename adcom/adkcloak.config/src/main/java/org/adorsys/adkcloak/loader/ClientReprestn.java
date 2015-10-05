package org.adorsys.adkcloak.loader;

import org.keycloak.representations.idm.ClientRepresentation;

public class ClientReprestn extends ClientRepresentation{
	private String realmId;
	private String redirectUriCsStg;
	
	public String getRealmId() {
		return realmId;
	}

	public void setRealmId(String realmId) {
		this.realmId = realmId;
	}

	public String getRedirectUriCsStg() {
		return redirectUriCsStg;
	}

	public void setRedirectUriCsStg(String redirectUriCsStg) {
		this.redirectUriCsStg = redirectUriCsStg;
	}
}
