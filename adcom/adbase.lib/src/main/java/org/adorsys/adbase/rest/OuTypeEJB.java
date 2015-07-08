package org.adorsys.adbase.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.jpa.OuType;
import org.adorsys.adbase.repo.OuTypeRepository;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class OuTypeEJB 
{

   @Inject
   private OuTypeRepository repository;

   @Inject
   private EntityManager em;
   
   public OuType create(OuType entity)
   {
      return repository.save(attach(entity));
   }


   public OuType deleteById(String id)
   {
      OuType entity = repository.findBy(id);
      if (entity != null)
      {
    	  repository.remove(entity);
      }
      return entity;
   }
   
   /**
    * Update the validTo of the entity, and save.
    * @param id
    * @return
    */
   public OuType deleteCustomById(String id)
   {
      OuType entity = repository.findBy(id);
      if (entity != null)
      {
    	  entity.setValidTo(new Date());
    	  repository.save(entity);
      }
      return entity;
   }

   public OuType update(OuType entity)
   {
      return repository.save(attach(entity));
   }

   public OuType findById(String id)
   {
      return repository.findBy(id);
   }

   public List<OuType> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<OuType> findBy(OuType entity, int start, int max, SingularAttribute<OuType, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(OuType entity, SingularAttribute<OuType, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<OuType> findByLike(OuType entity, int start, int max, SingularAttribute<OuType, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(OuType entity, SingularAttribute<OuType, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private OuType attach(OuType entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   public OuType findByIdentif(String identif, Date validOn){
	   List<OuType> resultList = repository.findByIdentif(identif, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }
   
   public List<OuType> findActifsFrom (Date validFrom){
	   return repository.findActifsFrom(validFrom);
   }

   public List<OuType> findActifsFromNow (){
	   return repository.findActifsFrom(new Date());
   }
   
   public Long countActifsFrom(Date validFrom){
	   return repository.countActifsFrom(validFrom);
   }

   public Long countActifsFromNow(){
	   return repository.countActifsFrom(new Date());
   }

   public List<OuType> searchQuery(String parentTypeId,String typeName,Date validFrom,int start, int max){
	   if(validFrom == null){
		   validFrom = new Date();
	   }
	   String qlString = "SELECT e FROM OuType AS e WHERE e.validFrom <= :validOn AND (e.validTo IS NULL OR e.validTo > :validOn)";
	   StringBuilder builder = new StringBuilder(qlString);
	   boolean isParentTypeId= false;
	   if(StringUtils.isNotBlank(parentTypeId)){
		   builder.append(" AND e.parentType = :parentTypeId");
		   isParentTypeId=true;
	   }
	   boolean isTypeName=false;
	   if(StringUtils.isNotBlank(typeName)) {
		   builder.append(" AND LOWER(e.typeName) LIKE( LOWER(:typeName))");
		   isTypeName=true;
	   }
	   TypedQuery<OuType> query = em.createQuery(builder.toString(),OuType.class);
	   query.setParameter("validOn",validFrom);
	   if(isParentTypeId){
		   query.setParameter("parentTypeId", parentTypeId);
	   }
	   if(isTypeName){
		   query.setParameter("typeName", typeName);
	   }
	   return query.setFirstResult(start).setMaxResults(max).getResultList();
   }
}
