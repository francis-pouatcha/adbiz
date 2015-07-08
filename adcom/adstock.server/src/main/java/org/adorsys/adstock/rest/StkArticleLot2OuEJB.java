package org.adorsys.adstock.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adstock.jpa.StkArticleLot2Ou;
import org.adorsys.adstock.repo.StkArticleLot2OuRepository;

@Stateless
public class StkArticleLot2OuEJB 
{

   @Inject
   private StkArticleLot2OuRepository repository;

   public StkArticleLot2Ou create(StkArticleLot2Ou entity)
   {
      return repository.save(attach(entity));
   }

   public StkArticleLot2Ou deleteById(String id)
   {
      StkArticleLot2Ou entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public StkArticleLot2Ou update(StkArticleLot2Ou entity)
   {
      return repository.save(attach(entity));
   }

   public StkArticleLot2Ou findById(String id)
   {
      return repository.findBy(id);
   }

   public List<StkArticleLot2Ou> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<StkArticleLot2Ou> findBy(StkArticleLot2Ou entity, int start, int max, SingularAttribute<StkArticleLot2Ou, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(StkArticleLot2Ou entity, SingularAttribute<StkArticleLot2Ou, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<StkArticleLot2Ou> findByLike(StkArticleLot2Ou entity, int start, int max, SingularAttribute<StkArticleLot2Ou, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(StkArticleLot2Ou entity, SingularAttribute<StkArticleLot2Ou, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private StkArticleLot2Ou attach(StkArticleLot2Ou entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   public List<StkArticleLot2Ou> findByArtPicAndLotPic(String artPic, String lotPic){
	   return repository.findByArtPicAndLotPic(artPic, lotPic);
   }
   
   public List<StkArticleLot2Ou> findByArtPicLikeAndOuLike(String artPic, String ouId) {
	   return repository.findByArtPicLikeAndOuLike(artPic, ouId);
   }
   
}
