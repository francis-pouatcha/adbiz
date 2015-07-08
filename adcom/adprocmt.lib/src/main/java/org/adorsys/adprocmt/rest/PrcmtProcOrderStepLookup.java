package org.adorsys.adprocmt.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstEntityStepRepo;
import org.adorsys.adcore.rest.CoreAbstEntityStepLookup;
import org.adorsys.adprocmt.jpa.PrcmtProcOrderStep;
import org.adorsys.adprocmt.repo.PrcmtProcOrderStepRepo;

@Stateless
public class PrcmtProcOrderStepLookup extends CoreAbstEntityStepLookup<PrcmtProcOrderStep> {

	@Inject
	private PrcmtProcOrderStepRepo repository;

	@Override
	protected CoreAbstEntityStepRepo<PrcmtProcOrderStep> getStepRepo() {
		return repository;
	}

}
