package org.adorsys.adstock.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adprocmt.event.PrcmtDeliveryClosedEvent;
import org.adorsys.adprocmt.event.PrcmtDeliveryClosingEvent;
import org.adorsys.adstock.jpa.StkInvceItemHstry;
import org.adorsys.adstock.repo.StkInvceItemHstryRepository;

@Stateless
public class StkInvceItemHstryEJB {

	@Inject
	private StkInvceItemHstryRepository repository;
	
	@Inject
	@PrcmtDeliveryClosingEvent
	private Event<StkInvceItemHstry> deliveryClosingEvent;

	@Inject
	@PrcmtDeliveryClosedEvent
	private Event<StkInvceItemHstry> deliveryClosedEvent;
	
	public StkInvceItemHstry create(StkInvceItemHstry entity) {
		return repository.save(attach(entity));
	}

	public StkInvceItemHstry deleteById(String id) {
		StkInvceItemHstry entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return entity;
	}

	public StkInvceItemHstry update(StkInvceItemHstry entity) {
		return repository.save(attach(entity));
	}

	public StkInvceItemHstry findById(String id) {
		return repository.findBy(id);
	}

	public List<StkInvceItemHstry> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}
	public Long countById(String id) {
		return repository.countById(id);
	}

	public List<StkInvceItemHstry> findBy(StkInvceItemHstry entity,
			int start, int max,
			SingularAttribute<StkInvceItemHstry, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(StkInvceItemHstry entity,
			SingularAttribute<StkInvceItemHstry, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<StkInvceItemHstry> findByLike(StkInvceItemHstry entity,
			int start, int max,
			SingularAttribute<StkInvceItemHstry, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(StkInvceItemHstry entity,
			SingularAttribute<StkInvceItemHstry, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private StkInvceItemHstry attach(StkInvceItemHstry entity) {
		if (entity == null)
			return null;

		return entity;
	}
}
