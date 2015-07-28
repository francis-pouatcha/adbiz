package org.adorsys.adstock.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstEntityJobEJB;
import org.adorsys.adstock.jpa.StkMvntJob;
import org.adorsys.adstock.repo.StkMvntJobRepo;

@Stateless
public class StkMvntJobEJB extends CoreAbstEntityJobEJB<StkMvntJob> {

	@Inject
	private StkMvntJobRepo jobRepo;
	
	@Override
	public StkMvntJob newJobInstance() {
		return new StkMvntJob();
	}

	@Override
	protected CoreAbstIdentifRepo<StkMvntJob> getRepo() {
		return jobRepo;
	}
}
