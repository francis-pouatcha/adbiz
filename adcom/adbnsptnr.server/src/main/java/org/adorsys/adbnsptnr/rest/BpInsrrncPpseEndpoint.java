package org.adorsys.adbnsptnr.rest;

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

import org.adorsys.adbnsptnr.jpa.BpInsrrncPpse;
import org.adorsys.adbnsptnr.jpa.BpInsrrncPpseSearchInput;
import org.adorsys.adbnsptnr.jpa.BpInsrrncPpseSearchResult;
import org.adorsys.adbnsptnr.jpa.BpInsrrncPpse_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/insurrances")
public class BpInsrrncPpseEndpoint
{

   @Inject
   private BpInsrrncPpseEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public BpInsrrncPpse create(BpInsrrncPpse entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id}")
   public Response deleteById(@PathParam("id") String id)
   {
      BpInsrrncPpse deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public BpInsrrncPpse update(BpInsrrncPpse entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      BpInsrrncPpse found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public BpInsrrncPpseSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<BpInsrrncPpse> resultList = ejb.listAll(start, max);
      BpInsrrncPpseSearchInput searchInput = new BpInsrrncPpseSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new BpInsrrncPpseSearchResult((long) resultList.size(),
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
   public BpInsrrncPpseSearchResult findBy(BpInsrrncPpseSearchInput searchInput)
   {
      SingularAttribute<BpInsrrncPpse, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<BpInsrrncPpse> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new BpInsrrncPpseSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(BpInsrrncPpseSearchInput searchInput)
   {
      SingularAttribute<BpInsrrncPpse, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public BpInsrrncPpseSearchResult findByLike(BpInsrrncPpseSearchInput searchInput)
   {
      SingularAttribute<BpInsrrncPpse, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<BpInsrrncPpse> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new BpInsrrncPpseSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(BpInsrrncPpseSearchInput searchInput)
   {
      SingularAttribute<BpInsrrncPpse, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<BpInsrrncPpse, ?>[] readSeachAttributes(
         BpInsrrncPpseSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<BpInsrrncPpse, ?>> result = new ArrayList<SingularAttribute<BpInsrrncPpse, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = BpInsrrncPpse_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<BpInsrrncPpse, ?>) field.get(null));
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

   

   private BpInsrrncPpse detach(BpInsrrncPpse entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<BpInsrrncPpse> detach(List<BpInsrrncPpse> list)
   {
      if (list == null)
         return list;
      List<BpInsrrncPpse> result = new ArrayList<BpInsrrncPpse>();
      for (BpInsrrncPpse entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private BpInsrrncPpseSearchInput detach(BpInsrrncPpseSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}