package org.adorsys.adprocmt.rest;

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

import org.adorsys.adprocmt.jpa.PrcmtProcOrder;
import org.adorsys.adprocmt.jpa.PrcmtProcOrderSearchInput;
import org.adorsys.adprocmt.jpa.PrcmtProcOrderSearchResult;
import org.adorsys.adprocmt.jpa.PrcmtProcOrder_;
import org.adorsys.adprocmt.jpa.ProcmtPOTriggerMode;
import org.adorsys.adprocmt.jpa.ProcmtPOType;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/prcmtprocorders")
public class PrcmtProcOrderEndpoint
{

   @Inject
   private PrcmtProcOrderEJB ejb;
   @Inject
   private ProcmtPOTriggerModeEJB triggerModeEJB;
   @Inject
   private ProcmtPOTypeEJB procmtPOTypeEJB;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public PrcmtProcOrder create(PrcmtProcOrder entity)
   {
      return detach(ejb.createCustom(entity));
   }

   @DELETE
   @Path("/{id}")
   public Response deleteById(@PathParam("id") String id)
   {
      PrcmtProcOrder deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public PrcmtProcOrder update(PrcmtProcOrder entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      PrcmtProcOrder found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }
   
   @POST
   @Path("/findCustom")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public PrcmtProcOrderSearchResult findCustom(PrcmtProcOrderSearchInput searchInput)
   {
      return ejb.findCustom(searchInput);
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public PrcmtProcOrderSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<PrcmtProcOrder> resultList = ejb.listAll(start, max);
      PrcmtProcOrderSearchInput searchInput = new PrcmtProcOrderSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new PrcmtProcOrderSearchResult((long) resultList.size(),
            detach(resultList), detach(searchInput));
   }
   
   @GET
   @Path("/listAllTriggerMode")
   @Produces({ "application/json", "application/xml" })
   public List<ProcmtPOTriggerMode> listAllTriggerMode()
   {
      return triggerModeEJB.listAll();
   }
   
   @GET
   @Path("/listAllPOType")
   @Produces({ "application/json", "application/xml" })
   public List<ProcmtPOType> listAllPOType()
   {
      return procmtPOTypeEJB.listAll();
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
   public PrcmtProcOrderSearchResult findBy(PrcmtProcOrderSearchInput searchInput)
   {
      SingularAttribute<PrcmtProcOrder, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<PrcmtProcOrder> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new PrcmtProcOrderSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(PrcmtProcOrderSearchInput searchInput)
   {
      SingularAttribute<PrcmtProcOrder, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public PrcmtProcOrderSearchResult findByLike(PrcmtProcOrderSearchInput searchInput)
   {
      SingularAttribute<PrcmtProcOrder, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<PrcmtProcOrder> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new PrcmtProcOrderSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(PrcmtProcOrderSearchInput searchInput)
   {
      SingularAttribute<PrcmtProcOrder, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<PrcmtProcOrder, ?>[] readSeachAttributes(
         PrcmtProcOrderSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<PrcmtProcOrder, ?>> result = new ArrayList<SingularAttribute<PrcmtProcOrder, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = PrcmtProcOrder_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<PrcmtProcOrder, ?>) field.get(null));
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

   

   private PrcmtProcOrder detach(PrcmtProcOrder entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<PrcmtProcOrder> detach(List<PrcmtProcOrder> list)
   {
      if (list == null)
         return list;
      List<PrcmtProcOrder> result = new ArrayList<PrcmtProcOrder>();
      for (PrcmtProcOrder entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private PrcmtProcOrderSearchInput detach(PrcmtProcOrderSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}