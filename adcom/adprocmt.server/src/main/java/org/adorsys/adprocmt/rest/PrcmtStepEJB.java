package org.adorsys.adprocmt.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstEntityStepEJB;
import org.adorsys.adprocmt.jpa.PrcmtStep;
import org.adorsys.adprocmt.repo.PrcmtStepRepo;

@Stateless
public class PrcmtStepEJB extends CoreAbstEntityStepEJB<PrcmtStep>{
	@Inject
	private PrcmtStepRepo repository;

	@Override
	public PrcmtStep newStepInstance() {
		return new PrcmtStep();
	}

	@Override
	protected CoreAbstIdentifRepo<PrcmtStep> getRepo() {
		return repository;
	}
}
