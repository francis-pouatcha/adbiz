package org.adorsys.adcshdwr.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstEntityJobEJB;
import org.adorsys.adcshdwr.jpa.CdrJob;
import org.adorsys.adcshdwr.repo.CdrJobRepo;

@Stateless
public class CdrJobEJB extends CoreAbstEntityJobEJB<CdrJob> {

	@Inject
	private CdrJobRepo jobRepo;
	
	@Override
	public CdrJob newJobInstance() {
		return new CdrJob();
	}

	@Override
	protected CoreAbstIdentifRepo<CdrJob> getRepo() {
		return jobRepo;
	}

}
