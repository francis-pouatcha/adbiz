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
import org.adorsys.adcore.rest.CoreAbstBsnsObjInjector;
import org.adorsys.adprocmt.jpa.PrcmtJob;
import org.adorsys.adprocmt.jpa.PrcmtPOItem;
import org.adorsys.adprocmt.jpa.PrcmtProcOrder;
import org.adorsys.adprocmt.jpa.PrcmtProcOrderCstr;
import org.adorsys.adprocmt.jpa.PrcmtProcOrderHstry;
import org.adorsys.adprocmt.jpa.PrcmtStep;
import org.adorsys.adprocmt.repo.PrcmtPOItemRepository;

@Stateless
public class PrcmtPOItemEJB extends CoreAbstBsnsItemEJB<PrcmtProcOrder, PrcmtPOItem, PrcmtProcOrderHstry, PrcmtJob, PrcmtStep, PrcmtProcOrderCstr> {

	@Inject
	private PrcmtPOItemRepository repository;
	@Inject
	private PrcmtProcOrder securityUtil;
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
	
	@Override
	protected CoreAbstBsnsObjInjector<PrcmtProcOrder, PrcmtPOItem, PrcmtProcOrderHstry, PrcmtJob, PrcmtStep, PrcmtProcOrderCstr> getInjector() {
		return null;
	}
	@Override
	protected CoreAbstBsnsItemRepo<PrcmtPOItem> getBsnsRepo() {
		// TODO Auto-generated method stub
		return null;
	}

}
