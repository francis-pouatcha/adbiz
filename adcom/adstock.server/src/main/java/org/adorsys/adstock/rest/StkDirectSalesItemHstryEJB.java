package org.adorsys.adstock.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adprocmt.event.PrcmtDeliveryClosedEvent;
import org.adorsys.adprocmt.event.PrcmtDeliveryClosingEvent;
import org.adorsys.adstock.jpa.StkDirectSalesItemHstry;
import org.adorsys.adstock.repo.StkDirectSalesItemHstryRepository;

@Stateless
public class StkDirectSalesItemHstryEJB {

	@Inject
	private StkDirectSalesItemHstryRepository repository;
	
	@Inject
	@PrcmtDeliveryClosingEvent
	private Event<StkDirectSalesItemHstry> deliveryClosingEvent;

	@Inject
	@PrcmtDeliveryClosedEvent
	private Event<StkDirectSalesItemHstry> deliveryClosedEvent;
	
	public StkDirectSalesItemHstry create(StkDirectSalesItemHstry entity) {
		return repository.save(attach(entity));
	}

	public StkDirectSalesItemHstry deleteById(String id) {
		StkDirectSalesItemHstry entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return entity;
	}

	public StkDirectSalesItemHstry update(StkDirectSalesItemHstry entity) {
		return repository.save(attach(entity));
	}

	public StkDirectSalesItemHstry findById(String id) {
		return repository.findBy(id);
	}

	public List<StkDirectSalesItemHstry> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}
	public Long countById(String id) {
		return repository.countById(id);
	}

	public List<StkDirectSalesItemHstry> findBy(StkDirectSalesItemHstry entity,
			int start, int max,
			SingularAttribute<StkDirectSalesItemHstry, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(StkDirectSalesItemHstry entity,
			SingularAttribute<StkDirectSalesItemHstry, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<StkDirectSalesItemHstry> findByLike(StkDirectSalesItemHstry entity,
			int start, int max,
			SingularAttribute<StkDirectSalesItemHstry, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(StkDirectSalesItemHstry entity,
			SingularAttribute<StkDirectSalesItemHstry, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private StkDirectSalesItemHstry attach(StkDirectSalesItemHstry entity) {
		if (entity == null)
			return null;

		return entity;
	}
}
