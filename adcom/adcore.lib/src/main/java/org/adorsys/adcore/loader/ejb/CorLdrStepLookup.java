package org.adorsys.adcore.loader.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.loader.jpa.CorLdrStep;
import org.adorsys.adcore.loader.repo.CorLdrStepRepo;
import org.adorsys.adcore.repo.CoreAbstEntityStepRepo;
import org.adorsys.adcore.rest.CoreAbstEntityStepLookup;

@Stateless
public class CorLdrStepLookup extends
		CoreAbstEntityStepLookup<CorLdrStep> {

	@Inject
	private CorLdrStepRepo repo;
	
	@Override
	protected CoreAbstEntityStepRepo<CorLdrStep> getStepRepo() {
		return repo;
	}

	@Override
	protected Class<CorLdrStep> getEntityClass() {
		return CorLdrStep.class;
	}

	public List<CorLdrStep> findByEntIdentif(String entityIdentif, int start, int max) {
		return getStepRepo().findByEntIdentif(entityIdentif).firstResult(start).maxResults(max).getResultList();
	}

	public Long countByEntIdentif(String entityIdentif){
		return getStepRepo().findByEntIdentif(entityIdentif).count();
	}
	
}
