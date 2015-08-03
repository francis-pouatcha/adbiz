package org.adorsys.adcost.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstEntityCstrRepo;
import org.adorsys.adcore.rest.CoreAbstEntityCstrLookup;
import org.adorsys.adcost.jpa.CstStmntCstr;
import org.adorsys.adcost.repo.CstStmntCstrRepo;

@Stateless
public class CstStmntCstrLookup extends CoreAbstEntityCstrLookup<CstStmntCstr> {

	@Inject
	private CstStmntCstrRepo repo;

	@Override
	protected CoreAbstEntityCstrRepo<CstStmntCstr> getCstrRepo() {
		return repo;
	}

	@Override
	protected Class<CstStmntCstr> getEntityClass() {
		return CstStmntCstr.class;
	}

}
