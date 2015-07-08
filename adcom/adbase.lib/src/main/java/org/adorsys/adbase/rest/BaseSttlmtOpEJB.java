package org.adorsys.adbase.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.jpa.BaseSttlmtOp;
import org.adorsys.adbase.repo.BaseSttlmtOpRepository;

@Stateless
public class BaseSttlmtOpEJB {
	@Inject
	private BaseSttlmtOpRepository repository;


	public BaseSttlmtOp create(BaseSttlmtOp entity) {
		return repository.save(attach(entity));
	}

	public BaseSttlmtOp deleteById(String id) {
		BaseSttlmtOp entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return entity;
	}

	public BaseSttlmtOp update(BaseSttlmtOp entity) {
		return repository.save(attach(entity));
	}

	public BaseSttlmtOp findById(String id) {
		return repository.findBy(id);
	}

	public List<BaseSttlmtOp> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}

	public List<BaseSttlmtOp> findBy(BaseSttlmtOp entity, int start,
			int max, SingularAttribute<BaseSttlmtOp, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(BaseSttlmtOp entity,
			SingularAttribute<BaseSttlmtOp, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<BaseSttlmtOp> findByLike(BaseSttlmtOp entity, int start,
			int max, SingularAttribute<BaseSttlmtOp, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(BaseSttlmtOp entity,
			SingularAttribute<BaseSttlmtOp, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private BaseSttlmtOp attach(BaseSttlmtOp entity) {
		if (entity == null)
			return null;

		return entity;
	}
	
	public BaseSttlmtOp findByIdentif(String identif) {
		return findById(identif);
	}
}
