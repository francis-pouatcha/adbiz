package org.adorsys.adcore.rest;

import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcore.jpa.CoreAbstBsnsObjectHstry;
import org.adorsys.adcore.repo.CoreAbstBsnsObjHstryRepo;

public abstract class CoreAbstBsnsObjectHstryLookup<E extends CoreAbstBsnsObjectHstry> {

	protected abstract CoreAbstBsnsObjHstryRepo<E> getRepo();

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

	public List<E> findByEntIdentif(String identif) {
		return getRepo().findByEntIdentif(identif).getResultList();
	}

	public Long countByEntIdentif(String identif) {
		return getRepo().findByEntIdentif(identif).count();
	}
	public List<E> findByEntIdentif(String identif, int start, int max) {
		return getRepo().findByEntIdentif(identif).firstResult(start).maxResults(max).getResultList();
	}
	public List<E> findByEntIdentifOrderByIdAsc(String identif, int start, int max) {
		return getRepo().findByEntIdentif(identif).orderAsc("id").firstResult(start).maxResults(max).getResultList();
	}
	public List<E> findByEntIdentifOrderByIdDesc(String identif, int start, int max) {
		return getRepo().findByEntIdentif(identif).orderDesc("id").firstResult(start).maxResults(max).getResultList();
	}
	public Long countByEntIdentifAndIdBetween(String entIdentif, String idStart, String idEnd) {
		return getRepo().findByEntIdentifAndIdBetween(entIdentif, idStart, idEnd).count();
	}
	public List<E> findByEntIdentifAndIdBetweenOrderByIdAsc(String entIdentif, String idStart, String idEnd, int start, int max) {
		return getRepo().findByEntIdentifAndIdBetween(entIdentif, idStart, idEnd).orderAsc("id").firstResult(start).maxResults(max).getResultList();
	}

	public Long countByEntIdentifAndEntStatus(String identif, String status) {
		return getRepo().findByEntIdentifAndEntStatus(identif, status).count();
	}
	public List<E> findByEntIdentifAndEntStatus(String identif, String status, int start, int max) {
		return getRepo().findByEntIdentifAndEntStatus(identif, status).firstResult(start).maxResults(max).getResultList();
	}
	public List<E> findByEntIdentifAndEntStatusOrderByIdAsc(String identif, String status, int start, int max) {
		return getRepo().findByEntIdentifAndEntStatus(identif, status).orderAsc("id").firstResult(start).maxResults(max).getResultList();
	}
	public List<E> findByEntIdentifAndEntStatusOrderByIdDesc(String identif, String status, int start, int max) {
		return getRepo().findByEntIdentifAndEntStatus(identif, status).orderDesc("id").firstResult(start).maxResults(max).getResultList();
	}
	
	public boolean hasAnyStatus(String entIdentif, List<String> entStatusList){
		for (String entStatus : entStatusList) {
			long count = getRepo().findByEntIdentifAndEntStatus(entIdentif, entStatus).count();
			if(count>0) return true;
		}
		return false;
	}
	public boolean hasAnyStatus(String entIdentif, String... entStatusList){
		for (String entStatus : entStatusList) {
			long count = getRepo().findByEntIdentifAndEntStatus(entIdentif, entStatus).count();
			if(count>0) return true;
		}
		return false;
	}
}
