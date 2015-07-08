package org.adorsys.adbase.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.jpa.BaseHistoryType;
import org.adorsys.adbase.repo.BaseHistoryTypeRepository;

@Stateless
public class BaseHistoryTypeEJB  {
	@Inject
	private BaseHistoryTypeRepository repository;


	public BaseHistoryType create(BaseHistoryType entity) {
		return repository.save(attach(entity));
	}

	public BaseHistoryType deleteById(String id) {
		BaseHistoryType entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return entity;
	}

	public BaseHistoryType update(BaseHistoryType entity) {
		return repository.save(attach(entity));
	}

	public BaseHistoryType findById(String id) {
		return repository.findBy(id);
	}

	public List<BaseHistoryType> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}

	public List<BaseHistoryType> findBy(BaseHistoryType entity, int start,
			int max, SingularAttribute<BaseHistoryType, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(BaseHistoryType entity,
			SingularAttribute<BaseHistoryType, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<BaseHistoryType> findByLike(BaseHistoryType entity, int start,
			int max, SingularAttribute<BaseHistoryType, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(BaseHistoryType entity,
			SingularAttribute<BaseHistoryType, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private BaseHistoryType attach(BaseHistoryType entity) {
		if (entity == null)
			return null;

		return entity;
	}
	
	public BaseHistoryType findByIdentif(String identif) {
		return findById(identif);
	}
}
