package org.adorsys.adcshdwr.jpa;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;
import org.adorsys.adcore.utils.BigDecimalUtils;

/**
 * pymntNbr is the container identifier
 * pymntItemNbr is the identifier
 * 
 * @author fpo
 *
 */
@MappedSuperclass
@Description("CdrPymntItem_description")
public abstract class CdrAbstPymntItem extends CoreAbstIdentifObject{
	private static final long serialVersionUID = 6766990594179244006L;

	@Column
	@Description("CdrPymntItem_pymntMode_description")
	@Enumerated
	@NotNull
	private CdrPymntMode pymntMode;

	@Column
	@Description("CdrPymntItem_pymntDocType_description")
	private String pymntDocType;

	@Column
	@Description("CdrPymntItem_pymntDocNbr_description")
	private String pymntDocNbr;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Description("CdrPymnt_pymntDt_description")
	@NotNull
	private Date pymntDt;

	@Column
	@Description("CdrPymntItem_amt_description")
	@NotNull
	private BigDecimal amt;

	@Column
	@Description("CdrPymntItem_rcvdAmt_description")
	private BigDecimal rcvdAmt;

	@Column
	@Description("CdrPymntItem_diffAmt_description")
	private BigDecimal diffAmt;

	@Column
	@Description("CdrPymntItem_orgUnit_description")
	@NotNull
	private String orgUnit;
	
	public CdrPymntMode getPymntMode() {
		return this.pymntMode;
	}

	public void setPymntMode(final CdrPymntMode pymntMode) {
		this.pymntMode = pymntMode;
	}

	public String getPymntDocType() {
		return this.pymntDocType;
	}

	public void setPymntDocType(final String pymntDocType) {
		this.pymntDocType = pymntDocType;
	}

	public String getPymntDocNbr() {
		return this.pymntDocNbr;
	}

	public void setPymntDocNbr(final String pymntDocNbr) {
		this.pymntDocNbr = pymntDocNbr;
	}

	public BigDecimal getAmt() {
		return this.amt;
	}

	public void setAmt(final BigDecimal amt) {
		this.amt = amt;
	}

	public Date getPymntDt() {
		return pymntDt;
	}

	public void setPymntDt(Date pymntDt) {
		this.pymntDt = pymntDt;
	}

	public BigDecimal getRcvdAmt() {
		return this.rcvdAmt;
	}

	public String getOrgUnit() {
		return orgUnit;
	}

	public void setOrgUnit(String orgUnit) {
		this.orgUnit = orgUnit;
	}

	public void setRcvdAmt(final BigDecimal rcvdAmt) {
		this.rcvdAmt = rcvdAmt;
	}

	public BigDecimal getDiffAmt() {
		return this.diffAmt;
	}

	public void setDiffAmt(final BigDecimal diffAmt) {
		this.diffAmt = diffAmt;
	}

	@Override
	public String makeIdentif() {
		throw new IllegalStateException("Identifier is supposed to be set before persist.");
	}

	/**
	 * evlte.
	 *
	 */
	public void evlte() {
		this.diffAmt = BigDecimalUtils.subs(rcvdAmt, amt);
	}
}
