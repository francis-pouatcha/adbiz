package org.adorsys.adbase.rest;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import javax.servlet.http.HttpServletRequest;
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

import org.adorsys.adbase.jpa.Country;
import org.adorsys.adbase.jpa.Country_;
import org.adorsys.adbase.jpa.CountrySearchInput;
import org.adorsys.adbase.jpa.CountrySearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/countrys")
public class CountryEndpoint
{

   @Inject
   private CountryEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public Country create(Country entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id}")
   public Response deleteById(@PathParam("id") String id)
   {
      Country deleted = ejb.deleteCustomById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public Country update(Country entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      Country found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public CountrySearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<Country> resultList = ejb.listAll(start, max);
      CountrySearchInput searchInput = new CountrySearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new CountrySearchResult((long) resultList.size(),
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
   public CountrySearchResult findBy(CountrySearchInput searchInput)
   {
      SingularAttribute<Country, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<Country> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new CountrySearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(CountrySearchInput searchInput)
   {
      SingularAttribute<Country, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public CountrySearchResult findByLike(CountrySearchInput searchInput)
   {
      SingularAttribute<Country, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<Country> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new CountrySearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(CountrySearchInput searchInput)
   {
      SingularAttribute<Country, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @GET
   @Path("/findActifsFromNow")
   @Produces({ "application/json", "application/xml" })
   public Response findActifsFromNow(HttpServletRequest request)
   {
	   List<Country> countrys = ejb.findActicfCountrys(new Date());
	   return Response.ok(countrys).build();
   }

   @GET
   @Path("/findByIdentif/{identif}")
   @Produces({ "application/json", "application/xml" })
   public Response findByIdIdentif(@PathParam("identif") String identif)
   {
      Country found = ejb.findByIdentif(identif, new Date());
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @POST
   @Path("/searchCountrys")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public CountrySearchResult searchCountrys(CountrySearchInput searchInput)
   {
     Country entity = searchInput.getEntity();
     String name = entity.getName();
     String iso3 = entity.getIso3();
     String dialCode = entity.getDialCode();
     String langsIso2 = entity.getLangsIso2();
     String currsIso2 = entity.getCurrsIso2();
     Date from = new Date();
     int start = searchInput.getStart();
     int max = searchInput.getMax();
     
     List<Country> countrys = ejb.searchCountrys(iso3, name, dialCode, langsIso2, currsIso2, from, start, max);
     Long count = ejb.countCountrys(iso3, name, dialCode, langsIso2, currsIso2, from, start, max);
      return new CountrySearchResult(count, detach(countrys),
            detach(searchInput));
   }
   @SuppressWarnings("unchecked")
   private SingularAttribute<Country, ?>[] readSeachAttributes(
         CountrySearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<Country, ?>> result = new ArrayList<SingularAttribute<Country, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = Country_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<Country, ?>) field.get(null));
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

   private Country detach(Country entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<Country> detach(List<Country> list)
   {
      if (list == null)
         return list;
      List<Country> result = new ArrayList<Country>();
      for (Country entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private CountrySearchInput detach(CountrySearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}