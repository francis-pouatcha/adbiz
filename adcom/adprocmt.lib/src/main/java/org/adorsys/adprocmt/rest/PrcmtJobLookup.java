package org.adorsys.adprocmt.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstEntityJobRepo;
import org.adorsys.adcore.rest.CoreAbstEntityJobLookup;
import org.adorsys.adprocmt.jpa.PrcmtJob;
import org.adorsys.adprocmt.repo.PrcmtJobRepo;

@Stateless
public class PrcmtJobLookup extends CoreAbstEntityJobLookup<PrcmtJob>{

	@Inject
	private PrcmtJobRepo repository;

	@Override
	protected CoreAbstEntityJobRepo<PrcmtJob> getJobRepo() {
		return repository;
	}

	@Override
	protected Class<PrcmtJob> getEntityClass() {
		return PrcmtJob.class;
	}

}
