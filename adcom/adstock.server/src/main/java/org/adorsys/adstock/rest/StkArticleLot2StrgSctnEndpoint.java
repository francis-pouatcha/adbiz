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

import org.adorsys.adstock.jpa.StkArticleLot;
import org.adorsys.adstock.jpa.StkArticleLot2StrgSctn;
import org.adorsys.adstock.jpa.StkArticleLot2StrgSctnSearchInput;
import org.adorsys.adstock.jpa.StkArticleLot2StrgSctnSearchResult;
import org.adorsys.adstock.jpa.StkArticleLot2StrgSctn_;
import org.adorsys.adstock.jpa.StkSection;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/stkarticlelot2strgsctns")
public class StkArticleLot2StrgSctnEndpoint{

   @Inject
   private StkArticleLot2StrgSctnEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public StkArticleLot2StrgSctn create(StkArticleLot2StrgSctn entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id}")
   public Response deleteById(@PathParam("id") String id)
   {
      StkArticleLot2StrgSctn deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public StkArticleLot2StrgSctn update(StkArticleLot2StrgSctn entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      StkArticleLot2StrgSctn found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public StkArticleLot2StrgSctnSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<StkArticleLot2StrgSctn> resultList = ejb.listAll(start, max);
      StkArticleLot2StrgSctnSearchInput searchInput = new StkArticleLot2StrgSctnSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new StkArticleLot2StrgSctnSearchResult((long) resultList.size(),
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
   public StkArticleLot2StrgSctnSearchResult findBy(StkArticleLot2StrgSctnSearchInput searchInput)
   {
      SingularAttribute<StkArticleLot2StrgSctn, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<StkArticleLot2StrgSctn> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new StkArticleLot2StrgSctnSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(StkArticleLot2StrgSctnSearchInput searchInput)
   {
      SingularAttribute<StkArticleLot2StrgSctn, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public StkArticleLot2StrgSctnSearchResult findByLike(StkArticleLot2StrgSctnSearchInput searchInput)
   {
      SingularAttribute<StkArticleLot2StrgSctn, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<StkArticleLot2StrgSctn> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new StkArticleLot2StrgSctnSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(StkArticleLot2StrgSctnSearchInput searchInput)
   {
      SingularAttribute<StkArticleLot2StrgSctn, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<StkArticleLot2StrgSctn, ?>[] readSeachAttributes(
         StkArticleLot2StrgSctnSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<StkArticleLot2StrgSctn, ?>> result = new ArrayList<SingularAttribute<StkArticleLot2StrgSctn, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = StkArticleLot2StrgSctn_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<StkArticleLot2StrgSctn, ?>) field.get(null));
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

   
   @Inject
   private StkSectionEJB stkSectionEJB;
   @Inject
   private StkArticleLotEJB articleLotEJB;
   @Inject
   private StkArticleLotDetachHelper articleLotDetachHelper;
   private StkArticleLot2StrgSctn detach(StkArticleLot2StrgSctn entity)
   {
      if (entity == null)
         return null;
      
      String strgSection = entity.getStrgSection();
      if(StringUtils.isNotBlank(strgSection)){
    	  StkSection stkSection = stkSectionEJB.findByIdentif(strgSection, new Date());
    	  if(stkSection!=null)entity.setStkSection(stkSection);
      }
      StkArticleLot stkArticleLot = articleLotEJB.findByIdentif(StkArticleLot.toId(entity.getLotPic()));
      if(articleLotEJB!=null)stkArticleLot = articleLotDetachHelper.detach(stkArticleLot);
      entity.setSectionArticleLot(stkArticleLot);
      return entity;
   }

   private List<StkArticleLot2StrgSctn> detach(List<StkArticleLot2StrgSctn> list)
   {
      if (list == null)
         return list;
      List<StkArticleLot2StrgSctn> result = new ArrayList<StkArticleLot2StrgSctn>();
      for (StkArticleLot2StrgSctn entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }
   
   private StkArticleLot2StrgSctnSearchInput detach(StkArticleLot2StrgSctnSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}