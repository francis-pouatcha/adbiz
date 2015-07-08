package org.adorsys.adstock.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adprocmt.event.PrcmtDeliveryClosedEvent;
import org.adorsys.adprocmt.event.PrcmtDeliveryClosingEvent;
import org.adorsys.adstock.jpa.StkInvtryItemHstry;
import org.adorsys.adstock.repo.StkInvtryItemHstryRepository;

@Stateless
public class StkInvtryItemHstryEJB {

	@Inject
	private StkInvtryItemHstryRepository repository;
	
	@Inject
	@PrcmtDeliveryClosingEvent
	private Event<StkInvtryItemHstry> deliveryClosingEvent;

	@Inject
	@PrcmtDeliveryClosedEvent
	private Event<StkInvtryItemHstry> deliveryClosedEvent;
	
	public StkInvtryItemHstry create(StkInvtryItemHstry entity) {
		return repository.save(attach(entity));
	}

	public StkInvtryItemHstry deleteById(String id) {
		StkInvtryItemHstry entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return entity;
	}

	public StkInvtryItemHstry update(StkInvtryItemHstry entity) {
		return repository.save(attach(entity));
	}

	public StkInvtryItemHstry findById(String id) {
		return repository.findBy(id);
	}

	public List<StkInvtryItemHstry> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}

	public List<StkInvtryItemHstry> findBy(StkInvtryItemHstry entity,
			int start, int max,
			SingularAttribute<StkInvtryItemHstry, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(StkInvtryItemHstry entity,
			SingularAttribute<StkInvtryItemHstry, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<StkInvtryItemHstry> findByLike(StkInvtryItemHstry entity,
			int start, int max,
			SingularAttribute<StkInvtryItemHstry, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(StkInvtryItemHstry entity,
			SingularAttribute<StkInvtryItemHstry, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private StkInvtryItemHstry attach(StkInvtryItemHstry entity) {
		if (entity == null)
			return null;

		return entity;
	}
}
