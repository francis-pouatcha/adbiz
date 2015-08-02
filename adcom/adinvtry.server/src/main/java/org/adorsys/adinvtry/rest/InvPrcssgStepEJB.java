package org.adorsys.adinvtry.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstPrcssngStepEJB;
import org.adorsys.adinvtry.jpa.InvPrcssgStep;
import org.adorsys.adinvtry.repo.InvPrcssgStepRepo;

@Stateless
public class InvPrcssgStepEJB extends CoreAbstPrcssngStepEJB<InvPrcssgStep>{
	@Inject
	private InvPrcssgStepRepo repository;

	@Override
	public InvPrcssgStep newInstance() {
		return new InvPrcssgStep();
	}

	@Override
	protected CoreAbstIdentifRepo<InvPrcssgStep> getRepo() {
		return repository;
	}

}
