package org.adorsys.adcost.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcost.jpa.CstActivityCenter;
import org.adorsys.adcost.repo.CstActivityCenterRepository;

@Stateless
public class CstActivityCenterEJB
{

   @Inject
   private CstActivityCenterRepository repository;

   public CstActivityCenter create(CstActivityCenter entity)
   {
      return repository.save(attach(entity));
   }

   public CstActivityCenter deleteById(String id)
   {
      CstActivityCenter entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public CstActivityCenter update(CstActivityCenter entity)
   {
      return repository.save(attach(entity));
   }

   public CstActivityCenter findById(String id)
   {
      return repository.findBy(id);
   }

   public List<CstActivityCenter> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<CstActivityCenter> findBy(CstActivityCenter entity, int start, int max, SingularAttribute<CstActivityCenter, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(CstActivityCenter entity, SingularAttribute<CstActivityCenter, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<CstActivityCenter> findByLike(CstActivityCenter entity, int start, int max, SingularAttribute<CstActivityCenter, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(CstActivityCenter entity, SingularAttribute<CstActivityCenter, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private CstActivityCenter attach(CstActivityCenter entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
