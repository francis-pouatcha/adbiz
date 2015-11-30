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
 * The delivery item number is also supposed to be unique. So the delivery item
 * number is the container identifier.
 * 
 * @author francis
 *
 */
@MappedSuperclass
@Description("PrcmtDelivery_description")
public class PrcmtAbstractDlvryItem2Ou extends CoreAbstIdentifObject {

	private static final long serialVersionUID = 1040392867591584841L;

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
		return toId(cntnrIdentif, rcvngOrgUnit);
	}
	
	public static String toId(String dlvryItemNbr, String rcvngOrgUnit){
		return dlvryItemNbr + "_" + rcvngOrgUnit;
	}
}