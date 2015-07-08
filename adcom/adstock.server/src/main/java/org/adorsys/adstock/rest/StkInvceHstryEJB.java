package org.adorsys.adstock.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adprocmt.event.PrcmtDeliveryClosedEvent;
import org.adorsys.adprocmt.event.PrcmtDeliveryClosingEvent;
import org.adorsys.adstock.jpa.StkInvceHstry;
import org.adorsys.adstock.repo.StkInvceHstryRepository;

@Stateless
public class StkInvceHstryEJB {

	@Inject
	private StkInvceHstryRepository repository;
	
	@Inject
	@PrcmtDeliveryClosingEvent
	private Event<StkInvceHstry> deliveryClosingEvent;

	@Inject
	@PrcmtDeliveryClosedEvent
	private Event<StkInvceHstry> deliveryClosedEvent;
	
	public StkInvceHstry create(StkInvceHstry entity) {
		return repository.save(attach(entity));
	}

	public StkInvceHstry deleteById(String id) {
		StkInvceHstry entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return entity;
	}

	public StkInvceHstry update(StkInvceHstry entity) {
		return repository.save(attach(entity));
	}

	public StkInvceHstry findById(String id) {
		return repository.findBy(id);
	}

	public List<StkInvceHstry> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}
	public Long countById(String id) {
		return repository.countById(id);
	}

	public List<StkInvceHstry> findBy(StkInvceHstry entity,
			int start, int max,
			SingularAttribute<StkInvceHstry, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(StkInvceHstry entity,
			SingularAttribute<StkInvceHstry, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<StkInvceHstry> findByLike(StkInvceHstry entity,
			int start, int max,
			SingularAttribute<StkInvceHstry, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(StkInvceHstry entity,
			SingularAttribute<StkInvceHstry, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private StkInvceHstry attach(StkInvceHstry entity) {
		if (entity == null)
			return null;

		return entity;
	}
}
