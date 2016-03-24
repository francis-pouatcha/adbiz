package org.adorsys.adprocmt.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstPrcssngStepLookup;
import org.adorsys.adprocmt.jpa.PrcmtPrcssgStep;
import org.adorsys.adprocmt.repo.PrcmtPrcssgStepRepo;

@Stateless
public class PrcmtPrcssgStepLookup extends CoreAbstPrcssngStepLookup<PrcmtPrcssgStep> {

	@Inject
	private PrcmtPrcssgStepRepo repo;
	
	@Override
	protected CoreAbstIdentifRepo<PrcmtPrcssgStep> getRepo() {
		return repo;
	}

	@Override
	protected Class<PrcmtPrcssgStep> getEntityClass() {
		return PrcmtPrcssgStep.class;
	}
}
