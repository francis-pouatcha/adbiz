package org.adorsys.adacc.jpa;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

import org.adorsys.adcore.jpa.CoreAbstIdentifData;
import org.adorsys.javaext.description.Description;

@MappedSuperclass
@Description("AccBrr_description")
public abstract class AccAbstractBrr extends CoreAbstIdentifData {

	private static final long serialVersionUID = -5582123540238913031L;
	
	/*
	 * The associated chart of account.
	 */
	@Column
	@Description("AccBrr_accCoA_description")
	private String accCoA;

	/*
	 * The identifier of this bearer.
	 */
	@Column
	@Description("AccBrr_brrNbr_description")
	private String brrNbr;

	/*
	 * The type of this bearer.
	 */
	@Column
	@Description("AccBrr_brrType_description")
	@Enumerated(EnumType.ORDINAL)
	private AccBrrType brrType;

	/*
	 * The identifier of the parent bearer if any.
	 */
	@Column
	@Description("AccBrr_prntBrrNbr_description")
	private String prntBrrNbr;

	/*
	 * The description of this bearer.
	 */
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