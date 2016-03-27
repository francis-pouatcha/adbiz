package org.adorsys.adcatal.jpa;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;

@Entity
@Description("CatalPicMapping_description")
public class CatalPicMapping extends CoreAbstIdentifObject {

	private static final long serialVersionUID = 2679486739942764215L;

	@Column
	@Description("CatalPicMapping_artIdentif_description")
	@NotNull
	private String artIdentif;

	@Column
	@Description("CatalPicMapping_code_description")
	@NotNull
	private String code;

	/*
	 * This is the key of the enumeration from CatalCipOrigine
	 */
	@Column
	@Description("CatalPicMapping_codeOrigin_description")
	@NotNull
	private String codeOrigin;
	
	@Column
	@Description("CatalPicMapping_bpName_description")
	@NotNull
	private String bpName;

	@Column
	@Description("CatalPicMapping_bpIdentif_description")
	@NotNull
	private String bpIdentif;

	@Column
	@Description("CatalArtManufSupp_unitPrice_description")
	private BigDecimal unitPrice;

	@Column
	@Description("CatalArtManufSupp_currIso3_description")
	private String currIso3;

	@Column
	@Description("CatalArtManufSupp_vatRate_description")
	private BigDecimal vatRate;
	
	@Column
	@Description("CatalArtManufSupp_maxDisctRate_description")
	private BigDecimal maxDisctRate;

	@Column
	@Description("CatalArtManufSupp_warrantyMonths_description")
	private BigDecimal warrantyMonths;

	@Column
	@Description("CatalArtManufSupp_returnDays_description")
	private BigDecimal returnDays;
	
	
	@Column
	@Description("CatalPicMapping_addInfo_description")
	@Size(max=256)
	private String addInfo;
	
	public String getArtIdentif() {
		return this.artIdentif;
	}

	public void setArtIdentif(final String artIdentif) {
		this.artIdentif = artIdentif;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(final String code) {
		this.code = code;
	}

	public String getCodeOrigin() {
		return this.codeOrigin;
	}

	public void setCodeOrigin(final String codeOrigin) {
		this.codeOrigin = codeOrigin;
	}

	public String getAddInfo() {
		return this.addInfo;
	}

	public void setAddInfo(final String addInfo) {
		this.addInfo = addInfo;
	}

	public String getBpName() {
		return bpName;
	}

	public void setBpName(String bpName) {
		this.bpName = bpName;
	}

	public String getBpIdentif() {
		return bpIdentif;
	}

	public void setBpIdentif(String bpIdentif) {
		this.bpIdentif = bpIdentif;
	}

	public BigDecimal getWarrantyMonths() {
		return warrantyMonths;
	}

	public void setWarrantyMonths(BigDecimal warrantyMonths) {
		this.warrantyMonths = warrantyMonths;
	}

	public BigDecimal getReturnDays() {
		return returnDays;
	}

	public void setReturnDays(BigDecimal returnDays) {
		this.returnDays = returnDays;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getCurrIso3() {
		return currIso3;
	}

	public void setCurrIso3(String currIso3) {
		this.currIso3 = currIso3;
	}

	public BigDecimal getVatRate() {
		return vatRate;
	}

	public void setVatRate(BigDecimal vatRate) {
		this.vatRate = vatRate;
	}

	public BigDecimal getMaxDisctRate() {
		return maxDisctRate;
	}

	public void setMaxDisctRate(BigDecimal maxDisctRate) {
		this.maxDisctRate = maxDisctRate;
	}
	

	@Override
	public void prePersist() {
		setCntnrIdentif(artIdentif);
		super.prePersist();
	}

	@Override
	protected String makeIdentif() {
		return artIdentif + "_" + code + "_" + codeOrigin + "_" + bpIdentif;
	}
}