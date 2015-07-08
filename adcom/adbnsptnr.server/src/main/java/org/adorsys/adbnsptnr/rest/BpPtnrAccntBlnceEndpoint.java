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

import org.adorsys.adbnsptnr.jpa.BpPtnrAccntBlnce;
import org.adorsys.adbnsptnr.jpa.BpPtnrAccntBlnceSearchInput;
import org.adorsys.adbnsptnr.jpa.BpPtnrAccntBlnceSearchResult;
import org.adorsys.adbnsptnr.jpa.BpPtnrAccntBlnce_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/bpptnraccntblnces")
public class BpPtnrAccntBlnceEndpoint
{

   @Inject
   private BpPtnrAccntBlnceEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public BpPtnrAccntBlnce create(BpPtnrAccntBlnce entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id}")
   public Response deleteById(@PathParam("id") String id)
   {
      BpPtnrAccntBlnce deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public BpPtnrAccntBlnce update(BpPtnrAccntBlnce entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      BpPtnrAccntBlnce found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public BpPtnrAccntBlnceSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<BpPtnrAccntBlnce> resultList = ejb.listAll(start, max);
      BpPtnrAccntBlnceSearchInput searchInput = new BpPtnrAccntBlnceSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new BpPtnrAccntBlnceSearchResult((long) resultList.size(),
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
   public BpPtnrAccntBlnceSearchResult findBy(BpPtnrAccntBlnceSearchInput searchInput)
   {
      SingularAttribute<BpPtnrAccntBlnce, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<BpPtnrAccntBlnce> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new BpPtnrAccntBlnceSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(BpPtnrAccntBlnceSearchInput searchInput)
   {
      SingularAttribute<BpPtnrAccntBlnce, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public BpPtnrAccntBlnceSearchResult findByLike(BpPtnrAccntBlnceSearchInput searchInput)
   {
      SingularAttribute<BpPtnrAccntBlnce, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<BpPtnrAccntBlnce> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new BpPtnrAccntBlnceSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(BpPtnrAccntBlnceSearchInput searchInput)
   {
      SingularAttribute<BpPtnrAccntBlnce, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<BpPtnrAccntBlnce, ?>[] readSeachAttributes(
         BpPtnrAccntBlnceSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<BpPtnrAccntBlnce, ?>> result = new ArrayList<SingularAttribute<BpPtnrAccntBlnce, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = BpPtnrAccntBlnce_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<BpPtnrAccntBlnce, ?>) field.get(null));
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

   

   private BpPtnrAccntBlnce detach(BpPtnrAccntBlnce entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<BpPtnrAccntBlnce> detach(List<BpPtnrAccntBlnce> list)
   {
      if (list == null)
         return list;
      List<BpPtnrAccntBlnce> result = new ArrayList<BpPtnrAccntBlnce>();
      for (BpPtnrAccntBlnce entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private BpPtnrAccntBlnceSearchInput detach(BpPtnrAccntBlnceSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}