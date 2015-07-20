package org.adorsys.adcore.rest;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.adorsys.adcore.jpa.CoreAbstBsnsObject;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.repo.CoreAbstBsnsObjectRepo;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.apache.commons.lang3.StringUtils;

public abstract class CoreAbstBsnsObjectLookup<E extends CoreAbstBsnsObject>
		extends CoreAbstIdentifLookup<E> {

	protected abstract CoreAbstBsnsObjectRepo<E> getBsnsRepo();

	protected CoreAbstIdentifRepo<E> getRepo() {
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
	
	@Override
	protected StringBuilder preprocessQuery(String findOrCount, CoreAbstIdentifObjectSearchInput<E> si){
		// Super
		StringBuilder qBuilder = super.preprocessQuery(findOrCount, si);

		CoreAbstBsnsObjectSearchInput<E> searchInput = (CoreAbstBsnsObjectSearchInput<E>) si;
		boolean whereSet = qBuilder.indexOf(whereClause)>-1;
		E entity = searchInput.getEntity();
		
		if(searchInput.getFieldNames().contains("txGroup") && StringUtils.isNotBlank(entity.getTxGroup())) whereSet = prep(whereSet, qBuilder, "LOWER(e.txGroup) LIKE(LOWER(:txGroup))");
		if(searchInput.getFieldNames().contains("txType") && StringUtils.isNotBlank(entity.getTxType())) whereSet = prep(whereSet, qBuilder, "e.txType=:txType");
		if(searchInput.getFieldNames().contains("section") && StringUtils.isNotBlank(entity.getSection())) whereSet = prep(whereSet, qBuilder, "LOWER(e.section) LIKE(LOWER(:section))");
		if(searchInput.getFieldNames().contains("rangeStart") && StringUtils.isNotBlank(entity.getRangeStart())) whereSet = prep(whereSet, qBuilder, "LOWER(e.rangeStart)>=LOWER(:rangeStart)");
		if(searchInput.getFieldNames().contains("rangeEnd") && StringUtils.isNotBlank(entity.getRangeEnd())) whereSet = prep(whereSet, qBuilder, "LOWER(e.rangeEnd)<=LOWER(:rangeEnd)");
		if(searchInput.getFieldNames().contains("descptn") && StringUtils.isNotBlank(entity.getDescptn())) whereSet = prep(whereSet, qBuilder, "LOWER(e.descptn) LIKE(LOWER(:descptn))");

		if(searchInput.getPrchGrossPrcPreTaxFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.prchGrossPrcPreTaxFrom >= :prchGrossPrcPreTaxFrom");
		if(searchInput.getPrchGrossPrcPreTaxTo()!=null) whereSet = prep(whereSet, qBuilder, "e.prchGrossPrcPreTaxTo <= :prchGrossPrcPreTaxTo");

		if(searchInput.getPrchRebateAmtFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.prchRebateAmtFrom >= :prchRebateAmtFrom");
		if(searchInput.getPrchRebateAmtTo()!=null) whereSet = prep(whereSet, qBuilder, "e.prchRebateAmtTo <= :prchRebateAmtTo");

		if(searchInput.getPrchRdngDscntAmtFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.prchRdngDscntAmtFrom >= :prchRdngDscntAmtFrom");
		if(searchInput.getPrchRdngDscntAmtTo()!=null) whereSet = prep(whereSet, qBuilder, "e.prchRdngDscntAmtTo <= :prchRdngDscntAmtTo");

		if(searchInput.getSlsGrossPrcPreTaxFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.slsGrossPrcPreTaxFrom >= :slsGrossPrcPreTaxFrom");
		if(searchInput.getSlsGrossPrcPreTaxTo()!=null) whereSet = prep(whereSet, qBuilder, "e.slsGrossPrcPreTaxTo <= :slsGrossPrcPreTaxTo");

		if(searchInput.getSlsRebateAmtFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.slsRebateAmtFrom >= :slsRebateAmtFrom");
		if(searchInput.getSlsRebateAmtTo()!=null) whereSet = prep(whereSet, qBuilder, "e.slsRebateAmtTo <= :slsRebateAmtTo");
		
		if(searchInput.getSlsRdngDscntAmtFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.slsRdngDscntAmtFrom >= :slsRdngDscntAmtFrom");
		if(searchInput.getSlsRdngDscntAmtFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.slsRdngDscntAmtFrom <= :slsRdngDscntAmtFrom");

		if(searchInput.getStkValPreTaxFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.stkValPreTaxFrom >= :stkValPreTaxFrom");
		if(searchInput.getStkValPreTaxTo()!=null) whereSet = prep(whereSet, qBuilder, "e.stkValPreTaxTo <= :stkValPreTaxTo");
		
		return qBuilder;
	}
	
	@Override
	protected void setParameters(CoreAbstIdentifObjectSearchInput<E> si, Query query)
	{

		super.setParameters(si, query);

		CoreAbstBsnsObjectSearchInput<E> searchInput = (CoreAbstBsnsObjectSearchInput<E>) si;
		E entity = searchInput.getEntity();

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

}
