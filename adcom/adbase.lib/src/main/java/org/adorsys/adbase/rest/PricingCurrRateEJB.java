package org.adorsys.adbase.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.jpa.PricingCurrRate;
import org.adorsys.adbase.jpa.PricingCurrRateSearchInput;
import org.adorsys.adbase.jpa.PricingCurrRateSearchResult;
import org.adorsys.adbase.repo.PricingCurrRateRepository;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class PricingCurrRateEJB {
	
	@Inject
	private EntityManager em;

	@Inject
	private PricingCurrRateRepository repository;

	public PricingCurrRate create(PricingCurrRate entity) {
		return repository.save(attach(entity));
	}

	public PricingCurrRate deleteById(String id) {
		PricingCurrRate entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return entity;
	}

	public PricingCurrRate update(PricingCurrRate entity) {
		return repository.save(attach(entity));
	}

	public PricingCurrRate findById(String id) {
		return repository.findBy(id);
	}

	public List<PricingCurrRate> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}

	public List<PricingCurrRate> findBy(PricingCurrRate entity, int start,
			int max, SingularAttribute<PricingCurrRate, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(PricingCurrRate entity,
			SingularAttribute<PricingCurrRate, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<PricingCurrRate> findByLike(PricingCurrRate entity, int start,
			int max, SingularAttribute<PricingCurrRate, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(PricingCurrRate entity,
			SingularAttribute<PricingCurrRate, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private PricingCurrRate attach(PricingCurrRate entity) {
		if (entity == null)
			return null;

		return entity;
	}

	public PricingCurrRate findByIdentif(String identif, Date validOn) {
		List<PricingCurrRate> resultList = repository
				.findByIdentif(identif, validOn).orderAsc("validFrom")
				.maxResults(1).getResultList();
		if (resultList.isEmpty())
			return null;
		return resultList.iterator().next();
	}

	public PricingCurrRate deleteCustom(String id) {
		PricingCurrRate entity = repository.findBy(id);
		if (entity != null) {
			entity.setValidTo(new Date());
			repository.save(entity);
		}
		return entity;

	}
	
	public PricingCurrRateSearchResult doFind(
			PricingCurrRateSearchInput searchInput) {

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT l FROM PricingCurrRate AS l WHERE l.validFrom <=:dateDay AND (l.validTo >:dateDay OR l.validTo IS NULL) ");

		if (StringUtils.isNotBlank(searchInput.getEntity().getSrcCurrISO3())) {

			sb.append("AND LOWER(l.srcCurrISO3) LIKE LOWER(:srcCurrISO3) ");
		}

		if (StringUtils.isNotBlank(searchInput.getEntity().getDestCurrISO3())) {

			sb.append("AND LOWER(l.destCurrISO3) LIKE LOWER(:destCurrISO3) ");
		}

		String query = sb.toString();
		Query createQuery = em.createQuery(query);
		createQuery.setParameter("dateDay", new Date());

		if (StringUtils.isNotBlank(searchInput.getEntity().getSrcCurrISO3())) {

			String srcCurrISO3 = searchInput.getEntity().getSrcCurrISO3() + "%";
			createQuery.setParameter("srcCurrISO3", srcCurrISO3);
		}

		if (StringUtils.isNotBlank(searchInput.getEntity().getDestCurrISO3())) {

			String destCurrISO3 = searchInput.getEntity().getDestCurrISO3()+"%";
			createQuery.setParameter("destCurrISO3", destCurrISO3);
		}

		@SuppressWarnings("unchecked")
		List<PricingCurrRate> resultList = createQuery.getResultList();

		if (searchInput.getStart() >= 0) {
			createQuery.setFirstResult(searchInput.getStart());
		}
		if (searchInput.getMax() > 0) {
			createQuery.setMaxResults(searchInput.getMax());
		}
		List resultList2 = createQuery.getResultList();

		PricingCurrRateSearchResult pricingCurrRateSearchResult = new PricingCurrRateSearchResult();
		pricingCurrRateSearchResult.setCount(Long.valueOf(resultList.size()));
		pricingCurrRateSearchResult.setSearchInput(searchInput);
		pricingCurrRateSearchResult.setResultList(resultList2);

		return pricingCurrRateSearchResult;
	}
}
