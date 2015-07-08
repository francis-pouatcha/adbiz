package org.adorsys.adcshdwr.print.api;

import java.awt.print.PrinterJob;
import java.io.File;

import javax.print.PrintService;

import org.adorsys.adcore.exceptions.AdException;
import org.apache.pdfbox.pdmodel.PDDocument;

/**
 * 
 * @author Hsimo
 *
 */
public class PrinterHelper {
	
	public static final String PRINTER_NAME= "printer";
	
	
	public static PrintService getPrinterService(){
		PrintService[] printServices = PrinterJob.lookupPrintServices();
		for (int i = 0; i < printServices.length; i++) {
			 if(PRINTER_NAME.equalsIgnoreCase(printServices[i].getName())){
				 return printServices[i];
			 }
		}
		return null;
	}
	
	
	
	public static boolean doPrint(PDDocument doc, String file) throws AdException{
		boolean print = false;
		try {
			doc = PDDocument.load(new File(file));
			PrinterJob printerJob = PrinterJob.getPrinterJob();
			printerJob.setPrintService(getPrinterService());
			printerJob.setJobName(file);
			printerJob.setPageable(doc);
			doc.print(printerJob);
			print = true;
			return print;
		} catch (Exception e) {
			throw new AdException(e.getMessage());
		}
	}
	
	
	

}
