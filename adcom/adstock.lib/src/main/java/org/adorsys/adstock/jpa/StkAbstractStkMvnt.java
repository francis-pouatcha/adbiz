package org.adorsys.adstock.jpa;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.adorsys.adcatal.jpa.CatalArtLangMapping;
import org.adorsys.adcore.jpa.AbstractMvmtData;
import org.adorsys.javaext.description.Description;

@MappedSuperclass
@Description("StkMvnt_description")
public abstract class StkAbstractStkMvnt extends AbstractMvmtData {

	private static final long serialVersionUID = -4624920888946443298L;

	@Column
	@Description("StkMvnt_lotPic_description")
	@NotNull
	private String lotPic;

	@Column
	@Description("StkMvnt_artPic_description")
	@NotNull
	private String artPic;
	
	

	@Temporal(TemporalType.TIMESTAMP)
	@Description("StkMvnt_mvntDt_description")
	@NotNull
	private Date mvntDt;

	@Column
	@Description("StkMvnt_mvgUser_description")
	@NotNull
	private String mvgUser;

	@Column
	@Description("StkMvnt_movedQty_description")
	@NotNull
	private BigDecimal movedQty;

	@Column
	@Description("StkMvnt_mvntType_description")
	@Enumerated(EnumType.STRING)
	@NotNull
	private StkMvtType mvntType;

	@Column
	@Description("StkMvnt_mvntOrigin_description")
	@Enumerated(EnumType.STRING)
	@NotNull
	private StkMvtTerminal mvntOrigin;

	@Column
	@Description("StkMvnt_mvntDest_description")
	@Enumerated(EnumType.STRING)
	@NotNull
	private StkMvtTerminal mvntDest;

	// The main originating process
	@Column
	@Description("StkMvnt_prcssType_description")
	@NotNull
	private String prcssType;

	// The identifier of the main originating process.
	@Column
	@Description("StkMvnt_prcssNbr_description")
	@NotNull
	private String prcssNbr;

	// Doc numbers will be a string separated list of doc types and nbrs.
	// like: INVOICE:XXX,PROX_ORDER:YYY,INVENTORY:ZZZ,CASH_RECEIPT:CCC
	@Column
	@Description("StkMvnt_origDocNbrs_description")
	private String origDocNbrs;
	
	
	/*
	 * The name of this article in the language of the user.
	 */
	@Transient
	private CatalArtLangMapping artFeatures;
	
	
	
	public CatalArtLangMapping getArtFeatures() {
		return artFeatures;
	}
	
	public void setArtFeatures(CatalArtLangMapping artFeatures) {
		this.artFeatures = artFeatures;
	}

	public String getLotPic() {
		return this.lotPic;
	}

	public void setLotPic(final String lotPic) {
		this.lotPic = lotPic;
	}

	public String getArtPic() {
		return this.artPic;
	}

	public void setArtPic(final String artPic) {
		this.artPic = artPic;
	}

	public Date getMvntDt() {
		return this.mvntDt;
	}

	public void setMvntDt(final Date mvntDt) {
		this.mvntDt = mvntDt;
	}

	public String getMvgUser() {
		return this.mvgUser;
	}

	public void setMvgUser(final String mvgUser) {
		this.mvgUser = mvgUser;
	}

	public BigDecimal getMovedQty() {
		return this.movedQty;
	}

	public void setMovedQty(final BigDecimal movedQty) {
		this.movedQty = movedQty;
	}

	public StkMvtType getMvntType() {
		return this.mvntType;
	}

	public void setMvntType(final StkMvtType mvntType) {
		this.mvntType = mvntType;
	}

	public StkMvtTerminal getMvntOrigin() {
		return this.mvntOrigin;
	}

	public void setMvntOrigin(final StkMvtTerminal mvntOrigin) {
		this.mvntOrigin = mvntOrigin;
	}

	public StkMvtTerminal getMvntDest() {
		return this.mvntDest;
	}

	public void setMvntDest(final StkMvtTerminal mvntDest) {
		this.mvntDest = mvntDest;
	}

	public String getOrigDocNbrs() {
		return this.origDocNbrs;
	}

	public void setOrigDocNbrs(final String origDocNbrs) {
		this.origDocNbrs = origDocNbrs;
	}

	public String getPrcssType() {
		return prcssType;
	}

	public void setPrcssType(String prcssType) {
		this.prcssType = prcssType;
	}

	public String getPrcssNbr() {
		return prcssNbr;
	}

	public void setPrcssNbr(String prcssNbr) {
		this.prcssNbr = prcssNbr;
	}
	
}