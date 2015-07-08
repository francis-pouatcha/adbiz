package org.adorsys.adacc.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.adorsys.adcore.jpa.CoreAbstTimedData;
import org.adorsys.javaext.description.Description;

@Entity
@Description("AccAccount_description")
public class AccAccount extends CoreAbstTimedData {
	private static final long serialVersionUID = 5118150729653002565L;

	@Column
	@Description("AccAccount_accCoA_description")
	private String accCoA;

	@Column
	@Description("AccAccount_accNbr_description")
	private String accNbr;

	@Column
	@Description("AccAccount_accType_description")
	@Enumerated(EnumType.ORDINAL)
	private AccType accType;

	@Column
	@Description("AccAccount_prntAccNbr_description")
	private String prntAccNbr;

	@Column
	@Description("AccAccount_accCur_description")
	private String accCur;

	@Column
	@Description("AccAccount_accDesc_description")
	private String accDesc;

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