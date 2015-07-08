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

import org.adorsys.adcshdwr.jpa.CdrPymnt;
import org.adorsys.adcshdwr.jpa.CdrPymntSearchInput;
import org.adorsys.adcshdwr.jpa.CdrPymntSearchResult;
import org.adorsys.adcshdwr.jpa.CdrPymnt_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/cdrpymnts")
public class CdrPymntEndpoint
{

   @Inject
   private CdrPymntEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public CdrPymnt create(CdrPymnt entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id}")
   public Response deleteById(@PathParam("id") String id)
   {
      CdrPymnt deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public CdrPymnt update(CdrPymnt entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      CdrPymnt found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public CdrPymntSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<CdrPymnt> resultList = ejb.listAll(start, max);
      CdrPymntSearchInput searchInput = new CdrPymntSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new CdrPymntSearchResult((long) resultList.size(),
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
   public CdrPymntSearchResult findBy(CdrPymntSearchInput searchInput)
   {
      SingularAttribute<CdrPymnt, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<CdrPymnt> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new CdrPymntSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(CdrPymntSearchInput searchInput)
   {
      SingularAttribute<CdrPymnt, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public CdrPymntSearchResult findByLike(CdrPymntSearchInput searchInput)
   {
      SingularAttribute<CdrPymnt, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<CdrPymnt> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new CdrPymntSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(CdrPymntSearchInput searchInput)
   {
      SingularAttribute<CdrPymnt, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<CdrPymnt, ?>[] readSeachAttributes(
         CdrPymntSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<CdrPymnt, ?>> result = new ArrayList<SingularAttribute<CdrPymnt, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = CdrPymnt_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<CdrPymnt, ?>) field.get(null));
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

   

   private CdrPymnt detach(CdrPymnt entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<CdrPymnt> detach(List<CdrPymnt> list)
   {
      if (list == null)
         return list;
      List<CdrPymnt> result = new ArrayList<CdrPymnt>();
      for (CdrPymnt entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private CdrPymntSearchInput detach(CdrPymntSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}