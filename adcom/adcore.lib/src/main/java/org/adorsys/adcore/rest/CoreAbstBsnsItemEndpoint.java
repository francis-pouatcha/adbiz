package org.adorsys.adcore.rest;

import java.io.File;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.adorsys.adcore.auth.AdcomUser;
import org.adorsys.adcore.exceptions.AdException;
import org.adorsys.adcore.jpa.CoreAbstBsnsItem;
import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchInput;
import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchResult;
import org.adorsys.adcore.jpa.CoreSearchInput;
import org.adorsys.adcore.pdfreport.PdfReportProperties;
import org.adorsys.adcore.pdfreport.PdfReportTemplate;
import org.adorsys.adcore.props.AbstEntiyProps;
import org.apache.commons.io.FileUtils;

public abstract class CoreAbstBsnsItemEndpoint<E extends CoreAbstBsnsItem, SI extends CoreAbstBsnsItemSearchInput<E>, SR extends CoreAbstBsnsItemSearchResult<E>> {

	protected abstract CoreAbstBsnsItemLookup<E> getLookup();
	protected abstract Field[] getEntityFields();
	protected abstract SR newSearchResult(Long size, List<E> resultList,CoreAbstBsnsItemSearchInput<E> searchInput);
	protected abstract SR newSearchResult(Long size, Long total, List<E> resultList,CoreAbstBsnsItemSearchInput<E> searchInput);
	protected abstract SI newSearchInput();
	protected abstract AbstEntiyProps getEntityProps();
	
	@Inject
	private AdcomUser userPrincipal;

	@GET
	@Path("/{id}")
	@Produces({ "application/json", "application/xml" })
	public Response findById(@PathParam("id") String id) {
		E found = getLookup().findById(id);
		if (found == null)
			return Response.status(Status.NOT_FOUND).build();
		return Response.ok(detach(found)).build();
	}

	@GET
	@Produces({ "application/json", "application/xml" })
	public SR listAll(
			@QueryParam("start") int start, @QueryParam("max") int max) {
		List<E> resultList = getLookup().listAll(start, max);
		CoreAbstBsnsItemSearchInput<E> searchInput = newSearchInput();
		searchInput.setStart(start);
		searchInput.setMax(max);
		return newSearchResult((long) resultList.size(),
				detach(resultList), detach(searchInput));
	}

	@GET
	@Path("/count")
	public Long count() {
		return getLookup().count();
	}

	@POST
	@Path("/findBy")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public SR findBy(
			CoreAbstBsnsItemSearchInput<E> searchInput) {
		SingularAttribute<E, ?>[] attributes = readSeachAttributes(searchInput);
		Long count = getLookup().countBy(searchInput.getEntity(), attributes);
		List<E> resultList = getLookup().findBy(
				searchInput.getEntity(), searchInput.getStart(),
				searchInput.getMax(), attributes);
		return newSearchResult(count, detach(resultList),
				detach(searchInput));
	}

	@POST
	@Path("/countBy")
	@Consumes({ "application/json", "application/xml" })
	public Long countBy(CoreAbstBsnsItemSearchInput<E> searchInput) {
		SingularAttribute<E, ?>[] attributes = readSeachAttributes(searchInput);
		return getLookup().countBy(searchInput.getEntity(), attributes);
	}

	@POST
	@Path("/countByLike")
	@Consumes({ "application/json", "application/xml" })
	public Long countByLike(CoreAbstBsnsItemSearchInput<E> searchInput) {
		SingularAttribute<E, ?>[] attributes = readSeachAttributes(searchInput);
		return getLookup().countByLike(searchInput.getEntity(), attributes);
	}

	@SuppressWarnings("unchecked")
	protected SingularAttribute<E, ?>[] readSeachAttributes(
			CoreAbstBsnsItemSearchInput<E> searchInput) {
		List<String> fieldNames = searchInput.getFieldNames();
		List<SingularAttribute<E, ?>> result = new ArrayList<SingularAttribute<E, ?>>();
		Field[] fields = getEntityFields();
		for (String fieldName : fieldNames) {
			for (Field field : fields) {
				if (field.getName().equals(fieldName)) {
					try {
						result.add((SingularAttribute<E, ?>) field
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

	private E detach(E entity) {
		if (entity == null)
			return null;

		return entity;
	}

	private List<E> detach(List<E> list) {
		if (list == null)
			return list;
		List<E> result = new ArrayList<E>();
		for (E entity : list) {
			result.add(detach(entity));
		}
		return result;
	}

	private CoreAbstBsnsItemSearchInput<E> detach(
			CoreAbstBsnsItemSearchInput<E> searchInput) {
		searchInput.setEntity(detach(searchInput.getEntity()));
		return searchInput;
	}

	   @POST
	   @Path("/findByLike")
	   @Produces({ "application/json", "application/xml" })
	   @Consumes({ "application/json", "application/xml" })
	   public SR findByLike(CoreAbstBsnsItemSearchInput<E> searchInput)
	   {
	      SingularAttribute<E, ?>[] attributes = readSeachAttributes(searchInput);
	      Long count = getLookup().countByLike(searchInput.getEntity(), attributes);
	      List<E> resultList = getLookup().findByLike(searchInput.getEntity(),
	            searchInput.getStart(), searchInput.getMax(), attributes);
	       return newSearchResult(count, detach(resultList),
		              detach(searchInput));
	   }

	   @POST
	   @Path("/findCustom")
	   @Produces({ "application/json", "application/xml" })
	   @Consumes({ "application/json", "application/xml" })
	   public SR findCustom(CoreAbstBsnsItemSearchInput<E> searchInput)
	   {
	      Long count = getLookup().countCustom(searchInput);
	      Long total = getLookup().count();
	      List<E> resultList = getLookup().findCustom(searchInput);
	       return newSearchResult(count, total, detach(resultList),
		              detach(searchInput));
	   }

	   @POST
	   @Path("/findByBsnsObjNbrSorted")
	   @Produces({ "application/json", "application/xml" })
	   @Consumes({ "application/json", "application/xml" })
		public SR findByBsnsObjNbrSorted(CoreAbstBsnsItemSearchInput<E> searchInput){
		   Long count = getLookup().countByCntnrIdentif(searchInput.getEntity().getCntnrIdentif());
		   List<E> resultList = getLookup().findByCntnrIdentifOrdered(
				   searchInput.getEntity().getCntnrIdentif(), searchInput.getStart(), searchInput.getMax(), searchInput.getSortFieldNames());
	       return newSearchResult(count, detach(resultList),
		              detach(searchInput));
	   }

	   @POST
	   @Path("/findConflict")
	   @Produces({ "application/json", "application/xml" })
	   @Consumes({ "application/json", "application/xml" })
	   public SR findConflict(CoreAbstBsnsItemSearchInput<E> searchInput)
	   {
		   String cntnrIdentif = searchInput.getEntity().getCntnrIdentif();
		   Long count = getLookup().countByCntnrIdentifAndConflictDtIsNotNull(cntnrIdentif);
		   List<E> resultList = getLookup().findByCntnrIdentifAndConflictDtIsNotNullOrderBySalIndexAsc(cntnrIdentif, searchInput.getStart(), searchInput.getMax());
	       return newSearchResult(count, detach(resultList),
		              detach(searchInput));
	   }
	
	@POST
	@Path("/findByArtPic")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public SR findByArtPic(
			CoreAbstBsnsItemSearchInput<E> searchInput) {
		String artPic = searchInput.getEntity().getArtPic();
		Long count = getLookup().countByArtPicLike(artPic);
		   List<E> resultList = getLookup().findByArtPicLike(artPic, searchInput.getStart(), searchInput.getMax());
	       return newSearchResult(count, detach(resultList),
		              detach(searchInput));
	}
	
	@POST
	@Path("/report.pdf")
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml", "application/pdf", "application/octet-stream" })
	public Response generateReport(@Context HttpServletResponse response, CoreAbstBsnsItemSearchInput<E> searchInput) throws AdException {
		
		AbstEntiyProps entityProps = getEntityProps();
		if(entityProps==null) return Response.noContent().build();
		
		PdfReportProperties reportProperties = searchInput.getReportProperties();
		if(reportProperties==null || reportProperties.getReportFields().isEmpty()) return Response.noContent().build();
		reportProperties.setUsername(userPrincipal.getLoginName());
		
		OutputStream os = null;
		PdfReportTemplate reportTemplate = new PdfReportTemplate()
				.withEntityProps(entityProps)
				.withReportProperties(reportProperties);

		SingularAttribute<E, ?>[] attributes = readSeachAttributes(searchInput);
		E entity = searchInput.getEntity();
		Long count = getLookup().countBy(entity, attributes);
		int start = searchInput.getStart();
		int pageSize = CoreSearchInput.MAX_MAX;
		int max = searchInput.getMax();
		if(max<=0) max=count.intValue();
		
		while (start < max) {
			int firstResult = start;
			start += pageSize;
			List<E> resultList = getLookup().findBy(entity, firstResult, pageSize, attributes);
			try {
				reportTemplate.addItems(resultList);
			} catch (Exception e) {
				throw new AdException("Error printing", e);
			}
		}
		try {
			File file = reportTemplate.build();
			// the contentlength
			int sizeOf = (int) FileUtils.sizeOf(file);
			response.setContentLength(sizeOf);
			os = response.getOutputStream();
			FileUtils.copyFile(file, os);
			os.flush();
			os.close();
		} catch (Exception e) {
			throw new AdException("Error printing", e);
		}

		return Response
				.ok(os)
				.header("Content-Disposition",
						"attachment; filename=report.pdf").build();
	}
	
	
}