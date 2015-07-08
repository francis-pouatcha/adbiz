package org.adorsys.adbase.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.jpa.Country;
import org.adorsys.adbase.repo.CountryRepository;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class CountryEJB
{

	
   @Inject
   private CountryRepository repository;

   @Inject
   private EntityManager em;
   public Country create(Country entity)
   {
      return repository.save(attach(entity));
   }

   public Country deleteById(String id)
   {
      Country entity = repository.findBy(id);
      if (entity != null)
      {
    	  repository.remove(entity);
      }
      return entity;
   }

   public Country deleteCustomById(String id)
   {
      Country entity = repository.findBy(id);
      if (entity != null)
      {
    	  entity.setValidTo(new Date());
    	  em.merge(entity);
      }
      return entity;
   }

   public Country update(Country entity)
   {
      return repository.save(attach(entity));
   }

   public Country findById(String id)
   {
      return repository.findBy(id);
   }

   public List<Country> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<Country> findBy(Country entity, int start, int max, SingularAttribute<Country, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(Country entity, SingularAttribute<Country, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<Country> findByLike(Country entity, int start, int max, SingularAttribute<Country, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(Country entity, SingularAttribute<Country, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private Country attach(Country entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   
   public Country findByIdentif(String identif, Date validOn){
	   List<Country> resultList = repository.findByIdentif(identif, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }

   public Country findByIso3(String iso3, Date validOn){
	   List<Country> resultList = repository.findByIso3(iso3, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }
   public List<Country> findActicfCountrys(Date validOn){
	   validOn = validOn == null ? new Date():validOn;
	   
	   return repository.findActicfCountrys(validOn);
   }
   
   public List<Country> searchCountrys(String iso3,String name,String dialCode,String langIso2,String currIso2,Date validFrom,int start,int max){
	   validFrom = validFrom == null ? new Date() : validFrom;
	   StringBuilder qBuilder = new StringBuilder("SELECT e FROM Country AS e WHERE e.validFrom <= :validFrom AND (e.validTo IS NULL OR e.validTo > :validFrom) ");
	   boolean isIso3 = false;
	   boolean isName = false;
	   boolean isDialCode = false;
	   boolean isLangIso2 = false;
	   boolean isCurrIso2 = false;
	   
	   if(StringUtils.isNotBlank(iso3)) {
		   qBuilder.append(" AND UPPER(e.iso3) = UPPER(:iso3)");
		   isIso3 = true;
	   }
	   if(StringUtils.isNotBlank(name)) {
		   qBuilder.append(" AND LOWER(e.name) LIKE (LOWER(:name))");
		   isName = true;
	   }
	   if(StringUtils.isNotBlank(dialCode)) {
		   qBuilder.append(" AND UPPER(e.dialCode) = UPPER(:dialCode)");
		   isDialCode = true;
	   }
	   if(StringUtils.isNotBlank(langIso2)) {
		   qBuilder.append(" AND UPPER(e.langsIso2) = UPPER(:langsIso2)");
		   isLangIso2 = true;
	   }
	   if(StringUtils.isNotBlank(currIso2)) {
		   qBuilder.append(" AND UPPER(e.currsIso2) = UPPER(:currsIso2)");
		   isCurrIso2 = true;
	   }
	   qBuilder.append(" ORDER BY name ASC");
	   TypedQuery<Country> query = em.createQuery(qBuilder.toString(), Country.class);
	   query.setParameter("validFrom", validFrom);
	   if(isIso3) {
		   query.setParameter("iso3", iso3);
	   }
	   if(isName) {
		   query.setParameter("name", "%"+name+"%");
	   }
	   if(isDialCode) {
		   query.setParameter("dialCode", dialCode);
	   }
	   if(isLangIso2) {
		   query.setParameter("langsIso2", langIso2);
	   }
	   if(isCurrIso2) {
		   query.setParameter("currsIso2", currIso2);
	   }
	   List<Country> resultList = query.setFirstResult(start).setMaxResults(max).getResultList();
	   return resultList;
   }

   
   public Long countCountrys(String iso3,String name,String dialCode,String langIso2,String currIso2,Date validFrom,int start,int max){
	   validFrom = validFrom == null ? new Date() : validFrom;
	   StringBuilder qBuilder = new StringBuilder("SELECT COUNT(e) FROM Country AS e WHERE e.validFrom <= :validFrom AND (e.validTo IS NULL OR e.validTo > :validFrom) ");
	   boolean isIso3 = false;
	   boolean isName = false;
	   boolean isDialCode = false;
	   boolean isLangIso2 = false;
	   boolean isCurrIso2 = false;
	   
	   if(StringUtils.isNotBlank(iso3)) {
		   qBuilder.append(" AND UPPER(e.iso3) = UPPER(:iso3)");
		   isIso3 = true;
	   }
	   if(StringUtils.isNotBlank(name)) {
		   qBuilder.append(" AND LOWER(e.name) LIKE (LOWER(:name))");
		   isName = true;
	   }
	   if(StringUtils.isNotBlank(dialCode)) {
		   qBuilder.append(" AND UPPER(e.dialCode) = UPPER(:dialCode)");
		   isDialCode = true;
	   }
	   if(StringUtils.isNotBlank(langIso2)) {
		   qBuilder.append(" AND UPPER(e.langsIso2) = UPPER(:langsIso2)");
		   isLangIso2 = true;
	   }
	   if(StringUtils.isNotBlank(currIso2)) {
		   qBuilder.append(" AND UPPER(e.currsIso2) = UPPER(:currsIso2)");
		   isCurrIso2 = true;
	   }
	   TypedQuery<Long> query = em.createQuery(qBuilder.toString(), Long.class);
	   query.setParameter("validFrom", validFrom);
	   if(isIso3) {
		   query.setParameter("iso3", iso3);
	   }
	   if(isName) {
		   query.setParameter("name", "%"+name+"%");
	   }
	   if(isDialCode) {
		   query.setParameter("dialCode", dialCode);
	   }
	   if(isLangIso2) {
		   query.setParameter("langsIso2", langIso2);
	   }
	   if(isCurrIso2) {
		   query.setParameter("currsIso2", currIso2);
	   }
	   return query.getSingleResult();
   }
}
