package org.adorsys.adcore.auth;

public final class AdcomUser {

	private String name;

	public AdcomUser(String name) {
		this.name = name;
	}

	public String getLoginName(){
		return name;
	};
	
	public String getWorkspaceId(){
		return name;
	}
	
}
