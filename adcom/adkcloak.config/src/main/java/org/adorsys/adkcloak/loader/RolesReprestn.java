package org.adorsys.adkcloak.loader;


public class RolesReprestn {

	private String realmId;
	private String compositeRoleClientId;
	private String compositeRoleName;
	private String childRoleClientId;
	private String childRoleName;
	
	private String childRoleId;
	
	public String getRealmId() {
		return realmId;
	}
	public void setRealmId(String realmId) {
		this.realmId = realmId;
	}
	public String getCompositeRoleClientId() {
		return compositeRoleClientId;
	}
	public void setCompositeRoleClientId(String compositeRoleClientId) {
		this.compositeRoleClientId = compositeRoleClientId;
	}
	public String getCompositeRoleName() {
		return compositeRoleName;
	}
	public void setCompositeRoleName(String compositeRoleName) {
		this.compositeRoleName = compositeRoleName;
	}
	public String getChildRoleClientId() {
		return childRoleClientId;
	}
	public void setChildRoleClientId(String childRoleClientId) {
		this.childRoleClientId = childRoleClientId;
	}
	public String getChildRoleName() {
		return childRoleName;
	}
	public void setChildRoleName(String childRoleName) {
		this.childRoleName = childRoleName;
	}
	public String getChildRoleId() {
		return childRoleId;
	}
	public void setChildRoleId(String childRoleId) {
		this.childRoleId = childRoleId;
	}
}
