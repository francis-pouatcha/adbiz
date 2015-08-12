package org.adorsys.adinvtry.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstEntityStepRepo;
import org.adorsys.adcore.rest.CoreAbstEntityStepLookup;
import org.adorsys.adinvtry.jpa.InvStep;
import org.adorsys.adinvtry.repo.InvStepRepo;

@Stateless
public class InvStepLookup extends CoreAbstEntityStepLookup<InvStep> {

	@Inject
	private InvStepRepo repository;

	@Override
	protected CoreAbstEntityStepRepo<InvStep> getStepRepo() {
		return repository;
	}

	@Override
	protected Class<InvStep> getEntityClass() {
		return InvStep.class;
	}

}
