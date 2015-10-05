package org.adorsys.adcost.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstPrcssngStepEJB;
import org.adorsys.adcost.jpa.CstPrcssgStep;
import org.adorsys.adcost.repo.CstPrcssgStepRepo;

@Stateless
public class CstPrcssgStepEJB extends CoreAbstPrcssngStepEJB<CstPrcssgStep>{
	@Inject
	private CstPrcssgStepRepo repository;

	@Override
	public CstPrcssgStep newInstance() {
		return new CstPrcssgStep();
	}

	@Override
	protected CoreAbstIdentifRepo<CstPrcssgStep> getRepo() {
		return repository;
	}
}
