package org.adorsys.adbase.rest;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
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

import org.adorsys.adbase.jpa.SecTerminal;
import org.adorsys.adbase.jpa.SecTerminal_;
import org.adorsys.adbase.jpa.SecTerminalSearchInput;
import org.adorsys.adbase.jpa.SecTerminalSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/secterminals") 
public class SecTerminalEndpoint
{

   @Inject
   private SecTerminalEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public SecTerminal create(SecTerminal entity)
   {
      return detach(ejb.create(entity));
   }

   @PUT
   @Path("/{id}")
   public Response deleteById(@PathParam("id") String id)
   {
      SecTerminal deleted = ejb.deleteCustom(id);
     
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public SecTerminal update(SecTerminal entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      SecTerminal found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public SecTerminalSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<SecTerminal> resultList = ejb.listAll(start, max);
      SecTerminalSearchInput searchInput = new SecTerminalSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new SecTerminalSearchResult((long) resultList.size(),
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
   public SecTerminalSearchResult findBy(SecTerminalSearchInput searchInput)
   {
      SingularAttribute<SecTerminal, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<SecTerminal> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new SecTerminalSearchResult(count, detach(resultList),
            detach(searchInput));
   }
   
   @POST
   @Path("/findAllActiveTerminals")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public SecTerminalSearchResult findAllActiveTerminals(SecTerminalSearchInput searchInput)
   {
	   SecTerminalSearchResult searchResult = ejb.findAllActiveTerminals(searchInput);
      return searchResult ;
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(SecTerminalSearchInput searchInput)
   {
      SingularAttribute<SecTerminal, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public SecTerminalSearchResult findByLike(SecTerminalSearchInput searchInput)
   {
      SingularAttribute<SecTerminal, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<SecTerminal> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new SecTerminalSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(SecTerminalSearchInput searchInput)
   {
      SingularAttribute<SecTerminal, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<SecTerminal, ?>[] readSeachAttributes(
         SecTerminalSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<SecTerminal, ?>> result = new ArrayList<SingularAttribute<SecTerminal, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = SecTerminal_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<SecTerminal, ?>) field.get(null));
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

   private static final List<String> emptyList = Collections.emptyList();

   private SecTerminal detach(SecTerminal entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<SecTerminal> detach(List<SecTerminal> list)
   {
      if (list == null)
         return list;
      List<SecTerminal> result = new ArrayList<SecTerminal>();
      for (SecTerminal entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private SecTerminalSearchInput detach(SecTerminalSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}