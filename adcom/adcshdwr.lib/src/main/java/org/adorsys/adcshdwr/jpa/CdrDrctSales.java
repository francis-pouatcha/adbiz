package org.adorsys.adcshdwr.jpa;

import javax.persistence.Entity;

import org.adorsys.adcore.jpa.CoreAbstBsnsObject;
import org.adorsys.javaext.description.Description;

@Entity
@Description("CdrDrctSales_description")
public class CdrDrctSales extends CoreAbstBsnsObject {
	private static final long serialVersionUID = -4463478926303975708L;
	
	private String cdrNbr;

	public String getCdrNbr() {
		return cdrNbr;
	}

	public void setCdrNbr(String cdrNbr) {
		this.cdrNbr = cdrNbr;
	}
}