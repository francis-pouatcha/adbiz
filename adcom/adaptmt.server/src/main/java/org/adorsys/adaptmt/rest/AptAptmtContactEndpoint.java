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

import org.adorsys.adaptmt.jpa.AptAptmtContact;
import org.adorsys.adaptmt.jpa.AptAptmtContactSearchInput;
import org.adorsys.adaptmt.jpa.AptAptmtContactSearchResult;
import org.adorsys.adaptmt.jpa.AptAptmtContact_;



/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/aptaptmtContacts")
public class AptAptmtContactEndpoint
{
	@Inject
	private AptAptmtContactEJB ejb;

	@POST
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public AptAptmtContact create(AptAptmtContact entity) {
		return detach(ejb.create(entity));
	}

	@DELETE
	@Path("/{id}")
	public Response deleteById(@PathParam("id") String id) {
		AptAptmtContact deleted = ejb.deleteById(id);
		if (deleted == null)
			return Response.status(Status.NOT_FOUND).build();

		return Response.ok(detach(deleted)).build();
	}

	@PUT
	@Path("/{id}")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public AptAptmtContact update(AptAptmtContact entity) {
		return detach(ejb.update(entity));
	}

	@GET
	@Path("/{id}")
	@Produces({ "application/json", "application/xml" })
	public Response findById(@PathParam("id") String id) {
		AptAptmtContact found = ejb.findById(id);
		if (found == null)
			return Response.status(Status.NOT_FOUND).build();
		return Response.ok(detach(found)).build();
	}

	@GET
	@Produces({ "application/json", "application/xml" })
	public AptAptmtContactSearchResult listAll(@QueryParam("start") int start,
			@QueryParam("max") int max) {
		List<AptAptmtContact> resultList = ejb.listAll(start, max);
		AptAptmtContactSearchInput searchInput = new AptAptmtContactSearchInput();
		searchInput.setStart(start);
		searchInput.setMax(max);
		return new AptAptmtContactSearchResult((long) resultList.size(),
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
	public AptAptmtContactSearchResult findBy(AptAptmtContactSearchInput searchInput) {
		SingularAttribute<AptAptmtContact, ?>[] attributes = readSeachAttributes(searchInput);
		Long count = ejb.countBy(searchInput.getEntity(), attributes);
		List<AptAptmtContact> resultList = ejb.findBy(searchInput.getEntity(),
				searchInput.getStart(), searchInput.getMax(), attributes);
		return new AptAptmtContactSearchResult(count, detach(resultList),
				detach(searchInput));
	}

	@POST
	@Path("/countBy")
	@Consumes({ "application/json", "application/xml" })
	public Long countBy(AptAptmtContactSearchInput searchInput) {
		SingularAttribute<AptAptmtContact, ?>[] attributes = readSeachAttributes(searchInput);
		return ejb.countBy(searchInput.getEntity(), attributes);
	}

	@POST
	@Path("/findByLike")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public AptAptmtContactSearchResult findByLike(AptAptmtContactSearchInput searchInput) {
		SingularAttribute<AptAptmtContact, ?>[] attributes = readSeachAttributes(searchInput);
		Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
		List<AptAptmtContact> resultList = ejb.findByLike(searchInput.getEntity(),
				searchInput.getStart(), searchInput.getMax(), attributes);
		return new AptAptmtContactSearchResult(countLike, detach(resultList),
				detach(searchInput));
	}

	@POST
	@Path("/countByLike")
	@Consumes({ "application/json", "application/xml" })
	public Long countByLike(AptAptmtContactSearchInput searchInput) {
		SingularAttribute<AptAptmtContact, ?>[] attributes = readSeachAttributes(searchInput);
		return ejb.countByLike(searchInput.getEntity(), attributes);
	}

	@SuppressWarnings("unchecked")
	private SingularAttribute<AptAptmtContact, ?>[] readSeachAttributes(
			AptAptmtContactSearchInput searchInput) {
		List<String> fieldNames = searchInput.getFieldNames();
		List<SingularAttribute<AptAptmtContact, ?>> result = new ArrayList<SingularAttribute<AptAptmtContact, ?>>();
		for (String fieldName : fieldNames) {
			Field[] fields = AptAptmtContact_.class.getFields();
			for (Field field : fields) {
				if (field.getName().equals(fieldName)) {
					try {
						result.add((SingularAttribute<AptAptmtContact, ?>) field
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

	private AptAptmtContact detach(AptAptmtContact entity) {
		if (entity == null)
			return null;

		return entity;
	}

	private List<AptAptmtContact> detach(List<AptAptmtContact> list) {
		if (list == null)
			return list;
		List<AptAptmtContact> result = new ArrayList<AptAptmtContact>();
		for (AptAptmtContact entity : list) {
			result.add(detach(entity));
		}
		return result;
	}

	private AptAptmtContactSearchInput detach(AptAptmtContactSearchInput searchInput) {
		searchInput.setEntity(detach(searchInput.getEntity()));
		return searchInput;
	}

}
