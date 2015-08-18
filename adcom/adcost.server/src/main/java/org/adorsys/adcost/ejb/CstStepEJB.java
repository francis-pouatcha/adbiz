package org.adorsys.adcost.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstEntityStepEJB;
import org.adorsys.adcost.jpa.CstStep;
import org.adorsys.adcost.repo.CstStepRepo;

@Stateless
public class CstStepEJB extends CoreAbstEntityStepEJB<CstStep>{
	@Inject
	private CstStepRepo repository;

	@Override
	public CstStep newStepInstance() {
		return new CstStep();
	}

	@Override
	protected CoreAbstIdentifRepo<CstStep> getRepo() {
		return repository;
	}

}
