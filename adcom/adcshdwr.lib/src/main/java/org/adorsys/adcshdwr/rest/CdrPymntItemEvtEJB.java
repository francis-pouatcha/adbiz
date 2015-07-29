package org.adorsys.adcshdwr.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcshdwr.jpa.CdrPymntItemEvt;
import org.adorsys.adcshdwr.repo.CdrPymntItemEvtRepository;

@Stateless
public class CdrPymntItemEvtEJB
{

   @Inject
   private CdrPymntItemEvtRepository repository;

   public CdrPymntItemEvt create(CdrPymntItemEvt entity)
   {
      return repository.save(attach(entity));
   }

   public CdrPymntItemEvt deleteById(String id)
   {
	   CdrPymntItemEvt entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public CdrPymntItemEvt update(CdrPymntItemEvt entity)
   {
      return repository.save(attach(entity));
   }

   public CdrPymntItemEvt findById(String id)
   {
      return repository.findBy(id);
   }

   public List<CdrPymntItemEvt> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<CdrPymntItemEvt> findBy(CdrPymntItemEvt entity, int start, int max, SingularAttribute<CdrPymntItemEvt, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(CdrPymntItemEvt entity, SingularAttribute<CdrPymntItemEvt, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<CdrPymntItemEvt> findByLike(CdrPymntItemEvt entity, int start, int max, SingularAttribute<CdrPymntItemEvt, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(CdrPymntItemEvt entity, SingularAttribute<CdrPymntItemEvt, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private CdrPymntItemEvt attach(CdrPymntItemEvt entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
