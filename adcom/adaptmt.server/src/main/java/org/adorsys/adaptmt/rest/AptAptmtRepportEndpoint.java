package org.adorsys.adaptmt.rest;

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

import org.adorsys.adaptmt.jpa.AptAptmtRepport;
import org.adorsys.adaptmt.jpa.AptAptmtRepportSearchInput;
import org.adorsys.adaptmt.jpa.AptAptmtRepportSearchResult;
import org.adorsys.adaptmt.jpa.AptAptmtRepport_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/aptaptmtRepports")
public class AptAptmtRepportEndpoint {
	@Inject
	private AptAptmtRepportEJB ejb;

	@POST
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public AptAptmtRepport create(AptAptmtRepport entity) {
		return detach(ejb.create(entity));
	}

	@DELETE
	@Path("/{id}")
	public Response deleteById(@PathParam("id") String id) {
		AptAptmtRepport deleted = ejb.deleteById(id);
		if (deleted == null)
			return Response.status(Status.NOT_FOUND).build();

		return Response.ok(detach(deleted)).build();
	}

	@PUT
	@Path("/{id}")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public AptAptmtRepport update(AptAptmtRepport entity) {
		return detach(ejb.update(entity));
	}

	@GET
	@Path("/{id}")
	@Produces({ "application/json", "application/xml" })
	public Response findById(@PathParam("id") String id) {
		AptAptmtRepport found = ejb.findById(id);
		if (found == null)
			return Response.status(Status.NOT_FOUND).build();
		return Response.ok(detach(found)).build();
	}

	@GET
	@Produces({ "application/json", "application/xml" })
	public AptAptmtRepportSearchResult listAll(@QueryParam("start") int start,
			@QueryParam("max") int max) {
		List<AptAptmtRepport> resultList = ejb.listAll(start, max);
		AptAptmtRepportSearchInput searchInput = new AptAptmtRepportSearchInput();
		searchInput.setStart(start);
		searchInput.setMax(max);
		return new AptAptmtRepportSearchResult((long) resultList.size(),
				detach(resultList), detach(searchInput));
	}

	@GET
	@Path("previousLogin/{id}")
	@Produces({ "application/json", "application/xml" })
	public Response previousLogin(@PathParam("id") String id) {
		List<AptAptmtRepport> found;
		try {
			found = ejb.findPreviousAptAptmtRepport(id);
		} catch (Exception e) {
			return Response.status(Status.NOT_FOUND).build();
		}
		if (found.isEmpty()) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(detach(found.iterator().next())).build();
	}

	@GET
	@Path("nextLogin/{id}")
	@Produces({ "application/json", "application/xml" })
	public Response nextLogin(@PathParam("id") String id) {
		List<AptAptmtRepport> found;
		try {
			found = ejb.findNextAptAptmtRepport(id);
		} catch (Exception e) {
			return Response.status(Status.NOT_FOUND).build();
		}
		if (found.isEmpty()) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(detach(found.iterator().next())).build();
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
	public AptAptmtRepportSearchResult findBy(AptAptmtRepportSearchInput searchInput) {
		SingularAttribute<AptAptmtRepport, ?>[] attributes = readSeachAttributes(searchInput);
		Long count = ejb.countBy(searchInput.getEntity(), attributes);
		List<AptAptmtRepport> resultList = ejb.findBy(searchInput.getEntity(),
				searchInput.getStart(), searchInput.getMax(), attributes);
		return new AptAptmtRepportSearchResult(count, detach(resultList),
				detach(searchInput));
	}

	@POST
	@Path("/countBy")
	@Consumes({ "application/json", "application/xml" })
	public Long countBy(AptAptmtRepportSearchInput searchInput) {
		SingularAttribute<AptAptmtRepport, ?>[] attributes = readSeachAttributes(searchInput);
		return ejb.countBy(searchInput.getEntity(), attributes);
	}

	@POST
	@Path("/findByLike")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public AptAptmtRepportSearchResult findByLike(AptAptmtRepportSearchInput searchInput) {
		SingularAttribute<AptAptmtRepport, ?>[] attributes = readSeachAttributes(searchInput);
		Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
		List<AptAptmtRepport> resultList = ejb.findByLike(searchInput.getEntity(),
				searchInput.getStart(), searchInput.getMax(), attributes);
		return new AptAptmtRepportSearchResult(countLike, detach(resultList),
				detach(searchInput));
	}

	@POST
	@Path("/countByLike")
	@Consumes({ "application/json", "application/xml" })
	public Long countByLike(AptAptmtRepportSearchInput searchInput) {
		SingularAttribute<AptAptmtRepport, ?>[] attributes = readSeachAttributes(searchInput);
		return ejb.countByLike(searchInput.getEntity(), attributes);
	}

	@SuppressWarnings("unchecked")
	private SingularAttribute<AptAptmtRepport, ?>[] readSeachAttributes(
			AptAptmtRepportSearchInput searchInput) {
		List<String> fieldNames = searchInput.getFieldNames();
		List<SingularAttribute<AptAptmtRepport, ?>> result = new ArrayList<SingularAttribute<AptAptmtRepport, ?>>();
		for (String fieldName : fieldNames) {
			Field[] fields = AptAptmtRepport_.class.getFields();
			for (Field field : fields) {
				if (field.getName().equals(fieldName)) {
					try {
						result.add((SingularAttribute<AptAptmtRepport, ?>) field
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

	private AptAptmtRepport detach(AptAptmtRepport entity) {
		if (entity == null)
			return null;

		return entity;
	}

	private List<AptAptmtRepport> detach(List<AptAptmtRepport> list) {
		if (list == null)
			return list;
		List<AptAptmtRepport> result = new ArrayList<AptAptmtRepport>();
		for (AptAptmtRepport entity : list) {
			result.add(detach(entity));
		}
		return result;
	}

	private AptAptmtRepportSearchInput detach(AptAptmtRepportSearchInput searchInput) {
		searchInput.setEntity(detach(searchInput.getEntity()));
		return searchInput;
	}

}
