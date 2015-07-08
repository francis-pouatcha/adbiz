package org.adorsys.adbase.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.jpa.BaseProcStep;
import org.adorsys.adbase.repo.BaseProcStepRepository;

@Stateless
public class BaseProcStepEJB {
	@Inject
	private BaseProcStepRepository repository;


	public BaseProcStep create(BaseProcStep entity) {
		return repository.save(attach(entity));
	}

	public BaseProcStep deleteById(String id) {
		BaseProcStep entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return entity;
	}

	public BaseProcStep update(BaseProcStep entity) {
		return repository.save(attach(entity));
	}

	public BaseProcStep findById(String id) {
		return repository.findBy(id);
	}

	public List<BaseProcStep> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}

	public List<BaseProcStep> findBy(BaseProcStep entity, int start,
			int max, SingularAttribute<BaseProcStep, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(BaseProcStep entity,
			SingularAttribute<BaseProcStep, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<BaseProcStep> findByLike(BaseProcStep entity, int start,
			int max, SingularAttribute<BaseProcStep, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(BaseProcStep entity,
			SingularAttribute<BaseProcStep, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private BaseProcStep attach(BaseProcStep entity) {
		if (entity == null)
			return null;

		return entity;
	}
	
	public BaseProcStep findByIdentif(String identif) {
		return findById(identif);
	}
}
