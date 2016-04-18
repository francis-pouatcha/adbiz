package org.adorsys.adcshdwr.jpa;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;
import org.adorsys.adcore.utils.BigDecimalUtils;

/**
 * pymntNbr is the identifier.
 * 
 * @author fpo
 *
 */
@Description("CdrPymnt_description")
@MappedSuperclass
public abstract class CdrAbstPymnt extends CoreAbstIdentifObject {
	private static final long serialVersionUID = -8018401728362538355L;

	@Column
	@Description("CdrPymnt_invNbr_description")
	@NotNull
	private String invNbr;

	@Column
	@Description("CdrPymnt_amt_description")
	private BigDecimal amt;

	@Column
	@Description("CdrPymnt_cashier_description")
	@NotNull
	private String cashier;

	@Column
	@Description("CdrPymnt_cdrNbr_description")
	private String cdrNbr;

	@Column
	@Description("CdrPymnt_paidBy_description")
	private String paidBy;

	@Column
	@Description("CdrPymnt_rcptNbr_description")
	private String rcptNbr;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("CdrPymnt_rcptPrntDt_description")
	private Date rcptPrntDt;

	public BigDecimal getAmt() {
		return this.amt;
	}

	public void setAmt(final BigDecimal amt) {
		this.amt = amt;
	}

	public String getCashier() {
		return this.cashier;
	}

	public void setCashier(final String cashier) {
		this.cashier = cashier;
	}

	public String getInvNbr() {
		return invNbr;
	}

	public void setInvNbr(String invNbr) {
		this.invNbr = invNbr;
	}

	public String getCdrNbr() {
		return this.cdrNbr;
	}

	public void setCdrNbr(final String cdrNbr) {
		this.cdrNbr = cdrNbr;
	}

	public String getPaidBy() {
		return this.paidBy;
	}

	public void setPaidBy(final String paidBy) {
		this.paidBy = paidBy;
	}

	public String getRcptNbr() {
		return this.rcptNbr;
	}

	public void setRcptNbr(final String rcptNbr) {
		this.rcptNbr = rcptNbr;
	}

	public Date getRcptPrntDt() {
		return this.rcptPrntDt;
	}

	public void setRcptPrntDt(final Date rcptPrntDt) {
		this.rcptPrntDt = rcptPrntDt;
	}

	@Override
	protected String makeIdentif() {
		throw new IllegalStateException("Identifier is supposed to be set before persist.");
	}

	/**
	 * addAmnt.
	 *
	 * @param amt2
	 */
	public void addAmnt(BigDecimal amt2) {
		this.amt = BigDecimalUtils.sum(this.amt, amt2);
	}

	/**
	 * clearAmts.
	 *
	 */
	public void clearAmts() {
		amt = BigDecimal.ZERO;
	}
}
