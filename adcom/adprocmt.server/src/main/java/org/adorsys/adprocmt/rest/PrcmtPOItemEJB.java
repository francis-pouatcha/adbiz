package org.adorsys.adprocmt.rest;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adcore.repo.CoreAbstBsnsItemRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsItemEJB;
import org.adorsys.adcore.rest.CoreAbstBsnsItemLookup;
import org.adorsys.adcore.rest.CoreAbstTxctedItemEJB;
import org.adorsys.adprocmt.jpa.PrcmtPOItem;
import org.adorsys.adprocmt.repo.PrcmtPOItemRepository;

@Stateless
public class PrcmtPOItemEJB extends CoreAbstTxctedItemEJB<PrcmtPOItem> {

	@Inject
	private PrcmtPOItemRepository repository;
	@Inject
	private SecurityUtil securityUtil;
	@Inject
	private PrcmtPOItemLookup lookup;
	@EJB
	private PrcmtPOItemEJB ejb;
	@Inject
	@PcrmConsistentProcOrderEvent
	private Event<String> consistentEvent;
	@Inject
	@PcrmInconsistentProcOrderEvent
	private Event<String> inconsistentEvent;

	public PrcmtPOItem create(PrcmtPOItem entity) {

		String currentLoginName = securityUtil.getCurrentLoginName();
		Date now = new Date();
		entity.setAcsngUser(currentLoginName);
		entity.setAcsngDt(now);
		return super.create(entity);
	}

	@Override
	protected CoreAbstBsnsItemRepo<PrcmtPOItem> getBsnsRepo() {
		return repository;
	}

	@Override
	protected CoreAbstBsnsItemLookup<PrcmtPOItem> getLookup() {
		return lookup;
	}
	
	@Override
	protected CoreAbstBsnsItemEJB<PrcmtPOItem> getEjb() {
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
