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

import org.adorsys.adbase.dto.LoginWorkspaceDto;
import org.adorsys.adbase.jpa.UserWorkspace;
import org.adorsys.adbase.jpa.UserWorkspace_;
import org.adorsys.adbase.jpa.UserWorkspaceSearchInput;
import org.adorsys.adbase.jpa.UserWorkspaceSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/userworkspaces")
public class UserWorkspaceEndpoint
{

   @Inject
   private UserWorkspaceEJB ejb;
   
   @Inject
   private LoginWsDtoService loginWsDtoService;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public UserWorkspace create(UserWorkspace entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id}")
   public Response deleteById(@PathParam("id") String id)
   {
      UserWorkspace deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public UserWorkspace update(UserWorkspace entity)
   {
      return detach(ejb.update(entity));
   }
   
   @GET
   @Path("/findUserWorkspaces")
   @Produces({ "application/json", "application/xml" })
   public List<UserWorkspace> findUserWorkspaces(){
	   return ejb.findUserWorkspaces();
   }
   
   @GET
   @Path("/loadUserWorkspacesDto/{orgUnit}/{loginName}")
   @Produces({ "application/json", "application/xml" })
   public List<LoginWorkspaceDto> loadUserWorkspaces(@PathParam("orgUnit") String orgUnit, @PathParam("loginName") String loginName){
	   return loginWsDtoService.load(orgUnit,loginName);
   }
   
   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   @Path("/saveUserWorkspace")
   public List<LoginWorkspaceDto> saveUserWorkspace(List<LoginWorkspaceDto> loginWorkspaceDtos)
   {
      return loginWsDtoService.saveUserWorkspace(loginWorkspaceDtos);
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      UserWorkspace found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public UserWorkspaceSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<UserWorkspace> resultList = ejb.listAll(start, max);
      UserWorkspaceSearchInput searchInput = new UserWorkspaceSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new UserWorkspaceSearchResult((long) resultList.size(),
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
   public UserWorkspaceSearchResult findBy(UserWorkspaceSearchInput searchInput)
   {
      SingularAttribute<UserWorkspace, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<UserWorkspace> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new UserWorkspaceSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(UserWorkspaceSearchInput searchInput)
   {
      SingularAttribute<UserWorkspace, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public UserWorkspaceSearchResult findByLike(UserWorkspaceSearchInput searchInput)
   {
      SingularAttribute<UserWorkspace, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<UserWorkspace> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new UserWorkspaceSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(UserWorkspaceSearchInput searchInput)
   {
      SingularAttribute<UserWorkspace, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<UserWorkspace, ?>[] readSeachAttributes(
         UserWorkspaceSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<UserWorkspace, ?>> result = new ArrayList<SingularAttribute<UserWorkspace, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = UserWorkspace_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<UserWorkspace, ?>) field.get(null));
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

   

   private UserWorkspace detach(UserWorkspace entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<UserWorkspace> detach(List<UserWorkspace> list)
   {
      if (list == null)
         return list;
      List<UserWorkspace> result = new ArrayList<UserWorkspace>();
      for (UserWorkspace entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private UserWorkspaceSearchInput detach(UserWorkspaceSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}