package org.adorsys.adinvtry.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstEntityStepEJB;
import org.adorsys.adinvtry.jpa.InvStep;
import org.adorsys.adinvtry.repo.InvStepRepo;

@Stateless
public class InvStepEJB extends CoreAbstEntityStepEJB<InvStep>{
	@Inject
	private InvStepRepo repository;

	@Override
	public InvStep newStepInstance() {
		return new InvStep();
	}

	@Override
	protected CoreAbstIdentifRepo<InvStep> getRepo() {
		return repository;
	}

}
