package org.adorsys.adcshdwr.rest;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.adorsys.adcore.event.EntityCanceledEvent;
import org.adorsys.adcore.event.EntitySettledEvent;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.utils.SequenceGenerator;
import org.adorsys.adcshdwr.jpa.CdrCstmrVchr;
import org.adorsys.adcshdwr.repo.CdrCstmrVchrRepository;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class CdrCstmrVchrEJB extends CoreAbstIdentifiedEJB<CdrCstmrVchr> {

	@Inject
	private CdrCstmrVchrRepository repository;
	
	@Inject
	@EntityCanceledEvent
	private Event<CdrCstmrVchr> entityCanceledEvent;

	@Inject
	@EntitySettledEvent
	private Event<CdrCstmrVchr> entitySettledEvent;

	public CdrCstmrVchr create(CdrCstmrVchr entity) {
		if (StringUtils.isBlank(entity.getIdentif())) {
			entity.setIdentif(SequenceGenerator.getSequence(SequenceGenerator.VOUCHER_SEQUENCE_PREFIXE));
		}
		return super.create(entity);
	}

	public CdrCstmrVchr cancel(CdrCstmrVchr entity) {
		entity = attach(entity);
		entity.setCanceled(true);
		entity = silentUpdate(entity);
		entityCanceledEvent.fire(entity);
		return entity;
	}

	public CdrCstmrVchr settle(CdrCstmrVchr entity) {
		entity = attach(entity);
		entity.setSettled(true);
		entity = silentUpdate(entity);
		entitySettledEvent.fire(entity);
		return entity;
	}
	
	@Override
	protected CoreAbstIdentifRepo<CdrCstmrVchr> getRepo() {
		return repository;
	}
}
