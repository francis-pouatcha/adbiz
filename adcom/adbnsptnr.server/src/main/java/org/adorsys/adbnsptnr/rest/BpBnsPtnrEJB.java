package org.adorsys.adbnsptnr.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.jpa.BaseCountryName;
import org.adorsys.adbase.rest.BaseCountryNameEJB;
import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adbnsptnr.jpa.BpBnsPtnr;
import org.adorsys.adbnsptnr.jpa.BpIndivPtnrName;
import org.adorsys.adbnsptnr.jpa.BpLegalPtnrId;
import org.adorsys.adbnsptnr.jpa.BpPtnrType;
import org.adorsys.adbnsptnr.repo.BpBnsPtnrRepository;
import org.adorsys.adcore.utils.SequenceGenerator;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class BpBnsPtnrEJB 
{
   @Inject
   private BpBnsPtnrRepository repository;
   
   @Inject
   private BpIndivPtnrNameEJB indivPtnrNameEJB;
   
   @Inject
   private BpLegalPtnrIdEJB legalPtnrIdEJB;

   public BpBnsPtnr create(BpBnsPtnr entity)
   {	
	   if(StringUtils.isBlank(entity.getPtnrNbr())){
		   entity.setPtnrNbr(SequenceGenerator.getSequence(SequenceGenerator.BUSINESS_PARTNER_SEQUENCE_PREFIXE));
	   }
	   BpIndivPtnrName ptnrName = entity.getIndivPtnrName();
	   
	   BpLegalPtnrId ptnrId = entity.getLegalPtnrId();
	   if(BpPtnrType.INDIVIDUAL.equals(entity.getPtnrType()) && ptnrName!=null){
		   entity.setFullName(ptnrName.makeFullName());
	   } else if (BpPtnrType.LEGAL.equals(entity.getPtnrType()) && ptnrId!=null){
		   entity.setFullName(ptnrId.getCpnyName());
		   ptnrId.setShortName(entity.getFullName());
	   }
	   
	   
	   if(entity.getCountryName()!=null && !StringUtils.equals(entity.getCtryOfRsdnc(), entity.getCountryName().getIso3())){
		   entity.setCtryOfRsdnc(entity.getCountryName().getIso3());
	   }
	   entity = repository.save(attach(entity));
	   
	   if(BpPtnrType.INDIVIDUAL.equals(entity.getPtnrType()) && ptnrName!=null){
		   ptnrName.setPtnrNbr(entity.getPtnrNbr());
		   ptnrName = indivPtnrNameEJB.update(ptnrName);
		   entity.setIndivPtnrName(ptnrName);
	   } else if (BpPtnrType.LEGAL.equals(entity.getPtnrType()) && ptnrId!=null){
		   ptnrId = legalPtnrIdEJB.update(ptnrId);
		   entity.setLegalPtnrId(ptnrId);
	   }
	   return entity;
   }

	public BpBnsPtnr deleteById(String id) {
		BpBnsPtnr entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
			indivPtnrNameEJB.deleteByIdentif(entity.getPtnrNbr());
			legalPtnrIdEJB.deleteByIdentif(entity.getPtnrNbr());
		}
		return entity;
	}

   public BpBnsPtnr update(BpBnsPtnr entity)
   {
	   BpIndivPtnrName ptnrName = entity.getIndivPtnrName();
	   BpLegalPtnrId ptnrId = entity.getLegalPtnrId();
	   
	   BpBnsPtnr orig = findByIdentif(entity.getIdentif());
	   boolean changed = false;
	   if(!StringUtils.equals(orig.getCtryOfRsdnc(), entity.getCtryOfRsdnc())){
		   orig.setCtryOfRsdnc(entity.getCtryOfRsdnc());
		   changed=true;
	   }
	   
	   if(entity.getCountryName()!=null && !StringUtils.equals(entity.getCtryOfRsdnc(), entity.getCountryName().getIso3())){
		   entity.setCtryOfRsdnc(entity.getCountryName().getIso3());
		   changed=true;
	   }

	   
	   if(BpPtnrType.INDIVIDUAL.equals(orig.getPtnrType()) && ptnrName!=null){
		   ptnrName = indivPtnrNameEJB.update(ptnrName);
		   if(!StringUtils.equals(orig.getFullName(), ptnrName.makeFullName())){
			   orig.setFullName(ptnrName.makeFullName());
			   changed=true;
		   }
	   } else if (BpPtnrType.LEGAL.equals(orig.getPtnrType()) && ptnrId!=null){
		   ptnrId = legalPtnrIdEJB.update(ptnrId);
		   if(!StringUtils.equals(orig.getFullName(), ptnrId.getCpnyName())){
			   orig.setFullName(ptnrId.getCpnyName());
			   changed=true;
		   }
	   }
	   if(changed)
		   orig = repository.save(orig);

	   if(BpPtnrType.INDIVIDUAL.equals(orig.getPtnrType()) && ptnrName!=null){
		   orig.setIndivPtnrName(ptnrName);
	   } else if (BpPtnrType.LEGAL.equals(orig.getPtnrType()) && ptnrId!=null){
		   orig.setLegalPtnrId(ptnrId);
	   }
	   
	   orig.setCountryName(entity.getCountryName());
	   return orig;
   }
   
   public BpBnsPtnr findById(String id)
   {
      return detach(repository.findBy(id));
   }

   public List<BpBnsPtnr> listAll(int start, int max)
   {
      return detach(repository.findAll(start, max));
   }
   
   public List<BpBnsPtnr> findByPtnrNbr(String ptnrNbr){
	   return detach(repository.findByPtnrNbr(ptnrNbr).getResultList());
   }
   
   public List<BpBnsPtnr> findByFullName(String fullName){
	   return detach(repository.findByFullName(fullName).getResultList());
   }
   
   

   public Long count()
   {
      return repository.count();
   }

   public List<BpBnsPtnr> findBy(BpBnsPtnr entity, int start, int max, SingularAttribute<BpBnsPtnr, ?>[] attributes)
   {
      return detach(repository.findBy(entity, start, max, attributes));
   }

   public Long countBy(BpBnsPtnr entity, SingularAttribute<BpBnsPtnr, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<BpBnsPtnr> findByLike(BpBnsPtnr entity, int start, int max, SingularAttribute<BpBnsPtnr, ?>[] attributes)
   {
      return detach(repository.findByLike(entity, start, max, attributes));
   }

   public Long countByLike(BpBnsPtnr entity, SingularAttribute<BpBnsPtnr, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private BpBnsPtnr attach(BpBnsPtnr entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   public BpBnsPtnr findByIdentif(String identif){
	   return findById(identif);
   }

   	@Inject
   	private BaseCountryNameEJB countryNameEJB;
   	@Inject
   	private SecurityUtil securityUtil;
   
	private BpBnsPtnr detach(BpBnsPtnr entity) {
		if(entity==null) return null;
		Date validOn = new Date();
		if (BpPtnrType.INDIVIDUAL.equals(entity.getPtnrType())) {
			BpIndivPtnrName ptnrName = indivPtnrNameEJB.findByIdentif(
					entity.getPtnrNbr(), validOn);
			entity.setIndivPtnrName(ptnrName);
		} else if (BpPtnrType.LEGAL.equals(entity.getPtnrType())) {
			BpLegalPtnrId ptnrId = legalPtnrIdEJB.findByIdentif(
					entity.getPtnrNbr(), validOn);
			entity.setLegalPtnrId(ptnrId);
		}
		
	   if(entity.getCountryName()==null || !StringUtils.equals(entity.getCtryOfRsdnc(), entity.getCountryName().getIso3())){
		   String userLange = securityUtil.getUserLange();
		   BaseCountryName baseCountryName = countryNameEJB.findById(BaseCountryName.toIdentif(entity.getCtryOfRsdnc(), userLange));
		   entity.setCountryName(baseCountryName);
	   }

		return entity;
	}
	public List<BpBnsPtnr> detach(List<BpBnsPtnr> entities){
		for (BpBnsPtnr bpBnsPtnr : entities) {
			detach(bpBnsPtnr);
		}
		return entities;
	}
}
