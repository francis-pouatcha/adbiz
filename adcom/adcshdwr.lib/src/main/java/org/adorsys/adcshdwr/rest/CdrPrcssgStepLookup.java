package org.adorsys.adcshdwr.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstPrcssngStepLookup;
import org.adorsys.adcshdwr.jpa.CdrPrcssgStep;
import org.adorsys.adcshdwr.repo.CdrPrcssgStepRepo;

@Stateless
public class CdrPrcssgStepLookup extends CoreAbstPrcssngStepLookup<CdrPrcssgStep> {

	@Inject
	private CdrPrcssgStepRepo repository;

	@Override
	protected CoreAbstIdentifRepo<CdrPrcssgStep> getRepo() {
		return repository;
	}

	@Override
	protected Class<CdrPrcssgStep> getEntityClass() {
		return CdrPrcssgStep.class;
	}
}
