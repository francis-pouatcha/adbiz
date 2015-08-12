package org.adorsys.adcore.loader.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.loader.jpa.CorLdrJob;
import org.adorsys.adcore.loader.repo.CorLdrJobRepo;
import org.adorsys.adcore.repo.CoreAbstEntityJobRepo;
import org.adorsys.adcore.rest.CoreAbstEntityJobLookup;

@Stateless
public class CorLdrJobLookup extends
		CoreAbstEntityJobLookup<CorLdrJob> {

	@Inject
	private CorLdrJobRepo repo;
	
	@Override
	protected CoreAbstEntityJobRepo<CorLdrJob> getJobRepo() {
		return repo;
	}

	@Override
	protected Class<CorLdrJob> getEntityClass() {
		return CorLdrJob.class;
	}

}
