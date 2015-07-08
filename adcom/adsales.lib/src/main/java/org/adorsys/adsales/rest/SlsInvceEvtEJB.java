package org.adorsys.adsales.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adsales.jpa.SlsInvceEvt;
import org.adorsys.adsales.repo.SlsInvceEvtRepository;

@Stateless
public class SlsInvceEvtEJB
{

   @Inject
   private SlsInvceEvtRepository repository;

   public SlsInvceEvt create(SlsInvceEvt entity)
   {
      return repository.save(attach(entity));
   }

   public SlsInvceEvt deleteById(String id)
   {
	   SlsInvceEvt entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public SlsInvceEvt update(SlsInvceEvt entity)
   {
      return repository.save(attach(entity));
   }

   public SlsInvceEvt findById(String id)
   {
      return repository.findBy(id);
   }

   public List<SlsInvceEvt> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<SlsInvceEvt> findBy(SlsInvceEvt entity, int start, int max, SingularAttribute<SlsInvceEvt, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(SlsInvceEvt entity, SingularAttribute<SlsInvceEvt, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<SlsInvceEvt> findByLike(SlsInvceEvt entity, int start, int max, SingularAttribute<SlsInvceEvt, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(SlsInvceEvt entity, SingularAttribute<SlsInvceEvt, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }
   public List<SlsInvceEvt> findByEvtName(String evtName){
	   return repository.findByEvtName(evtName);
   }
   

   private SlsInvceEvt attach(SlsInvceEvt entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
