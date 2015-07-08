package org.adorsys.adbase.dto;


public class LoginWorkspaceDto {
	
	private String loginName;
	private String userWsIdentif;
	
	private String ouWsIdentif;
	
	private String wsIdentif;
	
	private String ouIdentif;
	
	private String roleIdentif;
	
	private String ouTypeName;

	private String clientApp;
	
	private String restriction;
	
	private Boolean assigned = false;

	public String getUserWsIdentif() {
		return userWsIdentif;
	}

	public void setUserWsIdentif(String userWsIdentif) {
		this.userWsIdentif = userWsIdentif;
	}

	public String getOuWsIdentif() {
		return ouWsIdentif;
	}

	public void setOuWsIdentif(String ouWsIdentif) {
		this.ouWsIdentif = ouWsIdentif;
	}

	public String getWsIdentif() {
		return wsIdentif;
	}

	public void setWsIdentif(String wsIdentif) {
		this.wsIdentif = wsIdentif;
	}

	public String getOuIdentif() {
		return ouIdentif;
	}

	public void setOuIdentif(String ouIdentif) {
		this.ouIdentif = ouIdentif;
	}

	public String getRoleIdentif() {
		return roleIdentif;
	}

	public void setRoleIdentif(String roleIdentif) {
		this.roleIdentif = roleIdentif;
	}

	public String getOuTypeName() {
		return ouTypeName;
	}

	public void setOuTypeName(String ouTypeName) {
		this.ouTypeName = ouTypeName;
	}

	public String getClientApp() {
		return clientApp;
	}

	public void setClientApp(String clientApp) {
		this.clientApp = clientApp;
	}

	public String getRestriction() {
		return restriction;
	}

	public void setRestriction(String restriction) {
		this.restriction = restriction;
	}

	public Boolean getAssigned() {
		return assigned;
	}

	public void setAssigned(Boolean assigned) {
		this.assigned = assigned;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

}
