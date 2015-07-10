package org.adorsys.adcatal.jpa;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.annotation.Description;
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
public class CatalArtDetailConfig extends CatalAbstractArticle {

	private static final long serialVersionUID = -2136313145008332965L;

	/*
	 * This describes the quantity of units of the detailed article in the main article.
	 */
	@Column
	@Description("CatalArtDetailConfig_qtyOfDtldInMain_description")
	@NotNull
	private BigDecimal qtyOfDtldInMain;

	/*
	 * This expresses the proportion of the detailed article in the main article.
	 */
	@Column
	@Description("CatalArtDetailConfig_targetPrprtn_description")
	@NotNull
	private BigDecimal pptnOfDtldInMain;

	/*
	 * Says if the quantitative relation between the main and the proportion should be managed
	 * in proportions of in absolute quantity
	 */
	@Column
	@Description("CatalArtDetailConfig_mngInPptn_description")
	private Boolean mngInPptn;

	public BigDecimal getQtyOfDtldInMain() {
		return qtyOfDtldInMain;
	}

	public void setQtyOfDtldInMain(BigDecimal qtyOfDtldInMain) {
		this.qtyOfDtldInMain = qtyOfDtldInMain;
	}

	public BigDecimal getPptnOfDtldInMain() {
		return pptnOfDtldInMain;
	}

	public void setPptnOfDtldInMain(BigDecimal pptnOfDtldInMain) {
		this.pptnOfDtldInMain = pptnOfDtldInMain;
	}

	public Boolean getMngInPptn() {
		return mngInPptn;
	}

	public void setMngInPptn(Boolean mngInPptn) {
		this.mngInPptn = mngInPptn;
	}

	@Override
	protected String makeIdentif() {
		return SequenceGenerator.getSequence(SequenceGenerator.ARTICLE_DETAIL_SEQUENCE_PREFIXE);
	}
}