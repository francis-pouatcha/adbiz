package org.adorsys.adcatal.jpa;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;
import org.adorsys.adcore.utils.SequenceGenerator;

/**
 * Product Details Config.
 * 
 * The field container identifier is used to reference the main article.
 * 
 * @author francis
 *
 */
@Entity
@Description("CatalArtDetailConfig_description")
public class CatalArtDetailConfig extends CoreAbstIdentifObject {

	private static final long serialVersionUID = -2136313145008332965L;

	/*
	 * This describes the quantity of units of the detailed article in the main article.
	 */
	@Column
	@Description("CatalArtDetailConfig_qtyOfDtldInMain_description")
	@NotNull
	private BigDecimal qtyOfDtldInMain;

	/*
	 * Says if the quantitative relation between the main and the proportion should be managed
	 * in proportions of in absolute quantity
	 */
	@Column
	@Description("CatalArtDetailConfig_mngInPptn_description")
	private Boolean mngInPptn = Boolean.FALSE;
	
	/*
	 * Distinguishes the detailed article from the main article.  
	 */
	@NotNull
	private String qualifier;
	
	@Column
	@Description("CatalArticle_sppu_description")
	private BigDecimal sppu;
	
	/*
	 * The identifier of the corresponding article.
	 */
	@NotNull
	private String artIdentif;

	public BigDecimal getQtyOfDtldInMain() {
		return qtyOfDtldInMain;
	}

	public void setQtyOfDtldInMain(BigDecimal qtyOfDtldInMain) {
		this.qtyOfDtldInMain = qtyOfDtldInMain;
	}

	public Boolean getMngInPptn() {
		return mngInPptn;
	}

	public void setMngInPptn(Boolean mngInPptn) {
		this.mngInPptn = mngInPptn;
	}

	public String getQualifier() {
		return qualifier;
	}

	public void setQualifier(String qualifier) {
		this.qualifier = qualifier;
	}

	public BigDecimal getSppu() {
		return sppu;
	}

	public void setSppu(BigDecimal sppu) {
		this.sppu = sppu;
	}

	public String getArtIdentif() {
		return artIdentif;
	}

	public void setArtIdentif(String artIdentif) {
		this.artIdentif = artIdentif;
	}

	@Override
	protected String makeIdentif() {
		return SequenceGenerator.getSequence(SequenceGenerator.ARTICLE_DETAIL_SEQUENCE_PREFIXE);
	}
}