package org.adorsys.adcshdwr.receiptprint;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.adorsys.adcshdwr.print.api.PrintMode;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;


public class ReceiptPrintProperties {
	
	private PrintMode receiptPrintMode = PrintMode.open;
	private String receiptPrinterName = "receipt";
	private boolean debug = true;
	
	
	private int format = 80;
	private float fontSize = 12f;
	private float width = 302.36f;
	private float rowHeight = 15f;
	private float cellMargin = 2f;
	private float codeWidth= 0f;// IF zero, merge code and designation columns. .6f, .13f, .27f
	private float quantityWidth=.13f;
	private float totalWidth=.27f;
	private float margin = 10f;	
	private boolean displayTitleUnderLine = true;
	private boolean displayCompanyName = true;
	private boolean displayOwnerName = true;
	private boolean displayAddress = true;
	private boolean displayTelFax = true;
	private boolean displayEmail = true;
	private boolean displayRegisterNumber = true;
	private boolean displayRegisterUnderline = true;
	private boolean displayTicketNumberAndDate = true;
	private boolean displayCashier = true;
	private boolean displaySalesAgent = true;
	private boolean displayClient = true;
	private boolean displayClientDiver = true;
	private boolean displayInvoiceOverLine = true;
	private boolean displayInvoiceUnderline = true;
	private boolean displayPaymentMode = true;
	private boolean alwaysShowDiscount = true;
	
	public static ReceiptPrintProperties loadPrintProperties(){
		ReceiptPrintProperties r = new ReceiptPrintProperties();
		File file = new File("appconfig.properties");
		if(file.exists()){
			Properties properties = new Properties();
			try {
				properties.load(new FileInputStream(file));
				String paperFormat = properties.getProperty("receipt-printer-format");
				if(StringUtils.isNotBlank(paperFormat)){
					r.setFormat(NumberUtils.toInt(paperFormat));
				}

				String fontSizeStr = properties.getProperty("receipt-font-size");
				if(StringUtils.isNotBlank(fontSizeStr)){
					r.setFontSize(NumberUtils.toInt(fontSizeStr));
				}
				String docWidthStr = properties.getProperty("receipt-doc-width");
				if(StringUtils.isNotBlank(docWidthStr)){
					r.setWidth(NumberUtils.toFloat(docWidthStr));
				}
				String rowHeightStr = properties.getProperty("receipt-row-height");
				if(StringUtils.isNotBlank(rowHeightStr)){
					r.setRowHeight(NumberUtils.toFloat(rowHeightStr));
				}
				String cellMarginStr = properties.getProperty("receipt-cell-margin");
				if(StringUtils.isNotBlank(cellMarginStr)){
					r.setCellMargin(NumberUtils.toFloat(cellMarginStr));
				}
				String codeWidthStr = properties.getProperty("receipt-item-code-width");
				if(StringUtils.isNotBlank(codeWidthStr)){
					r.setCodeWidth(NumberUtils.toFloat(codeWidthStr));
				}
				String quantityWidthStr = properties.getProperty("receipt-item-quantity-width");
				if(StringUtils.isNotBlank(quantityWidthStr)){
					r.setQuantityWidth(NumberUtils.toFloat(quantityWidthStr));
				}
				String totalWidthStr = properties.getProperty("receipt-item-total-width");
				if(StringUtils.isNotBlank(totalWidthStr)){
					r.setTotalWidth(NumberUtils.toFloat(totalWidthStr));
				}
				String marginStr = properties.getProperty("receipt-margin");
				if(StringUtils.isNotBlank(marginStr)){
					r.setMargin(NumberUtils.toFloat(marginStr));
				}

				String receiptPrinterNameProp = properties.getProperty("receipt-printer-name");
				if(StringUtils.isNotBlank(receiptPrinterNameProp)){
					r.setReceiptPrinterName(receiptPrinterNameProp);
				}	
				String receiptPrintModeProp = properties.getProperty("receipt-print-mode");
				if(StringUtils.isNotBlank(receiptPrintModeProp)){
					try {
						r.setReceiptPrintMode(PrintMode.valueOf(receiptPrintModeProp));
					} catch(Exception ex){
						// noop. Keep default mode
					}
				}

				String debugProp = properties.getProperty("receipt-print-debug");
				if(StringUtils.isNotBlank(debugProp)){
					r.setDebug(BooleanUtils.toBoolean(debugProp));
				}	

				String displayTitleUnderLineProp = properties.getProperty("receipt-display-title-under-line");
				if(StringUtils.isNotBlank(displayTitleUnderLineProp)){
					r.setDisplayTitleUnderLine(BooleanUtils.toBoolean(displayTitleUnderLineProp));
				}	
			
				String displayCompanyNameProp = properties.getProperty("receipt-display-company-name");
				if(StringUtils.isNotBlank(displayCompanyNameProp)){
					r.setDisplayCompanyName(BooleanUtils.toBoolean(displayCompanyNameProp));
				}	
				
				String displayOwnerNameProp = properties.getProperty("receipt-display-owner-name");
				if(StringUtils.isNotBlank(displayOwnerNameProp)){
					r.setDisplayOwnerName(BooleanUtils.toBoolean(displayOwnerNameProp));
				}	
				
				String displayAddressProp = properties.getProperty("receipt-display-address");
				if(StringUtils.isNotBlank(displayAddressProp)){
					r.setDisplayAddress(BooleanUtils.toBoolean(displayAddressProp));
				}	
				
				String displayTelFaxProp = properties.getProperty("receipt-display-tel-fax");
				if(StringUtils.isNotBlank(displayTelFaxProp)){
					r.setDisplayTelFax(BooleanUtils.toBoolean(displayTelFaxProp));
				}	
				
				String displayEmailProp = properties.getProperty("receipt-display-email");
				if(StringUtils.isNotBlank(displayEmailProp)){
					r.setDisplayEmail(BooleanUtils.toBoolean(displayEmailProp));
				}	
				
				String displayRegisterNumberProp = properties.getProperty("receipt-display-register-number");
				if(StringUtils.isNotBlank(displayRegisterNumberProp)){
					r.setDisplayRegisterNumber(BooleanUtils.toBoolean(displayRegisterNumberProp));
				}	
				
				String displayRegisterUnderlineProp = properties.getProperty("receipt-display-register-underline");
				if(StringUtils.isNotBlank(displayRegisterUnderlineProp)){
					r.setDisplayRegisterUnderline(BooleanUtils.toBoolean(displayRegisterUnderlineProp));
				}	
				
				String displayTicketNumberAndDateProp = properties.getProperty("receipt-display-ticket-number-and-date");
				if(StringUtils.isNotBlank(displayTicketNumberAndDateProp)){
					r.setDisplayTicketNumberAndDate(BooleanUtils.toBoolean(displayTicketNumberAndDateProp));
				}	
				
				String displayCashierProp = properties.getProperty("receipt-display-cashier");
				if(StringUtils.isNotBlank(displayCashierProp)){
					r.setDisplayCashier(BooleanUtils.toBoolean(displayCashierProp));
				}	
				
				String displaySalesAgentProp = properties.getProperty("receipt-display-sales-agent");
				if(StringUtils.isNotBlank(displaySalesAgentProp)){
					r.setDisplaySalesAgent(BooleanUtils.toBoolean(displaySalesAgentProp));
				}	
				
				String displayClientProp = properties.getProperty("receipt-display-client");
				if(StringUtils.isNotBlank(displayClientProp)){
					r.setDisplayClient(BooleanUtils.toBoolean(displayClientProp));
				}	
				
				String displayClientDiverProp = properties.getProperty("receipt-display-client-diver");
				if(StringUtils.isNotBlank(displayClientDiverProp)){
					r.setDisplayClientDiver(BooleanUtils.toBoolean(displayClientDiverProp));
				}	
				
				String displayInvoiceOverLineProp = properties.getProperty("receipt-display-invoice-over-line");
				if(StringUtils.isNotBlank(displayInvoiceOverLineProp)){
					r.setDisplayInvoiceOverLine(BooleanUtils.toBoolean(displayInvoiceOverLineProp));
				}	
				
				String displayInvoiceUnderlineProp = properties.getProperty("receipt-display-invoice-underline");
				if(StringUtils.isNotBlank(displayInvoiceUnderlineProp)){
					r.setDisplayInvoiceUnderline(BooleanUtils.toBoolean(displayInvoiceUnderlineProp));
				}	
				
				String displayPaymentModeProp = properties.getProperty("receipt-display-payment-mode");
				if(StringUtils.isNotBlank(displayPaymentModeProp)){
					r.setDisplayPaymentMode(BooleanUtils.toBoolean(displayPaymentModeProp));
				}	
				
				String alwaysShowDiscountProp = properties.getProperty("receipt-always-show-discount");
				if(StringUtils.isNotBlank(alwaysShowDiscountProp)){
					r.setAlwaysShowDiscount(BooleanUtils.toBoolean(alwaysShowDiscountProp));
				}	
			} catch (FileNotFoundException e) {
				throw new IllegalStateException(e);
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
		}
		return r;
	}

	public PrintMode getReceiptPrintMode() {
		return receiptPrintMode;
	}

	public void setReceiptPrintMode(PrintMode receiptPrintMode) {
		this.receiptPrintMode = receiptPrintMode;
	}

	public String getReceiptPrinterName() {
		return receiptPrinterName;
	}

	public void setReceiptPrinterName(String receiptPrinterName) {
		this.receiptPrinterName = receiptPrinterName;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public int getFormat() {
		return format;
	}

	public void setFormat(int format) {
		this.format = format;
	}

	public float getFontSize() {
		return fontSize;
	}

	public void setFontSize(float fontSize) {
		this.fontSize = fontSize;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getRowHeight() {
		return rowHeight;
	}

	public void setRowHeight(float rowHeight) {
		this.rowHeight = rowHeight;
	}

	public float getCellMargin() {
		return cellMargin;
	}

	public void setCellMargin(float cellMargin) {
		this.cellMargin = cellMargin;
	}

	public float getCodeWidth() {
		return codeWidth;
	}

	public void setCodeWidth(float codeWidth) {
		this.codeWidth = codeWidth;
	}

	public float getQuantityWidth() {
		return quantityWidth;
	}

	public void setQuantityWidth(float quantityWidth) {
		this.quantityWidth = quantityWidth;
	}

	public float getTotalWidth() {
		return totalWidth;
	}

	public void setTotalWidth(float totalWidth) {
		this.totalWidth = totalWidth;
	}

	public float getMargin() {
		return margin;
	}

	public void setMargin(float margin) {
		this.margin = margin;
	}

	public boolean isDisplayTitleUnderLine() {
		return displayTitleUnderLine;
	}

	public void setDisplayTitleUnderLine(boolean displayTitleUnderLine) {
		this.displayTitleUnderLine = displayTitleUnderLine;
	}

	
	
	public boolean isDisplayCompanyName() {
		return displayCompanyName;
	}

	public void setDisplayCompanyName(boolean displayCompanyName) {
		this.displayCompanyName = displayCompanyName;
	}

	public boolean isDisplayOwnerName() {
		return displayOwnerName;
	}

	public void setDisplayOwnerName(boolean displayOwnerName) {
		this.displayOwnerName = displayOwnerName;
	}

	public boolean isDisplayAddress() {
		return displayAddress;
	}

	public void setDisplayAddress(boolean displayAddress) {
		this.displayAddress = displayAddress;
	}

	public boolean isDisplayTelFax() {
		return displayTelFax;
	}

	public void setDisplayTelFax(boolean displayTelFax) {
		this.displayTelFax = displayTelFax;
	}

	public boolean isDisplayEmail() {
		return displayEmail;
	}

	public void setDisplayEmail(boolean displayEmail) {
		this.displayEmail = displayEmail;
	}

	public boolean isDisplayRegisterNumber() {
		return displayRegisterNumber;
	}

	public void setDisplayRegisterNumber(boolean displayRegisterNumber) {
		this.displayRegisterNumber = displayRegisterNumber;
	}

	public boolean isDisplayRegisterUnderline() {
		return displayRegisterUnderline;
	}

	public void setDisplayRegisterUnderline(boolean displayRegisterUnderline) {
		this.displayRegisterUnderline = displayRegisterUnderline;
	}

	public boolean isDisplayTicketNumberAndDate() {
		return displayTicketNumberAndDate;
	}

	public void setDisplayTicketNumberAndDate(boolean displayTicketNumberAndDate) {
		this.displayTicketNumberAndDate = displayTicketNumberAndDate;
	}

	public boolean isDisplayCashier() {
		return displayCashier;
	}

	public void setDisplayCashier(boolean displayCashier) {
		this.displayCashier = displayCashier;
	}

	public boolean isDisplaySalesAgent() {
		return displaySalesAgent;
	}

	public void setDisplaySalesAgent(boolean displaySalesAgent) {
		this.displaySalesAgent = displaySalesAgent;
	}

	public boolean isDisplayClient() {
		return displayClient;
	}

	public void setDisplayClient(boolean displayClient) {
		this.displayClient = displayClient;
	}

	public boolean isDisplayClientDiver() {
		return displayClientDiver;
	}

	public void setDisplayClientDiver(boolean displayClientDiver) {
		this.displayClientDiver = displayClientDiver;
	}

	public boolean isDisplayInvoiceOverLine() {
		return displayInvoiceOverLine;
	}

	public void setDisplayInvoiceOverLine(boolean displayInvoiceOverLine) {
		this.displayInvoiceOverLine = displayInvoiceOverLine;
	}

	public boolean isDisplayInvoiceUnderline() {
		return displayInvoiceUnderline;
	}

	public void setDisplayInvoiceUnderline(boolean displayInvoiceUnderline) {
		this.displayInvoiceUnderline = displayInvoiceUnderline;
	}

	public boolean isDisplayPaymentMode() {
		return displayPaymentMode;
	}

	public void setDisplayPaymentMode(boolean displayPaymentMode) {
		this.displayPaymentMode = displayPaymentMode;
	}

	public boolean isAlwaysShowDiscount() {
		return alwaysShowDiscount;
	}

	public void setAlwaysShowDiscount(boolean alwaysShowDiscount) {
		this.alwaysShowDiscount = alwaysShowDiscount;
	}

	
	

}
