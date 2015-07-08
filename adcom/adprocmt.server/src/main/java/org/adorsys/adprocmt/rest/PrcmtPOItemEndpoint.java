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
import javax.persistence.metamodel.SingularAttribute;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adcore.exceptions.AdException;
import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchInput;
import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchResult;
import org.adorsys.adcore.pdfreport.PdfReportTemplate;
import org.adorsys.adcore.rest.CoreAbstBsnsItemEJB;
import org.adorsys.adcore.rest.CoreAbstBsnsItemEndpoint;
import org.adorsys.adcore.rest.CoreAbstBsnsItemLookup;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem;
import org.adorsys.adprocmt.jpa.PrcmtPOItem;
import org.adorsys.adprocmt.jpa.PrcmtPOItemSearchInput;
import org.adorsys.adprocmt.jpa.PrcmtPOItemSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/prcmtpoitems")
public class PrcmtPOItemEndpoint extends CoreAbstBsnsItemEndpoint<PrcmtPOItem> {

	@Inject
	private PrcmtPOItemEJB ejb;
	@Inject
	private PrcmtPOItemLookup lookup;
	@Inject
	private PdfReportTemplate<PrcmtPOItem> pdfReportTemplate;
	@Inject
	private SecurityUtil securityUtil;

	@GET
	@Path("/orderreport.pdf/{poNbr}")
	@Produces({ "application/json", "application/xml", "application/pdf",
			"application/octet-stream" })
	public Response buildCshdwrreportPdfReport(
			@PathParam("poNbr") String poNbr,
			@Context HttpServletResponse response) throws AdException {
		String loginName = securityUtil.getCurrentLoginName();
		String lang = securityUtil.getUserLange();
		Long count = lookup.countByBsnsObjNbr(poNbr);
		int start = 0;
		int pageSize = 100;
		OutputStream os = null;

		List<String> fields = new ArrayList<String>();
		fields.add("artPic");
		fields.add("artName");
		fields.add("qtyOrdered");
		fields.add("freeQty");
		fields.add("stkQtyPreOrder");
		fields.add("pppuPreTax");
		fields.add("strgSection");
		fields.add("supplier");
		while (start < count) {
			int firstResult = start;
			start += pageSize;
			List<PrcmtPOItem> resultList = lookup.findBBsnsObjNbr(poNbr,firstResult, pageSize);
			try {
				ByteArrayOutputStream baos = pdfReportTemplate.build(resultList,
						PrcmtPOItem.class, fields, loginName, lang);
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

		return Response
				.ok(os)
				.header("Content-Disposition",
						"attachment; filename=localitiesreport.pdf").build();
	}

	@Override
	protected CoreAbstBsnsItemLookup<PrcmtPOItem> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstBsnsItemEJB<PrcmtPOItem> getEjb() {
		return ejb;
	}

	@Override
	protected Field[] getEntityFields() {
		return PrcmtPOItem_.class.getFields();
	}

	@Override
	protected CoreAbstBsnsItemSearchResult<PrcmtPOItem> newSearchResult(Long size,
			List<PrcmtPOItem> resultList,
			CoreAbstBsnsItemSearchInput<PrcmtPOItem> searchInput) {
		return new PrcmtPOItemSearchResult(size, resultList, searchInput);
	}

	@Override
	protected CoreAbstBsnsItemSearchInput<PrcmtPOItem> newSearchInput() {
		return new PrcmtPOItemSearchInput();
	}

}