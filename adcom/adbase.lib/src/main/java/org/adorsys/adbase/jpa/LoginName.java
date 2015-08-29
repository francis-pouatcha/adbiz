package org.adorsys.adbase.jpa;

import org.adorsys.adcore.jpa.CoreAbstIdentifObject;

public class LoginName  extends CoreAbstIdentifObject {
	private static final long serialVersionUID = -4873411590820885249L;

	private String loginName;

	private String email;

	private String fullName;

	private String ouIdentif;
	
	private String langIso2;

	
	public LoginName() {
	}

	public LoginName(Login entity) {
		loginName = entity.getLoginName();
		email = entity.getEmail();
		fullName = entity.getFullName();
		ouIdentif = entity.getOuIdentif();
		langIso2 = entity.getLangIso2();
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getLangIso2() {
		return langIso2;
	}

	public void setLangIso2(String langIso2) {
		this.langIso2 = langIso2;
	}
	
	public String getOuIdentif() {
		return ouIdentif;
	}

	public void setOuIdentif(String ouIdentif) {
		this.ouIdentif = ouIdentif;
	}

	public Login toLogin(){
		Login login = new Login();
		login.setLoginName(loginName);
		login.setEmail(email);
		login.setFullName(fullName);
		login.setOuIdentif(ouIdentif);
		login.setLangIso2(langIso2);
		return login;
	}

	@Override
	protected String makeIdentif() {
		return loginName;
	}
}
