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

import org.adorsys.adaptmt.jpa.AptAptmtBsPtnr;
import org.adorsys.adaptmt.jpa.AptAptmtBsPtnrSearchInput;
import org.adorsys.adaptmt.jpa.AptAptmtBsPtnrSearchResult;
import org.adorsys.adaptmt.jpa.AptAptmtBsPtnr_;


/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/aptaptmtbsptnrs")
public class AptAptmtBsPtnrEndpoint
{
	@Inject
	private AptAptmtBsPtnrEJB ejb;

	@POST
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public AptAptmtBsPtnr create(AptAptmtBsPtnr entity) {
		return detach(ejb.create(entity));
	}

	@DELETE
	@Path("/{id}")
	public Response deleteById(@PathParam("id") String id) {
		AptAptmtBsPtnr deleted = ejb.deleteById(id);
		if (deleted == null)
			return Response.status(Status.NOT_FOUND).build();

		return Response.ok(detach(deleted)).build();
	}

	@PUT
	@Path("/{id}")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public AptAptmtBsPtnr update(AptAptmtBsPtnr entity) {
		return detach(ejb.update(entity));
	}

	@GET
	@Path("/{id}")
	@Produces({ "application/json", "application/xml" })
	public Response findById(@PathParam("id") String id) {
		AptAptmtBsPtnr found = ejb.findById(id);
		if (found == null)
			return Response.status(Status.NOT_FOUND).build();
		return Response.ok(detach(found)).build();
	}
	
	@GET
	@Path("aptmtIdentify/{aptmtIdentify}")
	@Produces({ "application/json", "application/xml" })
	public Response findByAptmtIdentify(@PathParam("aptmtIdentify") String aptmtIdentify) {
		List<AptAptmtBsPtnr> found = ejb.findAptmtBsPtnr(aptmtIdentify);
		if (found == null)
			return Response.status(Status.NOT_FOUND).build();
		return Response.ok(detach(found)).build();
	}

	@GET
	@Produces({ "application/json", "application/xml" })
	public AptAptmtBsPtnrSearchResult listAll(@QueryParam("start") int start,
			@QueryParam("max") int max) {
		List<AptAptmtBsPtnr> resultList = ejb.listAll(start, max);
		AptAptmtBsPtnrSearchInput searchInput = new AptAptmtBsPtnrSearchInput();
		searchInput.setStart(start);
		searchInput.setMax(max);
		return new AptAptmtBsPtnrSearchResult((long) resultList.size(),
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
	public AptAptmtBsPtnrSearchResult findBy(AptAptmtBsPtnrSearchInput searchInput) {
		SingularAttribute<AptAptmtBsPtnr, ?>[] attributes = readSeachAttributes(searchInput);
		Long count = ejb.countBy(searchInput.getEntity(), attributes);
		List<AptAptmtBsPtnr> resultList = ejb.findBy(searchInput.getEntity(),
				searchInput.getStart(), searchInput.getMax(), attributes);
		return new AptAptmtBsPtnrSearchResult(count, detach(resultList),
				detach(searchInput));
	}

	@POST
	@Path("/countBy")
	@Consumes({ "application/json", "application/xml" })
	public Long countBy(AptAptmtBsPtnrSearchInput searchInput) {
		SingularAttribute<AptAptmtBsPtnr, ?>[] attributes = readSeachAttributes(searchInput);
		return ejb.countBy(searchInput.getEntity(), attributes);
	}

	@POST
	@Path("/findByLike")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public AptAptmtBsPtnrSearchResult findByLike(AptAptmtBsPtnrSearchInput searchInput) {
		SingularAttribute<AptAptmtBsPtnr, ?>[] attributes = readSeachAttributes(searchInput);
		Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
		List<AptAptmtBsPtnr> resultList = ejb.findByLike(searchInput.getEntity(),
				searchInput.getStart(), searchInput.getMax(), attributes);
		return new AptAptmtBsPtnrSearchResult(countLike, detach(resultList),
				detach(searchInput));
	}

	@POST
	@Path("/countByLike")
	@Consumes({ "application/json", "application/xml" })
	public Long countByLike(AptAptmtBsPtnrSearchInput searchInput) {
		SingularAttribute<AptAptmtBsPtnr, ?>[] attributes = readSeachAttributes(searchInput);
		return ejb.countByLike(searchInput.getEntity(), attributes);
	}

	@SuppressWarnings("unchecked")
	private SingularAttribute<AptAptmtBsPtnr, ?>[] readSeachAttributes(
			AptAptmtBsPtnrSearchInput searchInput) {
		List<String> fieldNames = searchInput.getFieldNames();
		List<SingularAttribute<AptAptmtBsPtnr, ?>> result = new ArrayList<SingularAttribute<AptAptmtBsPtnr, ?>>();
		for (String fieldName : fieldNames) {
			Field[] fields = AptAptmtBsPtnr_.class.getFields();
			for (Field field : fields) {
				if (field.getName().equals(fieldName)) {
					try {
						result.add((SingularAttribute<AptAptmtBsPtnr, ?>) field
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

	private AptAptmtBsPtnr detach(AptAptmtBsPtnr entity) {
		if (entity == null)
			return null;

		return entity;
	}

	private List<AptAptmtBsPtnr> detach(List<AptAptmtBsPtnr> list) {
		if (list == null)
			return list;
		List<AptAptmtBsPtnr> result = new ArrayList<AptAptmtBsPtnr>();
		for (AptAptmtBsPtnr entity : list) {
			result.add(detach(entity));
		}
		return result;
	}

	private AptAptmtBsPtnrSearchInput detach(AptAptmtBsPtnrSearchInput searchInput) {
		searchInput.setEntity(detach(searchInput.getEntity()));
		return searchInput;
	}

}
