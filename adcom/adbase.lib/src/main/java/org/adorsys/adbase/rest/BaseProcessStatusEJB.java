package org.adorsys.adbase.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.jpa.BaseProcessStatus;
import org.adorsys.adbase.repo.BaseProcessStatusRepository;

@Stateless
public class BaseProcessStatusEJB {
	@Inject
	private BaseProcessStatusRepository repository;


	public BaseProcessStatus create(BaseProcessStatus entity) {
		return repository.save(attach(entity));
	}

	public BaseProcessStatus deleteById(String id) {
		BaseProcessStatus entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return entity;
	}

	public BaseProcessStatus update(BaseProcessStatus entity) {
		return repository.save(attach(entity));
	}

	public BaseProcessStatus findById(String id) {
		return repository.findBy(id);
	}

	public List<BaseProcessStatus> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}

	public List<BaseProcessStatus> findBy(BaseProcessStatus entity, int start,
			int max, SingularAttribute<BaseProcessStatus, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(BaseProcessStatus entity,
			SingularAttribute<BaseProcessStatus, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<BaseProcessStatus> findByLike(BaseProcessStatus entity, int start,
			int max, SingularAttribute<BaseProcessStatus, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(BaseProcessStatus entity,
			SingularAttribute<BaseProcessStatus, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private BaseProcessStatus attach(BaseProcessStatus entity) {
		if (entity == null)
			return null;

		return entity;
	}
	
	public BaseProcessStatus findByIdentif(String identif) {
		return findById(identif);
	}
}
