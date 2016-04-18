package org.adorsys.adbnsptnr.jpa;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;

@Entity
@Description("BpPtnrCreditDtls_description")
public class BpPtnrCreditDtls extends CoreAbstIdentifObject {

	private static final long serialVersionUID = 3088436723152282857L;

	@Column
	@Description("BpPtnrCreditDtls_ptnrNbr_description")
	@NotNull
	private String ptnrNbr;

	@Column
	@Description("BpPtnrCreditDtls_creditAuthrzd_description")
	private Boolean creditAuthrzd;

	@Column
	@Description("BpPtnrCreditDtls_ttlCreditLn_description")
	private BigDecimal ttlCreditLn;

	public String getPtnrNbr() {
		return this.ptnrNbr;
	}

	public void setPtnrNbr(final String ptnrNbr) {
		this.ptnrNbr = ptnrNbr;
	}

	public Boolean getCreditAuthrzd() {
		return this.creditAuthrzd;
	}

	public void setCreditAuthrzd(final Boolean creditAuthrzd) {
		this.creditAuthrzd = creditAuthrzd;
	}

	public BigDecimal getTtlCreditLn() {
		return this.ttlCreditLn;
	}

	public void setTtlCreditLn(final BigDecimal ttlCreditLn) {
		this.ttlCreditLn = ttlCreditLn;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (ptnrNbr != null && !ptnrNbr.trim().isEmpty())
			result += "ptnrNbr: " + ptnrNbr;
		if (creditAuthrzd != null)
			result += ", creditAuthrzd: " + creditAuthrzd;
		return result;
	}

	@Override
	protected String makeIdentif() {
		return ptnrNbr;
	}
}