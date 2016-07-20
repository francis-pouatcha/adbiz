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

/**
 * cdrNbr is the identifier.
 * 
 * @author fpo
 *
 */
@MappedSuperclass
@Description("CdrCshDrawer_description")
public abstract class CdrAbstCshDrawer extends CoreAbstIdentifObject {

	private static final long serialVersionUID = -5373320749182511372L;

	@Column
	@Description("CdrCshDrawer_cdrNbr_description")
	@NotNull
	private String cdrNbr;

	@Column
	@Description("CdrCshDrawer_cashier_description")
	@NotNull
	private String cashier;

	@Column
	@Description("CdrCshDrawer_closedBy_description")
	private String closedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("CdrCshDrawer_opngDt_description")
	@NotNull
	private Date opngDt;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("CdrCshDrawer_clsngDt_description")
	private Date clsngDt;

	@Column
	@Description("CdrCshDrawer_initialAmt_description")
	private BigDecimal initialAmt=BigDecimal.ZERO;;

	@Column
	@Description("CdrCshDrawer_ttlCashIn_description")
	private BigDecimal ttlCashIn=BigDecimal.ZERO;

	@Column
	@Description("CdrCshDrawer_ttlCashOut_description")
	private BigDecimal ttlCashOut=BigDecimal.ZERO;;

	@Column
	@Description("CdrCshDrawer_ttlCash_description")
	private BigDecimal ttlCash=BigDecimal.ZERO;;

	@Column
	@Description("CdrCshDrawer_ttlCheck_description")
	private BigDecimal ttlCheck=BigDecimal.ZERO;;

	@Column
	@Description("CdrCshDrawer_ttlCredCard_description")
	private BigDecimal ttlCredCard=BigDecimal.ZERO;;

	@Column
	@Description("CdrCshDrawer_ttlVchrIn_description")
	private BigDecimal ttlVchrIn=BigDecimal.ZERO;;

	@Column
	@Description("CdrCshDrawer_ttlVchrOut_description")
	private BigDecimal ttlVchrOut=BigDecimal.ZERO;;
	
	public String getCdrNbr() {
		return this.cdrNbr;
	}

	public void setCdrNbr(final String cdrNbr) {
		this.cdrNbr = cdrNbr;
	}

	public String getCashier() {
		return this.cashier;
	}

	public void setCashier(final String cashier) {
		this.cashier = cashier;
	}

	public String getClosedBy() {
		return this.closedBy;
	}

	public void setClosedBy(final String closedBy) {
		this.closedBy = closedBy;
	}

	public Date getOpngDt() {
		return this.opngDt;
	}

	public void setOpngDt(final Date opngDt) {
		this.opngDt = opngDt;
	}

	public Date getClsngDt() {
		return this.clsngDt;
	}

	public void setClsngDt(final Date clsngDt) {
		this.clsngDt = clsngDt;
	}

	public BigDecimal getInitialAmt() {
		return this.initialAmt;
	}

	public void setInitialAmt(final BigDecimal initialAmt) {
		this.initialAmt = initialAmt;
	}

	public BigDecimal getTtlCashIn() {
		return this.ttlCashIn;
	}

	public void setTtlCashIn(final BigDecimal ttlCashIn) {
		this.ttlCashIn = ttlCashIn;
	}

	public BigDecimal getTtlCashOut() {
		return this.ttlCashOut;
	}

	public void setTtlCashOut(final BigDecimal ttlCashOut) {
		this.ttlCashOut = ttlCashOut;
	}

	public BigDecimal getTtlCash() {
		return this.ttlCash;
	}

	public void setTtlCash(final BigDecimal ttlCash) {
		this.ttlCash = ttlCash;
	}

	public BigDecimal getTtlCheck() {
		return this.ttlCheck;
	}

	public void setTtlCheck(final BigDecimal ttlCheck) {
		this.ttlCheck = ttlCheck;
	}

	public BigDecimal getTtlCredCard() {
		return this.ttlCredCard;
	}

	public void setTtlCredCard(final BigDecimal ttlCredCard) {
		this.ttlCredCard = ttlCredCard;
	}

	public BigDecimal getTtlVchrIn() {
		return this.ttlVchrIn;
	}

	public void setTtlVchrIn(final BigDecimal ttlVchrIn) {
		this.ttlVchrIn = ttlVchrIn;
	}

	public BigDecimal getTtlVchrOut() {
		return this.ttlVchrOut;
	}

	public void setTtlVchrOut(final BigDecimal ttlVchrOut) {
		this.ttlVchrOut = ttlVchrOut;
	}

	@Override
	protected String makeIdentif() {
		throw new IllegalStateException("Identifier is supposed to be set before persist.");
	}
}