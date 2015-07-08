package org.adorsys.adinvtry.jpa;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.adorsys.adcore.jpa.CoreAbstBsnsItem;
import org.adorsys.adcore.utils.BigDecimalUtils;
import org.adorsys.adcore.utils.FinancialOps;
import org.adorsys.javaext.description.Description;

@MappedSuperclass
@Description("InvInvtryItem_description")
public abstract class InvAbstractInvtryItem extends CoreAbstBsnsItem {

	private static final long serialVersionUID = 4195806327796087791L;

	@Column
	@Description("InvInvtryItem_expectedQty_description")
	private BigDecimal expectedQty;

	@Column
	@Description("InvInvtryItem_asseccedQty_description")
	private BigDecimal asseccedQty;

	@Column
	@Description("InvInvtryItem_gap_description")
	private BigDecimal gap;

	protected void normalize(){
		this.expectedQty = BigDecimalUtils.zeroIfNull(this.expectedQty);
		this.asseccedQty = BigDecimalUtils.zeroIfNull(this.asseccedQty);
		this.gap = BigDecimalUtils.subs(this.expectedQty,this.asseccedQty);
		super.normalize();
	}

	@Override
	public void evlte() {
		normalize();
		setSlsGrossPrcPreTax(FinancialOps.qtyTmsPrice(this.gap, getSlsUnitPrcPreTax(), getSlsUnitPrcCur()));
		setPrchGrossPrcPreTax(FinancialOps.qtyTmsPrice(this.gap, getPrchUnitPrcPreTax(), getPrchUnitPrcCur()));
		setStkValPreTax(FinancialOps.qtyTmsPrice(this.gap, getStkUnitValPreTax(), getStkUnitValCur()));
	}

	public BigDecimal getExpectedQty() {
		return this.expectedQty;
	}

	public void setExpectedQty(final BigDecimal expectedQty) {
		this.expectedQty = expectedQty;
	}

	public BigDecimal getAsseccedQty() {
		return this.asseccedQty;
	}

	public void setAsseccedQty(final BigDecimal asseccedQty) {
		this.asseccedQty = asseccedQty;
	}

	public BigDecimal getGap() {
		return this.gap;
	}

	public void setGap(final BigDecimal gap) {
		this.gap = gap;
	}
	
	public boolean contentEquals(InvAbstractInvtryItem target) {
		if (!super.contentEquals(target)) return false;
		if(!BigDecimalUtils.numericEquals(target.asseccedQty,this.asseccedQty)) return false;
		if(!BigDecimalUtils.numericEquals(target.expectedQty,this.expectedQty)) return false;
		if(!BigDecimalUtils.numericEquals(target.gap,this.gap)) return false;
		return true;
	}
}