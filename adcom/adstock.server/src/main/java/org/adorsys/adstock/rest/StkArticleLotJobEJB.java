package org.adorsys.adstock.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstEntityJobEJB;
import org.adorsys.adstock.jpa.StkArticleLotJob;
import org.adorsys.adstock.repo.StkArticleLotJobRepo;

@Stateless
public class StkArticleLotJobEJB extends CoreAbstEntityJobEJB<StkArticleLotJob> {

	@Inject
	private StkArticleLotJobRepo jobRepo;
	
	@Override
	public StkArticleLotJob newJobInstance() {
		return new StkArticleLotJob();
	}

	@Override
	protected CoreAbstIdentifRepo<StkArticleLotJob> getRepo() {
		return jobRepo;
	}
}
