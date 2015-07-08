package org.adorsys.adbase.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.jpa.BaseBatchEvt;
import org.adorsys.adbase.repo.BaseBatchEvtRepository;

@Stateless
public class BaseBatchEvtEJB {
	@Inject
	private BaseBatchEvtRepository repository;


	public BaseBatchEvt create(BaseBatchEvt entity) {
		return repository.save(attach(entity));
	}

	public BaseBatchEvt deleteById(String id) {
		BaseBatchEvt entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return entity;
	}

	public BaseBatchEvt update(BaseBatchEvt entity) {
		return repository.save(attach(entity));
	}

	public BaseBatchEvt findById(String id) {
		return repository.findBy(id);
	}

	public List<BaseBatchEvt> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}

	public List<BaseBatchEvt> findBy(BaseBatchEvt entity, int start,
			int max, SingularAttribute<BaseBatchEvt, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(BaseBatchEvt entity,
			SingularAttribute<BaseBatchEvt, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<BaseBatchEvt> findByLike(BaseBatchEvt entity, int start,
			int max, SingularAttribute<BaseBatchEvt, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(BaseBatchEvt entity,
			SingularAttribute<BaseBatchEvt, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private BaseBatchEvt attach(BaseBatchEvt entity) {
		if (entity == null)
			return null;

		return entity;
	}
	
	public BaseBatchEvt findByIdentif(String identif) {
		return findById(identif);
	}
	
	public List<BaseBatchEvt> findByEvtModuleAndEvtKlassAndEvtName(String evtModule, String evtKlass, String evtName, int firstResult, int maxResult){
		return repository.findByEvtModuleAndEvtKlassAndEvtName(evtModule, evtKlass, evtName).firstResult(firstResult).maxResults(maxResult).getResultList();
	}

	public List<BaseBatchEvt> findByEntIdentifAndEvtModuleAndEvtKlassAndEvtName(String entIdentif, String evtModule, String evtKlass, String evtName, int firstResult, int maxResult){
		return repository.findByEntIdentifAndEvtModuleAndEvtKlassAndEvtName(entIdentif, evtModule, evtKlass, evtName).firstResult(firstResult).maxResults(maxResult).getResultList();
	}
}
