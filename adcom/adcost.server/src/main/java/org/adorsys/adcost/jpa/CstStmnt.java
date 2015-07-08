package org.adorsys.adcost.jpa;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.CoreAbstIdentifData;
import org.adorsys.adcore.jpa.CoreAmtOrPct;
import org.adorsys.adcore.utils.BigDecimalUtils;
import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.format.DateFormatPattern;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;

/**
 * A statement gathers costs/profits assigned to a bearer.
 * 
 * @author francis
 *
 */
@Entity
@Description("CstStmnt_description")
public class CstStmnt extends CoreAbstIdentifData {

	private static final long serialVersionUID = 5712985206096922961L;

	/*
	 * The identifier of the hosting statement.
	 */
	@Column
	@Description("CstStmntItem_stmntNbr_description")
	@NotNull
	private String stmntNbr;
	
	/*
	 * The type of this statement.
	 */
	@Column
	@Description("CstStmnt_stmntType_description")
	@Enumerated(EnumType.STRING)
	@NotNull
	private CstStmntType stmntType;

	/*
	 * The identifier of the source activity center.
	 */
	@Column
	@Description("CstStmntItem_srcCtrNbr_description")
	@NotNull
	private String srcCtrNbr;

	/*
	 * The identifier of the activity center receiving this statement.
	 */
	@Column
	@Description("CstStmntItem_destCtrNbr_description")
	@NotNull
	private String destCtrNbr;

	/*
	 * The identifier of a documentation of this cost bearer.
	 */
	@Column
	@Description("CstStmnt_stmntSlipNbr_description")
	private String stmntSlipNbr;
	
	/*
	 * The currency of this statement
	 */
	@Column
	@Description("CstStmnt_stmntCur_description")
	private String stmntCur;
	
	/*
	 * The value date of this statement.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Description("CstStmnt_valueDt_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	@NotNull
	private Date valueDt;

	@Column
	@Description("CstStmnt_grossTAPreTax_description")
	private BigDecimal grossTAPreTax;

	@Column
	@Description("CstStmnt_rebate_description")
	private BigDecimal rebate;

	@Column
	@Description("CstStmnt_netTAPreTax_description")
	private BigDecimal netTAPreTax;

	@Column
	@Description("CstStmnt_vatAmount_description")
	private BigDecimal vatAmount;

	@Column
	@Description("CstStmnt_netTATaxIncl_description")
	private BigDecimal netTATaxIncl;

	/*
	 * The payment discount type. Whether a percent or a nominal amount.
	 */
	@Column
	@Description("CstStmnt_pymtDscntType_description")
	@NotNull
	@Enumerated(EnumType.STRING)
	private CoreAmtOrPct pymtDscntType;
	
	/*
	 * Percentage of the payment discount. Will be derived if the
	 * payment discount type is amount.
	 */
	@Column
	@Description("CstStmnt_pymtDscntPct_description")
	private BigDecimal pymtDscntPct;

	/*
	 * The amount of the payment discount. Will be derived if the
	 * type of the payment discount is percent.
	 */
	@Column
	@Description("CstStmnt_pymtDscntAmt_description")
	private BigDecimal pymtDscntAmt;

	/*
	 * The transfer amount after payment discount.
	 */
	@Column
	@Description("CstStmnt_netTAPostPymntDscnt_description")
	private BigDecimal netTAPostPymntDscnt;

	@Column
	@Description("CstStmnt_rdngDscntAmt_description")
	private BigDecimal rdngDscntAmt;

	/*
	 * The net transfer amount after rounding discount.
	 */
	@Column
	@Description("CstStmntItem_netTransferAmt_description")
	@NumberFormatType(NumberType.CURRENCY)
	private BigDecimal netTransferAmt;

	@Override
	protected String makeIdentif() {
		return stmntNbr;
	}

	public void clearAmts() {
		this.grossTAPreTax=BigDecimal.ZERO;
		this.rebate = BigDecimal.ZERO;
		this.netTAPreTax=BigDecimal.ZERO;
		this.vatAmount=BigDecimal.ZERO;
		this.netTATaxIncl=BigDecimal.ZERO;
		this.netTAPreTax=BigDecimal.ZERO;
		this.netTransferAmt=BigDecimal.ZERO;
	}
	
	public void evlte() {
		if(CoreAmtOrPct.PERCENT == pymtDscntType){
			pymtDscntPct = BigDecimalUtils.zeroIfNull(pymtDscntPct);
			pymtDscntAmt = BigDecimalUtils.basePercentOfRatePct(pymtDscntPct, netTATaxIncl, stmntCur);
		} else {
			pymtDscntAmt = BigDecimalUtils.zeroIfNull(pymtDscntAmt);
			pymtDscntPct = BigDecimalUtils.ratePercentOfPartFromBase(pymtDscntAmt, netTATaxIncl, RoundingMode.HALF_EVEN);
		}
		BigDecimalUtils.subs(netTATaxIncl, pymtDscntAmt);
		
		rdngDscntAmt = BigDecimalUtils.zeroIfNull(rdngDscntAmt);
		
		this.netTransferAmt = BigDecimalUtils.subs(netTAPreTax, this.rdngDscntAmt);
	}

	public void addGrossTAPreTax(BigDecimal grossTAPreTax) {
		this.grossTAPreTax = BigDecimalUtils.zeroIfNull(this.grossTAPreTax).add(grossTAPreTax);
	}

	public void addRebate(BigDecimal rebate) {
		this.rebate= BigDecimalUtils.zeroIfNull(this.rebate).add(rebate);
	}

	public void addNetTAPreTax(BigDecimal netTAPreTax) {
		this.netTAPreTax = BigDecimalUtils.zeroIfNull(this.netTAPreTax).add(netTAPreTax);
	}

	public void addVatAmount(BigDecimal vatAmt) {
		this.vatAmount = BigDecimalUtils.zeroIfNull(this.vatAmount).add(vatAmt);
	}

	public void addNetTATaxIncl(BigDecimal netTATaxIncl) {
		this.netTATaxIncl=BigDecimalUtils.zeroIfNull(this.netTATaxIncl).add(netTATaxIncl);
	}

	public void copyTo(final CstStmnt target){
		target.destCtrNbr = destCtrNbr;
		target.grossTAPreTax = grossTAPreTax;
		target.identif = identif;
		target.netTAPostPymntDscnt = netTAPostPymntDscnt;
		target.netTAPreTax = netTAPreTax;
		target.netTATaxIncl = netTATaxIncl;
		target.netTransferAmt = netTransferAmt;
		target.pymtDscntAmt = pymtDscntAmt;
		target.pymtDscntPct = pymtDscntPct;
		target.pymtDscntType = pymtDscntType;
		target.rdngDscntAmt = rdngDscntAmt;
		target.rebate = rebate;
		target.srcCtrNbr = srcCtrNbr;
		target.stmntNbr = stmntNbr;
		target.stmntSlipNbr = stmntSlipNbr;
		target.stmntType = stmntType;
		target.vatAmount = vatAmount;
	}

	public String getStmntNbr() {
		return stmntNbr;
	}

	public void setStmntNbr(String stmntNbr) {
		this.stmntNbr = stmntNbr;
	}

	public CstStmntType getStmntType() {
		return stmntType;
	}

	public void setStmntType(CstStmntType stmntType) {
		this.stmntType = stmntType;
	}

	public String getSrcCtrNbr() {
		return srcCtrNbr;
	}

	public void setSrcCtrNbr(String srcCtrNbr) {
		this.srcCtrNbr = srcCtrNbr;
	}

	public String getDestCtrNbr() {
		return destCtrNbr;
	}

	public void setDestCtrNbr(String destCtrNbr) {
		this.destCtrNbr = destCtrNbr;
	}

	public String getStmntSlipNbr() {
		return stmntSlipNbr;
	}

	public void setStmntSlipNbr(String stmntSlipNbr) {
		this.stmntSlipNbr = stmntSlipNbr;
	}

	public String getStmntCur() {
		return stmntCur;
	}

	public void setStmntCur(String stmntCur) {
		this.stmntCur = stmntCur;
	}

	public Date getValueDt() {
		return valueDt;
	}

	public void setValueDt(Date valueDt) {
		this.valueDt = valueDt;
	}

	public BigDecimal getGrossTAPreTax() {
		return grossTAPreTax;
	}

	public void setGrossTAPreTax(BigDecimal grossTAPreTax) {
		this.grossTAPreTax = grossTAPreTax;
	}

	public BigDecimal getRebate() {
		return rebate;
	}

	public void setRebate(BigDecimal rebate) {
		this.rebate = rebate;
	}

	public BigDecimal getNetTAPreTax() {
		return netTAPreTax;
	}

	public void setNetTAPreTax(BigDecimal netTAPreTax) {
		this.netTAPreTax = netTAPreTax;
	}

	public BigDecimal getVatAmount() {
		return vatAmount;
	}

	public void setVatAmount(BigDecimal vatAmount) {
		this.vatAmount = vatAmount;
	}

	public BigDecimal getNetTATaxIncl() {
		return netTATaxIncl;
	}

	public void setNetTATaxIncl(BigDecimal netTATaxIncl) {
		this.netTATaxIncl = netTATaxIncl;
	}

	public CoreAmtOrPct getPymtDscntType() {
		return pymtDscntType;
	}

	public void setPymtDscntType(CoreAmtOrPct pymtDscntType) {
		this.pymtDscntType = pymtDscntType;
	}

	public BigDecimal getPymtDscntPct() {
		return pymtDscntPct;
	}

	public void setPymtDscntPct(BigDecimal pymtDscntPct) {
		this.pymtDscntPct = pymtDscntPct;
	}

	public BigDecimal getPymtDscntAmt() {
		return pymtDscntAmt;
	}

	public void setPymtDscntAmt(BigDecimal pymtDscntAmt) {
		this.pymtDscntAmt = pymtDscntAmt;
	}

	public BigDecimal getNetTAPostPymntDscnt() {
		return netTAPostPymntDscnt;
	}

	public void setNetTAPostPymntDscnt(BigDecimal netTAPostPymntDscnt) {
		this.netTAPostPymntDscnt = netTAPostPymntDscnt;
	}

	public BigDecimal getRdngDscntAmt() {
		return rdngDscntAmt;
	}

	public void setRdngDscntAmt(BigDecimal rdngDscntAmt) {
		this.rdngDscntAmt = rdngDscntAmt;
	}

	public BigDecimal getNetTransferAmt() {
		return netTransferAmt;
	}

	public void setNetTransferAmt(BigDecimal netTransferAmt) {
		this.netTransferAmt = netTransferAmt;
	}
	
}