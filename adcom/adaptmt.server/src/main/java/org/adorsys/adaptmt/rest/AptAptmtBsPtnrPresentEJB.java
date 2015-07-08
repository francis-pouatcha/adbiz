package org.adorsys.adaptmt.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adaptmt.jpa.AptAptmtBsPtnrPresent;
import org.adorsys.adaptmt.repo.AptAptmtBsPtnrPresentRepository;
import org.jboss.logging.Logger;

@Stateless
public class AptAptmtBsPtnrPresentEJB {

	@Inject
	private AptAptmtBsPtnrPresentRepository repository;
	
	Logger log = Logger.getLogger(this.getClass().getSimpleName());

	public AptAptmtBsPtnrPresent create(AptAptmtBsPtnrPresent entity) {
		log.debug(entity.toString());
		return repository.save(attach(entity));
	}

	public AptAptmtBsPtnrPresent deleteById(String id) {
		AptAptmtBsPtnrPresent entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return entity;
	}

	public AptAptmtBsPtnrPresent update(AptAptmtBsPtnrPresent entity) {
		return repository.save(attach(entity));
	}

	public AptAptmtBsPtnrPresent findById(String id) {
		return repository.findBy(id);
	}

	public List<AptAptmtBsPtnrPresent> findAptmtBsPtnrPresent(String aptmtIdentify) {
		return repository.findAptAptmtBsPtnrPresent(aptmtIdentify).getResultList();
	}

	public List<AptAptmtBsPtnrPresent> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}

	public List<AptAptmtBsPtnrPresent> findBy(AptAptmtBsPtnrPresent entity, int start,
			int max, SingularAttribute<AptAptmtBsPtnrPresent, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(AptAptmtBsPtnrPresent entity,
			SingularAttribute<AptAptmtBsPtnrPresent, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<AptAptmtBsPtnrPresent> findByLike(AptAptmtBsPtnrPresent entity, int start,
			int max, SingularAttribute<AptAptmtBsPtnrPresent, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(AptAptmtBsPtnrPresent entity,
			SingularAttribute<AptAptmtBsPtnrPresent, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private AptAptmtBsPtnrPresent attach(AptAptmtBsPtnrPresent entity) {
		if (entity == null)
			return null;

		return entity;
	}

}
