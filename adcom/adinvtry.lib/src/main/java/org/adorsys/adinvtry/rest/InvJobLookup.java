package org.adorsys.adinvtry.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstEntityJobRepo;
import org.adorsys.adcore.rest.CoreAbstEntityJobLookup;
import org.adorsys.adinvtry.jpa.InvJob;
import org.adorsys.adinvtry.repo.InvJobRepo;

@Stateless
public class InvJobLookup extends CoreAbstEntityJobLookup<InvJob>{

	@Inject
	private InvJobRepo repository;

	@Override
	protected CoreAbstEntityJobRepo<InvJob> getJobRepo() {
		return repository;
	}

	@Override
	protected Class<InvJob> getEntityClass() {
		return InvJob.class;
	}

}
