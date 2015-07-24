package org.adorsys.adinvtry.rest;

import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstEntityStepEJB;
import org.adorsys.adinvtry.jpa.InvInvtryStep;
import org.adorsys.adinvtry.repo.InvInvtryStepRepo;

public class InvInvtryStepEJB extends CoreAbstEntityStepEJB<InvInvtryStep>{
	@Inject
	private InvInvtryStepRepo repository;

	@Override
	public InvInvtryStep newStepInstance() {
		return new InvInvtryStep();
	}

	@Override
	protected CoreAbstIdentifRepo<InvInvtryStep> getRepo() {
		return repository;
	}

}
