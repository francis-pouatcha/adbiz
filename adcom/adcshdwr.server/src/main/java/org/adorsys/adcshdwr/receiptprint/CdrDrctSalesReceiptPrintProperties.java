package org.adorsys.adcshdwr.receiptprint;

import org.adorsys.adcore.print.ReceiptPrintProperties;

public class CdrDrctSalesReceiptPrintProperties extends ReceiptPrintProperties {

	private boolean displayGrossPricePreTax;
	private boolean displaySlsRebateAmt;
	private boolean displaySlsVatAmt;
	private boolean displaySlsNetPrcPreTax;
	private boolean displaySlsPymtDscntPct;
	private boolean displaySlsPymtDscntAmt;
	private boolean displaySlsRdngDscntAmt;
	private boolean displaySlsNetPymtAmt;
	private boolean displayTicketMessage;
	private boolean displaySlsNetPrcTaxIncl;
	private String ticketMessage;

	public boolean isDisplayGrossPricePreTax() {
		return displayGrossPricePreTax;
	}

	public boolean isDisplaySlsRebateAmt() {
		return displaySlsRebateAmt;
	}

	public boolean isDisplaySlsVatAmt() {
		return displaySlsVatAmt;
	}

	public boolean isDisplaySlsNetPrcPreTax() {
		return displaySlsNetPrcPreTax;
	}

	public boolean isDisplaySlsPymtDscntPct() {
		return displaySlsPymtDscntPct;
	}

	public boolean isDisplaySlsPymtDscntAmt() {
		return displaySlsPymtDscntAmt;
	}

	public boolean isDisplaySlsRdngDscntAmt() {
		return displaySlsRdngDscntAmt;
	}

	public boolean isDisplaySlsNetPymtAmt() {
		return displaySlsNetPymtAmt;
	}

	public boolean isDisplayTicketMessage() {
		return displayTicketMessage;
	}

	public void setDisplayGrossPricePreTax(boolean displayGrossPricePreTax) {
		this.displayGrossPricePreTax = displayGrossPricePreTax;
	}

	public void setDisplaySlsRebateAmt(boolean displaySlsRebateAmt) {
		this.displaySlsRebateAmt = displaySlsRebateAmt;
	}

	public void setDisplaySlsVatAmt(boolean displaySlsVatAmt) {
		this.displaySlsVatAmt = displaySlsVatAmt;
	}

	public void setDisplaySlsNetPrcPreTax(boolean displaySlsNetPrcPreTax) {
		this.displaySlsNetPrcPreTax = displaySlsNetPrcPreTax;
	}

	public void setDisplaySlsPymtDscntPct(boolean displaySlsPymtDscntPct) {
		this.displaySlsPymtDscntPct = displaySlsPymtDscntPct;
	}

	public void setDisplaySlsPymtDscntAmt(boolean displaySlsPymtDscntAmt) {
		this.displaySlsPymtDscntAmt = displaySlsPymtDscntAmt;
	}

	public void setDisplaySlsRdngDscntAmt(boolean displaySlsRdngDscntAmt) {
		this.displaySlsRdngDscntAmt = displaySlsRdngDscntAmt;
	}

	public void setDisplaySlsNetPymtAmt(boolean displaySlsNetPymtAmt) {
		this.displaySlsNetPymtAmt = displaySlsNetPymtAmt;
	}

	public void setDisplayTicketMessage(boolean displayTicketMessage) {
		this.displayTicketMessage = displayTicketMessage;
	}

	public boolean isDisplaySlsNetPrcTaxIncl() {
		return displaySlsNetPrcTaxIncl;
	}

	public void setDisplaySlsNetPrcTaxIncl(boolean displaySlsNetPrcTaxIncl) {
		this.displaySlsNetPrcTaxIncl = displaySlsNetPrcTaxIncl;
	}

	public String getTicketMessage() {
		return ticketMessage;
	}

	public void setTicketMessage(String ticketMessage) {
		this.ticketMessage = ticketMessage;
	}
	
}
