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

import org.adorsys.adsales.jpa.SlsInvcePtnr;
import org.adorsys.adsales.jpa.SlsInvcePtnrSearchInput;
import org.adorsys.adsales.jpa.SlsInvcePtnrSearchResult;
import org.adorsys.adsales.jpa.SlsInvcePtnr_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/slsinvceptnrs")
public class SlsInvcePtnrEndpoint
{

   @Inject
   private SlsInvcePtnrEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public SlsInvcePtnr create(SlsInvcePtnr entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id}")
   public Response deleteById(@PathParam("id") String id)
   {
      SlsInvcePtnr deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public SlsInvcePtnr update(SlsInvcePtnr entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      SlsInvcePtnr found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public SlsInvcePtnrSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<SlsInvcePtnr> resultList = ejb.listAll(start, max);
      SlsInvcePtnrSearchInput searchInput = new SlsInvcePtnrSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new SlsInvcePtnrSearchResult((long) resultList.size(),
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
   public SlsInvcePtnrSearchResult findBy(SlsInvcePtnrSearchInput searchInput)
   {
      SingularAttribute<SlsInvcePtnr, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<SlsInvcePtnr> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new SlsInvcePtnrSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(SlsInvcePtnrSearchInput searchInput)
   {
      SingularAttribute<SlsInvcePtnr, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public SlsInvcePtnrSearchResult findByLike(SlsInvcePtnrSearchInput searchInput)
   {
      SingularAttribute<SlsInvcePtnr, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<SlsInvcePtnr> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new SlsInvcePtnrSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(SlsInvcePtnrSearchInput searchInput)
   {
      SingularAttribute<SlsInvcePtnr, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<SlsInvcePtnr, ?>[] readSeachAttributes(
         SlsInvcePtnrSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<SlsInvcePtnr, ?>> result = new ArrayList<SingularAttribute<SlsInvcePtnr, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = SlsInvcePtnr_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<SlsInvcePtnr, ?>) field.get(null));
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

   

   private SlsInvcePtnr detach(SlsInvcePtnr entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<SlsInvcePtnr> detach(List<SlsInvcePtnr> list)
   {
      if (list == null)
         return list;
      List<SlsInvcePtnr> result = new ArrayList<SlsInvcePtnr>();
      for (SlsInvcePtnr entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private SlsInvcePtnrSearchInput detach(SlsInvcePtnrSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}