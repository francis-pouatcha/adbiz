package org.adorsys.adbnsptnr.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbnsptnr.jpa.BpCtgryOfPtnr;
import org.adorsys.adbnsptnr.repo.BpCtgryOfPtnrRepository;

@Stateless
public class BpCtgryOfPtnrEJB 
{

   @Inject
   private BpCtgryOfPtnrRepository repository;

   public BpCtgryOfPtnr create(BpCtgryOfPtnr entity)
   {
      return repository.save(attach(entity));
   }

   public BpCtgryOfPtnr deleteById(String id)
   {
      BpCtgryOfPtnr entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public BpCtgryOfPtnr update(BpCtgryOfPtnr entity)
   {
      return repository.save(attach(entity));
   }

   public BpCtgryOfPtnr findById(String id)
   {
      return repository.findBy(id);
   }

   public List<BpCtgryOfPtnr> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<BpCtgryOfPtnr> findBy(BpCtgryOfPtnr entity, int start, int max, SingularAttribute<BpCtgryOfPtnr, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(BpCtgryOfPtnr entity, SingularAttribute<BpCtgryOfPtnr, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<BpCtgryOfPtnr> findByLike(BpCtgryOfPtnr entity, int start, int max, SingularAttribute<BpCtgryOfPtnr, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(BpCtgryOfPtnr entity, SingularAttribute<BpCtgryOfPtnr, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private BpCtgryOfPtnr attach(BpCtgryOfPtnr entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   public BpCtgryOfPtnr findByIdentif(String identif, Date validOn){
	   List<BpCtgryOfPtnr> resultList = repository.findByIdentif(identif, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }
}
