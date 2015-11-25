package org.adorsys.adprocmt.jpa;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;
import org.adorsys.adcore.utils.BigDecimalUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * The assignment of a certain quantity of a delivery item to an organization unit.
 * 
 * @author francis
 *
 */
@MappedSuperclass
@Description("PrcmtDelivery_description")
public class PrcmtAbstractDlvryItem2Ou extends CoreAbstIdentifObject {

	private static final long serialVersionUID = 1040392867591584841L;

	
	@Column
	@Description("PrcmtDelivery_dlvryItemNbr_description")
	@NotNull
	private String dlvryItemNbr;

	@Column
	@Description("PrcmtProcOrder_rcvngOrgUnit_description")
	@NotNull
	private String rcvngOrgUnit;

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

	public String getRcvngOrgUnit() {
		return rcvngOrgUnit;
	}

	public void setRcvngOrgUnit(String rcvngOrgUnit) {
		this.rcvngOrgUnit = rcvngOrgUnit;
	}

	@Override
	protected String makeIdentif() {
		return toId(dlvryItemNbr, rcvngOrgUnit);
	}
	
	public static String toId(String dlvryItemNbr, String rcvngOrgUnit){
		return dlvryItemNbr + "_" + rcvngOrgUnit;
	}
	
	public void copyTo(PrcmtAbstractDlvryItem2Ou target){
		target.dlvryItemNbr = dlvryItemNbr;
		target.freeQty=freeQty;
		target.qtyDlvrd=qtyDlvrd;
		target.rcvngOrgUnit=rcvngOrgUnit;
	}

	public boolean contentEquals(PrcmtAbstractDlvryItem2Ou target){
		if(!BigDecimalUtils.numericEquals(target.freeQty,freeQty)) return false;
		if(!BigDecimalUtils.numericEquals(target.qtyDlvrd,qtyDlvrd)) return false;
		if(!StringUtils.equals(target.dlvryItemNbr, dlvryItemNbr)) return false;
		if(!StringUtils.equals(target.rcvngOrgUnit,rcvngOrgUnit)) return false;
		return true;
	}
}