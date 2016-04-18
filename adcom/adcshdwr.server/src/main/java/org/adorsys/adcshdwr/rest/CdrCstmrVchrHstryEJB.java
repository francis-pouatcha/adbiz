package org.adorsys.adcshdwr.rest;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.adorsys.adcore.enums.CoreHistoryTypeEnum;
import org.adorsys.adcore.enums.CoreProcStepEnum;
import org.adorsys.adcore.enums.CoreProcessStatusEnum;
import org.adorsys.adcore.event.EntityCanceledEvent;
import org.adorsys.adcore.event.EntitySettledEvent;
import org.adorsys.adcore.repo.CoreAbstIdentifObjectHstryRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedHstryEJB;
import org.adorsys.adcshdwr.jpa.CdrCstmrVchr;
import org.adorsys.adcshdwr.jpa.CdrCstmrVchrHstry;
import org.adorsys.adcshdwr.repo.CdrCstmrVchrHstryHstryRepo;

@Stateless
public class CdrCstmrVchrHstryEJB
 extends CoreAbstIdentifiedHstryEJB<CdrCstmrVchrHstry, CdrCstmrVchr>{

	@Inject
	private CdrCstmrVchrHstryHstryRepo repo;
	
	@Override
	protected CoreAbstIdentifObjectHstryRepo<CdrCstmrVchrHstry> getRepo() {
		return repo;
	}

	@Override
	protected CdrCstmrVchrHstry newHstryObj() {
		return new CdrCstmrVchrHstry();
	}

	public void handleEntityCanceledEvent(@Observes @EntityCanceledEvent CdrCstmrVchr entity) {
		CdrCstmrVchrHstry h = newHstry(entity);
		h.setEntStatus(CoreProcessStatusEnum.CANCELED.name());
		h.setHstryType(CoreHistoryTypeEnum.CANCELED.name());
		h.setProcStep(CoreProcStepEnum.RECALLING.name());
		create(h);
	}

	public void handleEntitySettledEvent(@Observes @EntitySettledEvent CdrCstmrVchr entity) {
		CdrCstmrVchrHstry h = newHstry(entity);
		h.setEntStatus(CoreProcessStatusEnum.CANCELED.name());
		h.setHstryType(CoreHistoryTypeEnum.SETTLED.name());
		h.setProcStep(CoreProcStepEnum.CLOSING.name());
		create(h);
	}
}
