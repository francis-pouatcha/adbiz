package org.adorsys.adcore.jpa;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.utils.BigDecimalUtils;
import org.adorsys.adcore.utils.FinancialOps;
import org.jboss.annotation.javaee.Description;

@MappedSuperclass
public abstract class CoreAbstTxctedItem extends CoreAbstBsnsItem {

	private static final long serialVersionUID = 6828053643617751154L;

	/*
	 * The quantity delivered. This can match the quantity ordered or not.
	 * In the normal case, qtyOrdered = qtyDlvrd - freeQty = qtyBilled
	 * 
	 * But in some cases the delivery might be erroneous.
	 * 
	 */
	@Column
	@Description("PrcmtDlvryItem_qtyDlvrd_description")
	@NotNull
	private BigDecimal qtyDlvrd;
	
	/*
	 * The free quantity. This might be a promotion of the supplier.
	 * 
	 * This information is generally not available in the procurement order.
	 */
	@Column
	@Description("PrcmtDlvryItem_freeQty_description")
	private BigDecimal freeQty;

	/*
	 * The quantity on the invoice. This might be less the the quantity delivered or
	 * the quantity expected to be delivered. In which case a claim process might 
	 * automatically be started by this application.
	 */
	@Column
	@Description("PrcmtDlvryItem_qtyBilled_description")
	private BigDecimal qtyBilled;

	@Column
	private Boolean rtndItem;
	
	/*
	 * This is the original itemNbr, that can be used to track
	 * the source of a returned item.
	 * 
	 * When this is not null, we reuse the rebates defined 
	 * there to process the return.
	 */
	@Column
	private String origItemNbr;

	public BigDecimal getQtyDlvrd() {
		return this.qtyDlvrd;
	}

	public void setQtyDlvrd(final BigDecimal qtyDlvrd) {
		this.qtyDlvrd = qtyDlvrd;
	}

	public BigDecimal getFreeQty() {
		return this.freeQty;
	}

	public void setFreeQty(final BigDecimal freeQty) {
		this.freeQty = freeQty;
	}

	public BigDecimal getQtyBilled() {
		return qtyBilled;
	}

	public void setQtyBilled(BigDecimal qtyBilled) {
		this.qtyBilled = qtyBilled;
	}

	public Boolean getRtndItem() {
		return rtndItem;
	}

	public void setRtndItem(Boolean rtndItem) {
		this.rtndItem = rtndItem;
	}

	public String getOrigItemNbr() {
		return origItemNbr;
	}

	public void setOrigItemNbr(String origItemNbr) {
		this.origItemNbr = origItemNbr;
	}
	
	@Override
	public void setAssdQty(BigDecimal assdQty) {
		super.setAssdQty(assdQty);
		setQtyDlvrd(assdQty);
	}

	public boolean contentEquals(CoreAbstTxctedItem target){
		if(!BigDecimalUtils.numericEquals(target.qtyDlvrd,qtyDlvrd)) return false;
		if(!BigDecimalUtils.numericEquals(target.freeQty,freeQty)) return false;
		if(!BigDecimalUtils.numericEquals(target.qtyBilled,qtyBilled)) return false;
		return true;
	}
	
	public void addQtyDlvrd(BigDecimal qtyDlvrd) {
		this.qtyDlvrd = BigDecimalUtils.sum(this.qtyDlvrd, qtyDlvrd);
	}

	public void addFreeQty(BigDecimal freeQty) {
		this.freeQty = BigDecimalUtils.sum(this.freeQty, freeQty);
	}

	public void addQtyBilled(BigDecimal qtyBilled) {
		this.qtyBilled = BigDecimalUtils.sum(this.qtyBilled, qtyBilled);
	}

	private void evltePrch() {
		qtyBilled = BigDecimalUtils.subs(qtyDlvrd, freeQty);
		if(Boolean.TRUE.equals(this.rtndItem)){
			qtyDlvrd = BigDecimalUtils.negate(qtyDlvrd);
			freeQty = BigDecimalUtils.negate(freeQty);
			if(getPrchRstckgFeesType()==CoreRstkgFeesType.FLAT_ONCE || getPrchRstckgFeesType()==CoreRstkgFeesType.FLAT_PER_RTRN){
				BigDecimal grossPrchsd = FinancialOps.qtyTmsPrice(qtyBilled, getPrchUnitPrcPreTax(), getPrchUnitPrcCur());
				grossPrchsd = FinancialOps.substract(grossPrchsd, getPrchRstckgFeesPreTax(), getPrchUnitPrcCur());
				setPrchGrossPrcPreTax(BigDecimalUtils.negate(grossPrchsd));
			} else {
				BigDecimal unitPricePreTax = BigDecimalUtils.subs(getPrchUnitPrcPreTax(), getPrchRstckgUnitFeesPreTax());
				BigDecimal grossPrchsd = FinancialOps.qtyTmsPrice(qtyBilled, unitPricePreTax, getPrchUnitPrcCur());
				setPrchGrossPrcPreTax(BigDecimalUtils.negate(grossPrchsd));
			}
		} else {
			setPrchGrossPrcPreTax(FinancialOps.qtyTmsPrice(qtyBilled, getPrchUnitPrcPreTax(), getPrchUnitPrcCur()));
		}
		computePrchNetPrcTaxIncl();
	}

	private void evlteSls() {
		qtyBilled = BigDecimalUtils.subs(qtyDlvrd, freeQty);
		if(Boolean.TRUE.equals(this.rtndItem)){
			qtyDlvrd = BigDecimalUtils.negate(qtyDlvrd);
			freeQty = BigDecimalUtils.negate(freeQty);
			if(getSlsRstckgFeesType()==CoreRstkgFeesType.FLAT_ONCE || getSlsRstckgFeesType()==CoreRstkgFeesType.FLAT_PER_RTRN){
				BigDecimal grossSold = FinancialOps.qtyTmsPrice(qtyBilled, getSlsUnitPrcPreTax(), getSlsUnitPrcCur());
				grossSold = FinancialOps.substract(grossSold, getSlsRstckgFeesPreTax(), getSlsUnitPrcCur());
				setSlsGrossPrcPreTax(BigDecimalUtils.negate(grossSold));
			} else {
				BigDecimal unitPricePreTax = BigDecimalUtils.subs(getSlsUnitPrcPreTax(), getSlsRstckgUnitFeesPreTax());
				BigDecimal grossSold = FinancialOps.qtyTmsPrice(qtyBilled, unitPricePreTax, getSlsUnitPrcCur());
				setSlsGrossPrcPreTax(BigDecimalUtils.negate(grossSold));
			}
		} else {
			setSlsGrossPrcPreTax(FinancialOps.qtyTmsPrice(qtyBilled, getSlsUnitPrcPreTax(), getSlsUnitPrcCur()));
		}
		computeSlsNetPrcTaxIncl();
	}
	
	public void evlte() {
		normalize();
		evltePrch();
		evlteSls();
	}
	protected void normalize(){
		qtyDlvrd = BigDecimalUtils.zeroIfNull(qtyDlvrd);
		freeQty = BigDecimalUtils.zeroIfNull(freeQty);
		qtyBilled=BigDecimalUtils.zeroIfNull(qtyBilled);
		super.normalize();
	}
}
