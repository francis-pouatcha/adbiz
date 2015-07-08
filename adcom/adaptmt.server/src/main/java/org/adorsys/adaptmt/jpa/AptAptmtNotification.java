package org.adorsys.adaptmt.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractTimedData;
import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.format.DateFormatPattern;

@Entity
@Description("AptAptmt_description")
public class AptAptmtNotification extends AbstractTimedData {
	private static final long serialVersionUID = 5118150729653002565L;
	
	@Column
	private String loginIdentify;
	
	@Column
	private String providerLogin;
	
	@Column
	private String title;
	
	@Column
	private String description;
	
	@Column
	private String state;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("AptAptmt_createDate_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private Date createDate;

	public String getLoginIdentify() {
		return loginIdentify;
	}
	
	public void setLoginIdentify(String loginIdentify) {
		this.loginIdentify = loginIdentify;
	}

	public String getProviderLogin() {
		return providerLogin;
	}
	
	public void setProviderLogin(String providerLogin) {
		this.providerLogin = providerLogin;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	

	@Override
	public String toString() {
		return "AptAptmtNotification [loginIdentify=" + loginIdentify
				+ ", providerLogin=" + providerLogin + ", title=" + title
				+ ", description=" + description + ", state=" + state
				+ ", createDate=" + createDate + "]";
	}
	
	public AptAptmtNotification markRead(){
		this.setState("SEEN");
		return this;	
	}
	
	






	@Override
	protected String makeIdentif() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
