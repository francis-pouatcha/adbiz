package org.adorsys.adprocmt.jpa;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;

@MappedSuperclass
@Description("PrcmtDelivery_description")
public abstract class PrcmtAbstractDlvry2PO extends CoreAbstIdentifObject {

	private static final long serialVersionUID = -586699504276634188L;

	@Column
	@Description("PrcmtDelivery_dlvryNbr_description")
	@NotNull
	private String dlvryNbr;

	/*
	 * A procurement order associated with this delivery.
	 * A delivery can span many procurement order. A PO can also
	 * apear in many deliveries.
	 * 
	 */
	@Column
	@Description("PrcmtProcOrder_poNbr_description")
	@NotNull
	private String poNbr;

	public String getDlvryNbr() {
		return dlvryNbr;
	}

	public void setDlvryNbr(String dlvryNbr) {
		this.dlvryNbr = dlvryNbr;
	}

	public String getPoNbr() {
		return poNbr;
	}

	public void setPoNbr(String poNbr) {
		this.poNbr = poNbr;
	}

	@Override
	protected String makeIdentif() {
		return toId(dlvryNbr, poNbr);
	}
	
	public static String toId(String dlvryNbr, String poNbr){
		return dlvryNbr + "_" + poNbr;
	}	
}