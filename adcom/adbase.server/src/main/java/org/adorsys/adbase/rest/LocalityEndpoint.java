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
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.adorsys.adbase.jpa.Locality;
import org.adorsys.adbase.jpa.Locality_;
import org.adorsys.adbase.jpa.LocalitySearchInput;
import org.adorsys.adbase.jpa.LocalitySearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/localitys")
public class LocalityEndpoint
{

   @Inject
   private LocalityEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public Locality create(Locality entity)
   {
      return detach(ejb.create(entity));
   }

   @PUT
   @Path("/{id}")
   public Response deleteById(@PathParam("id") String id)
   {
      Locality deleted = ejb.deleteCustomById(id);
     
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(deleted)).build();
   }
   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public Locality update(Locality entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      Locality found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public LocalitySearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<Locality> resultList = ejb.listAll(start, max);
      LocalitySearchInput searchInput = new LocalitySearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new LocalitySearchResult((long) resultList.size(),
            detach(resultList), detach(searchInput));
   }

   @POST
   @Path("/findAllActive")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public LocalitySearchResult findAllActiveLocality(LocalitySearchInput searchInput)
   {
	   LocalitySearchResult searchResult = ejb.findAllActiveLocality(searchInput);
      return searchResult ;
   }
   
   @POST
   @Path("/doFind")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public LocalitySearchResult doFind(LocalitySearchInput searchInput)
   {
	   LocalitySearchResult searchResult = ejb.doFind(searchInput);
      return searchResult ;
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
   public LocalitySearchResult findBy(LocalitySearchInput searchInput)
   {
      SingularAttribute<Locality, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<Locality> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new LocalitySearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(LocalitySearchInput searchInput)
   {
      SingularAttribute<Locality, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public LocalitySearchResult findByLike(LocalitySearchInput searchInput)
   {
      SingularAttribute<Locality, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<Locality> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new LocalitySearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(LocalitySearchInput searchInput)
   {
      SingularAttribute<Locality, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<Locality, ?>[] readSeachAttributes(
         LocalitySearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<Locality, ?>> result = new ArrayList<SingularAttribute<Locality, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = Locality_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<Locality, ?>) field.get(null));
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

   

   private Locality detach(Locality entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<Locality> detach(List<Locality> list)
   {
      if (list == null)
         return list;
      List<Locality> result = new ArrayList<Locality>();
      for (Locality entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private LocalitySearchInput detach(LocalitySearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}