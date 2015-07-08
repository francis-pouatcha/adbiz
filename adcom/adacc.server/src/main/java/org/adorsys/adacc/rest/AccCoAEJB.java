package org.adorsys.adacc.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adacc.jpa.AccCoA;
import org.adorsys.adacc.repo.AccCoARepository;

@Stateless
public class AccCoAEJB
{

   @Inject
   private AccCoARepository repository;

   public AccCoA create(AccCoA entity)
   {
      return repository.save(attach(entity));
   }

   public AccCoA deleteById(String id)
   {
      AccCoA entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public AccCoA update(AccCoA entity)
   {
      return repository.save(attach(entity));
   }

   public AccCoA findById(String id)
   {
      return repository.findBy(id);
   }

   public List<AccCoA> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<AccCoA> findBy(AccCoA entity, int start, int max, SingularAttribute<AccCoA, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(AccCoA entity, SingularAttribute<AccCoA, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<AccCoA> findByLike(AccCoA entity, int start, int max, SingularAttribute<AccCoA, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(AccCoA entity, SingularAttribute<AccCoA, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private AccCoA attach(AccCoA entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
