package org.adorsys.adbnsptnr.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbnsptnr.jpa.BpPtnrContact;
import org.adorsys.adbnsptnr.repo.BpPtnrContactRepository;

@Stateless
public class BpPtnrContactEJB 
{

   @Inject
   private BpPtnrContactRepository repository;

   public BpPtnrContact create(BpPtnrContact entity)
   {
	   increaseIndex(entity);
      return repository.save(attach(entity));
   }

   public BpPtnrContact deleteById(String id)
   {
      BpPtnrContact entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public BpPtnrContact update(BpPtnrContact entity)
   {
      return repository.save(attach(entity));
   }

   public BpPtnrContact findById(String id)
   {
      return repository.findBy(id);
   }

   public List<BpPtnrContact> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<BpPtnrContact> findBy(BpPtnrContact entity, int start, int max, SingularAttribute<BpPtnrContact, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(BpPtnrContact entity, SingularAttribute<BpPtnrContact, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<BpPtnrContact> findByLike(BpPtnrContact entity, int start, int max, SingularAttribute<BpPtnrContact, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(BpPtnrContact entity, SingularAttribute<BpPtnrContact, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private BpPtnrContact attach(BpPtnrContact entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   public BpPtnrContact findByIdentif(String identif, Date validOn){
	   List<BpPtnrContact> resultList = repository.findByIdentif(identif, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }
   
   public void increaseIndex(final BpPtnrContact entity){
	   List<BpPtnrContact> resultList = repository.findByPtnrNbrAndCntctRoleAndLangIso2(entity.getPtnrNbr(), entity.getCntctRole(), entity.getLangIso2()).orderDesc("cntctIndex").maxResults(1).getResultList();
	   if(!resultList.isEmpty()){
		   BpPtnrContact bpPtnrContact = resultList.iterator().next();
		   entity.setCntctIndex(bpPtnrContact.getCntctIndex()+1);
	   } else {
		   entity.setCntctIndex(0);
	   }
   }
}
