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

import org.adorsys.adbase.jpa.PricingCurrRate;
import org.adorsys.adbase.jpa.PricingCurrRate_;
import org.adorsys.adbase.jpa.PricingCurrRateSearchInput;
import org.adorsys.adbase.jpa.PricingCurrRateSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/pricingcurrrates")
public class PricingCurrRateEndpoint
{

   @Inject
   private PricingCurrRateEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public PricingCurrRate create(PricingCurrRate entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id}")
   public Response deleteById(@PathParam("id") String id)
   {
      PricingCurrRate deleted = ejb.deleteCustom(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public PricingCurrRate update(PricingCurrRate entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      PricingCurrRate found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public PricingCurrRateSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<PricingCurrRate> resultList = ejb.listAll(start, max);
      PricingCurrRateSearchInput searchInput = new PricingCurrRateSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new PricingCurrRateSearchResult((long) resultList.size(),
            detach(resultList), detach(searchInput));
   }

   @GET
   @Path("/count")
   public Long count()
   {
      return ejb.count();
   }
   
   @POST
   @Path("/doFind")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public PricingCurrRateSearchResult doFind(PricingCurrRateSearchInput searchInput)
   {
	   PricingCurrRateSearchResult searchResult = ejb.doFind(searchInput);
      return searchResult ;
   }

   @POST
   @Path("/findBy")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public PricingCurrRateSearchResult findBy(PricingCurrRateSearchInput searchInput)
   {
      SingularAttribute<PricingCurrRate, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<PricingCurrRate> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new PricingCurrRateSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(PricingCurrRateSearchInput searchInput)
   {
      SingularAttribute<PricingCurrRate, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public PricingCurrRateSearchResult findByLike(PricingCurrRateSearchInput searchInput)
   {
      SingularAttribute<PricingCurrRate, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<PricingCurrRate> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new PricingCurrRateSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(PricingCurrRateSearchInput searchInput)
   {
      SingularAttribute<PricingCurrRate, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<PricingCurrRate, ?>[] readSeachAttributes(
         PricingCurrRateSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<PricingCurrRate, ?>> result = new ArrayList<SingularAttribute<PricingCurrRate, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = PricingCurrRate_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<PricingCurrRate, ?>) field.get(null));
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

   

   private PricingCurrRate detach(PricingCurrRate entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<PricingCurrRate> detach(List<PricingCurrRate> list)
   {
      if (list == null)
         return list;
      List<PricingCurrRate> result = new ArrayList<PricingCurrRate>();
      for (PricingCurrRate entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private PricingCurrRateSearchInput detach(PricingCurrRateSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}