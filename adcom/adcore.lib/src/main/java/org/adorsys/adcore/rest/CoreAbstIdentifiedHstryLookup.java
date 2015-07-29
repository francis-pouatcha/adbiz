package org.adorsys.adcore.rest;

import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcore.jpa.CoreAbstIdentifHstry;
import org.adorsys.adcore.repo.CoreAbstIdentifObjectHstryRepo;

public abstract class CoreAbstIdentifiedHstryLookup<E extends CoreAbstIdentifHstry> {

	protected abstract CoreAbstIdentifObjectHstryRepo<E> getRepo();

	public E findById(String id) {
		return getRepo().findBy(id);
	}

	public List<E> listAll(int start, int max) {
		return getRepo().findAll(start, max);
	}

	public Long count() {
		return getRepo().count();
	}

	public List<E> findBy(E entity,
			int start, int max,
			SingularAttribute<E, ?>[] attributes) {
		return getRepo().findBy(entity, start, max, attributes);
	}

	public Long countBy(E entity,
			SingularAttribute<E, ?>[] attributes) {
		return getRepo().count(entity, attributes);
	}

	public List<E> findByLike(E entity,
			int start, int max,
			SingularAttribute<E, ?>[] attributes) {
		return getRepo().findByLike(entity, start, max, attributes);
	}

	public Long countByLike(E entity,
			SingularAttribute<E, ?>[] attributes) {
		return getRepo().countLike(entity, attributes);
	}

	public Long countByEntIdentifAndEntStatus(String identif, String status) {
		return getRepo().findByEntIdentifAndEntStatus(identif, status).count();
	}

	public List<E> findByEntIdentifAndEntStatus(String identif, String status, int start, int max) {
		return getRepo().findByEntIdentifAndEntStatus(identif, status).firstResult(start).maxResults(max).getResultList();
	}

	public Long countByEntIdentif(String identif) {
		return getRepo().findByEntIdentif(identif).count();
	}

	public List<E> findByEntIdentif(String identif, int start, int max) {
		return getRepo().findByEntIdentif(identif).firstResult(start).maxResults(max).getResultList();
	}

	public Long countByEntIdentifAndIdGreaterThan(String identif, String idStart) {
		return getRepo().findByEntIdentifAndIdGreaterThan(identif, idStart).count();
	}

	public List<E> findByEntIdentifAndIdGreaterThan(String identif, String idStart, int start, int max) {
		return getRepo().findByEntIdentifAndIdGreaterThan(identif,idStart).firstResult(start).maxResults(max).getResultList();
	}
	public Long countByEntIdentifAndIdGreaterThanEquals(String identif, String idStart) {
		return getRepo().findByEntIdentifAndIdGreaterThanEquals(identif, idStart).count();
	}

	public List<E> findByEntIdentifAndIdGreaterThanEquals(String identif, String idStart, int start, int max) {
		return getRepo().findByEntIdentifAndIdGreaterThanEquals(identif,idStart).firstResult(start).maxResults(max).getResultList();
	}
	
	public Long countByIdBetween(String idStart, String idEnd){
		return getRepo().findByIdBetween(idStart, idEnd).count();
	}
	public List<E> findByIdBetween(String idStart, String idEnd, int firstResult, int maxResult){
		return getRepo().findByIdBetween(idStart, idEnd).orderAsc("id").firstResult(firstResult).maxResults(maxResult).getResultList();
	}

	public Long countByIdGreaterThan(String idStart){
		return getRepo().findByIdGreaterThan(idStart).count();		
	}
	public List<E> findByIdGreaterThan(String idStart, int firstResult, int maxResult){
		return getRepo().findByIdGreaterThan(idStart).orderAsc("id").firstResult(firstResult).maxResults(maxResult).getResultList();		
	}

	public Long countByIdGreaterThanEquals(String idStart){
		return getRepo().findByIdGreaterThanEquals(idStart).count();				
	}
	public List<E> findByIdGreaterThanEquals(String idStart, int firstResult, int maxResult){
		return getRepo().findByIdGreaterThanEquals(idStart).orderAsc("id").firstResult(firstResult).maxResults(maxResult).getResultList();		
	}

	public List<E> findAll(int firstResult, int max) {
		return getRepo().findAll(firstResult, max);
	}
}
