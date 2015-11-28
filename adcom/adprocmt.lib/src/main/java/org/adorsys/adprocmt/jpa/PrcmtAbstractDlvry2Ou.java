package org.adorsys.adprocmt.jpa;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;

/**
 * The container identifier is the delivery number.
 * 
 * @author fpo
 *
 */
@MappedSuperclass
@Description("PrcmtDelivery_description")
public abstract class PrcmtAbstractDlvry2Ou extends CoreAbstIdentifObject {

	private static final long serialVersionUID = 69587852651621436L;

	@Column
	@Description("PrcmtProcOrder_rcvngOrgUnit_description")
	@NotNull
	private String rcvngOrgUnit;

	/*
	 * This is used to compute the quantity of received goods delivered 
	 * to be assigned to this org unit for each delivery item.
	 */
	@Column
	@Description("PrcmtDlvryItem_rebatePct_description")
	private BigDecimal qtyPct;

	public String getRcvngOrgUnit() {
		return rcvngOrgUnit;
	}

	public void setRcvngOrgUnit(String rcvngOrgUnit) {
		this.rcvngOrgUnit = rcvngOrgUnit;
	}

	public BigDecimal getQtyPct() {
		return qtyPct;
	}

	public void setQtyPct(BigDecimal qtyPct) {
		this.qtyPct = qtyPct;
	}

	public static String toId(String dlvryNbr, String rcvngOrgUnit){
		return dlvryNbr + "_" + rcvngOrgUnit;
	}

	@Override
	protected String makeIdentif() {
		return toId(getCntnrIdentif(), rcvngOrgUnit);
	}
}