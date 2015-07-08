package org.adorsys.adbnsptnr.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbnsptnr.jpa.BpPtnrAccntBlnce;
import org.adorsys.adbnsptnr.repo.BpPtnrAccntBlnceRepository;

@Stateless
public class BpPtnrAccntBlnceEJB
{

   @Inject
   private BpPtnrAccntBlnceRepository repository;

   public BpPtnrAccntBlnce create(BpPtnrAccntBlnce entity)
   {
      return repository.save(attach(entity));
   }

   public BpPtnrAccntBlnce deleteById(String id)
   {
      BpPtnrAccntBlnce entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public BpPtnrAccntBlnce update(BpPtnrAccntBlnce entity)
   {
      return repository.save(attach(entity));
   }

   public BpPtnrAccntBlnce findById(String id)
   {
      return repository.findBy(id);
   }

   public List<BpPtnrAccntBlnce> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<BpPtnrAccntBlnce> findBy(BpPtnrAccntBlnce entity, int start, int max, SingularAttribute<BpPtnrAccntBlnce, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(BpPtnrAccntBlnce entity, SingularAttribute<BpPtnrAccntBlnce, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<BpPtnrAccntBlnce> findByLike(BpPtnrAccntBlnce entity, int start, int max, SingularAttribute<BpPtnrAccntBlnce, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(BpPtnrAccntBlnce entity, SingularAttribute<BpPtnrAccntBlnce, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private BpPtnrAccntBlnce attach(BpPtnrAccntBlnce entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
