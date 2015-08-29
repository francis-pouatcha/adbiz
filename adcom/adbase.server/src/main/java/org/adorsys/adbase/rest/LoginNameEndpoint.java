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
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.adorsys.adbase.jpa.Login;
import org.adorsys.adbase.jpa.LoginName;
import org.adorsys.adbase.jpa.LoginNameSearchInput;
import org.adorsys.adbase.jpa.LoginNameSearchResult;
import org.adorsys.adbase.jpa.Login_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/loginnamess")
public class LoginNameEndpoint {

	@Inject
	private LoginLookup lookup;

	@GET
	@Produces({ "application/json", "application/xml" })
	public LoginNameSearchResult listAll(@QueryParam("start") int start,
			@QueryParam("max") int max) {
		List<Login> resultList = lookup.listAll(start, max);
		LoginNameSearchInput searchInput = new LoginNameSearchInput();
		searchInput.setStart(start);
		searchInput.setMax(max);
		return new LoginNameSearchResult((long) resultList.size(),
				detach(resultList), detach(searchInput));
	}

	@POST
	@Path("/findBy")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public LoginNameSearchResult findBy(LoginNameSearchInput searchInput) {
		SingularAttribute<Login, ?>[] attributes = readSeachAttributes(searchInput);
		Long count = lookup.countBy(searchInput.getEntity().toLogin(), attributes);
		List<Login> resultList = lookup.findBy(searchInput.getEntity().toLogin(),
				searchInput.getStart(), searchInput.getMax(), attributes);
		return new LoginNameSearchResult(count, detach(resultList),
				detach(searchInput));
	}

	@POST
	@Path("/findByLike")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public LoginNameSearchResult findByLike(LoginNameSearchInput searchInput) {
		SingularAttribute<Login, ?>[] attributes = readSeachAttributes(searchInput);
		Long countLike = lookup.countByLike(searchInput.getEntity().toLogin(), attributes);
		List<Login> resultList = lookup.findByLike(searchInput.getEntity().toLogin(),
				searchInput.getStart(), searchInput.getMax(), attributes);
		return new LoginNameSearchResult(countLike, detach(resultList),
				detach(searchInput));
	}

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      Login found = lookup.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }
	
	@SuppressWarnings("unchecked")
	private SingularAttribute<Login, ?>[] readSeachAttributes(
			LoginNameSearchInput searchInput) {
		List<String> fieldNames = searchInput.getFieldNames();
		List<SingularAttribute<Login, ?>> result = new ArrayList<SingularAttribute<Login, ?>>();
		for (String fieldName : fieldNames) {
			Field[] fields = Login_.class.getFields();
			for (Field field : fields) {
				if (field.getName().equals(fieldName)) {
					try {
						result.add((SingularAttribute<Login, ?>) field
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

	private LoginName detach(Login entity) {
		if (entity == null)
			return null;

		return new LoginName(entity);
	}

	private List<LoginName> detach(List<Login> list) {
		if (list == null)
			return null;
		List<LoginName> result = new ArrayList<LoginName>();
		for (Login entity : list) {
			result.add(detach(entity));
		}
		return result;
	}

	private LoginNameSearchInput detach(LoginNameSearchInput searchInput) {
//		searchInput.setEntity(detach(searchInput.getEntity()));
		return searchInput;
	}
}