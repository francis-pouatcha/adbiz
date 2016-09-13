package org.adorsys.adprocmt.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.adorsys.adcore.annotation.Description;

@Entity
@Description("PrcmtDelivery_description")
public class PrcmtDelivery extends PrcmtAbstractDelivery {
	private static final long serialVersionUID = -644543225855706107L;

	public void fillDataFromOrder(PrcmtProcOrder procOrder) {
		procOrder.copyTo(this);
		resetHeader();
	}
	
	
}