package org.adorsys.adprocmt.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adprocmt.jpa.PrcmtProcOrderHstry;
import org.adorsys.adprocmt.repo.PrcmtProcOrderHstryRepository;

@Stateless
public class PrcmtProcOrderHstryEJB
{

   @Inject
   private PrcmtProcOrderHstryRepository repository;

   public PrcmtProcOrderHstry create(PrcmtProcOrderHstry entity)
   {
      return repository.save(attach(entity));
   }

   public PrcmtProcOrderHstry deleteById(String id)
   {
      PrcmtProcOrderHstry entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public PrcmtProcOrderHstry update(PrcmtProcOrderHstry entity)
   {
      return repository.save(attach(entity));
   }

   public PrcmtProcOrderHstry findById(String id)
   {
      return repository.findBy(id);
   }

   public List<PrcmtProcOrderHstry> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<PrcmtProcOrderHstry> findBy(PrcmtProcOrderHstry entity, int start, int max, SingularAttribute<PrcmtProcOrderHstry, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(PrcmtProcOrderHstry entity, SingularAttribute<PrcmtProcOrderHstry, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<PrcmtProcOrderHstry> findByLike(PrcmtProcOrderHstry entity, int start, int max, SingularAttribute<PrcmtProcOrderHstry, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(PrcmtProcOrderHstry entity, SingularAttribute<PrcmtProcOrderHstry, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private PrcmtProcOrderHstry attach(PrcmtProcOrderHstry entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
