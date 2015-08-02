package org.adorsys.adsales.rest;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstBsnsItemRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsItemEJB;
import org.adorsys.adcore.rest.CoreAbstBsnsItemLookup;
import org.adorsys.adcore.rest.CoreAbstTxctedItemEJB;
import org.adorsys.adsales.event.SlsConsistentDlvryEvent;
import org.adorsys.adsales.event.SlsInconsistentDlvryEvent;
import org.adorsys.adsales.jpa.SlsDlvryItem;
import org.adorsys.adsales.repo.SlsDlvryItemRepository;

@Stateless
public class SlsDlvryItemEJB extends CoreAbstTxctedItemEJB<SlsDlvryItem> {

	@Inject
	private SlsDlvryItemRepository repository;
	@Inject
	private SlsDlvryItemLookup lookup;
	@Inject
	private SlsDlvryItemEJB ejb;
	@Inject
	@SlsConsistentDlvryEvent
	private Event<String> consistentEvent;
	@Inject
	@SlsInconsistentDlvryEvent
	private Event<String> inconsistentEvent;

	@Override
	protected CoreAbstBsnsItemRepo<SlsDlvryItem> getBsnsRepo() {
		return repository;
	}

	@Override
	protected CoreAbstBsnsItemLookup<SlsDlvryItem> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstBsnsItemEJB<SlsDlvryItem> getEjb() {
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
