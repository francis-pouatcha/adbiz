package org.adorsys.adcore.jpa;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;

@MappedSuperclass
public abstract class CoreAbstIdentifHstry extends CoreAbstEntity {

	private static final long serialVersionUID = -7391189914830675838L;

	@Column
	@NotNull
	private String cntnrIdentif;

	@Column
	@NotNull
	private String entIdentif;
		
	@Column
	private String entStatus;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date hstryDt;

	@Column
	@NotNull
	private String orignLogin;

	@Column
	@NotNull
	private String orignWrkspc;

	@Column
	@NotNull
	private String procStep;

	@Column
	@NotNull
	private String hstryType;

	@Column
	@Size(max = 256)
	private String comment;

	@Column
	@Size(max = 256)
	private String addtnlInfo;
	
	@PrePersist
	public void prePersist(){
		if(StringUtils.isBlank(getId()))
			setId(UUID.randomUUID().toString());
	}

	public String getEntIdentif() {
		return entIdentif;
	}

	public void setEntIdentif(String entIdentif) {
		this.entIdentif = entIdentif;
	}

	public String getEntStatus() {
		return entStatus;
	}

	public void setEntStatus(String entStatus) {
		this.entStatus = entStatus;
	}

	public Date getHstryDt() {
		return this.hstryDt;
	}

	public void setHstryDt(final Date hstryDt) {
		this.hstryDt = hstryDt;
	}

	public String getOrignLogin() {
		return this.orignLogin;
	}

	public void setOrignLogin(final String orignLogin) {
		this.orignLogin = orignLogin;
	}

	public String getOrignWrkspc() {
		return this.orignWrkspc;
	}

	public void setOrignWrkspc(final String orignWrkspc) {
		this.orignWrkspc = orignWrkspc;
	}

	public String getProcStep() {
		return this.procStep;
	}

	public void setProcStep(final String procStep) {
		this.procStep = procStep;
	}

	public String getHstryType() {
		return this.hstryType;
	}

	public void setHstryType(final String hstryType) {
		this.hstryType = hstryType;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	public String getAddtnlInfo() {
		return this.addtnlInfo;
	}

	public void setAddtnlInfo(final String addtnlInfo) {
		this.addtnlInfo = addtnlInfo;
	}

	public String getCntnrIdentif() {
		return cntnrIdentif;
	}

	public void setCntnrIdentif(String cntnrIdentif) {
		this.cntnrIdentif = cntnrIdentif;
	}

}