package org.adorsys.adcore.rest;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.metamodel.SingularAttribute;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.adorsys.adcore.jpa.CoreAbstBsnsObject;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchResult;


public abstract class CoreAbstBsnsObjectEndpoint<E extends CoreAbstBsnsObject, SI extends CoreAbstBsnsObjectSearchInput<E>, SR extends CoreAbstBsnsObjectSearchResult<E>> {

	protected abstract CoreAbstBsnsObjectLookup<E> getLookup();
	protected abstract Field[] getEntityFields();
	protected abstract SR newSearchResult(Long size, List<E> resultList,CoreAbstBsnsObjectSearchInput<E> searchInput);
	protected abstract SI newSearchInput();

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
	public SR listAll(@QueryParam("start") int start,
			@QueryParam("max") int max) {
		List<E> resultList = getLookup().listAll(start, max);
		CoreAbstBsnsObjectSearchInput<E> searchInput = newSearchInput();
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
	public SR findBy(CoreAbstBsnsObjectSearchInput<E> searchInput) {
		SingularAttribute<E, ?>[] attributes = readSeachAttributes(searchInput);
		Long count = getLookup().countBy(searchInput.getEntity(), attributes);
		List<E> resultList = getLookup().findBy(searchInput.getEntity(),
				searchInput.getStart(), searchInput.getMax(), attributes);
		return newSearchResult(count, detach(resultList),
				detach(searchInput));
	}

	@POST
	@Path("/countBy")
	@Consumes({ "application/json", "application/xml" })
	public Long countBy(CoreAbstBsnsObjectSearchInput<E> searchInput) {
		SingularAttribute<E, ?>[] attributes = readSeachAttributes(searchInput);
		return getLookup().countBy(searchInput.getEntity(), attributes);
	}

	@POST
	@Path("/findByLike")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public SR findByLike(
			CoreAbstBsnsObjectSearchInput<E> searchInput) {
		SingularAttribute<E, ?>[] attributes = readSeachAttributes(searchInput);
		Long countLike = getLookup().countByLike(searchInput.getEntity(), attributes);
		List<E> resultList = getLookup().findByLike(searchInput.getEntity(),
				searchInput.getStart(), searchInput.getMax(), attributes);
		return newSearchResult(countLike, detach(resultList),
				detach(searchInput));
	}

	@POST
	@Path("/findCustom")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public SR findCustom(
			CoreAbstBsnsObjectSearchInput<E> searchInput) {
		Long countLike = getLookup().countCustom(searchInput);
		List<E> resultList = getLookup().findCustom(searchInput);
		return newSearchResult(countLike, detach(resultList),
				detach(searchInput));
	}

	@POST
	@Path("/countByLike")
	@Consumes({ "application/json", "application/xml" })
	public Long countByLike(CoreAbstBsnsObjectSearchInput<E> searchInput) {
		SingularAttribute<E, ?>[] attributes = readSeachAttributes(searchInput);
		return getLookup().countByLike(searchInput.getEntity(), attributes);
	}

	@SuppressWarnings("unchecked")
	private SingularAttribute<E, ?>[] readSeachAttributes(
			CoreAbstBsnsObjectSearchInput<E> searchInput) {
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

	private CoreAbstBsnsObjectSearchInput<E> detach(CoreAbstBsnsObjectSearchInput<E> searchInput) {
		searchInput.setEntity(detach(searchInput.getEntity()));
		return searchInput;
	}
}