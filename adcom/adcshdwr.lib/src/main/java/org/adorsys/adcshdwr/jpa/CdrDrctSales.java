package org.adorsys.adcshdwr.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstBsnsObject;

/**
 * identif is cdrNbr
 * 
 * @author fpo
 *
 */
@Entity
@Description("CdrDrctSales_description")
public class CdrDrctSales extends CoreAbstBsnsObject {
	private static final long serialVersionUID = -4463478926303975708L;
	
	@Column
	@Description("CdrDrctSales_invoiceNbr_description")
	private String invoiceNbr;

	@Column
	@Description("CdrDrctSales_cdrNbr_description")
	private String cdrNbr;

	@Column
	@Description("CdrDrctSales_pymntNbr_description")
	private String pymntNbr;
	
	public String getInvoiceNbr() {
		return invoiceNbr;
	}

	public void setInvoiceNbr(String invoiceNbr) {
		this.invoiceNbr = invoiceNbr;
	}

	public String getCdrNbr() {
		return cdrNbr;
	}

	public void setCdrNbr(String cdrNbr) {
		this.cdrNbr = cdrNbr;
	}

	public String getPymntNbr() {
		return pymntNbr;
	}

	public void setPymntNbr(String pymntNbr) {
		this.pymntNbr = pymntNbr;
	}
}