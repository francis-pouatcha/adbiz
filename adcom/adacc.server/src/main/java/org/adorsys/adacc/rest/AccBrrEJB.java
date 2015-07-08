package org.adorsys.adacc.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adacc.jpa.AccBrr;
import org.adorsys.adacc.repo.AccBrrRepository;

@Stateless
public class AccBrrEJB
{

   @Inject
   private AccBrrRepository repository;

   public AccBrr create(AccBrr entity)
   {
      return repository.save(attach(entity));
   }

   public AccBrr deleteById(String id)
   {
      AccBrr entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public AccBrr update(AccBrr entity)
   {
      return repository.save(attach(entity));
   }

   public AccBrr findById(String id)
   {
      return repository.findBy(id);
   }

   public List<AccBrr> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<AccBrr> findBy(AccBrr entity, int start, int max, SingularAttribute<AccBrr, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(AccBrr entity, SingularAttribute<AccBrr, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<AccBrr> findByLike(AccBrr entity, int start, int max, SingularAttribute<AccBrr, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(AccBrr entity, SingularAttribute<AccBrr, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private AccBrr attach(AccBrr entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
