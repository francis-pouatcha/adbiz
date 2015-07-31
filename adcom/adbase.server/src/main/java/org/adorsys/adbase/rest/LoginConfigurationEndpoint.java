package org.adorsys.adbase.rest;

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

import org.adorsys.adbase.jpa.LoginConfiguration;
import org.adorsys.adbase.jpa.LoginConfiguration_;
import org.adorsys.adbase.jpa.LoginConfigurationSearchInput;
import org.adorsys.adbase.jpa.LoginConfigurationSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/loginconfigurations")
public class LoginConfigurationEndpoint {

	@Inject
	private LoginConfigurationEJB ejb;

	@POST
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public LoginConfiguration create(LoginConfiguration entity) {
		return detach(ejb.create(entity));
	}

	@DELETE
	@Path("/{id}")
	public Response deleteById(@PathParam("id") String id) {
		LoginConfiguration deleted = ejb.deleteById(id);
		if (deleted == null)
			return Response.status(Status.NOT_FOUND).build();

		return Response.ok(detach(deleted)).build();
	}

	@PUT
	@Path("/{id}")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public LoginConfiguration update(LoginConfiguration entity) {
		return detach(ejb.update(entity));
	}

	@GET
	@Path("/{id}")
	@Produces({ "application/json", "application/xml" })
	public Response findById(@PathParam("id") String id) {
		LoginConfiguration found = ejb.findById(id);
		if (found == null)
			return Response.status(Status.NOT_FOUND).build();
		return Response.ok(detach(found)).build();
	}

	@GET
	@Produces({ "application/json", "application/xml" })
	public LoginConfigurationSearchResult listAll(
			@QueryParam("start") int start, @QueryParam("max") int max) {
		List<LoginConfiguration> resultList = ejb.listAll(start, max);
		LoginConfigurationSearchInput searchInput = new LoginConfigurationSearchInput();
		searchInput.setStart(start);
		searchInput.setMax(max);
		return new LoginConfigurationSearchResult((long) resultList.size(),
				detach(resultList), detach(searchInput));
	}

	@GET
	@Path("previousLogin/{loginName}")
	@Produces({ "application/json", "application/xml" })
	public Response previousLoginRebate(@PathParam("loginName") String loginName) {
		List<LoginConfiguration> found;
		try {
			found = ejb.findPreviousLogin(loginName);
		} catch (Exception e) {
			return Response.status(Status.NOT_FOUND).build();
		}
		if (found.isEmpty()) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(detach(found.iterator().next())).build();
	}

	@GET
	@Path("nextLogin/{loginName}")
	@Produces({ "application/json", "application/xml" })
	public Response nextLoginRebate(@PathParam("loginName") String loginName) {
		List<LoginConfiguration> found;
		try {
			found = ejb.findNextLogin(loginName);
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
	public LoginConfigurationSearchResult findBy(
			LoginConfigurationSearchInput searchInput) {
		SingularAttribute<LoginConfiguration, ?>[] attributes = readSeachAttributes(searchInput);
		Long count = ejb.countBy(searchInput.getEntity(), attributes);
		List<LoginConfiguration> resultList = ejb.findBy(
				searchInput.getEntity(), searchInput.getStart(),
				searchInput.getMax(), attributes);
		return new LoginConfigurationSearchResult(count, detach(resultList),
				detach(searchInput));
	}

	@POST
	@Path("/countBy")
	@Consumes({ "application/json", "application/xml" })
	public Long countBy(LoginConfigurationSearchInput searchInput) {
		SingularAttribute<LoginConfiguration, ?>[] attributes = readSeachAttributes(searchInput);
		return ejb.countBy(searchInput.getEntity(), attributes);
	}

	@POST
	@Path("/findByLike")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public LoginConfigurationSearchResult findByLike(
			LoginConfigurationSearchInput searchInput) {
		SingularAttribute<LoginConfiguration, ?>[] attributes = readSeachAttributes(searchInput);
		Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
		List<LoginConfiguration> resultList = ejb.findByLike(
				searchInput.getEntity(), searchInput.getStart(),
				searchInput.getMax(), attributes);
		return new LoginConfigurationSearchResult(countLike,
				detach(resultList), detach(searchInput));
	}

	@POST
	@Path("/countByLike")
	@Consumes({ "application/json", "application/xml" })
	public Long countByLike(LoginConfigurationSearchInput searchInput) {
		SingularAttribute<LoginConfiguration, ?>[] attributes = readSeachAttributes(searchInput);
		return ejb.countByLike(searchInput.getEntity(), attributes);
	}

	@SuppressWarnings("unchecked")
	private SingularAttribute<LoginConfiguration, ?>[] readSeachAttributes(
			LoginConfigurationSearchInput searchInput) {
		List<String> fieldNames = searchInput.getFieldNames();
		List<SingularAttribute<LoginConfiguration, ?>> result = new ArrayList<SingularAttribute<LoginConfiguration, ?>>();
		for (String fieldName : fieldNames) {
			Field[] fields = LoginConfiguration_.class.getFields();
			for (Field field : fields) {
				if (field.getName().equals(fieldName)) {
					try {
						result.add((SingularAttribute<LoginConfiguration, ?>) field
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

	private LoginConfiguration detach(LoginConfiguration entity) {
		if (entity == null)
			return null;

		return entity;
	}

	private List<LoginConfiguration> detach(List<LoginConfiguration> list) {
		if (list == null)
			return list;
		List<LoginConfiguration> result = new ArrayList<LoginConfiguration>();
		for (LoginConfiguration entity : list) {
			result.add(detach(entity));
		}
		return result;
	}

	private LoginConfigurationSearchInput detach(
			LoginConfigurationSearchInput searchInput) {
		searchInput.setEntity(detach(searchInput.getEntity()));
		return searchInput;
	}
}