package org.adorsys.adcshdwr.rest;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import javax.servlet.ServletOutputStream;
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
import org.adorsys.adcore.pdfreport.PdfReportTemplate;
import org.adorsys.adcshdwr.jpa.CdrCstmrVchr;
import org.adorsys.adcshdwr.jpa.CdrCstmrVchrSearchInput;
import org.adorsys.adcshdwr.jpa.CdrCstmrVchrSearchResult;
import org.adorsys.adcshdwr.jpa.CdrCstmrVchr_;


/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/cdrcstmrvchrs")
public class CdrCstmrVchrEndpoint {

	@Inject
	private CdrCstmrVchrEJB ejb;
	@Inject
	private PdfReportTemplate<CdrCstmrVchr> pdfReportTemplate;
	@Inject
	private SecurityUtil securityUtil;

	@POST
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public CdrCstmrVchr create(CdrCstmrVchr entity) {
		return detach(ejb.create(entity));
	}

	@DELETE
	@Path("/{id}")
	public Response deleteById(@PathParam("id") String id) {
		CdrCstmrVchr deleted = ejb.deleteById(id);
		if (deleted == null)
			return Response.status(Status.NOT_FOUND).build();

		return Response.ok(detach(deleted)).build();
	}

	@PUT
	@Path("/{id}")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public CdrCstmrVchr update(CdrCstmrVchr entity) {
		return detach(ejb.update(entity));
	}

	@PUT
	@Path("/cancel/{id}")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public CdrCstmrVchr cancel(CdrCstmrVchr entity) {
		return detach(ejb.cancel(entity));
	}

	@GET
	@Path("/{id}")
	@Produces({ "application/json", "application/xml" })
	public Response findById(@PathParam("id") String id) {
		CdrCstmrVchr found = ejb.findById(id);
		if (found == null)
			return Response.status(Status.NOT_FOUND).build();
		return Response.ok(detach(found)).build();
	}

	@GET
	@Produces({ "application/json", "application/xml" })
	public CdrCstmrVchrSearchResult listAll(@QueryParam("start") int start,
			@QueryParam("max") int max) {
		List<CdrCstmrVchr> resultList = ejb.listAll(start, max);
		CdrCstmrVchrSearchInput searchInput = new CdrCstmrVchrSearchInput();
		searchInput.setStart(start);
		searchInput.setMax(max);
		return new CdrCstmrVchrSearchResult((long) resultList.size(),
				detach(resultList), detach(searchInput));
	}

	@GET
	@Path("/count")
	public Long count() {
		return ejb.count();
	}
	
	@POST
	@Path("/voucherreport.pdf")
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml","application/pdf","application/octet-stream" })
	public Response buildVoucherPdfReport(CdrCstmrVchrSearchInput searchInput,@Context HttpServletResponse response) throws AdException 
	{
		
		
		String loginName = securityUtil.getCurrentLoginName();
		String lang = securityUtil.getUserLange();
		List<CdrCstmrVchr> resultList = ejb.findCustom(searchInput);
		 OutputStream os = null ;
		try {
			ByteArrayOutputStream baos = pdfReportTemplate.build(resultList, CdrCstmrVchr.class, null, loginName,lang);
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
	
		return	Response.ok(os).
		header("Content-Disposition",
				"attachment; filename=localitiesreport.pdf")
		 .build();
	}
	

	@POST
	@Path("/findBy")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public CdrCstmrVchrSearchResult findBy(CdrCstmrVchrSearchInput searchInput) {
		SingularAttribute<CdrCstmrVchr, ?>[] attributes = readSeachAttributes(searchInput);
		Long count = ejb.countBy(searchInput.getEntity(), attributes);
		List<CdrCstmrVchr> resultList = ejb.findBy(searchInput.getEntity(),
				searchInput.getStart(), searchInput.getMax(), attributes);
		return new CdrCstmrVchrSearchResult(count, detach(resultList),
				detach(searchInput));
	}

	@POST
	@Path("/countBy")
	@Consumes({ "application/json", "application/xml" })
	public Long countBy(CdrCstmrVchrSearchInput searchInput) {
		SingularAttribute<CdrCstmrVchr, ?>[] attributes = readSeachAttributes(searchInput);
		return ejb.countBy(searchInput.getEntity(), attributes);
	}

	@POST
	@Path("/findByLike")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public CdrCstmrVchrSearchResult findByLike(
			CdrCstmrVchrSearchInput searchInput) {
		SingularAttribute<CdrCstmrVchr, ?>[] attributes = readSeachAttributes(searchInput);
		Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
		List<CdrCstmrVchr> resultList = ejb.findByLike(searchInput.getEntity(),
				searchInput.getStart(), searchInput.getMax(), attributes);
		return new CdrCstmrVchrSearchResult(countLike, detach(resultList),
				detach(searchInput));
	}

	@POST
	@Path("/findCustom")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public CdrCstmrVchrSearchResult findCustom(
			CdrCstmrVchrSearchInput searchInput) {
		Long count = ejb.countCustom(searchInput);
		List<CdrCstmrVchr> resultList = ejb.findCustom(searchInput);
		return new CdrCstmrVchrSearchResult(count, detach(resultList),
				detach(searchInput));
	}

	@POST
	@Path("/countByLike")
	@Consumes({ "application/json", "application/xml" })
	public Long countByLike(CdrCstmrVchrSearchInput searchInput) {
		SingularAttribute<CdrCstmrVchr, ?>[] attributes = readSeachAttributes(searchInput);
		return ejb.countByLike(searchInput.getEntity(), attributes);
	}

	@SuppressWarnings("unchecked")
	private SingularAttribute<CdrCstmrVchr, ?>[] readSeachAttributes(
			CdrCstmrVchrSearchInput searchInput) {
		List<String> fieldNames = searchInput.getFieldNames();
		List<SingularAttribute<CdrCstmrVchr, ?>> result = new ArrayList<SingularAttribute<CdrCstmrVchr, ?>>();
		for (String fieldName : fieldNames) {
			Field[] fields = CdrCstmrVchr_.class.getFields();
			for (Field field : fields) {
				if (field.getName().equals(fieldName)) {
					try {
						result.add((SingularAttribute<CdrCstmrVchr, ?>) field
								.get(null));
					} catch (IllegalArgumentException e) {
						throw new IllegalStateException(e);
					} catch (IllegalAccessException e) {
						throw new IllegalStateException(e);
					}
				}
			}
		}
		return result.toArray(new SingularAttribute[result.size()]);
	}

	private CdrCstmrVchr detach(CdrCstmrVchr entity) {
		if (entity == null)
			return null;

		return entity;
	}

	private List<CdrCstmrVchr> detach(List<CdrCstmrVchr> list) {
		if (list == null)
			return list;
		List<CdrCstmrVchr> result = new ArrayList<CdrCstmrVchr>();
		for (CdrCstmrVchr entity : list) {
			result.add(detach(entity));
		}
		return result;
	}

	private CdrCstmrVchrSearchInput detach(CdrCstmrVchrSearchInput searchInput) {
		searchInput.setEntity(detach(searchInput.getEntity()));
		return searchInput;
	}
}