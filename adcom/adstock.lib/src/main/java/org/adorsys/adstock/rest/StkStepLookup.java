package org.adorsys.adstock.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstEntityStepRepo;
import org.adorsys.adcore.rest.CoreAbstEntityStepLookup;
import org.adorsys.adstock.jpa.StkStep;
import org.adorsys.adstock.repo.StkStepRepo;

@Stateless
public class StkStepLookup extends CoreAbstEntityStepLookup<StkStep> {

	@Inject
	private StkStepRepo repository;

	@Override
	protected CoreAbstEntityStepRepo<StkStep> getStepRepo() {
		return repository;
	}

	@Override
	protected Class<StkStep> getEntityClass() {
		return StkStep.class;
	}

}
