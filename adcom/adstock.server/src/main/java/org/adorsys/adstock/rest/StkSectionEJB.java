package org.adorsys.adstock.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcore.utils.SequenceGenerator;
import org.adorsys.adstock.jpa.StkSection;
import org.adorsys.adstock.repo.StkSectionRepository;

@Stateless
public class StkSectionEJB
{

   @Inject
   private StkSectionRepository repository;

   public StkSection create(StkSection entity)
   {
      return repository.save(attach(entity));
   }

   public StkSection deleteById(String id)
   {
      StkSection entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public StkSection update(StkSection entity)
   {
      return repository.save(attach(entity));
   }

   public StkSection findById(String id)
   {
      return repository.findBy(id);
   }

   public List<StkSection> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<StkSection> findBy(StkSection entity, int start, int max, SingularAttribute<StkSection, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(StkSection entity, SingularAttribute<StkSection, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<StkSection> findByLike(StkSection entity, int start, int max, SingularAttribute<StkSection, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(StkSection entity, SingularAttribute<StkSection, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private StkSection attach(StkSection entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   public StkSection findByIdentif(String identif, Date validOn){
	   List<StkSection> resultList = repository.findByIdentif(identif, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }
}
