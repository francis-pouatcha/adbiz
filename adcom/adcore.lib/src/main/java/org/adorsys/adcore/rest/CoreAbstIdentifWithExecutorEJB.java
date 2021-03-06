package org.adorsys.adcore.rest;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.adorsys.adcore.event.EntityCreatedEvent;
import org.adorsys.adcore.event.EntityDeletedEvent;
import org.adorsys.adcore.event.EntityUpdatedEvent;
import org.adorsys.adcore.jpa.CoreAbstEntityJob;
import org.adorsys.adcore.jpa.CoreAbstEntityStep;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;
import org.adorsys.adcore.jpa.CoreAbstPrcssngStep;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.task.CoreAbstEntityBatch;

public abstract class CoreAbstIdentifWithExecutorEJB<E extends CoreAbstIdentifObject, J extends CoreAbstEntityJob, S extends CoreAbstEntityStep, PS extends CoreAbstPrcssngStep> implements CoreEntityJobExecutor<J,S> {

	protected abstract CoreAbstIdentifRepo<E> getRepo();
	public abstract CoreAbstEntityBatch<J, S, PS> getBatch();
	public abstract CoreEntityJobExecutor<J, S> getEjb();
	
	@Inject
	@EntityCreatedEvent
	private Event<E> entityCreatedEvent;

	@Inject
	@EntityUpdatedEvent
	private Event<E> entityUpdatedEvent;

	@Inject
	@EntityDeletedEvent
	private Event<E> entityDeletedEvent;
	
	@PostConstruct
	public void postConstruct(){
		getBatch().registerJobExecutor(getClass().getSimpleName(), getEjb());
	}
	
	public E create(E entity) {
		E saved = getRepo().save(attach(entity));
		fireEntityCreatedEvent(saved);
		return saved;
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
