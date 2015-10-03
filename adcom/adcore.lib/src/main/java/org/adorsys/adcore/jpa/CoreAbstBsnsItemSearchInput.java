package org.adorsys.adcore.jpa;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Holds an entity and corresponding field descriptions for a search by example
 * call.
 * 
 * @author francis pouatcha
 *
 */
public class CoreAbstBsnsItemSearchInput<E extends CoreAbstBsnsItem> extends CoreAbstIdentifObjectSearchInput<E>{

	private Date acsngDtFrom;

	private Date acsngDtTo;

	private Boolean a2z;

	private Date expirDtFrom;

	private Date expirDtTo;

	private BigDecimal prchUnitPrcPreTaxFrom;
	private BigDecimal prchUnitPrcPreTaxTo;

	private BigDecimal prchRstckgFeesPrctFrom;
	private BigDecimal prchRstckgFeesPrctTo;

	private BigDecimal prchRstckgUnitFeesPreTaxFrom;
	private BigDecimal prchRstckgUnitFeesPreTaxTo;

	private BigDecimal prchRstckgFeesPreTaxFrom;
	private BigDecimal prchRstckgFeesPreTaxTo;
	
	private BigDecimal prchGrossPrcPreTaxFrom;
	private BigDecimal prchGrossPrcPreTaxTo;
	
	private BigDecimal prchRebateAmtFrom;
	private BigDecimal prchRebateAmtTo;

	private BigDecimal prchRebatePctFrom;
	private BigDecimal prchRebatePctTo;
	
	private BigDecimal prchNetPrcPreTaxFrom;
	private BigDecimal prchNetPrcPreTaxTo;
	
	private BigDecimal prchVatPctFrom;
	private BigDecimal prchVatPctTo;
	
	private BigDecimal prchVatAmtFrom;
	private BigDecimal prchVatAmtTo;
	
	private BigDecimal prchNetPrcTaxInclFrom;
	private BigDecimal prchNetPrcTaxInclTo;
	
	private BigDecimal prchWrntyDaysFrom;
	private BigDecimal prchWrntyDaysTo;
	
	private BigDecimal prchRtrnDaysFrom;
	private BigDecimal prchRtrnDaysTo;
	
	private Date prchWrntyDtFrom;
	private Date prchWrntyDtTo;
	
	private Date prchRtrnDtFrom;
	private Date prchRtrnDtTo;
	
	private BigDecimal slsUnitPrcPreTaxFrom;
	private BigDecimal slsUnitPrcPreTaxTo;

	private BigDecimal slsRstckgFeesPrctFrom;
	private BigDecimal slsRstckgFeesPrctTo;

	private BigDecimal slsRstckgUnitFeesPreTaxFrom;
	private BigDecimal slsRstckgUnitFeesPreTaxTo;

	private BigDecimal slsRstckgFeesPreTaxFrom;
	private BigDecimal slsRstckgFeesPreTaxTo;
	
	private BigDecimal slsGrossPrcPreTaxFrom;
	private BigDecimal slsGrossPrcPreTaxTo;
	
	private BigDecimal slsRebateAmtFrom;
	private BigDecimal slsRebateAmtTo;

	private BigDecimal slsRebatePctFrom;
	private BigDecimal slsRebatePctTo;
	
	private BigDecimal slsNetPrcPreTaxFrom;
	private BigDecimal slsNetPrcPreTaxTo;
	
	private BigDecimal slsVatPctFrom;
	private BigDecimal slsVatPctTo;
	
	private BigDecimal slsVatAmtFrom;
	private BigDecimal slsVatAmtTo;
	
	private BigDecimal slsNetPrcTaxInclFrom;
	private BigDecimal slsNetPrcTaxInclTo;
	
	private BigDecimal slsWrntyDaysFrom;
	private BigDecimal slsWrntyDaysTo;
	
	private BigDecimal slsRtrnDaysFrom;
	private BigDecimal slsRtrnDaysTo;
	
	private Date slsWrntyDtFrom;
	private Date slsWrntyDtTo;
	
	private Date slsRtrnDtFrom;
	private Date slsRtrnDtTo;

	private BigDecimal stkUnitValPreTaxFrom;
	private BigDecimal stkUnitValPreTaxTo;
	
	private BigDecimal stkValPreTaxFrom;
	private BigDecimal stkValPreTaxTo;
	
	private Date conflictDtFrom;
	private Date conflictDtTo;
	
	private Date disabledDtFrom;
	private Date disabledDtTo;

	public Date getAcsngDtFrom() {
		return acsngDtFrom;
	}

	public void setAcsngDtFrom(Date acsngDtFrom) {
		this.acsngDtFrom = acsngDtFrom;
	}

	public Date getAcsngDtTo() {
		return acsngDtTo;
	}

	public void setAcsngDtTo(Date acsngDtTo) {
		this.acsngDtTo = acsngDtTo;
	}

	public Boolean getA2z() {
		return a2z;
	}

	public void setA2z(Boolean a2z) {
		this.a2z = a2z;
	}

	public Date getExpirDtFrom() {
		return expirDtFrom;
	}

	public void setExpirDtFrom(Date expirDtFrom) {
		this.expirDtFrom = expirDtFrom;
	}

	public Date getExpirDtTo() {
		return expirDtTo;
	}

	public void setExpirDtTo(Date expirDtTo) {
		this.expirDtTo = expirDtTo;
	}

	public BigDecimal getPrchUnitPrcPreTaxFrom() {
		return prchUnitPrcPreTaxFrom;
	}

	public void setPrchUnitPrcPreTaxFrom(BigDecimal prchUnitPrcPreTaxFrom) {
		this.prchUnitPrcPreTaxFrom = prchUnitPrcPreTaxFrom;
	}

	public BigDecimal getPrchUnitPrcPreTaxTo() {
		return prchUnitPrcPreTaxTo;
	}

	public void setPrchUnitPrcPreTaxTo(BigDecimal prchUnitPrcPreTaxTo) {
		this.prchUnitPrcPreTaxTo = prchUnitPrcPreTaxTo;
	}

	public BigDecimal getPrchRstckgFeesPrctFrom() {
		return prchRstckgFeesPrctFrom;
	}

	public void setPrchRstckgFeesPrctFrom(BigDecimal prchRstckgFeesPrctFrom) {
		this.prchRstckgFeesPrctFrom = prchRstckgFeesPrctFrom;
	}

	public BigDecimal getPrchRstckgFeesPrctTo() {
		return prchRstckgFeesPrctTo;
	}

	public void setPrchRstckgFeesPrctTo(BigDecimal prchRstckgFeesPrctTo) {
		this.prchRstckgFeesPrctTo = prchRstckgFeesPrctTo;
	}

	public BigDecimal getPrchRstckgUnitFeesPreTaxFrom() {
		return prchRstckgUnitFeesPreTaxFrom;
	}

	public void setPrchRstckgUnitFeesPreTaxFrom(
			BigDecimal prchRstckgUnitFeesPreTaxFrom) {
		this.prchRstckgUnitFeesPreTaxFrom = prchRstckgUnitFeesPreTaxFrom;
	}

	public BigDecimal getPrchRstckgUnitFeesPreTaxTo() {
		return prchRstckgUnitFeesPreTaxTo;
	}

	public void setPrchRstckgUnitFeesPreTaxTo(BigDecimal prchRstckgUnitFeesPreTaxTo) {
		this.prchRstckgUnitFeesPreTaxTo = prchRstckgUnitFeesPreTaxTo;
	}

	public BigDecimal getPrchRstckgFeesPreTaxFrom() {
		return prchRstckgFeesPreTaxFrom;
	}

	public void setPrchRstckgFeesPreTaxFrom(BigDecimal prchRstckgFeesPreTaxFrom) {
		this.prchRstckgFeesPreTaxFrom = prchRstckgFeesPreTaxFrom;
	}

	public BigDecimal getPrchRstckgFeesPreTaxTo() {
		return prchRstckgFeesPreTaxTo;
	}

	public void setPrchRstckgFeesPreTaxTo(BigDecimal prchRstckgFeesPreTaxTo) {
		this.prchRstckgFeesPreTaxTo = prchRstckgFeesPreTaxTo;
	}

	public BigDecimal getPrchGrossPrcPreTaxFrom() {
		return prchGrossPrcPreTaxFrom;
	}

	public void setPrchGrossPrcPreTaxFrom(BigDecimal prchGrossPrcPreTaxFrom) {
		this.prchGrossPrcPreTaxFrom = prchGrossPrcPreTaxFrom;
	}

	public BigDecimal getPrchGrossPrcPreTaxTo() {
		return prchGrossPrcPreTaxTo;
	}

	public void setPrchGrossPrcPreTaxTo(BigDecimal prchGrossPrcPreTaxTo) {
		this.prchGrossPrcPreTaxTo = prchGrossPrcPreTaxTo;
	}

	public BigDecimal getPrchRebateAmtFrom() {
		return prchRebateAmtFrom;
	}

	public void setPrchRebateAmtFrom(BigDecimal prchRebateAmtFrom) {
		this.prchRebateAmtFrom = prchRebateAmtFrom;
	}

	public BigDecimal getPrchRebateAmtTo() {
		return prchRebateAmtTo;
	}

	public void setPrchRebateAmtTo(BigDecimal prchRebateAmtTo) {
		this.prchRebateAmtTo = prchRebateAmtTo;
	}

	public BigDecimal getPrchRebatePctFrom() {
		return prchRebatePctFrom;
	}

	public void setPrchRebatePctFrom(BigDecimal prchRebatePctFrom) {
		this.prchRebatePctFrom = prchRebatePctFrom;
	}

	public BigDecimal getPrchRebatePctTo() {
		return prchRebatePctTo;
	}

	public void setPrchRebatePctTo(BigDecimal prchRebatePctTo) {
		this.prchRebatePctTo = prchRebatePctTo;
	}

	public BigDecimal getPrchNetPrcPreTaxFrom() {
		return prchNetPrcPreTaxFrom;
	}

	public void setPrchNetPrcPreTaxFrom(BigDecimal prchNetPrcPreTaxFrom) {
		this.prchNetPrcPreTaxFrom = prchNetPrcPreTaxFrom;
	}

	public BigDecimal getPrchNetPrcPreTaxTo() {
		return prchNetPrcPreTaxTo;
	}

	public void setPrchNetPrcPreTaxTo(BigDecimal prchNetPrcPreTaxTo) {
		this.prchNetPrcPreTaxTo = prchNetPrcPreTaxTo;
	}

	public BigDecimal getPrchVatPctFrom() {
		return prchVatPctFrom;
	}

	public void setPrchVatPctFrom(BigDecimal prchVatPctFrom) {
		this.prchVatPctFrom = prchVatPctFrom;
	}

	public BigDecimal getPrchVatPctTo() {
		return prchVatPctTo;
	}

	public void setPrchVatPctTo(BigDecimal prchVatPctTo) {
		this.prchVatPctTo = prchVatPctTo;
	}

	public BigDecimal getPrchVatAmtFrom() {
		return prchVatAmtFrom;
	}

	public void setPrchVatAmtFrom(BigDecimal prchVatAmtFrom) {
		this.prchVatAmtFrom = prchVatAmtFrom;
	}

	public BigDecimal getPrchVatAmtTo() {
		return prchVatAmtTo;
	}

	public void setPrchVatAmtTo(BigDecimal prchVatAmtTo) {
		this.prchVatAmtTo = prchVatAmtTo;
	}

	public BigDecimal getPrchNetPrcTaxInclFrom() {
		return prchNetPrcTaxInclFrom;
	}

	public void setPrchNetPrcTaxInclFrom(BigDecimal prchNetPrcTaxInclFrom) {
		this.prchNetPrcTaxInclFrom = prchNetPrcTaxInclFrom;
	}

	public BigDecimal getPrchNetPrcTaxInclTo() {
		return prchNetPrcTaxInclTo;
	}

	public void setPrchNetPrcTaxInclTo(BigDecimal prchNetPrcTaxInclTo) {
		this.prchNetPrcTaxInclTo = prchNetPrcTaxInclTo;
	}

	public BigDecimal getPrchWrntyDaysFrom() {
		return prchWrntyDaysFrom;
	}

	public void setPrchWrntyDaysFrom(BigDecimal prchWrntyDaysFrom) {
		this.prchWrntyDaysFrom = prchWrntyDaysFrom;
	}

	public BigDecimal getPrchWrntyDaysTo() {
		return prchWrntyDaysTo;
	}

	public void setPrchWrntyDaysTo(BigDecimal prchWrntyDaysTo) {
		this.prchWrntyDaysTo = prchWrntyDaysTo;
	}

	public BigDecimal getPrchRtrnDaysFrom() {
		return prchRtrnDaysFrom;
	}

	public void setPrchRtrnDaysFrom(BigDecimal prchRtrnDaysFrom) {
		this.prchRtrnDaysFrom = prchRtrnDaysFrom;
	}

	public BigDecimal getPrchRtrnDaysTo() {
		return prchRtrnDaysTo;
	}

	public void setPrchRtrnDaysTo(BigDecimal prchRtrnDaysTo) {
		this.prchRtrnDaysTo = prchRtrnDaysTo;
	}

	public Date getPrchWrntyDtFrom() {
		return prchWrntyDtFrom;
	}

	public void setPrchWrntyDtFrom(Date prchWrntyDtFrom) {
		this.prchWrntyDtFrom = prchWrntyDtFrom;
	}

	public Date getPrchWrntyDtTo() {
		return prchWrntyDtTo;
	}

	public void setPrchWrntyDtTo(Date prchWrntyDtTo) {
		this.prchWrntyDtTo = prchWrntyDtTo;
	}

	public Date getPrchRtrnDtFrom() {
		return prchRtrnDtFrom;
	}

	public void setPrchRtrnDtFrom(Date prchRtrnDtFrom) {
		this.prchRtrnDtFrom = prchRtrnDtFrom;
	}

	public Date getPrchRtrnDtTo() {
		return prchRtrnDtTo;
	}

	public void setPrchRtrnDtTo(Date prchRtrnDtTo) {
		this.prchRtrnDtTo = prchRtrnDtTo;
	}

	public BigDecimal getSlsUnitPrcPreTaxFrom() {
		return slsUnitPrcPreTaxFrom;
	}

	public void setSlsUnitPrcPreTaxFrom(BigDecimal slsUnitPrcPreTaxFrom) {
		this.slsUnitPrcPreTaxFrom = slsUnitPrcPreTaxFrom;
	}

	public BigDecimal getSlsUnitPrcPreTaxTo() {
		return slsUnitPrcPreTaxTo;
	}

	public void setSlsUnitPrcPreTaxTo(BigDecimal slsUnitPrcPreTaxTo) {
		this.slsUnitPrcPreTaxTo = slsUnitPrcPreTaxTo;
	}

	public BigDecimal getSlsRstckgFeesPrctFrom() {
		return slsRstckgFeesPrctFrom;
	}

	public void setSlsRstckgFeesPrctFrom(BigDecimal slsRstckgFeesPrctFrom) {
		this.slsRstckgFeesPrctFrom = slsRstckgFeesPrctFrom;
	}

	public BigDecimal getSlsRstckgFeesPrctTo() {
		return slsRstckgFeesPrctTo;
	}

	public void setSlsRstckgFeesPrctTo(BigDecimal slsRstckgFeesPrctTo) {
		this.slsRstckgFeesPrctTo = slsRstckgFeesPrctTo;
	}

	public BigDecimal getSlsRstckgUnitFeesPreTaxFrom() {
		return slsRstckgUnitFeesPreTaxFrom;
	}

	public void setSlsRstckgUnitFeesPreTaxFrom(
			BigDecimal slsRstckgUnitFeesPreTaxFrom) {
		this.slsRstckgUnitFeesPreTaxFrom = slsRstckgUnitFeesPreTaxFrom;
	}

	public BigDecimal getSlsRstckgUnitFeesPreTaxTo() {
		return slsRstckgUnitFeesPreTaxTo;
	}

	public void setSlsRstckgUnitFeesPreTaxTo(BigDecimal slsRstckgUnitFeesPreTaxTo) {
		this.slsRstckgUnitFeesPreTaxTo = slsRstckgUnitFeesPreTaxTo;
	}

	public BigDecimal getSlsRstckgFeesPreTaxFrom() {
		return slsRstckgFeesPreTaxFrom;
	}

	public void setSlsRstckgFeesPreTaxFrom(BigDecimal slsRstckgFeesPreTaxFrom) {
		this.slsRstckgFeesPreTaxFrom = slsRstckgFeesPreTaxFrom;
	}

	public BigDecimal getSlsRstckgFeesPreTaxTo() {
		return slsRstckgFeesPreTaxTo;
	}

	public void setSlsRstckgFeesPreTaxTo(BigDecimal slsRstckgFeesPreTaxTo) {
		this.slsRstckgFeesPreTaxTo = slsRstckgFeesPreTaxTo;
	}

	public BigDecimal getSlsGrossPrcPreTaxFrom() {
		return slsGrossPrcPreTaxFrom;
	}

	public void setSlsGrossPrcPreTaxFrom(BigDecimal slsGrossPrcPreTaxFrom) {
		this.slsGrossPrcPreTaxFrom = slsGrossPrcPreTaxFrom;
	}

	public BigDecimal getSlsGrossPrcPreTaxTo() {
		return slsGrossPrcPreTaxTo;
	}

	public void setSlsGrossPrcPreTaxTo(BigDecimal slsGrossPrcPreTaxTo) {
		this.slsGrossPrcPreTaxTo = slsGrossPrcPreTaxTo;
	}

	public BigDecimal getSlsRebateAmtFrom() {
		return slsRebateAmtFrom;
	}

	public void setSlsRebateAmtFrom(BigDecimal slsRebateAmtFrom) {
		this.slsRebateAmtFrom = slsRebateAmtFrom;
	}

	public BigDecimal getSlsRebateAmtTo() {
		return slsRebateAmtTo;
	}

	public void setSlsRebateAmtTo(BigDecimal slsRebateAmtTo) {
		this.slsRebateAmtTo = slsRebateAmtTo;
	}

	public BigDecimal getSlsRebatePctFrom() {
		return slsRebatePctFrom;
	}

	public void setSlsRebatePctFrom(BigDecimal slsRebatePctFrom) {
		this.slsRebatePctFrom = slsRebatePctFrom;
	}

	public BigDecimal getSlsRebatePctTo() {
		return slsRebatePctTo;
	}

	public void setSlsRebatePctTo(BigDecimal slsRebatePctTo) {
		this.slsRebatePctTo = slsRebatePctTo;
	}

	public BigDecimal getSlsNetPrcPreTaxFrom() {
		return slsNetPrcPreTaxFrom;
	}

	public void setSlsNetPrcPreTaxFrom(BigDecimal slsNetPrcPreTaxFrom) {
		this.slsNetPrcPreTaxFrom = slsNetPrcPreTaxFrom;
	}

	public BigDecimal getSlsNetPrcPreTaxTo() {
		return slsNetPrcPreTaxTo;
	}

	public void setSlsNetPrcPreTaxTo(BigDecimal slsNetPrcPreTaxTo) {
		this.slsNetPrcPreTaxTo = slsNetPrcPreTaxTo;
	}

	public BigDecimal getSlsVatPctFrom() {
		return slsVatPctFrom;
	}

	public void setSlsVatPctFrom(BigDecimal slsVatPctFrom) {
		this.slsVatPctFrom = slsVatPctFrom;
	}

	public BigDecimal getSlsVatPctTo() {
		return slsVatPctTo;
	}

	public void setSlsVatPctTo(BigDecimal slsVatPctTo) {
		this.slsVatPctTo = slsVatPctTo;
	}

	public BigDecimal getSlsVatAmtFrom() {
		return slsVatAmtFrom;
	}

	public void setSlsVatAmtFrom(BigDecimal slsVatAmtFrom) {
		this.slsVatAmtFrom = slsVatAmtFrom;
	}

	public BigDecimal getSlsVatAmtTo() {
		return slsVatAmtTo;
	}

	public void setSlsVatAmtTo(BigDecimal slsVatAmtTo) {
		this.slsVatAmtTo = slsVatAmtTo;
	}

	public BigDecimal getSlsNetPrcTaxInclFrom() {
		return slsNetPrcTaxInclFrom;
	}

	public void setSlsNetPrcTaxInclFrom(BigDecimal slsNetPrcTaxInclFrom) {
		this.slsNetPrcTaxInclFrom = slsNetPrcTaxInclFrom;
	}

	public BigDecimal getSlsNetPrcTaxInclTo() {
		return slsNetPrcTaxInclTo;
	}

	public void setSlsNetPrcTaxInclTo(BigDecimal slsNetPrcTaxInclTo) {
		this.slsNetPrcTaxInclTo = slsNetPrcTaxInclTo;
	}

	public BigDecimal getSlsWrntyDaysFrom() {
		return slsWrntyDaysFrom;
	}

	public void setSlsWrntyDaysFrom(BigDecimal slsWrntyDaysFrom) {
		this.slsWrntyDaysFrom = slsWrntyDaysFrom;
	}

	public BigDecimal getSlsWrntyDaysTo() {
		return slsWrntyDaysTo;
	}

	public void setSlsWrntyDaysTo(BigDecimal slsWrntyDaysTo) {
		this.slsWrntyDaysTo = slsWrntyDaysTo;
	}

	public BigDecimal getSlsRtrnDaysFrom() {
		return slsRtrnDaysFrom;
	}

	public void setSlsRtrnDaysFrom(BigDecimal slsRtrnDaysFrom) {
		this.slsRtrnDaysFrom = slsRtrnDaysFrom;
	}

	public BigDecimal getSlsRtrnDaysTo() {
		return slsRtrnDaysTo;
	}

	public void setSlsRtrnDaysTo(BigDecimal slsRtrnDaysTo) {
		this.slsRtrnDaysTo = slsRtrnDaysTo;
	}

	public Date getSlsWrntyDtFrom() {
		return slsWrntyDtFrom;
	}

	public void setSlsWrntyDtFrom(Date slsWrntyDtFrom) {
		this.slsWrntyDtFrom = slsWrntyDtFrom;
	}

	public Date getSlsWrntyDtTo() {
		return slsWrntyDtTo;
	}

	public void setSlsWrntyDtTo(Date slsWrntyDtTo) {
		this.slsWrntyDtTo = slsWrntyDtTo;
	}

	public Date getSlsRtrnDtFrom() {
		return slsRtrnDtFrom;
	}

	public void setSlsRtrnDtFrom(Date slsRtrnDtFrom) {
		this.slsRtrnDtFrom = slsRtrnDtFrom;
	}

	public Date getSlsRtrnDtTo() {
		return slsRtrnDtTo;
	}

	public void setSlsRtrnDtTo(Date slsRtrnDtTo) {
		this.slsRtrnDtTo = slsRtrnDtTo;
	}

	public BigDecimal getStkUnitValPreTaxFrom() {
		return stkUnitValPreTaxFrom;
	}

	public void setStkUnitValPreTaxFrom(BigDecimal stkUnitValPreTaxFrom) {
		this.stkUnitValPreTaxFrom = stkUnitValPreTaxFrom;
	}

	public BigDecimal getStkUnitValPreTaxTo() {
		return stkUnitValPreTaxTo;
	}

	public void setStkUnitValPreTaxTo(BigDecimal stkUnitValPreTaxTo) {
		this.stkUnitValPreTaxTo = stkUnitValPreTaxTo;
	}

	public BigDecimal getStkValPreTaxFrom() {
		return stkValPreTaxFrom;
	}

	public void setStkValPreTaxFrom(BigDecimal stkValPreTaxFrom) {
		this.stkValPreTaxFrom = stkValPreTaxFrom;
	}

	public BigDecimal getStkValPreTaxTo() {
		return stkValPreTaxTo;
	}

	public void setStkValPreTaxTo(BigDecimal stkValPreTaxTo) {
		this.stkValPreTaxTo = stkValPreTaxTo;
	}

	public Date getConflictDtFrom() {
		return conflictDtFrom;
	}

	public void setConflictDtFrom(Date conflictDtFrom) {
		this.conflictDtFrom = conflictDtFrom;
	}

	public Date getConflictDtTo() {
		return conflictDtTo;
	}

	public void setConflictDtTo(Date conflictDtTo) {
		this.conflictDtTo = conflictDtTo;
	}

	public Date getDisabledDtFrom() {
		return disabledDtFrom;
	}

	public void setDisabledDtFrom(Date disabledDtFrom) {
		this.disabledDtFrom = disabledDtFrom;
	}

	public Date getDisabledDtTo() {
		return disabledDtTo;
	}

	public void setDisabledDtTo(Date disabledDtTo) {
		this.disabledDtTo = disabledDtTo;
	}
}
