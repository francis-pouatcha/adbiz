package org.adorsys.adcost.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstBsnsItemRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsItemLookup;
import org.adorsys.adcost.jpa.CstStmntItem;
import org.adorsys.adcost.repo.CstStmntItemRepo;

@Stateless
public class CstStmntItemLookup extends CoreAbstBsnsItemLookup<CstStmntItem> {
	@Inject
	private CstStmntItemRepo repository;

	@Override
	protected CoreAbstBsnsItemRepo<CstStmntItem> getBsnsRepo() {
		return repository;
	}

	@Override
	protected Class<CstStmntItem> getEntityClass() {
		return CstStmntItem.class;
	}
}
