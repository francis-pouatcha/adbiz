package org.adorsys.adbnsptnr.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.jpa.BaseCountryName;
import org.adorsys.adbase.rest.BaseCountryNameEJB;
import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adbnsptnr.jpa.BpPtnrIdDtls;
import org.adorsys.adbnsptnr.repo.BpPtnrIdDtlsRepository;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class BpPtnrIdDtlsEJB 
{

   @Inject
   private BpPtnrIdDtlsRepository repository;

   public BpPtnrIdDtls create(BpPtnrIdDtls entity)
   {
	   if(entity.getIssuingCtryName()!=null && !StringUtils.equals(entity.getIssuingCtry(), entity.getIssuingCtryName().getIso3())){
		   entity.setIssuingCtry(entity.getIssuingCtryName().getIso3());
	   }
      return repository.save(attach(entity));
   }

   public BpPtnrIdDtls deleteById(String id)
   {
      BpPtnrIdDtls entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public BpPtnrIdDtls update(BpPtnrIdDtls entity)
   {
	   if(entity.getIssuingCtryName()!=null && !StringUtils.equals(entity.getIssuingCtry(), entity.getIssuingCtryName().getIso3())){
		   entity.setIssuingCtry(entity.getIssuingCtryName().getIso3());
	   }
      return repository.save(attach(entity));
   }

   public BpPtnrIdDtls findById(String id)
   {
	  return detach(repository.findBy(id));
   }

   public List<BpPtnrIdDtls> listAll(int start, int max)
   {
	      return detach(repository.findAll(start, max));
   }

   public Long count()
   {
      return repository.count();
   }

   public List<BpPtnrIdDtls> findBy(BpPtnrIdDtls entity, int start, int max, SingularAttribute<BpPtnrIdDtls, ?>[] attributes)
   {
	      return detach(repository.findBy(entity, start, max, attributes));
   }

   public Long countBy(BpPtnrIdDtls entity, SingularAttribute<BpPtnrIdDtls, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<BpPtnrIdDtls> findByLike(BpPtnrIdDtls entity, int start, int max, SingularAttribute<BpPtnrIdDtls, ?>[] attributes)
   {
	  return detach(repository.findByLike(entity, start, max, attributes));
   }

   public Long countByLike(BpPtnrIdDtls entity, SingularAttribute<BpPtnrIdDtls, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private BpPtnrIdDtls attach(BpPtnrIdDtls entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   public BpPtnrIdDtls findByIdentif(String identif, Date validOn){
	   List<BpPtnrIdDtls> resultList = repository.findByIdentif(identif, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }
   
  	@Inject
  	private BaseCountryNameEJB countryNameEJB;
  	@Inject
  	private SecurityUtil securityUtil;
   
   private BpPtnrIdDtls detach(BpPtnrIdDtls entity){
	   if(entity.getIssuingCtryName()==null || !StringUtils.equals(entity.getIssuingCtry(), entity.getIssuingCtryName().getIso3())){
		   String userLange = securityUtil.getUserLange();
		   BaseCountryName baseCountryName = countryNameEJB.findById(BaseCountryName.toIdentif(entity.getIssuingCtry(), userLange));
		   entity.setIssuingCtryName(baseCountryName);
	   }
	   return entity;
   }
   private List<BpPtnrIdDtls> detach(List<BpPtnrIdDtls> entities){
	   for (BpPtnrIdDtls bpPtnrIdDtls : entities) {
		   detach(bpPtnrIdDtls);
	   }
	   return entities;
   }
}
