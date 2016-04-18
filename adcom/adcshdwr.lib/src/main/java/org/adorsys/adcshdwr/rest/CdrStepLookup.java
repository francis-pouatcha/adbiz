package org.adorsys.adcshdwr.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstEntityStepRepo;
import org.adorsys.adcore.rest.CoreAbstEntityStepLookup;
import org.adorsys.adcshdwr.jpa.CdrStep;
import org.adorsys.adcshdwr.repo.CdrDrctSalesStepRepo;

@Stateless
public class CdrStepLookup extends CoreAbstEntityStepLookup<CdrStep> {

	@Inject
	private CdrDrctSalesStepRepo repository;

	@Override
	protected CoreAbstEntityStepRepo<CdrStep> getStepRepo() {
		return repository;
	}

	@Override
	protected Class<CdrStep> getEntityClass() {
		return CdrStep.class;
	}
}
