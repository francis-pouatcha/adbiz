package org.adorsys.adbase.rest;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.jpa.SecTerminal;
import org.adorsys.adbase.jpa.SecTerminalSearchInput;
import org.adorsys.adbase.jpa.SecTerminalSearchResult;
import org.adorsys.adbase.repo.SecTerminalRepository;

@Stateless
public class SecTerminalEJB {

	@Inject
	private SecTerminalRepository repository;

	public SecTerminal create(SecTerminal entity) {
		return repository.save(attach(entity));
	}

	public SecTerminal deleteById(String id) {
		SecTerminal entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return entity;
	}

	public SecTerminal update(SecTerminal entity) {
		return repository.save(attach(entity));
	}

	public SecTerminal findById(String id) {
		return repository.findBy(id);
	}

	public List<SecTerminal> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}

	public List<SecTerminal> findBy(SecTerminal entity, int start, int max,
			SingularAttribute<SecTerminal, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(SecTerminal entity,
			SingularAttribute<SecTerminal, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<SecTerminal> findByLike(SecTerminal entity, int start, int max,
			SingularAttribute<SecTerminal, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(SecTerminal entity,
			SingularAttribute<SecTerminal, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private SecTerminal attach(SecTerminal entity) {
		if (entity == null)
			return null;

		return entity;
	}

	public SecTerminal findByIdentif(String identif, Date validOn) {
		List<SecTerminal> resultList = repository
				.findByIdentif(identif, validOn).orderAsc("validFrom")
				.maxResults(1).getResultList();
		if (resultList.isEmpty())
			return null;
		return resultList.iterator().next();
	}

	public SecTerminalSearchResult findAllActiveTerminals(
			SecTerminalSearchInput searchInput) {
		Date date = new Date();
		Long count = repository.countActiveSecTerminal(date);
		List<SecTerminal> resultList = repository.findActiveSecTerminal(date)
				.firstResult(searchInput.getStart())
				.maxResults(searchInput.getMax()).getResultList();
		SecTerminalSearchResult searchResult = new SecTerminalSearchResult();
		searchResult.setCount(count);
		searchResult.setResultList(resultList);
		searchResult.setSearchInput(searchInput);
		return searchResult;

	}

	public SecTerminal deleteCustom(String id) {
		SecTerminal entity = repository.findBy(id);
		if (entity != null) {
			entity.setValidTo(new Date());
			repository.save(entity);
		}
		return entity;
	}
}
