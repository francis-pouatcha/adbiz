package org.adorsys.adcore.auth;

import org.keycloak.KeycloakSecurityContext;

public final class AdcomUser {

	private final String name;

	private final KeycloakSecurityContext context;
	
	public AdcomUser(String name) {
		this(name, null);
	}

	public AdcomUser(KeycloakSecurityContext context) {
		this(context.getToken().getPreferredUsername(), context);
	}

	public AdcomUser(String name, KeycloakSecurityContext context) {
		this.name = name;
		this.context = context;
	}

	public String getLoginName(){
		return name;
	};
	
	public String getWorkspaceId(){
		return name;
	}

	public KeycloakSecurityContext getContext() {
		return context;
	}
}
