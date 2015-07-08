package org.adorsys.adcshdwr.voucherprint;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.adorsys.adcshdwr.print.api.PrintMode;
import org.adorsys.adcshdwr.receiptprint.ReceiptDataOneColBuilder;
import org.adorsys.adcshdwr.receiptprint.ReceiptDataTable;
import org.adorsys.adcshdwr.receiptprint.ReceiptDataTableMetaData;
import org.adorsys.adcshdwr.receiptprint.ReceiptDataTwoColsBuilder;
import org.adorsys.adcshdwr.receiptprint.ReceiptDataTwoColsMetaData;
import org.adorsys.adcshdwr.receiptprint.ReceiptPrintProperties;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;

/**
 * 
 * @author Hsimo
 *
 */
public class VoucherPrintTemplatePdf extends VoucherPrintTemplate {
	
	static final String separatorText = "------------------------";
	private ByteArrayOutputStream bos;
	private PrintMode voucherPrintMode = PrintMode.open;
	private final VoucherprinterData voucherprinterData;
	
	private static ReceiptPrintProperties rpp = new ReceiptPrintProperties();
	private static ReceiptDataTableMetaData voucherTableMetaData;
	private static ReceiptDataTwoColsMetaData voucherTwoColsMetaData;
	private ReceiptDataTable rdt;
	

	public VoucherPrintTemplatePdf(VoucherprinterData voucherprinterData) {
		super();
		this.voucherprinterData = voucherprinterData;
		if (rpp.isDebug()) {
			initProperties();
		}
	}
	
	
	private static final void initProperties(){
		rpp = ReceiptPrintProperties.loadPrintProperties();
		voucherTableMetaData = new ReceiptDataTableMetaData(rpp.getMargin(), rpp.getWidth(), rpp.getFontSize(), rpp.getRowHeight());
		voucherTwoColsMetaData = new ReceiptDataTwoColsMetaData(rpp.getRowHeight(), rpp.getWidth(),rpp.getMargin(), 
				rpp.getCellMargin(), rpp.getTotalWidth());
	}

	@Override
	public void startPage() {
		try {
			printVoucherHeader();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		
	}

	private void printVoucherHeader() throws IOException {
        rdt = new ReceiptDataTable(voucherTableMetaData);
		new ReceiptDataOneColBuilder(voucherTableMetaData)
			.withBoldFont()
			.withCenterAlign()
			.withDesignation("BON AVOIR CLIENT").build(rdt);

		// underline
		if(rpp.isDisplayTitleUnderLine())
		new ReceiptDataOneColBuilder(voucherTableMetaData)
			.withCenterAlign().withDesignation(separatorText).build(rdt);
		
		// companyName  
		if(rpp.isDisplayCompanyName())
		new ReceiptDataOneColBuilder(voucherTableMetaData)
			.withDesignation(voucherprinterData.getCompany().getFullName()).build(rdt);
			
		// ownerName
		if(rpp.isDisplayOwnerName())
		new ReceiptDataOneColBuilder(voucherTableMetaData)
			.withBoldFont()
			.withDesignation(voucherprinterData.getCompany().getContact().getContactPeople()).build(rdt);

		// address 
		if(rpp.isDisplayAddress())
		new ReceiptDataOneColBuilder(voucherTableMetaData)
			.withDesignation(voucherprinterData.getCompany().getContact().getAdress()).build(rdt);
		
		if(rpp.isDisplayTelFax()){
			String phoneAndFax = "TEL: "+ voucherprinterData.getCompany().getContact().getPhone() +" "+ 
					             "FAX: " + voucherprinterData.getCompany().getContact().getFax();
			new ReceiptDataOneColBuilder(voucherTableMetaData)
				.withDesignation(phoneAndFax).build(rdt);
		}
		
		// email
		if(rpp.isDisplayEmail())
		new ReceiptDataOneColBuilder(voucherTableMetaData)
			.withDesignation("Email: "+ " " + voucherprinterData.getCompany().getContact().getEmail()).build(rdt);
		
		// compnyRegisterNumber
		if(rpp.isDisplayRegisterNumber())
		new ReceiptDataOneColBuilder(voucherTableMetaData)
			.withDesignation("RC:"+ " " + voucherprinterData.getCompany().getIdentif()).build(rdt);

		// underline
		if(rpp.isDisplayRegisterUnderline())
		new ReceiptDataOneColBuilder(voucherTableMetaData)
			.withCenterAlign().withDesignation(separatorText).build(rdt);
	}
	
	
	@Override
	public void printCdrCstmrVchrHeader(CdrCstmrVchrPrinterData cstmrVchrPrinterData) {
		try {
			printCdrCstmrVchrHeaderInternal(cstmrVchrPrinterData);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		
	}
	
	public String voucherType = "RETOUR";
	public void printCdrCstmrVchrHeaderInternal(CdrCstmrVchrPrinterData cstmrVchrPrinterData) throws IOException{
		if(rpp.isDisplaySalesAgent()){
			new ReceiptDataOneColBuilder(voucherTableMetaData)
			.withDesignation("AGENT:"+ " " + cstmrVchrPrinterData.getLogin().getFullName()).build(rdt);
		}
		new ReceiptDataOneColBuilder(voucherTableMetaData)
		.withDesignation("NUMERO DU BON:"+ " "+cstmrVchrPrinterData.getCstmrVchr().getVchrNbr()).build(rdt);
		
		new ReceiptDataOneColBuilder(voucherTableMetaData)
		.withDesignation("MONTANT DU BON:"+ " " + cstmrVchrPrinterData.getCstmrVchr().getAmt()).build(rdt);
	}
	
	
	
	@Override
	public void endPage() {
		try {
			printVoucherFooter();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		
	}
	public void printVoucherFooter() throws IOException{
		new ReceiptDataTwoColsBuilder(voucherTableMetaData, voucherTwoColsMetaData)
		.withDesignation("TYPE DE BON: ").withValue(voucherType).build(rdt);
		
		new ReceiptDataTwoColsBuilder(voucherTableMetaData, voucherTwoColsMetaData)
		.withDesignation("CLIENT: ").withValue(voucherprinterData.getCustomer()).build(rdt);
		
		new ReceiptDataTwoColsBuilder(voucherTableMetaData, voucherTwoColsMetaData)
		.withDesignation("DATE D'EMISSION: ").withValue(voucherprinterData.getVoucherDate()).build(rdt);
	}
	

	@Override
	public void closeVoucher() {
		try {
			closeVoucherInternal();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		
	}
	public void closeVoucherInternal() throws IOException{
		PDDocument pdDocument = new PDDocument();
		float docHeight = rdt.computeHeight();
		float docWidth = voucherTableMetaData.getWidth();
		PDPage sourcePage = new PDPage(new PDRectangle(voucherTableMetaData.getWidth(), rdt.computeHeight()));
		pdDocument.addPage(sourcePage);
		PDPageContentStream pageContentStream = new PDPageContentStream(pdDocument, sourcePage);
		pageContentStream.setFont(voucherTableMetaData.getFont(), voucherTableMetaData.getFontSize());
		rdt.write(pageContentStream, docWidth, docHeight);
		pageContentStream.close();
		bos = new ByteArrayOutputStream();
		try {
			pdDocument.save(bos);
		} catch (COSVisitorException e) {
			throw new IOException(e);
		}
		pdDocument.close();
		bos.close();
	}

	@Override
	public Object getPage() {
		if(bos==null) return null;
		return bos;
	}


	@Override
	public VoucherprinterData getVoucherprinterData() {
	   return voucherprinterData;
	}

	@Override
	public PrintMode getVoucherPrintMode() {
	    return voucherPrintMode;
	}

	

}
