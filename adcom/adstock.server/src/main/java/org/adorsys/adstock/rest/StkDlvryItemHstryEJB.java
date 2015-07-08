package org.adorsys.adstock.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adprocmt.event.PrcmtDeliveryClosedEvent;
import org.adorsys.adprocmt.event.PrcmtDeliveryClosingEvent;
import org.adorsys.adstock.jpa.StkDlvryItemHstry;
import org.adorsys.adstock.repo.StkDlvryItemHstryRepository;

@Stateless
public class StkDlvryItemHstryEJB {

	@Inject
	private StkDlvryItemHstryRepository repository;
	
	@Inject
	@PrcmtDeliveryClosingEvent
	private Event<StkDlvryItemHstry> deliveryClosingEvent;

	@Inject
	@PrcmtDeliveryClosedEvent
	private Event<StkDlvryItemHstry> deliveryClosedEvent;
	
	public StkDlvryItemHstry create(StkDlvryItemHstry entity) {
		return repository.save(attach(entity));
	}

	public StkDlvryItemHstry deleteById(String id) {
		StkDlvryItemHstry entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return entity;
	}

	public StkDlvryItemHstry update(StkDlvryItemHstry entity) {
		return repository.save(attach(entity));
	}

	public StkDlvryItemHstry findById(String id) {
		return repository.findBy(id);
	}

	public List<StkDlvryItemHstry> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}

	public List<StkDlvryItemHstry> findBy(StkDlvryItemHstry entity,
			int start, int max,
			SingularAttribute<StkDlvryItemHstry, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(StkDlvryItemHstry entity,
			SingularAttribute<StkDlvryItemHstry, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<StkDlvryItemHstry> findByLike(StkDlvryItemHstry entity,
			int start, int max,
			SingularAttribute<StkDlvryItemHstry, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(StkDlvryItemHstry entity,
			SingularAttribute<StkDlvryItemHstry, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private StkDlvryItemHstry attach(StkDlvryItemHstry entity) {
		if (entity == null)
			return null;

		return entity;
	}
}
