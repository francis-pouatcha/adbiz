/**
 * 
 */
package org.adorsys.adcshdwr.api;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.adorsys.adcshdwr.jpa.CdrDrctSales;
import org.adorsys.adcshdwr.print.api.PrintMode;

/**
 * @author boriswaguia
 *
 */
public class CdrDsArtHolder {
	
	private CdrDrctSales cdrDrctSales;
	private BigDecimal paidAmt;//amt given by the customer
	private BigDecimal changeAmt;//change
	
	private PrintMode printMode;
	
	private List<CdrDsArtItemHolder> items = new ArrayList<CdrDsArtItemHolder>();

	public CdrDrctSales getCdrDrctSales() {
		return cdrDrctSales;
	}

	public void setCdrDrctSales(CdrDrctSales cdrDrctSales) {
		this.cdrDrctSales = cdrDrctSales;
	}

	public List<CdrDsArtItemHolder> getItems() {
		return items;
	}

	public void setItems(List<CdrDsArtItemHolder> items) {
		this.items = items;
	}

	public BigDecimal getPaidAmt() {
		return paidAmt;
	}

	public void setPaidAmt(BigDecimal paidAmt) {
		this.paidAmt = paidAmt;
	}

	public BigDecimal getChangeAmt() {
		return changeAmt;
	}

	public void setChangeAmt(BigDecimal changeAmt) {
		this.changeAmt = changeAmt;
	}
	
	

	public PrintMode getPrintMode() {
		return printMode;
	}

	public void setPrintMode(PrintMode printMode) {
		this.printMode = printMode;
	}

	@Override
	public String toString() {
		return "CdrDsArtHolder [cdrDrctSales=" + cdrDrctSales + ", items="
				+ items + "]";
	}

}
