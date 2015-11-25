package org.adorsys.adbnsptnr.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;

@Entity
@Description("BpCtgryOfPtnr_description")
public class BpCtgryOfPtnr extends CoreAbstIdentifObject {

	private static final long serialVersionUID = -2320937065216321253L;

	@Column
	@Description("BpCtgryOfPtnr_ptnrNbr_description")
	@NotNull
	private String ptnrNbr;

	@Column
	@Description("BpCtgryOfPtnr_ctgryCode_description")
	@NotNull
	private String ctgryCode;

	@Column
	@Description("BpCtgryOfPtnr_whenInRole_description")
	@Enumerated(EnumType.STRING)
	private BpPtnrRole whenInRole;

	@Column
	@Description("BpCtgryOfPtnr_fullName_description")
	private String fullName;

	@Column
	@Description("BpCtgryOfPtnr_nbrInCtgry_description")
	private String nbrInCtgry;

	@Column
	@Description("BpCtgryOfPtnr_titleInCtgry_description")
	private String titleInCtgry;

	@Column
	@Description("BpCtgryOfPtnr_description_description")
	private String description;
	
	@Transient
	private BpPtnrCtgryDtls ctgryDtls;
	
	public String getPtnrNbr() {
		return this.ptnrNbr;
	}

	public void setPtnrNbr(final String ptnrNbr) {
		this.ptnrNbr = ptnrNbr;
	}

	public String getCtgryCode() {
		return this.ctgryCode;
	}

	public void setCtgryCode(final String ctgryCode) {
		this.ctgryCode = ctgryCode;
	}

	public BpPtnrRole getWhenInRole() {
		return this.whenInRole;
	}

	public void setWhenInRole(final BpPtnrRole whenInRole) {
		this.whenInRole = whenInRole;
	}

	@Override
	protected String makeIdentif() {
		return ptnrNbr +"_"+ctgryCode;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getNbrInCtgry() {
		return nbrInCtgry;
	}

	public void setNbrInCtgry(String nbrInCtgry) {
		this.nbrInCtgry = nbrInCtgry;
	}

	public String getTitleInCtgry() {
		return titleInCtgry;
	}

	public void setTitleInCtgry(String titleInCtgry) {
		this.titleInCtgry = titleInCtgry;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BpPtnrCtgryDtls getCtgryDtls() {
		return ctgryDtls;
	}

	public void setCtgryDtls(BpPtnrCtgryDtls ctgryDtls) {
		this.ctgryDtls = ctgryDtls;
	}
}