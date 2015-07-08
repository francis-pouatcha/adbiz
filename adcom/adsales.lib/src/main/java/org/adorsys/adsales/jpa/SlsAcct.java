package org.adorsys.adsales.jpa;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractMvmtData;
import org.adorsys.javaext.description.Description;

@Entity
@Description("SlsAcct_description")
public class SlsAcct extends AbstractMvmtData {

	private static final long serialVersionUID = 244147011549873343L;

	@Column
	@Description("SlsAcct_soAndInvceNbr_description")
	@NotNull
	private String soAndInvceNbr;

	@Column
	@Description("SlsAcct_invceNbr_description")
	private String invceNbr;

	@Column
	@Description("SlsAcct_soNbr_description")
	private String soNbr;

	@Column
	@Description("SlsAcct_ptnrNbr_description")
	@NotNull
	private String ptnrNbr;

	@Column
	@Description("SlsAcct_roleInInvce_description")
	@NotNull
	private String roleInInvce;

	@Column
	@Description("SlsAcct_sttlmtOp_description")
	@NotNull
	private String sttlmtOp;

	@Column
	@Description("SlsAcct_sttlmtOpHint_description")
	@NotNull
	private String sttlmtOpHint;

	@Column
	@Description("SlsAcct_amt_description")
	private BigDecimal amt;

	@Column
	@Description("SlsAcct_amtSide_description")
	@Enumerated
	@NotNull
	private SlsAmtSide amtSide;

	@Column
	@Description("SlsAcct_blnce_description")
	private BigDecimal blnce;

	@Column
	@Description("SlsAcct_blnceSide_description")
	@Enumerated
	@NotNull
	private SlsAmtSide blnceSide;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("SlsAcct_evntDt_description")
	@NotNull
	private Date evntDt;

	public String getSoAndInvceNbr() {
		return this.soAndInvceNbr;
	}

	public void setSoAndInvceNbr(final String soAndInvceNbr) {
		this.soAndInvceNbr = soAndInvceNbr;
	}

	public String getInvceNbr() {
		return this.invceNbr;
	}

	public void setInvceNbr(final String invceNbr) {
		this.invceNbr = invceNbr;
	}

	public String getSoNbr() {
		return this.soNbr;
	}

	public void setSoNbr(final String soNbr) {
		this.soNbr = soNbr;
	}

	public String getPtnrNbr() {
		return this.ptnrNbr;
	}

	public void setPtnrNbr(final String ptnrNbr) {
		this.ptnrNbr = ptnrNbr;
	}

	public String getRoleInInvce() {
		return this.roleInInvce;
	}

	public void setRoleInInvce(final String roleInInvce) {
		this.roleInInvce = roleInInvce;
	}

	public String getSttlmtOp() {
		return this.sttlmtOp;
	}

	public void setSttlmtOp(final String sttlmtOp) {
		this.sttlmtOp = sttlmtOp;
	}

	public String getSttlmtOpHint() {
		return this.sttlmtOpHint;
	}

	public void setSttlmtOpHint(final String sttlmtOpHint) {
		this.sttlmtOpHint = sttlmtOpHint;
	}

	public BigDecimal getAmt() {
		return this.amt;
	}

	public void setAmt(final BigDecimal amt) {
		this.amt = amt;
	}

	public SlsAmtSide getAmtSide() {
		return this.amtSide;
	}

	public void setAmtSide(final SlsAmtSide amtSide) {
		this.amtSide = amtSide;
	}

	public BigDecimal getBlnce() {
		return this.blnce;
	}

	public void setBlnce(final BigDecimal blnce) {
		this.blnce = blnce;
	}

	public SlsAmtSide getBlnceSide() {
		return this.blnceSide;
	}

	public void setBlnceSide(final SlsAmtSide blnceSide) {
		this.blnceSide = blnceSide;
	}

	public Date getEvntDt() {
		return this.evntDt;
	}

	public void setEvntDt(final Date evntDt) {
		this.evntDt = evntDt;
	}
}