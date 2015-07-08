package org.adorsys.adcost.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcost.jpa.CstBearer;
import org.adorsys.adcost.repo.CstBearerRepository;

@Stateless
public class CstBearerEJB
{

   @Inject
   private CstBearerRepository repository;

   public CstBearer create(CstBearer entity)
   {
      return repository.save(attach(entity));
   }

   public CstBearer deleteById(String id)
   {
      CstBearer entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public CstBearer update(CstBearer entity)
   {
      return repository.save(attach(entity));
   }

   public CstBearer findById(String id)
   {
      return repository.findBy(id);
   }

   public List<CstBearer> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<CstBearer> findBy(CstBearer entity, int start, int max, SingularAttribute<CstBearer, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(CstBearer entity, SingularAttribute<CstBearer, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<CstBearer> findByLike(CstBearer entity, int start, int max, SingularAttribute<CstBearer, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(CstBearer entity, SingularAttribute<CstBearer, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private CstBearer attach(CstBearer entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
