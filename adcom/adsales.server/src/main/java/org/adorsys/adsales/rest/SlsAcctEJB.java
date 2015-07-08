package org.adorsys.adsales.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adsales.jpa.SlsAcct;
import org.adorsys.adsales.repo.SlsAcctRepository;

@Stateless
public class SlsAcctEJB
{

   @Inject
   private SlsAcctRepository repository;

   public SlsAcct create(SlsAcct entity)
   {
      return repository.save(attach(entity));
   }

   public SlsAcct deleteById(String id)
   {
      SlsAcct entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public SlsAcct update(SlsAcct entity)
   {
      return repository.save(attach(entity));
   }

   public SlsAcct findById(String id)
   {
      return repository.findBy(id);
   }

   public List<SlsAcct> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<SlsAcct> findBy(SlsAcct entity, int start, int max, SingularAttribute<SlsAcct, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(SlsAcct entity, SingularAttribute<SlsAcct, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<SlsAcct> findByLike(SlsAcct entity, int start, int max, SingularAttribute<SlsAcct, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(SlsAcct entity, SingularAttribute<SlsAcct, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private SlsAcct attach(SlsAcct entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
