package org.adorsys.adprocmt.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adprocmt.jpa.PrcmtDlvryArtPrcssng;
import org.adorsys.adprocmt.repo.PrcmtDlvryArtPrcssngRepository;

@Stateless
public class PrcmtDlvryArtPrcssngEJB
{

   @Inject
   private PrcmtDlvryArtPrcssngRepository repository;

   public PrcmtDlvryArtPrcssng create(PrcmtDlvryArtPrcssng entity)
   {
      return repository.save(attach(entity));
   }

   public PrcmtDlvryArtPrcssng deleteById(String id)
   {
      PrcmtDlvryArtPrcssng entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   private PrcmtDlvryArtPrcssng update(PrcmtDlvryArtPrcssng entity)
   {
      return repository.save(attach(entity));
   }

   @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
   public boolean lock(PrcmtDlvryArtPrcssng entity){
	   PrcmtDlvryArtPrcssng found = findById(entity.getId());
	   if(found!=null && found.getVersion()==entity.getVersion()){
		   try {
			   update(found).getId();
			   return true;
		   } catch (Exception e){
			   return false;
		   }
	   }
	   return false;
   }   
   
   public PrcmtDlvryArtPrcssng unlock(PrcmtDlvryArtPrcssng entity){
	   entity.setPrcssngEndTme(null);
	   entity.setPrcssngAgent(null);
	   return update(entity);
   }   
   
   public PrcmtDlvryArtPrcssng findById(String id)
   {
      return repository.findBy(id);
   }

   public List<PrcmtDlvryArtPrcssng> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<PrcmtDlvryArtPrcssng> findBy(PrcmtDlvryArtPrcssng entity, int start, int max, SingularAttribute<PrcmtDlvryArtPrcssng, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(PrcmtDlvryArtPrcssng entity, SingularAttribute<PrcmtDlvryArtPrcssng, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<PrcmtDlvryArtPrcssng> findByLike(PrcmtDlvryArtPrcssng entity, int start, int max, SingularAttribute<PrcmtDlvryArtPrcssng, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(PrcmtDlvryArtPrcssng entity, SingularAttribute<PrcmtDlvryArtPrcssng, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private PrcmtDlvryArtPrcssng attach(PrcmtDlvryArtPrcssng entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   
   public List<PrcmtDlvryArtPrcssng> findByDlvryNbr(String dlvryNbr)
   {
      return repository.findByDlvryNbr(dlvryNbr);
   }
   
}
