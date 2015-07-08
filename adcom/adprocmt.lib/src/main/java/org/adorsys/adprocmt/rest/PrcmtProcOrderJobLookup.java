package org.adorsys.adprocmt.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstEntityJobRepo;
import org.adorsys.adcore.rest.CoreAbstEntityJobLookup;
import org.adorsys.adprocmt.jpa.PrcmtProcOrderJob;
import org.adorsys.adprocmt.repo.PrcmtProcOrderJobRepo;

@Stateless
public class PrcmtProcOrderJobLookup extends CoreAbstEntityJobLookup<PrcmtProcOrderJob>{
	@Inject
	private PrcmtProcOrderJobRepo repository;

	@Override
	protected CoreAbstEntityJobRepo<PrcmtProcOrderJob> getJobRepo() {
		return repository;
	}
}
