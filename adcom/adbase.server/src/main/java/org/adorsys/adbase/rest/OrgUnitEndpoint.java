package org.adorsys.adbase.rest;

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

import org.adorsys.adbase.dto.OrgUnitDto;
import org.adorsys.adbase.dto.OrgUnitDtoService;
import org.adorsys.adbase.exception.NotFoundOrNotActifEntityException;
import org.adorsys.adbase.jpa.OrgUnit;
import org.adorsys.adbase.jpa.OrgUnit_;
import org.adorsys.adbase.jpa.OrgUnitSearchInput;
import org.adorsys.adbase.jpa.OrgUnitSearchResult;
import org.adorsys.adbase.security.SecurityUtil;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/orgunits")
public class OrgUnitEndpoint
{

   @Inject
   private OrgUnitEJB ejb;

   @Inject
   private OrgUnitDtoService dtoService;
   
   @Inject
   private SecurityUtil secUtil;
   
   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public OrgUnit create(OrgUnit entity)
   {
	   return detach(ejb.createCustom(entity,""));
   }

   @POST
   @Path("/createFromDto")
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public OrgUnit createFromDto(OrgUnitDto dto)
   {
	   OrgUnit entity = dto.toOrgUnit();
	   return detach(ejb.createCustom(entity,dto.getParentIdentif()));
   }
   
   @DELETE
   @Path("/{id}")
   public Response deleteById(@PathParam("id") String id)
   {
      OrgUnit deleted = ejb.deleteCustomById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public OrgUnit update(OrgUnit entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      OrgUnit found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public OrgUnitSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<OrgUnit> resultList = ejb.listAll(start, max);
      OrgUnitSearchInput searchInput = new OrgUnitSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new OrgUnitSearchResult((long) resultList.size(),
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
   public OrgUnitSearchResult findBy(OrgUnitSearchInput searchInput)
   {
      SingularAttribute<OrgUnit, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<OrgUnit> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new OrgUnitSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(OrgUnitSearchInput searchInput)
   {
      SingularAttribute<OrgUnit, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public OrgUnitSearchResult findByLike(OrgUnitSearchInput searchInput)
   {
      SingularAttribute<OrgUnit, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<OrgUnit> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new OrgUnitSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(OrgUnitSearchInput searchInput)
   {
      SingularAttribute<OrgUnit, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }
   

   @POST
   @Path("/searchOrgUnits")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public OrgUnitSearchResult searchOrgUnits(OrgUnitSearchInput searchInput) throws NotFoundOrNotActifEntityException{
	   OrgUnit entity = searchInput.getEntity();
	   String ctryIso3 = entity.getCtryIso3();
	   String fullName = entity.getFullName();
	   String typeIdentif = entity.getTypeIdentif();
	   Date validFrom = entity.getValidFrom();
	   int start = searchInput.getStart();
	   int max = searchInput.getMax();
	   
	   Long countOrgUnits = ejb.countOrgUnits(fullName, typeIdentif, ctryIso3, validFrom, start, max);
	   List<OrgUnit> searchOrgUnits = ejb.searchOrgUnits(fullName, typeIdentif, ctryIso3, validFrom, start, max);
	   List<OrgUnitDto> convertOrgUnits = dtoService.convertOrgUnits(searchOrgUnits);
	   OrgUnitSearchResult searchResult = new OrgUnitSearchResult(countOrgUnits, searchOrgUnits, searchInput);
	   searchResult.setDtos(convertOrgUnits);
	   return searchResult;
   }

   @GET
   @Path("/dtoByidentif/{identif}")
   @Produces({ "application/json", "application/xml" })
   public Response findDtoByIdentif(@PathParam("identif") String identif) throws NotFoundOrNotActifEntityException
   {
	  OrgUnitDto orgUnitDto = dtoService.convertOrgUnit(identif);
	  
      if (orgUnitDto == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(orgUnitDto).build();
   }

   @GET
   @Path("/entityByidentif/{identif}")
   @Produces({ "application/json", "application/xml" })
   public Response findEntityByIdentif(@PathParam("identif") String identif) throws NotFoundOrNotActifEntityException
   {
	   OrgUnit orgUnit = ejb.findByIdentif(identif, new Date());
	   
      if (orgUnit == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(orgUnit).build();
   }

   @GET
   @Path("/findActifsFromNow")
   @Produces({ "application/json", "application/xml" })
   public List<OrgUnit> findActifsFromNow() throws NotFoundOrNotActifEntityException{
	   
	   return ejb.findActifsByIdentif(secUtil.getCurrentOrgUnit().getIdentif(), new Date());
   }
   
   @SuppressWarnings("unchecked")
   private SingularAttribute<OrgUnit, ?>[] readSeachAttributes(
         OrgUnitSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<OrgUnit, ?>> result = new ArrayList<SingularAttribute<OrgUnit, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = OrgUnit_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<OrgUnit, ?>) field.get(null));
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

   

   private OrgUnit detach(OrgUnit entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<OrgUnit> detach(List<OrgUnit> list)
   {
      if (list == null)
         return list;
      List<OrgUnit> result = new ArrayList<OrgUnit>();
      for (OrgUnit entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private OrgUnitSearchInput detach(OrgUnitSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}