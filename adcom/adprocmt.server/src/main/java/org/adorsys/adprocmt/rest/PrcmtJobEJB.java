package org.adorsys.adprocmt.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstEntityJobEJB;
import org.adorsys.adprocmt.jpa.PrcmtJob;
import org.adorsys.adprocmt.repo.PrcmtJobRepo;

@Stateless
public class PrcmtJobEJB extends CoreAbstEntityJobEJB<PrcmtJob> {

	@Inject
	private PrcmtJobRepo jobRepo;
	
	@Override
	public PrcmtJob newJobInstance() {
		return new PrcmtJob();
	}

	@Override
	protected CoreAbstIdentifRepo<PrcmtJob> getRepo() {
		return jobRepo;
	}

}
