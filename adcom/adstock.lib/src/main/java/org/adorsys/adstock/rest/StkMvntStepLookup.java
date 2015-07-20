package org.adorsys.adstock.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstEntityStepRepo;
import org.adorsys.adcore.rest.CoreAbstEntityStepLookup;
import org.adorsys.adstock.jpa.StkMvntStep;
import org.adorsys.adstock.repo.StkMvntStepRepo;

@Stateless
public class StkMvntStepLookup extends CoreAbstEntityStepLookup<StkMvntStep> {

	@Inject
	private StkMvntStepRepo repository;

	@Override
	protected CoreAbstEntityStepRepo<StkMvntStep> getStepRepo() {
		return repository;
	}

	@Override
	protected Class<StkMvntStep> getEntityClass() {
		return StkMvntStep.class;
	}

}
