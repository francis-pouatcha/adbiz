package org.adorsys.adstock.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstEntityJobRepo;
import org.adorsys.adcore.rest.CoreAbstEntityJobLookup;
import org.adorsys.adstock.jpa.StkMvntJob;
import org.adorsys.adstock.repo.StkMvntJobRepo;

@Stateless
public class StkMvntJobLookup extends CoreAbstEntityJobLookup<StkMvntJob>{

	@Inject
	private StkMvntJobRepo repository;

	@Override
	protected CoreAbstEntityJobRepo<StkMvntJob> getJobRepo() {
		return repository;
	}

	@Override
	protected Class<StkMvntJob> getEntityClass() {
		return StkMvntJob.class;
	}
}
