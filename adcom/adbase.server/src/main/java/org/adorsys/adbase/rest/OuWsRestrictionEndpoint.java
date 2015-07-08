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

import org.adorsys.adbase.jpa.OuWsRestriction;
import org.adorsys.adbase.jpa.OuWsRestriction_;
import org.adorsys.adbase.jpa.OuWsRestrictionSearchInput;
import org.adorsys.adbase.jpa.OuWsRestrictionSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/ouwsrestrictions")
public class OuWsRestrictionEndpoint
{

   @Inject
   private OuWsRestrictionEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public OuWsRestriction create(OuWsRestriction entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id}")
   public Response deleteById(@PathParam("id") String id)
   {
      OuWsRestriction deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public OuWsRestriction update(OuWsRestriction entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      OuWsRestriction found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public OuWsRestrictionSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<OuWsRestriction> resultList = ejb.listAll(start, max);
      OuWsRestrictionSearchInput searchInput = new OuWsRestrictionSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new OuWsRestrictionSearchResult((long) resultList.size(),
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
   public OuWsRestrictionSearchResult findBy(OuWsRestrictionSearchInput searchInput)
   {
      SingularAttribute<OuWsRestriction, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<OuWsRestriction> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new OuWsRestrictionSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(OuWsRestrictionSearchInput searchInput)
   {
      SingularAttribute<OuWsRestriction, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public OuWsRestrictionSearchResult findByLike(OuWsRestrictionSearchInput searchInput)
   {
      SingularAttribute<OuWsRestriction, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<OuWsRestriction> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new OuWsRestrictionSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(OuWsRestrictionSearchInput searchInput)
   {
      SingularAttribute<OuWsRestriction, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<OuWsRestriction, ?>[] readSeachAttributes(
         OuWsRestrictionSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<OuWsRestriction, ?>> result = new ArrayList<SingularAttribute<OuWsRestriction, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = OuWsRestriction_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<OuWsRestriction, ?>) field.get(null));
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

   

   private OuWsRestriction detach(OuWsRestriction entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<OuWsRestriction> detach(List<OuWsRestriction> list)
   {
      if (list == null)
         return list;
      List<OuWsRestriction> result = new ArrayList<OuWsRestriction>();
      for (OuWsRestriction entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private OuWsRestrictionSearchInput detach(OuWsRestrictionSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}