package org.adorsys.adcom.addashboard.client;

import javax.ws.rs.Path;

import org.adorsys.adcom.adres.lib.KeyCloakConfigEndpoint;

@Path("/keycloak.json")
public class AddashboardKeyCloakConfigEndpoint extends KeyCloakConfigEndpoint {

	@Override
	protected String getResource() {
		return "addashboard.client";
	}

}
