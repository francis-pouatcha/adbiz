package org.adorsys.adinvtry.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstEntityJobRepo;
import org.adorsys.adcore.rest.CoreAbstEntityJobLookup;
import org.adorsys.adinvtry.jpa.InvInvtryJob;
import org.adorsys.adinvtry.repo.InvInvtryJobRepo;

@Stateless
public class InvInvtryJobLookup extends CoreAbstEntityJobLookup<InvInvtryJob>{

	@Inject
	private InvInvtryJobRepo repository;

	@Override
	protected CoreAbstEntityJobRepo<InvInvtryJob> getJobRepo() {
		return repository;
	}

	@Override
	protected Class<InvInvtryJob> getEntityClass() {
		return InvInvtryJob.class;
	}

}
