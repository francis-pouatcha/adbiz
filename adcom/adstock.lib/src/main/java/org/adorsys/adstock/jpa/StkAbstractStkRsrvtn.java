package org.adorsys.adstock.jpa;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractMvmtData;
import org.adorsys.javaext.description.Description;

/**
 * Document the reservation of a stock quantity.
 * 
 * @author francis
 *
 */
@MappedSuperclass
@Description("StkRsrvtn_description")
public abstract class StkAbstractStkRsrvtn extends AbstractMvmtData {

	private static final long serialVersionUID = -7227802995034130322L;
	@Column
	@Description("StkRsrvtn_lotPic_description")
	@NotNull
	private String lotPic;

	@Column
	@Description("StkRsrvtn_artPic_description")
	@NotNull
	private String artPic;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("StkRsrvtn_rsrvtntDt_description")
	@NotNull
	private Date rsrvtntDt;

	// This reservation expires.
	@Temporal(TemporalType.TIMESTAMP)
	@Description("StkRsrvtn_expirDt_description")
	@NotNull
	private Date expirDt;
	
	@Column
	@Description("StkRsrvtn_rsrvgUser_description")
	@NotNull
	private String rsrvgUser;

	@Column
	@Description("StkRsrvtn_rsrvdQty_description")
	@NotNull
	private BigDecimal rsrvdQty;

	@Column
	@Description("StkRsrvtn_mvntType_description")
	@Enumerated(EnumType.STRING)
	@NotNull
	private StkMvtType mvntType;

	@Column
	@Description("StkRsrvtn_mvntOrigin_description")
	@Enumerated(EnumType.STRING)
	@NotNull
	private StkMvtTerminal mvntOrigin;

	@Column
	@Description("StkRsrvtn_mvntDest_description")
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
	// A reservation might not have a document. Then we consider this 
	// A transient reservation, with a standard expiration date.
	@Column
	@Description("StkMvnt_origDocNbrs_description")
	private String origDocNbrs;

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

	public Date getRsrvtntDt() {
		return rsrvtntDt;
	}

	public void setRsrvtntDt(Date rsrvtntDt) {
		this.rsrvtntDt = rsrvtntDt;
	}

	public Date getExpirDt() {
		return expirDt;
	}

	public void setExpirDt(Date expirDt) {
		this.expirDt = expirDt;
	}

	public String getRsrvgUser() {
		return rsrvgUser;
	}

	public void setRsrvgUser(String rsrvgUser) {
		this.rsrvgUser = rsrvgUser;
	}

	public BigDecimal getRsrvdQty() {
		return rsrvdQty;
	}

	public void setRsrvdQty(BigDecimal rsrvdQty) {
		this.rsrvdQty = rsrvdQty;
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