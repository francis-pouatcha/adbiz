package org.adorsys.adcshdwr.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstEntityJobRepo;
import org.adorsys.adcore.rest.CoreAbstEntityJobLookup;
import org.adorsys.adcshdwr.jpa.CdrJob;
import org.adorsys.adcshdwr.repo.CdrJobRepo;

@Stateless
public class CdrJobLookup extends CoreAbstEntityJobLookup<CdrJob>{

	@Inject
	private CdrJobRepo repository;

	@Override
	protected CoreAbstEntityJobRepo<CdrJob> getJobRepo() {
		return repository;
	}

	@Override
	protected Class<CdrJob> getEntityClass() {
		return CdrJob.class;
	}
}
