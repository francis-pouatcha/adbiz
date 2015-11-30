package org.adorsys.adstock.jpa;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.annotation.Description;

/**
 * Hold the qty of a article lot over sections.
 * 
 * @author fpo
 *
 */
@MappedSuperclass
public abstract class StkAbstLotStockQty extends StkAbstStockQty {
	
	private static final long serialVersionUID = -5056295182175309637L;
	
	@Column
	@Description("StkAbstractStockQty_lotPic_description")
	@NotNull
	private String lotPic;

	// The originating process. Sales, Inventory, Procurement
	@Column
	@Description("StkAbstractStockQty_origProcs_description")
	private String origProcs;

	// The identifier of the origin process.
	@Column
	@Description("StkAbstractStockQty_origProcsNbr_description")
	private String origProcsNbr;
	
	// The identifier of the origin process.
	@Column
	@Description("StkAbstractStockQty_stkMvntIdentif_description")
	@NotNull
	private String stkMvntIdentif;

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

	public String getLotPic() {
		return lotPic;
	}

	public void setLotPic(String lotPic) {
		this.lotPic = lotPic;
	}

	public String artPicAndLotPic(){
		return getCntnrIdentif() + "_" + getLotPic();
	}

	public String getStkMvntIdentif() {
		return stkMvntIdentif;
	}

	public void setStkMvntIdentif(String stkMvntIdentif) {
		this.stkMvntIdentif = stkMvntIdentif;
	}

	@Override
	protected String makeIdentif() {
		return getCntnrIdentif() + "_" + getLotPic() + "_" + getSeqNbr();
	}
}