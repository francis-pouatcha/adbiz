package org.adorsys.adacc.jpa;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

import org.adorsys.adcore.jpa.CoreAbstIdentifData;
import org.adorsys.javaext.description.Description;

@MappedSuperclass
@Description("AccAccount_description")
public abstract class AccAbstractAccount extends CoreAbstIdentifData {
	private static final long serialVersionUID = -8057236644491842037L;

	/*
	 * Identifier of the chart of account associated with this account.
	 */
	@Column
	@Description("AccAccount_accCoA_description")
	private String accCoA;

	/*
	 * Identifier of this account.
	 */
	@Column
	@Description("AccAccount_accNbr_description")
	private String accNbr;

	/*
	 * Categorization of this account.
	 */
	@Column
	@Description("AccAccount_accType_description")
	@Enumerated(EnumType.STRING)
	private AccType accType;

	/*
	 * The identifier of the parent account.
	 */
	@Column
	@Description("AccAccount_prntAccNbr_description")
	private String prntAccNbr;

	/*
	 * The currency of this account.
	 */
	@Column
	@Description("AccAccount_accCur_description")
	private String accCur;

	/*
	 * The description of this account.
	 */
	@Column
	@Description("AccAccount_accDesc_description")
	private String accDesc;

	/*
	 * Identifier of a bearer of this account.
	 */
	@Column
	@Description("AccAccount_accBrr_description")
	private String accBrr;

	public String getAccCoA() {
		return this.accCoA;
	}

	public void setAccCoA(final String accCoA) {
		this.accCoA = accCoA;
	}

	public String getAccNbr() {
		return this.accNbr;
	}

	public void setAccNbr(final String accNbr) {
		this.accNbr = accNbr;
	}

	public AccType getAccType() {
		return this.accType;
	}

	public void setAccType(final AccType accType) {
		this.accType = accType;
	}

	public String getPrntAccNbr() {
		return this.prntAccNbr;
	}

	public void setPrntAccNbr(final String prntAccNbr) {
		this.prntAccNbr = prntAccNbr;
	}

	public String getAccCur() {
		return this.accCur;
	}

	public void setAccCur(final String accCur) {
		this.accCur = accCur;
	}

	public String getAccDesc() {
		return this.accDesc;
	}

	public void setAccDesc(final String accDesc) {
		this.accDesc = accDesc;
	}

	public String getAccBrr() {
		return this.accBrr;
	}

	public void setAccBrr(final String accBrr) {
		this.accBrr = accBrr;
	}

	@Override
	protected String makeIdentif() {
		return accCoA + "_" + accNbr;
	}
}