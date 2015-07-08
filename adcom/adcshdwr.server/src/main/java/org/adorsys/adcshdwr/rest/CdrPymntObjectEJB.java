package org.adorsys.adcshdwr.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcshdwr.jpa.CdrPymntObject;
import org.adorsys.adcshdwr.repo.CdrPymntObjectRepository;

@Stateless
public class CdrPymntObjectEJB
{

   @Inject
   private CdrPymntObjectRepository repository;

   public CdrPymntObject create(CdrPymntObject entity)
   {
      return repository.save(attach(entity));
   }

   public CdrPymntObject deleteById(String id)
   {
      CdrPymntObject entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public CdrPymntObject update(CdrPymntObject entity)
   {
      return repository.save(attach(entity));
   }

   public CdrPymntObject findById(String id)
   {
      return repository.findBy(id);
   }

   public List<CdrPymntObject> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<CdrPymntObject> findBy(CdrPymntObject entity, int start, int max, SingularAttribute<CdrPymntObject, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(CdrPymntObject entity, SingularAttribute<CdrPymntObject, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<CdrPymntObject> findByLike(CdrPymntObject entity, int start, int max, SingularAttribute<CdrPymntObject, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(CdrPymntObject entity, SingularAttribute<CdrPymntObject, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private CdrPymntObject attach(CdrPymntObject entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   
   public CdrPymntObject findByOrigItemNbr(String origItemNbr) {
	   List<CdrPymntObject> resultList = repository.findByOrigItemNbr(origItemNbr).maxResults(1).getResultList();
	   if(!resultList.isEmpty()) return resultList.iterator().next();
	   return null;
   }
}
