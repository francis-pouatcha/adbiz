package org.adorsys.adbnsptnr.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbnsptnr.jpa.BpInsurrance;
import org.adorsys.adbnsptnr.repo.BpInsurranceRepository;

@Stateless
public class BpInsurranceEJB 
{

   @Inject
   private BpInsurranceRepository repository;

   public BpInsurrance create(BpInsurrance entity)
   {
      return repository.save(attach(entity));
   }

   public BpInsurrance deleteById(String id)
   {
      BpInsurrance entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public BpInsurrance update(BpInsurrance entity)
   {
      return repository.save(attach(entity));
   }

   public BpInsurrance findById(String id)
   {
      return repository.findBy(id);
   }

   public List<BpInsurrance> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<BpInsurrance> findBy(BpInsurrance entity, int start, int max, SingularAttribute<BpInsurrance, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(BpInsurrance entity, SingularAttribute<BpInsurrance, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<BpInsurrance> findByLike(BpInsurrance entity, int start, int max, SingularAttribute<BpInsurrance, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(BpInsurrance entity, SingularAttribute<BpInsurrance, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private BpInsurrance attach(BpInsurrance entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   public BpInsurrance findByIdentif(String identif, Date validOn){
	   List<BpInsurrance> resultList = repository.findByIdentif(identif, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }
}
