package org.adorsys.adbase.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.jpa.SecTermCredtl;
import org.adorsys.adbase.repo.SecTermCredtlRepository;

@Stateless
public class SecTermCredtlEJB
{

   @Inject
   private SecTermCredtlRepository repository;

   public SecTermCredtl create(SecTermCredtl entity)
   {
      return repository.save(attach(entity));
   }

   public SecTermCredtl deleteById(String id)
   {
      SecTermCredtl entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public SecTermCredtl update(SecTermCredtl entity)
   {
      return repository.save(attach(entity));
   }

   public SecTermCredtl findById(String id)
   {
      return repository.findBy(id);
   }

   public List<SecTermCredtl> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<SecTermCredtl> findBy(SecTermCredtl entity, int start, int max, SingularAttribute<SecTermCredtl, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(SecTermCredtl entity, SingularAttribute<SecTermCredtl, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<SecTermCredtl> findByLike(SecTermCredtl entity, int start, int max, SingularAttribute<SecTermCredtl, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(SecTermCredtl entity, SingularAttribute<SecTermCredtl, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private SecTermCredtl attach(SecTermCredtl entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
