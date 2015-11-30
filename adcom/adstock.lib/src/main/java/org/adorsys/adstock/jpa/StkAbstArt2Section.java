package org.adorsys.adstock.jpa;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;

/**
 * Defines the mapping between an article and a section. This is a sort 
 * of layout for the storage.
 * 
 * Following field are mapped from the inherited class.
 * 
 * The cntnrIdentif --> artPic
 * The valueDate --> maps the date of last modification
 * The identif --> cntnrIdentif + "_" + section
 * 
 * This information can also be used to determine where to store goods
 * upon delivery.
 * 
 * @author fpo
 *
 */
@MappedSuperclass
public abstract class StkAbstArt2Section extends CoreAbstIdentifObject {

	private static final long serialVersionUID = -3605677500029860511L;

	/*
	 * The section code
	 */
	@Column
	@Description("StkAbstArt2Section_section_description")
	@NotNull
	private String section;

	/*
	 * The quantity expected to be in stock. We can use this to 
	 * compute the quantity to be ordered or moved.
	 */
	@Column
	@Description("StkAbstArt2Section_dfltQty_description")
	@NotNull
	private BigDecimal dfltQty = BigDecimal.ZERO;
	
	/*
	 * The expected minimum quantity. Order could be triggered 
	 * based on this.
	 * 
	 * Eventual inter section stock movement can also be triggered based on this.
	 */
	@Column
	@Description("StkAbstArt2Section_minQty_description")
	@NotNull
	private BigDecimal minQty = BigDecimal.ZERO;

	/*
	 * The expected maximum quantity.
	 * 
	 * Eventual inter section stock movement can also be triggered based on this.
	 */
	@Column
	@Description("StkAbstArt2Section_maxQty_description")
	@NotNull
	private BigDecimal maxQty = BigDecimal.ZERO;

	/*
	 * The order threshold
	 */
	@Column
	@Description("StkAbstArt2Section_orderTrshld_description")
	@NotNull
	private BigDecimal orderTrshld = BigDecimal.ZERO;
	
	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public static String toArtPicAndSectionKey(String artPic, String section){
		return artPic +"_"+ section;
	}

	@Override
	protected String makeIdentif() {
		return toArtPicAndSectionKey(cntnrIdentif, section);
	}

	public BigDecimal getDfltQty() {
		return dfltQty;
	}

	public void setDfltQty(BigDecimal dfltQty) {
		this.dfltQty = dfltQty;
	}

	public BigDecimal getMinQty() {
		return minQty;
	}

	public void setMinQty(BigDecimal minQty) {
		this.minQty = minQty;
	}

	public BigDecimal getMaxQty() {
		return maxQty;
	}

	public void setMaxQty(BigDecimal maxQty) {
		this.maxQty = maxQty;
	}

	public BigDecimal getOrderTrshld() {
		return orderTrshld;
	}

	public void setOrderTrshld(BigDecimal orderTrshld) {
		this.orderTrshld = orderTrshld;
	}
}