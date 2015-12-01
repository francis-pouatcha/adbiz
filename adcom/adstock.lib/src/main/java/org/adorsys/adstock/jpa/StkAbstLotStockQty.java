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

	public String getLotPic() {
		return lotPic;
	}

	public void setLotPic(String lotPic) {
		this.lotPic = lotPic;
	}

	public String artPicAndLotPic(){
		return getCntnrIdentif() + "_" + getLotPic();
	}

	@Override
	protected String makeIdentif() {
		return getCntnrIdentif() + "_" + getLotPic() + "_" + getSeqNbr();
	}
}