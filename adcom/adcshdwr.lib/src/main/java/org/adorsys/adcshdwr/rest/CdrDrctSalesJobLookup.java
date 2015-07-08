package org.adorsys.adcshdwr.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstEntityJobRepo;
import org.adorsys.adcore.rest.CoreAbstEntityJobLookup;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesJob;
import org.adorsys.adcshdwr.repo.CdrDrctSalesJobRepo;

@Stateless
public class CdrDrctSalesJobLookup extends CoreAbstEntityJobLookup<CdrDrctSalesJob>{

	@Inject
	private CdrDrctSalesJobRepo repository;

	@Override
	protected CoreAbstEntityJobRepo<CdrDrctSalesJob> getJobRepo() {
		return repository;
	}

}
