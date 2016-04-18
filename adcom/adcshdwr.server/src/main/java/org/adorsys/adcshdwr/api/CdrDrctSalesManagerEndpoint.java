package org.adorsys.adcshdwr.api;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.adorsys.adcore.exceptions.AdException;
import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchInput;
import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchResult;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchInput;
import org.adorsys.adcore.rest.CoreAbstArchiveManager;
import org.adorsys.adcore.rest.CoreAbstBsnsManagerEndpoint;
import org.adorsys.adcore.rest.CoreAbstBsnsObjInjector;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectManager;
import org.adorsys.adcshdwr.jpa.CdrDrctSales;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesCstr;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesHstry;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesItem;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesItemSearchInput;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesItemSearchResult;
import org.adorsys.adcshdwr.jpa.CdrJob;
import org.adorsys.adcshdwr.jpa.CdrStep;
import org.adorsys.adcshdwr.receiptprint.CdrDirectSalesReceiptPrintTemplatePDF;
import org.adorsys.adcshdwr.receiptprint.CdrDirectSalesReceiptPrinterData;
import org.adorsys.adcshdwr.rest.CdrCstmrVchrEJB;
import org.adorsys.adcshdwr.rest.CdrDrctSalesInjector;
import org.adorsys.adcshdwr.voucherprint.CdrVoucherPrintTemplatePdf;
import org.adorsys.adcshdwr.voucherprint.CdrVoucherprinterData;

/**
 * 
 * @author fpo
 *
 */
@Path("/directsales")
public class CdrDrctSalesManagerEndpoint extends CoreAbstBsnsManagerEndpoint<CdrDrctSales, CdrDrctSalesItem, CdrDrctSalesHstry, CdrJob, CdrStep, CdrDrctSalesCstr>{

	@Inject
	private CdrDrctSalesManager manager;
	
	@Inject
	private CdrDrctSalesInjector injector;
	
	@Inject
	private CdrDrctSalesArchiveManager archiveManager;
	
	@Inject
	CdrCstmrVchrEJB cdrCstmrVchrEJB;

	@Override
	protected CoreAbstBsnsObjectManager<CdrDrctSales, CdrDrctSalesItem, CdrDrctSalesHstry, CdrJob, CdrStep, CdrDrctSalesCstr, CoreAbstBsnsObjectSearchInput<CdrDrctSales>> getBsnsObjManager() {
		return manager;
	}

	@Override
	protected CoreAbstBsnsObjInjector<CdrDrctSales, CdrDrctSalesItem, CdrDrctSalesHstry, CdrJob, CdrStep, CdrDrctSalesCstr> getInjector() {
		return injector;
	}

	@Override
	protected CoreAbstBsnsItemSearchInput<CdrDrctSalesItem> newItemSearchInput() {
		return new CdrDrctSalesItemSearchInput();
	}

	@Override
	protected CoreAbstBsnsItemSearchResult<CdrDrctSalesItem> newItemSearchResult(long count,
			List<CdrDrctSalesItem> resultList, CoreAbstBsnsItemSearchInput<CdrDrctSalesItem> itemSearchInput) {
		return new CdrDrctSalesItemSearchResult(count, resultList, itemSearchInput);
	}

	@Override
	protected CoreAbstArchiveManager<CdrDrctSales, ?, CdrDrctSalesItem, ?, CdrDrctSalesHstry, ?, CdrJob, CdrStep, CdrDrctSalesCstr> getArchiveManager() {
		return archiveManager;
	}
	
	@POST
	@Path("/{id}/receipt.pdf")
	@Produces({"application/pdf","application/octet-stream" })
	public Response printReceiptPdf(@PathParam("id") String id, @Context HttpServletResponse response, CdrDirectSalesReceiptPrinterData printerData) throws AdException{
		try {
			CdrDirectSalesReceiptPrintTemplatePDF templatePDF = manager.printReceipt(printerData);
			ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
			templatePDF.writeTo(baos);
			response.setContentLength(baos.size());	
			OutputStream os = response.getOutputStream();
			baos.writeTo(os);
			os.flush();
			os.close();
			return	Response.ok(os)
					.header("Content-Disposition","attachment; filename=receiptreport.pdf")
					.build();
		} catch (Exception e) {
			throw new AdException("Error printing");
		}
	  }

	@POST
	@Path("/{id}/voucher.pdf")
	@Produces({"application/pdf","application/octet-stream" })
	public Response printVoucherPdf(@PathParam("id") String id, @Context HttpServletResponse response, CdrVoucherprinterData printerData) throws AdException{
		try {
			CdrVoucherPrintTemplatePdf voucher = manager.printVoucher(printerData);
			ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
			voucher.writeTo(baos);
			response.setContentLength(baos.size());	
			OutputStream os = response.getOutputStream();
			baos.writeTo(os);
			os.flush();
			os.close();
			return	Response.ok(os)
					.header("Content-Disposition","attachment; filename=receiptreport.pdf")
					.build();
		} catch (Exception e) {
			throw new AdException("Error printing");
		}
	  }
}
