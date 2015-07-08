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

import org.adorsys.adsales.jpa.SlsSOHstry;
import org.adorsys.adsales.jpa.SlsSOHstrySearchInput;
import org.adorsys.adsales.jpa.SlsSOHstrySearchResult;
import org.adorsys.adsales.jpa.SlsSOHstry_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/slssohstrys")
public class SlsSOHstryEndpoint
{

   @Inject
   private SlsSOHstryEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public SlsSOHstry create(SlsSOHstry entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id}")
   public Response deleteById(@PathParam("id") String id)
   {
      SlsSOHstry deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public SlsSOHstry update(SlsSOHstry entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      SlsSOHstry found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public SlsSOHstrySearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<SlsSOHstry> resultList = ejb.listAll(start, max);
      SlsSOHstrySearchInput searchInput = new SlsSOHstrySearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new SlsSOHstrySearchResult((long) resultList.size(),
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
   public SlsSOHstrySearchResult findBy(SlsSOHstrySearchInput searchInput)
   {
      SingularAttribute<SlsSOHstry, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<SlsSOHstry> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new SlsSOHstrySearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(SlsSOHstrySearchInput searchInput)
   {
      SingularAttribute<SlsSOHstry, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public SlsSOHstrySearchResult findByLike(SlsSOHstrySearchInput searchInput)
   {
      SingularAttribute<SlsSOHstry, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<SlsSOHstry> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new SlsSOHstrySearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(SlsSOHstrySearchInput searchInput)
   {
      SingularAttribute<SlsSOHstry, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<SlsSOHstry, ?>[] readSeachAttributes(
         SlsSOHstrySearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<SlsSOHstry, ?>> result = new ArrayList<SingularAttribute<SlsSOHstry, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = SlsSOHstry_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<SlsSOHstry, ?>) field.get(null));
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

   

   private SlsSOHstry detach(SlsSOHstry entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<SlsSOHstry> detach(List<SlsSOHstry> list)
   {
      if (list == null)
         return list;
      List<SlsSOHstry> result = new ArrayList<SlsSOHstry>();
      for (SlsSOHstry entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private SlsSOHstrySearchInput detach(SlsSOHstrySearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}