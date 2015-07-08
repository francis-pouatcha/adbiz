package org.adorsys.adstock.rest;

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

import org.adorsys.adstock.jpa.StkLotStockQty;
import org.adorsys.adstock.jpa.StkLotStockQtySearchInput;
import org.adorsys.adstock.jpa.StkLotStockQtySearchResult;
import org.adorsys.adstock.jpa.StkLotStockQty_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/stklotstockqtys")
public class StkLotStockQtyEndpoint
{

   @Inject
   private StkLotStockQtyEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public StkLotStockQty create(StkLotStockQty entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id}")
   public Response deleteById(@PathParam("id") String id)
   {
      StkLotStockQty deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public StkLotStockQty update(StkLotStockQty entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      StkLotStockQty found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public StkLotStockQtySearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<StkLotStockQty> resultList = ejb.listAll(start, max);
      StkLotStockQtySearchInput searchInput = new StkLotStockQtySearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new StkLotStockQtySearchResult((long) resultList.size(),
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
   public StkLotStockQtySearchResult findBy(StkLotStockQtySearchInput searchInput)
   {
      SingularAttribute<StkLotStockQty, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<StkLotStockQty> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new StkLotStockQtySearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(StkLotStockQtySearchInput searchInput)
   {
      SingularAttribute<StkLotStockQty, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public StkLotStockQtySearchResult findByLike(StkLotStockQtySearchInput searchInput)
   {
      SingularAttribute<StkLotStockQty, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<StkLotStockQty> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new StkLotStockQtySearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(StkLotStockQtySearchInput searchInput)
   {
      SingularAttribute<StkLotStockQty, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<StkLotStockQty, ?>[] readSeachAttributes(
         StkLotStockQtySearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<StkLotStockQty, ?>> result = new ArrayList<SingularAttribute<StkLotStockQty, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = StkLotStockQty_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<StkLotStockQty, ?>) field.get(null));
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

   

   private StkLotStockQty detach(StkLotStockQty entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<StkLotStockQty> detach(List<StkLotStockQty> list)
   {
      if (list == null)
         return list;
      List<StkLotStockQty> result = new ArrayList<StkLotStockQty>();
      for (StkLotStockQty entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private StkLotStockQtySearchInput detach(StkLotStockQtySearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}