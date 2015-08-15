package org.adorsys.adcore.loader.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.loader.jpa.CorLdrPrcssngStep;
import org.adorsys.adcore.loader.repo.CorLdrPrcssngStepRepo;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstPrcssngStepEJB;

@Stateless
public class CorLdrPrcssngStepEJB extends
		CoreAbstPrcssngStepEJB<CorLdrPrcssngStep> {

	@Inject
	private CorLdrPrcssngStepRepo repo;

	@Override
	protected CoreAbstIdentifRepo<CorLdrPrcssngStep> getRepo() {
		return repo;
	}

	@Override
	public CorLdrPrcssngStep newInstance() {
		return new CorLdrPrcssngStep();
	}
}
