package org.adorsys.adcom.adprocmt.client;

import javax.ws.rs.Path;

import org.adorsys.adcom.adres.lib.KeyCloakConfigEndpoint;

@Path("/keycloak.json")
public class PcrmtKeyCloakConfigEndpoint extends KeyCloakConfigEndpoint {

	@Override
	protected String getResource() {
		return "adprocmt.client";
	}

}
