package org.adorsys.adcom.adstock.client;

import javax.ws.rs.Path;

import org.adorsys.adcom.adres.lib.KeyCloakConfigEndpoint;

@Path("/keycloak.json")
public class StkKeyCloakConfigEndpoint extends KeyCloakConfigEndpoint {

	@Override
	protected String getResource() {
		return "adstock.client";
	}

}
