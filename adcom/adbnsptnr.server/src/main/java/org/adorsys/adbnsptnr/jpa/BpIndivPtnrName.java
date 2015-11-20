package org.adorsys.adbnsptnr.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;
import org.adorsys.adcore.utils.CalendarUtil;
import org.apache.commons.lang3.StringUtils;

@Entity
@Description("BpIndivPtnrName_description")
public class BpIndivPtnrName extends CoreAbstIdentifObject {

	private static final long serialVersionUID = -7316060758866987627L;

	@Column
	@Description("BpIndivPtnrName_ptnrNbr_description")
	@NotNull
	private String ptnrNbr;

	@Column
	@Description("BpIndivPtnrName_gender_description")
	private String gender;

	@Column
	@Description("BpIndivPtnrName_firstName_description")
	private String firstName;

	@Column
	@Description("BpIndivPtnrName_lastName_description")
	@NotNull(message = "BpIndivPtnrName_lastName_NotNull_validation")
	private String lastName;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("BpIndivPtnrName_brthDt_description")
	private Date brthDt;

	public String getPtnrNbr() {
		return this.ptnrNbr;
	}

	public void setPtnrNbr(final String ptnrNbr) {
		this.ptnrNbr = ptnrNbr;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(final String gender) {
		this.gender = gender;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public Date getBrthDt() {
		return this.brthDt;
	}

	public void setBrthDt(final Date brthDt) {
		this.brthDt = brthDt;
	}

	@Override
	protected String makeIdentif() {
		return ptnrNbr;
	}
	
	@PrePersist
	public void prePersist(){
		makeFullName();
		super.prePersist();
	}
	
	@PreUpdate
	public void preUpdate(){
		makeFullName();
	} 
	
	public String makeFullName(){
		return StringUtils.isBlank(firstName)?lastName:firstName + " " + lastName;
	}
	
	public boolean updateContent(BpIndivPtnrName source){
		boolean changed = false;
		if(!StringUtils.equals(gender, source.gender)){
			gender = source.gender;
			changed = true;
		}
		if(!StringUtils.equals(firstName, source.firstName)){
			firstName = source.firstName;
			changed = true;
		}
		if(!StringUtils.equals(lastName, source.lastName)){
			lastName = source.lastName;
			changed = true;
		}
		if(!CalendarUtil.isSameDay(brthDt, source.brthDt)){
			brthDt = source.brthDt;
			changed = true;
		}
		return changed;
	}
}