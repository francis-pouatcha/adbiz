package org.adorsys.adcatal.jpa;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;

@MappedSuperclass
@Description("CatalArticle_description")
public abstract class CatalAbstractArticle extends CoreAbstIdentifObject {

	private static final long serialVersionUID = -2884028781983739994L;

	@Column
	@Description("CatalArticle_active_description")
	private Boolean active;

	@Column
	@Description("CatalArticle_authorizedSale_description")
	private Boolean authorizedSale;

	@Column
	@Description("CatalArticle_sppu_description")
	private BigDecimal sppu;
	
	@Column
	@Description("CatalArticle_pppu_description")
	private BigDecimal pppu;

	@Column
	@Description("CatalArticle_sppuCurrIso3_description")
	private String sppuCurrIso3;

	@Column
	@Description("CatalArticle_supplier_description")
	private String supplier;
	
	
	@Column
	@Description("CatalArticle_maxDisctRate_description")
	private BigDecimal maxDisctRate;

	@Column
	@Description("CatalArticle_maxStockQty_description")
	private BigDecimal maxStockQty;

	@Column
	@Description("CatalArticle_minStockQty_description")
	private BigDecimal minStockQty;
	
	@Column
	@Description("CatalArticle_vatRate_description")
	private BigDecimal vatRate;

	@Column
	@Description("CatalArticle_pic_description")
	@NotNull
	private String lotMgtScheme = "FiFo";

	@Column
	@Description("CatalArticle_manageLot_description")
	@NotNull
	private Boolean mngByLot = Boolean.TRUE;

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(final Boolean active) {
		this.active = active;
	}

	public Boolean getAuthorizedSale() {
		return this.authorizedSale;
	}

	public void setAuthorizedSale(final Boolean authorizedSale) {
		this.authorizedSale = authorizedSale;
	}

	public BigDecimal getSppu() {
		return this.sppu;
	}

	public void setSppu(final BigDecimal sppu) {
		this.sppu = sppu;
	}

	public String getSppuCurrIso3() {
		return this.sppuCurrIso3;
	}

	public void setSppuCurrIso3(final String sppuCurrIso3) {
		this.sppuCurrIso3 = sppuCurrIso3;
	}

	public BigDecimal getVatRate() {
		return this.vatRate;
	}

	public void setVatRate(final BigDecimal vatRate) {
		this.vatRate = vatRate;
	}
	
	public BigDecimal getMaxDisctRate() {
		return maxDisctRate;
	}

	public void setMaxDisctRate(BigDecimal maxDisctRate) {
		this.maxDisctRate = maxDisctRate;
	}

	public BigDecimal getMaxStockQty() {
		return maxStockQty;
	}

	public void setMaxStockQty(BigDecimal maxStockQty) {
		this.maxStockQty = maxStockQty;
	}

	public BigDecimal getMinStockQty() {
		return minStockQty;
	}

	public void setMinStockQty(BigDecimal minStockQty) {
		this.minStockQty = minStockQty;
	}

	public String getLotMgtScheme() {
		return lotMgtScheme;
	}

	public void setLotMgtScheme(String lotMgtScheme) {
		this.lotMgtScheme = lotMgtScheme;
	}

	public BigDecimal getPppu() {
		return pppu;
	}

	public void setPppu(BigDecimal pppu) {
		this.pppu = pppu;
	}

	public Boolean getMngByLot() {
		return mngByLot;
	}

	public void setMngByLot(Boolean mngByLot) {
		this.mngByLot = mngByLot;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	@Override
	protected String makeIdentif() {
		throw new IllegalStateException("Identifier is supposed to be set before persist.");
	}
	
}