package org.adorsys.adbase.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.jpa.Workspace;
import org.adorsys.adbase.repo.WorkspaceRepository;

@Stateless
public class WorkspaceEJB 
{

   @Inject
   private WorkspaceRepository repository;

   public Workspace create(Workspace entity)
   {
      return repository.save(attach(entity));
   }

   public Workspace deleteById(String id)
   {
      Workspace entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public Workspace update(Workspace entity)
   {
      return repository.save(attach(entity));
   }

   public Workspace findById(String id)
   {
      return repository.findBy(id);
   }

   public List<Workspace> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<Workspace> findBy(Workspace entity, int start, int max, SingularAttribute<Workspace, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(Workspace entity, SingularAttribute<Workspace, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<Workspace> findByLike(Workspace entity, int start, int max, SingularAttribute<Workspace, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(Workspace entity, SingularAttribute<Workspace, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private Workspace attach(Workspace entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   public Workspace findByIdentif(String identif, Date validOn){
	   List<Workspace> resultList = repository.findByIdentif(identif, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }
}
