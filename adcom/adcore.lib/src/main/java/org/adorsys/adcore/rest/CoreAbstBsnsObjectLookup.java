package org.adorsys.adcore.rest;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.adorsys.adcore.jpa.CoreAbstBsnsObject;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchInput;
import org.adorsys.adcore.repo.CoreAbstBsnsObjectRepo;
import org.adorsys.adcore.repo.CoreAbstIdentifDataRepo;
import org.apache.commons.lang3.StringUtils;

public abstract class CoreAbstBsnsObjectLookup<E extends CoreAbstBsnsObject>
		extends CoreAbstIdentifiedLookup<E> {

	protected abstract CoreAbstBsnsObjectRepo<E> getBsnsRepo();
	protected abstract Class<E> getBsnsObjClass();
	protected abstract EntityManager getEntityManager();

	protected CoreAbstIdentifDataRepo<E> getRepo() {
		return getBsnsRepo();
	}

	public Long countByTxDtBetween(Date from, Date to) {
		return getBsnsRepo().findByTxDtBetween(from, to).count();
	}

	public List<E> findByTxDtBetween(Date from, Date to, int start, int max) {
		return getBsnsRepo().findByTxDtBetween(from, to).firstResult(start)
				.maxResults(max).getResultList();
	}

	public Long countByPreparedDtIsNull() {
		return getBsnsRepo().findByPreparedDtIsNull().count();
	}

	public List<E> findByPreparedDtIsNull(int start, int max) {
		return getBsnsRepo().findByPreparedDtIsNull().firstResult(start)
				.maxResults(max).getResultList();
	}

	public Long countByCntnrIdentifIsNotNullAndMergedDateIsNull() {
		return getBsnsRepo().findByCntnrIdentifIsNotNullAndMergedDateIsNull()
				.count();
	}

	public List<E> findByCntnrIdentifIsNotNullAndMergedDateIsNull(int start,
			int max) {
		return getBsnsRepo().findByCntnrIdentifIsNotNullAndMergedDateIsNull()
				.firstResult(start).maxResults(max).getResultList();
	}

	public Long countByCntnrIdentifIsNotNullAndMergedDateIsNotNull() {
		return getBsnsRepo().findByCntnrIdentifIsNotNullAndMergedDateIsNotNull()
				.count();
	}

	public List<E> findByCntnrIdentifIsNotNullAndMergedDateIsNotNull(int start,
			int max) {
		return getBsnsRepo().findByCntnrIdentifIsNotNullAndMergedDateIsNotNull()
				.firstResult(start).maxResults(max).getResultList();
	}

	private final String FIND_CUSTOM_QUERY = "SELECT e FROM " + getBsnsObjClass().getSimpleName() + " AS e";
	private final String COUNT_CUSTOM_QUERY = "SELECT count(e.id) FROM " + getBsnsObjClass().getSimpleName() + " AS e";
	
	private StringBuilder preprocessQuery(String findOrCount, CoreAbstBsnsObjectSearchInput<E> searchInput){
		E entity = searchInput.getEntity();

		String whereClause = " WHERE ";
		String andClause = " AND ";

		StringBuilder qBuilder = new StringBuilder(findOrCount);
		boolean whereSet = false;
		
		if(searchInput.getFieldNames().contains("identif") && StringUtils.isNotBlank(entity.getIdentif())){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.identif=:identif");
		}

		if(searchInput.getFieldNames().contains("cntnrIdentif") && StringUtils.isNotBlank(entity.getCntnrIdentif())){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.cntnrIdentif=:cntnrIdentif");
		}
		
		if(searchInput.getFieldNames().contains("txGroup") && StringUtils.isNotBlank(entity.getTxGroup())){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("LOWER(e.txGroup) LIKE(LOWER(:txGroup))");
		}
		if(searchInput.getFieldNames().contains("txType") && entity.getTxType()!=null){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.txType=:txType");
		}
		if(searchInput.getFieldNames().contains("section") && StringUtils.isNotBlank(entity.getSection())){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("LOWER(e.section) LIKE(LOWER(:section))");
		}
		if(searchInput.getFieldNames().contains("rangeStart") && StringUtils.isNotBlank(entity.getRangeStart())){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("LOWER(e.rangeStart)<=LOWER(:rangeStart)");
		}
		if(searchInput.getFieldNames().contains("rangeEnd") && StringUtils.isNotBlank(entity.getRangeEnd())){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("LOWER(e.rangeEnd)<=LOWER(:rangeEnd)");
		}
		if(searchInput.getFieldNames().contains("descptn") && StringUtils.isNotBlank(entity.getDescptn())){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("LOWER(e.descptn) LIKE(LOWER(:descptn))");
		}
		if(searchInput.getValueDtFrom()!=null){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.valueDt >= :valueDtFrom");
		}
		if(searchInput.getValueDtTo()!=null){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.valueDt <= :valueDtTo");
		}
		if(searchInput.getIdentifFrom()!=null){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.identif >= :identifFrom");
		}
		if(searchInput.getIdentifTo()!=null){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.identif <= :identifTo");
		}

		if(searchInput.getPrchGrossPrcPreTaxFrom()!=null){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.prchGrossPrcPreTaxFrom >= :prchGrossPrcPreTaxFrom");
		}
		if(searchInput.getPrchGrossPrcPreTaxTo()!=null){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.prchGrossPrcPreTaxTo <= :prchGrossPrcPreTaxTo");
		}

		if(searchInput.getPrchRebateAmtFrom()!=null){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.prchRebateAmtFrom >= :prchRebateAmtFrom");
		}
		if(searchInput.getPrchRebateAmtTo()!=null){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.prchRebateAmtTo <= :prchRebateAmtTo");
		}

		if(searchInput.getPrchRdngDscntAmtFrom()!=null){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.prchRdngDscntAmtFrom >= :prchRdngDscntAmtFrom");
		}
		if(searchInput.getPrchRdngDscntAmtTo()!=null){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.prchRdngDscntAmtTo <= :prchRdngDscntAmtTo");
		}

		if(searchInput.getSlsGrossPrcPreTaxFrom()!=null){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.slsGrossPrcPreTaxFrom >= :slsGrossPrcPreTaxFrom");
		}
		if(searchInput.getSlsGrossPrcPreTaxTo()!=null){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.slsGrossPrcPreTaxTo <= :slsGrossPrcPreTaxTo");
		}

		if(searchInput.getSlsRebateAmtFrom()!=null){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.slsRebateAmtFrom >= :slsRebateAmtFrom");
		}
		if(searchInput.getSlsRebateAmtTo()!=null){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.slsRebateAmtTo <= :slsRebateAmtTo");
		}

		if(searchInput.getSlsRdngDscntAmtFrom()!=null){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.slsRdngDscntAmtFrom >= :slsRdngDscntAmtFrom");
		}
		if(searchInput.getSlsRdngDscntAmtFrom()!=null){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.slsRdngDscntAmtFrom <= :slsRdngDscntAmtFrom");
		}

		
		if(searchInput.getStkValPreTaxFrom()!=null){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.stkValPreTaxFrom >= :stkValPreTaxFrom");
		}
		if(searchInput.getStkValPreTaxTo()!=null){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.stkValPreTaxTo <= :stkValPreTaxTo");
		}
		
		return qBuilder;
	}
	
	public void setParameters(CoreAbstBsnsObjectSearchInput<E> searchInput, Query query)
	{
		E entity = searchInput.getEntity();

		if(searchInput.getFieldNames().contains("identif") && StringUtils.isNotBlank(entity.getIdentif())){
			query.setParameter("identif", entity.getIdentif());
		}
		if(searchInput.getFieldNames().contains("cntnrIdentif") && StringUtils.isNotBlank(entity.getCntnrIdentif())){
			query.setParameter("cntnrIdentif", entity.getCntnrIdentif());
		}
		
		if(searchInput.getFieldNames().contains("txGroup") && StringUtils.isNotBlank(entity.getTxGroup())){
			query.setParameter("txGroup", "%"+entity.getTxGroup()+"%");
		}
		if(searchInput.getFieldNames().contains("txType") && entity.getTxType()!=null){
			query.setParameter("txType", entity.getTxType());
		}
		if(searchInput.getFieldNames().contains("section") && StringUtils.isNotBlank(entity.getSection())){
			query.setParameter("section", "%"+entity.getSection()+"%");
		}
		if(searchInput.getFieldNames().contains("rangeStart") && StringUtils.isNotBlank(entity.getRangeStart())){
			query.setParameter("rangeStart", entity.getRangeStart());
		}
		if(searchInput.getFieldNames().contains("rangeEnd") && StringUtils.isNotBlank(entity.getRangeEnd())){
			query.setParameter("rangeEnd", entity.getRangeEnd());
		}
		if(searchInput.getFieldNames().contains("descptn") && StringUtils.isNotBlank(entity.getDescptn())){
			query.setParameter("descptn", "%"+entity.getDescptn()+"%");
		}
		if(searchInput.getValueDtFrom()!=null){
			query.setParameter("valueDtFrom", searchInput.getValueDtFrom());
		}
		if(searchInput.getValueDtTo()!=null){
			query.setParameter("valueDtTo", searchInput.getValueDtTo());
		}
		if(searchInput.getIdentifFrom()!=null){
			query.setParameter("identifFrom", searchInput.getIdentifFrom());
		}
		if(searchInput.getIdentifTo()!=null){
			query.setParameter("identifTo", searchInput.getIdentifTo());
		}

		if(searchInput.getPrchGrossPrcPreTaxFrom()!=null){
			query.setParameter("prchGrossPrcPreTaxFrom", searchInput.getPrchGrossPrcPreTaxFrom());
		}
		if(searchInput.getPrchGrossPrcPreTaxTo()!=null){
			query.setParameter("prchGrossPrcPreTaxTo", searchInput.getPrchGrossPrcPreTaxTo());
		}

		if(searchInput.getPrchRebateAmtFrom()!=null){
			query.setParameter("prchRebateAmtFrom", searchInput.getPrchRebateAmtFrom());
		}
		if(searchInput.getPrchRebateAmtTo()!=null){
			query.setParameter("prchRebateAmtTo", searchInput.getPrchRebateAmtTo());
		}

		if(searchInput.getPrchRdngDscntAmtFrom()!=null){
			query.setParameter("prchRdngDscntAmtFrom", searchInput.getPrchRdngDscntAmtFrom());
		}
		if(searchInput.getPrchRdngDscntAmtTo()!=null){
			query.setParameter("prchRdngDscntAmtTo", searchInput.getPrchRdngDscntAmtTo());
		}

		if(searchInput.getSlsGrossPrcPreTaxFrom()!=null){
			query.setParameter("slsGrossPrcPreTaxFrom", searchInput.getSlsGrossPrcPreTaxFrom());
		}
		if(searchInput.getSlsGrossPrcPreTaxTo()!=null){
			query.setParameter("slsGrossPrcPreTaxTo", searchInput.getSlsGrossPrcPreTaxTo());
		}

		if(searchInput.getSlsRebateAmtFrom()!=null){
			query.setParameter("slsRebateAmtFrom", searchInput.getSlsRebateAmtFrom());
		}
		if(searchInput.getSlsRebateAmtTo()!=null){
			query.setParameter("slsRebateAmtTo", searchInput.getSlsRebateAmtTo());
		}

		if(searchInput.getSlsRdngDscntAmtFrom()!=null){
			query.setParameter("slsRdngDscntAmtFrom", searchInput.getSlsRdngDscntAmtFrom());
		}
		if(searchInput.getSlsRdngDscntAmtTo()!=null){
			query.setParameter("slsRdngDscntAmtTo", searchInput.getSlsRdngDscntAmtTo());
		}

		if(searchInput.getStkValPreTaxFrom()!=null){
			query.setParameter("stkValPreTaxFrom", searchInput.getStkValPreTaxFrom());
		}
		if(searchInput.getStkValPreTaxTo()!=null){
			query.setParameter("stkValPreTaxTo", searchInput.getStkValPreTaxTo());
		}
	}	
	
	public List<E> findCustom(CoreAbstBsnsObjectSearchInput<E> searchInput)
	{
		StringBuilder qBuilder = preprocessQuery(FIND_CUSTOM_QUERY, searchInput);
		TypedQuery<E> query = getEntityManager().createQuery(qBuilder.toString(), getBsnsObjClass());
		setParameters(searchInput, query);

		int start = searchInput.getStart();
		int max = searchInput.getMax();

		if(start < 0)  start = 0;
		query.setFirstResult(start);
		if(max >= 1) 
			query.setMaxResults(max);
		
		return query.getResultList();
	}

	public Long countCustom(CoreAbstBsnsObjectSearchInput<E> searchInput)
	{
		StringBuilder qBuilder = preprocessQuery(COUNT_CUSTOM_QUERY, searchInput);
		TypedQuery<Long> query = getEntityManager().createQuery(qBuilder.toString(), Long.class);
		setParameters(searchInput, query);
		return query.getSingleResult();
	}
	
}
