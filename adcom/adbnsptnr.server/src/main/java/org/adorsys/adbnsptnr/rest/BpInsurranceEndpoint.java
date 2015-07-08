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

import org.adorsys.adbnsptnr.jpa.BpInsurrance;
import org.adorsys.adbnsptnr.jpa.BpInsurranceSearchInput;
import org.adorsys.adbnsptnr.jpa.BpInsurranceSearchResult;
import org.adorsys.adbnsptnr.jpa.BpInsurrance_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/insurrances")
public class BpInsurranceEndpoint
{

   @Inject
   private BpInsurranceEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public BpInsurrance create(BpInsurrance entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id}")
   public Response deleteById(@PathParam("id") String id)
   {
      BpInsurrance deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public BpInsurrance update(BpInsurrance entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      BpInsurrance found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public BpInsurranceSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<BpInsurrance> resultList = ejb.listAll(start, max);
      BpInsurranceSearchInput searchInput = new BpInsurranceSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new BpInsurranceSearchResult((long) resultList.size(),
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
   public BpInsurranceSearchResult findBy(BpInsurranceSearchInput searchInput)
   {
      SingularAttribute<BpInsurrance, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<BpInsurrance> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new BpInsurranceSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(BpInsurranceSearchInput searchInput)
   {
      SingularAttribute<BpInsurrance, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public BpInsurranceSearchResult findByLike(BpInsurranceSearchInput searchInput)
   {
      SingularAttribute<BpInsurrance, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<BpInsurrance> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new BpInsurranceSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(BpInsurranceSearchInput searchInput)
   {
      SingularAttribute<BpInsurrance, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<BpInsurrance, ?>[] readSeachAttributes(
         BpInsurranceSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<BpInsurrance, ?>> result = new ArrayList<SingularAttribute<BpInsurrance, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = BpInsurrance_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<BpInsurrance, ?>) field.get(null));
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

   

   private BpInsurrance detach(BpInsurrance entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<BpInsurrance> detach(List<BpInsurrance> list)
   {
      if (list == null)
         return list;
      List<BpInsurrance> result = new ArrayList<BpInsurrance>();
      for (BpInsurrance entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private BpInsurranceSearchInput detach(BpInsurranceSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}