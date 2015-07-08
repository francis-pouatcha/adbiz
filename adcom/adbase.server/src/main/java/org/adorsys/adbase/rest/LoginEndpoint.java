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

import org.adorsys.adbase.jpa.Login;
import org.adorsys.adbase.jpa.Login_;
import org.adorsys.adbase.jpa.LoginSearchInput;
import org.adorsys.adbase.jpa.LoginSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/logins")
public class LoginEndpoint
{

   @Inject
   private LoginEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public Login create(Login entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id}")
   public Response deleteById(@PathParam("id") String id)
   {
      Login deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public Login update(Login entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      Login found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public LoginSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<Login> resultList = ejb.listAll(start, max);
      LoginSearchInput searchInput = new LoginSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new LoginSearchResult((long) resultList.size(),
            detach(resultList), detach(searchInput));
   }

   
   	@GET
	@Path("previousLogin/{loginName}")
	@Produces({ "application/json", "application/xml" })
	public Response previousLogin(@PathParam("loginName") String loginName)
	{
		List<Login> found;
		try {
			found = ejb.findPreviousLogin(loginName);
		} catch (Exception e) {
			return Response.status(Status.NOT_FOUND).build();
		}
		if (found.isEmpty()){
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(detach(found.iterator().next())).build();
	}
   	
   	@GET
	@Path("nextLogin/{loginName}")
	@Produces({ "application/json", "application/xml" })
	public Response nextLogin(@PathParam("loginName") String loginName)
	{
		List<Login> found;
		try {
			found = ejb.findNextLogin(loginName);
		} catch (Exception e) {
			return Response.status(Status.NOT_FOUND).build();
		}
		if (found.isEmpty()){
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(detach(found.iterator().next())).build();
	}
   
   @GET
   @Path("/count")
   public Long count()
   {
      return ejb.count();
   }

   @POST
   @Path("/findBy")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public LoginSearchResult findBy(LoginSearchInput searchInput)
   {
      SingularAttribute<Login, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<Login> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new LoginSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(LoginSearchInput searchInput)
   {
      SingularAttribute<Login, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public LoginSearchResult findByLike(LoginSearchInput searchInput)
   {
      SingularAttribute<Login, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<Login> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new LoginSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(LoginSearchInput searchInput)
   {
      SingularAttribute<Login, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<Login, ?>[] readSeachAttributes(
         LoginSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<Login, ?>> result = new ArrayList<SingularAttribute<Login, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = Login_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<Login, ?>) field.get(null));
               }
               catch (IllegalArgumentException e)
               {
                  throw new IllegalStateException(e);
               }
               catch (IllegalAccessException e)
               {
                  throw new IllegalStateException(e);
               }
            }
         }
      }
      return result.toArray(new SingularAttribute[result.size()]);
   }

   

   private Login detach(Login entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<Login> detach(List<Login> list)
   {
      if (list == null)
         return list;
      List<Login> result = new ArrayList<Login>();
      for (Login entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private LoginSearchInput detach(LoginSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}