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

import org.adorsys.adbase.dto.OrgContactDto;
import org.adorsys.adbase.dto.OrgContactDtoService;
import org.adorsys.adbase.exception.NotFoundOrNotActifEntityException;
import org.adorsys.adbase.jpa.OrgContact;
import org.adorsys.adbase.jpa.OrgContact_;
import org.adorsys.adbase.jpa.OrgContactSearchInput;
import org.adorsys.adbase.jpa.OrgContactSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/orgcontacts")
public class OrgContactEndpoint
{

   @Inject
   private OrgContactEJB ejb;

   @Inject
   private OrgContactDtoService ocDtoService;
   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public OrgContact create(OrgContact entity)
   {
      return detach(ejb.createCustom(entity));
   }

   @DELETE
   @Path("/{id}")
   public Response deleteById(@PathParam("id") String id)
   {
      OrgContact deleted = ejb.deleteCustomById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public OrgContact update(OrgContact entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      OrgContact found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public OrgContactSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<OrgContact> resultList = ejb.listAll(start, max);
      OrgContactSearchInput searchInput = new OrgContactSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new OrgContactSearchResult((long) resultList.size(),
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
   public OrgContactSearchResult findBy(OrgContactSearchInput searchInput)
   {
      SingularAttribute<OrgContact, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<OrgContact> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new OrgContactSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(OrgContactSearchInput searchInput)
   {
      SingularAttribute<OrgContact, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public OrgContactSearchResult findByLike(OrgContactSearchInput searchInput)
   {
      SingularAttribute<OrgContact, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<OrgContact> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new OrgContactSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(OrgContactSearchInput searchInput)
   {
      SingularAttribute<OrgContact, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }
   
   @GET
   @Path("/findByIdentif/{identif}/entity")
   @Produces({"application/json","application/xml"})
   public OrgContact findByIdentifEntity(@PathParam("identif")String identif) {
	   
	   OrgContact orgContact = ejb.findByIdentif(identif, new Date());
	   return orgContact;
   }

   @GET
   @Path("/findByIdentif/{identif}/dto")
   @Produces({"application/json","application/xml"})
   public OrgContactDto findByIdentifDto(@PathParam("identif")String identif) throws NotFoundOrNotActifEntityException {
	   OrgContactDto contactDto = ocDtoService.convert(identif);
	   
	   return contactDto;
   }
   
   @POST
   @Path("/searchOrgContacts")
   @Consumes({ "application/json", "application/xml" })
   @Produces({"application/json","application/xml"})
   public OrgContactSearchResult searchOrgContacts(OrgContactSearchInput searchInput) throws NotFoundOrNotActifEntityException
   {
	   int start = searchInput.getStart();
	   int max = searchInput.getMax();
	   
	   OrgContact entity = searchInput.getEntity();
	   String contactPeople = entity.getContactPeople();
	   String ouIdentif = entity.getOuIdentif();
	   String email = entity.getEmail();
	   String phone = entity.getPhone();
	   String ctryIso3 = entity.getCountry();
	   Date validFrom = new Date();
	   List<OrgContact> orgContacts = ejb.searchOrgContacts(contactPeople, email, phone, ctryIso3, ouIdentif, validFrom, start, max);
	   Long count = ejb.countOrgContacts(contactPeople, email, phone, ctryIso3, ouIdentif, validFrom);
	   List<OrgContactDto> dtos = ocDtoService.convert(orgContacts);
	   return new OrgContactSearchResult(count, detach(orgContacts),dtos, detach(searchInput));
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<OrgContact, ?>[] readSeachAttributes(
         OrgContactSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<OrgContact, ?>> result = new ArrayList<SingularAttribute<OrgContact, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = OrgContact_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<OrgContact, ?>) field.get(null));
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

   

   private OrgContact detach(OrgContact entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<OrgContact> detach(List<OrgContact> list)
   {
      if (list == null)
         return list;
      List<OrgContact> result = new ArrayList<OrgContact>();
      for (OrgContact entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private OrgContactSearchInput detach(OrgContactSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}