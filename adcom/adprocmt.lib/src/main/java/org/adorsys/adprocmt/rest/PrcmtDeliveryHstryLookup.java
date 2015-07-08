package org.adorsys.adprocmt.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstBsnsObjHstryRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectHstryLookup;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryHstry;
import org.adorsys.adprocmt.repo.PrcmtDeliveryHstryRepository;

@Stateless
public class PrcmtDeliveryHstryLookup extends CoreAbstBsnsObjectHstryLookup<PrcmtDeliveryHstry>{

	@Inject
	private PrcmtDeliveryHstryRepository repository;

	@Override
	protected CoreAbstBsnsObjHstryRepo<PrcmtDeliveryHstry> getRepo() {
		return repository;
	}
}
