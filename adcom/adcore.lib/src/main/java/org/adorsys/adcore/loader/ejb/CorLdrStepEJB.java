package org.adorsys.adcore.loader.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.loader.jpa.CorLdrStep;
import org.adorsys.adcore.loader.repo.CorLdrStepRepo;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstEntityStepEJB;

@Stateless
public class CorLdrStepEJB extends CoreAbstEntityStepEJB<CorLdrStep> {

	@Inject
	private CorLdrStepRepo repo;

	@Override
	public CorLdrStep newStepInstance() {
		return new CorLdrStep();
	}

	@Override
	protected CoreAbstIdentifRepo<CorLdrStep> getRepo() {
		return repo;
	}

}
