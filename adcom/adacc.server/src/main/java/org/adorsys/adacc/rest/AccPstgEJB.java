package org.adorsys.adacc.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adacc.jpa.AccPstg;
import org.adorsys.adacc.repo.AccPstgRepository;

@Stateless
public class AccPstgEJB
{

   @Inject
   private AccPstgRepository repository;

   public AccPstg create(AccPstg entity)
   {
      return repository.save(attach(entity));
   }

   public AccPstg deleteById(String id)
   {
      AccPstg entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public AccPstg update(AccPstg entity)
   {
      return repository.save(attach(entity));
   }

   public AccPstg findById(String id)
   {
      return repository.findBy(id);
   }

   public List<AccPstg> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<AccPstg> findBy(AccPstg entity, int start, int max, SingularAttribute<AccPstg, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(AccPstg entity, SingularAttribute<AccPstg, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<AccPstg> findByLike(AccPstg entity, int start, int max, SingularAttribute<AccPstg, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(AccPstg entity, SingularAttribute<AccPstg, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private AccPstg attach(AccPstg entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
