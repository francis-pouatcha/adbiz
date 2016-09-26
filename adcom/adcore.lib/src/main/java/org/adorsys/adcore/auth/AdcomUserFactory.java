package org.adorsys.adcore.auth;

import java.security.Principal;

import javax.ejb.SessionContext;

import org.keycloak.KeycloakPrincipal;

public final class AdcomUserFactory {


	private static AdcomUser unauthUser = new AdcomUser("nobody");
	
	public static  AdcomUser getAdcomUser(SessionContext sessionContext){
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
