package org.adorsys.adprocmt.jpa;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;

/**
 * The delivery item number is the container identifier.
 * 
 * @author fpo
 *
 */
@MappedSuperclass
@Description("PrcmtDelivery_description")
public class PrcmtAbstractDlvryItem2POItem extends CoreAbstIdentifObject {
	private static final long serialVersionUID = 1040392867591584841L;

	@Column
	@Description("PrcmtProcOrder_poItemNbr_description")
	@NotNull
	private String poItemNbr;

	/*
	 * The quantity ordered. If available could also be found in the order item. 
	 */
	@Column
	@Description("PrcmtDlvryItem_qtyOrdered_description")
	private BigDecimal qtyOrdered;
	
	@Column
	@Description("PrcmtDlvryItem_qtyDlvrd_description")
	//@NotNull when we transform order to delivery, we dont konw yet the qtyDlvrd
	private BigDecimal qtyDlvrd;
	
	@Column
	@Description("PrcmtDlvryItem_freeQty_description")
	private BigDecimal freeQty;

	public String getPoItemNbr() {
		return poItemNbr;
	}


	public void setPoItemNbr(String poItemNbr) {
		this.poItemNbr = poItemNbr;
	}


	public BigDecimal getQtyDlvrd() {
		return qtyDlvrd;
	}


	public void setQtyDlvrd(BigDecimal qtyDlvrd) {
		this.qtyDlvrd = qtyDlvrd;
	}


	public BigDecimal getFreeQty() {
		return freeQty;
	}


	public void setFreeQty(BigDecimal freeQty) {
		this.freeQty = freeQty;
	}

	public BigDecimal getQtyOrdered() {
		return qtyOrdered;
	}

	public void setQtyOrdered(BigDecimal qtyOrdered) {
		this.qtyOrdered = qtyOrdered;
	}

	@Override
	protected String makeIdentif() {
		return toId(cntnrIdentif, poItemNbr);
	}
	
	public static String toId(String dlvryItemNbr, String poItemNbr){
		return dlvryItemNbr + "_" + poItemNbr;
	}
}