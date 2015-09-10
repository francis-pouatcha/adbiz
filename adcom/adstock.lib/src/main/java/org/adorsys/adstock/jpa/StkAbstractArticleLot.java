package org.adorsys.adstock.jpa;

import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstBsnsItem;

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

	@Override
	public String toString() {
		return "StkAbstractArticleLot [stkgDt=" + stkgDt + ", closedDt="
				+ closedDt + ", getAcsngUser()=" + getAcsngUser()
				+ ", getLotPic()=" + getLotPic() + ", getArtPic()="
				+ getArtPic() + ", getExpirDt()=" + getExpirDt()
				+ ", getSupplierPic()=" + getSupplierPic() + ", getSupplier()="
				+ getSupplier() + ", getSection()=" + getSection()
				+ ", getPrchUnitPrcPreTax()=" + getPrchUnitPrcPreTax()
				+ ", getPrchUnitPrcCur()=" + getPrchUnitPrcCur()
				+ ", getPrchGrossPrcPreTax()=" + getPrchGrossPrcPreTax()
				+ ", getPrchNetPrcPreTax()=" + getPrchNetPrcPreTax()
				+ ", getPrchVatPct()=" + getPrchVatPct() + ", getPrchVatAmt()="
				+ getPrchVatAmt() + ", getSlsUnitPrcPreTax()="
				+ getSlsUnitPrcPreTax() + ", getSlsUnitPrcCur()="
				+ getSlsUnitPrcCur() + ", getSlsGrossPrcPreTax()="
				+ getSlsGrossPrcPreTax() + ", getSlsNetPrcPreTax()="
				+ getSlsNetPrcPreTax() + ", getSlsVatPct()=" + getSlsVatPct()
				+ ", getSlsVatAmt()=" + getSlsVatAmt()
				+ ", getSlsNetPrcTaxIncl()=" + getSlsNetPrcTaxIncl()
				+ ", getPrchUnitPrcTaxIncl()=" + getPrchUnitPrcTaxIncl()
				+ ", getPrchVatUnitAmt()=" + getPrchVatUnitAmt()
				+ ", getSlsUnitPrcTaxIncl()=" + getSlsUnitPrcTaxIncl()
				+ ", getSlsVatUnitAmt()=" + getSlsVatUnitAmt()
				+ ", getStkUnitValCur()=" + getStkUnitValCur()
				+ ", getTrgtQty()=" + getTrgtQty() + ", getValueDt()="
				+ getValueDt() + "]";
	}

	
	
	
	
	
}