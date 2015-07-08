package org.adorsys.adcshdwr.voucherprint;

import org.adorsys.adcshdwr.print.api.PrintMode;

/**
 * 
 * @author Hsimo
 * 
 * Template class for print voucher pdf
 *
 */
public abstract class VoucherPrintTemplate {
	
    public abstract void startPage();
	
	public abstract void endPage();

	public abstract void closeVoucher();
	
	public abstract Object getPage();
	
	public abstract void printCdrCstmrVchrHeader(CdrCstmrVchrPrinterData cstmrVchrPrinterData);
	
	public abstract VoucherprinterData getVoucherprinterData();
	
	public abstract PrintMode getVoucherPrintMode();
	
	
	/**
	 * The voucher pdf template model
	 * @param cstmrVchrPrinterData
	 */
	public final void printPdfVoucher(CdrCstmrVchrPrinterData cstmrVchrPrinterData){
		startPage();
		printCdrCstmrVchrHeader(cstmrVchrPrinterData);
		endPage();
		closeVoucher();
	}
	
	
	

}
