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
@Description("PrcmtProcOrder_description")
public abstract class PrcmtAbstractProcOrder extends CoreAbstBsnsObject {

	private static final long serialVersionUID = 2603268059857640820L;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("PrcmtProcOrder_submitedDt_description")
	private Date submitedDt;

	@Column
	@Description("PrcmtProcOrder_poTriggerMode_description")
	@NotNull
	private String poTriggerMode;

	public Date getSubmitedDt() {
		return this.submitedDt;
	}

	public void setSubmitedDt(final Date submitedDt) {
		this.submitedDt = submitedDt;
	}

	public String getPoTriggerMode() {
		return poTriggerMode;
	}

	public void setPoTriggerMode(String poTriggerMode) {
		this.poTriggerMode = poTriggerMode;
	}
}