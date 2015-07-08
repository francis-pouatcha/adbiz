package org.adorsys.adbase.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.jpa.SecUserSession;
import org.adorsys.adbase.repo.SecUserSessionRepository;
import org.apache.deltaspike.data.api.QueryResult;

@Stateless
public class SecUserSessionEJB
{

   @Inject
   private SecUserSessionRepository repository;

   public SecUserSession create(SecUserSession entity)
   {
      return repository.save(attach(entity));
   }

   public SecUserSession deleteById(String id)
   {
      SecUserSession entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public SecUserSession update(SecUserSession entity)
   {
      return repository.save(attach(entity));
   }

   public SecUserSession findById(String id)
   {
      return repository.findBy(id);
   }

   public List<SecUserSession> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<SecUserSession> findBy(SecUserSession entity, int start, int max, SingularAttribute<SecUserSession, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(SecUserSession entity, SingularAttribute<SecUserSession, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<SecUserSession> findByLike(SecUserSession entity, int start, int max, SingularAttribute<SecUserSession, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(SecUserSession entity, SingularAttribute<SecUserSession, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   public QueryResult<SecUserSession> findByWorkspaceId(String workspaceId,Date when) {
	   return repository.findByWorkspaceId(workspaceId,when);
   }
   
   public SecUserSession findOneByWorkspaceId(String workspaceId,Date when) {
	   QueryResult<SecUserSession> queryResult = findByWorkspaceId(workspaceId, when);
	   if(queryResult.count() > 0) {
		   return queryResult.getSingleResult();
	   }
	   return null;
   }
   private SecUserSession attach(SecUserSession entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
