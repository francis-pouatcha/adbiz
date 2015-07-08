package org.adorsys.adbase.rest;

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

import org.adorsys.adbase.jpa.PermEntry;
import org.adorsys.adbase.jpa.PermEntry_;
import org.adorsys.adbase.jpa.PermEntrySearchInput;
import org.adorsys.adbase.jpa.PermEntrySearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/permentrys")
public class PermEntryEndpoint
{

   @Inject
   private PermEntryEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public PermEntry create(PermEntry entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id}")
   public Response deleteById(@PathParam("id") String id)
   {
      PermEntry deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public PermEntry update(PermEntry entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      PermEntry found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public PermEntrySearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<PermEntry> resultList = ejb.listAll(start, max);
      PermEntrySearchInput searchInput = new PermEntrySearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new PermEntrySearchResult((long) resultList.size(),
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
   public PermEntrySearchResult findBy(PermEntrySearchInput searchInput)
   {
      SingularAttribute<PermEntry, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<PermEntry> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new PermEntrySearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(PermEntrySearchInput searchInput)
   {
      SingularAttribute<PermEntry, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public PermEntrySearchResult findByLike(PermEntrySearchInput searchInput)
   {
      SingularAttribute<PermEntry, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<PermEntry> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new PermEntrySearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(PermEntrySearchInput searchInput)
   {
      SingularAttribute<PermEntry, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<PermEntry, ?>[] readSeachAttributes(
         PermEntrySearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<PermEntry, ?>> result = new ArrayList<SingularAttribute<PermEntry, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = PermEntry_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<PermEntry, ?>) field.get(null));
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

   

   private PermEntry detach(PermEntry entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<PermEntry> detach(List<PermEntry> list)
   {
      if (list == null)
         return list;
      List<PermEntry> result = new ArrayList<PermEntry>();
      for (PermEntry entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private PermEntrySearchInput detach(PermEntrySearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}