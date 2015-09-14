package org.adorsys.adbase.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.adorsys.adbase.auth.PasswordChecker;
import org.adorsys.adcore.annotation.DateFormatPattern;
import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;
import org.apache.commons.lang3.StringUtils;

@Entity 
@Table(name="BaseLogin")
@Description("Login_description")
public class Login extends CoreAbstIdentifObject {

	private static final long serialVersionUID = 5322058832893286944L;

	@Column
	@Description("Login_ouIdentif_description")
	@NotNull
	private String ouIdentif;

	@Column(unique=true)
	@Description("Login_loginName_description")
	@NotNull
	private String loginName;

	@Column(unique=true)
	@Description("Login_loginAlias_description")
	@NotNull
	private String loginAlias;

	@Column
	@Description("Login_email_description")
	private String email;

	@Column
	@Description("Login_fullName_description")
	@NotNull
	private String fullName;

	@Column
	@Description("Login_pwdHashed_description")
	@NotNull
	private String pwdHashed;

	@Column
	@Description("Login_disableLogin_description")
	private Boolean disableLogin = false;

	@Column
	@Description("Login_accountLocked_description")
	private Boolean accountLocked = false;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("Login_credtlExpir_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private Date credtlExpir;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("Login_accountExpir_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private Date accountExpir;
	
	@Column
	@Description("Login_langIso2_description")
	@NotNull
	private String langIso2="fr";

	@PrePersist
	public void prePersist() {
		if (StringUtils.isBlank(getId()))
			setId(loginName);
		if(StringUtils.isBlank(loginAlias))
			loginAlias = loginName;
	}	
	public String getOuIdentif() {
		return this.ouIdentif;
	}

	public void setOuIdentif(final String ouIdentif) {
		this.ouIdentif = ouIdentif;
	}

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(final String loginName) {
		this.loginName = loginName;
	}

	public String getLoginAlias() {
		return this.loginAlias;
	}

	public void setLoginAlias(final String loginAlias) {
		this.loginAlias = loginAlias;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(final String fullName) {
		this.fullName = fullName;
	}

	public String getPwdHashed() {
		return this.pwdHashed;
	}

	public void setPwdHashed(final String pwdHashed) {
		if(StringUtils.isNotBlank(pwdHashed))
			this.pwdHashed = PasswordChecker.encodePassword(pwdHashed);
	}

	public Boolean getDisableLogin() {
		return this.disableLogin;
	}

	public void setDisableLogin(final Boolean disableLogin) {
		this.disableLogin = disableLogin;
	}

	public Boolean getAccountLocked() {
		return this.accountLocked;
	}

	public void setAccountLocked(final Boolean accountLocked) {
		this.accountLocked = accountLocked;
	}

	public Date getCredtlExpir() {
		return this.credtlExpir;
	}

	public void setCredtlExpir(final Date credtlExpir) {
		this.credtlExpir = credtlExpir;
	}

	public Date getAccountExpir() {
		return this.accountExpir;
	}

	public void setAccountExpir(final Date accountExpir) {
		this.accountExpir = accountExpir;
	}

	@Override
	protected String makeIdentif() {
		return loginName;
	}
	
	public String getLangIso2() {
		return langIso2;
	}
	public void setLangIso2(String langIso2) {
		this.langIso2 = langIso2;
	}
	public String toLoginWorkspace(){
		return ouIdentif+"_login_"+ouIdentif+"_"+loginName;
	}
	
	public static boolean loginWorkspace(String wsid){
		return StringUtils.isNotBlank(wsid) && wsid.contains("_login_");
	}
	
}