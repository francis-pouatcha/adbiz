package org.adorsys.adbase.security;

import java.security.Principal;
import java.util.Date;

public class TermWsUserPrincipal implements Principal {

	private String termSessionId;
	
	private String userSessionId;
	
	private String workspaceId;
	
	private Date loginTime;

	private String termName;

	private String accessTime;

	private String timeZone;

	private String locality;

	private String macAddress;
	
	private String termCred;
	
	private String loginName;
	
	private String langIso2 = "fr";// default

	@Override
	public String getName() {
		return termSessionId+":"+userSessionId;
	}

	public String getTermName() {
		return termName;
	}

	public String getAccessTime() {
		return accessTime;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public String getLocality() {
		return locality;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setTermName(String termName) {
		this.termName = termName;
	}

	public void setAccessTime(String accessTime) {
		this.accessTime = accessTime;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getTermSessionId() {
		return termSessionId;
	}

	public void setTermSessionId(String termSessionId) {
		this.termSessionId = termSessionId;
	}

	public String getUserSessionId() {
		return userSessionId;
	}

	public void setUserSessionId(String userSessionId) {
		this.userSessionId = userSessionId;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getTermCred() {
		return termCred;
	}

	public void setTermCred(String termCred) {
		this.termCred = termCred;
	}

	public String getWorkspaceId() {
		return workspaceId;
	}

	public void setWorkspaceId(String workspaceId) {
		this.workspaceId = workspaceId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLangIso2() {
		return langIso2;
	}

	public void setLangIso2(String langIso2) {
		this.langIso2 = langIso2;
	}
	
}
