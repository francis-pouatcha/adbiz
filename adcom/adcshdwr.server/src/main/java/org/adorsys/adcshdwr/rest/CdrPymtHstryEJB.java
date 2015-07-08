package org.adorsys.adcshdwr.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcshdwr.jpa.CdrPymtHstry;
import org.adorsys.adcshdwr.repo.CdrPymtHstryRepository;

@Stateless
public class CdrPymtHstryEJB
{

   @Inject
   private CdrPymtHstryRepository repository;

   public CdrPymtHstry create(CdrPymtHstry entity)
   {
      return repository.save(attach(entity));
   }

   public CdrPymtHstry deleteById(String id)
   {
      CdrPymtHstry entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public CdrPymtHstry update(CdrPymtHstry entity)
   {
      return repository.save(attach(entity));
   }

   public CdrPymtHstry findById(String id)
   {
      return repository.findBy(id);
   }

   public List<CdrPymtHstry> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<CdrPymtHstry> findBy(CdrPymtHstry entity, int start, int max, SingularAttribute<CdrPymtHstry, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(CdrPymtHstry entity, SingularAttribute<CdrPymtHstry, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<CdrPymtHstry> findByLike(CdrPymtHstry entity, int start, int max, SingularAttribute<CdrPymtHstry, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(CdrPymtHstry entity, SingularAttribute<CdrPymtHstry, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private CdrPymtHstry attach(CdrPymtHstry entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
