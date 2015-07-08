package org.adorsys.adcost.jpa;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.CoreAbstIdentifData;
import org.adorsys.adcore.jpa.CoreAmtOrPct;
import org.adorsys.adcore.utils.BigDecimalUtils;
import org.adorsys.javaext.description.Description;
import org.apache.commons.lang3.StringUtils;

/**
 * A statement item identifies a single assignment in a statement.
 * 
 * @author francis
 *
 */
@Entity
@Description("CstStmntItem_description")
public class CstStmntItem extends CoreAbstIdentifData {

	private static final long serialVersionUID = -8466036435151817125L;

	/*
	 * The identifier of the hosting statement.
	 */
	@Column
	@Description("CstStmntItem_stmntNbr_description")
	@NotNull
	private String stmntNbr;

	/*
	 * The identifier of this statement item
	 */
	@Column
	@Description("CstStmntItem_stmntItemNbr_description")
	@NotNull
	private String stmntItemNbr;
	
	/*
	 * The identifier of the source activity center.
	 */
	@Column
	@Description("CstStmntItem_srcCtrNbr_description")
	@NotNull
	private String srcCtrNbr;

	/*
	 * The identifier of the source bearer.
	 */
	@Column
	@Description("CstStmntItem_srcBrrNbr_description")
	@NotNull
	private String srcBrrNbr;
	
	/*
	 * The identifier of the activity center receiving this statement.
	 */
	@Column
	@Description("CstStmntItem_destCtrNbr_description")
	@NotNull
	private String destCtrNbr;

	/*
	 * The bearer receiving the this statement item
	 */
	@Column
	@Description("CstStmntItem_destBrrNbr_description")
	@NotNull
	private String destBrrNbr;

	/*
	 * The identifier of the article object of this statement. For example
	 * the identifier of the raw material consumed by the destination bearer.
	 */
	@Column
	@Description("CstStmntItem_artNbr_description")
	@NotNull
	private String artNbr;
	
	/*
	 * The identifier of the lot.
	 */
	@Column
	@Description("CstStmntItem_lotNbr_description")
	@NotNull
	private String lotNbr;

	/*
	 * THe designation of the article object of this statement item.
	 */
	@Column
	@Description("CstStmntItem_artName_description")
	@NotNull
	private String artName;

	/*
	 * The quantity to be affected to the destination bearer.
	 */
	@Column
	@Description("CstStmntItem_qtyBilled_description")
	private BigDecimal qtyBilled;

	/*
	 * The quantity really freely given or not billed ot the destination center.
	 */
	@Column
	@Description("CstStmntItem_qtyFree_description")
	private BigDecimal qtyFree;
	
	/*
	 * The quantity delivered to the destination bearer.
	 */
	@Column
	@Description("CstStmntItem_qtyToDeliver_description")
	private BigDecimal qtyToDeliver;

	/*
	 * The transfer price per unit before tax.
	 */
	@Column
	@Description("CstStmntItem_tppuPreTax_description")
	private BigDecimal tppuPreTax;

	/*
	 * The currency of the transfer price.
	 */
	@Column
	@Description("CstStmntItem_tppuCur_description")
	private String tppuCur;

	/*
	 * The gross transfer amount. This is the quantity billed 
	 * times the transfer price per unit before tax.
	 */
	@Column
	@Description("CstStmntItem_grossTAPreTax_description")
	private BigDecimal grossTAPreTax;

	/*
	 * The type of transfer rebate. Whether it is a percentage or a nominal amount.
	 */
	@Column
	@Description("CstStmntItem_rebateType_description")
	@NotNull
	@Enumerated(EnumType.STRING)
	private CoreAmtOrPct rebateType = CoreAmtOrPct.PERCENT;
	
	/*
	 * The transfer discount before tax. If the rebate type is percent,
	 * this amount is always calculated from the percentage value of rebatePct.
	 */
	@Column
	@Description("CstStmntItem_rebate_description")
	private BigDecimal rebate;
	
	/*
	 * The percentage of the transfer discount before tax. If the rebate type
	 * is amount, this value is derived from the nominal rebate value rebate.
	 */
	@Column
	@Description("CstStmntItem_rebatePct_description")
	private BigDecimal rebatePct;

	/*
	 * The transfer amount after rebate and before value added tax.
	 */
	@Column
	@Description("CstStmntItem_netTAPreTax_description")
	private BigDecimal netTAPreTax;

	/*
	 * The type of VAT. Whether it is a percentage or a nominal amount.
	 */
	@Column
	@Description("CstStmntItem_vatType_description")
	@NotNull
	@Enumerated(EnumType.STRING)
	private CoreAmtOrPct vatType = CoreAmtOrPct.PERCENT;
	
	/*
	 * The percentage of the value added tax. This will be derived
	 * if the the vatType is amount.
	 */
	@Column
	@Description("CstStmntItem_vatPct_description")
	private BigDecimal vatPct;

	/*
	 * The amount of the value added tax. This will be derived if the value 
	 * of the vatType is percent.
	 */
	@Column
	@Description("CstStmntItem_vatAmount_description")
	private BigDecimal vatAmount;

	/*
	 * The transfer amount tax included.
	 */
	@Column
	@Description("CstStmntItem_netTATaxIncl_description")
	private BigDecimal netTATaxIncl;	

	public void copyTo(CstStmntItem target) {	
		target.artName = artName;
		target.artNbr = artNbr;
		target.destBrrNbr = destBrrNbr;
		target.destCtrNbr = destCtrNbr;
		target.grossTAPreTax = grossTAPreTax;
		target.identif = identif;
		target.lotNbr = lotNbr;
		target.netTAPreTax = netTAPreTax;
		target.netTATaxIncl = netTATaxIncl;
		target.qtyBilled = qtyBilled;
		target.qtyToDeliver = qtyToDeliver;
		target.qtyFree = qtyFree;
		target.rebate = rebate;
		target.rebatePct = rebatePct;
		target.rebateType = rebateType;
		target.srcBrrNbr = srcBrrNbr;
		target.srcCtrNbr = srcCtrNbr;
		target.stmntItemNbr =stmntItemNbr;
		target.stmntNbr = stmntNbr;
		target.tppuCur = tppuCur;
		target.tppuPreTax = tppuPreTax;
		target.vatAmount = vatAmount;
		target.vatPct = vatPct;
		target.vatType = vatType;
	}
	
	public boolean contentEquals(CstStmntItem target) {
		if(!StringUtils.equals(target.artName,artName)) return false;
		if(!StringUtils.equals(target.artNbr,artNbr)) return false;
		if(!StringUtils.equals(target.destBrrNbr,destBrrNbr)) return false;	
		if(!StringUtils.equals(target.destCtrNbr,destCtrNbr)) return false;	
		if(!BigDecimalUtils.numericEquals(target.grossTAPreTax,grossTAPreTax)) return false;
		if(!StringUtils.equals(target.lotNbr,lotNbr)) return false;	
		if(!BigDecimalUtils.numericEquals(target.netTAPreTax,netTAPreTax)) return false;
		if(!BigDecimalUtils.numericEquals(target.qtyBilled,qtyBilled)) return false;
		if(!BigDecimalUtils.numericEquals(target.qtyToDeliver,qtyToDeliver)) return false;
		if(!BigDecimalUtils.numericEquals(target.qtyFree,qtyFree)) return false;
		if(!BigDecimalUtils.numericEquals(target.rebate,rebate)) return false;
		if(!BigDecimalUtils.numericEquals(target.rebatePct,rebatePct)) return false;
		if(target.rebateType!=rebateType) return false;
		if(!StringUtils.equals(target.srcBrrNbr,srcBrrNbr)) return false;	
		if(!StringUtils.equals(target.srcCtrNbr,srcCtrNbr)) return false;	
		if(!StringUtils.equals(target.stmntItemNbr,stmntItemNbr)) return false;	
		if(!StringUtils.equals(target.stmntNbr,stmntNbr)) return false;	
		if(!StringUtils.equals(target.tppuCur,tppuCur)) return false;	
		if(!BigDecimalUtils.numericEquals(target.tppuPreTax,tppuPreTax)) return false;
		if(!BigDecimalUtils.numericEquals(target.vatAmount,vatAmount)) return false;
		if(!BigDecimalUtils.numericEquals(target.vatPct,vatPct)) return false;
		if(target.vatType!=vatType) return false;
		return true;
	}

	public void evlte() {
		qtyBilled = BigDecimalUtils.zeroIfNull(qtyBilled);
		qtyFree = BigDecimalUtils.zeroIfNull(qtyFree);
		qtyToDeliver = BigDecimalUtils.sum(qtyBilled, qtyFree);
		
		tppuPreTax = BigDecimalUtils.zeroIfNull(tppuPreTax);
		grossTAPreTax = tppuPreTax.multiply(qtyBilled);
		
		if(CoreAmtOrPct.PERCENT==rebateType){
			rebatePct=BigDecimalUtils.zeroIfNull(rebatePct);
			rebate = BigDecimalUtils.basePercentOfRatePct(rebatePct, grossTAPreTax, RoundingMode.HALF_EVEN);
		} else {
			rebate = BigDecimalUtils.zeroIfNull(rebate);
			rebatePct = BigDecimalUtils.ratePercentOfPartFromBase(rebate, grossTAPreTax, RoundingMode.HALF_EVEN);
		}
		
		netTAPreTax = BigDecimalUtils.subs(grossTAPreTax, rebate);
				
		if(CoreAmtOrPct.PERCENT==vatType){
			vatPct=BigDecimalUtils.zeroIfNull(vatPct);
			vatAmount = BigDecimalUtils.basePercentOfRatePct(vatPct, netTAPreTax, RoundingMode.HALF_EVEN);
		} else {
			vatAmount = BigDecimalUtils.zeroIfNull(vatAmount);
			vatPct = BigDecimalUtils.ratePercentOfPartFromBase(vatAmount, netTAPreTax, RoundingMode.HALF_EVEN);
		}
		
		netTATaxIncl = BigDecimalUtils.sum(netTAPreTax, vatAmount);
	}

	@Override
	protected String makeIdentif() {
		return stmntNbr + "_" + stmntNbr;
	}

	public String getStmntNbr() {
		return stmntNbr;
	}

	public void setStmntNbr(String stmntNbr) {
		this.stmntNbr = stmntNbr;
	}

	public String getStmntItemNbr() {
		return stmntItemNbr;
	}

	public void setStmntItemNbr(String stmntItemNbr) {
		this.stmntItemNbr = stmntItemNbr;
	}

	public String getSrcCtrNbr() {
		return srcCtrNbr;
	}

	public void setSrcCtrNbr(String srcCtrNbr) {
		this.srcCtrNbr = srcCtrNbr;
	}

	public String getSrcBrrNbr() {
		return srcBrrNbr;
	}

	public void setSrcBrrNbr(String srcBrrNbr) {
		this.srcBrrNbr = srcBrrNbr;
	}

	public String getDestCtrNbr() {
		return destCtrNbr;
	}

	public void setDestCtrNbr(String destCtrNbr) {
		this.destCtrNbr = destCtrNbr;
	}

	public String getDestBrrNbr() {
		return destBrrNbr;
	}

	public void setDestBrrNbr(String destBrrNbr) {
		this.destBrrNbr = destBrrNbr;
	}

	public String getArtNbr() {
		return artNbr;
	}

	public void setArtNbr(String artNbr) {
		this.artNbr = artNbr;
	}

	public String getLotNbr() {
		return lotNbr;
	}

	public void setLotNbr(String lotNbr) {
		this.lotNbr = lotNbr;
	}

	public String getArtName() {
		return artName;
	}

	public void setArtName(String artName) {
		this.artName = artName;
	}

	public BigDecimal getQtyBilled() {
		return qtyBilled;
	}

	public void setQtyBilled(BigDecimal qtyBilled) {
		this.qtyBilled = qtyBilled;
	}

	public BigDecimal getQtyFree() {
		return qtyFree;
	}

	public void setQtyFree(BigDecimal qtyFree) {
		this.qtyFree = qtyFree;
	}

	public BigDecimal getQtyToDeliver() {
		return qtyToDeliver;
	}

	public void setQtyToDeliver(BigDecimal qtyToDeliver) {
		this.qtyToDeliver = qtyToDeliver;
	}

	public BigDecimal getTppuPreTax() {
		return tppuPreTax;
	}

	public void setTppuPreTax(BigDecimal tppuPreTax) {
		this.tppuPreTax = tppuPreTax;
	}

	public String getTppuCur() {
		return tppuCur;
	}

	public void setTppuCur(String tppuCur) {
		this.tppuCur = tppuCur;
	}

	public BigDecimal getGrossTAPreTax() {
		return grossTAPreTax;
	}

	public void setGrossTAPreTax(BigDecimal grossTAPreTax) {
		this.grossTAPreTax = grossTAPreTax;
	}

	public CoreAmtOrPct getRebateType() {
		return rebateType;
	}

	public void setRebateType(CoreAmtOrPct rebateType) {
		this.rebateType = rebateType;
	}

	public BigDecimal getRebate() {
		return rebate;
	}

	public void setRebate(BigDecimal rebate) {
		this.rebate = rebate;
	}

	public BigDecimal getRebatePct() {
		return rebatePct;
	}

	public void setRebatePct(BigDecimal rebatePct) {
		this.rebatePct = rebatePct;
	}

	public BigDecimal getNetTAPreTax() {
		return netTAPreTax;
	}

	public void setNetTAPreTax(BigDecimal netTAPreTax) {
		this.netTAPreTax = netTAPreTax;
	}

	public CoreAmtOrPct getVatType() {
		return vatType;
	}

	public void setVatType(CoreAmtOrPct vatType) {
		this.vatType = vatType;
	}

	public BigDecimal getVatPct() {
		return vatPct;
	}

	public void setVatPct(BigDecimal vatPct) {
		this.vatPct = vatPct;
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
}