package org.adorsys.adstock.jpa;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.adorsys.adcatal.jpa.CatalArtLangMapping;
import org.adorsys.adcore.jpa.CoreAbstBsnsItem;
import org.adorsys.adcore.utils.FinancialOps;
import org.adorsys.javaext.description.Description;

@MappedSuperclass
@Description("StkArticleLot_description")
public class StkAbstractArticleLot extends CoreAbstBsnsItem {

	private static final long serialVersionUID = 6790628013825127916L;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("StkArticleLot_stkgDt_description")
	@NotNull
	private Date stkgDt;

	// Date of the closing of this article lot. This is generally done
	// after an inventory where we can state that there is none
	// of the articles of this lot remaining in stock.
	@Temporal(TemporalType.TIMESTAMP)
	@Description("StkArticleLot_closedDt_description")
	private Date closedDt;
	
	private BigDecimal qtyOnValDt;

	@Transient
	private List<StkArticleLot2StrgSctn> strgSctns = new ArrayList<StkArticleLot2StrgSctn>();

	/*
	 * The quantities in each lot of this article.
	 */
	@Transient
	private List<StkLotStockQty> artQties = new ArrayList<StkLotStockQty>();
	
	@Transient
	private BigDecimal lotQty;
	
	@Transient
	private Date lotQtyDt;
	
	/*
	 * The name of this article in the language of the user.
	 */
	@Transient
	private CatalArtLangMapping artFeatures;
	

	public Date getStkgDt() {
		return this.stkgDt;
	}

	public void setStkgDt(final Date stkgDt) {
		this.stkgDt = stkgDt;
	}

	@Override
	protected String makeIdentif() {
		return toId(getLotPic());
	}
	
	public static String toId(String lotPic){
		return lotPic;
	}

	public Date getClosedDt() {
		return closedDt;
	}

	public void setClosedDt(Date closedDt) {
		this.closedDt = closedDt;
	}

	public List<StkArticleLot2StrgSctn> getStrgSctns() {
		return strgSctns;
	}

	public void setStrgSctns(List<StkArticleLot2StrgSctn> strgSctns) {
		this.strgSctns = strgSctns;
	}

	public List<StkLotStockQty> getArtQties() {
		return artQties;
	}

	public void setArtQties(List<StkLotStockQty> artQties) {
		this.artQties = artQties;
	}

	public CatalArtLangMapping getArtFeatures() {
		return artFeatures;
	}

	public void setArtFeatures(CatalArtLangMapping artFeatures) {
		this.artFeatures = artFeatures;
	}

	public BigDecimal getLotQty() {
		return lotQty;
	}

	public void setLotQty(BigDecimal lotQty) {
		this.lotQty = lotQty;
	}

	public Date getLotQtyDt() {
		return lotQtyDt;
	}

	public void setLotQtyDt(Date lotQtyDt) {
		this.lotQtyDt = lotQtyDt;
	}

	@Override
	public void evlte() {
		qtyOnValDt = lotQty;
		setValueDt(lotQtyDt);
		setSlsGrossPrcPreTax(FinancialOps.qtyTmsPrice(this.qtyOnValDt, getSlsUnitPrcPreTax(), getSlsUnitPrcCur()));
		setPrchGrossPrcPreTax(FinancialOps.qtyTmsPrice(this.qtyOnValDt, getPrchUnitPrcPreTax(), getPrchUnitPrcCur()));
		setStkValPreTax(FinancialOps.qtyTmsPrice(this.qtyOnValDt, getStkUnitValPreTax(), getStkUnitValCur()));
	}
}