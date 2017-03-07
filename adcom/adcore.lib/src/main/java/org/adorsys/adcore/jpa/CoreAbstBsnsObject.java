package org.adorsys.adcore.jpa;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.adorsys.adcore.annotation.DateFormatPattern;
import org.adorsys.adcore.utils.BigDecimalUtils;
import org.adorsys.adcore.utils.FinancialOps;
import org.apache.commons.lang3.StringUtils;

@MappedSuperclass
public abstract class CoreAbstBsnsObject extends CoreAbstBsnsObjectHeader {

	private static final long serialVersionUID = 8742902615786244347L;

	@Column
	//@NotNull
	private String prchPrcCur = CoreCurrencyEnum.XAF.name();

	@Column
	private BigDecimal prchRstckgFeesPreTax;

	@Column
	private BigDecimal prchGrossPrcPreTax;

	@Column
	private BigDecimal prchRebateAmt;

	@Column
	private BigDecimal prchNetPrcPreTax;

	@Column
	private BigDecimal prchVatAmt;

	@Column
	private BigDecimal prchNetPrcTaxIncl;

	@Column
	@Enumerated(EnumType.STRING)
	private CoreAmtOrPct prchPymtDscntType;
	
	@Column
	private BigDecimal prchPymtDscntPct;

	@Column
	private BigDecimal prchNetPymtAmt;

	@Column
	private BigDecimal prchPymtDscntAmt;
	
	@Column
	private BigDecimal prchRdngDscntAmt;

	@Column
	private BigDecimal prchNetAmtToPay;
	
	@Column
	private BigDecimal prchWrntyDays;

	@Column
	private BigDecimal prchRtrnDays;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date prchWrntyDt;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date prchRtrnDt;

	@Column
	@NotNull
	private String slsPrcCur = CoreCurrencyEnum.XAF.name();
	
	@Column
	private BigDecimal slsRstckgFeesPreTax;

	@Column
	private BigDecimal slsGrossPrcPreTax;

	@Column
	private BigDecimal slsRebateAmt;

	@Column
	private BigDecimal slsNetPrcPreTax;

	@Column
	private BigDecimal slsVatAmt;

	@Column
	private BigDecimal slsNetPrcTaxIncl;

	@Column
	@Enumerated(EnumType.STRING)
	private CoreAmtOrPct slsPymtDscntType;
	
	@Column
	private BigDecimal slsPymtDscntPct;

	@Column
	private BigDecimal slsPymtDscntAmt;

	@Column
	private BigDecimal slsNetPymtAmt;
	
	@Column
	private BigDecimal slsRdngDscntAmt;

	@Column
	private BigDecimal slsNetAmtToPay;
	
	@Column
	private BigDecimal slsWrntyDays;

	@Column
	private BigDecimal slsRtrnDays;

	@Temporal(TemporalType.TIMESTAMP)
	private Date slsWrntyDt;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date slsRtrnDt;

	@Column
	@NotNull
	private String stkUnitValCur = CoreCurrencyEnum.XAF.name();

	@Column
	private BigDecimal stkValPreTax;

	@Column
	@Size(max = 256, message = "InvInvtry_descptn_Size_validation")
	private String descptn;

	/*
	 * The section of a business object can be used to identify the 
	 * store location, where the business is taking place.
	 * 
	 * The section of a item will then be the location of the item in the 
	 * corresponding storage.
	 */
	@Column
	private String section;

	@Temporal(TemporalType.TIMESTAMP)
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private Date preparedDt;

	@Temporal(TemporalType.TIMESTAMP)
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private Date closedDate;

	@Temporal(TemporalType.TIMESTAMP)
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private Date mergedDate;

	@Temporal(TemporalType.TIMESTAMP)
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private Date postedDate;
	
	@Column
	private String status;

	/*
	 * Define the group to which this inventory belongs to. IT is necessary to
	 * help select inventory belonging to the same group and compare them and even merge them.
	 */
	private String txGroup;
	
	/*
	 * The type of this transaction object.
	 */
	private String txType;

	@Temporal(TemporalType.TIMESTAMP)
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private Date conflictDt;
	
	private String bsnsPartner;

	private String bsnsPrtnrOU;
	
	private String bsnsPtnrName;
	
	//we will change to put userIdentif later, the creator of this object
	@Column
	private String username;
	
	private String docNbr;
	
	public String getPrchPrcCur() {
		return prchPrcCur;
	}

	public void setPrchPrcCur(String prchPrcCur) {
		this.prchPrcCur = prchPrcCur;
	}

	public BigDecimal getPrchRstckgFeesPreTax() {
		return prchRstckgFeesPreTax;
	}

	public void setPrchRstckgFeesPreTax(BigDecimal prchRstckgFeesPreTax) {
		this.prchRstckgFeesPreTax = prchRstckgFeesPreTax;
	}

	public BigDecimal getPrchGrossPrcPreTax() {
		return prchGrossPrcPreTax;
	}

	public void setPrchGrossPrcPreTax(BigDecimal prchGrossPrcPreTax) {
		this.prchGrossPrcPreTax = prchGrossPrcPreTax;
	}

	public BigDecimal getPrchRebateAmt() {
		return prchRebateAmt;
	}

	public void setPrchRebateAmt(BigDecimal prchRebateAmt) {
		this.prchRebateAmt = prchRebateAmt;
	}

	public BigDecimal getPrchNetPrcPreTax() {
		return prchNetPrcPreTax;
	}

	public void setPrchNetPrcPreTax(BigDecimal prchNetPrcPreTax) {
		this.prchNetPrcPreTax = prchNetPrcPreTax;
	}

	public BigDecimal getPrchVatAmt() {
		return prchVatAmt;
	}

	public void setPrchVatAmt(BigDecimal prchVatAmt) {
		this.prchVatAmt = prchVatAmt;
	}

	public BigDecimal getPrchNetPrcTaxIncl() {
		return prchNetPrcTaxIncl;
	}

	public void setPrchNetPrcTaxIncl(BigDecimal prchNetPrcTaxIncl) {
		this.prchNetPrcTaxIncl = prchNetPrcTaxIncl;
	}

	public BigDecimal getPrchRdngDscntAmt() {
		return prchRdngDscntAmt;
	}

	public void setPrchRdngDscntAmt(BigDecimal prchRdngDscntAmt) {
		this.prchRdngDscntAmt = prchRdngDscntAmt;
	}

	public BigDecimal getPrchNetAmtToPay() {
		return prchNetAmtToPay;
	}

	public void setPrchNetAmtToPay(BigDecimal prchNetAmtToPay) {
		this.prchNetAmtToPay = prchNetAmtToPay;
	}

	public BigDecimal getPrchWrntyDays() {
		return prchWrntyDays;
	}

	public void setPrchWrntyDays(BigDecimal prchWrntyDays) {
		this.prchWrntyDays = prchWrntyDays;
	}

	public BigDecimal getPrchRtrnDays() {
		return prchRtrnDays;
	}

	public void setPrchRtrnDays(BigDecimal prchRtrnDays) {
		this.prchRtrnDays = prchRtrnDays;
	}

	public Date getPrchWrntyDt() {
		return prchWrntyDt;
	}

	public void setPrchWrntyDt(Date prchWrntyDt) {
		this.prchWrntyDt = prchWrntyDt;
	}

	public Date getPrchRtrnDt() {
		return prchRtrnDt;
	}

	public void setPrchRtrnDt(Date prchRtrnDt) {
		this.prchRtrnDt = prchRtrnDt;
	}

	public String getSlsPrcCur() {
		return slsPrcCur;
	}

	public void setSlsPrcCur(String slsPrcCur) {
		this.slsPrcCur = slsPrcCur;
	}

	public BigDecimal getSlsRstckgFeesPreTax() {
		return slsRstckgFeesPreTax;
	}

	public void setSlsRstckgFeesPreTax(BigDecimal slsRstckgFeesPreTax) {
		this.slsRstckgFeesPreTax = slsRstckgFeesPreTax;
	}

	public BigDecimal getSlsGrossPrcPreTax() {
		return slsGrossPrcPreTax;
	}

	public void setSlsGrossPrcPreTax(BigDecimal slsGrossPrcPreTax) {
		this.slsGrossPrcPreTax = slsGrossPrcPreTax;
	}

	public BigDecimal getSlsRebateAmt() {
		return slsRebateAmt;
	}

	public void setSlsRebateAmt(BigDecimal slsRebateAmt) {
		this.slsRebateAmt = slsRebateAmt;
	}

	public BigDecimal getSlsNetPrcPreTax() {
		return slsNetPrcPreTax;
	}

	public void setSlsNetPrcPreTax(BigDecimal slsNetPrcPreTax) {
		this.slsNetPrcPreTax = slsNetPrcPreTax;
	}

	public BigDecimal getSlsVatAmt() {
		return slsVatAmt;
	}

	public void setSlsVatAmt(BigDecimal slsVatAmt) {
		this.slsVatAmt = slsVatAmt;
	}

	public BigDecimal getSlsNetPrcTaxIncl() {
		return slsNetPrcTaxIncl;
	}

	public void setSlsNetPrcTaxIncl(BigDecimal slsNetPrcTaxIncl) {
		this.slsNetPrcTaxIncl = slsNetPrcTaxIncl;
	}

	public BigDecimal getSlsRdngDscntAmt() {
		return slsRdngDscntAmt;
	}

	public void setSlsRdngDscntAmt(BigDecimal slsRdngDscntAmt) {
		this.slsRdngDscntAmt = slsRdngDscntAmt;
	}

	public BigDecimal getSlsNetAmtToPay() {
		return slsNetAmtToPay;
	}

	public void setSlsNetAmtToPay(BigDecimal slsNetAmtToPay) {
		this.slsNetAmtToPay = slsNetAmtToPay;
	}

	public BigDecimal getSlsWrntyDays() {
		return slsWrntyDays;
	}

	public void setSlsWrntyDays(BigDecimal slsWrntyDays) {
		this.slsWrntyDays = slsWrntyDays;
	}

	public BigDecimal getSlsRtrnDays() {
		return slsRtrnDays;
	}

	public void setSlsRtrnDays(BigDecimal slsRtrnDays) {
		this.slsRtrnDays = slsRtrnDays;
	}

	public Date getSlsWrntyDt() {
		return slsWrntyDt;
	}

	public void setSlsWrntyDt(Date slsWrntyDt) {
		this.slsWrntyDt = slsWrntyDt;
	}

	public Date getSlsRtrnDt() {
		return slsRtrnDt;
	}

	public void setSlsRtrnDt(Date slsRtrnDt) {
		this.slsRtrnDt = slsRtrnDt;
	}

	public String getStkUnitValCur() {
		return stkUnitValCur;
	}

	public void setStkUnitValCur(String stkUnitValCur) {
		this.stkUnitValCur = stkUnitValCur;
	}

	public BigDecimal getStkValPreTax() {
		return stkValPreTax;
	}

	public void setStkValPreTax(BigDecimal stkValPreTax) {
		this.stkValPreTax = stkValPreTax;
	}

	public String getDescptn() {
		return descptn;
	}

	public void setDescptn(String descptn) {
		this.descptn = descptn;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public Date getPreparedDt() {
		return preparedDt;
	}

	public void setPreparedDt(Date preparedDt) {
		this.preparedDt = preparedDt;
	}

	public Date getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}

	public Date getMergedDate() {
		return mergedDate;
	}

	public void setMergedDate(Date mergedDate) {
		this.mergedDate = mergedDate;
	}

	public Date getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTxGroup() {
		return txGroup;
	}

	public void setTxGroup(String txGroup) {
		this.txGroup = txGroup;
	}

	public String getTxType() {
		return txType;
	}

	public void setTxType(String txType) {
		this.txType = txType;
	}

	public Date getConflictDt() {
		return conflictDt;
	}

	public void setConflictDt(Date conflictDt) {
		this.conflictDt = conflictDt;
	}

	public CoreAmtOrPct getPrchPymtDscntType() {
		return prchPymtDscntType;
	}

	public void setPrchPymtDscntType(CoreAmtOrPct prchPymtDscntType) {
		this.prchPymtDscntType = prchPymtDscntType;
	}

	public BigDecimal getPrchPymtDscntPct() {
		return prchPymtDscntPct;
	}

	public void setPrchPymtDscntPct(BigDecimal prchPymtDscntPct) {
		this.prchPymtDscntPct = prchPymtDscntPct;
	}

	public BigDecimal getPrchPymtDscntAmt() {
		return prchPymtDscntAmt;
	}

	public void setPrchPymtDscntAmt(BigDecimal prchPymtDscntAmt) {
		this.prchPymtDscntAmt = prchPymtDscntAmt;
	}

	public CoreAmtOrPct getSlsPymtDscntType() {
		return slsPymtDscntType;
	}

	public void setSlsPymtDscntType(CoreAmtOrPct slsPymtDscntType) {
		this.slsPymtDscntType = slsPymtDscntType;
	}

	public BigDecimal getSlsPymtDscntPct() {
		return slsPymtDscntPct;
	}

	public void setSlsPymtDscntPct(BigDecimal slsPymtDscntPct) {
		this.slsPymtDscntPct = slsPymtDscntPct;
	}

	public BigDecimal getSlsPymtDscntAmt() {
		return slsPymtDscntAmt;
	}

	public void setSlsPymtDscntAmt(BigDecimal slsPymtDscntAmt) {
		this.slsPymtDscntAmt = slsPymtDscntAmt;
	}

	public String getBsnsPartner() {
		return bsnsPartner;
	}

	public void setBsnsPartner(String bsnsPartner) {
		this.bsnsPartner = bsnsPartner;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getBsnsPrtnrOU() {
		return bsnsPrtnrOU;
	}

	public void setBsnsPrtnrOU(String bsnsPrtnrOU) {
		this.bsnsPrtnrOU = bsnsPrtnrOU;
	}

	public BigDecimal getPrchNetPymtAmt() {
		return prchNetPymtAmt;
	}

	public void setPrchNetPymtAmt(BigDecimal prchNetPymtAmt) {
		this.prchNetPymtAmt = prchNetPymtAmt;
	}

	public BigDecimal getSlsNetPymtAmt() {
		return slsNetPymtAmt;
	}

	public void setSlsNetPymtAmt(BigDecimal slsNetPymtAmt) {
		this.slsNetPymtAmt = slsNetPymtAmt;
	}

	public String getDocNbr() {
		return docNbr;
	}

	public void setDocNbr(String docNbr) {
		this.docNbr = docNbr;
	}

	public String getBsnsPtnrName() {
		return bsnsPtnrName;
	}

	public void setBsnsPtnrName(String bsnsPtnrName) {
		this.bsnsPtnrName = bsnsPtnrName;
	}

	protected void normalize(){

		this.prchRstckgFeesPreTax=BigDecimalUtils.zeroIfNull(this.prchRstckgFeesPreTax);
		this.prchGrossPrcPreTax = BigDecimalUtils.zeroIfNull(this.prchGrossPrcPreTax);
		this.prchRebateAmt = BigDecimalUtils.zeroIfNull(this.prchRebateAmt);
		this.prchNetPrcPreTax=BigDecimalUtils.zeroIfNull(this.prchNetPrcPreTax);
		this.prchVatAmt=BigDecimalUtils.zeroIfNull(this.prchVatAmt);
		this.prchNetPrcTaxIncl=BigDecimalUtils.zeroIfNull(this.prchNetPrcTaxIncl);

		this.prchPymtDscntPct=BigDecimalUtils.zeroIfNull(this.prchPymtDscntPct);
		this.prchPymtDscntAmt=BigDecimalUtils.zeroIfNull(this.prchPymtDscntAmt);
		this.prchNetPymtAmt=BigDecimalUtils.zeroIfNull(this.prchNetPymtAmt);
		this.prchRdngDscntAmt = BigDecimalUtils.zeroIfNull(this.prchRdngDscntAmt);
		this.prchNetAmtToPay=BigDecimalUtils.zeroIfNull(this.prchNetAmtToPay);
		this.prchWrntyDays=BigDecimalUtils.zeroIfNull(this.prchWrntyDays);
		this.prchRtrnDays=BigDecimalUtils.zeroIfNull(this.prchRtrnDays);

		this.slsRstckgFeesPreTax = BigDecimalUtils.zeroIfNull(this.slsRstckgFeesPreTax);
		this.slsGrossPrcPreTax=BigDecimalUtils.zeroIfNull(this.slsGrossPrcPreTax);
		this.slsRebateAmt=BigDecimalUtils.zeroIfNull(this.slsRebateAmt);
		this.slsNetPrcPreTax=BigDecimalUtils.zeroIfNull(this.slsNetPrcPreTax);
		this.slsVatAmt = BigDecimalUtils.zeroIfNull(this.slsVatAmt);
		this.slsNetPrcTaxIncl=BigDecimalUtils.zeroIfNull(this.slsNetPrcTaxIncl);
		
		this.slsPymtDscntPct=BigDecimalUtils.zeroIfNull(this.slsPymtDscntPct);
		this.slsPymtDscntAmt=BigDecimalUtils.zeroIfNull(this.slsPymtDscntAmt);
		this.slsNetPymtAmt=BigDecimalUtils.zeroIfNull(this.slsNetPymtAmt);
		this.slsRdngDscntAmt=BigDecimalUtils.zeroIfNull(this.slsRdngDscntAmt);
		this.slsNetAmtToPay=BigDecimalUtils.zeroIfNull(this.slsNetAmtToPay);
		this.slsWrntyDays=BigDecimalUtils.zeroIfNull(this.slsWrntyDays);
		this.slsRtrnDays=BigDecimalUtils.zeroIfNull(this.slsRtrnDays);

		this.stkValPreTax=BigDecimalUtils.zeroIfNull(this.stkValPreTax);
	}
	
	public void addItemValue(CoreAbstBsnsItem item){
		this.prchRstckgFeesPreTax=BigDecimalUtils.sum(this.prchRstckgFeesPreTax, item.getPrchRstckgFeesPreTax());
		this.prchGrossPrcPreTax = BigDecimalUtils.sum(this.prchGrossPrcPreTax, item.getPrchGrossPrcPreTax());
		this.prchRebateAmt = BigDecimalUtils.sum(this.prchRebateAmt, item.getPrchRebateAmt());
		this.prchNetPrcPreTax=BigDecimalUtils.sum(this.prchNetPrcPreTax, item.getPrchNetPrcPreTax());
		this.prchVatAmt=BigDecimalUtils.sum(this.prchVatAmt, item.getPrchVatAmt());
		this.prchNetPrcTaxIncl=BigDecimalUtils.sum(this.prchNetPrcTaxIncl, item.getPrchNetPrcTaxIncl());

		this.slsRstckgFeesPreTax = BigDecimalUtils.sum(this.slsRstckgFeesPreTax, item.getSlsRstckgFeesPreTax());
		this.slsGrossPrcPreTax=BigDecimalUtils.sum(this.slsGrossPrcPreTax, item.getSlsGrossPrcPreTax());
		this.slsRebateAmt=BigDecimalUtils.sum(this.slsRebateAmt, item.getSlsRebateAmt());
		this.slsNetPrcPreTax=BigDecimalUtils.sum(this.slsNetPrcPreTax, item.getSlsNetPrcPreTax());
		this.slsVatAmt = BigDecimalUtils.sum(this.slsVatAmt, item.getSlsVatAmt());
		this.slsNetPrcTaxIncl=BigDecimalUtils.sum(this.slsNetPrcTaxIncl, item.getSlsNetPrcTaxIncl());

		this.stkValPreTax=BigDecimalUtils.sum(this.stkValPreTax, item.getStkValPreTax());
	}
	
	public void clearValues(){
		this.prchRstckgFeesPreTax=BigDecimal.ZERO;
		this.prchGrossPrcPreTax = BigDecimal.ZERO;
		this.prchRebateAmt = BigDecimal.ZERO;
		this.prchNetPrcPreTax=BigDecimal.ZERO;
		this.prchVatAmt=BigDecimal.ZERO;
		this.prchNetPrcTaxIncl=BigDecimal.ZERO;

		this.slsRstckgFeesPreTax = BigDecimal.ZERO;
		this.slsGrossPrcPreTax=BigDecimal.ZERO;
		this.slsRebateAmt=BigDecimal.ZERO;
		this.slsNetPrcPreTax=BigDecimal.ZERO;
		this.slsVatAmt = BigDecimal.ZERO;
		this.slsNetPrcTaxIncl=BigDecimal.ZERO;

		this.stkValPreTax=BigDecimal.ZERO;
	}	

	public void evlte(){
		normalize();
		if(this.prchPymtDscntType==null){
			if(!BigDecimalUtils.isNullOrZero(this.prchPymtDscntPct)){
				this.prchPymtDscntType=CoreAmtOrPct.PERCENT;
			} else if (!BigDecimalUtils.isNullOrZero(this.prchPymtDscntAmt)){
				this.prchPymtDscntType=CoreAmtOrPct.AMOUNT;
			} else {
				this.prchPymtDscntType=CoreAmtOrPct.PERCENT;
			}
		}
		if(CoreAmtOrPct.PERCENT.equals(this.prchPymtDscntType)){
			this.prchPymtDscntAmt = FinancialOps.amtFromPrct(this.prchNetPrcTaxIncl, this.prchPymtDscntPct, this.prchPrcCur);
		} else {
			this.prchPymtDscntPct = FinancialOps.prctFromAmt(this.prchNetPrcTaxIncl, this.prchPymtDscntAmt, this.prchPrcCur);
		}
		this.prchNetPymtAmt = FinancialOps.substract(this.prchNetPrcTaxIncl, this.prchPymtDscntAmt);
	
		if(this.slsPymtDscntType==null){
			if(!BigDecimalUtils.isNullOrZero(this.slsPymtDscntPct)){
				this.slsPymtDscntType=CoreAmtOrPct.PERCENT;
			} else if (!BigDecimalUtils.isNullOrZero(this.slsPymtDscntAmt)){
				this.slsPymtDscntType=CoreAmtOrPct.AMOUNT;
			} else {
				this.slsPymtDscntType=CoreAmtOrPct.PERCENT;
			}
		}
		if(CoreAmtOrPct.PERCENT.equals(this.slsPymtDscntType)){
			this.slsPymtDscntAmt = FinancialOps.amtFromPrct(this.slsNetPrcTaxIncl, this.slsPymtDscntPct, this.slsPrcCur);
		} else {
			this.slsPymtDscntPct = FinancialOps.prctFromAmt(this.slsNetPrcTaxIncl, this.slsPymtDscntAmt, this.slsPrcCur);
		}
		this.slsNetPymtAmt = FinancialOps.substract(this.slsNetPrcTaxIncl, this.slsPymtDscntAmt);
	}
	
	@Override
	protected String makeIdentif() {
		if(StringUtils.isBlank(identif)) throw new IllegalStateException("Identifier must be set before creation");
		return identif;
	}
	
}
