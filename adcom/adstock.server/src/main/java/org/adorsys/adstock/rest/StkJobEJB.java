package org.adorsys.adstock.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstEntityJobEJB;
import org.adorsys.adstock.jpa.StkJob;
import org.adorsys.adstock.repo.StkJobRepo;

@Stateless
public class StkJobEJB extends CoreAbstEntityJobEJB<StkJob> {

	@Inject
	private StkJobRepo jobRepo;
	
	@Override
	public StkJob newJobInstance() {
		return new StkJob();
	}

	@Override
	protected CoreAbstIdentifRepo<StkJob> getRepo() {
		return jobRepo;
	}
}
