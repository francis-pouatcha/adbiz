package org.adorsys.adaptmt.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractTimedData;

@Entity
public class AptAptmtBsPtnrPresent extends AbstractTimedData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column
	@NotNull
	private String aptmtIdentify;
	
	@Column
	@NotNull
	private String contactnNbr;

	public String getAptmtIdentify() {
		return aptmtIdentify;
	}

	public void setAptmtIdentify(String aptmtIdentify) {
		this.aptmtIdentify = aptmtIdentify;
	}

	public String getContactnNbr() {
		return contactnNbr;
	}

	public void setContactnNbr(String contactnNbr) {
		this.contactnNbr = contactnNbr;
	}

	@Override
	public String toString() {
		return "AptAptmtBsPtnrPresent [aptmtIdentify=" + aptmtIdentify
				+ ", contactnNbr=" + contactnNbr + "]";
	}

	@Override
	protected String makeIdentif() {
		return aptmtIdentify + "_" + contactnNbr;
	}
	
	

}
