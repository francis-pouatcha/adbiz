/**
 * 
 */
package org.adorsys.adcshdwr.jpa;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.CoreAbstIdentifData;
import org.adorsys.adcore.utils.BigDecimalUtils;
import org.adorsys.adcore.utils.FinancialOps;
import org.adorsys.javaext.description.Description;
import org.apache.commons.lang3.StringUtils;

/**
 * @author boriswaguia
 *
 */

@MappedSuperclass
@Description("CdrPymntItem_description")
public abstract class CdrAbstractPymntItem extends CoreAbstIdentifData{
	private static final long serialVersionUID = 6766990594179244006L;

	@Column
	@Description("CdrPymntItem_pymntNbr_description")
	@NotNull
	private String pymntNbr;

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

	public String getPymntNbr() {
		return this.pymntNbr;
	}

	public void setPymntNbr(final String pymntNbr) {
		this.pymntNbr = pymntNbr;
	}

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
		return pymntNbr + "_ " + pymntMode.name() + "_" + pymntDocType + "_" + pymntDocNbr;
	}

	/**
	 * contentEquals.
	 *
	 * @param persDi
	 * @return
	 */
	public boolean contentEquals(CdrAbstractPymntItem target) {
		if(!StringUtils.equals(target.pymntNbr,pymntNbr)) return false;
		if(target.pymntMode != pymntMode) return false;
		if(!StringUtils.equals(target.pymntDocType,pymntDocType)) return false;
		if(!StringUtils.equals(target.pymntDocNbr,pymntDocNbr)) return false;
		if(!BigDecimalUtils.numericEquals(target.amt,amt)) return false;
		if(!BigDecimalUtils.numericEquals(target.rcvdAmt,rcvdAmt)) return false;
		if(!BigDecimalUtils.numericEquals(target.diffAmt,diffAmt)) return false;
		return true;
	}

	/**
	 * copyTo.
	 *
	 * @param target
	 */
	public void copyTo(CdrAbstractPymntItem target) {
		target.pymntNbr=pymntNbr;
		target.pymntDt = pymntDt;
		target.pymntMode=pymntMode;
		target.pymntDocType=pymntDocType;
		target.pymntDocNbr=pymntDocNbr;
		target.amt=amt;
		target.rcvdAmt=rcvdAmt;
		target.diffAmt=diffAmt;
	}

	/**
	 * evlte.
	 *
	 */
	public void evlte() {
		if(this.amt == null) amt = BigDecimal.ZERO;
		if(this.rcvdAmt == null) rcvdAmt = BigDecimal.ZERO;
		if(this.diffAmt == null) diffAmt = BigDecimal.ZERO;
		this.diffAmt = FinancialOps.substract(rcvdAmt, amt);
	}
	
	public CdrPymntObject toPymntObject() {
		CdrPymntObject pymntObject = new CdrPymntObject();
		pymntObject.setAmt(amt);
		pymntObject.setOrigDocNbr(pymntDocNbr);
		pymntObject.setOrigItemNbr(getIdentif());
		pymntObject.setOrigDocType(CdrPymntItemInfo.getDocType());
		pymntObject.setPymntNbr(pymntNbr);
		return pymntObject;
	}
}
