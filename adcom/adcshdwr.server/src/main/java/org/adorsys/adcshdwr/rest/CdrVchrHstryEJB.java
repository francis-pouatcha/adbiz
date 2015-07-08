package org.adorsys.adcshdwr.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcshdwr.jpa.CdrVchrHstry;
import org.adorsys.adcshdwr.repo.CdrVchrHstryRepository;

@Stateless
public class CdrVchrHstryEJB
{

   @Inject
   private CdrVchrHstryRepository repository;

   public CdrVchrHstry create(CdrVchrHstry entity)
   {
      return repository.save(attach(entity));
   }

   public CdrVchrHstry deleteById(String id)
   {
      CdrVchrHstry entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public CdrVchrHstry update(CdrVchrHstry entity)
   {
      return repository.save(attach(entity));
   }

   public CdrVchrHstry findById(String id)
   {
      return repository.findBy(id);
   }

   public List<CdrVchrHstry> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<CdrVchrHstry> findBy(CdrVchrHstry entity, int start, int max, SingularAttribute<CdrVchrHstry, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(CdrVchrHstry entity, SingularAttribute<CdrVchrHstry, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<CdrVchrHstry> findByLike(CdrVchrHstry entity, int start, int max, SingularAttribute<CdrVchrHstry, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(CdrVchrHstry entity, SingularAttribute<CdrVchrHstry, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private CdrVchrHstry attach(CdrVchrHstry entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
