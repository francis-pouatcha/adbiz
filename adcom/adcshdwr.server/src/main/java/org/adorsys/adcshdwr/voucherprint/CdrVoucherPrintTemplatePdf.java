package org.adorsys.adcshdwr.voucherprint;

import java.io.IOException;
import java.io.OutputStream;

import org.adorsys.adbase.jpa.OrgContact;
import org.adorsys.adbase.jpa.OrgUnit;
import org.adorsys.adcore.auth.AdcomUser;
import org.adorsys.adcore.print.ReceiptDataOneColBuilder;
import org.adorsys.adcore.print.ReceiptDataTable;
import org.adorsys.adcore.print.ReceiptDataTableMetaData;
import org.adorsys.adcore.print.ReceiptDataTwoColsBuilder;
import org.adorsys.adcore.print.ReceiptDataTwoColsMetaData;
import org.adorsys.adcore.print.ReceiptPrintProperties;
import org.adorsys.adcshdwr.jpa.CdrCstmrVchr;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;

public class CdrVoucherPrintTemplatePdf {
	
	private ReceiptPrintProperties rpp;
	private ReceiptDataTableMetaData voucherTableMetaData;
	private ReceiptDataTwoColsMetaData voucherTwoColsMetaData;
	private ReceiptDataTable rdt;

	public CdrVoucherPrintTemplatePdf(CdrVoucherprinterData voucherprinterData) {
		this.rpp = voucherprinterData.getReceiptPrintProperties();
		voucherTableMetaData = new ReceiptDataTableMetaData(rpp.getMargin(), rpp.getWidth(), rpp.getFontSize(), rpp.getRowHeight());
		voucherTwoColsMetaData = new ReceiptDataTwoColsMetaData(rpp.getRowHeight(), rpp.getWidth(),rpp.getMargin(), 
				rpp.getCellMargin(), rpp.getTotalWidth());
	}
	
	private void startPage(OrgUnit tenant, OrgContact orgContact) {
		try {
			printVoucherHeader(tenant, orgContact);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		
	}

	private void printVoucherHeader(OrgUnit tenant, OrgContact orgContact) throws IOException {
        rdt = new ReceiptDataTable(voucherTableMetaData);
		new ReceiptDataOneColBuilder(voucherTableMetaData)
			.withBoldFont()
			.withCenterAlign()
			.withDesignation("BON AVOIR CLIENT").build(rdt);

		// underline
		if(rpp.isDisplayTitleUnderLine())
		new ReceiptDataOneColBuilder(voucherTableMetaData)
			.withCenterAlign().withDesignation(rpp.getSeparatorText()).build(rdt);
		
		// companyName  
		if(rpp.isDisplayCompanyName())
		new ReceiptDataOneColBuilder(voucherTableMetaData)
			.withDesignation(tenant.getFullName()).build(rdt);
			
		// ownerName
		if(rpp.isDisplayOwnerName())
		new ReceiptDataOneColBuilder(voucherTableMetaData)
			.withBoldFont()
			.withDesignation(orgContact.getContactPeople()).build(rdt);

		// address 
		if(rpp.isDisplayAddress())
		new ReceiptDataOneColBuilder(voucherTableMetaData)
			.withDesignation(orgContact.getAdress()).build(rdt);
		
		if(rpp.isDisplayTelFax()){
			String phoneAndFax = "TEL: "+ orgContact.getPhone() +" "+ 
					             "FAX: " + orgContact.getFax();
			new ReceiptDataOneColBuilder(voucherTableMetaData)
				.withDesignation(phoneAndFax).build(rdt);
		}
		
		// email
		if(rpp.isDisplayEmail())
		new ReceiptDataOneColBuilder(voucherTableMetaData)
			.withDesignation("Email: "+ " " + orgContact.getEmail()).build(rdt);
		
		// compnyRegisterNumber
		if(rpp.isDisplayRegisterNumber())
		new ReceiptDataOneColBuilder(voucherTableMetaData)
			.withDesignation("RC:"+ " " + tenant.getRegisterNumber()).build(rdt);

		// underline
		if(rpp.isDisplayRegisterUnderline())
		new ReceiptDataOneColBuilder(voucherTableMetaData)
			.withCenterAlign().withDesignation(rpp.getSeparatorText()).build(rdt);
	}
	
	private String voucherType = "RETOUR";
	private void printCdrCstmrVchrHeader(CdrCstmrVchr cstmrVchr, AdcomUser user) throws IOException{
		if(rpp.isDisplaySalesAgent()){
			new ReceiptDataOneColBuilder(voucherTableMetaData)
			.withDesignation("AGENT:"+ " " + user.getFullName()).build(rdt);
		}
		new ReceiptDataOneColBuilder(voucherTableMetaData)
		.withDesignation("NUMERO DU BON:"+ " "+cstmrVchr.getIdentif()).build(rdt);
		
		new ReceiptDataOneColBuilder(voucherTableMetaData)
		.withDesignation("MONTANT DU BON:"+ " " + cstmrVchr.getRemainingAmt()).build(rdt);
	}
	
	private void endPage(CdrCstmrVchr cstmrVchr) {
		try {
			printVoucherFooter(cstmrVchr);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
	
	private void printVoucherFooter(CdrCstmrVchr cstmrVchr) throws IOException{
		new ReceiptDataTwoColsBuilder(voucherTableMetaData, voucherTwoColsMetaData)
		.withDesignation("TYPE DE BON: ").withValue(voucherType).build(rdt);
		
		new ReceiptDataTwoColsBuilder(voucherTableMetaData, voucherTwoColsMetaData)
		.withDesignation("CLIENT: ").withValue(cstmrVchr.getBsnsPtnrName()).build(rdt);
		
		new ReceiptDataTwoColsBuilder(voucherTableMetaData, voucherTwoColsMetaData)
		.withDesignation("DATE D'EMISSION: ").withValue(cstmrVchr.getValueDt().toString()).build(rdt);
	}

	public void writeTo(OutputStream os) throws IOException{
		PDDocument pdDocument = new PDDocument();
		float docHeight = rdt.computeHeight();
		float docWidth = voucherTableMetaData.getWidth();
		PDPage sourcePage = new PDPage(new PDRectangle(voucherTableMetaData.getWidth(), rdt.computeHeight()));
		pdDocument.addPage(sourcePage);
		PDPageContentStream pageContentStream = new PDPageContentStream(pdDocument, sourcePage);
		pageContentStream.setFont(voucherTableMetaData.getFont(), voucherTableMetaData.getFontSize());
		rdt.write(pageContentStream, docWidth, docHeight);
		pageContentStream.close();
		try {
			pdDocument.save(os);
		} catch (COSVisitorException e) {
			throw new IOException(e);
		}
		pdDocument.close();
	}

	public final void printPdfVoucher(CdrCstmrVchr cstmrVchr, OrgUnit tenant, OrgContact orgContact, AdcomUser user) throws IOException{
		startPage(tenant, orgContact);
		printCdrCstmrVchrHeader(cstmrVchr, user);
		endPage(cstmrVchr);
	}
}
