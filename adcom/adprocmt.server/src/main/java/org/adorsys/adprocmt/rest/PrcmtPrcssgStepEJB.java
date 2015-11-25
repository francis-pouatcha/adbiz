package org.adorsys.adprocmt.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstPrcssngStepEJB;
import org.adorsys.adprocmt.jpa.PrcmtPrcssgStep;
import org.adorsys.adprocmt.repo.PrcmtPrcssgStepRepo;

@Stateless
public class PrcmtPrcssgStepEJB extends CoreAbstPrcssngStepEJB<PrcmtPrcssgStep> {

	@Inject
	private PrcmtPrcssgStepRepo repo;
	
	@Override
	public PrcmtPrcssgStep newInstance() {
		return new PrcmtPrcssgStep();
	}

	@Override
	protected CoreAbstIdentifRepo<PrcmtPrcssgStep> getRepo() {
		return repo;
	}
}
