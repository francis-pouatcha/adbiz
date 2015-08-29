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

import org.adorsys.adcore.annotation.DateFormatPattern;
import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstEntity;
import org.apache.commons.lang3.StringUtils;

@Entity 
@Table(name="BaseSecTermCredtl")
@Description("SecTermCredtl_description")
public class SecTermCredtl extends CoreAbstEntity {

	private static final long serialVersionUID = -8997465407110640284L;

	/* The the id of the terminal associated with this credential. */
	@Column(unique=true)
	@Description("SecTermCredtl_termId_description")
	@NotNull
	private String termId;

	@Column(unique=true)
	@Description("SecTermCredtl_termName_description")
	@NotNull
	private String termName;
	
	@Column(unique=true)
	@Description("SecTermCredtl_termSessionId_description")
	private String termSessionId;

	/*
	 * This used to identify the session when the user does not have a terminal session id
	 * and the user terminal is not identified by a private key but by a secure cookie.
	 * 
	 * */
	@Column
	@Description("SecTermCredtl_termKey_description")
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
	@Description("SecTermSession_langIso2_description")
	private String langIso2;

	@PrePersist
	public void prePersist() {
		if(StringUtils.isBlank(getId()))
			setId(UUID.randomUUID().toString());
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

	public String getTermKey() {
		return termKey;
	}

	public void setTermKey(String termKey) {
		this.termKey = termKey;
	}

	public String getTermSessionId() {
		return termSessionId;
	}

	public void setTermSessionId(String termSessionId) {
		this.termSessionId = termSessionId;
	}

	public String getLangIso2() {
		return langIso2;
	}

	public void setLangIso2(String langIso2) {
		this.langIso2 = langIso2;
	}
}