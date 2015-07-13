package org.adorsys.adstock.jpa;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;

/**
 * The assignment of a certain quantity of a lot to an organization unit.
 * 
 * @author francis
 *
 */
@Entity
@Description("StkArticleLot2Ou_description")
public class StkArticleLot2Ou extends CoreAbstIdentifObject {

	private static final long serialVersionUID = -1491990020460153912L;

	@Column
	@Description("StkArticleLot2Ou_lotPic_description")
	@NotNull
	private String lotPic;

	@Column
	@Description("StkArticleLot2Ou_artPic_description")
	@NotNull
	private String artPic;

	@Column
	@Description("StkArticleLot2Ou_orgUnit_description")
	@NotNull
	private String orgUnit;

	@Column
	@Description("StkArticleLot2Ou_qty_description")
	@NotNull
	private BigDecimal qty;

	public String getLotPic() {
		return lotPic;
	}

	public void setLotPic(String lotPic) {
		this.lotPic = lotPic;
	}

	public String getArtPic() {
		return artPic;
	}

	public void setArtPic(String artPic) {
		this.artPic = artPic;
	}

	public String getOrgUnit() {
		return orgUnit;
	}

	public void setOrgUnit(String orgUnit) {
		this.orgUnit = orgUnit;
	}

	public BigDecimal getQty() {
		return qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}

	@Override
	protected String makeIdentif() {
		return toId(artPic, lotPic, orgUnit);
	}
	
	public static String toId(String artPic, String lotPic, String orgUnit){
		return artPic + "_" + lotPic + "_" + orgUnit;
	}
}