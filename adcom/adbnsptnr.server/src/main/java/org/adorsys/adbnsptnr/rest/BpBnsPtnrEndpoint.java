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

import org.adorsys.adbnsptnr.jpa.BpBnsPtnr;
import org.adorsys.adbnsptnr.jpa.BpBnsPtnrSearchInput;
import org.adorsys.adbnsptnr.jpa.BpBnsPtnrSearchResult;
import org.adorsys.adbnsptnr.jpa.BpBnsPtnr_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/bpbnsptnrs")
public class BpBnsPtnrEndpoint
{

   @Inject
   private BpBnsPtnrEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public BpBnsPtnr create(BpBnsPtnr entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id}")
   public Response deleteById(@PathParam("id") String id)
   {
      BpBnsPtnr deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public BpBnsPtnr update(BpBnsPtnr entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      BpBnsPtnr found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public BpBnsPtnrSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<BpBnsPtnr> resultList = ejb.listAll(start, max);
      BpBnsPtnrSearchInput searchInput = new BpBnsPtnrSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new BpBnsPtnrSearchResult((long) resultList.size(),
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
   public BpBnsPtnrSearchResult findBy(BpBnsPtnrSearchInput searchInput)
   {
      SingularAttribute<BpBnsPtnr, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<BpBnsPtnr> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new BpBnsPtnrSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(BpBnsPtnrSearchInput searchInput)
   {
      SingularAttribute<BpBnsPtnr, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public BpBnsPtnrSearchResult findByLike(BpBnsPtnrSearchInput searchInput)
   {
      SingularAttribute<BpBnsPtnr, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<BpBnsPtnr> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new BpBnsPtnrSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(BpBnsPtnrSearchInput searchInput)
   {
      SingularAttribute<BpBnsPtnr, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<BpBnsPtnr, ?>[] readSeachAttributes(
         BpBnsPtnrSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<BpBnsPtnr, ?>> result = new ArrayList<SingularAttribute<BpBnsPtnr, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = BpBnsPtnr_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<BpBnsPtnr, ?>) field.get(null));
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

   

   private BpBnsPtnr detach(BpBnsPtnr entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<BpBnsPtnr> detach(List<BpBnsPtnr> list)
   {
      if (list == null)
         return list;
      List<BpBnsPtnr> result = new ArrayList<BpBnsPtnr>();
      for (BpBnsPtnr entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private BpBnsPtnrSearchInput detach(BpBnsPtnrSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}