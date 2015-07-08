package org.adorsys.adsales.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adsales.jpa.SlsSOHstry;
import org.adorsys.adsales.repo.SlsSOHstryRepository;

@Stateless
public class SlsSOHstryEJB
{

   @Inject
   private SlsSOHstryRepository repository;

   public SlsSOHstry create(SlsSOHstry entity)
   {
      return repository.save(attach(entity));
   }

   public SlsSOHstry deleteById(String id)
   {
      SlsSOHstry entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public SlsSOHstry update(SlsSOHstry entity)
   {
      return repository.save(attach(entity));
   }

   public SlsSOHstry findById(String id)
   {
      return repository.findBy(id);
   }

   public List<SlsSOHstry> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<SlsSOHstry> findBy(SlsSOHstry entity, int start, int max, SingularAttribute<SlsSOHstry, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(SlsSOHstry entity, SingularAttribute<SlsSOHstry, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<SlsSOHstry> findByLike(SlsSOHstry entity, int start, int max, SingularAttribute<SlsSOHstry, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(SlsSOHstry entity, SingularAttribute<SlsSOHstry, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private SlsSOHstry attach(SlsSOHstry entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
