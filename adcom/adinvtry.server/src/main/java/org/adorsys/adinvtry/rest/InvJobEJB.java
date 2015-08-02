package org.adorsys.adinvtry.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstEntityJobEJB;
import org.adorsys.adinvtry.jpa.InvJob;
import org.adorsys.adinvtry.repo.InvJobRepo;

@Stateless
public class InvJobEJB extends CoreAbstEntityJobEJB<InvJob> {

	@Inject
	private InvJobRepo jobRepo;
	
	@Override
	public InvJob newJobInstance() {
		return new InvJob();
	}

	@Override
	protected CoreAbstIdentifRepo<InvJob> getRepo() {
		return jobRepo;
	}

}
