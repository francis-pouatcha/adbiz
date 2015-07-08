package org.adorsys.adbase.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.jpa.PermEntry;
import org.adorsys.adbase.repo.PermEntryRepository;

@Stateless
public class PermEntryEJB 
{

   @Inject
   private PermEntryRepository repository;

   public PermEntry create(PermEntry entity)
   {
      return repository.save(attach(entity));
   }

   public PermEntry deleteById(String id)
   {
      PermEntry entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public PermEntry update(PermEntry entity)
   {
      return repository.save(attach(entity));
   }

   public PermEntry findById(String id)
   {
      return repository.findBy(id);
   }

   public List<PermEntry> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<PermEntry> findBy(PermEntry entity, int start, int max, SingularAttribute<PermEntry, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(PermEntry entity, SingularAttribute<PermEntry, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<PermEntry> findByLike(PermEntry entity, int start, int max, SingularAttribute<PermEntry, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(PermEntry entity, SingularAttribute<PermEntry, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private PermEntry attach(PermEntry entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   public PermEntry findByIdentif(String identif, Date validOn){
	   List<PermEntry> resultList = repository.findByIdentif(identif, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }
}
