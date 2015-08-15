package org.adorsys.adcost.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstPrcssngStepLookup;
import org.adorsys.adcost.jpa.CstPrcssgStep;
import org.adorsys.adcost.repo.CstPrcssgStepRepo;

@Stateless
public class CstPrcssgStepLookup extends CoreAbstPrcssngStepLookup<CstPrcssgStep> {

	@Inject
	private CstPrcssgStepRepo repo;

	@Override
	protected CoreAbstIdentifRepo<CstPrcssgStep> getRepo() {
		return repo;
	}

	@Override
	protected Class<CstPrcssgStep> getEntityClass() {
		return CstPrcssgStep.class;
	}
}
