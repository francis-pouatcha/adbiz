package org.adorsys.adbase.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.jpa.UserWsRestriction;
import org.adorsys.adbase.repo.UserWsRestrictionRepository;

@Stateless
public class UserWsRestrictionEJB 
{

   @Inject
   private UserWsRestrictionRepository repository;

   public UserWsRestriction create(UserWsRestriction entity)
   {
      return repository.save(attach(entity));
   }

   public UserWsRestriction deleteById(String id)
   {
      UserWsRestriction entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public UserWsRestriction update(UserWsRestriction entity)
   {
      return repository.save(attach(entity));
   }

   public UserWsRestriction findById(String id)
   {
      return repository.findBy(id);
   }

   public List<UserWsRestriction> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<UserWsRestriction> findBy(UserWsRestriction entity, int start, int max, SingularAttribute<UserWsRestriction, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(UserWsRestriction entity, SingularAttribute<UserWsRestriction, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<UserWsRestriction> findByLike(UserWsRestriction entity, int start, int max, SingularAttribute<UserWsRestriction, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(UserWsRestriction entity, SingularAttribute<UserWsRestriction, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private UserWsRestriction attach(UserWsRestriction entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   public UserWsRestriction findByIdentif(String identif, Date validOn){
	   List<UserWsRestriction> resultList = repository.findByIdentif(identif, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }
}
