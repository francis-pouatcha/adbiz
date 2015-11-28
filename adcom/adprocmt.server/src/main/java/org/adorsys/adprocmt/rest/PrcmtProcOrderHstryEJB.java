package org.adorsys.adprocmt.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifObjectHstryRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsObjInjector;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectHstryEJB;
import org.adorsys.adprocmt.jpa.PrcmtJob;
import org.adorsys.adprocmt.jpa.PrcmtPOItem;
import org.adorsys.adprocmt.jpa.PrcmtProcOrder;
import org.adorsys.adprocmt.jpa.PrcmtProcOrderCstr;
import org.adorsys.adprocmt.jpa.PrcmtProcOrderHstry;
import org.adorsys.adprocmt.jpa.PrcmtStep;
import org.adorsys.adprocmt.repo.PrcmtProcOrderHstryRepository;

@Stateless
public class PrcmtProcOrderHstryEJB extends CoreAbstBsnsObjectHstryEJB<PrcmtProcOrder, PrcmtPOItem, PrcmtProcOrderHstry, PrcmtJob, PrcmtStep, PrcmtProcOrderCstr>
{
	@Inject
	private PrcmtProcOrderHstryRepository repo;
	
	@Inject
	private PrcmtProcOrderInjector injector;

	@Override
	protected CoreAbstBsnsObjInjector<PrcmtProcOrder, PrcmtPOItem, PrcmtProcOrderHstry, PrcmtJob, PrcmtStep, PrcmtProcOrderCstr> getInjector() {
		return injector;
	}

	@Override
	protected PrcmtProcOrderHstry newHstryObj() {
		return new PrcmtProcOrderHstry();
	}

	@Override
	protected CoreAbstIdentifObjectHstryRepo<PrcmtProcOrderHstry> getRepo() {
		return repo;
	}

}
