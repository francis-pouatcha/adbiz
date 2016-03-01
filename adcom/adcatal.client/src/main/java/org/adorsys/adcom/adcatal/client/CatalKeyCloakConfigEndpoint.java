package org.adorsys.adcom.adcatal.client;

import javax.ws.rs.Path;

import org.adorsys.adcom.adres.lib.KeyCloakConfigEndpoint;

@Path("/keycloak.json")
public class CatalKeyCloakConfigEndpoint extends KeyCloakConfigEndpoint {
	@Override
	protected String getResource() {
		return "adcatal.client";
	}
}
