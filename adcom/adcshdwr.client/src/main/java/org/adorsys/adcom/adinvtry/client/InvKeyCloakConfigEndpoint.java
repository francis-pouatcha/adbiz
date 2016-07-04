package org.adorsys.adcom.adinvtry.client;

import javax.ws.rs.Path;

import org.adorsys.adcom.adres.lib.KeyCloakConfigEndpoint;

@Path("/keycloak.json")
public class InvKeyCloakConfigEndpoint extends KeyCloakConfigEndpoint {

	@Override
	protected String getResource() {
		return "adcshdwr.client";
	}

}
