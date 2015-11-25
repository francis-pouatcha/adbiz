package org.adorsys.adbnsptnr.jpa;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;

/**
 * A purpose of an insurance contract.
 * 
 * @author francis
 *
 */
@Entity
@Description("BpInsrrncPpse_description")
public class BpInsrrncPpse extends CoreAbstIdentifObject {

	private static final long serialVersionUID = 1867049695472622853L;

	@Column
	@Description("BpInsrrncPpse_insrncIdentif_description")
	@NotNull
	private String insrncIdentif;

	@Column
	@Description("BpInsrrncPpse_insuredNbr_description")
	@NotNull
	private String insuredNbr;
	
	@Column
	@Description("BpInsrrncPpse_ppseType_description")
	@Enumerated(EnumType.STRING)
	private BpInsrncPpseType ppseType;

	@Column
	@Description("BpInsrrncPpse_coverRatePct_description")
	@NotNull
	private BigDecimal coverRatePct;

	@Column
	@Description("BpInsrrncPpse_decutible_description")
	@NotNull
	private BigDecimal decutible;
	
	@Column
	@Description("BpInsrrncPpse_description_description")
	@Size(max = 256)
	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("BpInsrrncPpse_beginDate_description")
	@NotNull(message = "BpInsrrncPpse_beginDate_NotNull_validation")
	private Date beginDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("BpInsrrncPpse_endDate_description")
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

	public BigDecimal getCoverRatePct() {
		return this.coverRatePct;
	}

	public void setCoverRatePct(final BigDecimal coverRatePct) {
		this.coverRatePct = coverRatePct;
	}

	public String getInsrncIdentif() {
		return insrncIdentif;
	}

	public void setInsrncIdentif(String insrncIdentif) {
		this.insrncIdentif = insrncIdentif;
	}

	public BpInsrncPpseType getPpseType() {
		return ppseType;
	}

	public void setPpseType(BpInsrncPpseType ppseType) {
		this.ppseType = ppseType;
	}

	public BigDecimal getDecutible() {
		return decutible;
	}

	public void setDecutible(BigDecimal decutible) {
		this.decutible = decutible;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	protected String makeIdentif() {
		return insrncIdentif + "_" +insuredNbr +"_" +ppseType.name();
	}
}