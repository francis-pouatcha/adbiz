package org.adorsys.adbase.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.jpa.ConverterCurrRate;
import org.adorsys.adbase.jpa.ConverterCurrRateSearchInput;
import org.adorsys.adbase.jpa.ConverterCurrRateSearchResult;
import org.adorsys.adbase.jpa.Locality;
import org.adorsys.adbase.jpa.LocalitySearchInput;
import org.adorsys.adbase.jpa.LocalitySearchResult;
import org.adorsys.adbase.repo.ConverterCurrRateRepository;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class ConverterCurrRateEJB {

	@Inject
	private EntityManager em;

	@Inject
	private ConverterCurrRateRepository repository;

	public ConverterCurrRate create(ConverterCurrRate entity) {
		return repository.save(attach(entity));
	}

	public ConverterCurrRate deleteById(String id) {
		ConverterCurrRate entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return entity;
	}
	
	public ConverterCurrRate deleteCustom(String id) {
		ConverterCurrRate entity = repository.findBy(id);
		if (entity != null) {
			entity.setValidTo(new Date());
			repository.save(entity);
		}
		return entity;
	}

	public ConverterCurrRate update(ConverterCurrRate entity) {
		return repository.save(attach(entity));
	}

	public ConverterCurrRate findById(String id) {
		return repository.findBy(id);
	}

	public List<ConverterCurrRate> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}

	public List<ConverterCurrRate> findBy(ConverterCurrRate entity, int start,
			int max, SingularAttribute<ConverterCurrRate, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(ConverterCurrRate entity,
			SingularAttribute<ConverterCurrRate, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<ConverterCurrRate> findByLike(ConverterCurrRate entity,
			int start, int max,
			SingularAttribute<ConverterCurrRate, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(ConverterCurrRate entity,
			SingularAttribute<ConverterCurrRate, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private ConverterCurrRate attach(ConverterCurrRate entity) {
		if (entity == null)
			return null;

		return entity;
	}

	public ConverterCurrRate findByIdentif(String identif, Date validOn) {
		List<ConverterCurrRate> resultList = repository
				.findByIdentif(identif, validOn).orderAsc("validFrom")
				.maxResults(1).getResultList();
		if (resultList.isEmpty())
			return null;
		return resultList.iterator().next();
	}

	public ConverterCurrRateSearchResult findAllActiveConverterCurrRate(
			ConverterCurrRateSearchInput searchInput) {
		Date date = new Date();
		Long count = repository.countActiveConverterCurrRate(date);
		List<ConverterCurrRate> resultList = repository
				.findActiveConverterCurrRate(date)
				.firstResult(searchInput.getStart())
				.maxResults(searchInput.getMax()).getResultList();
		ConverterCurrRateSearchResult searchResult = new ConverterCurrRateSearchResult();
		searchResult.setCount(count);
		searchResult.setResultList(resultList);
		searchResult.setSearchInput(searchInput);
		return searchResult;

	}

	public ConverterCurrRateSearchResult doFind(
			ConverterCurrRateSearchInput searchInput) {

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT l FROM ConverterCurrRate AS l WHERE l.validFrom <=:dateDay AND (l.validTo >:dateDay OR l.validTo IS NULL) ");

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
		List<ConverterCurrRate> resultList = createQuery.getResultList();

		if (searchInput.getStart() >= 0) {
			createQuery.setFirstResult(searchInput.getStart());
		}
		if (searchInput.getMax() > 0) {
			createQuery.setMaxResults(searchInput.getMax());
		}
		@SuppressWarnings("unchecked")
		List<ConverterCurrRate> resultList2 = createQuery.getResultList();

		ConverterCurrRateSearchResult converterCurrRateSearchResult = new ConverterCurrRateSearchResult();
		converterCurrRateSearchResult.setCount(Long.valueOf(resultList.size()));
		converterCurrRateSearchResult.setSearchInput(searchInput);
		converterCurrRateSearchResult.setResultList(resultList2);

		return converterCurrRateSearchResult;
	}
}
