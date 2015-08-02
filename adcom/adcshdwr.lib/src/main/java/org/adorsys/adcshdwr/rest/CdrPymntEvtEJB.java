package org.adorsys.adcshdwr.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcshdwr.jpa.CdrPymntEvt;
import org.adorsys.adcshdwr.repo.CdrPymntEvtRepository;

@Stateless
public class CdrPymntEvtEJB
{

   @Inject
   private CdrPymntEvtRepository repository;

   public CdrPymntEvt create(CdrPymntEvt entity)
   {
      return repository.save(attach(entity));
   }

   public CdrPymntEvt deleteById(String id)
   {
	   CdrPymntEvt entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public CdrPymntEvt update(CdrPymntEvt entity)
   {
      return repository.save(attach(entity));
   }

   public CdrPymntEvt findById(String id)
   {
      return repository.findBy(id);
   }

   public List<CdrPymntEvt> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<CdrPymntEvt> findBy(CdrPymntEvt entity, int start, int max, SingularAttribute<CdrPymntEvt, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(CdrPymntEvt entity, SingularAttribute<CdrPymntEvt, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<CdrPymntEvt> findByLike(CdrPymntEvt entity, int start, int max, SingularAttribute<CdrPymntEvt, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(CdrPymntEvt entity, SingularAttribute<CdrPymntEvt, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private CdrPymntEvt attach(CdrPymntEvt entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
