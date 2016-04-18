package org.adorsys.adcshdwr.jpa;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;
import org.adorsys.adcore.utils.BigDecimalUtils;

/**
 * vchrNbr is the identifier.
 * 
 * @author fpo
 *
 */
@MappedSuperclass
@Description("CdrAbstVchr_description")
public abstract class CdrAbstVchr extends CoreAbstIdentifObject{

	private static final long serialVersionUID = 372706929812229922L;

	@Column
	@Description("CdrAbstVchr_origDocType_description")
	@NotNull
	private String origDocType;

	@Column
	@Description("CdrAbstVchr_origDocNbr_description")
	@NotNull
	private String origDocNbr;

	@Column
	@Description("CdrAbstVchr_origAmt_description")
	@NotNull
	private BigDecimal origAmt;

	@Column
	@Description("CdrAbstVchr_remainingAmt_description")
	private BigDecimal remainingAmt;

	@Column
	@Description("CdrAbstVchr_usedAmt_description")
	private BigDecimal usedAmt;

	@Column
	@Description("CdrPymntItem_orgUnit_description")
	private String bsnsPartner;

	private String bsnsPrtnrOU;
	
	@NotNull
	private String bsnsPtnrName;

	@Column
	@Description("CdrCstmrVchr_canceled_description")
	private Boolean canceled;
	
	@Column
	@Description("CdrCstmrVchr_settled_description")
	private Boolean settled;

	@Override
	public String makeIdentif() {
		throw new IllegalStateException("Identifier is supposed to be set before persist.");
	}

	/**
	 * evlte.
	 *
	 */
	public void evlte() {
		this.remainingAmt = BigDecimalUtils.subs(origAmt, usedAmt);
	}

	public String getOrigDocType() {
		return origDocType;
	}

	public void setOrigDocType(String origDocType) {
		this.origDocType = origDocType;
	}

	public String getOrigDocNbr() {
		return origDocNbr;
	}

	public void setOrigDocNbr(String origDocNbr) {
		this.origDocNbr = origDocNbr;
	}

	public BigDecimal getOrigAmt() {
		return origAmt;
	}

	public void setOrigAmt(BigDecimal origAmt) {
		this.origAmt = origAmt;
	}

	public BigDecimal getRemainingAmt() {
		return remainingAmt;
	}

	public void setRemainingAmt(BigDecimal remainingAmt) {
		this.remainingAmt = remainingAmt;
	}

	public BigDecimal getUsedAmt() {
		return usedAmt;
	}

	public void setUsedAmt(BigDecimal usedAmt) {
		this.usedAmt = usedAmt;
	}

	public String getBsnsPartner() {
		return bsnsPartner;
	}

	public void setBsnsPartner(String bsnsPartner) {
		this.bsnsPartner = bsnsPartner;
	}

	public String getBsnsPrtnrOU() {
		return bsnsPrtnrOU;
	}

	public void setBsnsPrtnrOU(String bsnsPrtnrOU) {
		this.bsnsPrtnrOU = bsnsPrtnrOU;
	}

	public String getBsnsPtnrName() {
		return bsnsPtnrName;
	}

	public void setBsnsPtnrName(String bsnsPtnrName) {
		this.bsnsPtnrName = bsnsPtnrName;
	}

	public Boolean getCanceled() {
		return canceled;
	}

	public void setCanceled(Boolean canceled) {
		this.canceled = canceled;
	}

	public Boolean getSettled() {
		return settled;
	}

	public void setSettled(Boolean settled) {
		this.settled = settled;
	}
}
