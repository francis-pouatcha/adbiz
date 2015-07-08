package org.adorsys.adstock.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adstock.jpa.StkArticleLot2StrgSctn;
import org.adorsys.adstock.repo.StkArticleLot2StrgSctnRepository;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class StkArticleLot2StrgSctnLookup 
{

   @Inject
   private StkArticleLot2StrgSctnRepository repository;
   
   @Inject
   private StkStrgSectionDetachHelper detachHelper;


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
	   List<StkArticleLot2StrgSctn> found = repository.findBy(entity, start, max, attributes);
	   return detachHelper.detachStrSections(found);
   }

   public Long countBy(StkArticleLot2StrgSctn entity, SingularAttribute<StkArticleLot2StrgSctn, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<StkArticleLot2StrgSctn> findByLike(StkArticleLot2StrgSctn entity, int start, int max, SingularAttribute<StkArticleLot2StrgSctn, ?>[] attributes)
   {
	   List<StkArticleLot2StrgSctn> found = repository.findByLike(entity, start, max, attributes);
	   return detachHelper.detachStrSections(found);
   }

   public Long countByLike(StkArticleLot2StrgSctn entity, SingularAttribute<StkArticleLot2StrgSctn, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   public Long countByArtPic(String artPic){
	   return repository.findByArtPic(artPic).count();
   }
   
   public List<StkArticleLot2StrgSctn> findByArtPic(String artPic, int start, int max){
	   List<StkArticleLot2StrgSctn> found = repository.findByArtPic(artPic).firstResult(start).maxResults(max).getResultList();
	   return detachHelper.detachStrSections(found);
   }
   
   public List<StkArticleLot2StrgSctn> findByArtPic(String artPic){
	   List<StkArticleLot2StrgSctn> found = repository.findByArtPic(artPic).getResultList();
	   return detachHelper.detachStrSections(found);
   }
   
   public List<StkArticleLot2StrgSctn> findByArtPicAndLotPic(String artPic, String lotPic){
	   List<StkArticleLot2StrgSctn> found = repository.findByArtPicAndLotPic(artPic, lotPic);
	   return detachHelper.detachStrSections(found);
   }
   
   public List<StkArticleLot2StrgSctn> findByArtPicAndStrgSection(String artPic, String strgSection) {
	   List<StkArticleLot2StrgSctn> found = repository.findByArtPicAndStrgSection(artPic, strgSection);
	   return detachHelper.detachStrSections(found);
   }
	
	public List<StkArticleLot2StrgSctn> findByStrgSection(String strgSection) {
		List<StkArticleLot2StrgSctn> found = repository.findByStrgSection(strgSection).getResultList();
		   return detachHelper.detachStrSections(found);
	}
	public List<StkArticleLot2StrgSctn> findByStrgSectionSorted(String strgSection, int start, int max) {
		List<StkArticleLot2StrgSctn> found = repository.findByStrgSection(strgSection).orderAsc("artName").firstResult(start).maxResults(max).getResultList();
		   return detachHelper.detachStrSections(found);
	}
	public Long countByStrgSection(String strgSection) {
		return repository.countByStrgSection(strgSection);
	}
	public List<StkArticleLot2StrgSctn> findByStrgSectionAndArtNameRange(String strgSection, String rangeStart, String rangeEnd, int start, int max) {
		List<StkArticleLot2StrgSctn> found = repository.findByStrgSectionAndArtNameRange(strgSection, rangeStart, rangeEnd).orderAsc("artName").firstResult(start).maxResults(max).getResultList();
		   return detachHelper.detachStrSections(found);
	}
	public Long countByStrgSectionAndArtNameRange(String strgSection, String rangeStart, String rangeEnd) {
		return repository.countByStrgSectionAndArtNameRange(strgSection, rangeStart, rangeEnd);
	}	
	public StkArticleLot2StrgSctn findByStrgSectionAndLotPicAndArtPic(String strgSection, String lotPic, String artPic){
		String primaryKey = StkArticleLot2StrgSctn.toId(artPic, lotPic, strgSection);
		StkArticleLot2StrgSctn found = repository.findBy(primaryKey);
		return detachHelper.detach(found);
	}

	public List<StkArticleLot2StrgSctn> findByArtNameRange(String rangeStart, String rangeEnd, int start, int max) {
		List<StkArticleLot2StrgSctn> found = repository.findByArtNameRange(rangeStart, rangeEnd).orderAsc("artName").firstResult(start).maxResults(max).getResultList();
		   return detachHelper.detachStrSections(found);
	}
	public Long countByArtNameRange(String rangeStart, String rangeEnd) {
		return repository.countByArtNameRange(rangeStart, rangeEnd);
	}	
}
