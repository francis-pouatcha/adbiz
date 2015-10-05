package org.adorsys.adcost.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstEntityJobRepo;
import org.adorsys.adcore.rest.CoreAbstEntityJobLookup;
import org.adorsys.adcost.jpa.CstJob;
import org.adorsys.adcost.repo.CstJobRepo;

@Stateless
public class CstJobLookup extends CoreAbstEntityJobLookup<CstJob>{

	@Inject
	private CstJobRepo repository;

	@Override
	protected CoreAbstEntityJobRepo<CstJob> getJobRepo() {
		return repository;
	}

	@Override
	protected Class<CstJob> getEntityClass() {
		return CstJob.class;
	}
}
