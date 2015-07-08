package org.adorsys.adbnsptnr.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbnsptnr.jpa.BpPtnrCreditDtls;
import org.adorsys.adbnsptnr.repo.BpPtnrCreditDtlsRepository;

@Stateless
public class BpPtnrCreditDtlsEJB 
{

   @Inject
   private BpPtnrCreditDtlsRepository repository;

   public BpPtnrCreditDtls create(BpPtnrCreditDtls entity)
   {
      return repository.save(attach(entity));
   }

   public BpPtnrCreditDtls deleteById(String id)
   {
      BpPtnrCreditDtls entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public BpPtnrCreditDtls update(BpPtnrCreditDtls entity)
   {
      return repository.save(attach(entity));
   }

   public BpPtnrCreditDtls findById(String id)
   {
      return repository.findBy(id);
   }

   public List<BpPtnrCreditDtls> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<BpPtnrCreditDtls> findBy(BpPtnrCreditDtls entity, int start, int max, SingularAttribute<BpPtnrCreditDtls, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(BpPtnrCreditDtls entity, SingularAttribute<BpPtnrCreditDtls, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<BpPtnrCreditDtls> findByLike(BpPtnrCreditDtls entity, int start, int max, SingularAttribute<BpPtnrCreditDtls, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(BpPtnrCreditDtls entity, SingularAttribute<BpPtnrCreditDtls, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private BpPtnrCreditDtls attach(BpPtnrCreditDtls entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   public BpPtnrCreditDtls findByIdentif(String identif, Date validOn){
	   List<BpPtnrCreditDtls> resultList = repository.findByIdentif(identif, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }
}
