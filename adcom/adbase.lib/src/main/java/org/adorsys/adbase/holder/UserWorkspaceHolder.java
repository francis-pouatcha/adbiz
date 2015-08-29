package org.adorsys.adbase.holder;

import java.io.Serializable;

public class UserWorkspaceHolder implements Serializable {

	private static final long serialVersionUID = 6777964216125446966L;

	private String loginName;
	
	private String roleIdentif;
	
	private String clientApp;
	
	private String ouTypes;
	
	private String targetOuIdentif;
	
	private String userFullName;
	
	private String email;
	
	private String terminalName;
	
	private String timeZone;
	
	private String langIso2;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getRoleIdentif() {
		return roleIdentif;
	}

	public void setRoleIdentif(String roleIdentif) {
		this.roleIdentif = roleIdentif;
	}

	public String getClientApp() {
		return clientApp;
	}

	public void setClientApp(String clientApp) {
		this.clientApp = clientApp;
	}

	public String getOuTypes() {
		return ouTypes;
	}

	public void setOuTypes(String ouTypes) {
		this.ouTypes = ouTypes;
	}

	public String getTargetOuIdentif() {
		return targetOuIdentif;
	}

	public void setTargetOuIdentif(String targetOuIdentif) {
		this.targetOuIdentif = targetOuIdentif;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTerminalName() {
		return terminalName;
	}

	public void setTerminalName(String terminalName) {
		this.terminalName = terminalName;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getLangIso2() {
		return langIso2;
	}

	public void setLangIso2(String langIso2) {
		this.langIso2 = langIso2;
	}
	
}
