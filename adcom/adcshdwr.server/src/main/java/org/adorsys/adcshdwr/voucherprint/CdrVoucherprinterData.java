package org.adorsys.adcshdwr.voucherprint;

import org.adorsys.adcore.print.ReceiptPrintProperties;

/**
 * Data object used to print a customer voucher.
 * 
 * @author fpo
 *
 */
public class CdrVoucherprinterData {
	private String voucherIdentif;
	private ReceiptPrintProperties receiptPrintProperties = new ReceiptPrintProperties();

	public ReceiptPrintProperties getReceiptPrintProperties() {
		return receiptPrintProperties;
	}
	public void setReceiptPrintProperties(ReceiptPrintProperties receiptPrintProperties) {
		this.receiptPrintProperties = receiptPrintProperties;
	}
	public String getVoucherIdentif() {
		return voucherIdentif;
	}
	public void setVoucherIdentif(String voucherIdentif) {
		this.voucherIdentif = voucherIdentif;
	}
}
