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

import org.adorsys.adbase.jpa.WorkspaceRestriction;
import org.adorsys.adbase.jpa.WorkspaceRestriction_;
import org.adorsys.adbase.jpa.WorkspaceRestrictionSearchInput;
import org.adorsys.adbase.jpa.WorkspaceRestrictionSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/workspacerestrictions")
public class WorkspaceRestrictionEndpoint
{

   @Inject
   private WorkspaceRestrictionEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public WorkspaceRestriction create(WorkspaceRestriction entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id}")
   public Response deleteById(@PathParam("id") String id)
   {
      WorkspaceRestriction deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public WorkspaceRestriction update(WorkspaceRestriction entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      WorkspaceRestriction found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public WorkspaceRestrictionSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<WorkspaceRestriction> resultList = ejb.listAll(start, max);
      WorkspaceRestrictionSearchInput searchInput = new WorkspaceRestrictionSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new WorkspaceRestrictionSearchResult((long) resultList.size(),
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
   public WorkspaceRestrictionSearchResult findBy(WorkspaceRestrictionSearchInput searchInput)
   {
      SingularAttribute<WorkspaceRestriction, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<WorkspaceRestriction> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new WorkspaceRestrictionSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(WorkspaceRestrictionSearchInput searchInput)
   {
      SingularAttribute<WorkspaceRestriction, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public WorkspaceRestrictionSearchResult findByLike(WorkspaceRestrictionSearchInput searchInput)
   {
      SingularAttribute<WorkspaceRestriction, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<WorkspaceRestriction> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new WorkspaceRestrictionSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(WorkspaceRestrictionSearchInput searchInput)
   {
      SingularAttribute<WorkspaceRestriction, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<WorkspaceRestriction, ?>[] readSeachAttributes(
         WorkspaceRestrictionSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<WorkspaceRestriction, ?>> result = new ArrayList<SingularAttribute<WorkspaceRestriction, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = WorkspaceRestriction_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<WorkspaceRestriction, ?>) field.get(null));
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

   

   private WorkspaceRestriction detach(WorkspaceRestriction entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<WorkspaceRestriction> detach(List<WorkspaceRestriction> list)
   {
      if (list == null)
         return list;
      List<WorkspaceRestriction> result = new ArrayList<WorkspaceRestriction>();
      for (WorkspaceRestriction entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private WorkspaceRestrictionSearchInput detach(WorkspaceRestrictionSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}