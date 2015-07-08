package org.adorsys.adacc.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractMvmtData;
import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.format.DateFormatPattern;

@Entity
@Description("AccBlnc_description")
public class AccBlnc extends AbstractMvmtData {

	private static final long serialVersionUID = 9018199932757359100L;

	@Column
	@Description("AccBlnc_accCoA_description")
	private String accCoA;

	@Column
	@Description("AccBlnc_accNbr_description")
	private String accNbr;

	@Column
	@Description("AccBlnc_pstgDir_description")
	@Enumerated(EnumType.ORDINAL)
	private AccPstgDir pstgDir;

	@Column
	@Description("AccBlnc_accCur_description")
	private String accCur;

	@Column
	@Description("AccBlnc_amtInAccCur_description")
	private String amtInAccCur;

	@Column
	@Description("AccBlnc_refCur_description")
	private String refCur;

	@Column
	@Description("AccBlnc_amtInRefCur_description")
	private String amtInRefCur;

	@Column
	@Description("AccBlnc_accBrr_description")
	private String accBrr;

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