package org.adorsys.adacc.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.adorsys.adcore.jpa.CoreAbstTimedData;
import org.adorsys.javaext.description.Description;

@Entity
@Description("AccBrr_description")
public class AccBrr extends CoreAbstTimedData {

	private static final long serialVersionUID = -5582123540238913031L;

	@Column
	@Description("AccBrr_accCoA_description")
	private String accCoA;

	@Column
	@Description("AccBrr_brrNbr_description")
	private String brrNbr;

	@Column
	@Description("AccBrr_brrType_description")
	@Enumerated(EnumType.ORDINAL)
	private AccBrrType brrType;

	@Column
	@Description("AccBrr_prntBrrNbr_description")
	private String prntBrrNbr;

	@Column
	@Description("AccBrr_brrDesc_description")
	private String brrDesc;

	public String getAccCoA() {
		return this.accCoA;
	}

	public void setAccCoA(final String accCoA) {
		this.accCoA = accCoA;
	}

	public String getBrrNbr() {
		return this.brrNbr;
	}

	public void setBrrNbr(final String brrNbr) {
		this.brrNbr = brrNbr;
	}

	public AccBrrType getBrrType() {
		return this.brrType;
	}

	public void setBrrType(final AccBrrType brrType) {
		this.brrType = brrType;
	}

	public String getPrntBrrNbr() {
		return this.prntBrrNbr;
	}

	public void setPrntBrrNbr(final String prntBrrNbr) {
		this.prntBrrNbr = prntBrrNbr;
	}

	public String getBrrDesc() {
		return this.brrDesc;
	}

	public void setBrrDesc(final String brrDesc) {
		this.brrDesc = brrDesc;
	}

	@Override
	protected String makeIdentif() {
		return accCoA + "_" + brrNbr;
	}
}