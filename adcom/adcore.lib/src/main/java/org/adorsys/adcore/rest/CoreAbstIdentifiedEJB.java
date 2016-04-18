package org.adorsys.adcore.rest;

import java.util.List;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;

import org.adorsys.adcore.event.EntityArchivedEvent;
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

	@Inject
	@EntityArchivedEvent
	private Event<E> entityArchivedEvent;

	public E create(E entity) {
		E attached = attach(entity);
		getRepo().persist(attached);
		fireEntityCreatedEvent(attached);
		return attached;
	}

	public E deleteById(String id) {
		E entity = getRepo().findBy(id);
		return internalDelete(entity);
	}
	
	/**
	 * Delete an object without having affecting history.
	 * 
	 * This is used for example in the case of archiving.
	 * 
	 * @param id
	 * @return
	 */
	public E archiveById(String id)
	{
		E entity = getRepo().findBy(id);
		getRepo().remove(entity);
		fireEntityArchivedEvent(entity);
		return entity;
	}


	
	public E deleteByIdentif(String identif){
		E entity = getRepo().findByIdentif(identif).getSingleResult();
		return internalDelete(entity);
	}
	
	public List<E> deleteByCntnrIdentif(String cntnrIdentif){
		long count = getRepo().findByCntnrIdentif(cntnrIdentif).count();
		if(count>1000) throw new UnsupportedOperationException("Bulk delete not allowed for child collections with more that 1000 Items");
		List<E> resultList = getRepo().findByCntnrIdentif(cntnrIdentif).getResultList();
		for (E entity : resultList) {
			internalDelete(entity);
		}
		return resultList;
	}
	
	private E internalDelete(E entity){
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

	protected E silentUpdate(E entity) {
		E saved = getRepo().save(attach(entity));
		return saved;
	}

	protected E attach(E entity) {
		if (entity == null)
			return null;
		
		// Entity being created.
		if(entity.getId()==null)
			return entity;
		
		// First find the one with the corresponding identif.
		// Identif is more important that id.
		E localVersion = getRepo().findOptionalByIdentif(entity.getIdentif());
		if(localVersion==null) throw new EntityNotFoundException("No entity with identif: "  + entity.getIdentif());
		return preUpdate(entity, localVersion);
	}
	
	/**
	 * This method will allow us to reverse undesired changes on key fields.
	 * 
	 * @param newVersion
	 * @param localVersion
	 * @return
	 */
	protected E preUpdate(E newVersion, E localVersion){
		newVersion.setId(localVersion.getId());
		return newVersion;
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
	protected void fireEntityArchivedEvent(E entity) {
		if(entity!=null) entityArchivedEvent.fire(entity);
	}
}
