package org.adorsys.adsales.rest;

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

import org.adorsys.adsales.jpa.SlsInvceHistory;
import org.adorsys.adsales.jpa.SlsInvceHistorySearchInput;
import org.adorsys.adsales.jpa.SlsInvceHistorySearchResult;
import org.adorsys.adsales.jpa.SlsInvceHistory_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/slsinvcehistorys")
public class SlsInvceHistoryEndpoint
{

   @Inject
   private SlsInvceHistoryEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public SlsInvceHistory create(SlsInvceHistory entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id}")
   public Response deleteById(@PathParam("id") String id)
   {
      SlsInvceHistory deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public SlsInvceHistory update(SlsInvceHistory entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      SlsInvceHistory found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public SlsInvceHistorySearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<SlsInvceHistory> resultList = ejb.listAll(start, max);
      SlsInvceHistorySearchInput searchInput = new SlsInvceHistorySearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new SlsInvceHistorySearchResult((long) resultList.size(),
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
   public SlsInvceHistorySearchResult findBy(SlsInvceHistorySearchInput searchInput)
   {
      SingularAttribute<SlsInvceHistory, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<SlsInvceHistory> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new SlsInvceHistorySearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(SlsInvceHistorySearchInput searchInput)
   {
      SingularAttribute<SlsInvceHistory, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public SlsInvceHistorySearchResult findByLike(SlsInvceHistorySearchInput searchInput)
   {
      SingularAttribute<SlsInvceHistory, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<SlsInvceHistory> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new SlsInvceHistorySearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(SlsInvceHistorySearchInput searchInput)
   {
      SingularAttribute<SlsInvceHistory, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<SlsInvceHistory, ?>[] readSeachAttributes(
         SlsInvceHistorySearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<SlsInvceHistory, ?>> result = new ArrayList<SingularAttribute<SlsInvceHistory, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = SlsInvceHistory_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<SlsInvceHistory, ?>) field.get(null));
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

   

   private SlsInvceHistory detach(SlsInvceHistory entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<SlsInvceHistory> detach(List<SlsInvceHistory> list)
   {
      if (list == null)
         return list;
      List<SlsInvceHistory> result = new ArrayList<SlsInvceHistory>();
      for (SlsInvceHistory entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private SlsInvceHistorySearchInput detach(SlsInvceHistorySearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}