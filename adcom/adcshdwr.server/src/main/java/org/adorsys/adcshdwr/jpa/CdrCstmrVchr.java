package org.adorsys.adcshdwr.jpa;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.CoreAbstIdentifData;
import org.adorsys.javaext.description.Description;

@Entity
@Description("CdrCstmrVchr_description")
public class CdrCstmrVchr extends CoreAbstIdentifData {

	private static final long serialVersionUID = -2114572675147032200L;

	@Column
	@Description("CdrCstmrVchr_vchrNbr_description")
	@NotNull
	private String vchrNbr;

	@Column
	@Description("CdrCstmrVchr_dsNbr_description")
	@NotNull
	private String dsNbr;

	@Column
	@Description("CdrCstmrVchr_amt_description")
	@NotNull
	private BigDecimal amt;

	@Column
	@Description("CdrCstmrVchr_cstmrNbr_description")
	private String cstmrNbr;

	@Column
	@Description("CdrCstmrVchr_cstmrName_description")
	private String cstmrName;

	@Column
	@Description("CdrCstmrVchr_canceled_description")
	private Boolean canceled;

	@Column
	@Description("CdrCstmrVchr_cashier_description")
	@NotNull
	private String cashier;

	@Column
	@Description("CdrCstmrVchr_cdrNbr_description")
	private String cdrNbr;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("CdrCstmrVchr_prntDt_description")
	private Date prntDt;

	@Column
	@Description("CdrCstmrVchr_amtUsed_description")
	private BigDecimal amtUsed;

	@Column
	@Description("CdrCstmrVchr_settled_description")
	private Boolean settled;

	@Column
	@Description("CdrCstmrVchr_restAmt_description")
	private BigDecimal restAmt;
	
	public void AddAmtUsed(BigDecimal amtUsed){
		if(this.amtUsed==null)this.amtUsed =BigDecimal.ZERO;
		this.amtUsed = this.amtUsed.add(amtUsed);
	}
	
	public void evlte(){
		if(this.amt==null)this.amt=BigDecimal.ZERO;
		if(this.amtUsed==null)this.amtUsed=BigDecimal.ZERO;
		if(this.restAmt==null)this.restAmt=BigDecimal.ZERO;
		this.restAmt = this.amt.add(this.amtUsed.negate());
		if(this.restAmt.compareTo(BigDecimal.ZERO) == 0)
			this.settled = true;
		else
			this.settled = false;
	}

	public String getVchrNbr() {
		return this.vchrNbr;
	}

	public void setVchrNbr(final String vchrNbr) {
		this.vchrNbr = vchrNbr;
	}

	public String getDsNbr() {
		return this.dsNbr;
	}

	public void setDsNbr(final String dsNbr) {
		this.dsNbr = dsNbr;
	}

	public BigDecimal getAmt() {
		return this.amt;
	}

	public void setAmt(final BigDecimal amt) {
		this.amt = amt;
	}

	public String getCstmrNbr() {
		return this.cstmrNbr;
	}

	public void setCstmrNbr(final String cstmrNbr) {
		this.cstmrNbr = cstmrNbr;
	}

	public String getCstmrName() {
		return this.cstmrName;
	}

	public void setCstmrName(final String cstmrName) {
		this.cstmrName = cstmrName;
	}

	public Boolean getCanceled() {
		return this.canceled;
	}

	public void setCanceled(final Boolean canceled) {
		this.canceled = canceled;
	}

	public String getCashier() {
		return this.cashier;
	}

	public void setCashier(final String cashier) {
		this.cashier = cashier;
	}

	public String getCdrNbr() {
		return this.cdrNbr;
	}

	public void setCdrNbr(final String cdrNbr) {
		this.cdrNbr = cdrNbr;
	}

	public Date getPrntDt() {
		return this.prntDt;
	}

	public void setPrntDt(final Date prntDt) {
		this.prntDt = prntDt;
	}

	public BigDecimal getAmtUsed() {
		return this.amtUsed;
	}

	public void setAmtUsed(final BigDecimal amtUsed) {
		this.amtUsed = amtUsed;
	}

	public Boolean getSettled() {
		return this.settled;
	}

	public void setSettled(final Boolean settled) {
		this.settled = settled;
	}

	public BigDecimal getRestAmt() {
		return this.restAmt;
	}

	public void setRestAmt(final BigDecimal restAmt) {
		this.restAmt = restAmt;
	}

	@Override
	protected String makeIdentif() {
		return vchrNbr;
	}
}