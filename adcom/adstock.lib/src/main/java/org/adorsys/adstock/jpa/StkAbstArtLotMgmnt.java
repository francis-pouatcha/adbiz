package org.adorsys.adstock.jpa;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;

/**
 * The artPic is the container identifier. 
 * 
 * Holds data for the management of lots. This is a central concept for the stocking of
 * goods. There is a lot of factors associated with the evaluation of different entries of
 * the same article.
 * 
 * @author fpo
 *
 */
@MappedSuperclass
public abstract class StkAbstArtLotMgmnt extends CoreAbstIdentifObject {
	private static final long serialVersionUID = 7105343465835014847L;

	/*
	 * Request user to enter lot code.
	 */
	@Description("StkAbstArtLotMgmnt_lotAssgnmtManual_description")
	private Boolean lotAssgnmtManual = Boolean.FALSE;

	/*
	 * Generate new lot per supplier.
	 */
	@Description("StkAbstArtLotMgmnt_lotAssgnmtSupplier_description")
	private Boolean lotAssgnmtSupplier = Boolean.FALSE;

	/*
	 * Generate new lot per supplier product id code.
	 */
	@Description("StkAbstArtLotMgmnt_lotAssgnmtSupplPic_description")
	private Boolean lotAssgnmtSupplrPic = Boolean.TRUE;

	/*
	 * Generate new lot per supplier product id code.
	 */
	@Description("StkAbstArtLotMgmnt_lotAssgnmtManufPic_description")
	private Boolean lotAssgnmtManufPic = Boolean.TRUE;
	
	/*
	 * Generate new lot for each expir date.
	 */
	@Description("StkAbstArtLotMgmnt_lotAssgnmtExpirDt_description")
	private Boolean lotAssgnmtExpirDt = Boolean.TRUE;

	/*
	 * Generate new lot for each po item.
	 */
	@Description("StkAbstArtLotMgmnt_lotAssgnmtPOItem_description")
	private Boolean lotAssgnmtPOItem = Boolean.TRUE;
	
	/*
	 * Generate new lot for each delivery.
	 */
	@Description("StkAbstArtLotMgmnt_lotAssgnmtDlvry_description")
	private Boolean lotAssgnmtDlvry = Boolean.FALSE;

	/*
	 * If a position has free qty, create a lot for free qties.
	 */
	@Description("StkAbstArtLotMgmnt_lotAssgnmtFreeQty_description")
	private Boolean lotAssgnmtFreeQty = Boolean.FALSE;


	@Enumerated(EnumType.STRING)
	private StkLotMgPrchPriceScheme prchPriceScheme=StkLotMgPrchPriceScheme.WEIGHTED_AVERAGE_DLVRD;
	
	@Enumerated(EnumType.STRING)
	private StkLotMgSlsPriceScheme slsPriceScheme = StkLotMgSlsPriceScheme.WEIGHTED_AVERAGE_DLVRD;

	@Enumerated(EnumType.STRING)
	private StkLotMgLotSelectnScheme selectnScheme = StkLotMgLotSelectnScheme.SMART;

	@Enumerated(EnumType.STRING)
	private StkLotMgLotSelectnDecisn selectnDecisn = StkLotMgLotSelectnDecisn.SEMIAUTO;

	public Boolean getLotAssgnmtManual() {
		return lotAssgnmtManual;
	}

	public void setLotAssgnmtManual(Boolean lotAssgnmtManual) {
		this.lotAssgnmtManual = lotAssgnmtManual;
	}

	public Boolean getLotAssgnmtSupplier() {
		return lotAssgnmtSupplier;
	}

	public void setLotAssgnmtSupplier(Boolean lotAssgnmtSupplier) {
		this.lotAssgnmtSupplier = lotAssgnmtSupplier;
	}

	public Boolean getLotAssgnmtSupplrPic() {
		return lotAssgnmtSupplrPic;
	}

	public void setLotAssgnmtSupplrPic(Boolean lotAssgnmtSupplrPic) {
		this.lotAssgnmtSupplrPic = lotAssgnmtSupplrPic;
	}

	public Boolean getLotAssgnmtManufPic() {
		return lotAssgnmtManufPic;
	}

	public void setLotAssgnmtManufPic(Boolean lotAssgnmtManufPic) {
		this.lotAssgnmtManufPic = lotAssgnmtManufPic;
	}

	public Boolean getLotAssgnmtExpirDt() {
		return lotAssgnmtExpirDt;
	}

	public void setLotAssgnmtExpirDt(Boolean lotAssgnmtExpirDt) {
		this.lotAssgnmtExpirDt = lotAssgnmtExpirDt;
	}

	public Boolean getLotAssgnmtPOItem() {
		return lotAssgnmtPOItem;
	}

	public void setLotAssgnmtPOItem(Boolean lotAssgnmtPOItem) {
		this.lotAssgnmtPOItem = lotAssgnmtPOItem;
	}

	public Boolean getLotAssgnmtDlvry() {
		return lotAssgnmtDlvry;
	}

	public void setLotAssgnmtDlvry(Boolean lotAssgnmtDlvry) {
		this.lotAssgnmtDlvry = lotAssgnmtDlvry;
	}

	public Boolean getLotAssgnmtFreeQty() {
		return lotAssgnmtFreeQty;
	}

	public void setLotAssgnmtFreeQty(Boolean lotAssgnmtFreeQty) {
		this.lotAssgnmtFreeQty = lotAssgnmtFreeQty;
	}

	public StkLotMgPrchPriceScheme getPrchPriceScheme() {
		return prchPriceScheme;
	}

	public void setPrchPriceScheme(StkLotMgPrchPriceScheme prchPriceScheme) {
		this.prchPriceScheme = prchPriceScheme;
	}

	public StkLotMgSlsPriceScheme getSlsPriceScheme() {
		return slsPriceScheme;
	}

	public void setSlsPriceScheme(StkLotMgSlsPriceScheme slsPriceScheme) {
		this.slsPriceScheme = slsPriceScheme;
	}

	public StkLotMgLotSelectnScheme getSelectnScheme() {
		return selectnScheme;
	}

	public void setSelectnScheme(StkLotMgLotSelectnScheme selectnScheme) {
		this.selectnScheme = selectnScheme;
	}

	public StkLotMgLotSelectnDecisn getSelectnDecisn() {
		return selectnDecisn;
	}

	public void setSelectnDecisn(StkLotMgLotSelectnDecisn selectnDecisn) {
		this.selectnDecisn = selectnDecisn;
	}
	
}