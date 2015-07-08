package org.adorsys.adbnsptnr.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbnsptnr.jpa.BpInsrrncPpse;
import org.adorsys.adbnsptnr.repo.BpInsrrncPpseRepository;

@Stateless
public class BpInsrrncPpseEJB 
{

   @Inject
   private BpInsrrncPpseRepository repository;

   public BpInsrrncPpse create(BpInsrrncPpse entity)
   {
      return repository.save(attach(entity));
   }

   public BpInsrrncPpse deleteById(String id)
   {
      BpInsrrncPpse entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public BpInsrrncPpse update(BpInsrrncPpse entity)
   {
      return repository.save(attach(entity));
   }

   public BpInsrrncPpse findById(String id)
   {
      return repository.findBy(id);
   }

   public List<BpInsrrncPpse> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<BpInsrrncPpse> findBy(BpInsrrncPpse entity, int start, int max, SingularAttribute<BpInsrrncPpse, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(BpInsrrncPpse entity, SingularAttribute<BpInsrrncPpse, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<BpInsrrncPpse> findByLike(BpInsrrncPpse entity, int start, int max, SingularAttribute<BpInsrrncPpse, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(BpInsrrncPpse entity, SingularAttribute<BpInsrrncPpse, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private BpInsrrncPpse attach(BpInsrrncPpse entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   public BpInsrrncPpse findByIdentif(String identif, Date validOn){
	   List<BpInsrrncPpse> resultList = repository.findByIdentif(identif, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }
}
