package org.adorsys.adcost.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstBsnsItemRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsItemEJB;
import org.adorsys.adcore.rest.CoreAbstBsnsObjInjector;
import org.adorsys.adcost.jpa.CstJob;
import org.adorsys.adcost.jpa.CstStep;
import org.adorsys.adcost.jpa.CstStmnt;
import org.adorsys.adcost.jpa.CstStmntCstr;
import org.adorsys.adcost.jpa.CstStmntHstry;
import org.adorsys.adcost.jpa.CstStmntItem;
import org.adorsys.adcost.repo.CstStmntItemRepo;

@Stateless
public class CstStmntItemEJB extends CoreAbstBsnsItemEJB<CstStmnt, CstStmntItem, CstStmntHstry, 
	CstJob, CstStep, CstStmntCstr>
{

	@Inject
	private CstStmntItemRepo repository;
	@Inject
	private CstStmntInjector injector;

	@Override
	protected CoreAbstBsnsObjInjector<CstStmnt, CstStmntItem, CstStmntHstry, CstJob, CstStep, CstStmntCstr> getInjector() {
		return injector;
	}

	@Override
	protected CoreAbstBsnsItemRepo<CstStmntItem> getBsnsRepo() {
		return repository;
	}
}
