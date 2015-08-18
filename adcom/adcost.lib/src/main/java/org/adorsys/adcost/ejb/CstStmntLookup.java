package org.adorsys.adcost.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstBsnsObjectRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectLookup;
import org.adorsys.adcost.jpa.CstStmnt;
import org.adorsys.adcost.repo.CstStmntRepo;

@Stateless
public class CstStmntLookup extends CoreAbstBsnsObjectLookup<CstStmnt>
{

	@Inject
	private CstStmntRepo repo;

	@Override
	protected CoreAbstBsnsObjectRepo<CstStmnt> getBsnsRepo() {
		return repo;
	}

	@Override
	protected Class<CstStmnt> getEntityClass() {
		return CstStmnt.class;
	}
}
