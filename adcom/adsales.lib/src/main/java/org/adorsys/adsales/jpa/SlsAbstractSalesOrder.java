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

import org.adorsys.adcore.enums.CoreProcessStatusEnum;
import org.adorsys.adcore.jpa.CoreAbstIdentifData;
import org.adorsys.adcore.utils.FinancialOps;
import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.format.DateFormatPattern;

@MappedSuperclass
@Description("SlsSalesOrder_description")
public abstract class SlsAbstractSalesOrder extends CoreAbstIdentifData {

	private static final long serialVersionUID = 8702438704782113053L;

	@Column
	@Description("SlsSalesOrder_soNbr_description")
	@NotNull
	private String soNbr;
	
	@Column
	@Description("SlsSalesOrder_acsngUser_description")
	@NotNull
	private String acsngUser;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("SlsSalesOrder_acsngDt_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	@NotNull
	private Date acsngDt;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Description("SlsSalesOrder_soDt_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	@NotNull
	private Date soDt;

	@Column
	@Description("SlsSalesOrder_soStatus_description")
	@Enumerated(EnumType.STRING)
	@NotNull
	private CoreProcessStatusEnum soStatus;

	@Column
	@Description("SlsSalesOrder_soCur_description")
	private String soCur;

	@Column
	@Description("SlsSalesOrder_grossSPPreTax_description")
	private BigDecimal grossSPPreTax;

	@Column
	@Description("SlsSalesOrder_rebate_description")
	private BigDecimal rebate;
	
	@Column
	@Description("SlsSalesOrder_netSPPreTax_description")
	private BigDecimal netSPPreTax;

	@Column
	@Description("SlsSalesOrder_vatAmount_description")
	private BigDecimal vatAmount;

	@Column
	@Description("SlsSalesOrder_netSPTaxIncl_description")
	private BigDecimal netSPTaxIncl;

	@Column
	@Description("SlsSalesOrder_pymtDscntPct_description")
	private BigDecimal pymtDscntPct;

	@Column
	@Description("SlsSalesOrder_pymtDscntAmt_description")
	private BigDecimal pymtDscntAmt;

	@Column
	@Description("SlsSalesOrder_netSalesAmt_description")
	private BigDecimal netSalesAmt;

	@Column
	@Description("SlsSalesOrder_rdngDscntAmt_description")
	private BigDecimal rdngDscntAmt;

	@Column
	@Description("SlsSalesOrder_netAmtToPay_description")
	private BigDecimal netAmtToPay;
	
	public void clearAmts() {
		this.grossSPPreTax=BigDecimal.ZERO;
		this.rebate = BigDecimal.ZERO;
		this.netSPPreTax=BigDecimal.ZERO;
		this.vatAmount=BigDecimal.ZERO;
		this.netSPTaxIncl=BigDecimal.ZERO;
		this.netSalesAmt=BigDecimal.ZERO;
		this.netAmtToPay=BigDecimal.ZERO;
	}
	
	public void evlte() {
		
			if(this.pymtDscntPct!=null)
				this.pymtDscntAmt = FinancialOps.amtFromPrct(this.netSPPreTax, this.pymtDscntPct, this.soCur);
			
		if(this.pymtDscntAmt==null) this.pymtDscntAmt=BigDecimal.ZERO;
		
		this.netSalesAmt = FinancialOps.substract(this.netSPTaxIncl, this.pymtDscntAmt, this.soCur);
		
		if(this.rdngDscntAmt==null) this.rdngDscntAmt=BigDecimal.ZERO;
		this.netAmtToPay = FinancialOps.substract(this.netSalesAmt, this.rdngDscntAmt, this.soCur);
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



	public String getSoNbr() {
		return this.soNbr;
	}

	public void setSoNbr(final String soNbr) {
		this.soNbr = soNbr;
	}


	public CoreProcessStatusEnum getSoStatus() {
		return soStatus;
	}

	public void setSoStatus(CoreProcessStatusEnum soStatus) {
		this.soStatus = soStatus;
	}

	public String getSoCur() {
		return this.soCur;
	}

	public void setSoCur(final String soCur) {
		this.soCur = soCur;
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
	

	public String getAcsngUser() {
		return acsngUser;
	}

	public void setAcsngUser(String acsngUser) {
		this.acsngUser = acsngUser;
	}

	public Date getAcsngDt() {
		return acsngDt;
	}

	public void setAcsngDt(Date acsngDt) {
		this.acsngDt = acsngDt;
	}

	public Date getSoDt() {
		return soDt;
	}

	public void setSoDt(Date soDt) {
		this.soDt = soDt;
	}

	@Override
	protected String makeIdentif() {
		return soNbr;
	}
	
	public void copyTo(final SlsAbstractSalesOrder target){
		target.acsngDt = acsngDt;
		target.acsngUser = acsngUser;
		target.grossSPPreTax = grossSPPreTax;
		target.identif = identif;
		target.netAmtToPay = netAmtToPay;
		target.netSalesAmt = netSalesAmt;
		target.netSPPreTax = netSPPreTax;
		target.netSPTaxIncl = netSPTaxIncl;
		target.pymtDscntAmt = pymtDscntAmt;
		target.pymtDscntPct = pymtDscntPct;
		target.rdngDscntAmt = rdngDscntAmt;
		target.rebate = rebate;
		target.soCur = soCur;
		target.soDt = soDt;
		target.soNbr = soNbr;
		target.soStatus = soStatus;
		target.vatAmount = vatAmount;
	}
}