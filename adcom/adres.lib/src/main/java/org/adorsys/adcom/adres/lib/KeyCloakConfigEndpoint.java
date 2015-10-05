package org.adorsys.adcom.adres.lib;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/keycloak.json")
public class KeyCloakConfigEndpoint {
	
	private KeyCloak keyCloak;
	
	@Inject
	private KeyCloakConfigResolver configResolver;
	
	@PostConstruct
	public void postConstruct(){
		keyCloak = new KeyCloak(configResolver.getResource(), 
				configResolver.getRealmPublicKey(), 
				configResolver.getAuthServerUrl(), 
				configResolver.isExposeToken(), 
				configResolver.getSecret(), configResolver.isPublicClient());
	}
	
	@GET
	@Produces({ "application/json"})
	public KeyCloak getKeyCloakConfig(){
		return keyCloak;
	}
}
