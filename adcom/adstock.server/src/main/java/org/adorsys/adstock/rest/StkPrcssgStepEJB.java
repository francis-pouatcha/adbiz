package org.adorsys.adstock.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstPrcssngStepEJB;
import org.adorsys.adstock.jpa.StkPrcssgStep;
import org.adorsys.adstock.repo.StkPrcssgStepRepo;

@Stateless
public class StkPrcssgStepEJB extends CoreAbstPrcssngStepEJB<StkPrcssgStep>{
	@Inject
	private StkPrcssgStepRepo repository;

	@Override
	public StkPrcssgStep newInstance() {
		return new StkPrcssgStep();
	}

	@Override
	protected CoreAbstIdentifRepo<StkPrcssgStep> getRepo() {
		return repository;
	}

}
