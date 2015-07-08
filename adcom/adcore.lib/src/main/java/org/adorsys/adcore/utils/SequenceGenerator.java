package org.adorsys.adcore.utils;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;


public class SequenceGenerator {
	public static final String DEBTS_INVOICE_SEQUENCE_PREFIXE = "DI";
	public static final String PAYMENT_SEQUENCE_PREFIX = "PY";
	public static final String PRODUCT_FAMILY_SEQUENCE_PREFIXE = "PF";
	public static final String PORCHASE_SEQUENCE_PREFIXE = "CF";
	public static final String DELIVERY_SEQUENCE_PREFIXE = "DE";
	public static final String LOT_SEQUENCE_PREFIXE = "LO";
	public static final String INVENTORY_SEQUENCE_PREFIXE = "INV";
	public static final String SALE_SEQUENCE_PREFIXE = "SO";
	public static final String AGENCY_SEQUENCE_PREFIXE = "AG";
	public static final String CASHDRAWER_SEQUENCE_PREFIXE = "CD";
	public static final String PRESCRIPTIONBOOK_SEQUENCE_PREFIXE = "PB";
	public static final String CUSTOMER_VOUCHER_SEQUENCE_PREFIXE = "CV";
	public static final String CUSTOMER_INVOICE_SEQUENCE_PREFIXE = "CI";
	public static final String CUSTOMER_INVENTORY_SEQUENCE_PREFIXE = "IV";
	public static final String ARTICLE_DETAIL_SEQUENCE_PREFIXE = "AD";
	public static final String BUSINESS_PARTNER_SEQUENCE_PREFIXE = "BP";
	public static final String ID_SEQUENCE_PREFIXE = "ID";
	public static final String BUSINESS_PARTNER_CATEGORY_SEQUENCE_PREFIXE = "BPC";
	public static final String PRCMT_ORDER_SEQUENCE_PREFIXE = "PO";
	public static final String APPOINTMENT_NUMBER_SEQUENCE_PREFIXE = "APT";
	public static final String APPOINTMENTREPPORT_NUMBER_SEQUENCE_PREFIXE="APTREP";
	public static final String DIRECT_SALES_SEQUENCE_PREFIXE = "DS";
	public static final String BUSINESS_PARTNER_LEGAL_SEQUENCE_PREFIXE = "BPL";
	public static final String APPOINTMENTLOGIN_NUMBER_SEQUENCE_PREFIXE = "ALOG";
	public static final String APPOINTMENTCONTACT_NUMBER_SEQUENCE_PREFIXE = "CON";
	public static final String PAYMENT_DS_SEQUENCE_PREFIXE = "PDS";
	public static final String VOUCHER_SEQUENCE_PREFIXE = "VH";
	

	static long time = 0l;// the time corresponding to the 01.01.2014 00:00:00:00 ...
	static {
		Date date = new Date();
		date = DateUtils.setYears(date, 2015);// never change this date.
		date = DateUtils.truncate(date, Calendar.YEAR);
		time = date.getTime();
	}
	public static final String getSequence(String prefixe){
		synchronized (prefixe) {
			try {
				Thread.currentThread().sleep(20);
			} catch (InterruptedException e) {
			}
			return prefixe+Long.toString(System.currentTimeMillis()-time, 36).toUpperCase();
		}
	}
}
