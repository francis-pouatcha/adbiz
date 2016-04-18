package org.adorsys.adinvtry.jpa;

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

	protected void normalize(){
		setTrgtQty(BigDecimalUtils.subs(getAsseccedQty(), getExpectedQty()));
		super.normalize();
	}
}