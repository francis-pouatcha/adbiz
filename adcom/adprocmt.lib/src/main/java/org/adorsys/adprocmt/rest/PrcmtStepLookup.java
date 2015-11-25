package org.adorsys.adprocmt.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstEntityStepRepo;
import org.adorsys.adcore.rest.CoreAbstEntityStepLookup;
import org.adorsys.adprocmt.jpa.PrcmtStep;
import org.adorsys.adprocmt.repo.PrcmtStepRepo;

@Stateless
public class PrcmtStepLookup extends CoreAbstEntityStepLookup<PrcmtStep> {

	@Inject
	private PrcmtStepRepo repository;

	@Override
	protected CoreAbstEntityStepRepo<PrcmtStep> getStepRepo() {
		return repository;
	}

	@Override
	protected Class<PrcmtStep> getEntityClass() {
		return PrcmtStep.class;
	}

}
