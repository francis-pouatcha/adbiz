package org.adorsys.adinvtry.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcore.enums.CoreHistoryTypeEnum;
import org.adorsys.adcore.rest.CoreAbstEntityHstryEJB;
import org.adorsys.adinvtry.event.InvInvtryPostedEvent;
import org.adorsys.adinvtry.jpa.InvInvtryHstry;
import org.adorsys.adinvtry.jpa.InvInvtryStatus;
import org.adorsys.adinvtry.repo.InvInvtryHstryRepository;

@Stateless
public class InvInvtryHstryEJB extends CoreAbstEntityHstryEJB<InvInvtryHstry> {

	@Inject
	private InvInvtryHstryRepository repository;

	@Inject
	@InvInvtryPostedEvent
	private Event<InvInvtryHstry> invtryPostedEvent;

	public InvInvtryHstry create(InvInvtryHstry entity) {
		InvInvtryHstry invtryHstry = repository.save(attach(entity));

		if (CoreHistoryTypeEnum.POSTED.name().equals(invtryHstry.getHstryType())){
			invtryPostedEvent.fire(invtryHstry);
		}

		return invtryHstry;
	}

	public InvInvtryHstry deleteById(String id) {
		InvInvtryHstry entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return entity;
	}
	public void deleteByEntIdentif(String invtryNbr) {
		List<InvInvtryHstry> list = repository.byEntIdentif(invtryNbr).getResultList();
		for (InvInvtryHstry invInvtryHstry : list) {
			repository.remove(invInvtryHstry);
		}
	}

	public Long countByEntIdentif(String invtryNbr) {
		return repository.byEntIdentif(invtryNbr).count();
	}

	public InvInvtryHstry update(InvInvtryHstry entity) {
		return repository.save(attach(entity));
	}

	public InvInvtryHstry findById(String id) {
		return repository.findBy(id);
	}

	public List<InvInvtryHstry> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}

	public List<InvInvtryHstry> findBy(InvInvtryHstry entity,
			int start, int max,
			SingularAttribute<InvInvtryHstry, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(InvInvtryHstry entity,
			SingularAttribute<InvInvtryHstry, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<InvInvtryHstry> findByLike(InvInvtryHstry entity,
			int start, int max,
			SingularAttribute<InvInvtryHstry, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(InvInvtryHstry entity,
			SingularAttribute<InvInvtryHstry, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private InvInvtryHstry attach(InvInvtryHstry entity) {
		if (entity == null)
			return null;

		return entity;
	}

	public boolean isClosed(String invtryNbr) {
		Long count = countByEntIdentifAndStatus(invtryNbr, InvInvtryStatus.CLOSED.name());
		return count >= 1;
	}

	public List<InvInvtryHstry> findByEntIdentif(String invtryNbr) {
		return repository.byEntIdentif(invtryNbr).getResultList();
	}

	private Long countByEntIdentifAndStatus(String invtryNbr, String status) {
		return repository.countByEntIdentifAndEntStatus(invtryNbr, status);
	}
}
