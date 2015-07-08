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

@MappedSuperclass
@Description("AccPstg_description")
public abstract class AccAbstractPstg extends AbstractMvmtData {

	private static final long serialVersionUID = -7349543580915844284L;

	@Column
	@Description("AccPstg_refNbr_description")
	private String refNbr;

	@Column
	@Description("AccPstg_accOpId_description")
	private String accOpId;

	@Column
	@Description("AccPstg_pstgDir_description")
	@Enumerated(EnumType.ORDINAL)
	private AccPstgDir pstgDir;

	@Column
	@Description("AccPstg_accCoA_description")
	private String accCoA;

	@Column
	@Description("AccPstg_accNbr_description")
	private String accNbr;

	@Column
	@Description("AccPstg_accType_description")
	@Enumerated(EnumType.ORDINAL)
	private AccType accType;

	@Column
	@Description("AccPstg_accCur_description")
	private String accCur;

	@Column
	@Description("AccPstg_amtInAccCur_description")
	private String amtInAccCur;

	@Column
	@Description("AccPstg_refCur_description")
	private String refCur;

	@Column
	@Description("AccPstg_amtInRefCur_description")
	private String amtInRefCur;

	@Column
	@Description("AccPstg_opType_description")
	@Enumerated(EnumType.ORDINAL)
	@NotNull
	private AccOpType opType;

	@Column
	@Description("AccPstg_opDesc_description")
	private String opDesc;

	@Column
	@Description("AccPstg_pstgDesc_description")
	private String pstgDesc;

	@Column
	@Description("AccPstg_opBrr_description")
	private String opBrr;

	@Column
	@Description("AccPstg_pstgBrr_description")
	private String pstgBrr;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("AccPstg_pstgDt_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	@NotNull
	private Date pstgDt;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("AccPstg_valueDt_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	@NotNull
	private Date valueDt;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("AccPstg_docDt_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	@NotNull
	private Date docDt;

	@Column
	@Description("AccPstg_docIds_description")
	private String docIds;

	public String getRefNbr() {
		return this.refNbr;
	}

	public void setRefNbr(final String refNbr) {
		this.refNbr = refNbr;
	}

	public String getAccOpId() {
		return this.accOpId;
	}

	public void setAccOpId(final String accOpId) {
		this.accOpId = accOpId;
	}

	public AccPstgDir getPstgDir() {
		return this.pstgDir;
	}

	public void setPstgDir(final AccPstgDir pstgDir) {
		this.pstgDir = pstgDir;
	}

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

	public AccOpType getOpType() {
		return this.opType;
	}

	public void setOpType(final AccOpType opType) {
		this.opType = opType;
	}

	public String getOpDesc() {
		return this.opDesc;
	}

	public void setOpDesc(final String opDesc) {
		this.opDesc = opDesc;
	}

	public String getPstgDesc() {
		return this.pstgDesc;
	}

	public void setPstgDesc(final String pstgDesc) {
		this.pstgDesc = pstgDesc;
	}

	public String getOpBrr() {
		return this.opBrr;
	}

	public void setOpBrr(final String opBrr) {
		this.opBrr = opBrr;
	}

	public String getPstgBrr() {
		return this.pstgBrr;
	}

	public void setPstgBrr(final String pstgBrr) {
		this.pstgBrr = pstgBrr;
	}

	public Date getPstgDt() {
		return this.pstgDt;
	}

	public void setPstgDt(final Date pstgDt) {
		this.pstgDt = pstgDt;
	}

	public Date getValueDt() {
		return this.valueDt;
	}

	public void setValueDt(final Date valueDt) {
		this.valueDt = valueDt;
	}

	public Date getDocDt() {
		return this.docDt;
	}

	public void setDocDt(final Date docDt) {
		this.docDt = docDt;
	}

	public String getDocIds() {
		return this.docIds;
	}

	public void setDocIds(final String docIds) {
		this.docIds = docIds;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (refNbr != null && !refNbr.trim().isEmpty())
			result += "refNbr: " + refNbr;
		if (accOpId != null && !accOpId.trim().isEmpty())
			result += ", accOpId: " + accOpId;
		if (accCoA != null && !accCoA.trim().isEmpty())
			result += ", accCoA: " + accCoA;
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
		if (opDesc != null && !opDesc.trim().isEmpty())
			result += ", opDesc: " + opDesc;
		if (pstgDesc != null && !pstgDesc.trim().isEmpty())
			result += ", pstgDesc: " + pstgDesc;
		if (opBrr != null && !opBrr.trim().isEmpty())
			result += ", opBrr: " + opBrr;
		if (pstgBrr != null && !pstgBrr.trim().isEmpty())
			result += ", pstgBrr: " + pstgBrr;
		if (docIds != null && !docIds.trim().isEmpty())
			result += ", docIds: " + docIds;
		return result;
	}
}