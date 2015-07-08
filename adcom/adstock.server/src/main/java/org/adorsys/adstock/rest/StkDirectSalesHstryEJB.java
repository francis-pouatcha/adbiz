package org.adorsys.adstock.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adprocmt.event.PrcmtDeliveryClosedEvent;
import org.adorsys.adprocmt.event.PrcmtDeliveryClosingEvent;
import org.adorsys.adstock.jpa.StkDirectSalesHstry;
import org.adorsys.adstock.repo.StkDirectSalesHstryRepository;

@Stateless
public class StkDirectSalesHstryEJB {

	@Inject
	private StkDirectSalesHstryRepository repository;
	
	@Inject
	@PrcmtDeliveryClosingEvent
	private Event<StkDirectSalesHstry> deliveryClosingEvent;

	@Inject
	@PrcmtDeliveryClosedEvent
	private Event<StkDirectSalesHstry> deliveryClosedEvent;
	
	public StkDirectSalesHstry create(StkDirectSalesHstry entity) {
		return repository.save(attach(entity));
	}

	public StkDirectSalesHstry deleteById(String id) {
		StkDirectSalesHstry entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return entity;
	}

	public StkDirectSalesHstry update(StkDirectSalesHstry entity) {
		return repository.save(attach(entity));
	}

	public StkDirectSalesHstry findById(String id) {
		return repository.findBy(id);
	}

	public List<StkDirectSalesHstry> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}
	public Long countById(String id) {
		return repository.countById(id);
	}

	public List<StkDirectSalesHstry> findBy(StkDirectSalesHstry entity,
			int start, int max,
			SingularAttribute<StkDirectSalesHstry, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(StkDirectSalesHstry entity,
			SingularAttribute<StkDirectSalesHstry, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<StkDirectSalesHstry> findByLike(StkDirectSalesHstry entity,
			int start, int max,
			SingularAttribute<StkDirectSalesHstry, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(StkDirectSalesHstry entity,
			SingularAttribute<StkDirectSalesHstry, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private StkDirectSalesHstry attach(StkDirectSalesHstry entity) {
		if (entity == null)
			return null;

		return entity;
	}
}
