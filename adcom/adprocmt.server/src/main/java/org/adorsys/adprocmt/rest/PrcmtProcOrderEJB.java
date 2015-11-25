package org.adorsys.adprocmt.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstBsnsObjectRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsObjInjector;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectEJB;
import org.adorsys.adprocmt.jpa.PrcmtJob;
import org.adorsys.adprocmt.jpa.PrcmtPOItem;
import org.adorsys.adprocmt.jpa.PrcmtProcOrder;
import org.adorsys.adprocmt.jpa.PrcmtProcOrderCstr;
import org.adorsys.adprocmt.jpa.PrcmtProcOrderHstry;
import org.adorsys.adprocmt.jpa.PrcmtStep;
import org.adorsys.adprocmt.repo.PrcmtProcOrderRepository;

@Stateless
public class PrcmtProcOrderEJB  extends CoreAbstBsnsObjectEJB<PrcmtProcOrder, PrcmtPOItem, PrcmtProcOrderHstry, PrcmtJob, PrcmtStep, PrcmtProcOrderCstr>{

	@Inject
	private PrcmtProcOrderInjector injector;
	@Inject
	private PrcmtProcOrderRepository repo;
	
	@Override
	protected CoreAbstBsnsObjInjector<PrcmtProcOrder, PrcmtPOItem, PrcmtProcOrderHstry, PrcmtJob, PrcmtStep, PrcmtProcOrderCstr> getInjector() {
		return injector;
	}

	@Override
	protected CoreAbstBsnsObjectRepo<PrcmtProcOrder> getBsnsRepo() {
		return repo;
	}

}
