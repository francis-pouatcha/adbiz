package org.adorsys.adcost.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstEntityJobEJB;
import org.adorsys.adcost.jpa.CstJob;
import org.adorsys.adcost.repo.CstJobRepo;

@Stateless
public class CstJobEJB extends CoreAbstEntityJobEJB<CstJob> {

	@Inject
	private CstJobRepo jobRepo;
	
	@Override
	public CstJob newJobInstance() {
		return new CstJob();
	}

	@Override
	protected CoreAbstIdentifRepo<CstJob> getRepo() {
		return jobRepo;
	}
}
