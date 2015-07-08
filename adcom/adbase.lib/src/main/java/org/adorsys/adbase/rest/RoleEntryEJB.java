package org.adorsys.adbase.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.jpa.RoleEntry;
import org.adorsys.adbase.repo.RoleEntryRepository;

@Stateless
public class RoleEntryEJB 
{

   @Inject
   private RoleEntryRepository repository;

   public RoleEntry create(RoleEntry entity)
   {
      return repository.save(attach(entity));
   }

   public RoleEntry deleteById(String id)
   {
      RoleEntry entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public RoleEntry update(RoleEntry entity)
   {
      return repository.save(attach(entity));
   }

   public RoleEntry findById(String id)
   {
      return repository.findBy(id);
   }

   public List<RoleEntry> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<RoleEntry> findBy(RoleEntry entity, int start, int max, SingularAttribute<RoleEntry, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(RoleEntry entity, SingularAttribute<RoleEntry, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<RoleEntry> findByLike(RoleEntry entity, int start, int max, SingularAttribute<RoleEntry, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(RoleEntry entity, SingularAttribute<RoleEntry, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private RoleEntry attach(RoleEntry entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   public RoleEntry findByIdentif(String identif, Date validOn){
	   List<RoleEntry> resultList = repository.findByIdentif(identif, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }
}
