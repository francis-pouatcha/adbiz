package org.adorsys.adbnsptnr.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbnsptnr.jpa.BpPtnrCtgryDtls;
import org.adorsys.adbnsptnr.repo.BpPtnrCtgryDtlsRepository;

@Stateless
public class BpPtnrCtgryDtlsEJB {

	@Inject
	private BpPtnrCtgryDtlsRepository repository;

	public BpPtnrCtgryDtls create(BpPtnrCtgryDtls entity) {
		return repository.save(attach(entity));
	}

	public BpPtnrCtgryDtls deleteById(String id) {
		BpPtnrCtgryDtls entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return entity;
	}

	public BpPtnrCtgryDtls update(BpPtnrCtgryDtls entity) {
		return repository.save(attach(entity));
	}

	public BpPtnrCtgryDtls findById(String id) {
		return repository.findBy(id);
	}

	public List<BpPtnrCtgryDtls> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}

	public List<BpPtnrCtgryDtls> findBy(BpPtnrCtgryDtls entity, int start,
			int max, SingularAttribute<BpPtnrCtgryDtls, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(BpPtnrCtgryDtls entity,
			SingularAttribute<BpPtnrCtgryDtls, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<BpPtnrCtgryDtls> findByLike(BpPtnrCtgryDtls entity, int start,
			int max, SingularAttribute<BpPtnrCtgryDtls, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(BpPtnrCtgryDtls entity,
			SingularAttribute<BpPtnrCtgryDtls, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private BpPtnrCtgryDtls attach(BpPtnrCtgryDtls entity) {
		if (entity == null)
			return null;

		return entity;
	}

	public BpPtnrCtgryDtls findByIdentif(String identif, Date validOn) {
		List<BpPtnrCtgryDtls> resultList = repository
				.findByIdentif(identif, validOn).orderAsc("validFrom")
				.maxResults(1).getResultList();
		if (resultList.isEmpty())
			return null;
		return resultList.iterator().next();
	}

	public List<BpPtnrCtgryDtls> findByCtgryCode(String ctgryCode) {
		return repository.findByCtgryCode(ctgryCode).getResultList();
	}
}
