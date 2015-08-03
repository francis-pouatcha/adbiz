package org.adorsys.adcore.rest;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.adorsys.adcore.event.EntityCreatedEvent;
import org.adorsys.adcore.event.EntityDeletedEvent;
import org.adorsys.adcore.event.EntityUpdatedEvent;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;

public abstract class CoreAbstIdentifiedEJB<E extends CoreAbstIdentifObject> {

	protected abstract CoreAbstIdentifRepo<E> getRepo();
	
	@Inject
	@EntityCreatedEvent
	private Event<E> entityCreatedEvent;

	@Inject
	@EntityUpdatedEvent
	private Event<E> entityUpdatedEvent;

	@Inject
	@EntityDeletedEvent
	private Event<E> entityDeletedEvent;
	
	public E create(E entity) {
		E attached = attach(entity);
		getRepo().persist(attached);
		fireEntityCreatedEvent(attached);
		return attached;
	}

	public E deleteById(String id) {
		E entity = getRepo().findBy(id);
		if (entity != null) {
			getRepo().remove(entity);
			fireEntityDeletedEvent(entity);
		}
		return entity;
	}

	public E update(E entity) {
		E saved = getRepo().save(attach(entity));
		fireEntityUpdatedEvent(saved);
		return saved;
	}


	protected E attach(E entity) {
		if (entity == null)
			return null;
		return entity;
	}

	protected void fireEntityCreatedEvent(E saved) {
		if(saved!=null) entityCreatedEvent.fire(saved);
	}
	protected void fireEntityDeletedEvent(E entity) {
		if(entity!=null) entityDeletedEvent.fire(entity);
	}
	protected void fireEntityUpdatedEvent(E saved) {
		if(saved!=null) entityUpdatedEvent.fire(saved);
	}
}
