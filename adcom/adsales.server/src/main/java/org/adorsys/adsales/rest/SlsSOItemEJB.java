package org.adorsys.adsales.rest;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstBsnsItemRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsItemEJB;
import org.adorsys.adcore.rest.CoreAbstBsnsItemLookup;
import org.adorsys.adcore.rest.CoreAbstTxctedItemEJB;
import org.adorsys.adsales.event.SlsConsistentSalesOrderEvent;
import org.adorsys.adsales.event.SlsInconsistentSalesOrderEvent;
import org.adorsys.adsales.jpa.SlsSOItem;
import org.adorsys.adsales.repo.SlsSOItemRepository;

@Stateless
public class SlsSOItemEJB extends CoreAbstTxctedItemEJB<SlsSOItem> {

	@Inject
	private SlsSOItemRepository repository;
	@Inject
	private SlsSOItemLookup lookup;
	@EJB
	private SlsSOItemEJB ejb;
	@Inject
	@SlsConsistentSalesOrderEvent
	private Event<String> consistentEvent;
	@Inject
	@SlsInconsistentSalesOrderEvent
	private Event<String> inconsistentEvent;

	@Override
	protected CoreAbstBsnsItemRepo<SlsSOItem> getBsnsRepo() {
		return repository;
	}

	@Override
	protected CoreAbstBsnsItemLookup<SlsSOItem> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstBsnsItemEJB<SlsSOItem> getEjb() {
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
