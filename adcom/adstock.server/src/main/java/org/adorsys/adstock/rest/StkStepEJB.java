package org.adorsys.adstock.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstEntityStepEJB;
import org.adorsys.adstock.jpa.StkStep;
import org.adorsys.adstock.repo.StkStepRepo;

@Stateless
public class StkStepEJB extends CoreAbstEntityStepEJB<StkStep>{
	@Inject
	private StkStepRepo repository;

	@Override
	public StkStep newStepInstance() {
		return new StkStep();
	}

	@Override
	protected CoreAbstIdentifRepo<StkStep> getRepo() {
		return repository;
	}

}
