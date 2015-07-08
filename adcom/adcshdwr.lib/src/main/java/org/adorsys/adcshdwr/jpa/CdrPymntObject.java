package org.adorsys.adcshdwr.jpa;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.CoreAbstIdentifData;
import org.adorsys.javaext.description.Description;

@Entity
@Description("CdrPymntObject_description")
public class CdrPymntObject extends CoreAbstIdentifData {

	private static final long serialVersionUID = 1L;

	@Column
	@Description("CdrPymntObject_pymntNbr_description")
	@NotNull
	private String pymntNbr;

	@Column
	@Description("CdrPymntObject_origDocType_description")
	@NotNull
	private String origDocType;

	@Column
	@Description("CdrPymntObject_origDocNbr_description")
	@NotNull
	private String origDocNbr;

	@Column
	@Description("CdrPymntObject_origItemNbr_description")
	@NotNull
	private String origItemNbr;

	@Column
	@Description("CdrPymntObject_amt_description")
	@NotNull
	private BigDecimal amt;

	@Column
	@Description("CdrPymntObject_orgUnit_description")
	@NotNull
	private String orgUnit;

	public String getPymntNbr() {
		return this.pymntNbr;
	}

	public void setPymntNbr(final String pymntNbr) {
		this.pymntNbr = pymntNbr;
	}

	public String getOrigDocType() {
		return this.origDocType;
	}

	public void setOrigDocType(final String origDocType) {
		this.origDocType = origDocType;
	}

	public String getOrigDocNbr() {
		return this.origDocNbr;
	}

	public void setOrigDocNbr(final String origDocNbr) {
		this.origDocNbr = origDocNbr;
	}

	public String getOrigItemNbr() {
		return this.origItemNbr;
	}

	public void setOrigItemNbr(final String origItemNbr) {
		this.origItemNbr = origItemNbr;
	}

	public BigDecimal getAmt() {
		return this.amt;
	}

	public void setAmt(final BigDecimal amt) {
		this.amt = amt;
	}

	public String getOrgUnit() {
		return this.orgUnit;
	}

	public void setOrgUnit(final String orgUnit) {
		this.orgUnit = orgUnit;
	}

	@Override
	protected String makeIdentif() {
		return pymntNbr + "_" + origDocType + "_" + origDocNbr;
	}

	/**
	 * copyPi.
	 *
	 * @param persPi
	 */
	public void copyTo(CdrPymntObject target) {
		target.pymntNbr=pymntNbr;
		target.origDocType=origDocType;
		target.origDocNbr=origDocNbr;
		target.origItemNbr=origItemNbr;
		target.amt=amt;
		target.orgUnit=orgUnit;
	}
}