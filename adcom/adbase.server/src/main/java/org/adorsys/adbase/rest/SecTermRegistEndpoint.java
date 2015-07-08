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

import org.adorsys.adbase.jpa.SecTermRegist;
import org.adorsys.adbase.jpa.SecTermRegist_;
import org.adorsys.adbase.jpa.SecTermRegistSearchInput;
import org.adorsys.adbase.jpa.SecTermRegistSearchResult;
/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/secterminalregist") 
public class SecTermRegistEndpoint
{

   @Inject
   private SecTermRegistEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public SecTermRegist create(SecTermRegist entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response deleteById(@PathParam("id") String id)
   {
      SecTermRegist deleted = ejb.deleteById(id);
     
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public SecTermRegist update(SecTermRegist entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
	   SecTermRegist found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public SecTermRegistSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<SecTermRegist> resultList = ejb.listAll(start, max);
      SecTermRegistSearchInput searchInput = new SecTermRegistSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new SecTermRegistSearchResult((long) resultList.size(),
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
   public SecTermRegistSearchResult findBy(SecTermRegistSearchInput searchInput)
   {
      SingularAttribute<SecTermRegist, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<SecTermRegist> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new SecTermRegistSearchResult(count, detach(resultList),
            detach(searchInput));
   }
   
   @POST
   @Path("/findAllActiveTerminals")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public SecTermRegistSearchResult findAllActiveTerminals(SecTermRegistSearchInput searchInput)
   {
	   SecTermRegistSearchResult searchResult = ejb.findAllActiveTerminals(searchInput);
      return searchResult ;
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(SecTermRegistSearchInput searchInput)
   {
      SingularAttribute<SecTermRegist, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public SecTermRegistSearchResult findByLike(SecTermRegistSearchInput searchInput)
   {
      SingularAttribute<SecTermRegist, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<SecTermRegist> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new SecTermRegistSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(SecTermRegistSearchInput searchInput)
   {
      SingularAttribute<SecTermRegist, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<SecTermRegist, ?>[] readSeachAttributes(
         SecTermRegistSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<SecTermRegist, ?>> result = new ArrayList<SingularAttribute<SecTermRegist, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = SecTermRegist_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<SecTermRegist, ?>) field.get(null));
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

   

   private SecTermRegist detach(SecTermRegist entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<SecTermRegist> detach(List<SecTermRegist> list)
   {
      if (list == null)
         return list;
      List<SecTermRegist> result = new ArrayList<SecTermRegist>();
      for (SecTermRegist entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private SecTermRegistSearchInput detach(SecTermRegistSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}