package org.adorsys.adsales.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcore.utils.SequenceGenerator;
import org.adorsys.adsales.jpa.SlsSalesOrder;
import org.adorsys.adsales.jpa.SlsSalesOrderSearchInput;
import org.adorsys.adsales.repo.SlsSalesOrderRepository;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class SlsSalesOrderEJB
{

   @Inject
   private SlsSalesOrderRepository repository;   
   
   @Inject
	private EntityManager em;
   
   private static final String FIND_CUSTOM_QUERY = "SELECT s FROM SlsSalesOrder AS s";
   private static final String FIND_CUSTOM_PARTNER_QUERY = "SELECT s FROM SlsSalesOrder AS s SlsSOPtnr AS p";
   private static final String COUNT_CUSTOM_QUERY = "SELECT count(s.id) FROM SlsSalesOrder AS s";

   public SlsSalesOrder create(SlsSalesOrder entity)
   {
	   if (StringUtils.isBlank(entity.getSoNbr())) {
			entity.setSoNbr(SequenceGenerator
					.getSequence(SequenceGenerator.SALE_SEQUENCE_PREFIXE));
		}

		entity.setId(entity.getSoNbr());
		entity.setIdentif(entity.getSoNbr());	
		entity = repository.save(attach(entity));
		
		/*SlsSalesOrderEvtData evtData = new SlsSalesOrderEvtData();	
		entity.copyTo(evtData);
		evtData.setId(entity.getId());
		evtData.setIdentif(entity.getIdentif());
		evtDataEJB.create(evtData);*/
		return entity;
   }
   
   public SlsSalesOrder deleteById(String id)
   {
      SlsSalesOrder entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public SlsSalesOrder update(SlsSalesOrder entity)
   {
      return repository.save(attach(entity));
   }

   public SlsSalesOrder findById(String id)
   {
      return repository.findBy(id);
   }

   public List<SlsSalesOrder> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<SlsSalesOrder> findBy(SlsSalesOrder entity, int start, int max, SingularAttribute<SlsSalesOrder, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(SlsSalesOrder entity, SingularAttribute<SlsSalesOrder, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<SlsSalesOrder> findByLike(SlsSalesOrder entity, int start, int max, SingularAttribute<SlsSalesOrder, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(SlsSalesOrder entity, SingularAttribute<SlsSalesOrder, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }
   
   
   public StringBuilder preprocessQuery(String findOrCount, SlsSalesOrderSearchInput searchInput){
	   
	   SlsSalesOrder entity = searchInput.getEntity();
	   String whereClause = " WHERE ";
	   String andClause = " AND ";

		StringBuilder qBuilder = new StringBuilder(findOrCount);
		boolean whereSet = false;
		if(searchInput.getFieldNames().contains("soNbr") && StringUtils.isNotBlank(entity.getSoNbr())){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("s.soNbr=:soNbr");
		}
		if(searchInput.getFieldNames().contains("acsngUser") && StringUtils.isNotBlank(entity.getAcsngUser())){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("s.acsngUser=:acsngUser");
		}
		if(searchInput.getFieldNames().contains("soStatus") && entity.getSoStatus()!=null){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("s.soStatus=:soStatus");
		}
		if(searchInput.getSlsSODtFrom()!=null){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("s.soDt >= :slsSODtFrom");
		}
		if(searchInput.getSlsSODtTo()!=null){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("s.soDt <= :slsSODtTo");
		}
		
		return qBuilder;
   }
   
   public void setParameters(SlsSalesOrderSearchInput searchInput, Query query){
	   SlsSalesOrder entity = searchInput.getEntity();
	   if(searchInput.getFieldNames().contains("soNbr") && StringUtils.isNotBlank(entity.getSoNbr())){
			query.setParameter("soNbr", entity.getSoNbr());
		}
		if(searchInput.getFieldNames().contains("acsngUser") && StringUtils.isNotBlank(entity.getAcsngUser())){
			query.setParameter("acsngUser", entity.getAcsngUser());
		}
		if(searchInput.getFieldNames().contains("soStatus") && entity.getSoStatus()!=null){
			query.setParameter("soStatus", entity.getSoStatus());
		}
	   
		if(searchInput.getSlsSODtFrom()!=null){
			query.setParameter("slsSODtFrom", searchInput.getSlsSODtFrom());
		}
		if(searchInput.getSlsSODtTo()!=null){
			query.setParameter("slsSODtTo", searchInput.getSlsSODtTo());
		}
	   
   }
   
   
   public List<SlsSalesOrder> findCustom(SlsSalesOrderSearchInput searchInput){
	   StringBuilder qBuilder = null;
	   if(StringUtils.isNotBlank(searchInput.getPtnrNbr())){
		   qBuilder= preprocessQuery(FIND_CUSTOM_PARTNER_QUERY, searchInput);
		   qBuilder.append(" AND s.soNbr = p.soNbr");
	   }else {
		   qBuilder = preprocessQuery(FIND_CUSTOM_QUERY, searchInput);
     	}
	   TypedQuery<SlsSalesOrder> query = em.createQuery(qBuilder.toString(), SlsSalesOrder.class);
	   setParameters(searchInput, query);
	   int start = searchInput.getStart();
		int max = searchInput.getMax();

		if(start < 0)  start = 0;
		query.setFirstResult(start);
		if(max >= 1) 
			query.setMaxResults(max);
		
		return query.getResultList();
   }
   
   public Long countCustom(SlsSalesOrderSearchInput searchInput)
	{
		StringBuilder qBuilder = preprocessQuery(COUNT_CUSTOM_QUERY, searchInput);
		TypedQuery<Long> query = em.createQuery(qBuilder.toString(), Long.class);
		setParameters(searchInput, query);
		return query.getSingleResult();
	}
   
   

   private SlsSalesOrder attach(SlsSalesOrder entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
