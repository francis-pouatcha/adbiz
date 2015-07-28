package org.adorsys.adcshdwr.rest;

import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifDataRepo;
import org.adorsys.adcore.rest.CoreAbstEntityStepEJB;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesStep;
import org.adorsys.adcshdwr.repo.CdrDrctSalesStepRepo;

public class CdrDrctSalesStepEjb extends CoreAbstEntityStepEJB<CdrDrctSalesStep> {
	
	@Inject
	private CdrDrctSalesStepRepo repo;
	
	@Override
	public CdrDrctSalesStep newStepInstance() {
		return new CdrDrctSalesStep();
	}

	@Override
	protected CoreAbstIdentifDataRepo<CdrDrctSalesStep> getRepo() {
		return repo;
	}

}
