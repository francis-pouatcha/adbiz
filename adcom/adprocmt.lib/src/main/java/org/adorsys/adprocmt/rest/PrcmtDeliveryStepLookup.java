package org.adorsys.adprocmt.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstEntityStepRepo;
import org.adorsys.adcore.rest.CoreAbstEntityStepLookup;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryStep;
import org.adorsys.adprocmt.repo.PrcmtDeliveryStepRepo;

@Stateless
public class PrcmtDeliveryStepLookup extends CoreAbstEntityStepLookup<PrcmtDeliveryStep> {

	@Inject
	private PrcmtDeliveryStepRepo repository;

	@Override
	protected CoreAbstEntityStepRepo<PrcmtDeliveryStep> getStepRepo() {
		return repository;
	}

}
