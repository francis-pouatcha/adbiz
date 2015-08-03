package org.adorsys.adcore.loader.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.loader.jpa.CorLdrJob;
import org.adorsys.adcore.loader.repo.CorLdrJobRepo;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstEntityJobEJB;

@Stateless
public class CorLdrJobEJB extends CoreAbstEntityJobEJB<CorLdrJob> {

	@Inject
	private CorLdrJobRepo repo;

	@Override
	public CorLdrJob newJobInstance() {
		return new CorLdrJob();
	}

	@Override
	protected CoreAbstIdentifRepo<CorLdrJob> getRepo() {
		return repo;
	}

}
