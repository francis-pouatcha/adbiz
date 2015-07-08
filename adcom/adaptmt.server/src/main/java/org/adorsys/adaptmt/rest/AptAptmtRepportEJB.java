package org.adorsys.adaptmt.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adaptmt.jpa.AptAptmtRepport;
import org.adorsys.adaptmt.repo.AptAptmtRepportRepository;
import org.adorsys.adbase.jpa.Login;
import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adcore.utils.SequenceGenerator;

@Stateless
public class AptAptmtRepportEJB {

	@Inject
	private AptAptmtRepportRepository repository;

	@Inject
	private SecurityUtil securityUtil;

	public AptAptmtRepport create(AptAptmtRepport entity) {

		Date now = new Date();
        entity.setCreated(now);
        
        entity.setAptmtRepportnNbr(SequenceGenerator
				.getSequence(SequenceGenerator.APPOINTMENTREPPORT_NUMBER_SEQUENCE_PREFIXE));
         
		Login login = securityUtil.getConnectedUser();
		entity.setCreateUserId(login.getIdentif());

		return repository.save(attach(entity));
	}

	public AptAptmtRepport deleteById(String id) {
		AptAptmtRepport entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return entity;
	}

	public AptAptmtRepport update(AptAptmtRepport entity) {
		return repository.save(attach(entity));
	}

	public AptAptmtRepport findById(String id) {
		return repository.findBy(id);
	}

	public List<AptAptmtRepport> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}

	public List<AptAptmtRepport> findBy(AptAptmtRepport entity, int start, int max,
			SingularAttribute<AptAptmtRepport, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(AptAptmtRepport entity,
			SingularAttribute<AptAptmtRepport, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<AptAptmtRepport> findByLike(AptAptmtRepport entity, int start, int max,
			SingularAttribute<AptAptmtRepport, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(AptAptmtRepport entity,
			SingularAttribute<AptAptmtRepport, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private AptAptmtRepport attach(AptAptmtRepport entity) {
		if (entity == null)
			return null;

		return entity;
	}


	public List<AptAptmtRepport> findPreviousAptAptmtRepport(String id) {
		return repository.findPreviousAptAptmtRepport(id).maxResults(2)
				.getResultList();
	}

	public List<AptAptmtRepport> findNextAptAptmtRepport(String id) {
		return repository.findNextAptAptmtRepport(id).maxResults(2).getResultList();
	}

}
