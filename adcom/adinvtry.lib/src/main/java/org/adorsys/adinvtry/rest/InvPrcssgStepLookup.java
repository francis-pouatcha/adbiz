package org.adorsys.adinvtry.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstPrcssngStepLookup;
import org.adorsys.adinvtry.jpa.InvPrcssgStep;
import org.adorsys.adinvtry.repo.InvPrcssgStepRepo;

@Stateless
public class InvPrcssgStepLookup extends CoreAbstPrcssngStepLookup<InvPrcssgStep> {

	@Inject
	private InvPrcssgStepRepo repository;

	@Override
	protected CoreAbstIdentifRepo<InvPrcssgStep> getRepo() {
		return repository;
	}

	@Override
	protected Class<InvPrcssgStep> getEntityClass() {
		return InvPrcssgStep.class;
	}
}
