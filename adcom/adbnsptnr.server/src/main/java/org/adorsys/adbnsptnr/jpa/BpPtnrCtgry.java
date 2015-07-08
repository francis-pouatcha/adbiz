package org.adorsys.adbnsptnr.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.CoreAbstIdentifData;
import org.adorsys.javaext.description.Description;

@Entity
@Description("BpPtnrCtgry_description")
public class BpPtnrCtgry extends CoreAbstIdentifData {

	private static final long serialVersionUID = -5239368389118117504L;

	@Column
	@Description("BpPtnrCtgry_ctgryCode_description")
	@NotNull
	private String ctgryCode;

	@Column
	@Description("BpPtnrCtgry_parentCode_description")
	private String parentCode;

	@Column
	@Description("BpPtnrCtgry_ptnrRole_description")
	@Enumerated(EnumType.STRING)
	private BpPtnrRole ptnrRole;
	
	@Transient
	private BpPtnrCtgryDtls ctgryDtls;

	@Transient
	private BpPtnrCtgryDtls parentCtgryDtls;
	
	public String getCtgryCode() {
		return this.ctgryCode;
	}

	public void setCtgryCode(final String ctgryCode) {
		this.ctgryCode = ctgryCode;
	}

	public String getParentCode() {
		return this.parentCode;
	}

	public void setParentCode(final String parentCode) {
		this.parentCode = parentCode;
	}

	public BpPtnrRole getPtnrRole() {
		return this.ptnrRole;
	}

	public void setPtnrRole(final BpPtnrRole ptnrRole) {
		this.ptnrRole = ptnrRole;
	}

	@Override
	protected String makeIdentif() {
		return ctgryCode;
	}

	public BpPtnrCtgryDtls getCtgryDtls() {
		return ctgryDtls;
	}

	public void setCtgryDtls(BpPtnrCtgryDtls ctgryDtls) {
		this.ctgryDtls = ctgryDtls;
	}

	public BpPtnrCtgryDtls getParentCtgryDtls() {
		return parentCtgryDtls;
	}

	public void setParentCtgryDtls(BpPtnrCtgryDtls parentCtgryDtls) {
		this.parentCtgryDtls = parentCtgryDtls;
	}

	public void copyTo(BpPtnrCtgry target) {
		target.ctgryCode=ctgryCode;
		target.ctgryDtls=ctgryDtls;
		target.identif=identif;
		target.parentCode=parentCode;
		target.parentCtgryDtls=parentCtgryDtls;
		target.ptnrRole=ptnrRole;
	}
}