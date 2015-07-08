package org.adorsys.adsales.rest;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstBsnsItemRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsItemEJB;
import org.adorsys.adcore.rest.CoreAbstBsnsItemLookup;
import org.adorsys.adcore.rest.CoreAbstTxctedItemEJB;
import org.adorsys.adsales.event.SlsConsistentInvceEvent;
import org.adorsys.adsales.event.SlsInconsistentInvceEvent;
import org.adorsys.adsales.jpa.SlsInvceItem;
import org.adorsys.adsales.repo.SlsInvceItemRepository;

@Stateless
public class SlsInvceItemEJB extends CoreAbstTxctedItemEJB<SlsInvceItem> {

	@Inject
	private SlsInvceItemRepository repository;
	@Inject
	private SlsInvceItemLookup lookup;
	@Inject
	private SlsInvceItemEJB ejb;
	@Inject
	@SlsConsistentInvceEvent
	private Event<String> consistentEvent;
	@Inject
	@SlsInconsistentInvceEvent
	private Event<String> inconsistentEvent;

	@Override
	protected CoreAbstBsnsItemRepo<SlsInvceItem> getBsnsRepo() {
		return repository;
	}

	@Override
	protected CoreAbstBsnsItemLookup<SlsInvceItem> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstBsnsItemEJB<SlsInvceItem> getEjb() {
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
