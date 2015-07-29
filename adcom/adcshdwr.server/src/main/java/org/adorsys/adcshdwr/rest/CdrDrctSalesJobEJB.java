package org.adorsys.adcshdwr.rest;

import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifDataRepo;
import org.adorsys.adcore.rest.CoreAbstEntityJobEJB;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesJob;
import org.adorsys.adcshdwr.repo.CdrDrctSalesJobRepo;

public class CdrDrctSalesJobEJB extends CoreAbstEntityJobEJB<CdrDrctSalesJob>{

	@Inject
	private CdrDrctSalesJobRepo repo;
	
	@Override
	public CdrDrctSalesJob newJobInstance() {
		return new CdrDrctSalesJob();
	}

	@Override
	protected CoreAbstIdentifDataRepo<CdrDrctSalesJob> getRepo() {
		return repo;
	}

}
