package org.adorsys.adsales.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcore.enums.CoreHistoryTypeEnum;
import org.adorsys.adsales.event.SlsInvceDeliveredEvent;
import org.adorsys.adsales.jpa.SlsInvceHistory;
import org.adorsys.adsales.repo.SlsInvceHistoryRepository;

@Stateless
public class SlsInvceHistoryEJB
{

   @Inject
   private SlsInvceHistoryRepository repository;
   
   	@Inject
   	@SlsInvceDeliveredEvent
	private Event<SlsInvceHistory> slsInvceDeliveredEvent;
  
   public SlsInvceHistory create(SlsInvceHistory entity)
   {
	   SlsInvceHistory slsInvceHistory = repository.save(attach(entity));
       
      if (CoreHistoryTypeEnum.DELIVERED.name().equals(slsInvceHistory.getHstryType())) {
    	  slsInvceDeliveredEvent.fire(slsInvceHistory);
		}    
      return slsInvceHistory;
   }

   public SlsInvceHistory deleteById(String id)
   {
      SlsInvceHistory entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public SlsInvceHistory update(SlsInvceHistory entity)
   {
      return repository.save(attach(entity));
   }

   public SlsInvceHistory findById(String id)
   {
      return repository.findBy(id);
   }

   public List<SlsInvceHistory> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<SlsInvceHistory> findBy(SlsInvceHistory entity, int start, int max, SingularAttribute<SlsInvceHistory, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(SlsInvceHistory entity, SingularAttribute<SlsInvceHistory, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<SlsInvceHistory> findByLike(SlsInvceHistory entity, int start, int max, SingularAttribute<SlsInvceHistory, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(SlsInvceHistory entity, SingularAttribute<SlsInvceHistory, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private SlsInvceHistory attach(SlsInvceHistory entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
