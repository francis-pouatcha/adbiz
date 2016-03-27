package org.adorsys.adkcloak.services.xls;

public class UserRealmRoles {
	private String realmId;
	private String username;
	private String roleName;
	private String roleRealmId;
	
	public String getRealmId() {
		return realmId;
	}
	public void setRealmId(String realmId) {
		this.realmId = realmId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleRealmId() {
		return roleRealmId;
	}
	public void setRoleRealmId(String roleRealmId) {
		this.roleRealmId = roleRealmId;
	}
}
