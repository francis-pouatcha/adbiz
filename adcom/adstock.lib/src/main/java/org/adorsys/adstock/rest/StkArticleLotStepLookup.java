package org.adorsys.adstock.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstEntityStepRepo;
import org.adorsys.adcore.rest.CoreAbstEntityStepLookup;
import org.adorsys.adstock.jpa.StkArticleLotStep;
import org.adorsys.adstock.repo.StkArticleLotStepRepo;

@Stateless
public class StkArticleLotStepLookup extends CoreAbstEntityStepLookup<StkArticleLotStep> {

	@Inject
	private StkArticleLotStepRepo repository;

	@Override
	protected CoreAbstEntityStepRepo<StkArticleLotStep> getStepRepo() {
		return repository;
	}

	@Override
	protected Class<StkArticleLotStep> getEntityClass() {
		return StkArticleLotStep.class;
	}

}
