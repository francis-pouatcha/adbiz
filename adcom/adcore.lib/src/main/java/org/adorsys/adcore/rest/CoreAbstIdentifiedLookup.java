package org.adorsys.adcore.rest;

import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcore.jpa.CoreAbstIdentifObject;
import org.adorsys.adcore.repo.CoreAbstIdentifDataRepo;

public abstract class CoreAbstIdentifiedLookup<E extends CoreAbstIdentifObject> {

	protected abstract CoreAbstIdentifDataRepo<E> getRepo();

	public E findById(String id) {
		return getRepo().findBy(id);
	}

	public E findByIdentif(String identif) {
		return getRepo().findOptionalByIdentif(identif);
	}
	
	public Long countByIdentif(String identif){
		return getRepo().findByIdentif(identif).count();
	}
	
	public List<E> listAll(int start, int max) {
		return getRepo().findAll(start, max);
	}

	public Long count() {
		return getRepo().count();
	}

	public List<E> findBy(E entity, int start, int max,
			SingularAttribute<E, ?>[] attributes) {
		return getRepo().findBy(entity, start, max, attributes);
	}

	public Long countBy(E entity,
			SingularAttribute<E, ?>[] attributes) {
		return getRepo().count(entity, attributes);
	}

	public List<E> findByLike(E entity, int start, int max,
			SingularAttribute<E, ?>[] attributes) {
		return getRepo().findByLike(entity, start, max, attributes);
	}

	public Long countByLike(E entity,
			SingularAttribute<E, ?>[] attributes) {
		return getRepo().countLike(entity, attributes);
	}
}
