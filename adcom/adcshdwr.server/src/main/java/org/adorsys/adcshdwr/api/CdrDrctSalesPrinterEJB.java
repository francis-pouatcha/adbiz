package org.adorsys.adcshdwr.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.Login;
import org.adorsys.adbase.jpa.OrgContact;
import org.adorsys.adbase.jpa.OrgUnit;
import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adcore.utils.DateUtil;
import org.adorsys.adcshdwr.jpa.CdrCstmrVchr;
import org.adorsys.adcshdwr.jpa.CdrDsArtItem;
import org.adorsys.adcshdwr.jpa.CdrDsArtItemSearchResult;
import org.adorsys.adcshdwr.jpa.CdrDsPymntItem;
import org.adorsys.adcshdwr.receiptprint.CdrDrctSalesPrinterData;
import org.adorsys.adcshdwr.receiptprint.ReceiptPrintTemplatePDF;
import org.adorsys.adcshdwr.receiptprint.ReceiptPrinterData;
import org.adorsys.adcshdwr.rest.CdrDsArtItemEJB;
import org.adorsys.adcshdwr.rest.CdrDsPymntItemEJB;
import org.adorsys.adcshdwr.voucherprint.CdrCstmrVchrPrinterData;
import org.adorsys.adcshdwr.voucherprint.VoucherPrintTemplatePdf;
import org.adorsys.adcshdwr.voucherprint.VoucherprinterData;
import org.apache.commons.lang3.StringUtils;


@Stateless
public class CdrDrctSalesPrinterEJB {
	
	@Inject
	private SecurityUtil securityUtil;
	
	@Inject
	CdrDsPymntItemEJB cdrDsPymntItemEJB;
	
	@Inject
	private CdrDsArtItemEJB cdrDsArtItemEJB;
	
	

	/*public void printReceiptPdf(CdrDrctSales sales){
		CdrDrctSalesPrinterData drctSalesPrinterData = createCdrDrctSalesPrinterData(sales);
		ReceiptPrinterData receiptPrinterData = createReceiptPrinterData(sales);
		ReceiptPrintTemplatePDF worker = new ReceiptPrintTemplatePDF(receiptPrinterData);
		worker.printPdfReceipt(drctSalesPrinterData);
		
		// Output pdf
		 byte[] data =  (byte[]) worker.getPage();
		String pymntDocNbr = worker.getReceiptPrinterData().getPayment().getPymntDocNbr();
		String fileName = pymntDocNbr+".pdf";
		PDDocument document;
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(fileName);
			IOUtils.write(data, fileOutputStream);
			File file = new File(fileName);
			PrintMode printMode = worker.getReceiptPrintMode();
			document = PDDocument.load(file);
			switch (printMode) {
			case open:
				 Desktop.getDesktop().open(new File(fileName));
				break;
            case print:
             	 Desktop.getDesktop().print(new File(fileName));
            	 break;
			/* case open:
				try {
					document.print();
				} catch (PrinterException e) {
					throw new IllegalArgumentException();
				}
				document.close();
				break;
            /* case print:
            	try {
					document.silentPrint();
				} catch (PrinterException e) {
					throw new IllegalArgumentException();
				}
            	document.close();
            	break;
			default:
				break;
			}
			
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		finally{
			
		}
		
	}*/
	
	/**
	 * Printing Receipt pdf
	 * @param sales
	 */
	public ReceiptPrintTemplatePDF printReceiptPdf(CdrDsArtHolder salesArtHolder){
		CdrDrctSalesPrinterData drctSalesPrinterData = createCdrDrctSalesPrinterData(salesArtHolder);
		ReceiptPrinterData receiptPrinterData = createReceiptPrinterData(salesArtHolder);
		ReceiptPrintTemplatePDF worker = new ReceiptPrintTemplatePDF(receiptPrinterData);
		worker.printPdfReceipt(drctSalesPrinterData);
		return worker;
	}
	
	
	/**
	 * Print Voucher pdf
	 */
	public VoucherPrintTemplatePdf printVoucherPdf(CdrCstmrVchr  cdrCstmrVchr){
		
		CdrCstmrVchrPrinterData cstmrVchrPrinterData = createCstmrVchrPrinterData(cdrCstmrVchr);
		VoucherprinterData vchrPrinterData = createVchrPrinterData(cdrCstmrVchr);
		VoucherPrintTemplatePdf worker = new VoucherPrintTemplatePdf(vchrPrinterData);
		worker.printPdfVoucher(cstmrVchrPrinterData);
	    return worker;
	}
	
	
	public CdrDrctSalesPrinterData createCdrDrctSalesPrinterData(CdrDsArtHolder salesArtHolder){
		Login connectedUser = securityUtil.getConnectedUser();
		OrgUnit company = securityUtil.getCurrentOrgUnit();
		if(company!=null){
			company.setContact(createSampleOrgContact());
		}else {
			OrgUnit orgUnit = createSampleOrgUnit();
			OrgContact contact = createSampleOrgContact(); 
			orgUnit.setContact(contact);
			company = orgUnit;
		}
		List<CdrDsArtItemHolder> itemsHolder = salesArtHolder.getItems();
		List<CdrDsArtItem> items = new ArrayList<CdrDsArtItem>();
 		for(CdrDsArtItemHolder itemHolder: itemsHolder){
			items.add(itemHolder.getItem());
		}
		CdrDsArtItemSearchResult cdrDsArtItemSearchResult = new CdrDsArtItemSearchResult();
		cdrDsArtItemSearchResult.setResultList(items);
		CdrDrctSalesPrinterData cdrDrctSalesPrinterData = new CdrDrctSalesPrinterData(salesArtHolder.getCdrDrctSales(), connectedUser, company, cdrDsArtItemSearchResult);
	
		return cdrDrctSalesPrinterData;
	}
	
	public ReceiptPrinterData createReceiptPrinterData(CdrDsArtHolder salesArtHolder){
		Login cashier = securityUtil.getConnectedUser();
		OrgUnit company = securityUtil.getCurrentOrgUnit();
		if(company!=null){
			company.setContact(createSampleOrgContact());
		}else {
			OrgUnit orgUnit = createSampleOrgUnit();
			OrgContact contact = createSampleOrgContact(); 
			orgUnit.setContact(contact);
			company = orgUnit;
		}
		CdrDsPymntItem cdrDsPymntItem = cdrDsPymntItemEJB.findByDsNbr(salesArtHolder.getCdrDrctSales().getDsNbr()).iterator().next();
		String customer="CLIENT DIVERS";
		String paymentDate = DateUtil.format(new Date(), DateUtil.DATE_TIME_FORMAT);
		ReceiptPrinterData receiptPrinterData = new ReceiptPrinterData(customer, paymentDate , cdrDsPymntItem, cashier, company);
		return receiptPrinterData;
	}
	
	
	public CdrCstmrVchrPrinterData createCstmrVchrPrinterData(CdrCstmrVchr  cdrCstmrVchr){
		Login connectedUser = securityUtil.getConnectedUser();
		OrgUnit company = securityUtil.getCurrentOrgUnit();
		if(company!=null){
			company.setContact(createSampleOrgContact());
		}else {
			OrgUnit orgUnit = createSampleOrgUnit();
			OrgContact contact = createSampleOrgContact(); 
			orgUnit.setContact(contact);
			company = orgUnit;
		}
		CdrCstmrVchrPrinterData cdrCstmrVchrPrinterData = new CdrCstmrVchrPrinterData(cdrCstmrVchr, connectedUser, company);
		return cdrCstmrVchrPrinterData;
	}
	
	
	public VoucherprinterData createVchrPrinterData(CdrCstmrVchr  cdrCstmrVchr){
		Login connectedUser = securityUtil.getConnectedUser();
		OrgUnit company = securityUtil.getCurrentOrgUnit();
		if(company!=null){
			company.setContact(createSampleOrgContact());
		}else {
			OrgUnit orgUnit = createSampleOrgUnit();
			OrgContact contact = createSampleOrgContact(); 
			orgUnit.setContact(contact);
			company = orgUnit;
		}
	    String customer = StringUtils.isBlank(cdrCstmrVchr.getCstmrName())?"CLIENTS DIVERS":cdrCstmrVchr.getCstmrName();
		VoucherprinterData voucherprinterData = new VoucherprinterData(customer, DateUtil.format(cdrCstmrVchr.getPrntDt(), DateUtil.DATE_TIME_FORMAT), connectedUser, company);
		return voucherprinterData;
	}
	
	
	public OrgUnit createSampleOrgUnit(){
		OrgUnit orgUnit = new OrgUnit();
		orgUnit.setFullName("PHARMACIE ALLIANCE");
		orgUnit.setShortName("PHALLLIANCE");
		orgUnit.setIdentif("RC0000013450");
		orgUnit.setIdentif("RC/00001");
		
		return orgUnit;
	}
	
	public OrgContact createSampleOrgContact(){
		OrgContact orgContact = new OrgContact();
		orgContact.setCountry("CAMEROUN");
		orgContact.setEmail("phalliance@yahoo.fr");
		orgContact.setCity("Douala");
		orgContact.setPhone("+237 22 31 72 56");
		orgContact.setRegion("Littoral");
		orgContact.setStreet("Akwa");
		orgContact.setFax("+237 22 31 72 24");
		orgContact.setContactPeople("DR TCHIENGOU PLUCHERIE");
		orgContact.setOuIdentif("RC/00001");
		orgContact.setContactIndex("MERCI DE VOTRE VISITE, BONNE GUERISON.");
		
		return orgContact;
	}

}
