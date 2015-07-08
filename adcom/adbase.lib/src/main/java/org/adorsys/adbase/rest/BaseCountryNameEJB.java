package org.adorsys.adbase.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.jpa.BaseCountryName;
import org.adorsys.adbase.repo.BaseCountryNameRepository;

@Stateless
public class BaseCountryNameEJB 
{

   @Inject
   private BaseCountryNameRepository repository;

   public BaseCountryName create(BaseCountryName entity)
   {
      return repository.save(attach(entity));
   }

   public BaseCountryName deleteById(String id)
   {
      BaseCountryName entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public BaseCountryName update(BaseCountryName entity)
   {
      return repository.save(attach(entity));
   }

   public BaseCountryName findById(String id)
   {
      return repository.findBy(id);
   }

   public List<BaseCountryName> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<BaseCountryName> findBy(BaseCountryName entity, int start, int max, SingularAttribute<BaseCountryName, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(BaseCountryName entity, SingularAttribute<BaseCountryName, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<BaseCountryName> findByLike(BaseCountryName entity, int start, int max, SingularAttribute<BaseCountryName, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(BaseCountryName entity, SingularAttribute<BaseCountryName, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private BaseCountryName attach(BaseCountryName entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
