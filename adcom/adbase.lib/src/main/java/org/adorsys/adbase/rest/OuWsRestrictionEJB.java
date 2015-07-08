package org.adorsys.adbase.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.jpa.OuWsRestriction;
import org.adorsys.adbase.repo.OuWsRestrictionRepository;

@Stateless
public class OuWsRestrictionEJB 
{

   @Inject
   private OuWsRestrictionRepository repository;

   public OuWsRestriction create(OuWsRestriction entity)
   {
      return repository.save(attach(entity));
   }

   public OuWsRestriction deleteById(String id)
   {
      OuWsRestriction entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public OuWsRestriction update(OuWsRestriction entity)
   {
      return repository.save(attach(entity));
   }

   public OuWsRestriction findById(String id)
   {
      return repository.findBy(id);
   }

   public List<OuWsRestriction> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<OuWsRestriction> findBy(OuWsRestriction entity, int start, int max, SingularAttribute<OuWsRestriction, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(OuWsRestriction entity, SingularAttribute<OuWsRestriction, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<OuWsRestriction> findByLike(OuWsRestriction entity, int start, int max, SingularAttribute<OuWsRestriction, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(OuWsRestriction entity, SingularAttribute<OuWsRestriction, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private OuWsRestriction attach(OuWsRestriction entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   public OuWsRestriction findByIdentif(String identif, Date validOn){
	   List<OuWsRestriction> resultList = repository.findByIdentif(identif, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }
}
