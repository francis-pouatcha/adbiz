package org.adorsys.adcshdwr.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstPrcssngStepEJB;
import org.adorsys.adcshdwr.jpa.CdrPrcssgStep;
import org.adorsys.adcshdwr.repo.CdrPrcssgStepRepo;

@Stateless
public class CdrPrcssgStepEJB extends CoreAbstPrcssngStepEJB<CdrPrcssgStep>{
	@Inject
	private CdrPrcssgStepRepo repository;

	@Override
	public CdrPrcssgStep newInstance() {
		return new CdrPrcssgStep();
	}

	@Override
	protected CoreAbstIdentifRepo<CdrPrcssgStep> getRepo() {
		return repository;
	}

}
