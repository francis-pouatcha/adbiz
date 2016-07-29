package org.adorsys.adprocmt.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstBsnsObject;

@MappedSuperclass
@Description("PrcmtDelivery_description")
public abstract class PrcmtAbstractDelivery extends CoreAbstBsnsObject {
	private static final long serialVersionUID = 6822137801850663249L;

	@Column(unique=true)
	@Description("PrcmtDelivery_dlvrySlipNbr_description")
	//@NotNull(message = "PrcmtDelivery_dlvrySlipNbr_NotNull_validation")
	private String dlvrySlipNbr;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("PrcmtDelivery_dtOnDlvrySlip_description")
	private Date dtOnDlvrySlip;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("PrcmtDelivery_orderDt_description")
	private Date orderDt;
	
	public String getDlvrySlipNbr() {
		return this.dlvrySlipNbr;
	}

	public void setDlvrySlipNbr(final String dlvrySlipNbr) {
		this.dlvrySlipNbr = dlvrySlipNbr;
	}

	public Date getDtOnDlvrySlip() {
		return this.dtOnDlvrySlip;
	}

	public void setDtOnDlvrySlip(final Date dtOnDlvrySlip) {
		this.dtOnDlvrySlip = dtOnDlvrySlip;
	}

	public Date getOrderDt() {
		return this.orderDt;
	}

	public void setOrderDt(final Date orderDt) {
		this.orderDt = orderDt;
	}
}