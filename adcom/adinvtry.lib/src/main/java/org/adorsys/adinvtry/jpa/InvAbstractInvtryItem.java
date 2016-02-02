package org.adorsys.adinvtry.jpa;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstBsnsItem;
import org.adorsys.adcore.utils.BigDecimalUtils;

/**
 * The gap is the target qty.
 * 
 * @author francis
 *
 */
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
	

	protected void normalize(){
		this.expectedQty = BigDecimalUtils.zeroIfNull(this.expectedQty);
		this.asseccedQty = BigDecimalUtils.zeroIfNull(this.asseccedQty);
		setTrgtQty(BigDecimalUtils.subs(this.asseccedQty, this.expectedQty));
		super.normalize();
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
	
}