package org.adorsys.adprocmt.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifObjectHstryRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsObjInjector;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectHstryEJB;
import org.adorsys.adprocmt.jpa.PrcmtDelivery;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryCstr;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryHstry;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem;
import org.adorsys.adprocmt.jpa.PrcmtJob;
import org.adorsys.adprocmt.jpa.PrcmtStep;
import org.adorsys.adprocmt.repo.PrcmtDeliveryHstryRepository;

@Stateless
public class PrcmtDeliveryHstryEJB extends CoreAbstBsnsObjectHstryEJB<PrcmtDelivery, PrcmtDlvryItem, PrcmtDeliveryHstry, PrcmtJob, PrcmtStep, PrcmtDeliveryCstr>
{
	@Inject
	private PrcmtDeliveryHstryRepository repo;
	
	@Inject
	private PrcmtDeliveryInjector injector;

	@Override
	protected CoreAbstBsnsObjInjector<PrcmtDelivery, PrcmtDlvryItem, PrcmtDeliveryHstry, PrcmtJob, PrcmtStep, PrcmtDeliveryCstr> getInjector() {
		return injector;
	}

	@Override
	protected PrcmtDeliveryHstry newHstryObj() {
		return new PrcmtDeliveryHstry();
	}

	@Override
	protected CoreAbstIdentifObjectHstryRepo<PrcmtDeliveryHstry> getRepo() {
		return repo;
	}
}
