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

import org.adorsys.adstock.jpa.StkMvnt;
import org.adorsys.adstock.jpa.StkMvntSearchInput;
import org.adorsys.adstock.jpa.StkMvntSearchResult;
import org.adorsys.adstock.jpa.StkMvnt_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/stkmvnts")
public class StkMvntEndpoint
{

   @Inject
   private StkMvntEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public StkMvnt create(StkMvnt entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id}")
   public Response deleteById(@PathParam("id") String id)
   {
      StkMvnt deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public StkMvnt update(StkMvnt entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      StkMvnt found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public StkMvntSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<StkMvnt> resultList = ejb.listAll(start, max);
      StkMvntSearchInput searchInput = new StkMvntSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new StkMvntSearchResult((long) resultList.size(),
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
   public StkMvntSearchResult findBy(StkMvntSearchInput searchInput)
   {
      SingularAttribute<StkMvnt, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<StkMvnt> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new StkMvntSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(StkMvntSearchInput searchInput)
   {
      SingularAttribute<StkMvnt, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public StkMvntSearchResult findByLike(StkMvntSearchInput searchInput)
   {
      SingularAttribute<StkMvnt, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<StkMvnt> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new StkMvntSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }
   
   @POST
   @Path("/findCustom")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public StkMvntSearchResult findCustom(StkMvntSearchInput searchInput)
   {
      SingularAttribute<StkMvnt, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<StkMvnt> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new StkMvntSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(StkMvntSearchInput searchInput)
   {
      SingularAttribute<StkMvnt, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<StkMvnt, ?>[] readSeachAttributes(
         StkMvntSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<StkMvnt, ?>> result = new ArrayList<SingularAttribute<StkMvnt, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = StkMvnt_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<StkMvnt, ?>) field.get(null));
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

   

   private StkMvnt detach(StkMvnt entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<StkMvnt> detach(List<StkMvnt> list)
   {
      if (list == null)
         return list;
      List<StkMvnt> result = new ArrayList<StkMvnt>();
      for (StkMvnt entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private StkMvntSearchInput detach(StkMvntSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}