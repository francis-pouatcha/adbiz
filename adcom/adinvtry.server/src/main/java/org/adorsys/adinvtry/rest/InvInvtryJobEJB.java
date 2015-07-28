package org.adorsys.adinvtry.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstEntityJobEJB;
import org.adorsys.adinvtry.jpa.InvInvtryJob;
import org.adorsys.adinvtry.repo.InvInvtryJobRepo;

@Stateless
public class InvInvtryJobEJB extends CoreAbstEntityJobEJB<InvInvtryJob> {

	@Inject
	private InvInvtryJobRepo jobRepo;
	
	@Override
	public InvInvtryJob newJobInstance() {
		return new InvInvtryJob();
	}

	@Override
	protected CoreAbstIdentifRepo<InvInvtryJob> getRepo() {
		return jobRepo;
	}

}
