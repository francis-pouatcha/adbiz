package org.adorsys.adsales.jpa;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.CoreAbstIdentifData;
import org.adorsys.adcore.utils.FinancialOps;
import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.format.DateFormatPattern;

@Entity
@Description("SlsInvoice_description")
public class SlsInvoice extends CoreAbstIdentifData {

	private static final long serialVersionUID = -5368159204977758066L;

	@Column
	@Description("SlsInvoice_invceType_description")
	//@NotNull
	private String invceType;

	@Column
	@Description("SlsInvoice_invceNbr_description")
	@NotNull
	private String invceNbr;
	
	@Column
	@Description("SlsInvoice_soNbr_description")
	//@NotNull
	private String soNbr;

	@Column
	@Description("SlsInvoice_invcePymntStatus_description")
	private SlsInvcePymtStatus invcePymntStatus;
	
	@Column
	@Description("SlsInvoice_invceDelivered_description")
	private boolean invceDelivered;
	
	@Column
	@Description("SlsInvoice_invceStatus_description")
	@NotNull
	private String invceStatus;

	@Column
	@Description("SlsInvoice_invceCur_description")
	private String invceCur;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Description("SlsInvoice_invceDt_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	@NotNull
	private Date invceDt;

	@Column
	@Description("SlsInvoice_grossSPPreTax_description")
	private BigDecimal grossSPPreTax;

	@Column
	@Description("SlsInvoice_rebate_description")
	private BigDecimal rebate;

	@Column
	@Description("SlsInvoice_netSPPreTax_description")
	private BigDecimal netSPPreTax;

	@Column
	@Description("SlsInvoice_vatAmount_description")
	private BigDecimal vatAmount;

	@Column
	@Description("SlsInvoice_netSPTaxIncl_description")
	private BigDecimal netSPTaxIncl;

	@Column
	@Description("SlsInvoice_pymtDscntPct_description")
	private BigDecimal pymtDscntPct;

	@Column
	@Description("SlsInvoice_pymtDscntAmt_description")
	private BigDecimal pymtDscntAmt;

	@Column
	@Description("SlsInvoice_netSalesAmt_description")
	private BigDecimal netSalesAmt;

	@Column
	@Description("SlsInvoice_rdngDscntAmt_description")
	private BigDecimal rdngDscntAmt;

	@Column
	@Description("SlsInvoice_netAmtToPay_description")
	private BigDecimal netAmtToPay;
	
	@Transient
	@Description("SlsInvoice_ptnrNbr_description")
	private String ptnrNbr;
	
	@Column
	private String creatingUsr;
	
	@Column
	@Description("SlsInvoice_holdingAmount_description")
	private BigDecimal holdingAmount;
	
	@Column
	@Description("SlsInvoice_holdingPct_description")
	private BigDecimal holdingPct;
	
	@Transient
	private List<SlsInvceItem> slsInvceItems = new ArrayList<SlsInvceItem>();
	
	@Transient
	private List<SlsInvcePtnr> slsInvcePtnrs = new ArrayList<SlsInvcePtnr>();
	
	
	
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
				this.pymtDscntAmt = FinancialOps.amtFromPrct(this.netSPPreTax, this.pymtDscntPct, this.invceCur);
			
		if(this.pymtDscntAmt==null) this.pymtDscntAmt=BigDecimal.ZERO;
		
		this.netSalesAmt = FinancialOps.substract(this.netSPTaxIncl, this.pymtDscntAmt, this.invceCur);
		
		if(this.rdngDscntAmt==null) this.rdngDscntAmt=BigDecimal.ZERO;
		this.netAmtToPay = FinancialOps.substract(this.netSalesAmt, this.rdngDscntAmt, this.invceCur);
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

	public void copyTo(final SlsInvoice target){
		target.creatingUsr = creatingUsr;
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
		target.invceCur = invceCur;
		target.invceDt = invceDt;
		target.soNbr = soNbr;
		target.invceNbr = invceNbr;
		target.invceStatus = invceStatus;
		target.vatAmount = vatAmount;
		target.holdingAmount = holdingAmount;
		target.holdingPct = holdingPct;
	}
	
	public List<SlsInvceItem> getSlsInvceItems() {
		return slsInvceItems;
	}
	
	public void setSlsInvceItems(List<SlsInvceItem> slsInvceItems) {
		this.slsInvceItems = slsInvceItems;
	}
	
	public List<SlsInvcePtnr> getSlsInvcePtnrs() {
		return slsInvcePtnrs;
	}
	
	public void setSlsInvcePtnrs(List<SlsInvcePtnr> slsInvcePtnrs) {
		this.slsInvcePtnrs = slsInvcePtnrs;
	}

	public String getCreatingUsr() {
		return creatingUsr;
	}

	public SlsInvcePymtStatus getInvcePymntStatus() {
		return invcePymntStatus;
	}

	public void setInvcePymntStatus(SlsInvcePymtStatus invcePymntStatus) {
		this.invcePymntStatus = invcePymntStatus;
	}

	public void setCreatingUsr(String creatingUsr) {
		this.creatingUsr = creatingUsr;
	}

	public String getInvceType() {
		return this.invceType;
	}

	public void setInvceType(final String invceType) {
		this.invceType = invceType;
	}

	public String getInvceNbr() {
		return this.invceNbr;
	}

	public void setInvceNbr(final String invceNbr) {
		this.invceNbr = invceNbr;
	}

	public String getSoNbr() {
		return this.soNbr;
	}

	public void setSoNbr(final String soNbr) {
		this.soNbr = soNbr;
	}
	
	
	public boolean getInvceDelivered() {
		return invceDelivered;
	}

	public void setInvceDelivered(boolean invceDelivered) {
		this.invceDelivered = invceDelivered;
	}

	public String getInvceStatus() {
		return this.invceStatus;
	}

	public void setInvceStatus(final String invceStatus) {
		this.invceStatus = invceStatus;
	}

	public String getInvceCur() {
		return this.invceCur;
	}

	public void setInvceCur(final String invceCur) {
		this.invceCur = invceCur;
	}
	
	
	public Date getInvceDt() {
		return invceDt;
	}

	public void setInvceDt(Date invceDt) {
		this.invceDt = invceDt;
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

	public String getPtnrNbr() {
		return ptnrNbr;
	}

	public void setPtnrNbr(String ptnrNbr) {
		this.ptnrNbr = ptnrNbr;
	}
	

	public BigDecimal getHoldingAmount() {
		return holdingAmount;
	}

	public void setHoldingAmount(BigDecimal holdingAmount) {
		this.holdingAmount = holdingAmount;
	}

	public BigDecimal getHoldingPct() {
		return holdingPct;
	}

	public void setHoldingPct(BigDecimal holdingPct) {
		this.holdingPct = holdingPct;
	}

	@Override
	protected String makeIdentif() {
		return invceNbr;
	}
}