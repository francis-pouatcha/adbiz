package org.adorsys.adcshdwr.receiptprint;

import java.awt.print.PrinterException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.adorsys.adbase.jpa.OrgContact;
import org.adorsys.adbase.jpa.OrgUnit;
import org.adorsys.adcore.auth.AdcomUser;
import org.adorsys.adcore.print.ReceiptDataItemBuilder;
import org.adorsys.adcore.print.ReceiptDataItemMetaData;
import org.adorsys.adcore.print.ReceiptDataOneColBuilder;
import org.adorsys.adcore.print.ReceiptDataTable;
import org.adorsys.adcore.print.ReceiptDataTableMetaData;
import org.adorsys.adcore.print.ReceiptDataTwoColsBuilder;
import org.adorsys.adcore.print.ReceiptDataTwoColsMetaData;
import org.adorsys.adcshdwr.jpa.CdrDrctSales;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesHstry;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesItem;
import org.adorsys.adcshdwr.jpa.CdrPymnt;
import org.adorsys.adcshdwr.jpa.CdrPymntItem;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;

public class CdrDirectSalesReceiptPrintTemplatePDF {
	
	private CdrDrctSalesReceiptPrintProperties rpp;
	private ReceiptDataItemMetaData receiptDataItemMetaData;
	private ReceiptDataTableMetaData receiptDataTableMetaData;
	private ReceiptDataTwoColsMetaData receiptDataTwoColsMetaData;
	private ReceiptDataTable rdt;
	
	public CdrDirectSalesReceiptPrintTemplatePDF(CdrDirectSalesReceiptPrinterData printerData) {
		rpp = printerData.getReceiptPrintProperties();
		receiptDataItemMetaData = new ReceiptDataItemMetaData(rpp.getRowHeight(), rpp.getWidth(),rpp.getMargin(),
				rpp.getCellMargin(), rpp.getCodeWidth(), rpp.getQuantityWidth(), rpp.getTotalWidth());
		receiptDataTableMetaData = new ReceiptDataTableMetaData(rpp.getMargin(), rpp.getWidth(), rpp.getFontSize(), rpp.getRowHeight());
		receiptDataTwoColsMetaData = new ReceiptDataTwoColsMetaData(rpp.getRowHeight(), rpp.getWidth(),rpp.getMargin(), 
				rpp.getCellMargin(), rpp.getTotalWidth());
	}
	

	public void startPage(CdrDrctSales drctSales, CdrPymnt pymnt, OrgUnit tenant, OrgContact orgContact, AdcomUser user) {
		try {
			printReceiptHeader(drctSales, pymnt, tenant, orgContact, user);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
	
	// Print the header of receipt
	private void printReceiptHeader(CdrDrctSales drctSales, CdrPymnt pymnt, OrgUnit tenant, OrgContact orgContact,AdcomUser user) throws IOException {
		rdt = new ReceiptDataTable(receiptDataTableMetaData);
		
		new ReceiptDataOneColBuilder(receiptDataTableMetaData)
			.withBoldFont()
			.withCenterAlign()
			.withDesignation("TICKET DE CAISSE"+ " "+ pymnt.getIdentif()).build(rdt);

		// underline
		if(rpp.isDisplayTitleUnderLine())
		new ReceiptDataOneColBuilder(receiptDataTableMetaData)
			.withCenterAlign().withDesignation(rpp.getSeparatorText()).build(rdt);
		
		// companyName  
		if(rpp.isDisplayCompanyName())
		new ReceiptDataOneColBuilder(receiptDataTableMetaData)
			.withDesignation(tenant.getFullName()).build(rdt);
			
		// ownerName
		if(rpp.isDisplayOwnerName())
		new ReceiptDataOneColBuilder(receiptDataTableMetaData)
			.withBoldFont()
			.withDesignation(orgContact.getContactPeople()).build(rdt);

		// address 
		if(rpp.isDisplayAddress())
		new ReceiptDataOneColBuilder(receiptDataTableMetaData)
			.withDesignation(orgContact.getAdress()).build(rdt);
		
		if(rpp.isDisplayTelFax()){
			String phoneAndFax = "TEL: "+ orgContact.getPhone() +" "+ 
					             "FAX: " + orgContact.getFax();
			new ReceiptDataOneColBuilder(receiptDataTableMetaData)
				.withDesignation(phoneAndFax).build(rdt);
		}
		
		// email
		if(rpp.isDisplayEmail())
		new ReceiptDataOneColBuilder(receiptDataTableMetaData)
			.withDesignation("E-Mail: "+ " " + orgContact.getEmail()).build(rdt);
		
		// compnyRegisterNumber
		if(rpp.isDisplayRegisterNumber())
		new ReceiptDataOneColBuilder(receiptDataTableMetaData)
			.withDesignation("RC:"+ " " + tenant.getRegisterNumber()).build(rdt);

		// underline
		if(rpp.isDisplayRegisterUnderline())
		new ReceiptDataOneColBuilder(receiptDataTableMetaData)
			.withCenterAlign().withDesignation(rpp.getSeparatorText()).build(rdt);
		
		// Ticket number and payment date
		if(rpp.isDisplayTicketNumberAndDate()){
			String receiptNumberAndDate=
					"TICKET:"+" "+ pymnt.getIdentif()+ " "+ 
			        "Du"+ " "+pymnt.getValueDt();
			new ReceiptDataOneColBuilder(receiptDataTableMetaData)
				.withDesignation(receiptNumberAndDate).build(rdt);
		}

		// cashierName
		if(rpp.isDisplayCashier())
		new ReceiptDataOneColBuilder(receiptDataTableMetaData)
			.withDesignation("CAISSIER:"+ " " + pymnt.getCashier()).build(rdt);

		// cashierName
		if(rpp.isDisplayUser())
		new ReceiptDataOneColBuilder(receiptDataTableMetaData)
			.withDesignation("IMPRIME PAR:"+ " " + user.getFullName()).build(rdt);
	}
	

	// Print the header of Direct Sales
	public void printCdrDrctSalesHeader(CdrDrctSales drctSales, CdrDrctSalesHstry dsHstry) {
		try {
			new ReceiptDataOneColBuilder(receiptDataTableMetaData)
			.withDesignation("COMMANDE:"+ " "+drctSales.getIdentif()).build(rdt);
			
			if(rpp.isDisplaySalesAgent()){
				new ReceiptDataOneColBuilder(receiptDataTableMetaData)
				.withDesignation("VENDEUR:"+ " " + dsHstry.getOriginUserName()).build(rdt);
			}
			if(rpp.isDisplayClient()){
				new ReceiptDataOneColBuilder(receiptDataTableMetaData)
				.withDesignation("CLIENT:"+ " " + drctSales.getBsnsPtnrName()).build(rdt);
			}			
			
			// underline
			new ReceiptDataOneColBuilder(receiptDataTableMetaData)
				.withCenterAlign().withDesignation(rpp.getSeparatorText()).build(rdt);
			
			buildDataItemTableMetaData();
		} catch (IOException e) {
			throw new java.lang.IllegalStateException(e);
		}
	}
	
	
	
	// CdrDataItemTable
	public void buildDataItemTableMetaData() throws IOException {
		ReceiptDataItemBuilder invoiceTableHeader = new ReceiptDataItemBuilder(receiptDataTableMetaData, receiptDataItemMetaData)
		.withDesignation("Designation")
		.withQuantity("Qte")
		.withTotal("Total")
		.withBoldFont();
		if(receiptDataItemMetaData.isMergeCodeAndDesignation()){
			invoiceTableHeader = invoiceTableHeader.withCode("");
		} else {
			invoiceTableHeader = invoiceTableHeader.withCode("Code");
		}
		invoiceTableHeader.build(rdt);
	}
	
	
	public void addItems(List<CdrDrctSalesItem> resultList) {
		   for(CdrDrctSalesItem item: resultList){
			   try {
					new ReceiptDataItemBuilder(receiptDataTableMetaData, receiptDataItemMetaData)
						.withCode(item.getArtPic())
						.withDesignation(item.getArtName())
						.withQuantity(item.getQtyBilled().toString())
						.withTotal(item.getSlsNetPrcTaxIncl().toString())
						.build(rdt);
				} catch (IOException e) {
					throw new java.lang.IllegalStateException(e);
				}
		   }
	}
	
	public void printReceiptTotal(CdrDrctSales drctSales) throws IOException{
		if(rpp.isDisplayUnderline())
			new ReceiptDataOneColBuilder(receiptDataTableMetaData)
			.withCenterAlign().withDesignation(rpp.getSeparatorText()).build(rdt);
		
		if(rpp.isDisplayGrossPricePreTax())
			new ReceiptDataTwoColsBuilder(receiptDataTableMetaData, receiptDataTwoColsMetaData)
			.withDesignation("MONTANT TOTAL").withValue(drctSales.getSlsGrossPrcPreTax().toString()).build(rdt);

		if(rpp.isDisplaySlsRebateAmt())
			new ReceiptDataTwoColsBuilder(receiptDataTableMetaData, receiptDataTwoColsMetaData)
			.withDesignation("REMISE").withValue(drctSales.getSlsRebateAmt().toString()).build(rdt);

		if(rpp.isDisplaySlsNetPrcPreTax())
			new ReceiptDataTwoColsBuilder(receiptDataTableMetaData, receiptDataTwoColsMetaData)
			.withDesignation("MONTANT TOTAL").withValue(drctSales.getSlsNetPrcPreTax().toString()).build(rdt);

		if(rpp.isDisplaySlsVatAmt())
			new ReceiptDataTwoColsBuilder(receiptDataTableMetaData, receiptDataTwoColsMetaData)
			.withDesignation("VAT").withValue(drctSales.getSlsVatAmt().toString()).build(rdt);

		if(rpp.isDisplaySlsNetPrcTaxIncl())
			new ReceiptDataTwoColsBuilder(receiptDataTableMetaData, receiptDataTwoColsMetaData)
			.withDesignation("MONTANT TOTAL").withValue(drctSales.getSlsNetPrcTaxIncl().toString()).build(rdt);

		if(rpp.isDisplaySlsPymtDscntPct())
			new ReceiptDataTwoColsBuilder(receiptDataTableMetaData, receiptDataTwoColsMetaData)
			.withDesignation("MONTANT TOTAL").withValue(drctSales.getSlsPymtDscntPct().toString()).build(rdt);

		if(rpp.isDisplaySlsPymtDscntAmt())
			new ReceiptDataTwoColsBuilder(receiptDataTableMetaData, receiptDataTwoColsMetaData)
			.withDesignation("MONTANT TOTAL").withValue(drctSales.getSlsPymtDscntAmt().toString()).build(rdt);

		if(rpp.isDisplaySlsRdngDscntAmt())
			new ReceiptDataTwoColsBuilder(receiptDataTableMetaData, receiptDataTwoColsMetaData)
			.withDesignation("MONTANT TOTAL").withValue(drctSales.getSlsRdngDscntAmt().toString()).build(rdt);
		
		if(rpp.isDisplaySlsNetPymtAmt())
			new ReceiptDataTwoColsBuilder(receiptDataTableMetaData, receiptDataTwoColsMetaData)
			.withDesignation("MONTANT TOTAL").withValue(drctSales.getSlsNetPymtAmt().toString()).build(rdt);
	}

	public void printTiketMessage(OrgUnit tenant) throws IOException{
		// Ticket Message
		if(rpp.isDisplayTicketMessage())
			new ReceiptDataOneColBuilder(receiptDataTableMetaData)
			.withDesignation(rpp.getTicketMessage()).build(rdt);
	}
	
	public void printPymntLine(CdrPymntItem pymntItem) throws IOException{
		if(rpp.isDisplayPaymentMode()){
			new ReceiptDataOneColBuilder(receiptDataTableMetaData)
			.withDesignation("MODE PAIEMENT:"+ " "+pymntItem.getPymntMode().name()).build(rdt);
		}
		
		new ReceiptDataTwoColsBuilder(receiptDataTableMetaData, receiptDataTwoColsMetaData)
		.withDesignation("SOMME RECUE").withValue(pymntItem.getRcvdAmt().toString()).build(rdt);
		
		new ReceiptDataTwoColsBuilder(receiptDataTableMetaData, receiptDataTwoColsMetaData)
		.withDesignation("DIFFERENCE").withValue(pymntItem.getDiffAmt().toString()).build(rdt);
		
	}
		
	public void writeTo(OutputStream os) throws IOException, PrinterException{
		PDDocument pdDocument = new PDDocument();
		float docHeight = rdt.computeHeight();
		float docWidth = receiptDataTableMetaData.getWidth();
		PDPage sourcePage = new PDPage(new PDRectangle(receiptDataTableMetaData.getWidth(), rdt.computeHeight()));
		pdDocument.addPage(sourcePage);
		PDPageContentStream pageContentStream = new PDPageContentStream(pdDocument, sourcePage);
		pageContentStream.setFont(receiptDataTableMetaData.getFont(), receiptDataTableMetaData.getFontSize());
		rdt.write(pageContentStream, docWidth, docHeight);
		pageContentStream.close();
		try {
			pdDocument.save(os);
		} catch (COSVisitorException e) {
			throw new IOException(e);
		}
		pdDocument.close();		
	}
}
