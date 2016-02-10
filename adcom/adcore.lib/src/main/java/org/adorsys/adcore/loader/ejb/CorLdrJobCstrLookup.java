package org.adorsys.adcore.loader.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.loader.jpa.CorLdrJobCstr;
import org.adorsys.adcore.loader.repo.CorLdrJobCstrRepo;
import org.adorsys.adcore.repo.CoreAbstEntityCstrRepo;
import org.adorsys.adcore.rest.CoreAbstEntityCstrLookup;

@Stateless
public class CorLdrJobCstrLookup extends
		CoreAbstEntityCstrLookup<CorLdrJobCstr> {

	@Inject
	private CorLdrJobCstrRepo repo;

	@Override
	protected CoreAbstEntityCstrRepo<CorLdrJobCstr> getCstrRepo() {
		return repo;
	}

	@Override
	protected Class<CorLdrJobCstr> getEntityClass() {
		return CorLdrJobCstr.class;
	}

}
