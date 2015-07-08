package org.adorsys.adsales.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcore.enums.CoreProcessStatusEnum;
import org.adorsys.adcore.utils.SequenceGenerator;
import org.adorsys.adsales.jpa.SlsInvcePymtStatus;
import org.adorsys.adsales.jpa.SlsInvoice;
import org.adorsys.adsales.jpa.SlsInvoiceSearchInput;
import org.adorsys.adsales.repo.SlsInvoiceRepository;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class SlsInvoiceEJB
{

   @Inject
   private SlsInvoiceRepository repository;
   
   @Inject
	private EntityManager em;
   
   private static final String FIND_CUSTOM_QUERY = "SELECT s FROM SlsInvoice AS s";
   private static final String FIND_CUSTOM_PARTNER_QUERY = "SELECT s FROM SlsInvoice AS s, SlsInvcePtnr AS p";
   private static final String COUNT_CUSTOM_QUERY = "SELECT count(s.id) FROM SlsInvoice AS s";

   public SlsInvoice create(SlsInvoice entity)
   {
	   if (StringUtils.isBlank(entity.getInvceNbr())) {
			entity.setInvceNbr(SequenceGenerator
					.getSequence(SequenceGenerator.DEBTS_INVOICE_SEQUENCE_PREFIXE));
		}
		entity.setId(entity.getInvceNbr());
		entity.setIdentif(entity.getInvceNbr());	
		entity.setInvceDelivered(false);
		entity.setInvcePymntStatus(SlsInvcePymtStatus.CREDIT);// by default, all invoice will be credit first
		entity = repository.save(attach(entity));
		return entity;
   }

   public SlsInvoice deleteById(String id)
   {
      SlsInvoice entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public SlsInvoice update(SlsInvoice entity)
   {
      return repository.save(attach(entity));
   }

   public SlsInvoice findById(String id)
   {
      return repository.findBy(id);
   }

   public List<SlsInvoice> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<SlsInvoice> findBy(SlsInvoice entity, int start, int max, SingularAttribute<SlsInvoice, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(SlsInvoice entity, SingularAttribute<SlsInvoice, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<SlsInvoice> findByLike(SlsInvoice entity, int start, int max, SingularAttribute<SlsInvoice, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }
   
   public List<SlsInvoice> findByLikePay(SlsInvoice entity, int start, int max, SingularAttribute<SlsInvoice, ?>[] attributes)
   {
		List<SlsInvoice> slsInvoices = new ArrayList<SlsInvoice>();
		//please change this method, just add a repository function, find closed and order by creation date
		for (SlsInvoice slsInvoice : repository.findByLike(entity, start, max, attributes)) {
			String statusOfInvoice = slsInvoice.getInvceStatus();
			
			if(statusOfInvoice.equals(CoreProcessStatusEnum.CLOSED.name()))
				slsInvoices.add(slsInvoice);
		}

      return slsInvoices;
   }

   public Long countByLike(SlsInvoice entity, SingularAttribute<SlsInvoice, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private SlsInvoice attach(SlsInvoice entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   
   
public StringBuilder preprocessQuery(String findOrCount, SlsInvoiceSearchInput searchInput){
	   
	   SlsInvoice entity = searchInput.getEntity();
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
		if(searchInput.getFieldNames().contains("invceNbr") && StringUtils.isNotBlank(entity.getInvceNbr())){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("s.invceNbr=:invceNbr");
		}
		if(searchInput.getFieldNames().contains("invcePymntStatus") && StringUtils.isNotBlank(String.valueOf(entity.getInvcePymntStatus()))){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("s.invcePymntStatus=:invcePymntStatus");
		}
		if(searchInput.getFieldNames().contains("slsInvceStatus") && StringUtils.isNotBlank(String.valueOf(entity.getInvcePymntStatus()))){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("s.slsInvceStatus=:slsInvceStatus");
		}
		if(searchInput.getFieldNames().contains("invceDelivered") && StringUtils.isNotBlank(String.valueOf(entity.getInvceDelivered()))){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("s.invceDelivered=:invceDelivered");
		}
		if(searchInput.getInvceDtFrom()!=null){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("s.invceDt >= :invceDtFrom");
		}
		if(searchInput.getInvceDtTo()!=null){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("s.invceDt <= :invceDtTo");
		}
		
		return qBuilder;
   }
   
   public void setParameters(SlsInvoiceSearchInput searchInput, Query query){
	    SlsInvoice entity = searchInput.getEntity();
	   if(searchInput.getFieldNames().contains("soNbr") && StringUtils.isNotBlank(entity.getSoNbr())){
			query.setParameter("soNbr", entity.getSoNbr());
		}
	   if(searchInput.getFieldNames().contains("invceNbr") && StringUtils.isNotBlank(entity.getSoNbr())){
			query.setParameter("invceNbr", entity.getInvceNbr());
		}
	   if(searchInput.getFieldNames().contains("invcePymntStatus") && StringUtils.isNotBlank(String.valueOf(entity.getInvcePymntStatus()))){
			query.setParameter("invcePymntStatus", entity.getInvcePymntStatus());
		}
	   if(searchInput.getFieldNames().contains("slsInvceStatus") && StringUtils.isNotBlank(String.valueOf(entity.getInvceStatus()))){
			query.setParameter("invceStatus", entity.getInvceStatus());
		}
	   if(searchInput.getFieldNames().contains("invceDelivered") && StringUtils.isNotBlank(String.valueOf(entity.getInvceDelivered()))){
			query.setParameter("invceDelivered", entity.getInvceDelivered());
		}
		
		if(searchInput.getInvceDtFrom()!=null){
			query.setParameter("invceDtFrom", searchInput.getInvceDtFrom());
		}
		if(searchInput.getInvceDtTo()!=null){
			query.setParameter("invceDtTo", searchInput.getInvceDtTo());
		}
	   
   }
   
   
   public List<SlsInvoice> findCustom(SlsInvoiceSearchInput searchInput){
	   StringBuilder qBuilder = null;
	   if(StringUtils.isNotBlank(searchInput.getPtnrNbr())){
		   qBuilder= preprocessQuery(FIND_CUSTOM_PARTNER_QUERY, searchInput);
		   qBuilder.append(" AND s.invceNbr = p.invceNbr");
	   }else {
		   qBuilder = preprocessQuery(FIND_CUSTOM_QUERY, searchInput);
     	}
	   TypedQuery<SlsInvoice> query = em.createQuery(qBuilder.toString(), SlsInvoice.class);
	   setParameters(searchInput, query);
	   int start = searchInput.getStart();
		int max = searchInput.getMax();

		if(start < 0)  start = 0;
		query.setFirstResult(start);
		if(max >= 1) 
			query.setMaxResults(max);
		
		return query.getResultList();
   }
   
   public Long countCustom(SlsInvoiceSearchInput searchInput)
	{
		StringBuilder qBuilder = preprocessQuery(COUNT_CUSTOM_QUERY, searchInput);
		TypedQuery<Long> query = em.createQuery(qBuilder.toString(), Long.class);
		setParameters(searchInput, query);
		return query.getSingleResult();
	}

public SlsInvoice saleDelivery(SlsInvoice entity) {
	entity.setInvceDelivered(true);
	entity = repository.save(entity);
	
	return entity;
}
   
}
