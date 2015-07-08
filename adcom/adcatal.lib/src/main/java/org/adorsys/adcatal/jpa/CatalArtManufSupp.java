package org.adorsys.adcatal.jpa;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;

@Entity
@Description("CatalArtManufSupp_description")
public class CatalArtManufSupp extends CoreAbstIdentifObject {

	private static final long serialVersionUID = -7351661009522560614L;

	@Column
	@Description("CatalArtManufSupp_artIdentif_description")
	@NotNull
	private String artIdentif;

	@Column
	@Description("CatalArtManufSupp_msIdentif_description")
	@NotNull
	private String msIdentif;

	@Column
	@Description("CatalArtManufSupp_msType_description")
	private String msType;
	
	/*
	 * Titlee value of msType
	 */
	@Transient
	@Description("CatalArtManufSupp_msType_description.title")
	private String msTypeName;

	/*
	 * Text value of msType
	 */
	@Transient
	@Description("CatalArtManufSupp_msType_description.text")
	private String msTypeDescription;

	@Column
	@Description("CatalArtManufSupp_warrantyMonths_description")
	private BigDecimal warrantyMonths;

	@Column
	@Description("CatalArtManufSupp_returnDays_description")
	private BigDecimal returnDays;

	@Column
	@Description("CatalArtManufSupp_pppu_description")
	private BigDecimal pppu;

	public String getMsTypeName() {
		return msTypeName;
	}

	public void setMsTypeName(String msTypeName) {
		this.msTypeName = msTypeName;
	}

	public String getMsTypeDescription() {
		return msTypeDescription;
	}

	public void setMsTypeDescription(String msTypeDescription) {
		this.msTypeDescription = msTypeDescription;
	}

	@Column
	@Description("CatalArtManufSupp_pppuCurrIso3_description")
	private String pppuCurrIso3;

	@Column
	@Description("CatalArtManufSupp_vatRate_description")
	private BigDecimal vatRate;

	public String getArtIdentif() {
		return this.artIdentif;
	}

	public void setArtIdentif(final String artIdentif) {
		this.artIdentif = artIdentif;
	}

	public String getMsIdentif() {
		return this.msIdentif;
	}

	public void setMsIdentif(final String msIdentif) {
		this.msIdentif = msIdentif;
	}

	public String getMsType() {
		return this.msType;
	}

	public void setMsType(final String msType) {
		this.msType = msType;
	}

	public BigDecimal getWarrantyMonths() {
		return this.warrantyMonths;
	}

	public void setWarrantyMonths(final BigDecimal warrantyMonths) {
		this.warrantyMonths = warrantyMonths;
	}

	public BigDecimal getReturnDays() {
		return this.returnDays;
	}

	public void setReturnDays(final BigDecimal returnDays) {
		this.returnDays = returnDays;
	}

	public BigDecimal getPppu() {
		return this.pppu;
	}

	public void setPppu(final BigDecimal pppu) {
		this.pppu = pppu;
	}

	public String getPppuCurrIso3() {
		return this.pppuCurrIso3;
	}

	public void setPppuCurrIso3(final String pppuCurrIso3) {
		this.pppuCurrIso3 = pppuCurrIso3;
	}

	public BigDecimal getVatRate() {
		return this.vatRate;
	}

	public void setVatRate(final BigDecimal vatRate) {
		this.vatRate = vatRate;
	}

	@Override
	protected String makeIdentif() {
		return artIdentif + "_" + msIdentif;
	}
}