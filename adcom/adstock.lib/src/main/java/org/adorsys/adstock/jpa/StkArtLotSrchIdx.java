package org.adorsys.adstock.jpa;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
public class StkArtLotSrchIdx extends StkAbstLot2Section {

	private static final long serialVersionUID = -246126930118220304L;

	@Column
	@NotNull
	private String langIso2;
	
	@Column
	@NotNull
	private String artName;
	
	@Column
	@NotNull
	private String artLMIdx;

	/*
	 * The expiration date of this lot. This is decisive for the splitting in lots.
	 * All articles in the same lot have the same expiration date.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date expirDt;
	
	@Column
	private BigDecimal slsUnitPrcPreTax;

	@Column
	private BigDecimal slsUnitPrcTaxIncl;
	
	@Column
	private BigDecimal slsMinUnitPrcPreTax;

	@Column
	private BigDecimal slsMinUnitPrcTaxIncl;
	
	@Column
	private BigDecimal slsVatPct;
	
	public static String toId(String lotPic, String section, String lg){
		return lotPic +"_"+ section + "_" + lg;
	}
	
	@Override
	protected String makeIdentif() {
		return toId(getLotPic(), getSection(), langIso2);
	}

	public String getLangIso2() {
		return langIso2;
	}

	public void setLangIso2(String langIso2) {
		this.langIso2 = langIso2;
	}

	public String getArtName() {
		return artName;
	}

	public void setArtName(String artName) {
		this.artName = artName;
	}

	public Date getExpirDt() {
		return expirDt;
	}

	public void setExpirDt(Date expirDt) {
		this.expirDt = expirDt;
	}

	public BigDecimal getSlsUnitPrcPreTax() {
		return slsUnitPrcPreTax;
	}

	public void setSlsUnitPrcPreTax(BigDecimal slsUnitPrcPreTax) {
		this.slsUnitPrcPreTax = slsUnitPrcPreTax;
	}

	public BigDecimal getSlsUnitPrcTaxIncl() {
		return slsUnitPrcTaxIncl;
	}

	public void setSlsUnitPrcTaxIncl(BigDecimal slsUnitPrcTaxIncl) {
		this.slsUnitPrcTaxIncl = slsUnitPrcTaxIncl;
	}

	public BigDecimal getSlsMinUnitPrcPreTax() {
		return slsMinUnitPrcPreTax;
	}

	public void setSlsMinUnitPrcPreTax(BigDecimal slsMinUnitPrcPreTax) {
		this.slsMinUnitPrcPreTax = slsMinUnitPrcPreTax;
	}

	public BigDecimal getSlsMinUnitPrcTaxIncl() {
		return slsMinUnitPrcTaxIncl;
	}

	public void setSlsMinUnitPrcTaxIncl(BigDecimal slsMinUnitPrcTaxIncl) {
		this.slsMinUnitPrcTaxIncl = slsMinUnitPrcTaxIncl;
	}

	public BigDecimal getSlsVatPct() {
		return slsVatPct;
	}

	public void setSlsVatPct(BigDecimal slsVatPct) {
		this.slsVatPct = slsVatPct;
	}

	public String getArtLMIdx() {
		return artLMIdx;
	}

	public void setArtLMIdx(String artLMIdx) {
		this.artLMIdx = artLMIdx;
	}
}
