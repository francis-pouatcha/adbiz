package org.adorsys.adstock.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstEntityJobRepo;
import org.adorsys.adcore.rest.CoreAbstEntityJobLookup;
import org.adorsys.adstock.jpa.StkArticleLotJob;
import org.adorsys.adstock.repo.StkArticleLotJobRepo;

@Stateless
public class StkArticleLotJobLookup extends CoreAbstEntityJobLookup<StkArticleLotJob>{

	@Inject
	private StkArticleLotJobRepo repository;

	@Override
	protected CoreAbstEntityJobRepo<StkArticleLotJob> getJobRepo() {
		return repository;
	}

	@Override
	protected Class<StkArticleLotJob> getEntityClass() {
		return StkArticleLotJob.class;
	}
}
