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

import org.adorsys.adaptmt.jpa.AptAptmt;
import org.adorsys.adaptmt.jpa.AptAptmtSearchInput;
import org.adorsys.adaptmt.jpa.AptAptmtSearchResult;
import org.adorsys.adaptmt.jpa.AptAptmt_;
import org.adorsys.adbase.jpa.Login;
import org.adorsys.adbase.security.SecurityUtil;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/aptaptmts")
public class AptAptmtEndpoint {
	@Inject
	private AptAptmtEJB ejb;
	
	@POST
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public AptAptmt create(AptAptmt entity) {
		return detach(ejb.create(entity));
	}

	@DELETE
	@Path("/{id}")
	public Response deleteById(@PathParam("id") String id) {
		AptAptmt deleted = ejb.deleteById(id);
		if (deleted == null)
			return Response.status(Status.NOT_FOUND).build();

		return Response.ok(detach(deleted)).build();
	}
	
	@PUT
	@Path("close/{id}")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public AptAptmt close(AptAptmt entity) {
		return detach(ejb.close(entity));
	}

	@PUT
	@Path("/{id}")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public AptAptmt update(AptAptmt entity) {
		return detach(ejb.update(entity));
	}

	@GET
	@Path("/{id}")
	@Produces({ "application/json", "application/xml" })
	public Response findById(@PathParam("id") String id) {
		AptAptmt found = ejb.findById(id);
		if (found == null)
			return Response.status(Status.NOT_FOUND).build();
		return Response.ok(detach(found)).build();
	}

	/*
	 * @GET
	 * 
	 * @Path("bnsptnrs/{id}")
	 * 
	 * @Produces({ "application/json", "application/xml" }) public Apt
	 * findAptAptmtBnsPtnrs(@PathParam("id") String id) { List<AptAptmtBsPtnr>
	 * found = ejb.findAptmtBsPtnr(id); if (found == null) return
	 * Response.status(Status.NOT_FOUND).build(); return
	 * Response.ok(detachfound).build(); }
	 */

	@GET
	@Produces({ "application/json", "application/xml" })
	public AptAptmtSearchResult listAll(@QueryParam("start") int start,
			@QueryParam("max") int max) {
		List<AptAptmt> resultList = ejb.listAll(start, max);
		AptAptmtSearchInput searchInput = new AptAptmtSearchInput();
		searchInput.setStart(start);
		searchInput.setMax(max);
		return new AptAptmtSearchResult((long) resultList.size(),
				detach(resultList), detach(searchInput));
	}
	
	@GET
	@Path("/loginConnected")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public Login loginConnected() {
		Login resultList = ejb.loginConnected();
		return resultList;
	}

	@GET
	@Path("previousLogin/{id}")
	@Produces({ "application/json", "application/xml" })
	public Response previousLogin(@PathParam("id") String id) {
		List<AptAptmt> found;
		try {
			found = ejb.findPreviousAptAptmt(id);
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
		List<AptAptmt> found;
		try {
			found = ejb.findNextAptAptmt(id);
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
	public AptAptmtSearchResult findBy(AptAptmtSearchInput searchInput) {
		SingularAttribute<AptAptmt, ?>[] attributes = readSeachAttributes(searchInput);
		Long count = ejb.countBy(searchInput.getEntity(), attributes);
		List<AptAptmt> resultList = ejb.findBy(searchInput.getEntity(),
				searchInput.getStart(), searchInput.getMax(), attributes);
		return new AptAptmtSearchResult(count, detach(resultList),
				detach(searchInput));
	}

	@POST
	@Path("/countBy")
	@Consumes({ "application/json", "application/xml" })
	public Long countBy(AptAptmtSearchInput searchInput) {
		SingularAttribute<AptAptmt, ?>[] attributes = readSeachAttributes(searchInput);
		return ejb.countBy(searchInput.getEntity(), attributes);
	}

	@POST
	@Path("/findByLike")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public AptAptmtSearchResult findByLike(AptAptmtSearchInput searchInput) {
		SingularAttribute<AptAptmt, ?>[] attributes = readSeachAttributes(searchInput);
		Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
		List<AptAptmt> resultList = ejb.findByLike(searchInput.getEntity(),
				searchInput.getStart(), searchInput.getMax(), attributes);
		return new AptAptmtSearchResult(countLike, detach(resultList),
				detach(searchInput));
	}

	@POST
	@Path("/findCustom")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public AptAptmtSearchResult findCustom(AptAptmtSearchInput searchInput) {
		if (searchInput.noSpecialParams())
			return findByLike(searchInput);

		Long count = ejb.countCustom(searchInput);
		List<AptAptmt> results = ejb.findCustom(searchInput);
		return new AptAptmtSearchResult(count, detach(results),
				detach(searchInput));
	}

	@POST
	@Path("/countByLike")
	@Consumes({ "application/json", "application/xml" })
	public Long countByLike(AptAptmtSearchInput searchInput) {
		SingularAttribute<AptAptmt, ?>[] attributes = readSeachAttributes(searchInput);
		return ejb.countByLike(searchInput.getEntity(), attributes);
	}

	@SuppressWarnings("unchecked")
	private SingularAttribute<AptAptmt, ?>[] readSeachAttributes(
			AptAptmtSearchInput searchInput) {
		List<String> fieldNames = searchInput.getFieldNames();
		List<SingularAttribute<AptAptmt, ?>> result = new ArrayList<SingularAttribute<AptAptmt, ?>>();
		for (String fieldName : fieldNames) {
			Field[] fields = AptAptmt_.class.getFields();
			for (Field field : fields) {
				if (field.getName().equals(fieldName)) {
					try {
						result.add((SingularAttribute<AptAptmt, ?>) field
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

	private AptAptmt detach(AptAptmt entity) {
		if (entity == null)
			return null;

		return entity;
	}

	private List<AptAptmt> detach(List<AptAptmt> list) {
		if (list == null)
			return list;
		List<AptAptmt> result = new ArrayList<AptAptmt>();
		for (AptAptmt entity : list) {
			result.add(detach(entity));
		}
		return result;
	}

	private AptAptmtSearchInput detach(AptAptmtSearchInput searchInput) {
		searchInput.setEntity(detach(searchInput.getEntity()));
		return searchInput;
	}

}
