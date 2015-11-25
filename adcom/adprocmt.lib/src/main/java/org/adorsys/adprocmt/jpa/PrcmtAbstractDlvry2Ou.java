package org.adorsys.adprocmt.jpa;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;
import org.adorsys.adcore.utils.BigDecimalUtils;
import org.apache.commons.lang3.StringUtils;

@MappedSuperclass
@Description("PrcmtDelivery_description")
public abstract class PrcmtAbstractDlvry2Ou extends CoreAbstIdentifObject {

	private static final long serialVersionUID = 69587852651621436L;

	@Column
	@Description("PrcmtDelivery_dlvryNbr_description")
	@NotNull
	private String dlvryNbr;

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
	
	public String getDlvryNbr() {
		return dlvryNbr;
	}

	public void setDlvryNbr(String dlvryNbr) {
		this.dlvryNbr = dlvryNbr;
	}

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
		return toId(dlvryNbr, rcvngOrgUnit);
	}
	
	public void copyTo(PrcmtAbstractDlvry2Ou target){
		target.dlvryNbr=dlvryNbr;
		target.rcvngOrgUnit=rcvngOrgUnit;
		target.qtyPct=qtyPct;
	}
	
	public boolean contentEquals(PrcmtAbstractDlvry2Ou target){
		if(target==null) return false;
		if(!StringUtils.equals(dlvryNbr, target.dlvryNbr)) return false;
		if(!StringUtils.equals(rcvngOrgUnit, target.rcvngOrgUnit)) return false;
		if(!BigDecimalUtils.numericEquals(qtyPct, target.qtyPct)) return false;
		return true;
	}
}