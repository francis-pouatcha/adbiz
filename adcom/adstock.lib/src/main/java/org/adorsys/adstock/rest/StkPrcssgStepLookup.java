package org.adorsys.adstock.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstPrcssngStepLookup;
import org.adorsys.adstock.jpa.StkPrcssgStep;
import org.adorsys.adstock.repo.StkPrcssgStepRepo;

@Stateless
public class StkPrcssgStepLookup extends CoreAbstPrcssngStepLookup<StkPrcssgStep> {

	@Inject
	private StkPrcssgStepRepo repository;

	@Override
	protected CoreAbstIdentifRepo<StkPrcssgStep> getRepo() {
		return repository;
	}

	@Override
	protected Class<StkPrcssgStep> getEntityClass() {
		return StkPrcssgStep.class;
	}
}
