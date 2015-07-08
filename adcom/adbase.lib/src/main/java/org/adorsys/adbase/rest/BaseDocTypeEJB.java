package org.adorsys.adbase.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.jpa.BaseDocType;
import org.adorsys.adbase.repo.BaseDocTypeRepository;

@Stateless
public class BaseDocTypeEJB {
	@Inject
	private BaseDocTypeRepository repository;


	public BaseDocType create(BaseDocType entity) {
		return repository.save(attach(entity));
	}

	public BaseDocType deleteById(String id) {
		BaseDocType entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return entity;
	}

	public BaseDocType update(BaseDocType entity) {
		return repository.save(attach(entity));
	}

	public BaseDocType findById(String id) {
		return repository.findBy(id);
	}

	public List<BaseDocType> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}

	public List<BaseDocType> findBy(BaseDocType entity, int start,
			int max, SingularAttribute<BaseDocType, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(BaseDocType entity,
			SingularAttribute<BaseDocType, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<BaseDocType> findByLike(BaseDocType entity, int start,
			int max, SingularAttribute<BaseDocType, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(BaseDocType entity,
			SingularAttribute<BaseDocType, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private BaseDocType attach(BaseDocType entity) {
		if (entity == null)
			return null;

		return entity;
	}
	
	public BaseDocType findByIdentif(String identif) {
		return findById(identif);
	}
}
