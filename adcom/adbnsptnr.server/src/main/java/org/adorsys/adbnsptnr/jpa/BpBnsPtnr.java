package org.adorsys.adbnsptnr.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.adorsys.adbase.jpa.BaseCountryName;
import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;

@Entity
@Description("BpBnsPtnr_description")
public class BpBnsPtnr extends CoreAbstIdentifObject {

	private static final long serialVersionUID = -9038535636658903372L;

	@Column
	@Description("BpBnsPtnr_ptnrNbr_description")
	@NotNull
	private String ptnrNbr;

	@Column
	@Description("BpBnsPtnr_ptnrType_description")
	@Enumerated(EnumType.STRING)
	@NotNull
	private BpPtnrType ptnrType;

	@Column
	@Description("BpBnsPtnr_ctryOfRsdnc_description")
	private String ctryOfRsdnc;
	
	@Transient
	private BaseCountryName countryName;
	
	@Column
	@Description("BpBnsPtnr_fullName_description")
	private String fullName;
	
	@Transient
	private BpIndivPtnrName indivPtnrName;
	
	@Transient
	private BpLegalPtnrId legalPtnrId;

	public String getPtnrNbr() {
		return this.ptnrNbr;
	}

	public void setPtnrNbr(final String ptnrNbr) {
		this.ptnrNbr = ptnrNbr;
	}

	public BpPtnrType getPtnrType() {
		return this.ptnrType;
	}

	public void setPtnrType(final BpPtnrType ptnrType) {
		this.ptnrType = ptnrType;
	}

	public String getCtryOfRsdnc() {
		return this.ctryOfRsdnc;
	}

	public void setCtryOfRsdnc(final String ctryOfRsdnc) {
		this.ctryOfRsdnc = ctryOfRsdnc;
	}

	public BaseCountryName getCountryName() {
		return countryName;
	}

	public void setCountryName(BaseCountryName countryName) {
		this.countryName = countryName;
	}

	@Override
	protected String makeIdentif() {
		return ptnrNbr;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public BpIndivPtnrName getIndivPtnrName() {
		return indivPtnrName;
	}

	public void setIndivPtnrName(BpIndivPtnrName indivPtnrName) {
		this.indivPtnrName = indivPtnrName;
	}

	public BpLegalPtnrId getLegalPtnrId() {
		return legalPtnrId;
	}

	public void setLegalPtnrId(BpLegalPtnrId legalPtnrId) {
		this.legalPtnrId = legalPtnrId;
	}
	
}