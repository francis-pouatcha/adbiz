package org.adorsys.adbnsptnr.rest;

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

import org.adorsys.adbnsptnr.jpa.BpIndivPtnrName;
import org.adorsys.adbnsptnr.jpa.BpIndivPtnrNameSearchInput;
import org.adorsys.adbnsptnr.jpa.BpIndivPtnrNameSearchResult;
import org.adorsys.adbnsptnr.jpa.BpIndivPtnrName_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/bpindivptnrnames")
public class BpIndivPtnrNameEndpoint
{

   @Inject
   private BpIndivPtnrNameEJB ejb;

   @DELETE
   @Path("/{id}")
   public Response deleteById(@PathParam("id") String id)
   {
      BpIndivPtnrName deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public BpIndivPtnrName update(BpIndivPtnrName entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      BpIndivPtnrName found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public BpIndivPtnrNameSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<BpIndivPtnrName> resultList = ejb.listAll(start, max);
      BpIndivPtnrNameSearchInput searchInput = new BpIndivPtnrNameSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new BpIndivPtnrNameSearchResult((long) resultList.size(),
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
   public BpIndivPtnrNameSearchResult findBy(BpIndivPtnrNameSearchInput searchInput)
   {
      SingularAttribute<BpIndivPtnrName, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<BpIndivPtnrName> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new BpIndivPtnrNameSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(BpIndivPtnrNameSearchInput searchInput)
   {
      SingularAttribute<BpIndivPtnrName, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public BpIndivPtnrNameSearchResult findByLike(BpIndivPtnrNameSearchInput searchInput)
   {
      SingularAttribute<BpIndivPtnrName, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<BpIndivPtnrName> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new BpIndivPtnrNameSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(BpIndivPtnrNameSearchInput searchInput)
   {
      SingularAttribute<BpIndivPtnrName, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<BpIndivPtnrName, ?>[] readSeachAttributes(
         BpIndivPtnrNameSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<BpIndivPtnrName, ?>> result = new ArrayList<SingularAttribute<BpIndivPtnrName, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = BpIndivPtnrName_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<BpIndivPtnrName, ?>) field.get(null));
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

   

   private BpIndivPtnrName detach(BpIndivPtnrName entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<BpIndivPtnrName> detach(List<BpIndivPtnrName> list)
   {
      if (list == null)
         return list;
      List<BpIndivPtnrName> result = new ArrayList<BpIndivPtnrName>();
      for (BpIndivPtnrName entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private BpIndivPtnrNameSearchInput detach(BpIndivPtnrNameSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}