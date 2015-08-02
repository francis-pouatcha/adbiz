/**
 * 
 */
package org.adorsys.adcshdwr.api;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.adorsys.adcore.exceptions.AdException;
import org.adorsys.adcshdwr.jpa.CdrCstmrVchr;
import org.adorsys.adcshdwr.receiptprint.ReceiptPrintTemplatePDF;
import org.adorsys.adcshdwr.rest.CdrCstmrVchrEJB;
import org.adorsys.adcshdwr.voucherprint.VoucherPrintTemplatePdf;

/**
 * @author boriswaguia
 *@author guymoyo
 */
@Path("/cdrdrctsalesmanager")
public class CdrDrctSalesManagerEndpoint {

	@Inject
	private CdrDrctSalesManager ejb;
	
	@Inject
	CdrCstmrVchrEJB cdrCstmrVchrEJB;
	
	@Inject
	private CdrDrctSalesPrinterEJB printerEJB;
	
	
	@POST
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public CdrDsArtHolder save(CdrDsArtHolder cdrDsArtHolder, @Context HttpServletRequest request) throws AdException {
		 CdrDsArtHolder closeSales = ejb.closeSales(cdrDsArtHolder);
		 return closeSales;
	}

	// TODO: talk with francis. No full scan.
	@GET
	@Path("/receiptreport.pdf/{id}")
	@Produces({"application/pdf","application/octet-stream" })
	public Response buildReceiptPdfReport(@PathParam("id") String id, @Context HttpServletResponse response) throws AdException{
//		CdrDsArtHolder cdrDsArtHolder = ejb.findCdrDsArtHolder(id);
		OutputStream os = null;
//		ReceiptPrintTemplatePDF worker = printerEJB.printReceiptPdf(cdrDsArtHolder);
//		try {
// 			ByteArrayOutputStream baos = (ByteArrayOutputStream)worker.getPage();
//			response.setContentLength(baos.size());	
//			os = response.getOutputStream();
//			baos.writeTo(os);
//			os.flush();
//			os.close();
//		} catch (Exception e) {
//			throw new AdException("Error printing");
//		}
 		return	Response.ok(os)
 				.header("Content-Disposition","attachment; filename=receiptreport.pdf")
 				.build();
	  }
	
	
	
	   @GET
	   @Path("/{id}")
	   @Produces({ "application/json", "application/xml" })
	   public CdrDsArtHolder findCdrDsArtHolder(@PathParam("id") String id)
	   {
		   return ejb.findCdrDsArtHolder(id);
		   
	   }
//	 
//	 	@POST
//	 	@Path("/returnProduct")
//		@Consumes({ "application/json", "application/xml" })
//		@Produces({ "application/json", "application/xml" })
//		public CdrDsArtHolder returnProduct(CdrDsArtHolder cdrDsArtHolder) throws AdException {
//			return ejb.returnProduct(cdrDsArtHolder);
//		}

	 	@GET
		@Path("/voucherreport.pdf/{dsNbr}")
		@Produces({"application/pdf","application/octet-stream" })
		public Response buildVoucherPdfReport(@PathParam("dsNbr") String dsNbr, @Context HttpServletResponse response) throws AdException 
		{
	 		OutputStream os = null;
	 		CdrCstmrVchr voucher = cdrCstmrVchrEJB.findByDsNbr(dsNbr).iterator().next();
	 		VoucherPrintTemplatePdf worker = printerEJB.printVoucherPdf(voucher);
	 		try {
	 			ByteArrayOutputStream baos = (ByteArrayOutputStream)worker.getPage();
				response.setContentLength(baos.size());
				os = response.getOutputStream();
				baos.writeTo(os);
				os.flush();
				os.close();
			} catch (Exception e) {
				throw new AdException("Error printing");
			}
	 		
	 		return	Response.ok(os)
	 				.header("Content-Disposition","attachment; filename=voucherreport.pdf")
	 				.build();
		}
}
