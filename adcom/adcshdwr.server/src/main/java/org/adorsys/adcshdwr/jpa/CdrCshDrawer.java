package org.adorsys.adcshdwr.jpa;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractMvmtData;
import org.adorsys.javaext.description.Description;

@Entity
@Description("CdrCshDrawer_description")
public class CdrCshDrawer extends AbstractMvmtData {

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
//	@NotNull
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
	private BigDecimal initialAmt;

	@Column
	@Description("CdrCshDrawer_ttlCashIn_description")
	private BigDecimal ttlCashIn;

	@Column
	@Description("CdrCshDrawer_ttlCashOut_description")
	private BigDecimal ttlCashOut;

	@Column
	@Description("CdrCshDrawer_ttlCash_description")
	private BigDecimal ttlCash;

	@Column
	@Description("CdrCshDrawer_ttlCheck_description")
	private BigDecimal ttlCheck;

	@Column
	@Description("CdrCshDrawer_ttlCredCard_description")
	private BigDecimal ttlCredCard;

	@Column
	@Description("CdrCshDrawer_ttlVchrIn_description")
	private BigDecimal ttlVchrIn;

	@Column
	@Description("CdrCshDrawer_ttlVchrOut_description")
	private BigDecimal ttlVchrOut;
	
	
	public void evlte() {
		if(this.ttlCashIn==null)this.ttlCashIn=BigDecimal.ZERO;
		if(this.initialAmt==null)this.initialAmt=BigDecimal.ZERO;
		if(this.ttlCash==null)this.ttlCash=BigDecimal.ZERO;
		if(this.ttlCheck==null)this.ttlCheck=BigDecimal.ZERO;
		if(this.ttlCredCard==null)this.ttlCredCard=BigDecimal.ZERO;
		if(this.ttlVchrIn==null)this.ttlVchrIn=BigDecimal.ZERO;
		this.ttlCashIn = this.initialAmt.add(this.ttlCash).add(this.ttlCheck)
				.add(this.ttlCredCard).add(ttlVchrIn);		
	}

	public void AddTtlCashOut(BigDecimal cashOut){
		if(this.ttlCashOut==null)this.ttlCashOut =BigDecimal.ZERO;
		this.ttlCashOut = this.ttlCashOut.add(cashOut);
	}
	public void AddTtlCash(BigDecimal cashAmount){
		if(this.ttlCash==null)this.ttlCash =BigDecimal.ZERO;
		this.ttlCash = this.ttlCash.add(cashAmount);
	}
	public void AddTtlCheck(BigDecimal checkAmount){
		if(this.ttlCheck==null)this.ttlCheck =BigDecimal.ZERO;
		this.ttlCheck = this.ttlCheck.add(checkAmount);
	}
	public void AddTtlCredCard(BigDecimal credcardAmount){
		if(this.ttlCredCard==null)this.ttlCredCard =BigDecimal.ZERO;
		this.ttlCredCard = this.ttlCredCard.add(credcardAmount);
	}
	public void AddTtlVchrIn(BigDecimal vchrInAmount){
		if(this.ttlVchrIn==null)this.ttlVchrIn =BigDecimal.ZERO;
		this.ttlVchrIn = this.ttlVchrIn.add(vchrInAmount);
	}
	public void AddTtlVchrOut(BigDecimal vchrOutAmount){
		if(this.ttlVchrOut==null)this.ttlVchrOut =BigDecimal.ZERO;
		this.ttlVchrOut = this.ttlVchrOut.add(vchrOutAmount);
	}

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
}