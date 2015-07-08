package org.adorsys.adstock.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adstock.jpa.StkArticleLot2StrgSctn;
import org.adorsys.adstock.repo.StkArticleLot2StrgSctnRepository;

@Stateless
public class StkArticleLot2StrgSctnEJB 
{

   @Inject
   private StkArticleLot2StrgSctnRepository repository;

   public StkArticleLot2StrgSctn create(StkArticleLot2StrgSctn entity)
   {
      return repository.save(attach(entity));
   }

   public StkArticleLot2StrgSctn deleteById(String id)
   {
      StkArticleLot2StrgSctn entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public StkArticleLot2StrgSctn update(StkArticleLot2StrgSctn entity)
   {
      return repository.save(attach(entity));
   }

   public StkArticleLot2StrgSctn findById(String id)
   {
      return repository.findBy(id);
   }

   public List<StkArticleLot2StrgSctn> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<StkArticleLot2StrgSctn> findBy(StkArticleLot2StrgSctn entity, int start, int max, SingularAttribute<StkArticleLot2StrgSctn, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(StkArticleLot2StrgSctn entity, SingularAttribute<StkArticleLot2StrgSctn, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<StkArticleLot2StrgSctn> findByLike(StkArticleLot2StrgSctn entity, int start, int max, SingularAttribute<StkArticleLot2StrgSctn, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(StkArticleLot2StrgSctn entity, SingularAttribute<StkArticleLot2StrgSctn, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private StkArticleLot2StrgSctn attach(StkArticleLot2StrgSctn entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   
   public List<StkArticleLot2StrgSctn> findByArtPicAndLotPic(String artPic, String lotPic){
	   return repository.findByArtPicAndLotPic(artPic, lotPic);
   }
   
   public List<StkArticleLot2StrgSctn> findByArtPicAndStrgSection(String artPic, String strgSection) {
	   return repository.findByArtPicAndStrgSection(artPic, strgSection);
   }
	
	public List<StkArticleLot2StrgSctn> findByStrgSection(String strgSection) {
		return repository.findByStrgSection(strgSection).getResultList();
	}
	
	public StkArticleLot2StrgSctn findByStrgSectionAndLotPicAndArtPic(String strgSection, String lotPic, String artPic){
		String primaryKey = StkArticleLot2StrgSctn.toId(artPic, lotPic, strgSection);
		return repository.findBy(primaryKey);
	}

	public void outofStock(String artPic, String lotPic, String section) {
		// TODO Auto-generated method stub
		
	}
}
