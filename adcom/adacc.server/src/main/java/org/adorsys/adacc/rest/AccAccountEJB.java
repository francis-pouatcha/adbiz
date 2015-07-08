package org.adorsys.adacc.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adacc.jpa.AccAccount;
import org.adorsys.adacc.repo.AccAccountRepository;

@Stateless
public class AccAccountEJB
{

   @Inject
   private AccAccountRepository repository;

   public AccAccount create(AccAccount entity)
   {
      return repository.save(attach(entity));
   }

   public AccAccount deleteById(String id)
   {
      AccAccount entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public AccAccount update(AccAccount entity)
   {
      return repository.save(attach(entity));
   }

   public AccAccount findById(String id)
   {
      return repository.findBy(id);
   }

   public List<AccAccount> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<AccAccount> findBy(AccAccount entity, int start, int max, SingularAttribute<AccAccount, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(AccAccount entity, SingularAttribute<AccAccount, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<AccAccount> findByLike(AccAccount entity, int start, int max, SingularAttribute<AccAccount, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(AccAccount entity, SingularAttribute<AccAccount, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private AccAccount attach(AccAccount entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
