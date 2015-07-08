package org.adorsys.adbase.jpa;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractMvmtData;
import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.format.DateFormatPattern;
import org.apache.commons.lang3.StringUtils;

@Entity 
@Table(name="BaseSecTermSession")
@Description("SecTermSession_description")
public class SecTermSession extends AbstractMvmtData {

	private static final long serialVersionUID = -8997465407110640284L;

	/* The the id of the terminal associated with this session. */
	@Column(unique=true)
	@Description("SecTermSession_termId_description")
	@NotNull
	private String termId;

	@Column(unique=true)
	@Description("SecTermSession_termName_description")
	@NotNull
	private String termName;
	
	@Column(unique=true)
	@Description("SecTermSession_termCredtl_description")
	@NotNull
	private String termCredtl;

	/* 
	 * The login name of the user currently using this terminal. The same user can
	 * not be on two terminals at a time. Two users can not use the same terminal at a 
	 * time.
	 */
	@Column(unique=true)
	@Description("SecTermSession_userSession_description")
	private String userSession;

	@Column(unique=true)
	@Description("SecTermSession_loginName_description")
	private String loginName;

	/*
	 * This used to identify the session when the user does not have a terminal session id
	 * and the user terminal is not identified by a private key but by a secure cookie.
	 * 
	 * */
	@Column
	@Description("SecTermSession_termKey_description")
	private String termKey;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Description("SecTermSession_created_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	@NotNull
	private Date created;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("SecTermSession_expires_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	@NotNull
	private Date expires;
	
	@Column
	@Description("SecTermSession_ipAddress_description")
	private String ipAddress;

	@Column
	@Description("SecTermSession_langIso2_description")
	private String langIso2;
	
	@PrePersist
	public void prePersist() {
		if(StringUtils.isBlank(getId()))
			setId(UUID.randomUUID().toString());
	}
	
	public String getTermCredtl() {
		return termCredtl;
	}

	public void setTermCredtl(String termCredtl) {
		this.termCredtl = termCredtl;
	}

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

	public String getTermId() {
		return termId;
	}

	public void setTermId(String termId) {
		this.termId = termId;
	}

	public String getTermName() {
		return termName;
	}

	public void setTermName(String termName) {
		this.termName = termName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUserSession() {
		return userSession;
	}

	public void setUserSession(String userSession) {
		this.userSession = userSession;
	}

	public String getTermKey() {
		return termKey;
	}

	public void setTermKey(String termKey) {
		this.termKey = termKey;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getLangIso2() {
		return langIso2;
	}

	public void setLangIso2(String langIso2) {
		this.langIso2 = langIso2;
	}
}