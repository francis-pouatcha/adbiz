package org.adorsys.adacc.rest;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
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

import org.adorsys.adacc.jpa.AccAccount;
import org.adorsys.adacc.jpa.AccAccountSearchInput;
import org.adorsys.adacc.jpa.AccAccountSearchResult;
import org.adorsys.adacc.jpa.AccAccount_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/accaccounts")
public class AccAccountEndpoint {

	@Inject
	private AccAccountEJB ejb;

	@POST
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public AccAccount create(AccAccount entity) {
		return detach(ejb.create(entity));
	}

	@DELETE
	@Path("/{id}")
	public Response deleteById(@PathParam("id") String id) {
		AccAccount deleted = ejb.deleteById(id);
		if (deleted == null)
			return Response.status(Status.NOT_FOUND).build();

		return Response.ok(detach(deleted)).build();
	}

	@PUT
	@Path("/{id}")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public AccAccount update(AccAccount entity) {
		return detach(ejb.update(entity));
	}

	@GET
	@Path("/{id}")
	@Produces({ "application/json", "application/xml" })
	public Response findById(@PathParam("id") String id) {
		AccAccount found = ejb.findById(id);
		if (found == null)
			return Response.status(Status.NOT_FOUND).build();
		return Response.ok(detach(found)).build();
	}

	@GET
	@Produces({ "application/json", "application/xml" })
	public AccAccountSearchResult listAll(@QueryParam("start") int start,
			@QueryParam("max") int max) {
		List<AccAccount> resultList = ejb.listAll(start, max);
		AccAccountSearchInput searchInput = new AccAccountSearchInput();
		searchInput.setStart(start);
		searchInput.setMax(max);
		return new AccAccountSearchResult((long) resultList.size(),
				detach(resultList), detach(searchInput));
	}

	@GET
	@Path("/count")
	public Long count() {
		return ejb.count();
	}

	@POST
	@Path("/findBy")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public AccAccountSearchResult findBy(AccAccountSearchInput searchInput) {
		SingularAttribute<AccAccount, ?>[] attributes = readSeachAttributes(searchInput);
		Long count = ejb.countBy(searchInput.getEntity(), attributes);
		List<AccAccount> resultList = ejb.findBy(searchInput.getEntity(),
				searchInput.getStart(), searchInput.getMax(), attributes);
		return new AccAccountSearchResult(count, detach(resultList),
				detach(searchInput));
	}

	@POST
	@Path("/countBy")
	@Consumes({ "application/json", "application/xml" })
	public Long countBy(AccAccountSearchInput searchInput) {
		SingularAttribute<AccAccount, ?>[] attributes = readSeachAttributes(searchInput);
		return ejb.countBy(searchInput.getEntity(), attributes);
	}

	@POST
	@Path("/findByLike")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public AccAccountSearchResult findByLike(AccAccountSearchInput searchInput) {
		SingularAttribute<AccAccount, ?>[] attributes = readSeachAttributes(searchInput);
		Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
		List<AccAccount> resultList = ejb.findByLike(searchInput.getEntity(),
				searchInput.getStart(), searchInput.getMax(), attributes);
		return new AccAccountSearchResult(countLike, detach(resultList),
				detach(searchInput));
	}

	@POST
	@Path("/countByLike")
	@Consumes({ "application/json", "application/xml" })
	public Long countByLike(AccAccountSearchInput searchInput) {
		SingularAttribute<AccAccount, ?>[] attributes = readSeachAttributes(searchInput);
		return ejb.countByLike(searchInput.getEntity(), attributes);
	}

	@SuppressWarnings("unchecked")
	private SingularAttribute<AccAccount, ?>[] readSeachAttributes(
			AccAccountSearchInput searchInput) {
		List<String> fieldNames = searchInput.getFieldNames();
		List<SingularAttribute<AccAccount, ?>> result = new ArrayList<SingularAttribute<AccAccount, ?>>();
		for (String fieldName : fieldNames) {
			Field[] fields = AccAccount_.class.getFields();
			for (Field field : fields) {
				if (field.getName().equals(fieldName)) {
					try {
						result.add((SingularAttribute<AccAccount, ?>) field
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

	private AccAccount detach(AccAccount entity) {
		if (entity == null)
			return null;

		return entity;
	}

	private List<AccAccount> detach(List<AccAccount> list) {
		if (list == null)
			return list;
		List<AccAccount> result = new ArrayList<AccAccount>();
		for (AccAccount entity : list) {
			result.add(detach(entity));
		}
		return result;
	}

	private AccAccountSearchInput detach(AccAccountSearchInput searchInput) {
		searchInput.setEntity(detach(searchInput.getEntity()));
		return searchInput;
	}
}