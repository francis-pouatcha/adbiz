package org.adorsys.adinvtry.rest;

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
import org.adorsys.adinvtry.jpa.InvInvtryItem;
import org.adorsys.adinvtry.jpa.InvInvtryItem_;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/invinvtryitems")
public class InvInvtryItemEndpoint extends
		CoreAbstBsnsItemEndpoint<InvInvtryItem> {

	@Inject
	private InvInvtryItemEJB ejb;
	@Inject
	private InvInvtryItemLookup lookup;

	@Inject
	private PdfReportTemplate<InvInvtryItem> pdfReportTemplate;
	@Inject
	private SecurityUtil securityUtil;

	@GET
	@Path("/invintryreport.pdf/{invNbr}")
	@Produces({ "application/json", "application/xml", "application/pdf",
			"application/octet-stream" })
	public Response buildCshdwrreportPdfReport(
			@PathParam("invNbr") String invNbr,
			@Context HttpServletResponse response) throws AdException {
		String loginName = securityUtil.getCurrentLoginName();
		String lang = securityUtil.getUserLange();
		Long countByInvtryNbr = lookup.countByBsnsObjNbr(invNbr);
		int start = 0;
		int pageSize = 100;
		OutputStream os = null;
		List<String> fields = new ArrayList<String>();
		fields.add("section");
		fields.add("lotPic");
		fields.add("artPic");
		fields.add("artName");
		fields.add("asseccedQty");
		fields.add("expirDt");
		fields.add("acsngUser");
		while (start < countByInvtryNbr) {
			int firstResult = start;
			start += pageSize;
			List<InvInvtryItem> resultList = lookup.findBBsnsObjNbr(invNbr,
					firstResult, pageSize);
			try {
				ByteArrayOutputStream baos = pdfReportTemplate.build(
						resultList, InvInvtryItem.class, fields, loginName,
						lang);
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
	protected CoreAbstBsnsItemLookup<InvInvtryItem> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstBsnsItemEJB<InvInvtryItem> getEjb() {
		return ejb;
	}

	@Override
	protected Field[] getEntityFields() {
		return InvInvtryItem_.class.getFields();
	}

	@Override
	protected CoreAbstBsnsItemSearchResult<InvInvtryItem> newSearchResult(Long size,
			List<InvInvtryItem> resultList,
			CoreAbstBsnsItemSearchInput<InvInvtryItem> searchInput) {
		return new InvInvtryItemSearchResult();
	}

	@Override
	protected CoreAbstBsnsItemSearchInput<InvInvtryItem> newSearchInput() {
		return new InvInvtryItemSearchInput();
	}
}