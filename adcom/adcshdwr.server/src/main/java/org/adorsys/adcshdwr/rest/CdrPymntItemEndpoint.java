package org.adorsys.adcshdwr.rest;

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

import org.adorsys.adcshdwr.jpa.CdrPymntItem;
import org.adorsys.adcshdwr.jpa.CdrPymntItemSearchInput;
import org.adorsys.adcshdwr.jpa.CdrPymntItemSearchResult;
import org.adorsys.adcshdwr.jpa.CdrPymntItem_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/cdrpymntitems")
public class CdrPymntItemEndpoint
{

   @Inject
   private CdrPymntItemEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public CdrPymntItem create(CdrPymntItem entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id}")
   public Response deleteById(@PathParam("id") String id)
   {
      CdrPymntItem deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public CdrPymntItem update(CdrPymntItem entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      CdrPymntItem found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public CdrPymntItemSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<CdrPymntItem> resultList = ejb.listAll(start, max);
      CdrPymntItemSearchInput searchInput = new CdrPymntItemSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new CdrPymntItemSearchResult((long) resultList.size(),
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
   public CdrPymntItemSearchResult findBy(CdrPymntItemSearchInput searchInput)
   {
      SingularAttribute<CdrPymntItem, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<CdrPymntItem> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new CdrPymntItemSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(CdrPymntItemSearchInput searchInput)
   {
      SingularAttribute<CdrPymntItem, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public CdrPymntItemSearchResult findByLike(CdrPymntItemSearchInput searchInput)
   {
      SingularAttribute<CdrPymntItem, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<CdrPymntItem> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new CdrPymntItemSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(CdrPymntItemSearchInput searchInput)
   {
      SingularAttribute<CdrPymntItem, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<CdrPymntItem, ?>[] readSeachAttributes(
         CdrPymntItemSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<CdrPymntItem, ?>> result = new ArrayList<SingularAttribute<CdrPymntItem, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = CdrPymntItem_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<CdrPymntItem, ?>) field.get(null));
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

   

   private CdrPymntItem detach(CdrPymntItem entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<CdrPymntItem> detach(List<CdrPymntItem> list)
   {
      if (list == null)
         return list;
      List<CdrPymntItem> result = new ArrayList<CdrPymntItem>();
      for (CdrPymntItem entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private CdrPymntItemSearchInput detach(CdrPymntItemSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}