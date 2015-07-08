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

import org.adorsys.adbnsptnr.jpa.BpCtgryOfPtnr;
import org.adorsys.adbnsptnr.jpa.BpCtgryOfPtnrSearchInput;
import org.adorsys.adbnsptnr.jpa.BpCtgryOfPtnrSearchResult;
import org.adorsys.adbnsptnr.jpa.BpCtgryOfPtnr_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/bpctgryofptnrs")
public class BpCtgryOfPtnrEndpoint
{

   @Inject
   private BpCtgryOfPtnrEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public BpCtgryOfPtnr create(BpCtgryOfPtnr entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id}")
   public Response deleteById(@PathParam("id") String id)
   {
      BpCtgryOfPtnr deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public BpCtgryOfPtnr update(BpCtgryOfPtnr entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      BpCtgryOfPtnr found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public BpCtgryOfPtnrSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<BpCtgryOfPtnr> resultList = ejb.listAll(start, max);
      BpCtgryOfPtnrSearchInput searchInput = new BpCtgryOfPtnrSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new BpCtgryOfPtnrSearchResult((long) resultList.size(),
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
   public BpCtgryOfPtnrSearchResult findBy(BpCtgryOfPtnrSearchInput searchInput)
   {
      SingularAttribute<BpCtgryOfPtnr, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<BpCtgryOfPtnr> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new BpCtgryOfPtnrSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(BpCtgryOfPtnrSearchInput searchInput)
   {
      SingularAttribute<BpCtgryOfPtnr, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public BpCtgryOfPtnrSearchResult findByLike(BpCtgryOfPtnrSearchInput searchInput)
   {
      SingularAttribute<BpCtgryOfPtnr, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<BpCtgryOfPtnr> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new BpCtgryOfPtnrSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(BpCtgryOfPtnrSearchInput searchInput)
   {
      SingularAttribute<BpCtgryOfPtnr, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<BpCtgryOfPtnr, ?>[] readSeachAttributes(
         BpCtgryOfPtnrSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<BpCtgryOfPtnr, ?>> result = new ArrayList<SingularAttribute<BpCtgryOfPtnr, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = BpCtgryOfPtnr_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<BpCtgryOfPtnr, ?>) field.get(null));
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

   

   private BpCtgryOfPtnr detach(BpCtgryOfPtnr entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<BpCtgryOfPtnr> detach(List<BpCtgryOfPtnr> list)
   {
      if (list == null)
         return list;
      List<BpCtgryOfPtnr> result = new ArrayList<BpCtgryOfPtnr>();
      for (BpCtgryOfPtnr entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private BpCtgryOfPtnrSearchInput detach(BpCtgryOfPtnrSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}