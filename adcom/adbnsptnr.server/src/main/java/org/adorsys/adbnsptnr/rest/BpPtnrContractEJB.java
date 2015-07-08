package org.adorsys.adbnsptnr.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbnsptnr.jpa.BpPtnrContract;
import org.adorsys.adbnsptnr.repo.BpPtnrContractRepository;

@Stateless
public class BpPtnrContractEJB 
{

   @Inject
   private BpPtnrContractRepository repository;

   public BpPtnrContract create(BpPtnrContract entity)
   {
      return repository.save(attach(entity));
   }

   public BpPtnrContract deleteById(String id)
   {
      BpPtnrContract entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public BpPtnrContract update(BpPtnrContract entity)
   {
      return repository.save(attach(entity));
   }

   public BpPtnrContract findById(String id)
   {
      return repository.findBy(id);
   }

   public List<BpPtnrContract> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<BpPtnrContract> findBy(BpPtnrContract entity, int start, int max, SingularAttribute<BpPtnrContract, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(BpPtnrContract entity, SingularAttribute<BpPtnrContract, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<BpPtnrContract> findByLike(BpPtnrContract entity, int start, int max, SingularAttribute<BpPtnrContract, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(BpPtnrContract entity, SingularAttribute<BpPtnrContract, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private BpPtnrContract attach(BpPtnrContract entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   public BpPtnrContract findByIdentif(String identif, Date validOn){
	   List<BpPtnrContract> resultList = repository.findByIdentif(identif, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }

   public List<BpPtnrContract> findByHolderIdentif(String holderIdentif){
	   return repository.findByHolderIdentif(holderIdentif).getResultList();
   }
   
}
