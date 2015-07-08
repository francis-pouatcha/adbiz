package org.adorsys.adbnsptnr.jpa;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.CoreAbstTimedData;
import org.adorsys.adcore.utils.BigDecimalUtils;
import org.adorsys.javaext.description.Description;
import org.apache.commons.lang3.StringUtils;

@Entity
@Description("BpLegalPtnrId_description")
public class BpLegalPtnrId extends CoreAbstTimedData {

	private static final long serialVersionUID = -1682554156480323362L;

	@Column
	@Description("BpLegalPtnrId_ptnrNbr_description")
	@NotNull
	private String ptnrNbr;

	@Column
	@Description("BpLegalPtnrId_cpnyName_description")
	@NotNull
	private String cpnyName;

	@Column
	@Description("BpLegalPtnrId_shortName_description")
	@NotNull
	private String shortName;

	@Column
	@Description("BpLegalPtnrId_legalForm_description")
	private String legalForm;

	@Column
	@Description("BpLegalPtnrId_equity_description")
	private BigDecimal equity;

	@Column
	@Description("BpLegalPtnrId_cmrcRgstrNbr_description")
	private String cmrcRgstrNbr;

	@Column
	@Description("BpLegalPtnrId_taxPayerIdNbr_description")
	private String taxPayerIdNbr;

	public String getPtnrNbr() {
		return this.ptnrNbr;
	}

	public void setPtnrNbr(final String ptnrNbr) {
		this.ptnrNbr = ptnrNbr;
	}

	public String getCpnyName() {
		return this.cpnyName;
	}

	public void setCpnyName(final String cpnyName) {
		this.cpnyName = cpnyName;
	}

	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(final String shortName) {
		this.shortName = shortName;
	}

	public String getLegalForm() {
		return this.legalForm;
	}

	public void setLegalForm(final String legalForm) {
		this.legalForm = legalForm;
	}

	public BigDecimal getEquity() {
		return this.equity;
	}

	public void setEquity(final BigDecimal equity) {
		this.equity = equity;
	}

	public String getCmrcRgstrNbr() {
		return this.cmrcRgstrNbr;
	}

	public void setCmrcRgstrNbr(final String cmrcRgstrNbr) {
		this.cmrcRgstrNbr = cmrcRgstrNbr;
	}

	public String getTaxPayerIdNbr() {
		return this.taxPayerIdNbr;
	}

	public void setTaxPayerIdNbr(final String taxPayerIdNbr) {
		this.taxPayerIdNbr = taxPayerIdNbr;
	}

	@Override
	protected String makeIdentif() {
		return ptnrNbr;
	}
	
	
	public boolean updateContent(BpLegalPtnrId source){
		boolean changed = false;
		if(!StringUtils.equals(cpnyName, source.cpnyName)){
			cpnyName = source.cpnyName;
			changed = true;
		}
		if(!StringUtils.equals(shortName, source.shortName)){
			shortName = source.shortName;
			changed = true;
		}
		if(!StringUtils.equals(legalForm, source.legalForm)){
			legalForm = source.legalForm;
			changed = true;
		}
		if(!BigDecimalUtils.numericEquals(equity, equity)){
			equity = source.equity;
			changed = true;
		}
		if(!StringUtils.equals(cmrcRgstrNbr, source.cmrcRgstrNbr)){
			cmrcRgstrNbr = source.cmrcRgstrNbr;
			changed = true;
		}
		if(!StringUtils.equals(taxPayerIdNbr, source.taxPayerIdNbr)){
			taxPayerIdNbr = source.taxPayerIdNbr;
			changed = true;
		}
		return changed;
	}
}