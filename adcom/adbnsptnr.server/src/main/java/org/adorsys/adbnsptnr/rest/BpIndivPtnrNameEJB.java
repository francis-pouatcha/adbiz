package org.adorsys.adbnsptnr.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbnsptnr.jpa.BpIndivPtnrName;
import org.adorsys.adbnsptnr.repo.BpIndivPtnrNameRepository;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class BpIndivPtnrNameEJB 
{

   @Inject
   private BpIndivPtnrNameRepository repository;

   public BpIndivPtnrName deleteById(String id)
   {
      BpIndivPtnrName entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public BpIndivPtnrName update(BpIndivPtnrName entity)
   {
	   if(entity==null) return null;
	   if(StringUtils.isBlank(entity.getId()))
		   return repository.save(attach(entity));
	   
	   BpIndivPtnrName found = findById(entity.getId());
	   if(found==null) {// persist
		   return repository.save(attach(entity));
	   }
	   if(found.updateContent(entity))
		   return repository.save(found);
	   return found;
   }

   public BpIndivPtnrName findById(String id)
   {
      return repository.findBy(id);
   }

   public List<BpIndivPtnrName> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<BpIndivPtnrName> findBy(BpIndivPtnrName entity, int start, int max, SingularAttribute<BpIndivPtnrName, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(BpIndivPtnrName entity, SingularAttribute<BpIndivPtnrName, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<BpIndivPtnrName> findByLike(BpIndivPtnrName entity, int start, int max, SingularAttribute<BpIndivPtnrName, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(BpIndivPtnrName entity, SingularAttribute<BpIndivPtnrName, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private BpIndivPtnrName attach(BpIndivPtnrName entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   public BpIndivPtnrName findByIdentif(String identif, Date validOn){
	   List<BpIndivPtnrName> resultList = repository.findByIdentif(identif, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }

	public void deleteByIdentif(String ptnrNbr) {
		List<BpIndivPtnrName> resultList = repository.findAllByIdentif(ptnrNbr).getResultList();
		for (BpIndivPtnrName bpIndivPtnrName : resultList) {
			repository.remove(bpIndivPtnrName);
		}
	}
}
