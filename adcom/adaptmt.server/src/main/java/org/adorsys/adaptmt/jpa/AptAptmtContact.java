package org.adorsys.adaptmt.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.adorsys.adcore.jpa.AbstractTimedData;
import org.adorsys.javaext.description.Description;



@Entity
public class AptAptmtContact extends AbstractTimedData {

	
	private static final long serialVersionUID = 1234567898745632105L;

	@Column
	private String contactnNbr;
	
	@Column
	private String fullname;
	
	@Column
	private String bnsptntIdentify;
	
	@Column
	private String email;
	
	@Column
	private String personalMobile;
	
	@Column
	private String professionalMobile;
	
	@Column
	private String title;
	
	@Column
	private String loginIdentify;
	
	
	public String getLoginIdentify() {
		return loginIdentify;
	}

	public void setLoginIdentify(String loginIdentify) {
		this.loginIdentify = loginIdentify;
	}

	public String getContactnNbr() {
		return contactnNbr;
	}

	public void setContactnNbr(String contactnNbr) {
		this.contactnNbr = contactnNbr;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getBnsptntIdentify() {
		return bnsptntIdentify;
	}

	public void setBnsptntIdentify(final String bnsptntIdentify) {
		this.bnsptntIdentify = bnsptntIdentify;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPersonalMobile() {
		return personalMobile;
	}

	public void setPersonalMobile(String personalMobile) {
		this.personalMobile = personalMobile;
	}

	public String getProfessionalMobile() {
		return professionalMobile;
	}

	public void setProfessionalMobile(String professionalMobile) {
		this.professionalMobile = professionalMobile;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
	
	@Override
	public String toString() {
		return "AptAptmtContact [contactnNbr=" + contactnNbr + ", fullname="
				+ fullname + ", bnsptntIdentify=" + bnsptntIdentify
				+ ", email=" + email + ", personalMobile=" + personalMobile
				+ ", professionalMobile=" + professionalMobile + ", title="
				+ title + "]";
	}

	@Override
	protected String makeIdentif() {
		// TODO Auto-generated method stub
		return null;
	}

}
