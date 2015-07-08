package org.adorsys.adcshdwr.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcore.utils.SequenceGenerator;
import org.adorsys.adcshdwr.jpa.CdrDsPymntItem;
import org.adorsys.adcshdwr.repo.CdrDsPymntItemRepository;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class CdrDsPymntItemEJB
{

   @Inject
   private CdrDsPymntItemRepository repository;

   public CdrDsPymntItem create(CdrDsPymntItem entity)
   {
	   if (StringUtils.isBlank(entity.getPymntDocNbr())) {
			entity.setPymntDocNbr(SequenceGenerator
					.getSequence(SequenceGenerator.PAYMENT_DS_SEQUENCE_PREFIXE));
		}
      return repository.save(attach(entity));
   }

   public CdrDsPymntItem deleteById(String id)
   {
      CdrDsPymntItem entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public CdrDsPymntItem update(CdrDsPymntItem entity)
   {
      return repository.save(attach(entity));
   }

   public CdrDsPymntItem findById(String id)
   {
      return repository.findBy(id);
   }

   public List<CdrDsPymntItem> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<CdrDsPymntItem> findBy(CdrDsPymntItem entity, int start, int max, SingularAttribute<CdrDsPymntItem, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(CdrDsPymntItem entity, SingularAttribute<CdrDsPymntItem, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<CdrDsPymntItem> findByLike(CdrDsPymntItem entity, int start, int max, SingularAttribute<CdrDsPymntItem, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(CdrDsPymntItem entity, SingularAttribute<CdrDsPymntItem, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private CdrDsPymntItem attach(CdrDsPymntItem entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

	public List<CdrDsPymntItem> findByDsNbr(String saleNbr) {
		return repository.findByDsNbr(saleNbr).getResultList();	
	}
}
