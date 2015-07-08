package org.adorsys.adbase.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.jpa.WorkspaceRestriction;
import org.adorsys.adbase.repo.WorkspaceRestrictionRepository;

@Stateless
public class WorkspaceRestrictionEJB 
{

   @Inject
   private WorkspaceRestrictionRepository repository;

   public WorkspaceRestriction create(WorkspaceRestriction entity)
   {
      return repository.save(attach(entity));
   }

   public WorkspaceRestriction deleteById(String id)
   {
      WorkspaceRestriction entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public WorkspaceRestriction update(WorkspaceRestriction entity)
   {
      return repository.save(attach(entity));
   }

   public WorkspaceRestriction findById(String id)
   {
      return repository.findBy(id);
   }

   public List<WorkspaceRestriction> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<WorkspaceRestriction> findBy(WorkspaceRestriction entity, int start, int max, SingularAttribute<WorkspaceRestriction, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(WorkspaceRestriction entity, SingularAttribute<WorkspaceRestriction, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<WorkspaceRestriction> findByLike(WorkspaceRestriction entity, int start, int max, SingularAttribute<WorkspaceRestriction, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(WorkspaceRestriction entity, SingularAttribute<WorkspaceRestriction, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private WorkspaceRestriction attach(WorkspaceRestriction entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   public WorkspaceRestriction findByIdentif(String identif, Date validOn){
	   List<WorkspaceRestriction> resultList = repository.findByIdentif(identif, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }
}
