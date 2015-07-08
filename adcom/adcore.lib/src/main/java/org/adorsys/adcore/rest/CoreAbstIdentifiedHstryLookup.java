package org.adorsys.adcore.rest;

import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectHstry;
import org.adorsys.adcore.repo.CoreAbstIdentifObjectHstryRepo;

public abstract class CoreAbstIdentifiedHstryLookup<E extends CoreAbstIdentifObjectHstry> {

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

	public Long countByEntIdentif(String identif) {
		return getRepo().findByEntIdentif(identif).count();
	}

	public List<E> findByEntIdentif(String invtryNbr, int start, int max) {
		return getRepo().findByEntIdentif(invtryNbr).firstResult(start).maxResults(max).getResultList();
	}

	public Long countByEntIdentifAndEntStatus(String identif, String status) {
		return getRepo().findByEntIdentifAndEntStatus(identif, status).count();
	}

	public List<E> findByEntIdentifAndEntStatus(String identif, String status, int start, int max) {
		return getRepo().findByEntIdentifAndEntStatus(identif, status).firstResult(start).maxResults(max).getResultList();
	}
}
