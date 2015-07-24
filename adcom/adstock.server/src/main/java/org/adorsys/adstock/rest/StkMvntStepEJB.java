package org.adorsys.adstock.rest;

import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstEntityStepEJB;
import org.adorsys.adstock.jpa.StkMvntStep;
import org.adorsys.adstock.repo.StkMvntStepRepo;

public class StkMvntStepEJB extends CoreAbstEntityStepEJB<StkMvntStep>{
	@Inject
	private StkMvntStepRepo repository;

	@Override
	public StkMvntStep newStepInstance() {
		return new StkMvntStep();
	}

	@Override
	protected CoreAbstIdentifRepo<StkMvntStep> getRepo() {
		return repository;
	}

}
