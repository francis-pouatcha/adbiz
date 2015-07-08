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

import org.adorsys.adsales.jpa.SlsAcct;
import org.adorsys.adsales.jpa.SlsAcctSearchInput;
import org.adorsys.adsales.jpa.SlsAcctSearchResult;
import org.adorsys.adsales.jpa.SlsAcct_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/slsaccts")
public class SlsAcctEndpoint
{

   @Inject
   private SlsAcctEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public SlsAcct create(SlsAcct entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id}")
   public Response deleteById(@PathParam("id") String id)
   {
      SlsAcct deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public SlsAcct update(SlsAcct entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      SlsAcct found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public SlsAcctSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<SlsAcct> resultList = ejb.listAll(start, max);
      SlsAcctSearchInput searchInput = new SlsAcctSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new SlsAcctSearchResult((long) resultList.size(),
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
   public SlsAcctSearchResult findBy(SlsAcctSearchInput searchInput)
   {
      SingularAttribute<SlsAcct, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<SlsAcct> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new SlsAcctSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(SlsAcctSearchInput searchInput)
   {
      SingularAttribute<SlsAcct, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public SlsAcctSearchResult findByLike(SlsAcctSearchInput searchInput)
   {
      SingularAttribute<SlsAcct, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<SlsAcct> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new SlsAcctSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(SlsAcctSearchInput searchInput)
   {
      SingularAttribute<SlsAcct, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<SlsAcct, ?>[] readSeachAttributes(
         SlsAcctSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<SlsAcct, ?>> result = new ArrayList<SingularAttribute<SlsAcct, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = SlsAcct_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<SlsAcct, ?>) field.get(null));
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

   

   private SlsAcct detach(SlsAcct entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<SlsAcct> detach(List<SlsAcct> list)
   {
      if (list == null)
         return list;
      List<SlsAcct> result = new ArrayList<SlsAcct>();
      for (SlsAcct entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private SlsAcctSearchInput detach(SlsAcctSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}