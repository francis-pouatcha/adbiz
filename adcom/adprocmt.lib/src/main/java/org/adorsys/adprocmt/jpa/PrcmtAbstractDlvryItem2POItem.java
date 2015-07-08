package org.adorsys.adprocmt.jpa;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.CoreAbstIdentifData;
import org.adorsys.adcore.utils.BigDecimalUtils;
import org.adorsys.javaext.description.Description;
import org.apache.commons.lang3.StringUtils;

@MappedSuperclass
@Description("PrcmtDelivery_description")
public class PrcmtAbstractDlvryItem2POItem extends CoreAbstIdentifData {
	private static final long serialVersionUID = 1040392867591584841L;

	@Column
	@Description("PrcmtDelivery_dlvryItemNbr_description")
	@NotNull
	private String dlvryItemNbr;

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
	@NotNull
	private BigDecimal qtyDlvrd;
	
	@Column
	@Description("PrcmtDlvryItem_freeQty_description")
	private BigDecimal freeQty;

	public String getDlvryItemNbr() {
		return dlvryItemNbr;
	}

	public void setDlvryItemNbr(String dlvryItemNbr) {
		this.dlvryItemNbr = dlvryItemNbr;
	}


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
		return toId(dlvryItemNbr, poItemNbr);
	}
	
	public static String toId(String dlvryItemNbr, String poItemNbr){
		return dlvryItemNbr + "_" + poItemNbr;
	}
	public void copyTo(PrcmtAbstractDlvryItem2POItem target){
		target.dlvryItemNbr=dlvryItemNbr;
		target.poItemNbr=poItemNbr;
		target.qtyOrdered=qtyOrdered;
		target.qtyDlvrd=qtyDlvrd;
		target.freeQty=freeQty;
	}	

	public boolean contentEquals(PrcmtAbstractDlvryItem2POItem target){
		if(!BigDecimalUtils.numericEquals(target.freeQty,freeQty)) return false;
		if(!BigDecimalUtils.numericEquals(target.qtyDlvrd,qtyDlvrd)) return false;
		if(!BigDecimalUtils.numericEquals(target.qtyOrdered,qtyOrdered)) return false;
		if(!StringUtils.equals(target.dlvryItemNbr, dlvryItemNbr)) return false;
		if(!StringUtils.equals(target.poItemNbr,poItemNbr)) return false;
		return true;
	}
}