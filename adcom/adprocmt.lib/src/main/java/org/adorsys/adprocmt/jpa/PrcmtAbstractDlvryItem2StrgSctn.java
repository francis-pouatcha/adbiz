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
public abstract class PrcmtAbstractDlvryItem2StrgSctn extends CoreAbstIdentifData {

	private static final long serialVersionUID = 2920134612071462983L;

	@Column
	@Description("PrcmtDelivery_dlvryItemNbr_description")
	@NotNull
	private String dlvryItemNbr;

	@Column
	@Description("PrcmtProcOrder_strgSection_description")
	@NotNull
	private String strgSection;

	@Column
	@Description("PrcmtDlvryItem_qtyDlvrd_description")
	@NotNull
	private BigDecimal qtyStrd;

	/*
	 * The article quantity estimated to be in stock before this delivery.
	 */
	@Column
	@Description("PrcmtDlvryItem_stkQtyPreDlvry_description")
	private BigDecimal stkQtyPreDlvry;
	
	public String getDlvryItemNbr() {
		return dlvryItemNbr;
	}

	public void setDlvryItemNbr(String dlvryItemNbr) {
		this.dlvryItemNbr = dlvryItemNbr;
	}

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
		return toId(dlvryItemNbr, strgSection);
	}
	
	public void copyTo(PrcmtAbstractDlvryItem2StrgSctn target){
		target.dlvryItemNbr = dlvryItemNbr;
		target.strgSection=strgSection;
		target.stkQtyPreDlvry = stkQtyPreDlvry;
		target.qtyStrd = qtyStrd;
	}
	
	public boolean contentEquals(PrcmtAbstractDlvryItem2StrgSctn target){
		if(!BigDecimalUtils.numericEquals(target.stkQtyPreDlvry,stkQtyPreDlvry)) return false;
		if(!BigDecimalUtils.numericEquals(target.qtyStrd,qtyStrd)) return false;
		if(!StringUtils.equals(target.dlvryItemNbr, dlvryItemNbr)) return false;
		if(!StringUtils.equals(target.strgSection,strgSection)) return false;
		return true;
	}
	
}