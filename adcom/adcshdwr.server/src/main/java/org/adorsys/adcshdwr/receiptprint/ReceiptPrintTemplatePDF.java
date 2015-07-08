package org.adorsys.adcshdwr.receiptprint;

import java.awt.print.PrinterException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.adorsys.adcshdwr.jpa.CdrDsArtItem;
import org.adorsys.adcshdwr.print.api.PrintMode;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;

public class ReceiptPrintTemplatePDF extends ReceiptPrintTemplate {
	
    static final String separatorText = "------------------------";
	private ByteArrayOutputStream bos;
	
	private final ReceiptPrinterData receiptPrinterData; 
	
	private static ReceiptPrintProperties rpp = new ReceiptPrintProperties();
	private static ReceiptDataItemMetaData receiptDataItemMetaData;
	private static ReceiptDataTableMetaData receiptDataTableMetaData;
	private static ReceiptDataTwoColsMetaData receiptDataTwoColsMetaData;
	private ReceiptDataTable rdt;
	
	static {
		initProperties();
	}
	
	
	public ReceiptPrintTemplatePDF(ReceiptPrinterData receiptPrinterData) {
		super();
		this.receiptPrinterData = receiptPrinterData;
		if(rpp.isDebug()){
			initProperties();
		}
	}


	private static final void initProperties(){
		rpp = ReceiptPrintProperties.loadPrintProperties();
		receiptDataItemMetaData = new ReceiptDataItemMetaData(rpp.getRowHeight(), rpp.getWidth(),rpp.getMargin(),
				rpp.getCellMargin(), rpp.getCodeWidth(), rpp.getQuantityWidth(), rpp.getTotalWidth());
		receiptDataTableMetaData = new ReceiptDataTableMetaData(rpp.getMargin(), rpp.getWidth(), rpp.getFontSize(), rpp.getRowHeight());
		receiptDataTwoColsMetaData = new ReceiptDataTwoColsMetaData(rpp.getRowHeight(), rpp.getWidth(),rpp.getMargin(), 
				rpp.getCellMargin(), rpp.getTotalWidth());
	}
	

	@Override
	public void startPage() {
		try {
			printReceiptHeader();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
	
	// Print the header of receipt
	private void printReceiptHeader() throws IOException {
		rdt = new ReceiptDataTable(receiptDataTableMetaData);
		
		new ReceiptDataOneColBuilder(receiptDataTableMetaData)
			.withBoldFont()
			.withCenterAlign()
			.withDesignation("TICKET DE CAISSE"+ " "+ receiptPrinterData.getPayment().getPymntDocNbr()).build(rdt);

		// underline
		if(rpp.isDisplayTitleUnderLine())
		new ReceiptDataOneColBuilder(receiptDataTableMetaData)
			.withCenterAlign().withDesignation(separatorText).build(rdt);
		
		// companyName  
		if(rpp.isDisplayCompanyName())
		new ReceiptDataOneColBuilder(receiptDataTableMetaData)
			.withDesignation(receiptPrinterData.getCompany().getFullName()).build(rdt);
			
		// ownerName
		if(rpp.isDisplayOwnerName())
		new ReceiptDataOneColBuilder(receiptDataTableMetaData)
			.withBoldFont()
			.withDesignation(receiptPrinterData.getCompany().getContact().getContactPeople()).build(rdt);

		// address 
		if(rpp.isDisplayAddress())
		new ReceiptDataOneColBuilder(receiptDataTableMetaData)
			.withDesignation(receiptPrinterData.getCompany().getContact().getAdress()).build(rdt);
		
		if(rpp.isDisplayTelFax()){
			String phoneAndFax = "TEL: "+ receiptPrinterData.getCompany().getContact().getPhone() +" "+ 
					             "FAX: " + receiptPrinterData.getCompany().getContact().getFax();
			new ReceiptDataOneColBuilder(receiptDataTableMetaData)
				.withDesignation(phoneAndFax).build(rdt);
		}
		
		// email
		if(rpp.isDisplayEmail())
		new ReceiptDataOneColBuilder(receiptDataTableMetaData)
			.withDesignation("Email: "+ " " + receiptPrinterData.getCompany().getContact().getEmail()).build(rdt);
		
		// compnyRegisterNumber
		if(rpp.isDisplayRegisterNumber())
		new ReceiptDataOneColBuilder(receiptDataTableMetaData)
			.withDesignation("RC:"+ " " + receiptPrinterData.getCompany().getIdentif()).build(rdt);

		// underline
		if(rpp.isDisplayRegisterUnderline())
		new ReceiptDataOneColBuilder(receiptDataTableMetaData)
			.withCenterAlign().withDesignation(separatorText).build(rdt);
		
		// Ticket number and payment date
		if(rpp.isDisplayTicketNumberAndDate()){
			String receiptNumberAndDate=
					"TICKET:"+" "+ receiptPrinterData.getPayment().getPymntDocNbr()+ " "+ 
			        "Du"+ " "+receiptPrinterData.getPaymentDate();
			new ReceiptDataOneColBuilder(receiptDataTableMetaData)
				.withDesignation(receiptNumberAndDate).build(rdt);
		}

		// cashierName
		if(rpp.isDisplayCashier())
		new ReceiptDataOneColBuilder(receiptDataTableMetaData)
			.withDesignation("CAISSIER:"+ " " + receiptPrinterData.getCashier().getFullName()).build(rdt);
	}
	

	// Print the header of Direct Sales
	@Override
	public void printCdrDrctSalesHeader(CdrDrctSalesPrinterData cdrData) {
		try {
			printCdrDrctSalesHeaderInternal(cdrData);
		} catch (IOException e) {
			throw new java.lang.IllegalStateException(e);
		}
		
	}
	
	public void printCdrDrctSalesHeaderInternal(CdrDrctSalesPrinterData drcData) throws IOException{
		
		new ReceiptDataOneColBuilder(receiptDataTableMetaData)
		.withDesignation("COMMANDE:"+ " "+drcData.getCdrDrctSales().getDsNbr()).build(rdt);
		
		if(rpp.isDisplaySalesAgent()){
			new ReceiptDataOneColBuilder(receiptDataTableMetaData)
			.withDesignation("VENDEUR:"+ " " + drcData.getLogin().getFullName()).build(rdt);
		}
		if(rpp.isDisplayClient()){
			new ReceiptDataOneColBuilder(receiptDataTableMetaData)
			.withDesignation("CLIENT:"+ " " + receiptPrinterData.getCustomerName()).build(rdt);
		}
		
		
		cdrTotalAmount = cdrTotalAmount.add(drcData.getCdrDrctSales().getNetSalesAmt());
		cdrTotalDiscount = cdrTotalDiscount.add(drcData.getCdrDrctSales().getPymtDscntAmt());
		
		
		// underline
		new ReceiptDataOneColBuilder(receiptDataTableMetaData)
			.withCenterAlign().withDesignation(separatorText).build(rdt);
		
		buildDataItemTableMetaData();
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
	
	
	@Override
	public void addItems(List<CdrDsArtItem> resultList) {
		   for(CdrDsArtItem item: resultList){
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
	
	
	
	@Override
	public void endPage() {
		try {
			printReceiptFooter();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		
	}
	BigDecimal cdrTotalAmount = BigDecimal.ZERO;
	BigDecimal cdrTotalDiscount = BigDecimal.ZERO;
	public void printReceiptFooter() throws IOException{
			// underline
			if(rpp.isDisplayInvoiceUnderline())
			new ReceiptDataOneColBuilder(receiptDataTableMetaData)
				.withCenterAlign().withDesignation(separatorText).build(rdt);
			
			if(rpp.isDisplayPaymentMode())
				new ReceiptDataOneColBuilder(receiptDataTableMetaData)
					.withDesignation("MODE PAIEMENT:"+ " "+receiptPrinterData.getPayment().getPymntMode()).build(rdt);
			
			new ReceiptDataTwoColsBuilder(receiptDataTableMetaData, receiptDataTwoColsMetaData)
			.withDesignation("MONTANT TOTAL").withValue(cdrTotalAmount.toString()).build(rdt);
			
			new ReceiptDataTwoColsBuilder(receiptDataTableMetaData, receiptDataTwoColsMetaData)
			.withDesignation("REMISE").withValue(cdrTotalDiscount.toString()).build(rdt);
			
			new ReceiptDataTwoColsBuilder(receiptDataTableMetaData, receiptDataTwoColsMetaData)
			.withDesignation("NET A PAYER").withValue(receiptPrinterData.getPayment().getAmt().toString()).build(rdt);
			
			new ReceiptDataTwoColsBuilder(receiptDataTableMetaData, receiptDataTwoColsMetaData)
			.withDesignation("SOMME RECUE").withValue(receiptPrinterData.getPayment().getRcvdAmt().toString()).build(rdt);
			
			new ReceiptDataTwoColsBuilder(receiptDataTableMetaData, receiptDataTwoColsMetaData)
			.withDesignation("DIFFERENCE").withValue(receiptPrinterData.getPayment().getDiffAmt().toString()).build(rdt);
			
			// Ticket Message
			new ReceiptDataOneColBuilder(receiptDataTableMetaData)
			.withDesignation(receiptPrinterData.getCompany().getContact().getContactIndex()).build(rdt);
	}
	
	@Override
	public void closePayment() {
		
		try {
			closePaymentInternal();
		} catch (IOException e) {
			throw new java.lang.IllegalStateException(e);
		} catch (PrinterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void closePaymentInternal() throws IOException, PrinterException{
		PDDocument pdDocument = new PDDocument();
		float docHeight = rdt.computeHeight();
		float docWidth = receiptDataTableMetaData.getWidth();
		PDPage sourcePage = new PDPage(new PDRectangle(receiptDataTableMetaData.getWidth(), rdt.computeHeight()));
		pdDocument.addPage(sourcePage);
		PDPageContentStream pageContentStream = new PDPageContentStream(pdDocument, sourcePage);
		pageContentStream.setFont(receiptDataTableMetaData.getFont(), receiptDataTableMetaData.getFontSize());
		rdt.write(pageContentStream, docWidth, docHeight);
		pageContentStream.close();
		bos = new ByteArrayOutputStream();
		try {
			pdDocument.save(bos);
		} catch (COSVisitorException e) {
			throw new IOException(e);
		}
		pdDocument.close();		
	}


	@Override
	public Object getPage() {
		if(bos==null) return null;
		return bos;
	}

	@Override
	public ReceiptPrinterData getReceiptPrinterData() {
		return receiptPrinterData;
	}

	@Override
	public String getReceiptPrinterName() {
		return rpp.getReceiptPrinterName();
	}

	@Override
	public PrintMode getReceiptPrintMode() {
		return rpp.getReceiptPrintMode();
	}

}
