package org.adorsys.adcom.adcshdwr.client;

import javax.ws.rs.Path;

import org.adorsys.adcom.adres.lib.KeyCloakConfigEndpoint;

@Path("/keycloak.json")
public class CdrKeyCloakConfigEndpoint extends KeyCloakConfigEndpoint {

	@Override
	protected String getResource() {
		return "adcshdwr.client";
	}

}
