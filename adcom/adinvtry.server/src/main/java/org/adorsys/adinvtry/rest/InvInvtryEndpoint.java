package org.adorsys.adinvtry.rest;

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

import org.adorsys.adinvtry.jpa.InvInvtry;
import org.adorsys.adinvtry.jpa.InvInvtry_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/invinvtrys")
public class InvInvtryEndpoint
{

   @Inject
   private InvInvtryEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public InvInvtry create(InvInvtry entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id}")
   public Response deleteById(@PathParam("id") String id)
   {
      InvInvtry deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public InvInvtry update(InvInvtry entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      InvInvtry found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }
   
   @GET
   @Path("/findByIdentif/{identif}")
   @Produces({ "application/json", "application/xml" })
   public Response findByIdentif(@PathParam("identif") String identif)
   {
	   InvInvtry found = ejb.findByIdentif(identif);
	   if (found == null)
		   return Response.status(Status.NOT_FOUND).build();
	   return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public InvInvtrySearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<InvInvtry> resultList = ejb.listAll(start, max);
      InvInvtrySearchInput searchInput = new InvInvtrySearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new InvInvtrySearchResult((long) resultList.size(),
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
   public InvInvtrySearchResult findBy(InvInvtrySearchInput searchInput)
   {
      SingularAttribute<InvInvtry, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<InvInvtry> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new InvInvtrySearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(InvInvtrySearchInput searchInput)
   {
      SingularAttribute<InvInvtry, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public InvInvtrySearchResult findByLike(InvInvtrySearchInput searchInput)
   {
      SingularAttribute<InvInvtry, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<InvInvtry> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new InvInvtrySearchResult(countLike, detach(resultList),
            detach(searchInput));
   }
   
   @POST
   @Path("/findCustom")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public InvInvtrySearchResult findCustom(InvInvtrySearchInput searchInput)
   {
	   if(searchInput.noSpecialParams()) return findByLike(searchInput);
	   
	   Long count = ejb.countCustom(searchInput);
	   List<InvInvtry> results = ejb.findCustom(searchInput);
	   return new InvInvtrySearchResult(count, detach(results),
	            detach(searchInput));
   }


   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(InvInvtrySearchInput searchInput)
   {
      SingularAttribute<InvInvtry, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<InvInvtry, ?>[] readSeachAttributes(
         InvInvtrySearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<InvInvtry, ?>> result = new ArrayList<SingularAttribute<InvInvtry, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = InvInvtry_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<InvInvtry, ?>) field.get(null));
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

   

   private InvInvtry detach(InvInvtry entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<InvInvtry> detach(List<InvInvtry> list)
   {
      if (list == null)
         return list;
      List<InvInvtry> result = new ArrayList<InvInvtry>();
      for (InvInvtry entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private InvInvtrySearchInput detach(InvInvtrySearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}