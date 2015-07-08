package org.adorsys.adsales.jpa;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.CoreAbstIdentifData;
import org.adorsys.adcore.jpa.CoreAmtOrPct;
import org.adorsys.adcore.jpa.CoreCurrencyEnum;
import org.adorsys.adcore.utils.FinancialOps;
import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.format.DateFormatPattern;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;

@MappedSuperclass
@Description("SlsDelivery_description")
public class SlsAbstractDelivery extends CoreAbstIdentifData {

	private static final long serialVersionUID = -3800254699892409566L;

	@Column
	@Description("SlsDelivery_dlvryNbr_description")
	@NotNull
	private String dlvryNbr;

	@Column
	@Description("SlsDelivery_dlvrySlipNbr_description")
	@NotNull(message = "SlsDelivery_dlvrySlipNbr_NotNull_validation")
	private String dlvrySlipNbr;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("SlsDelivery_dtOnDlvrySlip_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy")
	private Date dtOnDlvrySlip;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("SlsDelivery_orderDt_description")
	private Date orderDt;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("SlsDelivery_dlvryDt_description")
	@NotNull
	private Date dlvryDt;

	@Column
	@Description("SlsDelivery_dlvryCur_description")
	@NotNull
	private String dlvryCur = CoreCurrencyEnum.XAF.name();

	@Column
	@Description("SlsDelivery_grossSPPreTax_description")
	private BigDecimal grossSPPreTax;

	@Column
	@Description("SlsDelivery_rebate_description")
	private BigDecimal rebate;

	@Column
	@Description("SlsDelivery_netSPPreTax_description")
	private BigDecimal netSPPreTax;

	@Column
	@Description("SlsDelivery_vatAmount_description")
	private BigDecimal vatAmount;

	@Column
	@Description("SlsDelivery_netSPTaxIncl_description")
	private BigDecimal netSPTaxIncl;

	@Column
	@Description("SlsDelivery_pymtDscntType_description")
	//@NotNull
	@Enumerated(EnumType.STRING)
	private CoreAmtOrPct pymtDscntType;
	
	@Column
	@Description("SlsDelivery_pymtDscntPct_description")
	private BigDecimal pymtDscntPct;

	@Column
	@Description("SlsDelivery_pymtDscntAmt_description")
	@NumberFormatType(NumberType.CURRENCY)
	private BigDecimal pymtDscntAmt;

	@Column
	@Description("SlsDelivery_netSalesAmt_description")
	@NumberFormatType(NumberType.CURRENCY)
	private BigDecimal netSalesAmt;

	@Column
	@Description("SlsDelivery_rdngDscntAmt_description")
	@NumberFormatType(NumberType.CURRENCY)
	private BigDecimal rdngDscntAmt;

	@Column
	@Description("SlsDelivery_netAmtToPay_description")
	@NumberFormatType(NumberType.CURRENCY)
	private BigDecimal netAmtToPay;

	@Column
	@Description("SlsDelivery_dlvryStatus_description")
	@NotNull
	private String dlvryStatus;

	public String getDlvryNbr() {
		return this.dlvryNbr;
	}

	public void setDlvryNbr(final String dlvryNbr) {
		this.dlvryNbr = dlvryNbr;
	}

	public String getDlvrySlipNbr() {
		return this.dlvrySlipNbr;
	}

	public void setDlvrySlipNbr(final String dlvrySlipNbr) {
		this.dlvrySlipNbr = dlvrySlipNbr;
	}

	public Date getDtOnDlvrySlip() {
		return this.dtOnDlvrySlip;
	}

	public void setDtOnDlvrySlip(final Date dtOnDlvrySlip) {
		this.dtOnDlvrySlip = dtOnDlvrySlip;
	}

	public Date getOrderDt() {
		return this.orderDt;
	}

	public void setOrderDt(final Date orderDt) {
		this.orderDt = orderDt;
	}

	public Date getDlvryDt() {
		return this.dlvryDt;
	}

	public void setDlvryDt(final Date dlvryDt) {
		this.dlvryDt = dlvryDt;
	}

	public String getDlvryCur() {
		return this.dlvryCur;
	}

	public void setDlvryCur(final String dlvryCur) {
		this.dlvryCur = dlvryCur;
	}

	public BigDecimal getGrossSPPreTax() {
		return this.grossSPPreTax;
	}

	public void setGrossSPPreTax(final BigDecimal grossSPPreTax) {
		this.grossSPPreTax = grossSPPreTax;
	}

	public BigDecimal getRebate() {
		return this.rebate;
	}

	public void setRebate(final BigDecimal rebate) {
		this.rebate = rebate;
	}

	public BigDecimal getNetSPPreTax() {
		return this.netSPPreTax;
	}

	public void setNetSPPreTax(final BigDecimal netSPPreTax) {
		this.netSPPreTax = netSPPreTax;
	}

	public BigDecimal getVatAmount() {
		return this.vatAmount;
	}

	public void setVatAmount(final BigDecimal vatAmount) {
		this.vatAmount = vatAmount;
	}

	public BigDecimal getNetSPTaxIncl() {
		return this.netSPTaxIncl;
	}

	public void setNetSPTaxIncl(final BigDecimal netSPTaxIncl) {
		this.netSPTaxIncl = netSPTaxIncl;
	}

	public BigDecimal getPymtDscntPct() {
		return this.pymtDscntPct;
	}

	public void setPymtDscntPct(final BigDecimal pymtDscntPct) {
		this.pymtDscntPct = pymtDscntPct;
	}

	public BigDecimal getPymtDscntAmt() {
		return this.pymtDscntAmt;
	}

	public void setPymtDscntAmt(final BigDecimal pymtDscntAmt) {
		this.pymtDscntAmt = pymtDscntAmt;
	}

	public BigDecimal getNetSalesAmt() {
		return this.netSalesAmt;
	}

	public void setNetSalesAmt(final BigDecimal netSalesAmt) {
		this.netSalesAmt = netSalesAmt;
	}

	public BigDecimal getRdngDscntAmt() {
		return this.rdngDscntAmt;
	}

	public void setRdngDscntAmt(final BigDecimal rdngDscntAmt) {
		this.rdngDscntAmt = rdngDscntAmt;
	}

	public BigDecimal getNetAmtToPay() {
		return this.netAmtToPay;
	}

	public void setNetAmtToPay(final BigDecimal netAmtToPay) {
		this.netAmtToPay = netAmtToPay;
	}

	public String getDlvryStatus() {
		return dlvryStatus;
	}

	public void setDlvryStatus(String dlvryStatus) {
		this.dlvryStatus = dlvryStatus;
	}
	
	public CoreAmtOrPct getPymtDscntType() {
		return pymtDscntType;
	}

	public void setPymtDscntType(CoreAmtOrPct pymtDscntType) {
		this.pymtDscntType = pymtDscntType;
	}

	public void copyTo(SlsAbstractDelivery target){
		target.dlvryCur = dlvryCur;
		target.dlvryDt = dlvryDt;
		target.dlvryNbr = dlvryNbr;
		target.identif = identif;
		target.dlvrySlipNbr = dlvrySlipNbr;
		target.dlvryStatus = dlvryStatus;
		target.dtOnDlvrySlip = dtOnDlvrySlip;
		target.grossSPPreTax = grossSPPreTax;
		target.netAmtToPay = netAmtToPay;
		target.netSPPreTax = netSPPreTax;
		target.netSPTaxIncl = netSPTaxIncl;
		target.netSalesAmt = netSalesAmt;
		target.orderDt = orderDt;
		target.pymtDscntAmt = pymtDscntAmt;
		target.pymtDscntPct = pymtDscntPct;
		target.pymtDscntType = pymtDscntType;
		target.rdngDscntAmt = rdngDscntAmt;
		target.rebate = rebate;
		target.vatAmount = vatAmount;
	}
	public void evlte() {
		if(CoreAmtOrPct.PERCENT.equals(this.pymtDscntType)){
			if(this.pymtDscntPct==null)this.pymtDscntPct = BigDecimal.ZERO;
			this.pymtDscntAmt = FinancialOps.amtFromPrct(this.netSPTaxIncl, this.pymtDscntPct, this.dlvryCur);
		}
		if(this.pymtDscntAmt==null) this.pymtDscntAmt=BigDecimal.ZERO;
		
		this.netSalesAmt = FinancialOps.substract(this.netSPTaxIncl, this.pymtDscntAmt, this.dlvryCur);
		
		if(this.rdngDscntAmt==null) this.rdngDscntAmt=BigDecimal.ZERO;
		this.netAmtToPay = FinancialOps.substract(this.netSalesAmt, this.rdngDscntAmt, this.dlvryCur);
	}
	
	public void clearAmts() {
		this.grossSPPreTax=BigDecimal.ZERO;
		this.rebate = BigDecimal.ZERO;
		this.netSPPreTax=BigDecimal.ZERO;
		this.vatAmount=BigDecimal.ZERO;
		this.netSPTaxIncl=BigDecimal.ZERO;
	}

	public void addGrossSPPreTax(BigDecimal grossSPPreTax) {
		if(this.grossSPPreTax==null)this.grossSPPreTax=BigDecimal.ZERO;
		this.grossSPPreTax = this.grossSPPreTax.add(grossSPPreTax);
	}

	public void addRebate(BigDecimal rebate) {
		if(this.rebate==null) this.rebate = BigDecimal.ZERO;
		this.rebate=this.rebate.add(rebate);
	}

	public void addNetSPPreTax(BigDecimal netSPPreTax) {
		if(this.netSPPreTax==null) this.netSPPreTax=BigDecimal.ZERO;
		this.netSPPreTax = this.netSPPreTax.add(netSPPreTax);
	}

	public void addVatAmount(BigDecimal vatAmt) {
		if(this.vatAmount==null)this.vatAmount=BigDecimal.ZERO;
		this.vatAmount = this.vatAmount.add(vatAmt);
	}

	public void addNetSPTaxIncl(BigDecimal netSPTaxIncl) {
		if(this.netSPTaxIncl==null)this.netSPTaxIncl=BigDecimal.ZERO;
		this.netSPTaxIncl=this.netSPTaxIncl.add(netSPTaxIncl);
	}
	
	@Override
	protected String makeIdentif() {
		return dlvryNbr;
	}
}