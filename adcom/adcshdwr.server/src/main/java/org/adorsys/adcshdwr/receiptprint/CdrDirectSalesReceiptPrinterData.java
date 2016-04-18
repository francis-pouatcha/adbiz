package org.adorsys.adcshdwr.receiptprint;

/**
 * Data object for printing a customer receipt.
 * 
 * @author fpo
 *
 */
public class CdrDirectSalesReceiptPrinterData {
	private String drctSalesIdentif;
	private boolean unDiscountReceipt= Boolean.FALSE;
	private CdrDrctSalesReceiptPrintProperties receiptPrintProperties = new CdrDrctSalesReceiptPrintProperties();

	public String getDrctSalesIdentif() {
		return drctSalesIdentif;
	}
	public void setDrctSalesIdentif(String drctSalesIdentif) {
		this.drctSalesIdentif = drctSalesIdentif;
	}

	public boolean isUnDiscountReceipt() {
		return unDiscountReceipt;
	}

	public void setUnDiscountReceipt(boolean unDiscountReceipt) {
		this.unDiscountReceipt = unDiscountReceipt;
	}

	public CdrDrctSalesReceiptPrintProperties getReceiptPrintProperties() {
		return receiptPrintProperties;
	}

	public void setReceiptPrintProperties(CdrDrctSalesReceiptPrintProperties receiptPrintProperties) {
		this.receiptPrintProperties = receiptPrintProperties;
	}
}
