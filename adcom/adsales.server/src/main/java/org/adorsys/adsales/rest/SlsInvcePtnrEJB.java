package org.adorsys.adsales.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adsales.jpa.SlsInvcePtnr;
import org.adorsys.adsales.jpa.SlsInvoice;
import org.adorsys.adsales.jpa.SlsSOPtnr;
import org.adorsys.adsales.repo.SlsInvcePtnrRepository;
import org.apache.deltaspike.data.api.QueryResult;

@Stateless
public class SlsInvcePtnrEJB
{

   @Inject
   private SlsInvcePtnrRepository repository;

   public SlsInvcePtnr create(SlsInvcePtnr entity)
   {
      return repository.save(attach(entity));
   }

   public SlsInvcePtnr deleteById(String id)
   {
      SlsInvcePtnr entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public SlsInvcePtnr update(SlsInvcePtnr entity)
   {
      return repository.save(attach(entity));
   }

   public SlsInvcePtnr findById(String id)
   {
      return repository.findBy(id);
   }

   public List<SlsInvcePtnr> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<SlsInvcePtnr> findBy(SlsInvcePtnr entity, int start, int max, SingularAttribute<SlsInvcePtnr, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(SlsInvcePtnr entity, SingularAttribute<SlsInvcePtnr, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<SlsInvcePtnr> findByLike(SlsInvcePtnr entity, int start, int max, SingularAttribute<SlsInvcePtnr, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(SlsInvcePtnr entity, SingularAttribute<SlsInvcePtnr, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private SlsInvcePtnr attach(SlsInvcePtnr entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   
   public SlsInvcePtnr findPtnr(String invceNbr, String ptnrNbr, String roleInInvce) {
		return repository.findBy(SlsInvcePtnr.toId(invceNbr, ptnrNbr, roleInInvce));
	}
   
   public List<SlsInvcePtnr> findByInvceNbr(String invceNbr){
	   return repository.findByInvceNbr(invceNbr).getResultList();
   }
	
	public SlsInvcePtnr addPtnr(SlsInvoice invoice, String ptnrNbr,
			String roleInSO) {
		
		SlsInvcePtnr slsInvcePtnr = new SlsInvcePtnr();
		slsInvcePtnr.setInvceNbr(invoice.getInvceNbr());
		slsInvcePtnr.setPtnrNbr(ptnrNbr);
		slsInvcePtnr.setRoleInInvce(roleInSO);
		return repository.save(slsInvcePtnr);
		
	}
}
