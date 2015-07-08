package org.adorsys.adacc.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractMvmtData;
import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.format.DateFormatPattern;

/**
 * The balance of an account.
 * 
 * @author francis
 *
 */
@MappedSuperclass
@Description("AccBlnc_description")
public abstract class AccAbstractBlnc extends AbstractMvmtData {

	private static final long serialVersionUID = 9018199932757359100L;

	/*
	 * The associated chart of account.
	 */
	@Column
	@Description("AccBlnc_accCoA_description")
	@NotNull
	private String accCoA;

	/*
	 * The associated account.
	 */
	@Column
	@Description("AccBlnc_accNbr_description")
	@NotNull
	private String accNbr;

	/*
	 * The posting direction, indication whether this is a debit or a credit balance.
	 */
	@Column
	@Description("AccBlnc_pstgDir_description")
	@Enumerated(EnumType.STRING)
	@NotNull
	private AccPstgDir pstgDir;

	/*
	 * The currency of this account.
	 */
	@Column
	@Description("AccBlnc_accCur_description")
	@NotNull
	private String accCur;

	/*
	 * The balance amount in the specified currency.
	 */
	@Column
	@Description("AccBlnc_amtInAccCur_description")
	private String amtInAccCur;

	/*
	 * The reference currency of the chart of account.
	 */
	@Column
	@Description("AccBlnc_refCur_description")
	private String refCur;

	/*
	 * The ballance amount in the reference currency,
	 */
	@Column
	@Description("AccBlnc_amtInRefCur_description")
	private String amtInRefCur;

	/*
	 * The account bearer.
	 */
	@Column
	@Description("AccBlnc_accBrr_description")
	private String accBrr;

	/*
	 * The balance date.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Description("AccBlnc_blncDt_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	@NotNull
	private Date blncDt;

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

	public AccPstgDir getPstgDir() {
		return this.pstgDir;
	}

	public void setPstgDir(final AccPstgDir pstgDir) {
		this.pstgDir = pstgDir;
	}

	public String getAccCur() {
		return this.accCur;
	}

	public void setAccCur(final String accCur) {
		this.accCur = accCur;
	}

	public String getAmtInAccCur() {
		return this.amtInAccCur;
	}

	public void setAmtInAccCur(final String amtInAccCur) {
		this.amtInAccCur = amtInAccCur;
	}

	public String getRefCur() {
		return this.refCur;
	}

	public void setRefCur(final String refCur) {
		this.refCur = refCur;
	}

	public String getAmtInRefCur() {
		return this.amtInRefCur;
	}

	public void setAmtInRefCur(final String amtInRefCur) {
		this.amtInRefCur = amtInRefCur;
	}

	public String getAccBrr() {
		return this.accBrr;
	}

	public void setAccBrr(final String accBrr) {
		this.accBrr = accBrr;
	}

	public Date getBlncDt() {
		return this.blncDt;
	}

	public void setBlncDt(final Date blncDt) {
		this.blncDt = blncDt;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (accCoA != null && !accCoA.trim().isEmpty())
			result += "accCoA: " + accCoA;
		if (accNbr != null && !accNbr.trim().isEmpty())
			result += ", accNbr: " + accNbr;
		if (accCur != null && !accCur.trim().isEmpty())
			result += ", accCur: " + accCur;
		if (amtInAccCur != null && !amtInAccCur.trim().isEmpty())
			result += ", amtInAccCur: " + amtInAccCur;
		if (refCur != null && !refCur.trim().isEmpty())
			result += ", refCur: " + refCur;
		if (amtInRefCur != null && !amtInRefCur.trim().isEmpty())
			result += ", amtInRefCur: " + amtInRefCur;
		if (accBrr != null && !accBrr.trim().isEmpty())
			result += ", accBrr: " + accBrr;
		return result;
	}
}