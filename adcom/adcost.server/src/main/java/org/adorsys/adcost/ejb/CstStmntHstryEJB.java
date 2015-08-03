package org.adorsys.adcost.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifObjectHstryRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsObjInjector;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectHstryEJB;
import org.adorsys.adcost.jpa.CstJob;
import org.adorsys.adcost.jpa.CstStep;
import org.adorsys.adcost.jpa.CstStmnt;
import org.adorsys.adcost.jpa.CstStmntCstr;
import org.adorsys.adcost.jpa.CstStmntHstry;
import org.adorsys.adcost.jpa.CstStmntItem;
import org.adorsys.adcost.repo.CstStmntHstryRepo;

@Stateless
public class CstStmntHstryEJB extends CoreAbstBsnsObjectHstryEJB<CstStmnt, CstStmntItem, CstStmntHstry, CstJob, CstStep, CstStmntCstr> {

	@Inject
	private CstStmntHstryRepo repository;
	
	@Inject
	private CstStmntInjector injector;

	@Override
	protected CoreAbstBsnsObjInjector<CstStmnt, CstStmntItem, CstStmntHstry, CstJob, CstStep, CstStmntCstr> getInjector() {
		return injector;
	}

	@Override
	protected CstStmntHstry newHstryObj() {
		return new CstStmntHstry();
	}
	
	@Override
	protected CoreAbstIdentifObjectHstryRepo<CstStmntHstry> getRepo() {
		return repository;
	}
}
