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

import org.adorsys.adcshdwr.jpa.CdrDsPymntItem;
import org.adorsys.adcshdwr.jpa.CdrDsPymntItemSearchInput;
import org.adorsys.adcshdwr.jpa.CdrDsPymntItemSearchResult;
import org.adorsys.adcshdwr.jpa.CdrDsPymntItem_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/cdrdspymntitems")
public class CdrDsPymntItemEndpoint
{

   @Inject
   private CdrDsPymntItemEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public CdrDsPymntItem create(CdrDsPymntItem entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id}")
   public Response deleteById(@PathParam("id") String id)
   {
      CdrDsPymntItem deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public CdrDsPymntItem update(CdrDsPymntItem entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      CdrDsPymntItem found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public CdrDsPymntItemSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<CdrDsPymntItem> resultList = ejb.listAll(start, max);
      CdrDsPymntItemSearchInput searchInput = new CdrDsPymntItemSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new CdrDsPymntItemSearchResult((long) resultList.size(),
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
   public CdrDsPymntItemSearchResult findBy(CdrDsPymntItemSearchInput searchInput)
   {
      SingularAttribute<CdrDsPymntItem, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<CdrDsPymntItem> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new CdrDsPymntItemSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(CdrDsPymntItemSearchInput searchInput)
   {
      SingularAttribute<CdrDsPymntItem, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public CdrDsPymntItemSearchResult findByLike(CdrDsPymntItemSearchInput searchInput)
   {
      SingularAttribute<CdrDsPymntItem, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<CdrDsPymntItem> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new CdrDsPymntItemSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(CdrDsPymntItemSearchInput searchInput)
   {
      SingularAttribute<CdrDsPymntItem, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<CdrDsPymntItem, ?>[] readSeachAttributes(
         CdrDsPymntItemSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<CdrDsPymntItem, ?>> result = new ArrayList<SingularAttribute<CdrDsPymntItem, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = CdrDsPymntItem_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<CdrDsPymntItem, ?>) field.get(null));
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

   

   private CdrDsPymntItem detach(CdrDsPymntItem entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<CdrDsPymntItem> detach(List<CdrDsPymntItem> list)
   {
      if (list == null)
         return list;
      List<CdrDsPymntItem> result = new ArrayList<CdrDsPymntItem>();
      for (CdrDsPymntItem entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private CdrDsPymntItemSearchInput detach(CdrDsPymntItemSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}