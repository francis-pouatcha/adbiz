/**
 * 
 */
package org.adorsys.adstock.rest.extension.invtry;

import java.util.List;

import org.adorsys.adstock.jpa.StkAbstractArticleLot;
import org.adorsys.adstock.jpa.StkArticleLot2Ou;
import org.adorsys.adstock.jpa.StkLotStockQty;


/**
 * @author boriswaguia
 *
 */
public class StkArticleLotDTO {
	
	/**
	 * The artPic field.
	 */
	private String artPic;
	/**
	 * The articleLot2Ou field.
	 */
	private StkArticleLot2Ou articleLot2Ou;
	/**
	 * The stkArticleLot field.
	 */
	private StkAbstractArticleLot stkArticleLot;

	/**
	 * The stkLotStkQtys field.
	 */
	private List<StkLotStockQty> stkLotStkQtys;
	
	public String getArtPic() {
		return artPic;
	}
	public void setArtPic(String artPic) {
		this.artPic = artPic;
	}
	public StkArticleLot2Ou getArticleLot2Ou() {
		return articleLot2Ou;
	}
	public void setArticleLot2Ou(StkArticleLot2Ou articleLot2Ou) {
		this.articleLot2Ou = articleLot2Ou;
	}
	public StkAbstractArticleLot getStkArticleLot() {
		return stkArticleLot;
	}
	public void setStkArticleLot(StkAbstractArticleLot stkArticleLot) {
		this.stkArticleLot = stkArticleLot;
	}

	public List<StkLotStockQty> getStkLotStkQtys() {
		return stkLotStkQtys;
	}
	public void setStkLotStkQtys(List<StkLotStockQty> stkLotStkQtys) {
		this.stkLotStkQtys = stkLotStkQtys;
	}
	
}