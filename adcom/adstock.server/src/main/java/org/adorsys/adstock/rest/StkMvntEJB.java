package org.adorsys.adstock.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adstock.jpa.StkMvnt;
import org.adorsys.adstock.repo.StkMvntRepository;

@Stateless
public class StkMvntEJB
{

   @Inject
   private StkMvntRepository repository;

   public StkMvnt create(StkMvnt entity)
   {
      return repository.save(attach(entity));
   }

   public StkMvnt deleteById(String id)
   {
      StkMvnt entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public StkMvnt update(StkMvnt entity)
   {
      return repository.save(attach(entity));
   }

   public StkMvnt findById(String id)
   {
      return repository.findBy(id);
   }

   public List<StkMvnt> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<StkMvnt> findBy(StkMvnt entity, int start, int max, SingularAttribute<StkMvnt, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(StkMvnt entity, SingularAttribute<StkMvnt, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<StkMvnt> findByLike(StkMvnt entity, int start, int max, SingularAttribute<StkMvnt, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }
   
   public List<StkMvnt> findCustom(StkMvnt entity, int start, int max, SingularAttribute<StkMvnt, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(StkMvnt entity, SingularAttribute<StkMvnt, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private StkMvnt attach(StkMvnt entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
