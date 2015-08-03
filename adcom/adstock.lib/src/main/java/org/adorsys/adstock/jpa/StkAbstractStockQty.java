package org.adorsys.adstock.jpa;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;
import org.apache.commons.lang3.StringUtils;

@MappedSuperclass
public abstract class StkAbstractStockQty extends CoreAbstIdentifObject {

	private static final long serialVersionUID = 5437976674412946410L;

	@Column
	@Description("StkAbstractStockQty_artPic_description")
	@NotNull
	private String artPic;

	@Column
	@Description("StkAbstractStockQty_lotPic_description")
	@NotNull
	private String lotPic;

	@Column
	@Description("StkAbstractStockQty_section_description")
	@NotNull
	private String section;
	
	@Column
	@Description("StkAbstractStockQty_stockQty_description")
	@NotNull
	private BigDecimal stockQty = BigDecimal.ZERO;

	@Column
	@Description("StkAbstractStockQty_rsvrdQty_description")
	@NotNull
	private BigDecimal rsvrdQty = BigDecimal.ZERO;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Description("StkAbstractStockQty_qtyDt_description")
	@NotNull
	private Date qtyDt;
	
	// The originating process. Sales, Inventory, Procurement
	@Column
	@Description("StkAbstractStockQty_origProcs_description")
	private String origProcs;

	// The identifier of the origin process.
	@Column
	@Description("StkAbstractStockQty_origProcsNbr_description")
	private String origProcsNbr;
	
	// The number is incremented every time a new stock quantity
	// is computed from a parent. The new seqNbr is the sequence
	// number of the parent increased by one.
	@NotNull
	@Description("StkAbstractStockQty_seqNbr_description")
	private Integer seqNbr;
	
	// For any seqNbr > 0, the id of the parent rec must be documented here.
	// Unless it is a consolidated record.
	@Description("StkAbstractStockQty_parentRcrd_description")
	private String parentRcrd;
	
	// Set to true if the record is consolidated. In that case there
	// will be no prntRecord.
	@Description("StkAbstractStockQty_cnsldtd_description")
	private Boolean cnsldtd;
	
	@Description("StkAbstractStockQty_resvtnTime_description")
	private Date resvdDt;

	@Description("StkAbstractStockQty_resvdCncldDt_description")
	private Date resvdCncldDt;

	// The identifier of the origin process.
	@Column
	@Description("StkAbstractStockQty_stkMvntIdentif_description")
	@NotNull
	private String stkMvntIdentif;
	
	public String getArtPic() {
		return this.artPic;
	}

	public void setArtPic(final String artPic) {
		this.artPic = artPic;
	}

	public BigDecimal getStockQty() {
		return this.stockQty;
	}

	public void setStockQty(final BigDecimal stockQty) {
		this.stockQty = stockQty;
	}

	public Date getQtyDt() {
		return this.qtyDt;
	}

	public void setQtyDt(final Date qtyDt) {
		this.qtyDt = qtyDt;
	}

	public Integer getSeqNbr() {
		return seqNbr;
	}

	public void setSeqNbr(Integer seqNbr) {
		this.seqNbr = seqNbr;
	}

	public String getOrigProcs() {
		return origProcs;
	}

	public void setOrigProcs(String origProcs) {
		this.origProcs = origProcs;
	}

	public String getOrigProcsNbr() {
		return origProcsNbr;
	}

	public void setOrigProcsNbr(String origProcsNbr) {
		this.origProcsNbr = origProcsNbr;
	}

	public String getParentRcrd() {
		return parentRcrd;
	}

	public void setParentRcrd(String parentRcrd) {
		this.parentRcrd = parentRcrd;
	}

	public Boolean getCnsldtd() {
		return cnsldtd;
	}

	public void setCnsldtd(Boolean cnsldtd) {
		this.cnsldtd = cnsldtd;
	}

	public String getLotPic() {
		return lotPic;
	}

	public void setLotPic(String lotPic) {
		this.lotPic = lotPic;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}
	
	public Date getResvdDt() {
		return resvdDt;
	}

	public void setResvdDt(Date resvdDt) {
		this.resvdDt = resvdDt;
	}

	public Date getResvdCncldDt() {
		return resvdCncldDt;
	}

	public void setResvdCncldDt(Date resvdCncldDt) {
		this.resvdCncldDt = resvdCncldDt;
	}

	public String artPicAndLotPicAndSection(){
		return getArtPic() + "_" + getLotPic() + "_" + getSection();
	}

	public BigDecimal getRsvrdQty() {
		return rsvrdQty;
	}

	public void setRsvrdQty(BigDecimal rsvrdQty) {
		this.rsvrdQty = rsvrdQty;
	}

	public String getStkMvntIdentif() {
		return stkMvntIdentif;
	}

	public void setStkMvntIdentif(String stkMvntIdentif) {
		this.stkMvntIdentif = stkMvntIdentif;
	}

	@Override
	protected String makeIdentif() {
		if(StringUtils.isNotBlank(stkMvntIdentif)) return stkMvntIdentif;
		return getArtPic() + "_" + getLotPic() + "_" + getSection() + "_" + getSeqNbr();
	}
}