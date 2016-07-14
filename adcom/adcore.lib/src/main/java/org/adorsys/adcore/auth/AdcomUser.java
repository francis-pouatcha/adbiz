package org.adorsys.adcore.auth;

import org.keycloak.KeycloakSecurityContext;

public final class AdcomUser {

	private final String name;
	
	private final String fullName;

	private final KeycloakSecurityContext context;
	
	public AdcomUser(String name) {
		this(name, null);
	}

	public AdcomUser(KeycloakSecurityContext context) {
		this(context.getToken().getPreferredUsername(), context);
	}

	public AdcomUser(String name, KeycloakSecurityContext context) {
		this(name, context, null);
	}

	public AdcomUser(String name, KeycloakSecurityContext context, String fullName) {
		this.name = name;
		this.context = context;
		if(fullName==null){
			if(context!=null && context.getIdToken()!=null){
				this.fullName = context.getIdToken().getGivenName() + " " + context.getIdToken().getGivenName();
			} else {
				this.fullName = name;
			}
		} else {
			this.fullName = fullName;
		}
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
	
	public String getFullName(){
		return fullName;
	}
	
	public String getRealm(){
		if(getContext()==null)
			return null;	
		return getContext().getRealm();
	}
}
