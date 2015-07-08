package org.adorsys.adsales.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adsales.jpa.SlsSOPtnr;
import org.adorsys.adsales.jpa.SlsSalesOrder;
import org.adorsys.adsales.repo.SlsSOPtnrRepository;

@Stateless
public class SlsSOPtnrEJB
{

   @Inject
   private SlsSOPtnrRepository repository;

   public SlsSOPtnr create(SlsSOPtnr entity)
   {
      return repository.save(attach(entity));
   }

   public SlsSOPtnr deleteById(String id)
   {
      SlsSOPtnr entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public SlsSOPtnr update(SlsSOPtnr entity)
   {
      return repository.save(attach(entity));
   }

   public SlsSOPtnr findById(String id)
   {
      return repository.findBy(id);
   }

   public List<SlsSOPtnr> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<SlsSOPtnr> findBy(SlsSOPtnr entity, int start, int max, SingularAttribute<SlsSOPtnr, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(SlsSOPtnr entity, SingularAttribute<SlsSOPtnr, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<SlsSOPtnr> findByLike(SlsSOPtnr entity, int start, int max, SingularAttribute<SlsSOPtnr, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(SlsSOPtnr entity, SingularAttribute<SlsSOPtnr, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private SlsSOPtnr attach(SlsSOPtnr entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

	public SlsSOPtnr findPtnr(String soNbr, String ptnrNbr, String roleInSo) {
		return repository.findBy(SlsSOPtnr.toId(soNbr, ptnrNbr, roleInSo));
	}
	
	public SlsSOPtnr addPtnr(SlsSalesOrder salesOrder, String ptnrNbr,
			String roleInSO) {
		
		SlsSOPtnr slsSOPtnr = new SlsSOPtnr();
		slsSOPtnr.setSoNbr(salesOrder.getSoNbr());
		slsSOPtnr.setPtnrNbr(ptnrNbr);
		slsSOPtnr.setRoleInSO(roleInSO);
		return repository.save(slsSOPtnr);
		
	}
}
