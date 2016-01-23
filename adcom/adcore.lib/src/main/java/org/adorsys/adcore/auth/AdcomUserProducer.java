package org.adorsys.adcore.auth;

import java.security.Principal;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;

import org.keycloak.KeycloakPrincipal;

@Stateless
public class AdcomUserProducer {

	@Resource
	private SessionContext sessionContext;
	
	private AdcomUser unauthUser = new AdcomUser("nobody");
	
	@Produces
	public AdcomUser getCallerPrincipal() {
		Principal callerPrincipal = sessionContext.getCallerPrincipal();
		if(callerPrincipal==null){
			return unauthUser;
		}
		
		if(callerPrincipal instanceof KeycloakPrincipal){
			KeycloakPrincipal<?> kcPrincipal = (KeycloakPrincipal<?>) callerPrincipal;
			return new AdcomUser(kcPrincipal.getKeycloakSecurityContext());
		}

		return new AdcomUser(callerPrincipal.getName());
	}

}
