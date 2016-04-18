package org.adorsys.adcshdwr.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstEntityStepEJB;
import org.adorsys.adcshdwr.jpa.CdrStep;
import org.adorsys.adcshdwr.repo.CdrStepRepo;

@Stateless
public class CdrStepEJB extends CoreAbstEntityStepEJB<CdrStep>{
	@Inject
	private CdrStepRepo repository;

	@Override
	public CdrStep newStepInstance() {
		return new CdrStep();
	}

	@Override
	protected CoreAbstIdentifRepo<CdrStep> getRepo() {
		return repository;
	}

}
