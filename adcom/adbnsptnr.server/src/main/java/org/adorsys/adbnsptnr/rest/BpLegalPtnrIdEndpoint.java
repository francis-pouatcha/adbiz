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

import org.adorsys.adbnsptnr.jpa.BpLegalPtnrId;
import org.adorsys.adbnsptnr.jpa.BpLegalPtnrIdSearchInput;
import org.adorsys.adbnsptnr.jpa.BpLegalPtnrIdSearchResult;
import org.adorsys.adbnsptnr.jpa.BpLegalPtnrId_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/bplegalptnrids")
public class BpLegalPtnrIdEndpoint
{

   @Inject
   private BpLegalPtnrIdEJB ejb;

   @DELETE
   @Path("/{id}")
   public Response deleteById(@PathParam("id") String id)
   {
      BpLegalPtnrId deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public BpLegalPtnrId update(BpLegalPtnrId entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      BpLegalPtnrId found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public BpLegalPtnrIdSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<BpLegalPtnrId> resultList = ejb.listAll(start, max);
      BpLegalPtnrIdSearchInput searchInput = new BpLegalPtnrIdSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new BpLegalPtnrIdSearchResult((long) resultList.size(),
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
   public BpLegalPtnrIdSearchResult findBy(BpLegalPtnrIdSearchInput searchInput)
   {
      SingularAttribute<BpLegalPtnrId, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<BpLegalPtnrId> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new BpLegalPtnrIdSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(BpLegalPtnrIdSearchInput searchInput)
   {
      SingularAttribute<BpLegalPtnrId, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public BpLegalPtnrIdSearchResult findByLike(BpLegalPtnrIdSearchInput searchInput)
   {
      SingularAttribute<BpLegalPtnrId, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<BpLegalPtnrId> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new BpLegalPtnrIdSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(BpLegalPtnrIdSearchInput searchInput)
   {
      SingularAttribute<BpLegalPtnrId, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<BpLegalPtnrId, ?>[] readSeachAttributes(
         BpLegalPtnrIdSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<BpLegalPtnrId, ?>> result = new ArrayList<SingularAttribute<BpLegalPtnrId, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = BpLegalPtnrId_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<BpLegalPtnrId, ?>) field.get(null));
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

   

   private BpLegalPtnrId detach(BpLegalPtnrId entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<BpLegalPtnrId> detach(List<BpLegalPtnrId> list)
   {
      if (list == null)
         return list;
      List<BpLegalPtnrId> result = new ArrayList<BpLegalPtnrId>();
      for (BpLegalPtnrId entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private BpLegalPtnrIdSearchInput detach(BpLegalPtnrIdSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}