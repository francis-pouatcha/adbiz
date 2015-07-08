package org.adorsys.adstock.rest;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
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

import org.adorsys.adstock.jpa.StkSection;
import org.adorsys.adstock.jpa.StkSectionSearchInput;
import org.adorsys.adstock.jpa.StkSectionSearchResult;
import org.adorsys.adstock.jpa.StkSection_;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/stksections")
public class StkSectionEndpoint
{

   @Inject
   private StkSectionEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public StkSection create(StkSection entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id}")
   public Response deleteById(@PathParam("id") String id)
   {
      StkSection deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public StkSection update(StkSection entity)
   {
      return detach(ejb.update(entity));
   }

   @GET	
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      StkSection found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }
   
   @GET
   @Path("/{identif}")
   @Produces({ "application/json", "application/xml" })
   public Response findByIdentif(@PathParam("identif") String identif)
   {
      StkSection found = ejb.findByIdentif(identif, new Date());
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }


   @GET
   @Produces({ "application/json", "application/xml" })
   public StkSectionSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<StkSection> resultList = ejb.listAll(start, max);
      StkSectionSearchInput searchInput = new StkSectionSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new StkSectionSearchResult((long) resultList.size(),
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
   public StkSectionSearchResult findBy(StkSectionSearchInput searchInput)
   {
      SingularAttribute<StkSection, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<StkSection> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new StkSectionSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(StkSectionSearchInput searchInput)
   {
      SingularAttribute<StkSection, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public StkSectionSearchResult findByLike(StkSectionSearchInput searchInput)
   {
      SingularAttribute<StkSection, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<StkSection> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new StkSectionSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/findCustom")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public StkSectionSearchResult findCustom(StkSectionSearchInput searchInput)
   {
	   if(StringUtils.isNotBlank(searchInput.getCodeOrName())){
		   String codeOrName = searchInput.getCodeOrName();
		   searchInput.getFieldNames().clear();
		   if (StringUtils.isNumeric(searchInput.getCodeOrName())){
			   searchInput.getFieldNames().add("sectionCode");
			   searchInput.getEntity().setSectionCode(codeOrName);
		   } else {
			   searchInput.getFieldNames().add("name");
			   searchInput.getEntity().setName(codeOrName);
		   }
	   }
	   return findByLike(searchInput);
   }
   
   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(StkSectionSearchInput searchInput)
   {
      SingularAttribute<StkSection, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<StkSection, ?>[] readSeachAttributes(
         StkSectionSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<StkSection, ?>> result = new ArrayList<SingularAttribute<StkSection, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = StkSection_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<StkSection, ?>) field.get(null));
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

   

   private StkSection detach(StkSection entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<StkSection> detach(List<StkSection> list)
   {
      if (list == null)
         return list;
      List<StkSection> result = new ArrayList<StkSection>();
      for (StkSection entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private StkSectionSearchInput detach(StkSectionSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}