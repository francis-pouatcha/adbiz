/**
 * 
 */
package org.adorsys.adcshdwr.api;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.OrgContact;
import org.adorsys.adbase.jpa.OrgUnit;
import org.adorsys.adbase.rest.OrgContactLookup;
import org.adorsys.adbase.rest.OrgUnitLookup;
import org.adorsys.adcore.auth.AdcomUser;
import org.adorsys.adcore.auth.AdcomUserFactory;
import org.adorsys.adcore.enums.CoreHistoryTypeEnum;
import org.adorsys.adcore.exceptions.AdException;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchResult;
import org.adorsys.adcore.jpa.CoreSearchInput;
import org.adorsys.adcore.rest.CoreAbstBsnsObjInjector;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectManager;
import org.adorsys.adcshdwr.jpa.CdrCstmrVchr;
import org.adorsys.adcshdwr.jpa.CdrDrctSales;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesCstr;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesHstry;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesItem;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesSearchInput;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesSearchResult;
import org.adorsys.adcshdwr.jpa.CdrDrctSales_;
import org.adorsys.adcshdwr.jpa.CdrJob;
import org.adorsys.adcshdwr.jpa.CdrPymnt;
import org.adorsys.adcshdwr.jpa.CdrPymntItem;
import org.adorsys.adcshdwr.jpa.CdrStep;
import org.adorsys.adcshdwr.receiptprint.CdrDirectSalesReceiptPrintTemplatePDF;
import org.adorsys.adcshdwr.receiptprint.CdrDirectSalesReceiptPrinterData;
import org.adorsys.adcshdwr.rest.CdrCstmrVchrLookup;
import org.adorsys.adcshdwr.rest.CdrDrctSalesInjector;
import org.adorsys.adcshdwr.rest.CdrPymntItemLookup;
import org.adorsys.adcshdwr.rest.CdrPymntLookup;
import org.adorsys.adcshdwr.voucherprint.CdrVoucherPrintTemplatePdf;
import org.adorsys.adcshdwr.voucherprint.CdrVoucherprinterData;

/**
 * 
 * @author fpo
 */
@Stateless
public class CdrDrctSalesManager extends CoreAbstBsnsObjectManager<CdrDrctSales, CdrDrctSalesItem, CdrDrctSalesHstry, CdrJob, CdrStep, CdrDrctSalesCstr, CoreAbstBsnsObjectSearchInput<CdrDrctSales>> {

	@Inject
	private CdrDrctSalesInjector injector;

	@Inject
	private CdrPymntLookup pymntLookup;

	@Inject
	private CdrPymntItemLookup pymntItemLookup;
	
	@Inject
	private CdrCstmrVchrLookup cstmrVchrLookup;
	
/*	@Inject
	private AdcomUser user;*/
	
	@Resource
	private SessionContext context;
	
	@Inject
	private OrgUnitLookup orgUnitLookup;
	
	@Inject
	private OrgContactLookup orgContactLookup;
	
	@Override
	protected CoreAbstBsnsObjInjector<CdrDrctSales, CdrDrctSalesItem, CdrDrctSalesHstry, CdrJob, CdrStep, CdrDrctSalesCstr> getInjector() {
		return injector;
	}

	@Override
	protected Field[] getEntityFields() {
		return CdrDrctSales_.class.getFields();
	}

	@Override
	protected CoreAbstBsnsObjectSearchResult<CdrDrctSales> newSearchResult(Long size, List<CdrDrctSales> resultList,
			CoreAbstBsnsObjectSearchInput<CdrDrctSales> searchInput) {
		return new CdrDrctSalesSearchResult(size, resultList, searchInput);
	}

	@Override
	protected CoreAbstBsnsObjectSearchInput<CdrDrctSales> newSearchInput() {
		return new CdrDrctSalesSearchInput();
	}
	
	public CdrDirectSalesReceiptPrintTemplatePDF printReceipt(CdrDirectSalesReceiptPrinterData printerData) throws AdException, IOException{
		CdrDirectSalesReceiptPrintTemplatePDF template = new CdrDirectSalesReceiptPrintTemplatePDF(printerData);
		String cdrDrctSalesIdentif = printerData.getDrctSalesIdentif();
		CdrDrctSales drctSales = injector.getBsnsObjLookup().findByIdentif(cdrDrctSalesIdentif);
		CdrPymnt pymnt = pymntLookup.findByIdentif(drctSales.getPymntNbr());
		Long itemCount = injector.getItemLookup().countByCntnrIdentifAndDisabledDtIsNull(cdrDrctSalesIdentif);
		int processed = 0;
		
		AdcomUser user = AdcomUserFactory.getAdcomUser(context);
		OrgUnit tenant = orgUnitLookup.findTenant(user.getRealm());
		OrgContact orgContact = orgContactLookup.findFirstMainContact(tenant.getIdentif());
		template.startPage(drctSales, pymnt, tenant, orgContact, user);
		List<CdrDrctSalesHstry> hstry = injector.getHstrLookup().findByEntIdentifAndEntStatusOrderByIdDesc(cdrDrctSalesIdentif, CoreHistoryTypeEnum.POSTED.name(), 0, 1);
		if(hstry.isEmpty()){
			hstry = injector.getHstrLookup().findByEntIdentifAndEntStatusOrderByIdDesc(cdrDrctSalesIdentif, CoreHistoryTypeEnum.CLOSED.name(), 0, 1);
		}
		if(hstry.isEmpty())
			throw new AdException("Can not print receipt for unclosed sales.");
		template.printCdrDrctSalesHeader(drctSales, hstry.iterator().next());
		while(processed<itemCount){
			int start = processed;
			processed += CoreSearchInput.MAX_MAX;
			List<CdrDrctSalesItem> items = injector.getItemLookup().findByCntnrIdentifAndDisabledDtIsNullOrderByIdentifAsc(cdrDrctSalesIdentif, start, CoreSearchInput.MAX_MAX);
			template.addItems(items);
		}
		
		template.printReceiptTotal(drctSales);

		// Printing Payment
		Long itemsCount = pymntItemLookup.countByCntnrIdentif(pymnt.getIdentif());
		processed = 0;
		while(processed<itemsCount){
			int start = processed;
			processed+=CoreSearchInput.MAX_MAX;
			List<CdrPymntItem> pymntItems = pymntItemLookup.findByCntnrIdentif(pymnt.getIdentif(), start, CoreSearchInput.MAX_MAX);
			for (CdrPymntItem pymntItem : pymntItems) {
				template.printPymntLine(pymntItem);
			}
		}
		template.printTiketMessage(tenant);
		return template;
	}
	
	public CdrVoucherPrintTemplatePdf printVoucher(CdrVoucherprinterData printerData) throws IOException{
		AdcomUser user = AdcomUserFactory.getAdcomUser(context);
		CdrVoucherPrintTemplatePdf template = new CdrVoucherPrintTemplatePdf(printerData);
		CdrCstmrVchr cstmrVchr = cstmrVchrLookup.findByIdentif(printerData.getVoucherIdentif());
		OrgUnit tenant = orgUnitLookup.findTenant(user.getRealm());
		OrgContact orgContact = orgContactLookup.findFirstMainContact(tenant.getIdentif());
		template.printPdfVoucher(cstmrVchr, tenant, orgContact, user);
		return template;
	}
}