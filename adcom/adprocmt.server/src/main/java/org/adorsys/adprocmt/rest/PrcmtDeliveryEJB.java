package org.adorsys.adprocmt.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstBsnsObjectRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsObjInjector;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectEJB;
import org.adorsys.adprocmt.jpa.PrcmtDelivery;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryCstr;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryHstry;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem;
import org.adorsys.adprocmt.jpa.PrcmtJob;
import org.adorsys.adprocmt.jpa.PrcmtStep;
import org.adorsys.adprocmt.repo.PrcmtDeliveryRepository;

@Stateless
public class PrcmtDeliveryEJB extends CoreAbstBsnsObjectEJB<
	PrcmtDelivery, PrcmtDlvryItem, PrcmtDeliveryHstry, 
	PrcmtJob, PrcmtStep, PrcmtDeliveryCstr> {

	@Inject
	private PrcmtDeliveryRepository repo;
	@Inject
	private PrcmtDeliveryInjector injector;
	
	@Override
	protected CoreAbstBsnsObjInjector<PrcmtDelivery, PrcmtDlvryItem, PrcmtDeliveryHstry, PrcmtJob, PrcmtStep, PrcmtDeliveryCstr> getInjector() {
		return injector;
	}

	@Override
	protected CoreAbstBsnsObjectRepo<PrcmtDelivery> getBsnsRepo() {
		return repo;
	}
}
