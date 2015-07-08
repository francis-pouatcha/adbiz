package org.adorsys.adcshdwr.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstEntityStepRepo;
import org.adorsys.adcore.rest.CoreAbstEntityStepLookup;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesStep;
import org.adorsys.adcshdwr.repo.CdrDrctSalesStepRepo;

@Stateless
public class CdrDrctSalesStepLookup extends CoreAbstEntityStepLookup<CdrDrctSalesStep> {

	@Inject
	private CdrDrctSalesStepRepo repository;

	@Override
	protected CoreAbstEntityStepRepo<CdrDrctSalesStep> getStepRepo() {
		return repository;
	}
}
