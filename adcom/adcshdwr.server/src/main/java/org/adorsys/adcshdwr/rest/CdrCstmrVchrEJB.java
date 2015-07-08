package org.adorsys.adcshdwr.rest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adcore.exceptions.AdException;
import org.adorsys.adcore.utils.SequenceGenerator;
import org.adorsys.adcshdwr.api.CdrDrctSalesPrinterEJB;
import org.adorsys.adcshdwr.api.CdrDsArtHolder;
import org.adorsys.adcshdwr.api.CdrDsArtItemHolder;
import org.adorsys.adcshdwr.jpa.CdrCshDrawer;
import org.adorsys.adcshdwr.jpa.CdrCstmrVchr;
import org.adorsys.adcshdwr.jpa.CdrCstmrVchrSearchInput;
import org.adorsys.adcshdwr.jpa.CdrDrctSales;
import org.adorsys.adcshdwr.jpa.CdrDsArtItem;
import org.adorsys.adcshdwr.repo.CdrCstmrVchrRepository;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class CdrCstmrVchrEJB {

	@Inject
	private CdrCstmrVchrRepository repository;
	@Inject
	private SecurityUtil securityUtil;
	@Inject
	private CdrCshDrawerEJB cshDrawerEJB;
	@Inject
	private CdrDrctSalesPrinterEJB salesPrinterEJB;
	
	@Inject
	private EntityManager em;

	public CdrCstmrVchr create(CdrCstmrVchr entity)
   {
	   if (StringUtils.isBlank(entity.getVchrNbr())) {
			entity.setVchrNbr(SequenceGenerator
					.getSequence(SequenceGenerator.VOUCHER_SEQUENCE_PREFIXE));
		}
	   
	   entity.setCanceled(false);
	   entity.setPrntDt(new Date());
	   entity.setCashier(securityUtil.getCurrentLoginName());
      return repository.save(attach(entity));
   }

	public CdrCstmrVchr deleteById(String id) {
		CdrCstmrVchr entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return entity;
	}

	public CdrCstmrVchr update(CdrCstmrVchr entity) {
		return repository.save(attach(entity));
	}

	public CdrCstmrVchr findById(String id) {
		return repository.findBy(id);
	}

	public List<CdrCstmrVchr> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}

	public List<CdrCstmrVchr> findBy(CdrCstmrVchr entity, int start, int max,
			SingularAttribute<CdrCstmrVchr, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(CdrCstmrVchr entity,
			SingularAttribute<CdrCstmrVchr, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<CdrCstmrVchr> findByLike(CdrCstmrVchr entity, int start,
			int max, SingularAttribute<CdrCstmrVchr, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(CdrCstmrVchr entity,
			SingularAttribute<CdrCstmrVchr, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private CdrCstmrVchr attach(CdrCstmrVchr entity) {
		if (entity == null)
			return null;

		return entity;
	}

	public List<CdrCstmrVchr> findByVchrNbr(String vchrNbr) {
		return repository.findByVchrNbr(vchrNbr);
	}
	
	public List<CdrCstmrVchr> findByDsNbr(String dsNbr){
		return repository.findByDsNbr(dsNbr);
	}
//
//	public void generateVoucher(CdrDsArtHolder cdrDsArtHolder)
//			throws AdException {
//		CdrDrctSales cdrDrctSales = cdrDsArtHolder.getCdrDrctSales();
//		BigDecimal amt = BigDecimal.ZERO;
////		for (CdrDsArtItemHolder itemHolder : cdrDsArtHolder.getItems()) {
////			CdrDsArtItem item = itemHolder.getItem();
////			if (item.getReturnedQty() != null
////					&& item.getReturnedQty().compareTo(BigDecimal.ZERO) == 1) {
////				BigDecimal grossSPPreTaxReturned = item.getReturnedQty()
////						.multiply(item.getSppuPreTax());
////				BigDecimal vatAmountReturned = grossSPPreTaxReturned.divide(
////						BigDecimal.valueOf(100)).multiply(item.getVatPct());
////				amt = amt.add(grossSPPreTaxReturned).add(vatAmountReturned);
////			}
////		}
//		cdrDrctSales.evlte();
//		CdrCstmrVchr vchr = new CdrCstmrVchr();
//		vchr.setDsNbr(cdrDrctSales.getDsNbr());
//		CdrCshDrawer activeCshDrawer = cshDrawerEJB.getActiveCshDrawer();
//		vchr.setCdrNbr(activeCshDrawer.getCdrNbr());
//		vchr.setAmt(amt);
//		vchr.evlte();
//		vchr = create(vchr);
//		activeCshDrawer.AddTtlVchrOut(vchr.getAmt());
//		cshDrawerEJB.update(activeCshDrawer);
//		// Print voucher pdf
//		//salesPrinterEJB.printVoucherPdf(vchr);
//	}

	private static final String FIND_CUSTOM_QUERY = "SELECT e FROM CdrCstmrVchr AS e";
	private static final String COUNT_CUSTOM_QUERY = "SELECT count(e.id) FROM CdrCstmrVchr AS e";

	public Long countCustom(CdrCstmrVchrSearchInput searchInput) {
		StringBuilder qBuilder = preprocessQuery(COUNT_CUSTOM_QUERY,
				searchInput);
		TypedQuery<Long> query = em
				.createQuery(qBuilder.toString(), Long.class);
		setParameters(searchInput, query);
		return query.getSingleResult();
	}

	public List<CdrCstmrVchr> findCustom(CdrCstmrVchrSearchInput searchInput) {
		StringBuilder qBuilder = preprocessQuery(FIND_CUSTOM_QUERY, searchInput);
		TypedQuery<CdrCstmrVchr> query = em.createQuery(qBuilder.toString(),
				CdrCstmrVchr.class);
		setParameters(searchInput, query);

		int start = searchInput.getStart();
		int max = searchInput.getMax();

		if (start < 0)
			start = 0;
		query.setFirstResult(start);
		if (max >= 1)
			query.setMaxResults(max);

		return query.getResultList();
	}

	private StringBuilder preprocessQuery(String findOrCount,
			CdrCstmrVchrSearchInput searchInput) {
		CdrCstmrVchr entity = searchInput.getEntity();

		String whereClause = " WHERE  ";
		String andClause = " AND ";

		StringBuilder qBuilder = new StringBuilder(findOrCount);
		boolean whereSet = false;

		if (StringUtils.isNotBlank(entity.getVchrNbr())) {
			if (!whereSet) {
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.vchrNbr=:vchrNbr");
		}
		if (searchInput.getFrom() != null) {
			if (!whereSet) {
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.prntDt>:from");
		}
		if (searchInput.getTo() != null) {
			if (!whereSet) {
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.prntDt<:to");
		}
		return qBuilder;
	}

	public void setParameters(CdrCstmrVchrSearchInput searchInput, Query query) {
		CdrCstmrVchr entity = searchInput.getEntity();

		if (StringUtils.isNotBlank(entity.getVchrNbr())) {
			query.setParameter("vchrNbr", entity.getVchrNbr());
		}
		if (searchInput.getFrom() != null) {
			query.setParameter("from", searchInput.getFrom());
		}
		if (searchInput.getTo() != null) {
			query.setParameter("to", searchInput.getTo());
		}
	}

	public CdrCstmrVchr cancel(CdrCstmrVchr entity) {
		entity.setCanceled(true);
		return repository.save(attach(entity));
	}

}
