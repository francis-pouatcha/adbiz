package org.adorsys.adstock.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adstock.jpa.StkSection;
import org.adorsys.adstock.repo.StkSectionRepository;

@Stateless
public class StkSectionLookup
{

   @Inject
   private StkSectionRepository repository;

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

   public StkSection findByIdentif(String identif, Date validOn){
	   List<StkSection> resultList = repository.findByIdentif(identif, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }
}
