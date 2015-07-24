package org.adorsys.adinvtry.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstEntityStepRepo;
import org.adorsys.adcore.rest.CoreAbstEntityStepLookup;
import org.adorsys.adinvtry.jpa.InvInvtryStep;
import org.adorsys.adinvtry.repo.InvInvtryStepRepo;

@Stateless
public class InvInvtryStepLookup extends CoreAbstEntityStepLookup<InvInvtryStep> {

	@Inject
	private InvInvtryStepRepo repository;

	@Override
	protected CoreAbstEntityStepRepo<InvInvtryStep> getStepRepo() {
		return repository;
	}

	@Override
	protected Class<InvInvtryStep> getEntityClass() {
		return InvInvtryStep.class;
	}

}
