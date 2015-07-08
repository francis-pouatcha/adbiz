package org.adorsys.adcshdwr.jpa;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.CoreAbstIdentifData;
import org.adorsys.javaext.description.Description;

@Entity
@Description("CdrDsPymntItem_description")
public class CdrDsPymntItem extends CoreAbstIdentifData {

	private static final long serialVersionUID = 2547038812611354768L;

	@Column
	@Description("CdrDsPymntItem_dsNbr_description")
	@NotNull
	private String dsNbr;

	@Column
	@Description("CdrDsPymntItem_pymntMode_description")
	@Enumerated
	@NotNull
	private CdrPymntMode pymntMode;

	@Column
	@Description("CdrDsPymntItem_pymntDocType_description")
	private String pymntDocType;

	@Column
	@Description("CdrDsPymntItem_pymntDocNbr_description")
	private String pymntDocNbr;

	@Column
	@Description("CdrDsPymntItem_amt_description")
	@NotNull
	private BigDecimal amt;

	@Column
	@Description("CdrDsPymntItem_rcvdAmt_description")
	private BigDecimal rcvdAmt;

	@Column
	@Description("CdrDsPymntItem_diffAmt_description")
	private BigDecimal diffAmt;

	public String getDsNbr() {
		return this.dsNbr;
	}

	public void setDsNbr(final String dsNbr) {
		this.dsNbr = dsNbr;
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
	
	public void soustractDiffAmt() {
		if(this.amt == null) this.amt = BigDecimal.ZERO;
		if(this.rcvdAmt == null) this.rcvdAmt = BigDecimal.ZERO;
		if(this.diffAmt == null) this.diffAmt = BigDecimal.ZERO;
		this.diffAmt = this.diffAmt.add(rcvdAmt);
		this.diffAmt = this.diffAmt.add(amt.negate());
	}

	@Override
	protected String makeIdentif() {
		return dsNbr + "_ " + pymntMode.name() + "_" + pymntDocType + "_" + pymntDocNbr;
	}
}