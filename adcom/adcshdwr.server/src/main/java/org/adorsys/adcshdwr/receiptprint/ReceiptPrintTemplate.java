package org.adorsys.adcshdwr.receiptprint;

import java.util.List;

import org.adorsys.adcshdwr.jpa.CdrDsArtItem;
import org.adorsys.adcshdwr.print.api.PrintMode;

/**
 * 
 * @author Hsimo
 * Template Class for printing ticket pdf
 *
 */
public abstract class ReceiptPrintTemplate {
	
	public abstract void startPage();
	
	public abstract void endPage();

	public abstract void closePayment();

	public abstract void printCdrDrctSalesHeader(CdrDrctSalesPrinterData cdrPrinterData);

	public abstract void addItems(List<CdrDsArtItem> items);

	public abstract Object getPage();

	public abstract ReceiptPrinterData getReceiptPrinterData();

	public abstract String getReceiptPrinterName();
	
	public abstract PrintMode getReceiptPrintMode();
	
	
	/**
	 *  The receipt template pdf Model
	 * @param cdrPrinterData
	 */
	public final void printPdfReceipt(CdrDrctSalesPrinterData cdrPrinterData){
		List<CdrDsArtItem> items = cdrPrinterData.getCdrDsArtItemSearchResult().getResultList();
		startPage();
		printCdrDrctSalesHeader(cdrPrinterData);
		addItems(items);
		endPage();
		closePayment();
	}

}
