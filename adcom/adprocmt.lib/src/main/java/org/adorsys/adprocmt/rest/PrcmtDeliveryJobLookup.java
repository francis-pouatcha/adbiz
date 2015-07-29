package org.adorsys.adprocmt.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstEntityJobRepo;
import org.adorsys.adcore.rest.CoreAbstEntityJobLookup;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryJob;
import org.adorsys.adprocmt.repo.PrcmtDeliveryJobRepo;

@Stateless
public class PrcmtDeliveryJobLookup extends CoreAbstEntityJobLookup<PrcmtDeliveryJob>{

	@Inject
	private PrcmtDeliveryJobRepo repository;

	@Override
	protected CoreAbstEntityJobRepo<PrcmtDeliveryJob> getJobRepo() {
		return repository;
	}

}
