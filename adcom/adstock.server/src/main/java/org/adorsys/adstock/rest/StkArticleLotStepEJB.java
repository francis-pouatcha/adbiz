package org.adorsys.adstock.rest;

import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstEntityStepEJB;
import org.adorsys.adstock.jpa.StkArticleLotStep;
import org.adorsys.adstock.repo.StkArticleLotStepRepo;

public class StkArticleLotStepEJB extends CoreAbstEntityStepEJB<StkArticleLotStep>{
	@Inject
	private StkArticleLotStepRepo repository;

	@Override
	public StkArticleLotStep newStepInstance() {
		return new StkArticleLotStep();
	}

	@Override
	protected CoreAbstIdentifRepo<StkArticleLotStep> getRepo() {
		return repository;
	}

}
