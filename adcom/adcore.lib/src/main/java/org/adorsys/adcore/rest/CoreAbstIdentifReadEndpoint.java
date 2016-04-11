package org.adorsys.adcore.rest;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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

import org.adorsys.adcore.exceptions.AdException;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.vo.StringListHolder;

public abstract class CoreAbstIdentifReadEndpoint<E extends CoreAbstIdentifObject> {

	protected abstract CoreAbstIdentifLookup<E> getLookup();
	protected abstract Field[] getEntityFields();
	protected abstract CoreAbstIdentifObjectSearchInput<E> newSearchInput();
	protected abstract CoreAbstIdentifObjectSearchResult<E> newSearchResult(Long count, Long total, List<E> resultList, CoreAbstIdentifObjectSearchInput<E> searchInput);

	@GET
	@Path("/{id}")
	@Produces({ "application/json"})
	public Response findById(@PathParam("id") String id) {
		E found = getLookup().findById(id);
		if (found == null)
			return Response.status(Status.NOT_FOUND).build();
		return Response.ok(detach(found)).build();
	}

	@GET
	@Produces({ "application/json"})
	public CoreAbstIdentifObjectSearchResult<E> listAll(
			@QueryParam("start") int start, @QueryParam("max") int max) {
		// Limit the max of items queryable.
		max = CoreAbstIdentifObjectSearchInput.checkMax(max);
		Long total = getLookup().count();
		List<E> resultList = getLookup().listAll(start, max);
		CoreAbstIdentifObjectSearchInput<E> searchInput = newSearchInput();
		searchInput.setStart(start);
		searchInput.setMax(max);
		return newSearchResult(new Long(resultList.size()), total,
				detach(resultList), detach(searchInput));
	}

	@GET
	@Path("/findByCntnrIdentif")
	@Produces({ "application/json"})
	public CoreAbstIdentifObjectSearchResult<E> findByCntnrIdentif(@QueryParam("cntnrIdentif") String cntnrIdentif,
			@QueryParam("start") int start, @QueryParam("max") int max) {
		Long count = getLookup().countByCntnrIdentif(cntnrIdentif);
		Long total = getLookup().count();
		List<E> resultList = getLookup().findByCntnrIdentif(cntnrIdentif, start, max);
		CoreAbstIdentifObjectSearchInput<E> searchInput = newSearchInput();
		searchInput.setStart(start);
		searchInput.setMax(max);
		return newSearchResult(count,total, detach(resultList), detach(searchInput));
	}
	
	@POST
	@Path("/findByCntnrIdentifIn")
	@Produces({ "application/json"})
	public CoreAbstIdentifObjectSearchResult<E> findByCntnrIdentifIn(StringListHolder holder) {
		List<E> resultList = getLookup().findByCntnrIdentifIn(holder);
		CoreAbstIdentifObjectSearchInput<E> searchInput = newSearchInput();
		searchInput.setStart(holder.getStart());
		searchInput.setMax(holder.getMax());
		Long size = new Long(resultList.size());
		return newSearchResult(size, size,
				detach(resultList), detach(searchInput));
	}
	
	
	@POST
	@Path("/findByIdentifIn")
	@Produces({ "application/json"})
	public CoreAbstIdentifObjectSearchResult<E> findByIdentifIn(StringListHolder cntnrIdentifs) {
		List<E> resultList = getLookup().findByIdentifIn(cntnrIdentifs.getList());
		CoreAbstIdentifObjectSearchInput<E> searchInput = newSearchInput();
		searchInput.setStart(cntnrIdentifs.getStart());
		searchInput.setMax(cntnrIdentifs.getMax());
		Long size = new Long(resultList.size());
		return newSearchResult(size, size,
				detach(resultList), detach(searchInput));
	}
	
	@GET
	@Path("/count")
	public Long count() {
		return getLookup().count();
	}

	@POST
	@Path("/findBy")
	@Produces({ "application/json"})
	@Consumes({ "application/json"})
	public CoreAbstIdentifObjectSearchResult<E> findBy(
			CoreAbstIdentifObjectSearchInput<E> searchInput) {
		SingularAttribute<E, ?>[] attributes = readSeachAttributes(searchInput);
		Long count = getLookup().countBy(searchInput.getEntity(), attributes);
		Long total = getLookup().count();
		List<E> resultList = getLookup().findBy(
				searchInput.getEntity(), searchInput.getStart(),
				searchInput.getMax(), attributes);
		return newSearchResult(count,total, detach(resultList),
				detach(searchInput));
	}

	@POST
	@Path("/countBy")
	@Consumes({ "application/json"})
	public Long countBy(CoreAbstIdentifObjectSearchInput<E> searchInput) {
		SingularAttribute<E, ?>[] attributes = readSeachAttributes(searchInput);
		return getLookup().countBy(searchInput.getEntity(), attributes);
	}

	@POST
	@Path("/findByLike")
	@Produces({ "application/json"})
	@Consumes({ "application/json"})
	public CoreAbstIdentifObjectSearchResult<E> findByLike(
			CoreAbstIdentifObjectSearchInput<E> searchInput) {
		SingularAttribute<E, ?>[] attributes = readSeachAttributes(searchInput);
		Long countLike = getLookup().countByLike(searchInput.getEntity(), attributes);
		Long total = getLookup().count();
		List<E> resultList = getLookup().findByLike(
				searchInput.getEntity(), searchInput.getStart(),
				searchInput.getMax(), attributes);
		return newSearchResult(countLike, total,
				detach(resultList), detach(searchInput));
	}

	@POST
	@Path("/findCustom")
	@Produces({ "application/json" })
	@Consumes({ "application/json" })
	public CoreAbstIdentifObjectSearchResult<E> findCustom(CoreAbstIdentifObjectSearchInput<E> searchInput) {
		Long countLike = getLookup().countCustom(searchInput);
		Long total = getLookup().count();
		List<E> resultList = getLookup().findCustom(searchInput);
		return newSearchResult(countLike, total, detach(resultList),
				detach(searchInput));
	}
	
	@POST
	@Path("/countByLike")
	@Consumes({ "application/json"})
	public Long countByLike(CoreAbstIdentifObjectSearchInput<E> searchInput) {
		SingularAttribute<E, ?>[] attributes = readSeachAttributes(searchInput);
		return getLookup().countByLike(searchInput.getEntity(), attributes);
	}

	@SuppressWarnings("unchecked")
	protected SingularAttribute<E, ?>[] readSeachAttributes(
			CoreAbstIdentifObjectSearchInput<E> searchInput) {
		List<String> fieldNames = searchInput.getFieldNames();
		List<SingularAttribute<E, ?>> result = new ArrayList<SingularAttribute<E, ?>>();
		for (String fieldName : fieldNames) {
			Field[] fields = getEntityFields();
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

	protected E detach(E entity) {
		if (entity == null)
			return null;

		return entity;
	}

	protected List<E> detach(List<E> list) {
		if (list == null)
			return list;
		List<E> result = new ArrayList<E>();
		for (E entity : list) {
			result.add(detach(entity));
		}
		return result;
	}

	protected CoreAbstIdentifObjectSearchInput<E> detach(
			CoreAbstIdentifObjectSearchInput<E> searchInput) {
		searchInput.setEntity(detach(searchInput.getEntity()));
		return searchInput;
	}
	
	@POST
	@Path("/download")
	@Produces({"application/vnd.ms-excel" })
	public Response download(@PathParam("xlsType") String xlsType,
			@Context HttpServletResponse response, CoreAbstIdentifObjectSearchInput<E> searchInput) throws AdException {
		
		return Response.noContent().build();
//
//		String filename = xlsType+".xls";
//		OutputStream os = null;
//		try {
//			File file = new File(DOWNLOAD_DIR, filename);
//			int sizeOf = (int) FileUtils.sizeOf(file);
//			response.setContentLength(sizeOf);
//			os = response.getOutputStream();
//			FileUtils.copyFile(file, os);
//			os.flush();
//			os.close();
//		} catch (Exception e) {
//			throw new AdException("Error printing", e);
//		}
//
//		return Response
//				.ok(os)
//				.header("Content-Disposition",
//						"attachment; filename=sample.xls").build();
	}	
}