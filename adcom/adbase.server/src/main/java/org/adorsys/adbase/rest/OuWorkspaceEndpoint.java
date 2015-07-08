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

import org.adorsys.adbase.dto.OuWorkspaceDTOHolder;
import org.adorsys.adbase.dto.OuWorkspaceDtoService;
import org.adorsys.adbase.exception.NotFoundOrNotActifEntityException;
import org.adorsys.adbase.jpa.OuWorkspace;
import org.adorsys.adbase.jpa.OuWorkspace_;
import org.adorsys.adbase.jpa.OuWorkspaceSearchInput;
import org.adorsys.adbase.jpa.OuWorkspaceSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/ouworkspaces")
public class OuWorkspaceEndpoint
{

   @Inject
   private OuWorkspaceEJB ejb;

   @Inject
   private OuWorkspaceDtoService dtoService;
   
   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public OuWorkspace create(OuWorkspace entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id}")
   public Response deleteById(@PathParam("id") String id)
   {
      OuWorkspace deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public OuWorkspace update(OuWorkspace entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      OuWorkspace found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public OuWorkspaceSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<OuWorkspace> resultList = ejb.listAll(start, max);
      OuWorkspaceSearchInput searchInput = new OuWorkspaceSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new OuWorkspaceSearchResult((long) resultList.size(),
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
   public OuWorkspaceSearchResult findBy(OuWorkspaceSearchInput searchInput)
   {
      SingularAttribute<OuWorkspace, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<OuWorkspace> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new OuWorkspaceSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(OuWorkspaceSearchInput searchInput)
   {
      SingularAttribute<OuWorkspace, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public OuWorkspaceSearchResult findByLike(OuWorkspaceSearchInput searchInput)
   {
      SingularAttribute<OuWorkspace, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<OuWorkspace> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new OuWorkspaceSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(OuWorkspaceSearchInput searchInput)
   {
      SingularAttribute<OuWorkspace, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }


   @GET
   @Path("/findActivesOuWorkspaces")
   @Produces({ "application/json", "application/xml" })
   public List<OuWorkspace> findActivesOuWorkspaces()
   {
	   List<OuWorkspace> ouWorkspaces = ejb.findActivesOuWorkspaces();
	   return ouWorkspaces;
   }
   

   @GET
   @Path("/findActivesOuWorkspaces/{targetOuIdentif}")
   @Produces({ "application/json", "application/xml" })
   public OuWorkspaceDTOHolder findOuWorkspaceDtoForTargetOu(@PathParam("targetOuIdentif")String targetOuIdentif) throws NotFoundOrNotActifEntityException {
	   OuWorkspaceDTOHolder dtoHolder = dtoService.createDtos(targetOuIdentif);
	   return dtoHolder;
   }


   @POST
   @Path("/assignWorkspaces")
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public OuWorkspaceDTOHolder assignWorkspaces(OuWorkspaceDTOHolder dtoHolder) throws NotFoundOrNotActifEntityException {
	   dtoService.assignWorkspaces(dtoHolder);
	   OuWorkspaceDTOHolder dtoHolder2 = dtoService.createDtos(dtoHolder.getTargetOu().getIdentif());
	   return dtoHolder2;
   }
   
   @SuppressWarnings("unchecked")
   private SingularAttribute<OuWorkspace, ?>[] readSeachAttributes(
         OuWorkspaceSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<OuWorkspace, ?>> result = new ArrayList<SingularAttribute<OuWorkspace, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = OuWorkspace_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<OuWorkspace, ?>) field.get(null));
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

   

   private OuWorkspace detach(OuWorkspace entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<OuWorkspace> detach(List<OuWorkspace> list)
   {
      if (list == null)
         return list;
      List<OuWorkspace> result = new ArrayList<OuWorkspace>();
      for (OuWorkspace entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private OuWorkspaceSearchInput detach(OuWorkspaceSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}