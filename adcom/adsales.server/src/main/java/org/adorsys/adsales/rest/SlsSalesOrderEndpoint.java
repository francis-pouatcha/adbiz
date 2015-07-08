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

import org.adorsys.adsales.jpa.SlsSalesOrder;
import org.adorsys.adsales.jpa.SlsSalesOrderSearchInput;
import org.adorsys.adsales.jpa.SlsSalesOrderSearchResult;
import org.adorsys.adsales.jpa.SlsSalesOrder_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/slssalesorders")
public class SlsSalesOrderEndpoint
{

   @Inject
   private SlsSalesOrderEJB ejb;
   
   @Inject
   private SlsSalesOrderHolderEJB holderEJB;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public SlsSalesOrder create(SlsSalesOrder entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id}")
   public Response deleteById(@PathParam("id") String id)
   {
      SlsSalesOrder deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public SlsSalesOrder update(SlsSalesOrder entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      SlsSalesOrder found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public SlsSalesOrderSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<SlsSalesOrder> resultList = ejb.listAll(start, max);
      SlsSalesOrderSearchInput searchInput = new SlsSalesOrderSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new SlsSalesOrderSearchResult((long) resultList.size(),
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
   public SlsSalesOrderSearchResult findBy(SlsSalesOrderSearchInput searchInput)
   {
      SingularAttribute<SlsSalesOrder, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<SlsSalesOrder> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new SlsSalesOrderSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(SlsSalesOrderSearchInput searchInput)
   {
      SingularAttribute<SlsSalesOrder, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public SlsSalesOrderSearchResult findByLike(SlsSalesOrderSearchInput searchInput)
   {
      SingularAttribute<SlsSalesOrder, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<SlsSalesOrder> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      List<SlsSalesOrder> resultList2 = holderEJB.reloadSlsSalesOrders(resultList);
      return new SlsSalesOrderSearchResult(countLike, detach(resultList2),
            detach(searchInput));
   }
   
   @POST
   @Path("/findCustom")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public SlsSalesOrderSearchResult findCustom(SlsSalesOrderSearchInput searchInput)
   {
	 if(searchInput.noSpecialParams()) return findByLike(searchInput);
	 Long count = ejb.countCustom(searchInput);
	 List<SlsSalesOrder> resultList = ejb.findCustom(searchInput);
	 return new SlsSalesOrderSearchResult(count, detach(resultList), detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(SlsSalesOrderSearchInput searchInput)
   {
      SingularAttribute<SlsSalesOrder, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<SlsSalesOrder, ?>[] readSeachAttributes(
         SlsSalesOrderSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<SlsSalesOrder, ?>> result = new ArrayList<SingularAttribute<SlsSalesOrder, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = SlsSalesOrder_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<SlsSalesOrder, ?>) field.get(null));
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

   

   private SlsSalesOrder detach(SlsSalesOrder entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<SlsSalesOrder> detach(List<SlsSalesOrder> list)
   {
      if (list == null)
         return list;
      List<SlsSalesOrder> result = new ArrayList<SlsSalesOrder>();
      for (SlsSalesOrder entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private SlsSalesOrderSearchInput detach(SlsSalesOrderSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}