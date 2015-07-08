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

import org.adorsys.adbase.jpa.UserWsRestriction;
import org.adorsys.adbase.jpa.UserWsRestriction_;
import org.adorsys.adbase.jpa.UserWsRestrictionSearchInput;
import org.adorsys.adbase.jpa.UserWsRestrictionSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/userwsrestrictions")
public class UserWsRestrictionEndpoint
{

   @Inject
   private UserWsRestrictionEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public UserWsRestriction create(UserWsRestriction entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id}")
   public Response deleteById(@PathParam("id") String id)
   {
      UserWsRestriction deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public UserWsRestriction update(UserWsRestriction entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      UserWsRestriction found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public UserWsRestrictionSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<UserWsRestriction> resultList = ejb.listAll(start, max);
      UserWsRestrictionSearchInput searchInput = new UserWsRestrictionSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new UserWsRestrictionSearchResult((long) resultList.size(),
            detach(resultList), detach(searchInput));
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
   public UserWsRestrictionSearchResult findBy(UserWsRestrictionSearchInput searchInput)
   {
      SingularAttribute<UserWsRestriction, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<UserWsRestriction> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new UserWsRestrictionSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(UserWsRestrictionSearchInput searchInput)
   {
      SingularAttribute<UserWsRestriction, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public UserWsRestrictionSearchResult findByLike(UserWsRestrictionSearchInput searchInput)
   {
      SingularAttribute<UserWsRestriction, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<UserWsRestriction> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new UserWsRestrictionSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(UserWsRestrictionSearchInput searchInput)
   {
      SingularAttribute<UserWsRestriction, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<UserWsRestriction, ?>[] readSeachAttributes(
         UserWsRestrictionSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<UserWsRestriction, ?>> result = new ArrayList<SingularAttribute<UserWsRestriction, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = UserWsRestriction_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<UserWsRestriction, ?>) field.get(null));
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

   

   private UserWsRestriction detach(UserWsRestriction entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<UserWsRestriction> detach(List<UserWsRestriction> list)
   {
      if (list == null)
         return list;
      List<UserWsRestriction> result = new ArrayList<UserWsRestriction>();
      for (UserWsRestriction entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private UserWsRestrictionSearchInput detach(UserWsRestrictionSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}