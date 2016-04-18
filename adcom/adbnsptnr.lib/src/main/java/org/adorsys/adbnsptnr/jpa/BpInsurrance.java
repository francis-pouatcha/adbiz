package org.adorsys.adbnsptnr.jpa;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;

/**
 * Mapping of an insurance contract between business partners.
 * 
 * @author francis
 *
 */
@Entity
@Description("BpInsurrance_description")
public class BpInsurrance extends CoreAbstIdentifObject {

	private static final long serialVersionUID = 3928927183324719436L;

	@Column
	@Description("BpInsurrance_insuredNbr_description")
	@NotNull
	private String insuredNbr;

	@Column
	@Description("BpInsurrance_insurerNbr_description")
	@NotNull
	private String insurerNbr;

	@Column
	@Description("BpInsurrance_contractNbr_description")
	@NotNull
	private String contractNbr;

	@Column
	@Description("BpInsurrance_coverRatePct_description")
	@NotNull
	private BigDecimal coverRatePct;

	@Column
	@Description("BpInsurrance_decutible_description")
	@NotNull
	private BigDecimal decutible;

	@Column
	@Description("BpInsurrance_description_description")
	@Size(max = 256)
	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("BpInsurrance_beginDate_description")
	@NotNull(message = "BpInsurrance_beginDate_NotNull_validation")
	private Date beginDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("BpInsurrance_endDate_description")
	private Date endDate;
	
	public Date getBeginDate() {
		return this.beginDate;
	}

	public void setBeginDate(final Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	public String getInsurerNbr() {
		return this.insurerNbr;
	}

	public void setInsurerNbr(final String insurerNbr) {
		this.insurerNbr = insurerNbr;
	}

	public BigDecimal getCoverRatePct() {
		return this.coverRatePct;
	}

	public void setCoverRatePct(final BigDecimal coverRatePct) {
		this.coverRatePct = coverRatePct;
	}

	@Override
	protected String makeIdentif() {
		return insurerNbr + "_" + insuredNbr + "_" + contractNbr;
	}
}