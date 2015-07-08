package org.adorsys.adprocmt.rest;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adcore.exceptions.AdException;
import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchInput;
import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchResult;
import org.adorsys.adcore.pdfreport.PdfReportTemplate;
import org.adorsys.adcore.rest.CoreAbstBsnsItemEJB;
import org.adorsys.adcore.rest.CoreAbstBsnsItemEndpoint;
import org.adorsys.adcore.rest.CoreAbstBsnsItemLookup;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem_;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItemSearchInput;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItemSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/prcmtdlvryitems")
public class PrcmtDlvryItemEndpoint extends
		CoreAbstBsnsItemEndpoint<PrcmtDlvryItem> {

	@Inject
	private PrcmtDlvryItemEJB ejb;
	@Inject
	private PrcmtDlvryItemLookup lookup;
	@Inject
	private PdfReportTemplate<PrcmtDlvryItem> pdfReportTemplate;
	@Inject
	private SecurityUtil securityUtil;

	@Override
	protected CoreAbstBsnsItemLookup<PrcmtDlvryItem> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstBsnsItemEJB<PrcmtDlvryItem> getEjb() {
		return ejb;
	}

	@Override
	protected Field[] getEntityFields() {
		return PrcmtDlvryItem_.class.getFields();
	}

	@Override
	protected CoreAbstBsnsItemSearchResult<PrcmtDlvryItem> newSearchResult(
			Long size, List<PrcmtDlvryItem> resultList,
			CoreAbstBsnsItemSearchInput<PrcmtDlvryItem> searchInput) {
		return new PrcmtDlvryItemSearchResult(size, resultList, searchInput);
	}

	@Override
	protected CoreAbstBsnsItemSearchInput<PrcmtDlvryItem> newSearchInput() {
		return new PrcmtDlvryItemSearchInput();
	}
	
	   
	   @GET
		@Path("/deliveryreport.pdf/{dlvryNbr}")
		@Produces({ "application/json", "application/xml","application/pdf","application/octet-stream" })
		public Response builddlvryreportPdfReport(@PathParam("dlvryNbr") String dlvryNbr,@Context HttpServletResponse response) throws AdException 
		{	
		   	String loginName = securityUtil.getCurrentLoginName();
			String lang = securityUtil.getUserLange();
			Long count = lookup.countByBsnsObjNbr(dlvryNbr);
			int start = 0;
			int pageSize = 100;
			OutputStream os = null;
			List<String> fields = new ArrayList<String>();
			 fields.add("lotPic");
			 fields.add("artPic");
			 fields.add("artName");
			 fields.add("qtyDlvrd");
			 fields.add("freeQty");
			 fields.add("stkQtyPreDlvry");
			 fields.add("sppuPreTax");
			 fields.add("expirDt");
			 fields.add("supplier");
			while (start < count) {
				int firstResult = start;
				start += pageSize;
				List<PrcmtDlvryItem> resultList = lookup.findBBsnsObjNbr(dlvryNbr,firstResult, pageSize);
				try {
					ByteArrayOutputStream baos = pdfReportTemplate.build(resultList, PrcmtDlvryItem.class, fields, loginName, lang);
					// the contentlength
					response.setContentLength(baos.size());
					
					// write ByteArrayOutputStream to the ServletOutputStream
					os = response.getOutputStream();
					baos.writeTo(os);
					os.flush();
					os.close();
					
				} catch (Exception e) {
					throw new AdException("Error printing");
				}
			}
		
			return	Response.ok(os).
			header("Content-Disposition",
					"attachment; filename=localitiesreport.pdf")
			 .build();
		}
}