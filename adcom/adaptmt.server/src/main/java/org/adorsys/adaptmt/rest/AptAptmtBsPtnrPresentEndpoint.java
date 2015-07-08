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

import org.adorsys.adaptmt.jpa.AptAptmtBsPtnrPresent;
import org.adorsys.adaptmt.jpa.AptAptmtBsPtnrPresentSearchInput;
import org.adorsys.adaptmt.jpa.AptAptmtBsPtnrPresentSearchResult;
import org.adorsys.adaptmt.jpa.AptAptmtBsPtnrPresent_;


/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/aptaptmtbsptnrpresents")
public class AptAptmtBsPtnrPresentEndpoint
{
	@Inject
	private AptAptmtBsPtnrPresentEJB ejb;

	@POST
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public AptAptmtBsPtnrPresent create(AptAptmtBsPtnrPresent entity) {
		return detach(ejb.create(entity));
	}

	@DELETE
	@Path("/{id}")
	public Response deleteById(@PathParam("id") String id) {
		AptAptmtBsPtnrPresent deleted = ejb.deleteById(id);
		if (deleted == null)
			return Response.status(Status.NOT_FOUND).build();

		return Response.ok(detach(deleted)).build();
	}

	@PUT
	@Path("/{id}")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public AptAptmtBsPtnrPresent update(AptAptmtBsPtnrPresent entity) {
		return detach(ejb.update(entity));
	}

	@GET
	@Path("/{id}")
	@Produces({ "application/json", "application/xml" })
	public Response findById(@PathParam("id") String id) {
		AptAptmtBsPtnrPresent found = ejb.findById(id);
		if (found == null)
			return Response.status(Status.NOT_FOUND).build();
		return Response.ok(detach(found)).build();
	}
	
	@GET
	@Path("aptmtIdentify/{aptmtIdentify}")
	@Produces({ "application/json", "application/xml" })
	public Response findByAptmtIdentify(@PathParam("aptmtIdentify") String aptmtIdentify) {
		List<AptAptmtBsPtnrPresent> found = ejb.findAptmtBsPtnrPresent(aptmtIdentify);
		if (found == null)
			return Response.status(Status.NOT_FOUND).build();
		return Response.ok(detach(found)).build();
	}

	@GET
	@Produces({ "application/json", "application/xml" })
	public AptAptmtBsPtnrPresentSearchResult listAll(@QueryParam("start") int start,
			@QueryParam("max") int max) {
		List<AptAptmtBsPtnrPresent> resultList = ejb.listAll(start, max);
		AptAptmtBsPtnrPresentSearchInput searchInput = new AptAptmtBsPtnrPresentSearchInput();
		searchInput.setStart(start);
		searchInput.setMax(max);
		return new AptAptmtBsPtnrPresentSearchResult((long) resultList.size(),detach(resultList), detach(searchInput));
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
	public AptAptmtBsPtnrPresentSearchResult findBy(AptAptmtBsPtnrPresentSearchInput searchInput) {
		SingularAttribute<AptAptmtBsPtnrPresent, ?>[] attributes = readSeachAttributes(searchInput);
		Long count = ejb.countBy(searchInput.getEntity(), attributes);
		List<AptAptmtBsPtnrPresent> resultList = ejb.findBy(searchInput.getEntity(),
				searchInput.getStart(), searchInput.getMax(), attributes);
		return new AptAptmtBsPtnrPresentSearchResult(count, detach(resultList),
				detach(searchInput));
	}

	@POST
	@Path("/countBy")
	@Consumes({ "application/json", "application/xml" })
	public Long countBy(AptAptmtBsPtnrPresentSearchInput searchInput) {
		SingularAttribute<AptAptmtBsPtnrPresent, ?>[] attributes = readSeachAttributes(searchInput);
		return ejb.countBy(searchInput.getEntity(), attributes);
	}

	@POST
	@Path("/findByLike")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public AptAptmtBsPtnrPresentSearchResult findByLike(AptAptmtBsPtnrPresentSearchInput searchInput) {
		SingularAttribute<AptAptmtBsPtnrPresent, ?>[] attributes = readSeachAttributes(searchInput);
		Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
		List<AptAptmtBsPtnrPresent> resultList = ejb.findByLike(searchInput.getEntity(),
				searchInput.getStart(), searchInput.getMax(), attributes);
		return new AptAptmtBsPtnrPresentSearchResult(countLike, detach(resultList),
				detach(searchInput));
	}

	@POST
	@Path("/countByLike")
	@Consumes({ "application/json", "application/xml" })
	public Long countByLike(AptAptmtBsPtnrPresentSearchInput searchInput) {
		SingularAttribute<AptAptmtBsPtnrPresent, ?>[] attributes = readSeachAttributes(searchInput);
		return ejb.countByLike(searchInput.getEntity(), attributes);
	}

	@SuppressWarnings("unchecked")
	private SingularAttribute<AptAptmtBsPtnrPresent, ?>[] readSeachAttributes(
			AptAptmtBsPtnrPresentSearchInput searchInput) {
		List<String> fieldNames = searchInput.getFieldNames();
		List<SingularAttribute<AptAptmtBsPtnrPresent, ?>> result = new ArrayList<SingularAttribute<AptAptmtBsPtnrPresent, ?>>();
		for (String fieldName : fieldNames) {
			Field[] fields = AptAptmtBsPtnrPresent_.class.getFields();
			for (Field field : fields) {
				if (field.getName().equals(fieldName)) {
					try {
						result.add((SingularAttribute<AptAptmtBsPtnrPresent, ?>) field
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

	private AptAptmtBsPtnrPresent detach(AptAptmtBsPtnrPresent entity) {
		if (entity == null)
			return null;

		return entity;
	}

	private List<AptAptmtBsPtnrPresent> detach(List<AptAptmtBsPtnrPresent> list) {
		if (list == null)
			return list;
		List<AptAptmtBsPtnrPresent> result = new ArrayList<AptAptmtBsPtnrPresent>();
		for (AptAptmtBsPtnrPresent entity : list) {
			result.add(detach(entity));
		}
		return result;
	}

	private AptAptmtBsPtnrPresentSearchInput detach(AptAptmtBsPtnrPresentSearchInput searchInput) {
		searchInput.setEntity(detach(searchInput.getEntity()));
		return searchInput;
	}

}




















