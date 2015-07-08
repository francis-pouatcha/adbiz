package org.adorsys.adcshdwr.receiptprint;

import org.adorsys.adbase.jpa.Login;
import org.adorsys.adbase.jpa.OrgUnit;
import org.adorsys.adcshdwr.jpa.CdrDsPymntItem;

public class ReceiptPrinterData {
	
	private final String customerName;
	private final String paymentDate;
	private final CdrDsPymntItem payment;
	private final Login cashier;
	private final OrgUnit company;
	private final boolean unDiscountReceipt= Boolean.FALSE;
	
	
	
	public ReceiptPrinterData(String customerName, String paymentDate, CdrDsPymntItem payment, Login cashier, OrgUnit company) {
		super();
		this.customerName = customerName;
		this.paymentDate = paymentDate;
		this.payment = payment;
		this.cashier = cashier;
		this.company = company;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	public String getPaymentDate() {
		return paymentDate;
	}
	public boolean isUnDiscountReceipt() {
		return unDiscountReceipt;
	}
	public Login getCashier() {
		return cashier;
	}
	
	public CdrDsPymntItem getPayment() {
		return payment;
	}
	
	public OrgUnit getCompany() {
		return company;
	}

}
