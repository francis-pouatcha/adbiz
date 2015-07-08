package org.adorsys.adcshdwr.rest;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstBsnsItemRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsItemEJB;
import org.adorsys.adcore.rest.CoreAbstBsnsItemLookup;
import org.adorsys.adcore.rest.CoreAbstTxctedItemEJB;
import org.adorsys.adcshdwr.jpa.CdrDsArtItem;
import org.adorsys.adcshdwr.repo.CdrDsArtItemRepo;

@Stateless
public class CdrDsArtItemEJB extends CoreAbstTxctedItemEJB<CdrDsArtItem>
{
	@Inject
	private CdrDsArtItemRepo repository;
	@Inject
	private CdrDsArtItemLookup lookup;
	@EJB
	private CdrDsArtItemEJB ejb;
	@Inject
	@CdrConsistentDsEvent
	private Event<String> consistentEvent;
	@Inject
	@CdrInconsistentDsEvent
	private Event<String> inconsistentEvent;

	@Override
	protected CoreAbstBsnsItemRepo<CdrDsArtItem> getBsnsRepo() {
		return repository;
	}

	@Override
	protected CoreAbstBsnsItemLookup<CdrDsArtItem> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstBsnsItemEJB<CdrDsArtItem> getEjb() {
		return ejb;
	}
	@Override
	protected void fireInconsistentEvent(String hldrNbr) {
		inconsistentEvent.fire(hldrNbr);
	}
	@Override
	protected void fireConsistentEvent(String hldrNbr) {
		consistentEvent.fire(hldrNbr);
	}
}
