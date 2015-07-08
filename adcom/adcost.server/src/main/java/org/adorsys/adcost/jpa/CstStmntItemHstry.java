package org.adorsys.adcost.jpa;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.adorsys.adcore.jpa.CoreAbstEntityHstry;
import org.adorsys.javaext.description.Description;

@Entity
public class CstStmntItemHstry extends CoreAbstEntityHstry {

	private static final long serialVersionUID = -6986222916744051974L;

	/*
	 * This field is used to track some quantitative history informations.
	 * For example, it could be used to track part of a multi step delivery.
	 */
	@Column
	@Description("CstStmntItemHstry_qtyInfo_description")
	private BigDecimal qtyInfo;

	public BigDecimal getQtyInfo() {
		return qtyInfo;
	}

	public void setQtyInfo(BigDecimal qtyInfo) {
		this.qtyInfo = qtyInfo;
	}
}
