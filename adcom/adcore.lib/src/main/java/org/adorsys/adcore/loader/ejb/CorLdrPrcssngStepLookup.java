package org.adorsys.adcore.loader.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.loader.jpa.CorLdrPrcssngStep;
import org.adorsys.adcore.loader.repo.CorLdrPrcssngStepRepo;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstPrcssngStepLookup;

@Stateless
public class CorLdrPrcssngStepLookup extends
		CoreAbstPrcssngStepLookup<CorLdrPrcssngStep> {

	@Inject
	private CorLdrPrcssngStepRepo repo;

	@Override
	protected CoreAbstIdentifRepo<CorLdrPrcssngStep> getRepo() {
		return repo;
	}

	@Override
	protected Class<CorLdrPrcssngStep> getEntityClass() {
		return CorLdrPrcssngStep.class;
	}
}
