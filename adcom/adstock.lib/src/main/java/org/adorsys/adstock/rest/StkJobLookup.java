package org.adorsys.adstock.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstEntityJobRepo;
import org.adorsys.adcore.rest.CoreAbstEntityJobLookup;
import org.adorsys.adstock.jpa.StkJob;
import org.adorsys.adstock.repo.StkJobRepo;

@Stateless
public class StkJobLookup extends CoreAbstEntityJobLookup<StkJob>{

	@Inject
	private StkJobRepo repository;

	@Override
	protected CoreAbstEntityJobRepo<StkJob> getJobRepo() {
		return repository;
	}

	@Override
	protected Class<StkJob> getEntityClass() {
		return StkJob.class;
	}

}
