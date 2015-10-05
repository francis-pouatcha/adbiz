package org.adorsys.adcost.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstEntityStepRepo;
import org.adorsys.adcore.rest.CoreAbstEntityStepLookup;
import org.adorsys.adcost.jpa.CstStep;
import org.adorsys.adcost.repo.CstStepRepo;

@Stateless
public class CstStepLookup extends CoreAbstEntityStepLookup<CstStep> {

	@Inject
	private CstStepRepo repo;

	@Override
	protected CoreAbstEntityStepRepo<CstStep> getStepRepo() {
		return repo;
	}

	@Override
	protected Class<CstStep> getEntityClass() {
		return CstStep.class;
	}

}
