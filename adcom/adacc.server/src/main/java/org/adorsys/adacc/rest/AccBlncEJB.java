package org.adorsys.adacc.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adacc.jpa.AccBlnc;
import org.adorsys.adacc.repo.AccBlncRepository;

@Stateless
public class AccBlncEJB
{

   @Inject
   private AccBlncRepository repository;

   public AccBlnc create(AccBlnc entity)
   {
      return repository.save(attach(entity));
   }

   public AccBlnc deleteById(String id)
   {
      AccBlnc entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public AccBlnc update(AccBlnc entity)
   {
      return repository.save(attach(entity));
   }

   public AccBlnc findById(String id)
   {
      return repository.findBy(id);
   }

   public List<AccBlnc> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<AccBlnc> findBy(AccBlnc entity, int start, int max, SingularAttribute<AccBlnc, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(AccBlnc entity, SingularAttribute<AccBlnc, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<AccBlnc> findByLike(AccBlnc entity, int start, int max, SingularAttribute<AccBlnc, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(AccBlnc entity, SingularAttribute<AccBlnc, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private AccBlnc attach(AccBlnc entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
