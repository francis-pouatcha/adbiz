package org.adorsys.adcore.rest;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.metamodel.SingularAttribute;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.adorsys.adcore.jpa.CoreAbstIdentifObject;
import org.adorsys.adcore.jpa.CoreSearchInput;
import org.adorsys.adcore.jpa.CoreSearchResult;

public abstract class CoreAbstIdentifiedEndpoint<E extends CoreAbstIdentifObject> {

	protected abstract CoreAbstIdentifiedLookup<E> getLookup();
	protected abstract CoreAbstIdentifiedEJB<E> getEjb();
	protected abstract Field[] getEntityFields();

	@POST
	@Consumes({ "application/json"})
	@Produces({ "application/json"})
	public E create(E entity) {
		return detach(getEjb().create(entity));
	}

	@DELETE
	@Path("/{id}")
	public Response deleteById(@PathParam("id") String id) {
		E deleted = getEjb().deleteById(id);
		if (deleted == null)
			return Response.status(Status.NOT_FOUND).build();

		return Response.ok(detach(deleted)).build();
	}

	@PUT
	@Path("/{id}")
	@Produces({ "application/json"})
	@Consumes({ "application/json"})
	public E update(E entity) {
		return detach(getEjb().update(entity));
	}

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
	public CoreSearchResult<E> listAll(
			@QueryParam("start") int start, @QueryParam("max") int max) {
		// Limit the max of items queryable.
		max = CoreSearchInput.checkMax(max);
		List<E> resultList = getLookup().listAll(start, max);
		CoreSearchInput<E> searchInput = new CoreSearchInput<E>();
		searchInput.setStart(start);
		searchInput.setMax(max);
		return new CoreSearchResult<E>((long) resultList.size(),
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
	public CoreSearchResult<E> findBy(
			CoreSearchInput<E> searchInput) {
		SingularAttribute<E, ?>[] attributes = readSeachAttributes(searchInput);
		Long count = getLookup().countBy(searchInput.getEntity(), attributes);
		List<E> resultList = getLookup().findBy(
				searchInput.getEntity(), searchInput.getStart(),
				searchInput.getMax(), attributes);
		return new CoreSearchResult<E>(count, detach(resultList),
				detach(searchInput));
	}

	@POST
	@Path("/countBy")
	@Consumes({ "application/json"})
	public Long countBy(CoreSearchInput<E> searchInput) {
		SingularAttribute<E, ?>[] attributes = readSeachAttributes(searchInput);
		return getLookup().countBy(searchInput.getEntity(), attributes);
	}

	@POST
	@Path("/findByLike")
	@Produces({ "application/json"})
	@Consumes({ "application/json"})
	public CoreSearchResult<E> findByLike(
			CoreSearchInput<E> searchInput) {
		SingularAttribute<E, ?>[] attributes = readSeachAttributes(searchInput);
		Long countLike = getLookup().countByLike(searchInput.getEntity(), attributes);
		List<E> resultList = getLookup().findByLike(
				searchInput.getEntity(), searchInput.getStart(),
				searchInput.getMax(), attributes);
		return new CoreSearchResult<E>(countLike,
				detach(resultList), detach(searchInput));
	}

	@POST
	@Path("/countByLike")
	@Consumes({ "application/json"})
	public Long countByLike(CoreSearchInput<E> searchInput) {
		SingularAttribute<E, ?>[] attributes = readSeachAttributes(searchInput);
		return getLookup().countByLike(searchInput.getEntity(), attributes);
	}

	@SuppressWarnings("unchecked")
	private SingularAttribute<E, ?>[] readSeachAttributes(
			CoreSearchInput<E> searchInput) {
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

	private CoreSearchInput<E> detach(
			CoreSearchInput<E> searchInput) {
		searchInput.setEntity(detach(searchInput.getEntity()));
		return searchInput;
	}
}