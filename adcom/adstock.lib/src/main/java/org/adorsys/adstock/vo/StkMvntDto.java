package org.adorsys.adstock.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.annotation.DateFormatPattern;
import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;
import org.adorsys.adcore.jpa.CoreStkMvtTerminal;
import org.adorsys.adcore.jpa.CoreStkMvtType;

public class StkMvntDto extends CoreAbstIdentifObject {
	
	private static final long serialVersionUID = 1645388779910905925L;

	/*
	 * The lot identifier
	 */
	@Column
	private String lotPic;

	/*
	 * The section target of this movement. For a move in, this is the section where the
	 * item is put (mvntDestIdentif). For a move out, this is the section where the item was taken (mvntOriginIdentif).
	 */
	@Column
	private String section;

	/**
	 * The quantity acessing date
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private Date acsngDt;

	/*
	 * The quantity moved
	 */
	@Column
	private BigDecimal trgtQty;	

	/*
	 * The movement type : IN, OUT
	 */
	@Column
	@Description("StkMvnt_mvntType_description")
	@Enumerated(EnumType.STRING)
	private CoreStkMvtType mvntType;

	/*
	 * The movement origin : WAREHOUSE, POS,SUPPLIER, CUSTOMER, DONNATION,SAMPLE,EXPIRING,TRASH,NONE
	 * 
	 */
	@Column
	@Description("StkMvnt_mvntOrigin_description")
	@Enumerated(EnumType.STRING)
	private CoreStkMvtTerminal mvntTerminal;

	/*
	 * The original document number. This is for example
	 * the invoice 
	 */
	@Column
	@Description("StkMvnt_origDocNbrs_description")
	private String origDocNbrs;

	/*
	 * The originating process. Sales, Inventory, Procurement
	 */
	@Column
	@Description("StkAbstractStockQty_origProcs_description")
	@NotNull
	private String origProcs;

	/*
	 * The identifier of the origin process: InvoiceNbr, InvtryNbr, ProcurementNbr, 
	 */
	@Column
	@Description("StkAbstractStockQty_origProcsNbr_description")
	@NotNull
	private String origProcsNbr;

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

	public Date getAcsngDt() {
		return acsngDt;
	}

	public void setAcsngDt(Date acsngDt) {
		this.acsngDt = acsngDt;
	}

	public BigDecimal getTrgtQty() {
		return trgtQty;
	}

	public void setTrgtQty(BigDecimal trgtQty) {
		this.trgtQty = trgtQty;
	}

	public CoreStkMvtType getMvntType() {
		return mvntType;
	}

	public void setMvntType(CoreStkMvtType mvntType) {
		this.mvntType = mvntType;
	}

	public String getOrigDocNbrs() {
		return origDocNbrs;
	}

	public void setOrigDocNbrs(String origDocNbrs) {
		this.origDocNbrs = origDocNbrs;
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

	public CoreStkMvtTerminal getMvntTerminal() {
		return mvntTerminal;
	}

	public void setMvntTerminal(CoreStkMvtTerminal mvntTerminal) {
		this.mvntTerminal = mvntTerminal;
	}

	@Override
	protected String makeIdentif() {
		return UUID.randomUUID().toString();
	}
}
