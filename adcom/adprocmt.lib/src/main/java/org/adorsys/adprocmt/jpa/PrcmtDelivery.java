package org.adorsys.adprocmt.jpa;

import javax.persistence.Entity;

import org.adorsys.adcore.jpa.CoreAbstBsnsObjectHeader;
import org.adorsys.javaext.description.Description;

@Entity
@Description("PrcmtDelivery_description")
public class PrcmtDelivery extends PrcmtAbstractDelivery {
	private static final long serialVersionUID = -644543225855706107L;

	public void fillDataFromOrder(PrcmtProcOrder procOrder) {
		procOrder.copyTo(this);
		CoreAbstBsnsObjectHeader header = new CoreAbstBsnsObjectHeader();
		header.copyTo(this);
	}

	
}