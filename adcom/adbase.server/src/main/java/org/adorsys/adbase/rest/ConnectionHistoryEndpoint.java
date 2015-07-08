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

import org.adorsys.adbase.exception.NotFoundOrNotActifEntityException;
import org.adorsys.adbase.jpa.ConnectionHistory;
import org.adorsys.adbase.jpa.ConnectionHistory_;
import org.adorsys.adbase.jpa.ConnectionHistorySearchInput;
import org.adorsys.adbase.jpa.ConnectionHistorySearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/connectionhistorys")
public class ConnectionHistoryEndpoint
{

   @Inject
   private ConnectionHistoryEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public ConnectionHistory create(ConnectionHistory entity)
   {
	   return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id}")
   public Response deleteById(@PathParam("id") String id)
   {
      ConnectionHistory deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public ConnectionHistory update(ConnectionHistory entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      ConnectionHistory found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public ConnectionHistorySearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<ConnectionHistory> resultList = ejb.listAll(start, max);
      ConnectionHistorySearchInput searchInput = new ConnectionHistorySearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new ConnectionHistorySearchResult((long) resultList.size(),
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
   public ConnectionHistorySearchResult findBy(ConnectionHistorySearchInput searchInput)
   {
      SingularAttribute<ConnectionHistory, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<ConnectionHistory> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new ConnectionHistorySearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(ConnectionHistorySearchInput searchInput)
   {
      SingularAttribute<ConnectionHistory, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public ConnectionHistorySearchResult findByLike(ConnectionHistorySearchInput searchInput)
   {
      SingularAttribute<ConnectionHistory, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<ConnectionHistory> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new ConnectionHistorySearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(ConnectionHistorySearchInput searchInput)
   {
      SingularAttribute<ConnectionHistory, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }
   

   @POST
   @Path("/searchConnectionHistorys")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public ConnectionHistorySearchResult searchConnectionHistorys(ConnectionHistorySearchInput searchInput){
	   
	   Long countConnectionHistorys = ejb.countConnectionHistorys(searchInput);
	   List<ConnectionHistory> searchConnectionHistorys = ejb.searchConnectionHistorys(searchInput);
	   ConnectionHistorySearchResult searchResult = new ConnectionHistorySearchResult(countConnectionHistorys, searchConnectionHistorys, searchInput);
	   return searchResult;
   }

   @GET
   @Path("/findByLogin/{login}")
   @Produces({ "application/json", "application/xml" })
   public Response findEntityByIdentif(@PathParam("login") String login) throws NotFoundOrNotActifEntityException
   {
	   ConnectionHistory ConnectionHistory = ejb.findByLoginName(login);
	   
      if (ConnectionHistory == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(ConnectionHistory).build();
   }
   
   @SuppressWarnings("unchecked")
   private SingularAttribute<ConnectionHistory, ?>[] readSeachAttributes(
         ConnectionHistorySearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<ConnectionHistory, ?>> result = new ArrayList<SingularAttribute<ConnectionHistory, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = ConnectionHistory_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<ConnectionHistory, ?>) field.get(null));
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

   

   private ConnectionHistory detach(ConnectionHistory entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<ConnectionHistory> detach(List<ConnectionHistory> list)
   {
      if (list == null)
         return list;
      List<ConnectionHistory> result = new ArrayList<ConnectionHistory>();
      for (ConnectionHistory entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private ConnectionHistorySearchInput detach(ConnectionHistorySearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}