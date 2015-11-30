package org.adorsys.adprocmt.jpa;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;

@MappedSuperclass
@Description("PrcmtAbstractPOItem2StrgSctn_description")
public abstract class PrcmtAbstractPOItem2StrgSctn extends CoreAbstIdentifObject {

	private static final long serialVersionUID = 6494573297223822690L;

	@Column
	@Description("PrcmtAbstractPOItem2StrgSctn_strgSection_description")
	@NotNull
	private String strgSection;

	@Column
	@Description("PrcmtAbstractPOItem2StrgSctn_qtyDlvrd_description")
	@NotNull
	private BigDecimal qtyStrd;

	/*
	 * The article quantity estimated to be in stock before this delivery.
	 */
	@Column
	@Description("PrcmtAbstractPOItem2StrgSctn_stkQtyPreDlvry_description")
	private BigDecimal stkQtyPreDlvry;

	public String getStrgSection() {
		return strgSection;
	}

	public void setStrgSection(String strgSection) {
		this.strgSection = strgSection;
	}

	public BigDecimal getQtyStrd() {
		return qtyStrd;
	}

	public void setQtyStrd(BigDecimal qtyStrd) {
		this.qtyStrd = qtyStrd;
	}

	public BigDecimal getStkQtyPreDlvry() {
		return stkQtyPreDlvry;
	}

	public void setStkQtyPreDlvry(BigDecimal stkQtyPreDlvry) {
		this.stkQtyPreDlvry = stkQtyPreDlvry;
	}

	public static String toId(String dlvryItemNbr, String strgSection){
		return dlvryItemNbr + "_" + strgSection;
	}
	@Override
	protected String makeIdentif() {
		return toId(cntnrIdentif, strgSection);
	}
}