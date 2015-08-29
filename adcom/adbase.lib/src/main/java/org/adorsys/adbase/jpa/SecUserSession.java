package org.adorsys.adbase.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.annotation.DateFormatPattern;
import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstEntity;

@Entity 
@Table(name="BaseSecUserSession")
@Description("SecUserSession_description")
public class SecUserSession extends CoreAbstEntity {

	private static final long serialVersionUID = 2417607506198295117L;

	@Column
	@Description("SecUserSession_termSessionId_description")
	@NotNull
	private String termSessionId;
	
	/* The identifier of the login associated with this terminal. */
	@Description("SecUserSession_loginName_description")
	@NotNull
	private String loginName;

	@Column
	@Description("SecUserSession_ouId_description")
	@NotNull
	private String ouId;
	
	@Column
	@Description("SecUserSession_workspaceId_description")
	@NotNull
	private String workspaceId;

	@Column
	@Description("SecUserSession_langIso2_description")
	@NotNull
	private String langIso2="fr";
	
	@Temporal(TemporalType.TIMESTAMP)
	@Description("SecUserSession_created_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	@NotNull
	private Date created;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("SecUserSession_expires_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	@NotNull
	private Date expires;

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getExpires() {
		return expires;
	}

	public void setExpires(Date expires) {
		this.expires = expires;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getWorkspaceId() {
		return workspaceId;
	}

	public void setWorkspaceId(String workspaceId) {
		this.workspaceId = workspaceId;
	}

	public String getTermSessionId() {
		return termSessionId;
	}

	public void setTermSessionId(String termSessionId) {
		this.termSessionId = termSessionId;
	}

	public String getOuId() {
		return ouId;
	}

	public void setOuId(String ouId) {
		this.ouId = ouId;
	}

	public String getLangIso2() {
		return langIso2;
	}

	public void setLangIso2(String langIso2) {
		this.langIso2 = langIso2;
	}
}