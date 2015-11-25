package org.adorsys.adprocmt.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstBsnsObjectRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectLookup;
import org.adorsys.adprocmt.jpa.PrcmtDelivery;
import org.adorsys.adprocmt.repo.PrcmtDeliveryRepository;

@Stateless
public class PrcmtDeliveryLookup extends CoreAbstBsnsObjectLookup<PrcmtDelivery>{

	@Inject
	private PrcmtDeliveryRepository repository;

	@Override
	protected CoreAbstBsnsObjectRepo<PrcmtDelivery> getBsnsRepo() {
		return repository;
	}

	@Override
	protected Class<PrcmtDelivery> getEntityClass() {
		return PrcmtDelivery.class;
	}

	
}
