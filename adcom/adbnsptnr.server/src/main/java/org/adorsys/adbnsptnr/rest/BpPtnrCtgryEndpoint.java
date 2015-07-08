package org.adorsys.adbnsptnr.rest;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import org.adorsys.adbnsptnr.jpa.BpPtnrCtgry;
import org.adorsys.adbnsptnr.jpa.BpPtnrCtgryDtls;
import org.adorsys.adbnsptnr.jpa.BpPtnrCtgryDtls_;
import org.adorsys.adbnsptnr.jpa.BpPtnrCtgrySearchInput;
import org.adorsys.adbnsptnr.jpa.BpPtnrCtgrySearchResult;
import org.adorsys.adbnsptnr.jpa.BpPtnrCtgry_;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/bpptnrctgrys")
public class BpPtnrCtgryEndpoint
{

   @Inject
   private BpPtnrCtgryEJB ejb;
   
   @Inject
   private BpPtnrCtgryDtlsEJB ctgryDtlsEJB;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public BpPtnrCtgry create(BpPtnrCtgry entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{identif}")
   public Response deleteByIdentif(@PathParam("identif") String identif)
   {
      BpPtnrCtgry deleted = ejb.deleteByIdentif(identif);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public BpPtnrCtgry update(BpPtnrCtgry entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      BpPtnrCtgry found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public BpPtnrCtgrySearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<BpPtnrCtgry> resultList = ejb.listAll(start, max);
      BpPtnrCtgrySearchInput searchInput = new BpPtnrCtgrySearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new BpPtnrCtgrySearchResult((long) resultList.size(),
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
   public BpPtnrCtgrySearchResult findBy(BpPtnrCtgrySearchInput searchInput)
   {
      SingularAttribute<BpPtnrCtgry, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<BpPtnrCtgry> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new BpPtnrCtgrySearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(BpPtnrCtgrySearchInput searchInput)
   {
      SingularAttribute<BpPtnrCtgry, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public BpPtnrCtgrySearchResult findByLike(BpPtnrCtgrySearchInput searchInput)
   {
      SingularAttribute<BpPtnrCtgry, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<BpPtnrCtgry> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new BpPtnrCtgrySearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/findCustom")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public BpPtnrCtgrySearchResult findCustom(BpPtnrCtgrySearchInput searchInput)
   {
	  BpPtnrCtgry entity = searchInput.getEntity();
	  if(entity.getCtgryDtls()!=null && StringUtils.isNotBlank(entity.getCtgryDtls().getName())){
		  return findByNameLike(searchInput);
	  }
	  return findByLike(searchInput);
   }

   @SuppressWarnings("unchecked")
   @POST
   @Path("/findByNameLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public BpPtnrCtgrySearchResult findByNameLike(BpPtnrCtgrySearchInput searchInput)
   {
	  BpPtnrCtgry entity = searchInput.getEntity();
	  if(entity.getCtgryDtls()==null || StringUtils.isBlank(entity.getCtgryDtls().getName())){
		  Long countLike = 0l;
	      List<BpPtnrCtgry> resultList = Collections.emptyList();
	        return new BpPtnrCtgrySearchResult(countLike, detach(resultList),
	              detach(searchInput));
	   }
	  @SuppressWarnings("rawtypes")
	  SingularAttribute[] attributes = new SingularAttribute[]{BpPtnrCtgryDtls_.name};
	  BpPtnrCtgryDtls ctgryDtls = entity.getCtgryDtls();
	  Map<String, BpPtnrCtgry> resultMap = new HashMap<String, BpPtnrCtgry>();
	  List<BpPtnrCtgry> resultList = new ArrayList<BpPtnrCtgry>();
	  Long countByLike = ctgryDtlsEJB.countByLike(ctgryDtls, attributes);
	  List<BpPtnrCtgryDtls> list = ctgryDtlsEJB.findByLike(ctgryDtls, searchInput.getStart(), searchInput.getMax(), attributes);
	  for (BpPtnrCtgryDtls bpPtnrCtgryDtls : list) {
		  BpPtnrCtgry ptnrCtgry = resultMap.get(bpPtnrCtgryDtls.getCtgryCode());
		  if(ptnrCtgry!=null){
			  BpPtnrCtgry c = ptnrCtgry;
			  ptnrCtgry = new BpPtnrCtgry();
			  c.copyTo(ptnrCtgry);
			  ptnrCtgry.setId(c.getId());
		  } else {
			ptnrCtgry = ejb.findByIdentif(bpPtnrCtgryDtls.getCtgryCode());
			resultMap.put(bpPtnrCtgryDtls.getCtgryCode(), ptnrCtgry);
		  }
		  ptnrCtgry.setCtgryDtls(bpPtnrCtgryDtls);
		  resultList.add(ptnrCtgry);
	  }
      return new BpPtnrCtgrySearchResult(countByLike, detach(resultList),
              detach(searchInput));
   }
   
   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(BpPtnrCtgrySearchInput searchInput)
   {
      SingularAttribute<BpPtnrCtgry, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<BpPtnrCtgry, ?>[] readSeachAttributes(
         BpPtnrCtgrySearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<BpPtnrCtgry, ?>> result = new ArrayList<SingularAttribute<BpPtnrCtgry, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = BpPtnrCtgry_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<BpPtnrCtgry, ?>) field.get(null));
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

   

   private BpPtnrCtgry detach(BpPtnrCtgry entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<BpPtnrCtgry> detach(List<BpPtnrCtgry> list)
   {
      if (list == null)
         return list;
      List<BpPtnrCtgry> result = new ArrayList<BpPtnrCtgry>();
      for (BpPtnrCtgry entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private BpPtnrCtgrySearchInput detach(BpPtnrCtgrySearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}