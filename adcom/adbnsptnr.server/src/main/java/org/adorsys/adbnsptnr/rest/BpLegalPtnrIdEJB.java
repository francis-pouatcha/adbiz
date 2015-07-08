package org.adorsys.adbnsptnr.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbnsptnr.jpa.BpLegalPtnrId;
import org.adorsys.adbnsptnr.repo.BpLegalPtnrIdRepository;
import org.adorsys.adcore.utils.SequenceGenerator;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class BpLegalPtnrIdEJB 
{

   @Inject
   private BpLegalPtnrIdRepository repository;

   public BpLegalPtnrId deleteById(String id)
   {
      BpLegalPtnrId entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public BpLegalPtnrId update(BpLegalPtnrId entity)
   {
	   if(entity==null) return null;
	   if(StringUtils.isBlank(entity.getId()))
	   {
		   entity.setPtnrNbr(SequenceGenerator
					.getSequence(SequenceGenerator.BUSINESS_PARTNER_LEGAL_SEQUENCE_PREFIXE));
		   return repository.save(attach(entity));
	   }
	   
	   BpLegalPtnrId found = findById(entity.getId());
	   if(found==null) {// persist
		   entity.setPtnrNbr(SequenceGenerator
					.getSequence(SequenceGenerator.BUSINESS_PARTNER_LEGAL_SEQUENCE_PREFIXE));
		   return repository.save(attach(entity));
	   }
	   
	   if(found.updateContent(entity))
	   {
		   found.setPtnrNbr(SequenceGenerator
					.getSequence(SequenceGenerator.BUSINESS_PARTNER_LEGAL_SEQUENCE_PREFIXE));
		   return repository.save(found);
	   }
	   return found;
   }

   public BpLegalPtnrId findById(String id)
   {
      return repository.findBy(id);
   }

   public List<BpLegalPtnrId> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<BpLegalPtnrId> findBy(BpLegalPtnrId entity, int start, int max, SingularAttribute<BpLegalPtnrId, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(BpLegalPtnrId entity, SingularAttribute<BpLegalPtnrId, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<BpLegalPtnrId> findByLike(BpLegalPtnrId entity, int start, int max, SingularAttribute<BpLegalPtnrId, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(BpLegalPtnrId entity, SingularAttribute<BpLegalPtnrId, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private BpLegalPtnrId attach(BpLegalPtnrId entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   public BpLegalPtnrId findByIdentif(String identif, Date validOn){
	   List<BpLegalPtnrId> resultList = repository.findByIdentif(identif, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }

	public void deleteByIdentif(String ptnrNbr) {
		List<BpLegalPtnrId> resultList = repository.findAllByIdentif(ptnrNbr).getResultList();
		for (BpLegalPtnrId bpLegalPtnrId : resultList) {
			repository.remove(bpLegalPtnrId);
		}
	}
}
