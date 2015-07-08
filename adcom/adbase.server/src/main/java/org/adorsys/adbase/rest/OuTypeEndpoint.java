package org.adorsys.adbase.rest;

import java.lang.reflect.Field;
import java.util.ArrayList;
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

import org.adorsys.adbase.jpa.OuType;
import org.adorsys.adbase.jpa.OuType_;
import org.adorsys.adbase.jpa.OuTypeSearchInput;
import org.adorsys.adbase.jpa.OuTypeSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/outypes")
public class OuTypeEndpoint
{

   @Inject
   private OuTypeEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public OuType create(OuType entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id}")
   public Response deleteById(@PathParam("id") String id)
   {
      OuType deleted = ejb.deleteCustomById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public OuType update(OuType entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      OuType found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public OuTypeSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<OuType> resultList = ejb.listAll(start, max);
      OuTypeSearchInput searchInput = new OuTypeSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new OuTypeSearchResult((long) resultList.size(),
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
   public OuTypeSearchResult findBy(OuTypeSearchInput searchInput)
   {
      SingularAttribute<OuType, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<OuType> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new OuTypeSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(OuTypeSearchInput searchInput)
   {
      SingularAttribute<OuType, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public OuTypeSearchResult findByLike(OuTypeSearchInput searchInput)
   {
      SingularAttribute<OuType, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<OuType> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new OuTypeSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(OuTypeSearchInput searchInput)
   {
      SingularAttribute<OuType, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }


   @GET
   @Path("/findActifsFromNow")
   @Produces({ "application/json", "application/xml" })
   public OuTypeSearchResult findActifsFrom(HttpServletRequest httpServReq)
   {
      List<OuType> actifsFromNow = ejb.findActifsFromNow();
      Long size = ejb.countActifsFromNow();
      return new OuTypeSearchResult(size,
            detach(actifsFromNow), detach(new OuTypeSearchInput()));
   }
   
   @POST
   @Path("/search")
   @Produces({"application/json","application/xml"})
   public OuTypeSearchResult search(OuTypeSearchInput ouTypeSearchInput){
	   OuType entity = ouTypeSearchInput.getEntity();
	   List<OuType> resultList = ejb.searchQuery(entity.getParentType(), entity.getTypeName(), entity.getValidFrom(), ouTypeSearchInput.getStart(),ouTypeSearchInput.getMax());
	   OuTypeSearchResult searchResult = new OuTypeSearchResult(Long.valueOf(resultList.size()), detach(resultList), ouTypeSearchInput);
	   return searchResult;
   }
   @SuppressWarnings("unchecked")
   private SingularAttribute<OuType, ?>[] readSeachAttributes(
         OuTypeSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<OuType, ?>> result = new ArrayList<SingularAttribute<OuType, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = OuType_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<OuType, ?>) field.get(null));
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

   

   private OuType detach(OuType entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<OuType> detach(List<OuType> list)
   {
      if (list == null)
         return list;
      List<OuType> result = new ArrayList<OuType>();
      for (OuType entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private OuTypeSearchInput detach(OuTypeSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}